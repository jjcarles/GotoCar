package com.jocajica.gotocar.libs.di;

import android.support.v4.app.Fragment;
import com.jocajica.gotocar.libs.GreenRobotEventBus;
import com.jocajica.gotocar.libs.base.EventBus;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ykro.
 */
@Module
public class LibsModule {
    private Fragment fragment;

    public LibsModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @Singleton
    Fragment providesFragment() {
        return this.fragment;
    }

    @Provides
    @Singleton
    EventBus providesEventBus() {
        return new GreenRobotEventBus();
    }

}
