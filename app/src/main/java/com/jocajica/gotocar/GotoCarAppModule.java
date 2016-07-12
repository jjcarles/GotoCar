package com.jocajica.gotocar;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jcarles on 12/07/2016.
 */
@Module
public class GotoCarAppModule {
    private GotoCarApp app;

    public GotoCarAppModule(GotoCarApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return this.app;
    }

    @Provides
    @Singleton
    Context providesApplicationContext() {
        return app.getApplicationContext();
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return application.getSharedPreferences(app.getSharedPreferencesName(), Context.MODE_PRIVATE);
    }
}
