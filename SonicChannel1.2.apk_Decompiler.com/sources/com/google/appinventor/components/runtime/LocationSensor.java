package com.google.appinventor.components.runtime;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.util.BulkPermissionRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DesignerComponent(category = ComponentCategory.SENSORS, description = "Non-visible component providing location information, including longitude, latitude, altitude (if supported by the device), speed (if supported by the device), and address.  This can also perform \"geocoding\", converting a given address (not necessarily the current one) to a latitude (with the <code>LatitudeFromAddress</code> method) and a longitude (with the <code>LongitudeFromAddress</code> method).</p>\n<p>In order to function, the component must have its <code>Enabled</code> property set to True, and the device must have location sensing enabled through wireless networks or GPS satellites (if outdoors).</p>\nLocation information might not be immediately available when an app starts.  You'll have to wait a short time for a location provider to be found and used, or wait for the LocationChanged event", iconName = "images/locationSensor.png", nonVisible = true, version = 3)
@SimpleObject
@UsesPermissions(permissionNames = "android.permission.ACCESS_FINE_LOCATION,android.permission.ACCESS_COARSE_LOCATION,android.permission.ACCESS_MOCK_LOCATION,android.permission.ACCESS_LOCATION_EXTRA_COMMANDS")
public class LocationSensor extends AndroidNonvisibleComponent implements Component, OnStopListener, OnResumeListener, Deleteable, RealTimeDataSource<String, Float> {
    /* access modifiers changed from: private */
    public static final String LOG_TAG = LocationSensor.class.getSimpleName();
    public static final int UNKNOWN_VALUE = 0;
    /* access modifiers changed from: private */
    public List<String> allProviders;
    /* access modifiers changed from: private */
    public double altitude;
    /* access modifiers changed from: private */
    public final Handler androidUIHandler;
    private Set<DataSourceChangeListener> dataSourceObservers;
    private int distanceInterval;
    /* access modifiers changed from: private */
    public boolean enabled;
    private Geocoder geocoder;
    private final Handler handler;
    /* access modifiers changed from: private */
    public boolean hasAltitude;
    /* access modifiers changed from: private */
    public boolean hasLocationData;
    /* access modifiers changed from: private */
    public boolean havePermission;
    private boolean initialized;
    /* access modifiers changed from: private */
    public Location lastLocation;
    /* access modifiers changed from: private */
    public double latitude;
    /* access modifiers changed from: private */
    public final Set<LocationSensorListener> listeners;
    private boolean listening;
    private final Criteria locationCriteria;
    private final LocationManager locationManager;
    private LocationProvider locationProvider;
    /* access modifiers changed from: private */
    public double longitude;
    private MyLocationListener myLocationListener;
    private boolean providerLocked;
    /* access modifiers changed from: private */
    public String providerName;
    /* access modifiers changed from: private */
    public float speed;
    private int timeInterval;

    public interface LocationSensorListener extends LocationListener {
        void onDistanceIntervalChanged(int i);

        void onTimeIntervalChanged(int i);

        void setSource(LocationSensor locationSensor);
    }

    private class MyLocationListener implements LocationListener {
        private MyLocationListener() {
        }

        public void onLocationChanged(Location location) {
            LocationSensor.this.lastLocation = location;
            LocationSensor.this.longitude = location.getLongitude();
            LocationSensor.this.latitude = location.getLatitude();
            LocationSensor.this.speed = location.getSpeed();
            if (location.hasAltitude()) {
                LocationSensor.this.hasAltitude = true;
                LocationSensor.this.altitude = location.getAltitude();
            }
            if (LocationSensor.this.longitude != 0.0d || LocationSensor.this.latitude != 0.0d) {
                LocationSensor.this.hasLocationData = true;
                double argLatitude = LocationSensor.this.latitude;
                double argLongitude = LocationSensor.this.longitude;
                double argAltitude = LocationSensor.this.altitude;
                float argSpeed = LocationSensor.this.speed;
                final double d = argLatitude;
                final double d2 = argLongitude;
                final double d3 = argAltitude;
                AnonymousClass1 r10 = r0;
                final float f = argSpeed;
                Handler r11 = LocationSensor.this.androidUIHandler;
                final Location location2 = location;
                AnonymousClass1 r0 = new Runnable() {
                    public void run() {
                        LocationSensor.this.LocationChanged(d, d2, d3, f);
                        for (LocationSensorListener listener : LocationSensor.this.listeners) {
                            listener.onLocationChanged(location2);
                        }
                    }
                };
                r11.post(r10);
            }
        }

