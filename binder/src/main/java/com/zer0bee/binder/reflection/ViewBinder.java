package com.zer0bee.binder.reflection;

import android.app.Activity;
import android.view.View;
import com.zer0bee.annotations.BindView;
import java.lang.reflect.Field;

public class ViewBinder {

  public static void bind(final Activity target) {
    bindViews(target, target.getClass().getDeclaredFields(),
        target.getWindow().getDecorView().getRootView());
  }

  private static void bindViews(Activity target, Field[] declaredFields, View rootView) {
    for (Field field : declaredFields) {
      BindView annotation = field.getAnnotation(BindView.class);
      if (annotation != null) {
        int resId = annotation.value();
        View view = rootView.findViewById(resId);
        try {
          field.setAccessible(true);
          field.set(target, view);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
