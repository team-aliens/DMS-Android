package team.aliens.dms.android.data.chatbot.repository

import team.aliens.dms.android.network.chatbot.apiservice.ChatBotApiService
import team.aliens.dms.android.network.chatbot.model.ChatBotQuestionRequest

class ChatBotRepository(
    private val chatBotApi: ChatBotApiService,
) {
    suspend fun askQuestion(question: String): String {
        return chatBotApi.askQuestion(
            request = ChatBotQuestionRequest(question = question),
        ).answer
    }
}
