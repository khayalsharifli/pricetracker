package az.khayalsharifli.domain.di

import az.khayalsharifli.domain.usecase.stock.ObserveConnectionUseCase
import az.khayalsharifli.domain.usecase.stock.ObserveStocksUseCase
import az.khayalsharifli.domain.usecase.stock.TogglePriceFeedUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { ObserveStocksUseCase(repository = get()) }
    factory { ObserveConnectionUseCase(repository = get()) }
    factory { TogglePriceFeedUseCase(repository = get()) }
}
