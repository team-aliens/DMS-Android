package team.aliens.dms.android.network.chatbot.datasource

import team.aliens.dms.android.network.chatbot.model.ChatBotAnswerResponse
import team.aliens.dms.android.network.chatbot.model.ChatBotQuestionRequest

interface NetworkChatBotDataSource {
    suspend fun askQuestion(
        request: ChatBotQuestionRequest,
    ): ChatBotAnswerResponse
}
