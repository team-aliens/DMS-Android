package team.aliens.dms.android.feature.chatbot.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme

@Composable
internal fun ChatBotSuggestionChip(
    text: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = DmsTheme.colorScheme.surfaceTint,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 14.dp),
            text = text,
            color = DmsTheme.colorScheme.surfaceBright,
            style = DmsTheme.typography.body3,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
        )
    }
}
