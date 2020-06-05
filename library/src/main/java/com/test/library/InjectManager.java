package com.test.library;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.test.library.annotations.ContentView;
import com.test.library.annotations.EventBase;
import com.test.library.annotations.InjectView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * desc   : InjectManager
 * 注解管理类
 */
public class InjectManager {

    /**
     * @param activity
     */
    public static void inject(Activity activity){
        //布局注入
        injectLayout(activity);
        //控件注入
        injectView(activity);
        //事件注入
        injectEvent(activity);
    }

    /**
     * @param activity
     * 布局注入
     */
    public static void injectLayout(Activity activity){
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取作用在类上的注解,这里取的是ContentView类型的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView!=null) {
            //获取ContentView注解的值,如Demo中Mainactivity取到的是 R.layout.activity_main
            int layoutId = contentView.value();
            try {
                //获取setContentView方法
                Method method = clazz.getMethod("setContentView", int.class);
                //执行setContentView方法
                method.invoke(activity,layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param activity
     * 控件的注入
     */
    private static void injectView(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取所有属性,包括private等
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            //获取属性上的InjectView类型的注解
            InjectView injectView = field.getAnnotation(InjectView.class);
            if (injectView != null) {
                //获取注解的值,就是View的id值
                int viewId = injectView.value();
                try {
                    //获取findViewById方法
                    Method method = clazz.getMethod("findViewById", int.class);
                    //执行findViewById方法
                    Object view = method.invoke(activity, viewId);
                    //打开私有访问权限,即private属性可以修改
                    field.setAccessible(true);
                    //将findViewById方法的返回值赋值给控件
                    field.set(activity,view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param activity
     * 事件注入
     */
    private static void injectEvent(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            //获取方法上的注解
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                //获取注解的类型
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null) {
                    //获取EventBase注解
                    EventBase eventBaseAnnotation = annotationType.getAnnotation(EventBase.class);
                    //获取三个信息
                    if (eventBaseAnnotation!=null) {
                        //listenerSetter = setOnClickListener
                        String listenerSetter = eventBaseAnnotation.listenerSetter();
                        //listenerType = View.OnClickListener
                        Class<?> listenerType = eventBaseAnnotation.listenerType();
                        //
                        String callBackListener = eventBaseAnnotation.callBackListener();

                        try {
                            //获取OnClick注解的value方法
                            Method valueMethod = annotationType.getDeclaredMethod("value");
                            //获取OnClick注解的的值
                            int[] viewIds = (int[])valueMethod.invoke(annotation);
                            //代理的方式,根据注解类型,匹配对应的回调方法
                            //即View.OnClickListener 回调 onClick(View view)
                            //View.OnLongClickListener() 回调 onLongClick(View v)
                            ListenerInvocationHandler listenerInvocationHandler = new ListenerInvocationHandler(activity);
                            //添加需要拦截的方法,和替换执行的方法
                            //callBackListener onClick
                            //method clickEvent
                            Log.e("callBackListener:",callBackListener+"");
                            Log.e("method:",method.getName()+"");
                            listenerInvocationHandler.addMethod(callBackListener,method);
                            Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, listenerInvocationHandler);

                            for (int viewId : viewIds) {
                                //获取findViewById方法
                                Method findViewMethod = clazz.getMethod("findViewById", int.class);
                                //执行findViewById方法
                                View view = (View) findViewMethod.invoke(activity, viewId);

                                Method setter = view.getClass().getMethod(listenerSetter, listenerType);
                                setter.invoke(view,listener);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
