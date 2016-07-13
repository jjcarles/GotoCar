package com.jocajica.gotocar.libs.di;

import com.jocajica.gotocar.GotoCarAppModule;
import com.jocajica.gotocar.domain.di.DomainModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jcarles on 13/07/2016.
 */
@Singleton
@Component(modules = {LibsModule.class, GotoCarAppModule.class})
public interface LibsComponent {
}
