package team.aliens.dms.android.network.chatbot.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import team.aliens.dms.android.core.network.di.DefaultRetrofitClient
import team.aliens.dms.android.network.chatbot.apiservice.ChatBotApiService
import team.aliens.dms.android.network.chatbot.datasource.NetworkChatBotDataSource
import team.aliens.dms.android.network.chatbot.datasource.NetworkChatBotDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ChatBotNetworkModule {
    @Binds
    @Singleton
    abstract fun bindNetworkChatBotDataSource(
        networkChatBotDataSourceImpl: NetworkChatBotDataSourceImpl,
    ): NetworkChatBotDataSource

    companion object {
        @Provides
        @Singleton
        fun provideChatBotApiService(
            @DefaultRetrofitClient retrofit: Retrofit,
        ): ChatBotApiService {
            return retrofit.create(ChatBotApiService::class.java)
        }
    }
}
