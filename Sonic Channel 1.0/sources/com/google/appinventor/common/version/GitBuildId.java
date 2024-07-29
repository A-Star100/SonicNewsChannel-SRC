package com.google.appinventor.common.version;

public final class GitBuildId {
    public static final String ACRA_URI = "${acra.uri}";
    public static final String ANT_BUILD_DATE = "February 19 2024";
    public static final String GIT_BUILD_FINGERPRINT = "2965242f6c92e55bc7d049ec621aec875559c799";
    public static final String GIT_BUILD_VERSION = "nb196";

    private GitBuildId() {
    }

    public static String getVersion() {
        if (GIT_BUILD_VERSION == "" || GIT_BUILD_VERSION.contains(" ")) {
            return "none";
        }
        return GIT_BUILD_VERSION;
    }

    public static String getFingerprint() {
        return GIT_BUILD_FINGERPRINT;
    }

    public static String getDate() {
        return ANT_BUILD_DATE;
    }

    public static String getAcraUri() {
        if (ACRA_URI.equals(ACRA_URI)) {
            return "";
        }
        return ACRA_URI.trim();
    }
}
