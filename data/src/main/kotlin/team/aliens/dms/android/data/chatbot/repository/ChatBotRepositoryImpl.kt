package team.aliens.dms.android.data.chatbot.repository

import team.aliens.dms.android.network.chatbot.datasource.NetworkChatBotDataSource
import team.aliens.dms.android.network.chatbot.model.ChatBotQuestionRequest
import javax.inject.Inject

class ChatBotRepositoryImpl @Inject constructor(
    private val networkChatBotDataSource: NetworkChatBotDataSource,
) : ChatBotRepository {
    override suspend fun askQuestion(question: String): String {
        return networkChatBotDataSource.askQuestion(
            request = ChatBotQuestionRequest(question = question),
        ).answer
    }
}
