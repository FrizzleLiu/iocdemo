package com.test.library.annotations;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * desc   : OnLongClick
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSetter="setOnLongClickListener",listenerType= View.OnLongClickListener.class,callBackListener="onLongClick")
public @interface OnLongClick {
    int[] value();
}
