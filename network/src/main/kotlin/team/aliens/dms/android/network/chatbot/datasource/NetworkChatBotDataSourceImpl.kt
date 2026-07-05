package team.aliens.dms.android.network.chatbot.datasource

import team.aliens.dms.android.network.chatbot.apiservice.ChatBotApiService
import team.aliens.dms.android.network.chatbot.model.ChatBotAnswerResponse
import team.aliens.dms.android.network.chatbot.model.ChatBotQuestionRequest
import javax.inject.Inject

class NetworkChatBotDataSourceImpl @Inject constructor(
    private val chatBotApiService: ChatBotApiService,
) : NetworkChatBotDataSource {
    override suspend fun askQuestion(
        request: ChatBotQuestionRequest,
    ): ChatBotAnswerResponse {
        return chatBotApiService.askQuestion(request)
    }
}
