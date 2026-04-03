package az.khayalsharifli.pricetracker.di

import az.khayalsharifli.data.di.dataModule
import az.khayalsharifli.domain.di.domainModule
import az.khayalsharifli.presentation.di.presentationModule

val appModule = listOf(
    dataModule,
    domainModule,
    presentationModule
)
