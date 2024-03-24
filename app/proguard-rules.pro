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
#-renamesourcefileattribute SourceFile

-keep class com.titi.app.data.alarm.impl.local.model.** {*;}
-keep class com.titi.app.data.alarm.api.model.** {*;}
-keep class com.titi.app.domain.alarm.model.** {*;}

-keep class com.titi.app.data.color.impl.local.model.** {*;}
-keep class com.titi.app.data.color.api.model.** {*;}
-keep class com.titi.app.domain.color.model.** {*;}

-keep class com.titi.app.data.daily.impl.local.model.** {*;}
-keep class com.titi.app.data.daily.api.model.** {*;}
-keep class com.titi.app.doamin.daily.model.** {*;}

-keep class com.titi.app.data.graph.impl.local.model.** {*;}
-keep class com.titi.app.data.graph.api.model.** {*;}

-keep class com.titi.app.data.sleep.impl.local.** {*;}

-keep class com.titi.app.data.task.impl.local.model.** {*;}
-keep class com.titi.app.data.task.api.model.** {*;}
-keep class com.titi.app.domain.task.model.** {*;}

-keep class com.titi.app.data.time.impl.local.model.** {*;}
-keep class com.titi.app.data.time.api.model.** {*;}
-keep class com.titi.app.domain.time.model.** {*;}

-keep class com.titi.app.feature.color.model.** {*;}
-keep class com.titi.app.feature.log.model.** {*;}
-keep class com.titi.app.feature.main.model.** {*;}
-keep class com.titi.app.feature.measure.model.** {*;}
-keep class com.titi.app.feature.time.model.** {*;}

-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}

-keep @com.squareup.moshi.JsonQualifier @interface *

-keepclassmembers @com.squareup.moshi.JsonClass class * extends java.lang.Enum {
    <fields>;
    **[] values();
}

-keepclassmembers class com.squareup.moshi.internal.Util {
    private static java.lang.String getKotlinMetadataClassName();
}

-keepclassmembers class * {
  @com.squareup.moshi.FromJson <methods>;
  @com.squareup.moshi.ToJson <methods>;
}