package kz.ukteplo.uktsrepairs.utils;

import android.app.Application;

public class UKTSApplication extends Application {
    private static UKTSApplication instance;
    private ConfigManager configManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        configManager = new ConfigManager(this);
    }

    public UKTSApplication getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
