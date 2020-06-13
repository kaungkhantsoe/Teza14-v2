package com.kks.teza14.di.pending_member;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by kaungkhantsoe on 01/06/2020.
 **/

@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PendingMemberScope {
}
