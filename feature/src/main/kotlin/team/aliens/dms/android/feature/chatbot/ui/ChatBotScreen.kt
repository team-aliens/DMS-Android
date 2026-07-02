package team.aliens.dms.android.feature.chatbot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme

@Composable
fun DmsAiRoute() {
    DmsAiScreen()
}

@Composable
private fun DmsAiScreen() {
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
            DmsAiHeader()
            SuggestionQuestions()
        }

        DmsAiInputBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp, vertical = 96.dp),
        )
    }
}

@Composable
private fun DmsAiHeader() {
    Column(
        modifier = Modifier.padding(top = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "기숙사 생활,\n이제 바로 물어보세요",
            color = DmsTheme.colorScheme.onBackground,
            style = DmsTheme.typography.headline1,
            textAlign = TextAlign.Center,
        )

        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = "외출, 점호, 벌점, 세탁실 이용 등 기숙사 규정을 AI\n가 빠르고 정확하게 안내해 드립니다.",
            color = DmsTheme.colorScheme.onSurfaceVariant,
            style = DmsTheme.typography.body2,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SuggestionQuestions() {
    Column(
        modifier = Modifier.padding(top = 72.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        SuggestionChip("외출 신청은 언제까지 해야 해?")
        SuggestionChip("점호 시간 알려줘")
        SuggestionChip("세탁실 이용 시간이 궁금해")
        SuggestionChip("벌점 기준 알려줘")
    }
}

@Composable
private fun SuggestionChip(text: String) {
    Surface(
        modifier = Modifier.widthIn(min = 168.dp),
        shape = RoundedCornerShape(12.dp),
        color = DmsTheme.colorScheme.surfaceTint,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            text = text,
            color = DmsTheme.colorScheme.onBackground,
            style = DmsTheme.typography.body2,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun DmsAiInputBar(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(14.dp, RoundedCornerShape(28.dp)),
        shape = RoundedCornerShape(28.dp),
        color = DmsTheme.colorScheme.surfaceTint,
    ) {
        Row(
            modifier = Modifier.padding(start = 24.dp, top = 10.dp, end = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "DMS AI에게 질문해 보세요...",
                color = DmsTheme.colorScheme.onSurfaceVariant,
                style = DmsTheme.typography.body2,
            )

            Spacer(modifier = Modifier.weight(1f))

            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = DmsTheme.colorScheme.secondary,
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "↑",
                        color = DmsTheme.colorScheme.surfaceTint,
                        style = DmsTheme.typography.title2,
                    )
                }
            }
        }
    }
}
