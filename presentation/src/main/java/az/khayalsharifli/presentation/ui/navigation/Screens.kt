package az.khayalsharifli.presentation.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {

    @Serializable
    data object Feed : Screens

    @Serializable
    data class Detail(val symbol: String) : Screens
}