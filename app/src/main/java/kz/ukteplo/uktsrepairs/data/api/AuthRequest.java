package kz.ukteplo.uktsrepairs.data.api;

import android.os.Build;

public class AuthRequest {
    private String username;
    private String password;
    private String lang;
    private String token;
    private String deviceid;
    private String version;
    private final String apikey;
    private final String model = Build.BRAND + " " + Build.MODEL;

    public AuthRequest(String apikey, String login, String pass, String token, String deviceid, String version) {
        this.apikey = apikey;
        this.username = login;
        this.password = pass;
        this.lang = "ru";
        this.deviceid = deviceid;
        this.version = version;
        this.token = token;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApikey() {
        return apikey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceId() {
        return deviceid;
    }

    public void setDeviceId(String deviceId) {
        this.deviceid = deviceId;
    }

    public String getModel() {
        return model;
    }
}
