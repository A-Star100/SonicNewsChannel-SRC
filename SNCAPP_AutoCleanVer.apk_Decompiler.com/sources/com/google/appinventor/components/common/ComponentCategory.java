package com.google.appinventor.components.common;

import androidx.core.app.NotificationCompat;
import java.util.HashMap;
import java.util.Map;

public enum ComponentCategory {
    USERINTERFACE("User Interface"),
    LAYOUT("Layout"),
    MEDIA("Media"),
    ANIMATION("Drawing and Animation"),
    MAPS("Maps"),
    CHARTS("Charts"),
    DATASCIENCE("Data Science"),
    SENSORS("Sensors"),
    SOCIAL("Social"),
    STORAGE("Storage"),
    CONNECTIVITY("Connectivity"),
    LEGOMINDSTORMS("LEGO® MINDSTORMS®"),
    EXPERIMENTAL("Experimental"),
    EXTENSION("Extension"),
    FUTURE("Future"),
    INTERNAL("For internal use only"),
    UNINITIALIZED("Uninitialized");
    
    private static final Map<String, String> DOC_MAP = null;
    private final String name;

    static {
        HashMap hashMap = new HashMap();
        DOC_MAP = hashMap;
        hashMap.put("User Interface", "userinterface");
        hashMap.put("Layout", "layout");
        hashMap.put("Media", "media");
        hashMap.put("Drawing and Animation", "animation");
        hashMap.put("Maps", "maps");
        hashMap.put("Charts", "charts");
        hashMap.put("Data Science", "datascience");
        hashMap.put("Sensors", "sensors");
        hashMap.put("Social", NotificationCompat.CATEGORY_SOCIAL);
        hashMap.put("Storage", "storage");
        hashMap.put("Connectivity", "connectivity");
        hashMap.put("LEGO® MINDSTORMS®", "legomindstorms");
        hashMap.put("Experimental", "experimental");
        hashMap.put("Extension", "extension");
        hashMap.put("Future", "future");
    }

    private ComponentCategory(String categoryName) {
        this.name = categoryName;
    }

    public String getName() {
        return this.name;
    }

    public String getDocName() {
        return DOC_MAP.get(this.name);
    }
}
