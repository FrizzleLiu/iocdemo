package com.test.library.annotations;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * desc   : OnClick
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSetter="setOnClickListener",listenerType= View.OnClickListener.class,callBackListener="onClick")
public @interface OnClick {
    int[] value();
}
