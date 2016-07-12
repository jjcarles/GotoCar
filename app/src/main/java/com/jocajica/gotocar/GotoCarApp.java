package com.jocajica.gotocar;

import android.app.Application;

import com.jocajica.gotocar.domain.di.DomainModule;

/**
 * Created by Joan Carles Jiménez on 10/07/2016.
 */
public class GotoCarApp extends Application {
    private final static String SHARED_PREFERENCES_NAME = "UserPrefs";

    private GotoCarAppModule gotoCarModule;
    private DomainModule domainModule;

    @Override
    public void onCreate() {
        super.onCreate();
        initModules();
    }

    private void initModules() {
        gotoCarModule = new GotoCarAppModule(this);
        domainModule = new DomainModule()
    }

    public String getSharedPreferencesName() {
        return SHARED_PREFERENCES_NAME;
    }
}
