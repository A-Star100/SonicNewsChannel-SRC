package com.google.appinventor.components.runtime;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import java.util.HashSet;
import java.util.Set;

@SimpleObject
@DesignerComponent(category = ComponentCategory.SENSORS, description = "<p>Non-visible component that can measure angular velocity in three dimensions in units of degrees per second.</p><p>In order to function, the component must have its <code>Enabled</code> property set to True, and the device must have a gyroscope sensor.</p>", iconName = "images/gyroscopesensor.png", nonVisible = true, version = 1)
public class GyroscopeSensor extends AndroidNonvisibleComponent implements SensorEventListener, Deleteable, OnPauseListener, OnResumeListener, RealTimeDataSource<String, Float> {
    private Set<DataSourceChangeListener> dataSourceObservers = new HashSet();
    private boolean enabled;
    private final Sensor gyroSensor;
    private boolean listening;
    private final SensorManager sensorManager;
    private float xAngularVelocity;
    private float yAngularVelocity;
    private float zAngularVelocity;

    public GyroscopeSensor(ComponentContainer container) {
        super(container.$form());
        SensorManager sensorManager2 = (SensorManager) this.form.getSystemService("sensor");
        this.sensorManager = sensorManager2;
        this.gyroSensor = sensorManager2.getDefaultSensor(4);
        this.form.registerForOnResume(this);
        this.form.registerForOnPause(this);
        Enabled(true);
    }

    private void startListening() {
        if (!this.listening) {
            this.sensorManager.registerListener(this, this.gyroSensor, 0);
            this.listening = true;
        }
    }

    private void stopListening() {
        if (this.listening) {
            this.sensorManager.unregisterListener(this);
            this.listening = false;
            this.xAngularVelocity = 0.0f;
            this.yAngularVelocity = 0.0f;
            this.zAngularVelocity = 0.0f;
        }
    }

    @SimpleEvent(description = "Indicates that the gyroscope sensor data has changed. The timestamp parameter is the time in nanoseconds at which the event occurred.")
    public void GyroscopeChanged(float xAngularVelocity2, float yAngularVelocity2, float zAngularVelocity2, long timestamp) {
        EventDispatcher.dispatchEvent(this, "GyroscopeChanged", Float.valueOf(xAngularVelocity2), Float.valueOf(yAngularVelocity2), Float.valueOf(zAngularVelocity2), Long.valueOf(timestamp));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Indicates whether a gyroscope sensor is available.")
    public boolean Available() {
        return this.sensorManager.getSensorList(4).size() > 0;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean Enabled() {
        return this.enabled;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty(description = "If enabled, then sensor events will be generated and XAngularVelocity, YAngularVelocity, and ZAngularVelocity properties will have meaningful values.")
    public void Enabled(boolean enabled2) {
        if (this.enabled != enabled2) {
            this.enabled = enabled2;
            if (enabled2) {
                startListening();
            } else {
                stopListening();
            }
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The angular velocity around the X axis, in degrees per second.")
    public float XAngularVelocity() {
        return this.xAngularVelocity;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The angular velocity around the Y axis, in degrees per second.")
    public float YAngularVelocity() {
        return this.yAngularVelocity;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The angular velocity around the Z axis, in degrees per second.")
    public float ZAngularVelocity() {
        return this.zAngularVelocity;
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (this.enabled) {
            this.xAngularVelocity = (float) Math.toDegrees((double) sensorEvent.values[0]);
            this.yAngularVelocity = (float) Math.toDegrees((double) sensorEvent.values[1]);
            this.zAngularVelocity = (float) Math.toDegrees((double) sensorEvent.values[2]);
            notifyDataObservers("X", (Object) Float.valueOf(this.xAngularVelocity));
            notifyDataObservers("Y", (Object) Float.valueOf(this.yAngularVelocity));
            notifyDataObservers("Z", (Object) Float.valueOf(this.zAngularVelocity));
            GyroscopeChanged(this.xAngularVelocity, this.yAngularVelocity, this.zAngularVelocity, sensorEvent.timestamp);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onDelete() {
        stopListening();
    }

    public void onPause() {
        stopListening();
    }

    public void onResume() {
        if (this.enabled) {
            startListening();
        }
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
                case 88: goto L_0x001c;
                case 89: goto L_0x0012;
                case 90: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0026
        L_0x0008:
            java.lang.String r0 = "Z"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 2
            goto L_0x0027
        L_0x0012:
            java.lang.String r0 = "Y"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x0027
        L_0x001c:
            java.lang.String r0 = "X"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 0
            goto L_0x0027
        L_0x0026:
            r0 = -1
        L_0x0027:
            switch(r0) {
                case 0: goto L_0x003e;
                case 1: goto L_0x0037;
                case 2: goto L_0x0030;
                default: goto L_0x002a;
            }
        L_0x002a:
            r0 = 0
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            return r0
        L_0x0030:
            float r0 = r1.zAngularVelocity
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            return r0
        L_0x0037:
            float r0 = r1.yAngularVelocity
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            return r0
        L_0x003e:
            float r0 = r1.xAngularVelocity
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.GyroscopeSensor.getDataValue(java.lang.String):java.lang.Float");
    }
}
