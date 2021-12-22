package kz.ukteplo.uktsrepairs.utils;

import android.content.Context;
import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;

import com.google.gson.Gson;

import kz.ukteplo.uktsrepairs.data.models.User;

public class ConfigManager {
    private final static String CONFIG_FILE_NAME = "UKTSService";
    private Configuration config;
    private SharedPreferences settings;
    private boolean isRegistred = false;
    private boolean isLogined = false;

    public ConfigManager(Context context) {
        config = new Configuration(context);
        settings = context.getSharedPreferences(CONFIG_FILE_NAME, MODE_PRIVATE);

        restoreUser();
        restoreRole();
    }

    public void restoreUser() {
        String userJson = settings.getString(User.class.getSimpleName(), "");
        if (!userJson.isEmpty()) {
            User user = new Gson().fromJson(userJson, User.class);
            config.setUser(user);
            isRegistred = true;
        }
    }

    public void restoreRole() {
        String role = settings.getString("role", "");
        if (!role.isEmpty()) {
            config.setRole(role);
        }
    }

    public void saveRole() {
        settings.edit().putString("role", config.getRole()).apply();
    }

    public void saveUser() {
        settings.edit().putString(User.class.getSimpleName(), new Gson().toJson(config.getUser())).apply();
    }

    public void setRole(String role) {
        config.setRole(role);
    }

    public String getRole() {
        return config.getRole();
    }

    public String getUserAgent() {
        return config.getUserAgent();
    }

    public User getUser() {
        return config.getUser();
    }

    public void setToken(String token) {
        config.setToken(token);
    }

    public String getToken() {
        return config.getToken();
    }

    public String getVersion() {
        return config.getVersionName();
    }

    public String getApikey() {
        return config.getApikey();
    }

    public String getDeviceid() {
        return config.getDeviceid();
    }

    public void setUser(User user) {
        config.setUser(user);
    }

    public String getBaseURL() {
        return config.getBaseURL();
    }

    public boolean isRegistred() {
        return isRegistred;
    }

    public boolean isLogined() {
        return isLogined;
    }

    public void setLogined(boolean logined) {
        isLogined = logined;
    }

    public void setRegistred(boolean registred) {
        isRegistred = registred;
    }
}
