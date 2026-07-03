package team.aliens.dms.android.feature.chatbot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.feature.chatbot.ui.component.ChatBotInputBar
import team.aliens.dms.android.feature.chatbot.ui.component.ChatBotMessageBubble
import team.aliens.dms.android.feature.chatbot.ui.component.ChatBotSuggestionChip
import team.aliens.dms.android.feature.chatbot.ui.component.ChatBotTypingBubble
import team.aliens.dms.android.feature.chatbot.viewmodel.ChatBotMessage
import team.aliens.dms.android.feature.chatbot.viewmodel.ChatBotState
import team.aliens.dms.android.feature.chatbot.viewmodel.ChatBotViewModel


@Composable
fun ChatBotRoute() {
    val viewModel: ChatBotViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChatBotScreen(
        state = state,
        onInputChange = viewModel::onInputChange,
        onSendClick = viewModel::sendQuestion,
        onSuggestionClick = { question ->
            viewModel.onInputChange(question)
            viewModel.sendQuestion()
        },
    )
}

@Composable
private fun ChatBotScreen(
    state: ChatBotState,
    onInputChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onSuggestionClick: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ChatBotHeader()

            if (state.messages.isEmpty() && !state.isLoading) {
                ChatBotSuggestionQuestions(
                    onSuggestionClick = onSuggestionClick,
                )
            } else {
                ChatBotMessages(
                    messages = state.messages,
                    isLoading = state.isLoading,
                )
            }
        }

        ChatBotInputBar(
            value = state.input,
            onValueChange = onInputChange,
            onSendClick = onSendClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 112.dp,
                ),
        )
    }
}

@Composable
private fun ChatBotHeader() {
    Column(
        modifier = Modifier.padding(top = 92.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "기숙사 생활,\n이제 바로 물어보세요",
            color = DmsTheme.colorScheme.onBackground,
            style = DmsTheme.typography.headline2,
            textAlign = TextAlign.Center,
        )

        Text(
            modifier = Modifier.padding(top = 22.dp),
            text = "외출, 점호, 벌점, 세탁실 이용 등 기숙사 규정을 AI\n가 빠르고 정확하게 안내해 드립니다.",
            color = DmsTheme.colorScheme.inverseSurface,
            style = DmsTheme.typography.body3,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ChatBotSuggestionQuestions(
    onSuggestionClick: (String) -> Unit,
) {
    val suggestions = listOf(
        "외출 신청은 언제까지 해야 해?",
        "점호 시간 알려줘",
        "세탁실 이용 시간이 궁금해",
        "벌점 기준 알려줘",
    )

    Column(
        modifier = Modifier.padding(top = 46.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        suggestions.forEach { suggestion ->
            ChatBotSuggestionChip(
                text = suggestion,
                onClick = { onSuggestionClick(suggestion) },
            )
        }
    }
}

@Composable
private fun ChatBotMessages(
    messages: List<ChatBotMessage>,
    isLoading: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 52.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        messages.forEach { message ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = if (message.isUser) {
                    Alignment.CenterEnd
                } else {
                    Alignment.CenterStart
                },
            ) {
                ChatBotMessageBubble(
                    text = message.text,
                    isUser = message.isUser,
                )
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart,
            ) {
                ChatBotTypingBubble()
            }
        }
    }
}

@Preview(
    name = "ChatBot Screen",
    showBackground = true,
    widthDp = 375,
    heightDp = 812,
)
@Composable
fun ChatBotScreenPreview() {
    DmsTheme(isDarkTheme = false) {
        ChatBotScreen(
            state = ChatBotState(
                input = "",
                messages = emptyList(),
                isLoading = false,
            ),
            onInputChange = {},
            onSendClick = {},
            onSuggestionClick = {},
        )
    }
}
