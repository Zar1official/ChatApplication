package ru.zar1official.chatapplication.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.websocket.*
import ru.zar1official.chatapplication.data.network.Service
import ru.zar1official.chatapplication.data.network.ServiceImpl
import ru.zar1official.chatapplication.data.network.WebSocketService
import ru.zar1official.chatapplication.data.network.WebSocketServiceImpl
import ru.zar1official.chatapplication.data.repositories.RepositoryImpl
import ru.zar1official.chatapplication.domain.repository.Repository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {

    @InstallIn(SingletonComponent::class)
    @Module
    object ProvideModule {
        @Provides
        @Singleton
        fun provideHttpClient(): HttpClient {
            return HttpClient(CIO) {

                install(WebSockets)

                install(JsonFeature) {
                    serializer = KotlinxSerializer(
                        kotlinx.serialization.json.Json {
                            isLenient = true
                            ignoreUnknownKeys = true
                        }
                    )
                }
            }
        }
    }

    @Binds
    @Singleton
    abstract fun provideService(serviceImpl: ServiceImpl): Service

    @Binds
    @Singleton
    abstract fun provideWebSocketService(webSocketServiceImpl: WebSocketServiceImpl): WebSocketService

    @Binds
    @Singleton
    abstract fun provideRepository(repositoryImpl: RepositoryImpl): Repository
}