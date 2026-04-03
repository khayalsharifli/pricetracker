package az.khayalsharifli.presentation.ui.detail.mvi

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface DetailEffect {
    data object NavigateBack : DetailEffect
}
