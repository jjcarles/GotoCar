package com.jocajica.gotocar.domain.di;

import android.content.Context;
import android.location.Geocoder;

import com.jocajica.gotocar.domain.Util;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jcarles on 12/07/2016.
 */
@Module
public class DomainModule {
    @Provides
    @Singleton
    Util providesUtil(Geocoder geocoder) {
        return new Util(geocoder);
    }

    @Provides
    @Singleton
    Geocoder providesGeocoder(Context context) {
        return new Geocoder(context);
    }
}
