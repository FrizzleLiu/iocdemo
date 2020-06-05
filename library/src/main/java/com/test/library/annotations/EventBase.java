package com.test.library.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * desc   : EventBase
 * 事件注入的注解
 */

@Target(ElementType.ANNOTATION_TYPE)//作用在其它注解之上
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    //1.监听方法名 setOnClickListener / setOnLongClickListener
    String listenerSetter();

    //2.监听对象 View.OnClickListener() / View.OnLongClickListener()
    Class<?> listenerType();

    //3. 返回值  onClick(View v) /  onLongClick(View v)
    String callBackListener();
}
