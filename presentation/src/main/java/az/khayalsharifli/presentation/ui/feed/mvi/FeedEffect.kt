package az.khayalsharifli.presentation.ui.feed.mvi

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface FeedEffect {
    data class NavigateToDetail(val symbol: String) : FeedEffect
}
