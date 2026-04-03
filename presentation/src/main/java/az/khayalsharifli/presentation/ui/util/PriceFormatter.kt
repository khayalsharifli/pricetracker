package az.khayalsharifli.presentation.ui.util

fun Double.toFormattedPrice(): String = "$${String.format("%.2f", this)}"
