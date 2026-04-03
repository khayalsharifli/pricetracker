# ──────────────────────────────────────────────
# Data module consumer rules
# Automatically applied to any app that depends on :data
# ──────────────────────────────────────────────

# ── Kotlinx Serialization ──
# Keep @Serializable classes and their generated serializers
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

-keepclassmembers @kotlinx.serialization.Serializable class az.khayalsharifli.data.** {
    *** Companion;
    *** INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

-keepclasseswithmembers class az.khayalsharifli.data.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class az.khayalsharifli.data.**$$serializer { *; }

# ── OkHttp ──
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**

# Keep OkHttp WebSocket listener callbacks
-keepclassmembers class * extends okhttp3.WebSocketListener {
    public void on*(...);
}

# ── Koin ──
-keep class az.khayalsharifli.data.di.** { *; }
