package com.kks.teza14.di.login;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by kaungkhantsoe on 22/05/2020.
 **/

@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginScope {
}
