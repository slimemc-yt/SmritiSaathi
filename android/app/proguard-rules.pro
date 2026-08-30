# Project specific ProGuard rules
-keep class com.google.firebase.** { *; }
-keep class com.socklet.smritisaathi.data.** { *; }
-keep class com.socklet.smritisaathi.domain.model.** { *; }
-keepclassmembers class com.socklet.smritisaathi.domain.model.** {
    <fields>;
    <methods>;
}
-dontwarn org.conscrypt.**
-keepattributes *Annotation*,InnerClasses,EnclosingMethod,Signature
-keep class dagger.hilt.** { *; }

