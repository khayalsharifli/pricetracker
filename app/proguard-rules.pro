# ──────────────────────────────────────────────
# App module ProGuard rules
# ──────────────────────────────────────────────

# Keep line numbers for crash reports
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# ── Koin ──
-keep class az.khayalsharifli.pricetracker.di.** { *; }

# ── Kotlin ──
-dontwarn kotlin.**
-dontwarn kotlinx.**
