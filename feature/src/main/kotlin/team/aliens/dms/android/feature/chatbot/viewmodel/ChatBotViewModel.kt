package team.aliens.dms.android.feature.chatbot.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class ChatBotViewModel : ViewModel() {
    private val _state = MutableStateFlow(ChatBotState())
    val state: StateFlow<ChatBotState> = _state.asStateFlow()
}
