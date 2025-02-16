package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import java.util.HashSet;
import java.util.Set;

@SimpleObject
@DesignerComponent(category = ComponentCategory.SENSORS, description = "A Component that acts like a Pedometer. It senses motion via the Accelerometer and attempts to determine if a step has been taken. Using a configurable stride length, it can estimate the distance traveled as well. ", iconName = "images/pedometer.png", nonVisible = true, version = 3)
public class Pedometer extends AndroidNonvisibleComponent implements Component, SensorEventListener, Deleteable, RealTimeDataSource<String, Float> {
    private static final int INTERVAL_VARIATION = 250;
    private static final int NUM_INTERVALS = 2;
    private static final float PEAK_VALLEY_RANGE = 40.0f;
    private static final String PREFS_NAME = "PedometerPrefs";
    private static final float STRIDE_LENGTH = 0.73f;
    private static final String TAG = "Pedometer";
    private static final int WIN_SIZE = 100;
    private int avgPos = 0;
    private float[] avgWindow = new float[10];
    private final Context context;
    private Set<DataSourceChangeListener> dataSourceObservers = new HashSet();
    private boolean foundNonStep = true;
    private boolean foundValley = false;
    private int intervalPos = 0;
    private float lastValley = 0.0f;
    private float[] lastValues = new float[100];
    private int numStepsRaw = 0;
    private int numStepsWithFilter = 0;
    private boolean pedometerPaused = true;
    private long prevStopClockTime = 0;
    private final SensorManager sensorManager;
    private boolean startPeaking = false;
    private long startTime = 0;
    private long[] stepInterval = new long[2];
    private long stepTimestamp = 0;
    private int stopDetectionTimeout = 2000;
    private float strideLength = STRIDE_LENGTH;
    private float totalDistance = 0.0f;
    private int winPos = 0;

    public Pedometer(ComponentContainer container) {
        super(container.$form());
        Activity $context = container.$context();
        this.context = $context;
        this.winPos = 0;
        this.startPeaking = false;
        this.numStepsWithFilter = 0;
        this.numStepsRaw = 0;
        this.foundValley = true;
        this.lastValley = 0.0f;
        this.sensorManager = (SensorManager) $context.getSystemService("sensor");
        SharedPreferences settings = $context.getSharedPreferences(PREFS_NAME, 0);
        this.strideLength = settings.getFloat("Pedometer.stridelength", STRIDE_LENGTH);
        this.totalDistance = settings.getFloat("Pedometer.distance", 0.0f);
        this.numStepsRaw = settings.getInt("Pedometer.prevStepCount", 0);
        this.prevStopClockTime = settings.getLong("Pedometer.clockTime", 0);
        this.numStepsWithFilter = this.numStepsRaw;
        this.startTime = System.currentTimeMillis();
        Log.d(TAG, "Pedometer Created");
    }

    @SimpleFunction(description = "Start counting steps")
    public void Start() {
        if (this.pedometerPaused) {
            this.pedometerPaused = false;
            SensorManager sensorManager2 = this.sensorManager;
            sensorManager2.registerListener(this, sensorManager2.getSensorList(1).get(0), 0);
            this.startTime = System.currentTimeMillis();
        }
    }

    @SimpleFunction(description = "Stop counting steps")
    public void Stop() {
        if (!this.pedometerPaused) {
            this.pedometerPaused = true;
            this.sensorManager.unregisterListener(this);
            Log.d(TAG, "Unregistered listener on pause");
            this.prevStopClockTime += System.currentTimeMillis() - this.startTime;
        }
    }

    @SimpleFunction(description = "Resets the step counter, distance measure and time running.")
    public void Reset() {
        this.numStepsWithFilter = 0;
        this.numStepsRaw = 0;
        this.totalDistance = 0.0f;
        this.prevStopClockTime = 0;
        this.startTime = System.currentTimeMillis();
    }

    @Deprecated
    @SimpleFunction(description = "Resumes counting, synonym of Start.")
    public void Resume() {
        Start();
    }

    @Deprecated
    @SimpleFunction(description = "Pause counting of steps and distance.")
    public void Pause() {
        Stop();
    }

    @SimpleFunction(description = "Saves the pedometer state to the phone. Permits permits accumulation of steps and distance between invocations of an App that uses the pedometer. Different Apps will have their own saved state.")
    public void Save() {
        SharedPreferences.Editor editor = this.context.getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putFloat("Pedometer.stridelength", this.strideLength);
        editor.putFloat("Pedometer.distance", this.totalDistance);
        editor.putInt("Pedometer.prevStepCount", this.numStepsRaw);
        if (this.pedometerPaused) {
            editor.putLong("Pedometer.clockTime", this.prevStopClockTime);
        } else {
            editor.putLong("Pedometer.clockTime", this.prevStopClockTime + (System.currentTimeMillis() - this.startTime));
        }
        editor.putLong("Pedometer.closeTime", System.currentTimeMillis());
        editor.commit();
        Log.d(TAG, "Pedometer state saved.");
    }

