package az.khayalsharifli.data.di

import az.khayalsharifli.data.mapper.StockMapper
import az.khayalsharifli.data.repository.StockRepositoryImpl
import az.khayalsharifli.data.websocket.StockWebSocketService
import az.khayalsharifli.data.websocket.StockWebSocketServiceImpl
import az.khayalsharifli.domain.repository.StockRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val dataModule = module {
    single {
        OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .pingInterval(20, TimeUnit.SECONDS)
            .build()
    }

    single<StockWebSocketService> { StockWebSocketServiceImpl(client = get()) }

    single { StockMapper() }

    single<StockRepository> {
        StockRepositoryImpl(
            webSocketService = get(),
            mapper = get()
        )
    }
}
