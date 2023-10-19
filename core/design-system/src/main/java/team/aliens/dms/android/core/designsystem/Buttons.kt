package team.aliens.dms.android.core.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: Dp = 0.dp,
    shape: Shape = DmsTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    val containerColor = colors.containerColor(enabled).value
    val contentColor = colors.contentColor(enabled).value
    Surface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        enabled = enabled,
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        border = border,
        interactionSource = interactionSource
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            ProvideTextStyle(value = DmsTheme.typography.button) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = ButtonDefaults.MinWidth, minHeight = ButtonDefaults.MinHeight
                        )
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 8.dp,
                        alignment = Alignment.CenterHorizontally,
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    content = content,
                )
            }
        }
    }
}

@Composable
fun ContainedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: Dp = 0.dp,
    shape: Shape = DmsTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.containedButtonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) = Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    elevation = elevation,
    shape = shape,
    border = border,
    colors = colors,
    contentPadding = contentPadding,
    content = content,
)

@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: Dp = 0.dp,
    shape: Shape = DmsTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) = Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    elevation = elevation,
    shape = shape,
    border = border,
    colors = colors,
    contentPadding = contentPadding,
    content = content,
)

@Composable
fun TextButton() {

}

@Composable
fun RoundedButton() {

}

@Stable
class ButtonColors(
    private val containerColor: Color,
    private val contentColor: Color,
    private val disabledContainerColor: Color,
    private val disabledContentColor: Color,
) {
    @Composable
    internal fun containerColor(enabled: Boolean): State<Color> = rememberUpdatedState(
        if (enabled) {
            containerColor
        } else {
            disabledContainerColor
        },
    )

    @Composable
    internal fun contentColor(enabled: Boolean): State<Color> = rememberUpdatedState(
        if (enabled) {
            contentColor
        } else {
            disabledContentColor
        },
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is ButtonColors) return false

        if (containerColor != other.containerColor) return false
        if (contentColor != other.contentColor) return false
        if (disabledContainerColor != other.disabledContainerColor) return false
        if (disabledContentColor != other.disabledContentColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = containerColor.hashCode()
        result = 31 * result + contentColor.hashCode()
        result = 31 * result + disabledContainerColor.hashCode()
        result = 31 * result + disabledContentColor.hashCode()
        return result
    }
}

object ButtonDefaults {

    private val ButtonHorizontalPadding = 16.dp
    private val ButtonVerticalPadding = 8.dp

    val ContentPadding = PaddingValues(
        start = ButtonHorizontalPadding,
        top = ButtonVerticalPadding,
        end = ButtonHorizontalPadding,
        bottom = ButtonVerticalPadding,
    )

    val MinWidth = 50.dp

    val MinHeight = 46.dp

    val IconSize = 18.dp

    val IconSpacing = 8.dp

    const val DisabledContainerOpacity = 0.12f

    const val DisabledContentOpacity = 0.38f

    const val DisabledTextOpacity = 0.38f

    val containedShape: Shape
        @Composable get() = DmsTheme.shapes.small

    val outlinedShape: Shape
        @Composable get() = DmsTheme.shapes.small

    val textShape: Shape
        @Composable get() = DmsTheme.shapes.small

    val roundShape: Shape
        @Composable get() = DmsTheme.shapes.circle

    @Composable
    fun buttonColors(
        containerColor: Color = DmsTheme.colors.primary,
        contentColor: Color = DmsTheme.colors.onPrimary,
        disabledContainerColor: Color = containerColor.copy(alpha = DisabledContainerOpacity),
        disabledContentColor: Color = contentColor.copy(alpha = DisabledContentOpacity),
    ): ButtonColors = ButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun containedButtonColors(
        containerColor: Color = DmsTheme.colors.primary,
        contentColor: Color = DmsTheme.colors.onPrimary,
        disabledContainerColor: Color = containerColor.copy(alpha = DisabledContainerOpacity),
        disabledContentColor: Color = contentColor.copy(alpha = DisabledContentOpacity),
    ): ButtonColors = ButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun outlinedButtonColors(
        containerColor: Color = Color.Transparent,
        contentColor: Color = DmsTheme.colors.primary,
        disabledContainerColor: Color = containerColor,
        disabledContentColor: Color = contentColor.copy(alpha = DisabledTextOpacity),
    ): ButtonColors = ButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun textButtonColors(
        containerColor: Color = Color.Transparent,
        contentColor: Color = DmsTheme.colors.primary,
        disabledContainerColor: Color = containerColor,
        disabledContentColor: Color = contentColor.copy(alpha = DisabledTextOpacity),
    ): ButtonColors = ButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun roundedButtonColors(
        containerColor: Color = DmsTheme.colors.primaryContainer,
        contentColor: Color = DmsTheme.colors.onPrimaryContainer,
        disabledContainerColor: Color = containerColor.copy(alpha = DisabledContainerOpacity),
        disabledContentColor: Color = contentColor.copy(alpha = DisabledContentOpacity),
    ): ButtonColors = ButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )
}

@Preview(showSystemUi = true)
@Composable
private fun ButtonPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {},
        ) {
            Text(text = "Default Button")
        }

        ContainedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {},
        ) {
            Text(text = "Contained Button")
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
            )
        }

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
        ) {
            Text(text = "Outlined Button")
        }

        androidx.compose.material3.Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { /*TODO*/ },
        ) {
            Text(text = "Material3 Button")
        }
    }
}
