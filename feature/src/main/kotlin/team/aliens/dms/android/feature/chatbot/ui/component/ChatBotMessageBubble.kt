package team.aliens.dms.android.feature.chatbot.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme

@Composable
internal fun ChatBotMessageBubble(
    text: String,
    isUser: Boolean,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.widthIn(max = 280.dp),
        shape = RoundedCornerShape(12.dp),
        color = if (isUser) {
            DmsTheme.colorScheme.surfaceVariant
        } else {
            DmsTheme.colorScheme.surfaceTint
        },
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
            text = if (isUser) {
                AnnotatedString(text)
            } else {
                text.toChatBotMarkdownText()
            },
            color = DmsTheme.colorScheme.surfaceBright,
            style = DmsTheme.typography.body3,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Composable
internal fun ChatBotTypingBubble(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = DmsTheme.colorScheme.surfaceTint,
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
        ) {
            ChatBotTypingIndicator()
        }
    }
}

private fun String.toChatBotMarkdownText(): AnnotatedString {
    val convertedText = replace(
        regex = Regex("""(?m)^\s*\*\s+"""),
        replacement = "• ",
    )
    val boldRegex = Regex("""\*\*(.*?)\*\*""")

    return buildAnnotatedString {
        var currentIndex = 0

        boldRegex.findAll(convertedText).forEach { matchResult ->
            append(convertedText.substring(currentIndex, matchResult.range.first))

            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(matchResult.groupValues[1])
            pop()

            currentIndex = matchResult.range.last + 1
        }

        append(convertedText.substring(currentIndex))
    }
}
