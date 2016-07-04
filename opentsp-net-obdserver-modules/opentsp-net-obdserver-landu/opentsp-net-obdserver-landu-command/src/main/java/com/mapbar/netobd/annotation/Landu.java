package com.mapbar.netobd.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.annotation.Secured;

import com.navinfo.opentsp.common.messaging.routing.annotation.MessageGroup;

/**
 * 监控对象注解
 * 
 * @author xubh
 *
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Secured("ROLE_NETOBD_SERVER")
@MessageGroup("net_obd_landu")
public @interface Landu {
}