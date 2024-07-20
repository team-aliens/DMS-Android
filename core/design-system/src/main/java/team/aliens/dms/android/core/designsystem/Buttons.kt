package team.aliens.dms.android.core.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
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

// TODO: Button 접근제한자 internal로 변경
@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shadowElevation: Dp = 0.dp,
    shape: Shape = ButtonDefaults.containedShape,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    fillMinSize: Boolean = true,
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
        shadowElevation = shadowElevation,
        border = border,
        interactionSource = interactionSource
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            ProvideTextStyle(value = DmsTheme.typography.button) {
                Row(
                    modifier = if (fillMinSize) {
                        Modifier
                            .defaultMinSize(
                                minWidth = ButtonDefaults.MinWidth,
                                minHeight = ButtonDefaults.MinHeight,
                            )
                            .padding(contentPadding)
                    } else {
                        Modifier.padding(contentPadding)
                    },
                    horizontalArrangement = Arrangement.spacedBy(
                        space = ButtonDefaults.ContentSpacing,
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
    shadowElevation: Dp = 0.dp,
    shape: Shape = ButtonDefaults.containedShape,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.containedButtonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    fillMinSize: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) = Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    shadowElevation = shadowElevation,
    shape = shape,
    border = border,
    colors = colors,
    contentPadding = contentPadding,
    fillMinSize = fillMinSize,
    content = content,
)

@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shadowElevation: Dp = 0.dp,
    shape: Shape = ButtonDefaults.outlinedShape,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    border: BorderStroke? = BorderStroke(
        width = ButtonDefaults.OutlineWidth,
        color = colors.contentColor,
    ),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    fillMinSize: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) = Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    shadowElevation = shadowElevation,
    shape = shape,
    border = border,
    colors = colors,
    contentPadding = contentPadding,
    fillMinSize = fillMinSize,
    content = content,
)

@Composable
fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shadowElevation: Dp = 0.dp,
    shape: Shape = ButtonDefaults.textShape,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    fillMinSize: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) = Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    shadowElevation = shadowElevation,
    shape = shape,
    border = border,
    colors = colors,
    contentPadding = contentPadding,
    fillMinSize = fillMinSize,
    content = content,
)

@Composable
fun RoundedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shadowElevation: Dp = 0.dp,
    shape: Shape = DmsTheme.shapes.circle,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.roundedButtonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    fillMinSize: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) = Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    shadowElevation = shadowElevation,
    shape = shape,
    border = border,
    colors = colors,
    contentPadding = contentPadding,
    fillMinSize = fillMinSize,
    content = content,
)

