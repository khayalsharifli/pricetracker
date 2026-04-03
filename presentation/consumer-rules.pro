# ──────────────────────────────────────────────
# Presentation module consumer rules
# Automatically applied to any app that depends on :presentation
# ──────────────────────────────────────────────

# ── Kotlinx Serialization (Navigation routes) ──
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

-keepclassmembers @kotlinx.serialization.Serializable class az.khayalsharifli.presentation.** {
    *** Companion;
    *** INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

-keepclasseswithmembers class az.khayalsharifli.presentation.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class az.khayalsharifli.presentation.**$$serializer { *; }

# Keep @Serializable navigation route classes
-keep @kotlinx.serialization.Serializable class az.khayalsharifli.presentation.ui.navigation.** { *; }

# ── Compose ──
-dontwarn androidx.compose.**

# ── Orbit MVI ──
-keep class org.orbitmvi.orbit.** { *; }
-dontwarn org.orbitmvi.orbit.**

# ── Koin ──
-keep class az.khayalsharifli.presentation.di.** { *; }

# Keep ViewModels (instantiated via reflection by Koin)
-keep class az.khayalsharifli.presentation.ui.**.mvi.*ViewModel { *; }
