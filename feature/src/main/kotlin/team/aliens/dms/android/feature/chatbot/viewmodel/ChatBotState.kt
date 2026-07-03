package team.aliens.dms.android.feature.chatbot.viewmodel

data class ChatBotState(
    val input: String = "",
    val messages: List<ChatBotMessage> = emptyList(),
    val isLoading: Boolean = false,
)

data class ChatBotMessage(
    val text: String,
    val isUser: Boolean,
)
