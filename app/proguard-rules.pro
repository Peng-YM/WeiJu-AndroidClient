# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-ignorewarnings
-keep class * {
    public private *;
}

-renamesourcefileattribute SourceFile

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-dontwarn com.yanzhenjie.album.**
-keep class com.yanzhenjie.album.**{*;}

-dontwarn com.yanzhenjie.fragment.**
-keep class com.yanzhenjie.fragment.**{*;}

-dontwarn com.yanzhenjie.mediascanner.**
-keep class com.yanzhenjie.mediascanner.**{*;}

-dontwarn com.yanzhenjie.loading.**
-keep class com.yanzhenjie.loading.**{*;}

-dontwarn com.yanzhenjie.statusview.**
-keep class com.yanzhenjie.statusview.**{*;}

-dontwarn okio.**
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** {*;}
-keep interface com.squareup.okhttp3.* { *; }
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

# remove Logcat output in release
-assumenosideeffects class com.orhanobut.logger.Logger {
    *;
}