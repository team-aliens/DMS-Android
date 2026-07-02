package team.aliens.dms.android.feature.chatbot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import team.aliens.dms.android.core.designsystem.DmsTheme

@Composable
fun DmsAiRoute() {
    DmsAiScreen()
}

@Composable
private fun DmsAiScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        DmsAiHeader()
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
