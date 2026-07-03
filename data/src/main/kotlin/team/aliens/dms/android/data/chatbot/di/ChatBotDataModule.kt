package team.aliens.dms.android.data.chatbot.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.data.chatbot.repository.ChatBotRepository
import team.aliens.dms.android.data.chatbot.repository.ChatBotRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ChatBotDataModule {
    @Binds
    @Singleton
    abstract fun bindChatBotRepository(
        chatBotRepositoryImpl: ChatBotRepositoryImpl,
    ): ChatBotRepository
}
