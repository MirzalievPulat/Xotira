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


## Keep all members of SharedPreferenceHelper to prevent obfuscation
#-keep class uz.polat.xotira.domain.locale.SharedPreferenceHelper {*;}
#-keep class uz.polat.xotira.domain.locale.LocalStorage {*;}
#
## Keep all inner classes of SharedPreferenceHelper
#-keepclassmembers class uz.polat.xotira.domain.locale.SharedPreferenceHelper$* { *; }
#
#
## Keep all property names used in delegated properties
#-keepclassmembers class uz.polat.xotira.domain.locale.SharedPreferenceHelper {
#    *;
#}
#-keepclassmembers class uz.polat.xotira.domain.locale.LocalStorage {
#    *;
#}
#
## Keep all property names used in delegated properties
#-keepattributes RuntimeVisibleAnnotations, Signature




# SharedPreferenceHelper va uning ichki klasslari
#-keep class uz.polat.xotira.domain.locale.SharedPreferenceHelper { *; }
#-keep class uz.polat.xotira.domain.locale.SharedPreferenceHelper$* { *; }

## SharedPreferences bilan ishlash
#-keep class android.content.SharedPreferences { *; }
#-keep class android.content.SharedPreferences$* { *; }
#-keepclassmembers class android.content.SharedPreferences {
#    public *;
#    private *;
#}
#
## Subclass’lardagi property va metodlarni saqlash
#-keepclassmembers class uz.polat.xotira.domain.locale.LocalStorage {
#    *** *();
#}
#
## Kotlin delegate va reflection uchun
#-keep class kotlin.properties.ReadWriteProperty { *; }
#-keep class kotlin.reflect.KProperty { *; }
#
## Xatolik xabarlarini o‘chirish
#-dontwarn android.content.SharedPreferencesImpl
#-dontwarn kotlin.**
#-dontwarn uz.kholisbek.entity.local.**
