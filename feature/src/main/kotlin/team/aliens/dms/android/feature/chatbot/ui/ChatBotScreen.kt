package team.aliens.dms.android.feature.chatbot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.feature.chatbot.ui.component.ChatBotInputBar
import team.aliens.dms.android.feature.chatbot.ui.component.ChatBotSuggestionChip

@Composable
fun ChatBotRoute() {
    ChatBotScreen()
}
@Composable
private fun ChatBotScreen() {
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
            ChatBotProfileIcon(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 26.dp, end = 8.dp),
            )
            ChatBotHeader()
            ChatBotSuggestionQuestions()
        }

        ChatBotInputBar(
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
private fun ChatBotProfileIcon(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.size(28.dp),
        shape = CircleShape,
        color = DmsTheme.colorScheme.inverseOnSurface,
    ) {
        Box(contentAlignment = Alignment.BottomCenter) {
            Surface(
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .size(9.dp),
                shape = CircleShape,
                color = DmsTheme.colorScheme.surfaceTint,
            ) {}

            Surface(
                modifier = Modifier
                    .padding(bottom = 3.dp)
                    .size(width = 18.dp, height = 9.dp),
                shape = RoundedCornerShape(topStart = 9.dp, topEnd = 9.dp),
                color = DmsTheme.colorScheme.surfaceTint,
            ) {}
        }
    }
}

@Composable
private fun ChatBotHeader() {
    Column(
        modifier = Modifier.padding(top = 44.dp),
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
private fun ChatBotSuggestionQuestions() {
    Column(
        modifier = Modifier.padding(top = 74.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        ChatBotSuggestionChip("외출 신청은 언제까지 해야 해?")
        ChatBotSuggestionChip("점호 시간 알려줘")
        ChatBotSuggestionChip("세탁실 이용 시간이 궁금해")
        ChatBotSuggestionChip("벌점 기준 알려줘")
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
    DmsTheme {
        ChatBotScreen()
    }
}
