package kz.ukteplo.uktsrepairs.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import kz.ukteplo.uktsrepairs.BuildConfig;
import kz.ukteplo.uktsrepairs.data.models.User;

public class Configuration {
    private User user = new User();
    private static final String userAgent = "UKTSService/"
            + BuildConfig.VERSION_NAME
            + " (" + Build.HOST
            + "; Android " + Build.VERSION.RELEASE
            + "; " + Build.DEVICE + ")";

    private static final String baseURL = "https://ukteplo.kz/api/1.1/";

    private final String versionName = BuildConfig.VERSION_NAME;
    private String token;
    private static final String apikey = "fb5be5b5-85fe-4dec-90c8-ab56cb4982e0";
    private String role;

    private String deviceid;

    public Configuration(Context context) {
        deviceid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (deviceid.isEmpty()) {
            deviceid = "35" +
                    Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                    Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                    Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                    Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                    Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                    Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                    Build.USER.length() % 10;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public static String getApikey() {
        return apikey;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
