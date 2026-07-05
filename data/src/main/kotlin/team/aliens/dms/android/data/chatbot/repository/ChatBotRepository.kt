package team.aliens.dms.android.data.chatbot.repository

interface ChatBotRepository {
    suspend fun askQuestion(question: String): String
}
