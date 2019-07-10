package com.zer0bee.compiler;

import com.zer0bee.annotations.internal.BindingSuffix;

public class NameStore {
  private NameStore() {
  }

  public static String getGeneratedClassName(String clsName) {
    return clsName + BindingSuffix.GENERATED_CLASS_SUFFIX;
  }

  public static class Method {
    // Binder
    public static final String BIND_VIEWS = "bindViews";
    public static final String BIND = "bind";
  }

  public static class Variable {
    public static final String ANDROID_ACTIVITY = "activity";
    public static final String ANDROID_VIEW = "view";
  }
}
