package team.aliens.dms.android.core.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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
            Text(text = "Base Button")
        }

        ContainedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {},
        ) {
            Text(text = "Contained Button")
        }

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
        ) {
            Text(text = "Outlined Button")
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
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    textStyle: TextStyle = DmsTheme.typography.button.copy(
        color = DmsTheme.colors.onPrimary,
    ),
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
    textStyle = textStyle,
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
    border: BorderStroke = ButtonDefaults.outlinedBorder,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    textStyle: TextStyle = DmsTheme.typography.button.copy(
        color = DmsTheme.colors.primary,
    ),
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
    textStyle = textStyle,
    content = content,
)

@Composable
fun TextButton() {

}

@Composable
fun RoundedButton() {

}

@OptIn(ExperimentalMaterialApi::class)
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
    textStyle: TextStyle = DmsTheme.typography.button,
    content: @Composable RowScope.() -> Unit,
) {
    val contentColor by colors.contentColor(enabled)

    Surface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        enabled = enabled,
        shape = shape,
        color = colors.backgroundColor(enabled).value,
        contentColor = contentColor.copy(alpha = 1f),
        border = border,
        elevation = elevation,
        interactionSource = interactionSource,
    ) {
        CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
            ProvideTextStyle(
                value = textStyle,
            ) {
                Row(
                    modifier = Modifier
                        .defaultMinSize(
                            minWidth = ButtonDefaults.MinWidth,
                            minHeight = ButtonDefaults.MinHeight,
                        )
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content,
                )
            }
        }
    }
}

@Stable
interface ButtonElevation {

    @Composable
    fun elevation(
        enabled: Boolean,
        interactionSource: InteractionSource,
    ): State<Dp>
}

@Stable
interface ButtonColors {

    @Composable
    fun backgroundColor(enabled: Boolean): State<Color>

    @Composable
    fun contentColor(enabled: Boolean): State<Color>
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

    @Composable
    fun buttonColors(
        backgroundColor: Color = DmsTheme.colors.primary,
        contentColor: Color = contentColorFor(backgroundColor),
        disabledBackgroundColor: Color = DmsTheme.colors.onSurface.copy(alpha = 0.12f)
            .compositeOver(DmsTheme.colors.surface),
        disabledContentColor: Color = DmsTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
    ): ButtonColors = DefaultButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = disabledBackgroundColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun outlinedButtonColors(
        backgroundColor: Color = Color.Transparent,
        contentColor: Color = Color.Transparent,
        disabledContentColor: Color = DmsTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
    ): ButtonColors = DefaultButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = backgroundColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun textButtonColors(
        backgroundColor: Color = Color.Transparent,
        contentColor: Color = DmsTheme.colors.primary,
        disabledContentColor: Color = DmsTheme.colors.onSurface
            .copy(alpha = ContentAlpha.disabled),
    ): ButtonColors = DefaultButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = backgroundColor,
        disabledContentColor = disabledContentColor,
    )

    //TODO
    @Composable
    fun roundedButtonColors(
        backgroundColor: Color = Color.Transparent,
        contentColor: Color = DmsTheme.colors.primary,
        disabledContentColor: Color = DmsTheme.colors.onSurface
            .copy(alpha = ContentAlpha.disabled),
    ): ButtonColors = DefaultButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = backgroundColor,
        disabledContentColor = disabledContentColor
    )

    const val OutlinedBorderOpacity = 1f
    val OutlinedBorderSize = 1.dp

    val outlinedBorder: BorderStroke
        @Composable
        get() = BorderStroke(
            width = OutlinedBorderSize,
            color = DmsTheme.colors.primary.copy(alpha = OutlinedBorderOpacity),
        )
}

@Immutable
private class DefaultButtonColors(
    private val backgroundColor: Color,
    private val contentColor: Color,
    private val disabledBackgroundColor: Color,
    private val disabledContentColor: Color,
) : ButtonColors {

    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> =
        rememberUpdatedState(if (enabled) backgroundColor else disabledBackgroundColor)

    @Composable
    override fun contentColor(enabled: Boolean): State<Color> =
        rememberUpdatedState(if (enabled) contentColor else disabledContentColor)

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || this::class != other::class) {
            return false
        }

        other as DefaultButtonColors

        if (backgroundColor != other.backgroundColor) {
            return false
        }
        if (contentColor != other.contentColor) {
            return false
        }
        if (disabledBackgroundColor != other.disabledBackgroundColor) {
            return false
        }
        if (disabledContentColor != other.disabledContentColor) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = backgroundColor.hashCode()
        result = 31 * result + contentColor.hashCode()
        result = 31 * result + disabledBackgroundColor.hashCode()
        result = 31 * result + disabledContentColor.hashCode()
        return result
    }
}
