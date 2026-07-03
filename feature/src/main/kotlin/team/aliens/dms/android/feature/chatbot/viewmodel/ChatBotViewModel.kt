package team.aliens.dms.android.feature.chatbot.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import team.aliens.dms.android.data.chatbot.repository.ChatBotRepository
import javax.inject.Inject

@HiltViewModel
internal class ChatBotViewModel @Inject constructor(
    private val chatBotRepository: ChatBotRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(ChatBotState())
    val state: StateFlow<ChatBotState> = _state.asStateFlow()

    fun onInputChange(input: String) {
        _state.update {
            it.copy(input = input)
        }
    }

    fun sendQuestion() {
        val question = state.value.input.trim()

        if (question.isEmpty() || state.value.isLoading) return

        viewModelScope.launch {
            _state.update {
                it.copy(
                    input = "",
                    isLoading = true,
                    messages = it.messages + ChatBotMessage(
                        text = question,
                        isUser = true,
                    ),
                )
            }

            runCatching {
                chatBotRepository.askQuestion(question)
            }.onSuccess { answer ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        messages = it.messages + ChatBotMessage(
                            text = answer,
                            isUser = false,
                        ),
                    )
                }
            }.onFailure {
                _state.update {
                    it.copy(
                        isLoading = false,
                        messages = it.messages + ChatBotMessage(
                            text = "답변을 불러오지 못했어요. 잠시 후 다시 시도해 주세요.",
                            isUser = false,
                        ),
                    )
                }
            }
        }
    }
}