    @SimpleEvent(description = "This event is run when a raw step is detected.")
    public void SimpleStep(int simpleSteps, float distance) {
        notifyDataObservers("SimpleSteps", (Object) Integer.valueOf(simpleSteps));
        notifyDataObservers("Distance", (Object) Float.valueOf(distance));
        EventDispatcher.dispatchEvent(this, "SimpleStep", Integer.valueOf(simpleSteps), Float.valueOf(distance));
    }

    @SimpleEvent(description = "This event is run when a walking step is detected. A walking step is a step that appears to be involved in forward motion.")
    public void WalkStep(int walkSteps, float distance) {
        notifyDataObservers("WalkSteps", (Object) Integer.valueOf(walkSteps));
        notifyDataObservers("Distance", (Object) Float.valueOf(distance));
        EventDispatcher.dispatchEvent(this, "WalkStep", Integer.valueOf(walkSteps), Float.valueOf(distance));
    }

    @DesignerProperty(defaultValue = "0.73", editorType = "non_negative_float")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Set the average stride length in meters.")
    public void StrideLength(float length) {
        this.strideLength = length;
    }

    @SimpleProperty
    public float StrideLength() {
        return this.strideLength;
    }

    @DesignerProperty(defaultValue = "2000", editorType = "non_negative_integer")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The duration in milliseconds of idleness (no steps detected) after which to go into a \"stopped\" state")
    public void StopDetectionTimeout(int timeout) {
        this.stopDetectionTimeout = timeout;
    }

    @SimpleProperty
    public int StopDetectionTimeout() {
        return this.stopDetectionTimeout;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The approximate distance traveled in meters.")
    public float Distance() {
        return this.totalDistance;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Time elapsed in milliseconds since the pedometer was started.")
    public long ElapsedTime() {
        if (this.pedometerPaused) {
            return this.prevStopClockTime;
        }
        return this.prevStopClockTime + (System.currentTimeMillis() - this.startTime);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The number of simple steps taken since the pedometer has started.")
    public int SimpleSteps() {
        return this.numStepsRaw;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "the number of walk steps taken since the pedometer has started.")
    public int WalkSteps() {
        return this.numStepsWithFilter;
    }

    private boolean areStepsEquallySpaced() {
        float avg = 0.0f;
        int num = 0;
        for (long interval : this.stepInterval) {
            if (interval > 0) {
                num++;
                avg += (float) interval;
            }
        }
        float avg2 = avg / ((float) num);
        for (long interval2 : this.stepInterval) {
            if (Math.abs(((float) interval2) - avg2) > 250.0f) {
                return false;
            }
        }
        return true;
    }

    private boolean isPeak() {
        int mid = (this.winPos + 50) % 100;
        for (int i = 0; i < 100; i++) {
            if (i != mid) {
                float[] fArr = this.lastValues;
                if (fArr[i] > fArr[mid]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValley() {
        int mid = (this.winPos + 50) % 100;
        for (int i = 0; i < 100; i++) {
            if (i != mid) {
                float[] fArr = this.lastValues;
                if (fArr[i] < fArr[mid]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "Accelerometer accuracy changed.");
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == 1) {
            float magnitude = 0.0f;
            for (float v : event.values) {
                magnitude += v * v;
            }
            int mid = (this.winPos + 50) % 100;
            if (this.startPeaking && isPeak() && this.foundValley && this.lastValues[mid] - this.lastValley > PEAK_VALLEY_RANGE) {
                long timestamp = System.currentTimeMillis();
                long[] jArr = this.stepInterval;
                int i = this.intervalPos;
                jArr[i] = timestamp - this.stepTimestamp;
                this.intervalPos = (i + 1) % 2;
                this.stepTimestamp = timestamp;
                if (areStepsEquallySpaced()) {
                    if (this.foundNonStep) {
                        this.numStepsWithFilter += 2;
                        this.totalDistance += this.strideLength * 2.0f;
                        this.foundNonStep = false;
                    }
                    int i2 = this.numStepsWithFilter + 1;
                    this.numStepsWithFilter = i2;
                    WalkStep(i2, this.totalDistance);
                    this.totalDistance += this.strideLength;
                } else {
                    this.foundNonStep = true;
                }
                int i3 = this.numStepsRaw + 1;
                this.numStepsRaw = i3;
                SimpleStep(i3, this.totalDistance);
                this.foundValley = false;
            }
            if (this.startPeaking && isValley()) {
                this.foundValley = true;
                this.lastValley = this.lastValues[mid];
            }
            float[] fArr = this.avgWindow;
            int i4 = this.avgPos;
            fArr[i4] = magnitude;
            this.avgPos = (i4 + 1) % fArr.length;
            this.lastValues[this.winPos] = 0.0f;
            for (float m : fArr) {
                float[] fArr2 = this.lastValues;
                int i5 = this.winPos;
                fArr2[i5] = fArr2[i5] + m;
            }
            float[] fArr3 = this.lastValues;
            int i6 = this.winPos;
            float length = fArr3[i6] / ((float) this.avgWindow.length);
            fArr3[i6] = length;
            boolean z = this.startPeaking;
            if (z || i6 > 1) {
                int i7 = this.winPos - 1;
                if (i7 < 0) {
                    i7 += 100;
                }
                float f = length + (fArr3[i7] * 2.0f);
                fArr3[i6] = f;
                int i8 = i7 - 1;
                if (i8 < 0) {
                    i8 += 100;
                }
                float f2 = f + fArr3[i8];
                fArr3[i6] = f2;
                fArr3[i6] = f2 / 4.0f;
            } else if (!z && i6 == 1) {
                fArr3[1] = (fArr3[1] + fArr3[0]) / 2.0f;
            }
            long elapsedTimestamp = System.currentTimeMillis();
            if (elapsedTimestamp - this.stepTimestamp > ((long) this.stopDetectionTimeout)) {
                this.stepTimestamp = elapsedTimestamp;
            }
            int i9 = this.winPos;
            if (i9 == 99 && !this.startPeaking) {
                this.startPeaking = true;
            }
            this.winPos = (i9 + 1) % 100;
        }
    }

    public void onDelete() {
        this.sensorManager.unregisterListener(this);
    }

    @SimpleEvent(description = "This event has been deprecated.")
    @Deprecated
    public void StartedMoving() {
    }

    @SimpleEvent(description = "This event has been deprecated.")
    @Deprecated
    public void StoppedMoving() {
    }

    @Deprecated
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This property has been deprecated.")
    public void UseGPS(boolean gps) {
    }

    @SimpleEvent(description = "This event has been deprecated.")
    @Deprecated
    public void CalibrationFailed() {
    }

    @SimpleEvent(description = "This event has been deprecated.")
    @Deprecated
    public void GPSAvailable() {
    }

    @SimpleEvent(description = "This event has been deprecated.")
    @Deprecated
    public void GPSLost() {
    }

    @Deprecated
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This property has been deprecated.")
    public void CalibrateStrideLength(boolean cal) {
    }

    @Deprecated
    @SimpleProperty(description = "This property has been deprecated.")
    public boolean CalibrateStrideLength() {
        return false;
    }

    @Deprecated
    @SimpleProperty(description = "This property has been deprecated.")
    public boolean Moving() {
        return false;
    }

    public void addDataObserver(DataSourceChangeListener dataComponent) {
        this.dataSourceObservers.add(dataComponent);
    }

    public void removeDataObserver(DataSourceChangeListener dataComponent) {
        this.dataSourceObservers.remove(dataComponent);
    }

    public void notifyDataObservers(String key, Object value) {
        for (DataSourceChangeListener dataComponent : this.dataSourceObservers) {
            dataComponent.onReceiveValue(this, key, value);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Float getDataValue(java.lang.String r2) {
        /*
            r1 = this;
            int r0 = r2.hashCode()
            switch(r0) {
                case -871160130: goto L_0x001c;
                case 237934709: goto L_0x0012;
                case 353103893: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0026
        L_0x0008:
            java.lang.String r0 = "Distance"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 2
            goto L_0x0027
        L_0x0012:
            java.lang.String r0 = "SimpleSteps"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 0
            goto L_0x0027
        L_0x001c:
            java.lang.String r0 = "WalkSteps"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x0027
        L_0x0026:
            r0 = -1
        L_0x0027:
            switch(r0) {
                case 0: goto L_0x003f;
                case 1: goto L_0x0037;
                case 2: goto L_0x0030;
                default: goto L_0x002a;
            }
        L_0x002a:
            r0 = 0
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            return r0
        L_0x0030:
            float r0 = r1.totalDistance
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            return r0
        L_0x0037:
            int r0 = r1.numStepsWithFilter
            float r0 = (float) r0
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            return r0
        L_0x003f:
            int r0 = r1.numStepsRaw
            float r0 = (float) r0
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.Pedometer.getDataValue(java.lang.String):java.lang.Float");
    }
}
