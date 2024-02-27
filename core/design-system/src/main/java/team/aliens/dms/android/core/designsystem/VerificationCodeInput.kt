package team.aliens.dms.android.core.designsystem

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
fun VerificationCodeInput(
    modifier: Modifier = Modifier,
    totalLength: Int,
    text: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    filledTextColor: Color = DmsTheme.colorScheme.onSurface,
    defaultTextColor: Color = DmsTheme.colorScheme.onSurfaceVariant,
    supportingText: @Composable (() -> Unit)? = null,
) {
    BasicTextField(
        modifier = modifier,
        value = text,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Next,
        ),
        decorationBox = { _ ->
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = VerificationCodeInputDefaults.DefaultIndicatorSpace,
                        alignment = Alignment.CenterHorizontally,
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    items(totalLength) { index ->
                        Canvas(
                            modifier = Modifier.size(VerificationCodeInputDefaults.DefaultIndicatorSize),
                            onDraw = {
                                drawCircle(
                                    color = if (index > text.length - 1) {
                                        defaultTextColor
                                    } else {
                                        filledTextColor
                                    }
                                )
                            },
                        )
                    }
                }
                if (supportingText != null) {
                    supportingText()
                }
            }
        },
    )
}

object VerificationCodeInputDefaults {
    val DefaultIndicatorSize = DpSize(
        width = 16.dp,
        height = 16.dp,
    )

    val DefaultIndicatorSpace = 20.dp

    @Composable
    fun SupportingText(
        modifier: Modifier = Modifier,
        text: String,
        isError: Boolean = false,
        style: TextStyle = DmsTheme.typography.caption,
        color: Color = if (isError) {
            DmsTheme.colorScheme.onSurface
        } else {
            DmsTheme.colorScheme.error
        },
    ) = Text(
        modifier = modifier,
        text = text,
        style = style,
        color = color,
    )
}
