package az.khayalsharifli.presentation.di

import az.khayalsharifli.presentation.ui.detail.mvi.DetailViewModel
import az.khayalsharifli.presentation.ui.feed.mvi.FeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        FeedViewModel(
            observeStocksUseCase = get(),
            observeConnectionUseCase = get(),
            togglePriceFeedUseCase = get()
        )
    }
    viewModel {
        DetailViewModel(
            savedStateHandle = get(),
            observeStocksUseCase = get()
        )
    }
}
