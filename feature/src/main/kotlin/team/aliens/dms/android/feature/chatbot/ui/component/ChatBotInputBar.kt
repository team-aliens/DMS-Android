package team.aliens.dms.android.feature.chatbot.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme

@Composable
internal fun ChatBotInputBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onFocusChanged: (Boolean) -> Unit = {},
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = Color.Black.copy(alpha = 0.20f),
                spotColor = Color.Black.copy(alpha = 0.30f),
            ),
        shape = RoundedCornerShape(28.dp),
        color = DmsTheme.colorScheme.surfaceTint,
    ) {
        Row(
            modifier = Modifier.padding(
                start = 24.dp,
                top = 6.dp,
                end = 6.dp,
                bottom = 6.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = "DMS AI에게 질문해 보세요...",
                        color = DmsTheme.colorScheme.inverseOnSurface,
                        style = DmsTheme.typography.body3,
                        fontWeight = FontWeight.Normal,
                    )
                }

                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    enabled = enabled,
                    textStyle = DmsTheme.typography.body3.copy(
                        color = DmsTheme.colorScheme.surfaceBright,
                        fontWeight = FontWeight.Normal,
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Send,
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            if (enabled) {
                                onSendClick()
                            }
                        },
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            onFocusChanged(focusState.hasFocus)
                        },
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .clickable(
                        enabled = enabled,
                        onClick = onSendClick,
                    ),
                shape = CircleShape,
                color = DmsTheme.colorScheme.onPrimaryContainer,
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "↑",
                        color = DmsTheme.colorScheme.surfaceTint,
                        style = DmsTheme.typography.title3,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }
        }
    }
}