        public void onProviderDisabled(String provider) {
            LocationSensor.this.StatusChanged(provider, "Disabled");
            LocationSensor.this.stopListening();
            if (LocationSensor.this.enabled) {
                LocationSensor.this.RefreshProvider("onProviderDisabled");
            }
        }

        public void onProviderEnabled(String provider) {
            LocationSensor.this.StatusChanged(provider, "Enabled");
            LocationSensor.this.RefreshProvider("onProviderEnabled");
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case 0:
                    LocationSensor.this.StatusChanged(provider, "OUT_OF_SERVICE");
                    if (provider.equals(LocationSensor.this.providerName)) {
                        LocationSensor.this.stopListening();
                        LocationSensor.this.RefreshProvider("onStatusChanged");
                        return;
                    }
                    return;
                case 1:
                    LocationSensor.this.StatusChanged(provider, "TEMPORARILY_UNAVAILABLE");
                    return;
                case 2:
                    LocationSensor.this.StatusChanged(provider, "AVAILABLE");
                    if (!provider.equals(LocationSensor.this.providerName) && !LocationSensor.this.allProviders.contains(provider)) {
                        LocationSensor.this.RefreshProvider("onStatusChanged");
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public LocationSensor(ComponentContainer container) {
        this(container, true);
    }

    public LocationSensor(ComponentContainer container, boolean enabled2) {
        super(container.$form());
        this.dataSourceObservers = new HashSet();
        this.listeners = new HashSet();
        this.providerLocked = false;
        this.initialized = false;
        this.listening = false;
        this.longitude = 0.0d;
        this.latitude = 0.0d;
        this.altitude = 0.0d;
        this.speed = 0.0f;
        this.hasLocationData = false;
        this.hasAltitude = false;
        this.androidUIHandler = new Handler();
        this.enabled = true;
        this.havePermission = false;
        this.enabled = enabled2;
        this.handler = new Handler();
        this.form.registerForOnResume(this);
        this.form.registerForOnStop(this);
        this.timeInterval = 60000;
        this.distanceInterval = 5;
        Context context = container.$context();
        this.geocoder = new Geocoder(context);
        this.locationManager = (LocationManager) context.getSystemService("location");
        this.locationCriteria = new Criteria();
        this.myLocationListener = new MyLocationListener();
        this.allProviders = new ArrayList();
        Enabled(enabled2);
    }

    public void Initialize() {
        this.initialized = true;
        Enabled(this.enabled);
    }

    @SimpleEvent(description = "Indicates that a new location has been detected.")
    public void LocationChanged(double latitude2, double longitude2, double altitude2, float speed2) {
        notifyDataObservers(PropertyTypeConstants.PROPERTY_TYPE_LATITUDE, (Object) Double.valueOf(latitude2));
        notifyDataObservers(PropertyTypeConstants.PROPERTY_TYPE_LONGITUDE, (Object) Double.valueOf(longitude2));
        notifyDataObservers("altitude", (Object) Double.valueOf(altitude2));
        notifyDataObservers("speed", (Object) Float.valueOf(speed2));
        EventDispatcher.dispatchEvent(this, "LocationChanged", Double.valueOf(latitude2), Double.valueOf(longitude2), Double.valueOf(altitude2), Float.valueOf(speed2));
    }

    @SimpleEvent
    public void StatusChanged(String provider, String status) {
        if (this.enabled) {
            EventDispatcher.dispatchEvent(this, "StatusChanged", provider, status);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ProviderName() {
        String str = this.providerName;
        if (str == null) {
            return "NO PROVIDER";
        }
        return str;
    }

    @SimpleProperty
    public void ProviderName(String providerName2) {
        this.providerName = providerName2;
        if (empty(providerName2) || !startProvider(providerName2)) {
            RefreshProvider("ProviderName");
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean ProviderLocked() {
        return this.providerLocked;
    }

    @SimpleProperty
    public void ProviderLocked(boolean lock) {
        this.providerLocked = lock;
    }

    @DesignerProperty(defaultValue = "60000", editorType = "sensor_time_interval")
    @SimpleProperty
    public void TimeInterval(int interval) {
        if (interval >= 0 && interval <= 1000000) {
            this.timeInterval = interval;
            if (this.enabled) {
                RefreshProvider("TimeInterval");
            }
            for (LocationSensorListener listener : this.listeners) {
                listener.onTimeIntervalChanged(this.timeInterval);
            }
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Determines the minimum time interval, in milliseconds, that the sensor will try to use for sending out location updates. However, location updates will only be received when the location of the phone actually changes, and use of the specified time interval is not guaranteed. For example, if 1000 is used as the time interval, location updates will never be fired sooner than 1000ms, but they may be fired anytime after.")
    public int TimeInterval() {
        return this.timeInterval;
    }

    @DesignerProperty(defaultValue = "5", editorType = "sensor_dist_interval")
    @SimpleProperty
    public void DistanceInterval(int interval) {
        if (interval >= 0 && interval <= 1000) {
            this.distanceInterval = interval;
            if (this.enabled) {
                RefreshProvider("DistanceInterval");
            }
            for (LocationSensorListener listener : this.listeners) {
                listener.onDistanceIntervalChanged(this.distanceInterval);
            }
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Determines the minimum distance interval, in meters, that the sensor will try to use for sending out location updates. For example, if this is set to 5, then the sensor will fire a LocationChanged event only after 5 meters have been traversed. However, the sensor does not guarantee that an update will be received at exactly the distance interval. It may take more than 5 meters to fire an event, for instance.")
    public int DistanceInterval() {
        return this.distanceInterval;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean HasLongitudeLatitude() {
        return this.hasLocationData && this.enabled;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean HasAltitude() {
        return this.hasAltitude && this.enabled;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean HasAccuracy() {
        return Accuracy() != 0.0d && this.enabled;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public double Longitude() {
        return this.longitude;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public double Latitude() {
        return this.latitude;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The most recently available altitude value, in meters.  If no value is available, 0 will be returned.")
    public double Altitude() {
        return this.altitude;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The most recent measure of accuracy, in meters.  If no value is available, 0 will be returned.")
    public double Accuracy() {
        Location location = this.lastLocation;
        if (location != null && location.hasAccuracy()) {
            return (double) this.lastLocation.getAccuracy();
        }
        LocationProvider locationProvider2 = this.locationProvider;
        if (locationProvider2 != null) {
            return (double) locationProvider2.getAccuracy();
        }
        return 0.0d;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean Enabled() {
        return this.enabled;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty
    public void Enabled(boolean enabled2) {
        this.enabled = enabled2;
        if (this.initialized) {
            if (!enabled2) {
                stopListening();
            } else {
                RefreshProvider("Enabled");
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0021, code lost:
        if (r11.longitude <= 180.0d) goto L_0x002e;
     */
    @com.google.appinventor.components.annotations.SimpleProperty(category = com.google.appinventor.components.annotations.PropertyCategory.BEHAVIOR, description = "Provides a textual representation of the current address or \"No address available\".")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String CurrentAddress() {
        /*
            r11 = this;
            boolean r0 = r11.hasLocationData
            if (r0 == 0) goto L_0x0023
            double r0 = r11.latitude
            r2 = 4636033603912859648(0x4056800000000000, double:90.0)
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 > 0) goto L_0x0023
            r2 = -4587338432941916160(0xc056800000000000, double:-90.0)
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 < 0) goto L_0x0023
            double r0 = r11.longitude
            r2 = 4640537203540230144(0x4066800000000000, double:180.0)
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x002e
        L_0x0023:
            double r0 = r11.longitude
            r2 = -4582834833314545664(0xc066800000000000, double:-180.0)
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 < 0) goto L_0x00b3
        L_0x002e:
            android.location.Geocoder r5 = r11.geocoder     // Catch:{ Exception -> 0x006c }
            double r6 = r11.latitude     // Catch:{ Exception -> 0x006c }
            double r8 = r11.longitude     // Catch:{ Exception -> 0x006c }
            r10 = 1
            java.util.List r0 = r5.getFromLocation(r6, r8, r10)     // Catch:{ Exception -> 0x006c }
            if (r0 == 0) goto L_0x006b
            int r1 = r0.size()     // Catch:{ Exception -> 0x006c }
            r2 = 1
            if (r1 != r2) goto L_0x006b
            r1 = 0
            java.lang.Object r1 = r0.get(r1)     // Catch:{ Exception -> 0x006c }
            android.location.Address r1 = (android.location.Address) r1     // Catch:{ Exception -> 0x006c }
            if (r1 == 0) goto L_0x006b
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x006c }
            r2.<init>()     // Catch:{ Exception -> 0x006c }
            r3 = 0
        L_0x0051:
            int r4 = r1.getMaxAddressLineIndex()     // Catch:{ Exception -> 0x006c }
            if (r3 > r4) goto L_0x0066
            java.lang.String r4 = r1.getAddressLine(r3)     // Catch:{ Exception -> 0x006c }
            r2.append(r4)     // Catch:{ Exception -> 0x006c }
            java.lang.String r4 = "\n"
            r2.append(r4)     // Catch:{ Exception -> 0x006c }
            int r3 = r3 + 1
            goto L_0x0051
        L_0x0066:
            java.lang.String r3 = r2.toString()     // Catch:{ Exception -> 0x006c }
            return r3
        L_0x006b:
            goto L_0x00b3
        L_0x006c:
            r0 = move-exception
            boolean r1 = r0 instanceof java.lang.IllegalArgumentException
            if (r1 != 0) goto L_0x0097
            boolean r1 = r0 instanceof java.io.IOException
            if (r1 != 0) goto L_0x0097
            boolean r1 = r0 instanceof java.lang.IndexOutOfBoundsException
            if (r1 == 0) goto L_0x007a
            goto L_0x0097
        L_0x007a:
            java.lang.String r1 = LOG_TAG
            java.lang.String r2 = r0.getMessage()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Unexpected exception thrown by getting current address "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.String r2 = r2.toString()
            android.util.Log.e(r1, r2)
            goto L_0x00b3
        L_0x0097:
            java.lang.String r1 = LOG_TAG
            java.lang.String r2 = r0.getMessage()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Exception thrown by getting current address "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.String r2 = r2.toString()
            android.util.Log.e(r1, r2)
        L_0x00b3:
            java.lang.String r0 = "No address available"
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.LocationSensor.CurrentAddress():java.lang.String");
    }

    @SimpleFunction(description = "Derives latitude of given address")
    public double LatitudeFromAddress(String locationName) {
        try {
            List<Address> addressObjs = this.geocoder.getFromLocationName(locationName, 1);
            Log.i(LOG_TAG, "latitude addressObjs size is " + addressObjs.size() + " for " + locationName);
            if (addressObjs != null && addressObjs.size() != 0) {
                return addressObjs.get(0).getLatitude();
            }
            throw new IOException("");
        } catch (IOException e) {
            this.form.dispatchErrorOccurredEvent(this, "LatitudeFromAddress", 101, locationName);
            return 0.0d;
        }
    }

    @SimpleFunction(description = "Derives longitude of given address")
    public double LongitudeFromAddress(String locationName) {
        try {
            List<Address> addressObjs = this.geocoder.getFromLocationName(locationName, 1);
            Log.i(LOG_TAG, "longitude addressObjs size is " + addressObjs.size() + " for " + locationName);
            if (addressObjs != null && addressObjs.size() != 0) {
                return addressObjs.get(0).getLongitude();
            }
            throw new IOException("");
        } catch (IOException e) {
            this.form.dispatchErrorOccurredEvent(this, "LongitudeFromAddress", 102, locationName);
            return 0.0d;
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public List<String> AvailableProviders() {
        return this.allProviders;
    }

    public void RefreshProvider(final String caller) {
        if (this.initialized) {
            stopListening();
            if (!this.havePermission) {
                this.androidUIHandler.post(new Runnable() {
                    public void run() {
                        this.form.askPermission(new BulkPermissionRequest(this, "RefreshProvider", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION") {
                            public void onGranted() {
                                this.havePermission = true;
                                this.RefreshProvider(caller);
                                Log.d(LocationSensor.LOG_TAG, "Permission Granted");
                            }
                        });
                    }
                });
            }
            if (!this.providerLocked || empty(this.providerName)) {
                this.allProviders = this.locationManager.getProviders(true);
                String bProviderName = this.locationManager.getBestProvider(this.locationCriteria, true);
                if (bProviderName != null && !bProviderName.equals(this.allProviders.get(0))) {
                    this.allProviders.add(0, bProviderName);
                }
                for (String providerN : this.allProviders) {
                    boolean startProvider = startProvider(providerN);
                    this.listening = startProvider;
                    if (startProvider) {
                        if (!this.providerLocked) {
                            this.providerName = providerN;
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            this.listening = startProvider(this.providerName);
        }
    }

    private boolean startProvider(String providerName2) {
        this.providerName = providerName2;
        LocationProvider tLocationProvider = this.locationManager.getProvider(providerName2);
        if (tLocationProvider == null) {
            Log.d(LOG_TAG, "getProvider(" + providerName2 + ") returned null");
            return false;
        }
        stopListening();
        this.locationProvider = tLocationProvider;
        this.locationManager.requestLocationUpdates(providerName2, (long) this.timeInterval, (float) this.distanceInterval, this.myLocationListener);
        this.listening = true;
        return true;
    }

    /* access modifiers changed from: private */
    public void stopListening() {
        if (this.listening) {
            this.locationManager.removeUpdates(this.myLocationListener);
            this.locationProvider = null;
            this.listening = false;
        }
    }

    public void onResume() {
        if (this.enabled) {
            RefreshProvider("onResume");
        }
    }

    public void onStop() {
        stopListening();
    }

    public void onDelete() {
        stopListening();
    }

    public void addListener(LocationSensorListener listener) {
        listener.setSource(this);
        this.listeners.add(listener);
    }

    public void removeListener(LocationSensorListener listener) {
        this.listeners.remove(listener);
        listener.setSource((LocationSensor) null);
    }

    private boolean empty(String s) {
        return s == null || s.length() == 0;
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
    public java.lang.Float getDataValue(java.lang.String r3) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            switch(r0) {
                case -1439978388: goto L_0x0026;
                case 109641799: goto L_0x001c;
                case 137365935: goto L_0x0012;
                case 2036550306: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0030
        L_0x0008:
            java.lang.String r0 = "altitude"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 2
            goto L_0x0031
        L_0x0012:
            java.lang.String r0 = "longitude"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x0031
        L_0x001c:
            java.lang.String r0 = "speed"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 3
            goto L_0x0031
        L_0x0026:
            java.lang.String r0 = "latitude"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 0
            goto L_0x0031
        L_0x0030:
            r0 = -1
        L_0x0031:
            switch(r0) {
                case 0: goto L_0x0051;
                case 1: goto L_0x0049;
                case 2: goto L_0x0041;
                case 3: goto L_0x003a;
                default: goto L_0x0034;
            }
        L_0x0034:
            r0 = 0
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            return r0
        L_0x003a:
            float r0 = r2.speed
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            return r0
        L_0x0041:
            double r0 = r2.altitude
            float r0 = (float) r0
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            return r0
        L_0x0049:
            double r0 = r2.longitude
            float r0 = (float) r0
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            return r0
        L_0x0051:
            double r0 = r2.latitude
            float r0 = (float) r0
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.LocationSensor.getDataValue(java.lang.String):java.lang.Float");
    }
}
