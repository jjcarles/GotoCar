package com.jocajica.gotocar.domain.di;

import com.jocajica.gotocar.GotoCarAppModule;

import javax.inject.Singleton;

import dagger.Component;
import com.jocajica.gotocar.GotoCarAppModule;

/**
 * Created by jcarles on 12/07/2016.
 */
@Singleton
@Component(modules = {DomainModule.class, GotoCarAppModule.class})
public interface DomainComponent {
}