@Stable
class ButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
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

    private val HorizontalPadding = 16.dp
    private val VerticalPadding = 12.dp

    val ContentPadding = PaddingValues(
        start = HorizontalPadding,
        top = VerticalPadding,
        end = HorizontalPadding,
        bottom = VerticalPadding,
    )

    val MinWidth = 50.dp

    val MinHeight = 48.dp

    val IconSize = 18.dp

    val ContentSpacing = 8.dp

    val OutlineWidth = 1.0.dp

    const val DisabledContainerOpacity = 0.38f

    const val DisabledContentOpacity = 0.38f

    const val DisabledTextOpacity = 0.38f

    val outlineColor: Color
        @Composable get() = DmsTheme.colorScheme.primary

    val outlinedButtonBorder: BorderStroke
        @Composable get() = BorderStroke(
            width = OutlineWidth,
            color = outlineColor,
        )

    val containedShape: Shape
        @Composable get() = DmsTheme.shapes.medium

    val outlinedShape: Shape
        @Composable get() = DmsTheme.shapes.medium

    val textShape: Shape
        @Composable get() = DmsTheme.shapes.medium

    val roundShape: Shape
        @Composable get() = DmsTheme.shapes.circle

    @Composable
    fun buttonColors(
        containerColor: Color = DmsTheme.colorScheme.primary,
        contentColor: Color = DmsTheme.colorScheme.onPrimary,
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
        containerColor: Color = DmsTheme.colorScheme.primary,
        contentColor: Color = DmsTheme.colorScheme.onPrimary,
        disabledContainerColor: Color = containerColor.copy(alpha = DisabledContainerOpacity),
        disabledContentColor: Color = contentColor.copy(alpha = DisabledContentOpacity),
    ): ButtonColors = ButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun containedGrayButtonColors(
        containerColor: Color = DmsTheme.colorScheme.backgroundVariant,
        contentColor: Color = DmsTheme.colorScheme.onBackgroundVariant,
        disabledContainerColor: Color = containerColor.copy(alpha = DisabledContainerOpacity),
        disabledContentColor: Color = contentColor.copy(alpha = DisabledContentOpacity),
    ): ButtonColors = containedButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun containedErrorButtonColors(
        containerColor: Color = DmsTheme.colorScheme.error,
        contentColor: Color = DmsTheme.colorScheme.onError,
        disabledContainerColor: Color = containerColor.copy(alpha = DisabledContainerOpacity),
        disabledContentColor: Color = contentColor.copy(alpha = DisabledContentOpacity),
    ): ButtonColors = containedButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun containedRefuseButtonColors(
        containerColor: Color = DmsTheme.colorScheme.errorContainer,
        contentColor: Color = DmsTheme.colorScheme.onErrorContainer,
        disabledContainerColor: Color = containerColor.copy(alpha = DisabledContainerOpacity),
        disabledContentColor: Color = contentColor.copy(alpha = DisabledContentOpacity),
    ): ButtonColors = containedButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun outlinedButtonColors(
        containerColor: Color = Color.Transparent,
        contentColor: Color = DmsTheme.colorScheme.primary,
        disabledContainerColor: Color = containerColor,
        disabledContentColor: Color = contentColor.copy(alpha = DisabledTextOpacity),
    ): ButtonColors = ButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun outlinedGrayButtonColors(
        containerColor: Color = Color.Transparent,
        contentColor: Color = DmsTheme.colorScheme.backgroundVariant,
        disabledContainerColor: Color = containerColor,
        disabledContentColor: Color = contentColor.copy(alpha = DisabledTextOpacity),
    ): ButtonColors = ButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun outlinedErrorButtonColors(
        containerColor: Color = Color.Transparent,
        contentColor: Color = DmsTheme.colorScheme.error,
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
        contentColor: Color = DmsTheme.colorScheme.primary,
        disabledContainerColor: Color = containerColor,
        disabledContentColor: Color = contentColor.copy(alpha = DisabledTextOpacity),
    ): ButtonColors = ButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun textGrayButtonColors(
        containerColor: Color = Color.Transparent,
        contentColor: Color = DmsTheme.colorScheme.backgroundVariant,
        disabledContainerColor: Color = containerColor,
        disabledContentColor: Color = contentColor.copy(alpha = DisabledTextOpacity),
    ): ButtonColors = ButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun textErrorButtonColors(
        containerColor: Color = Color.Transparent,
        contentColor: Color = DmsTheme.colorScheme.error,
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
        containerColor: Color = DmsTheme.colorScheme.primaryContainer,
        contentColor: Color = DmsTheme.colorScheme.onPrimaryContainer,
        disabledContainerColor: Color = containerColor.copy(alpha = DisabledContainerOpacity),
        disabledContentColor: Color = contentColor.copy(alpha = DisabledContentOpacity),
    ): ButtonColors = ButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun roundedGrayButtonColors(
        containerColor: Color = DmsTheme.colorScheme.backgroundVariant,
        contentColor: Color = DmsTheme.colorScheme.onBackgroundVariant,
        disabledContainerColor: Color = containerColor.copy(alpha = DisabledContainerOpacity),
        disabledContentColor: Color = contentColor.copy(alpha = DisabledContentOpacity),
    ): ButtonColors = ButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun roundedErrorButtonColors(
        containerColor: Color = DmsTheme.colorScheme.error,
        contentColor: Color = DmsTheme.colorScheme.onError,
        disabledContainerColor: Color = containerColor.copy(alpha = DisabledContainerOpacity),
        disabledContentColor: Color = contentColor.copy(alpha = DisabledContentOpacity),
    ): ButtonColors = ButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun roundedRefuseButtonColors(
        containerColor: Color = DmsTheme.colorScheme.errorContainer,
        contentColor: Color = DmsTheme.colorScheme.onErrorContainer,
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
        modifier = Modifier
            .background(color = DmsTheme.colorScheme.background)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ContainedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
        ) {
            Text(text = "Contained Default Button")
        }
        ContainedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
            )
            Text(text = "Contained Default Button")
        }

        ContainedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.containedGrayButtonColors(),
        ) {
            Text(text = "Contained Gray Button")
        }
        ContainedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.containedGrayButtonColors(),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
            )
            Text(text = "Contained Gray Button")
        }

        ContainedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.containedErrorButtonColors(),
        ) {
            Text(text = "Contained Error Button")
        }
        ContainedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.containedErrorButtonColors(),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
            )
            Text(text = "Contained Error Button")
        }

        ContainedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.containedRefuseButtonColors(),
        ) {
            Text(text = "Contained Refuse Button")
        }
        ContainedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.containedRefuseButtonColors(),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
            )
            Text(text = "Contained Refuse Button")
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
        )

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
        ) {
            Text(text = "Outlined Default Button")
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
            )
            Text(text = "Outlined Default Button")
        }

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.outlinedGrayButtonColors()
        ) {
            Text(text = "Outlined Gray Button")
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.outlinedGrayButtonColors()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
            )
            Text(text = "Outlined Gray Button")
        }

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.outlinedErrorButtonColors()
        ) {
            Text(text = "Outlined Error Button")
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.outlinedErrorButtonColors()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
            )
            Text(text = "Outlined Error Button")
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
        )

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
        ) {
            Text(text = "Text Default Button")
        }
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
            )
            Text(text = "Text Default Button")
        }

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.textGrayButtonColors()
        ) {
            Text(text = "Text Gray Button")
        }
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.textGrayButtonColors()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
            )
            Text(text = "Text Gray Button")
        }

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.textErrorButtonColors()
        ) {
            Text(text = "Text Error Button")
        }
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.textErrorButtonColors()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
            )
            Text(text = "Text Error Button")
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
        )

        RoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
        ) {
            Text(text = "Rounded Default Button")
        }
        RoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
            )
            Text(text = "Rounded Default Button")
        }

        RoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.roundedGrayButtonColors(),
        ) {
            Text(text = "Rounded Gray Button")
        }
        RoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.roundedGrayButtonColors(),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
            )
            Text(text = "Rounded Gray Button")
        }

        RoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.roundedErrorButtonColors(),
            onClick = { },
        ) {
            Text(text = "Rounded Error Button")
        }
        RoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.roundedErrorButtonColors(),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
            )
            Text(text = "Rounded Error Button")
        }

        RoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.roundedRefuseButtonColors(),
        ) {
            Text(text = "Rounded Refuse Button")
        }
        RoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
            colors = ButtonDefaults.roundedRefuseButtonColors(),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
            )
            Text(text = "Rounded Refuse Button")
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
        )

        androidx.compose.material3.Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { },
        ) {
            Text(text = "Material3 Button")
        }
    }
}
