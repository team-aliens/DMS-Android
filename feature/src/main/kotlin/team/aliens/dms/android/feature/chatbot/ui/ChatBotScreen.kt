package team.aliens.dms.android.feature.chatbot.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.feature.chatbot.ui.component.ChatBotInputBar
import team.aliens.dms.android.feature.chatbot.ui.component.ChatBotMessageBubble
import team.aliens.dms.android.feature.chatbot.ui.component.ChatBotSuggestionChip
import team.aliens.dms.android.feature.chatbot.ui.component.ChatBotTypingIndicator
import team.aliens.dms.android.feature.chatbot.viewmodel.ChatBotMessage
import team.aliens.dms.android.feature.chatbot.viewmodel.ChatBotState
import team.aliens.dms.android.feature.chatbot.viewmodel.ChatBotViewModel

private val ChatBotInputBarHeight = 64.dp
private val ChatBotKeyboardInputBottomPadding = 12.dp
private val ChatBotBottomNavigationInputPadding = 16.dp
private val ChatBotScaffoldBottomBarPadding = 104.dp
private val ChatBotInputBarHorizontalPadding = 20.dp
private val ChatBotMessageBottomSpacing = 24.dp

@Composable
fun ChatBotRoute(
    onInputFocusChange: (Boolean) -> Unit = {},
) {
    val viewModel: ChatBotViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    ChatBotScreen(
        state = state,
        onInputChange = viewModel::onInputChange,
        onSendClick = {
            viewModel.sendQuestion()
        },
        onSuggestionClick = { question ->
            viewModel.onInputChange(question)
            viewModel.sendQuestion()
            focusManager.clearFocus()
        },
        onInputFocusChange = onInputFocusChange,
    )
}

@Composable
private fun ChatBotScreen(
    state: ChatBotState,
    onInputChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onSuggestionClick: (String) -> Unit,
    onInputFocusChange: (Boolean) -> Unit = {},
) {
    var isInputFocused by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val keyboardBottomPadding = WindowInsets.ime
        .asPaddingValues()
        .calculateBottomPadding()
    val isKeyboardVisible = keyboardBottomPadding > 0.dp

    val targetInputBottomPadding = if (isKeyboardVisible) {
        (
                keyboardBottomPadding -
                        ChatBotScaffoldBottomBarPadding +
                        ChatBotKeyboardInputBottomPadding
                ).coerceAtLeast(ChatBotKeyboardInputBottomPadding)
    } else {
        ChatBotBottomNavigationInputPadding
    }

    val inputBottomPadding by animateDpAsState(
        targetValue = targetInputBottomPadding,
        label = "ChatBotInputBottomPadding",
    )

    val inputBackgroundHeight = inputBottomPadding + ChatBotInputBarHeight
    val messageBottomPadding = inputBackgroundHeight + ChatBotMessageBottomSpacing

    LaunchedEffect(state.messages.size, state.isLoading) {
        if (state.messages.isNotEmpty() || state.isLoading) {
            listState.animateScrollToItem(
                index = state.messages.size + if (state.isLoading) 1 else 0,
                scrollOffset = 80,
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background)
            .statusBarsPadding(),
    ) {
        if (state.messages.isEmpty() && !isInputFocused) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ChatBotHeader()

                ChatBotSuggestionQuestions(
                    onSuggestionClick = onSuggestionClick,
                )
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(
                    top = 72.dp,
                    bottom = messageBottomPadding,
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                state.messages.forEach { message ->
                    item {
                        ChatBotMessageItem(message = message)
                    }
                }

                if (state.isLoading) {
                    item {
                        ChatBotTypingItem()
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(inputBackgroundHeight)
                .background(DmsTheme.colorScheme.background),
        )

        ChatBotInputBar(
            value = state.input,
            onValueChange = onInputChange,
            onSendClick = onSendClick,
            enabled = true,
            onFocusChange = { focused ->
                isInputFocused = focused
                onInputFocusChange(focused)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    start = ChatBotInputBarHorizontalPadding,
                    end = ChatBotInputBarHorizontalPadding,
                    bottom = inputBottomPadding,
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp),
            text = "외출, 점호, 벌점, 세탁실 이용 등 기숙사 규정을 \nAI가 빠르고 정확하게 안내해 드립니다.",
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
    Column(
        modifier = Modifier.padding(top = 46.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ChatBotSuggestionChip(
            text = "외출 신청은 언제까지 해야 해?",
            onClick = { onSuggestionClick("외출 신청은 언제까지 해야 해?") },
        )
        ChatBotSuggestionChip(
            text = "점호 시간 알려줘",
            onClick = { onSuggestionClick("점호 시간 알려줘") },
        )
        ChatBotSuggestionChip(
            text = "세탁실 이용 시간이 궁금해",
            onClick = { onSuggestionClick("세탁실 이용 시간이 궁금해") },
        )
        ChatBotSuggestionChip(
            text = "벌점 기준 알려줘",
            onClick = { onSuggestionClick("벌점 기준 알려줘") },
        )
    }
}

@Composable
private fun ChatBotMessageItem(
    message: ChatBotMessage,
) {
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

@Composable
private fun ChatBotTypingItem() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart,
    ) {
        ChatBotTypingIndicator()
    }
}
