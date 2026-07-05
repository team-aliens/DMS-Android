package team.aliens.dms.android.network.chatbot.apiservice

import retrofit2.http.Body
import retrofit2.http.POST
import team.aliens.dms.android.network.chatbot.model.ChatBotAnswerResponse
import team.aliens.dms.android.network.chatbot.model.ChatBotQuestionRequest

interface ChatBotApiService {
    @POST("/chatbots/questions")
    suspend fun askQuestion(
        @Body request: ChatBotQuestionRequest,
    ): ChatBotAnswerResponse
}
