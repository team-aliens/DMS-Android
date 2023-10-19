package team.aliens.dms.android.core.designsystem

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors(),
) {
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(
            enabled, isError, interactionSource
        ).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    // CompositionLocalProvider(LocalTextSelectionColors provides colors.selectionColors) {
    BasicTextField(
        value = value,
        modifier = if (label != null) {
            modifier
                .semantics(mergeDescendants = true) {}
                .padding(top = TextFieldDefaults.TopPadding)
        } else {
            modifier
        }.defaultMinSize(
            minWidth = TextFieldDefaults.MinWidth, minHeight = TextFieldDefaults.MinHeight
        ),
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        decorationBox = @Composable { innerTextField ->
            Column {
                TextFieldDefaults.DecorationBox(
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors,
                    content = innerTextField,
                )
                val supportingTextColor =
                    colors.supportingTextColor(enabled, isError, interactionSource).value
                val decoratedSupporting: @Composable (() -> Unit)? = supportingText?.let {
                    @Composable {
                        Decoration(
                            contentColor = supportingTextColor,
                            typography = DmsTheme.typography.body2,
                            content = it
                        )
                    }
                }

                supportingText?.let { it() }
            }
        },
    )
    // }
}

@Composable
internal fun TextFieldLayout() {

}

@Immutable
class TextFieldColors(
    val focusedTextColor: Color,
    val unfocusedTextColor: Color,
    val disabledTextColor: Color,
    val errorTextColor: Color,
    val focusedContainerColor: Color,
    val unfocusedContainerColor: Color,
    val disabledContainerColor: Color,
    val errorContainerColor: Color,
    val cursorColor: Color,
    val errorCursorColor: Color,
    val focusedIndicatorColor: Color,
    val unfocusedIndicatorColor: Color,
    val disabledIndicatorColor: Color,
    val errorIndicatorColor: Color,
    val focusedTrailingIconColor: Color,
    val unfocusedTrailingIconColor: Color,
    val disabledTrailingIconColor: Color,
    val errorTrailingIconColor: Color,
    val focusedSupportingTextColor: Color,
    val unfocusedSupportingTextColor: Color,
    val disabledSupportingTextColor: Color,
    val errorSupportingTextColor: Color,
) {

    @Composable
    internal fun trailingIconColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        return rememberUpdatedState(
            when {
                !enabled -> disabledTrailingIconColor
                isError -> errorTrailingIconColor
                focused -> focusedTrailingIconColor
                else -> unfocusedTrailingIconColor
            }
        )
    }

    @Composable
    internal fun indicatorColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            !enabled -> disabledIndicatorColor
            isError -> errorIndicatorColor
            focused -> focusedIndicatorColor
            else -> unfocusedIndicatorColor
        }
        return if (enabled) {
            animateColorAsState(
                targetValue = targetValue,
                animationSpec = tween(durationMillis = TextFieldDefaults.AnimationDuration),
                label = "Text field indicator",
            )
        } else {
            rememberUpdatedState(targetValue)
        }
    }

    @Composable
    internal fun containerColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            !enabled -> disabledContainerColor
            isError -> errorContainerColor
            focused -> focusedContainerColor
            else -> unfocusedContainerColor
        }
        return animateColorAsState(
            targetValue = targetValue,
            animationSpec = tween(durationMillis = TextFieldDefaults.AnimationDuration),
            label = "Text field container",
        )
    }

    @Composable
    internal fun textColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            !enabled -> disabledTextColor
            isError -> errorTextColor
            focused -> focusedTextColor
            else -> unfocusedTextColor
        }
        return rememberUpdatedState(targetValue)
    }

    @Composable
    internal fun supportingTextColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        return rememberUpdatedState(
            when {
                !enabled -> disabledSupportingTextColor
                isError -> errorSupportingTextColor
                focused -> focusedSupportingTextColor
                else -> unfocusedSupportingTextColor
            }
        )
    }

    @Composable
    internal fun cursorColor(isError: Boolean): State<Color> = rememberUpdatedState(
        newValue = if (isError) {
            errorCursorColor
        } else {
            cursorColor
        },
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is TextFieldColors) return false

        if (focusedTextColor != other.focusedTextColor) return false
        if (unfocusedTextColor != other.unfocusedTextColor) return false
        if (disabledTextColor != other.disabledTextColor) return false
        if (errorTextColor != other.errorTextColor) return false
        if (focusedContainerColor != other.focusedContainerColor) return false
        if (unfocusedContainerColor != other.unfocusedContainerColor) return false
        if (disabledContainerColor != other.disabledContainerColor) return false
        if (errorContainerColor != other.errorContainerColor) return false
        if (cursorColor != other.cursorColor) return false
        if (errorCursorColor != other.errorCursorColor) return false
        if (focusedIndicatorColor != other.focusedIndicatorColor) return false
        if (unfocusedIndicatorColor != other.unfocusedIndicatorColor) return false
        if (disabledIndicatorColor != other.disabledIndicatorColor) return false
        if (errorIndicatorColor != other.errorIndicatorColor) return false
        if (focusedTrailingIconColor != other.focusedTrailingIconColor) return false
        if (unfocusedTrailingIconColor != other.unfocusedTrailingIconColor) return false
        if (disabledTrailingIconColor != other.disabledTrailingIconColor) return false
        if (errorTrailingIconColor != other.errorTrailingIconColor) return false
        if (focusedSupportingTextColor != other.focusedSupportingTextColor) return false
        if (unfocusedSupportingTextColor != other.unfocusedSupportingTextColor) return false
        if (disabledSupportingTextColor != other.disabledSupportingTextColor) return false
        if (errorSupportingTextColor != other.errorSupportingTextColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = focusedTextColor.hashCode()
        result = 31 * result + unfocusedTextColor.hashCode()
        result = 31 * result + disabledTextColor.hashCode()
        result = 31 * result + errorTextColor.hashCode()
        result = 31 * result + focusedContainerColor.hashCode()
        result = 31 * result + unfocusedContainerColor.hashCode()
        result = 31 * result + disabledContainerColor.hashCode()
        result = 31 * result + errorContainerColor.hashCode()
        result = 31 * result + cursorColor.hashCode()
        result = 31 * result + errorCursorColor.hashCode()
        result = 31 * result + focusedIndicatorColor.hashCode()
        result = 31 * result + unfocusedIndicatorColor.hashCode()
        result = 31 * result + disabledIndicatorColor.hashCode()
        result = 31 * result + errorIndicatorColor.hashCode()
        result = 31 * result + focusedTrailingIconColor.hashCode()
        result = 31 * result + unfocusedTrailingIconColor.hashCode()
        result = 31 * result + disabledTrailingIconColor.hashCode()
        result = 31 * result + errorTrailingIconColor.hashCode()
        result = 31 * result + focusedSupportingTextColor.hashCode()
        result = 31 * result + unfocusedSupportingTextColor.hashCode()
        result = 31 * result + disabledSupportingTextColor.hashCode()
        result = 31 * result + errorSupportingTextColor.hashCode()
        return result
    }
}

object TextFieldDefaults {

    private val HorizontalPadding = 16.dp
    private val VerticalPadding =12.dp

    val ContentPadding = PaddingValues(
        start = HorizontalPadding,
        top = VerticalPadding,
        end = HorizontalPadding,
        bottom = VerticalPadding,
    )

    val shape: Shape
        @Composable get() = DmsTheme.shapes.small

    val MinHeight = 56.dp

    val MinWidth = 280.dp

    val UnfocusedBorderThickness = 1.dp

    val FocusedBorderThickness = 2.dp

    const val AnimationDuration = 150

    val TopPadding = 8.dp

    const val DisabledInputOpacity = 0.38f

    const val DisabledActiveIndicatorOpacity = 0.38f

    const val DisabledTrailingIconOpacity = 0.38f

    const val DisabledSupportingOpacity = 0.38f

    @Composable
    fun colors(
        focusedTextColor: Color = DmsTheme.colorScheme.onSurface,
        unfocusedTextColor: Color = focusedTextColor,
        disabledTextColor: Color = focusedTextColor.copy(alpha = DisabledInputOpacity),
        errorTextColor: Color = focusedTextColor,
        focusedContainerColor: Color = Color.Transparent,
        unfocusedContainerColor: Color = focusedContainerColor,
        disabledContainerColor: Color = focusedContainerColor,
        errorContainerColor: Color = focusedContainerColor,
        cursorColor: Color = DmsTheme.colorScheme.onSurface,
        errorCursorColor: Color = cursorColor,
        focusedIndicatorColor: Color = DmsTheme.colorScheme.primary,
        unfocusedIndicatorColor: Color = DmsTheme.colorScheme.onSurfaceVariant,
        disabledIndicatorColor: Color = unfocusedIndicatorColor.copy(alpha = DisabledActiveIndicatorOpacity),
        errorIndicatorColor: Color = DmsTheme.colorScheme.error,
        focusedTrailingIconColor: Color = DmsTheme.colorScheme.onSurfaceVariant,
        unfocusedTrailingIconColor: Color = focusedTrailingIconColor,
        disabledTrailingIconColor: Color = focusedTrailingIconColor.copy(alpha = DisabledTrailingIconOpacity),
        errorTrailingIconColor: Color = focusedTrailingIconColor,
        focusedSupportingTextColor: Color = DmsTheme.colorScheme.onSurface,
        unfocusedSupportingTextColor: Color = focusedSupportingTextColor,
        disabledSupportingTextColor: Color = focusedSupportingTextColor
            .copy(alpha = DisabledSupportingOpacity),
        errorSupportingTextColor: Color = DmsTheme.colorScheme.error,
    ): TextFieldColors = TextFieldColors(
        focusedTextColor = focusedTextColor,
        unfocusedTextColor = unfocusedTextColor,
        disabledTextColor = disabledTextColor,
        errorTextColor = errorTextColor,
        focusedContainerColor = focusedContainerColor,
        unfocusedContainerColor = unfocusedContainerColor,
        disabledContainerColor = disabledContainerColor,
        errorContainerColor = errorContainerColor,
        cursorColor = cursorColor,
        errorCursorColor = errorCursorColor,
        focusedIndicatorColor = focusedIndicatorColor,
        unfocusedIndicatorColor = unfocusedIndicatorColor,
        disabledIndicatorColor = disabledIndicatorColor,
        errorIndicatorColor = errorIndicatorColor,
        focusedTrailingIconColor = focusedTrailingIconColor,
        unfocusedTrailingIconColor = unfocusedTrailingIconColor,
        disabledTrailingIconColor = disabledTrailingIconColor,
        errorTrailingIconColor = errorTrailingIconColor,
        focusedSupportingTextColor = focusedSupportingTextColor,
        unfocusedSupportingTextColor = unfocusedSupportingTextColor,
        disabledSupportingTextColor = disabledSupportingTextColor,
        errorSupportingTextColor = errorSupportingTextColor,
    )

    @Composable
    fun DecorationBox(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
        colors: TextFieldColors,
        focusedBorderThickness: Dp = FocusedBorderThickness,
        unfocusedBorderThickness: Dp = UnfocusedBorderThickness,
        content: @Composable () -> Unit,
    ) {
        val borderStroke = animateBorderStrokeAsState(
            enabled = enabled,
            isError = isError,
            interactionSource = interactionSource,
            colors = colors,
            focusedBorderThickness = focusedBorderThickness,
            unfocusedBorderThickness = unfocusedBorderThickness,
        )

        Box(
            modifier = Modifier
                .border(
                    border = borderStroke.value,
                    shape = DmsTheme.shapes.small,
                )
                .padding(ContentPadding),
        ) {
            content()
        }
    }
}

@Composable
private fun animateBorderStrokeAsState(
    enabled: Boolean,
    isError: Boolean,
    interactionSource: InteractionSource,
    colors: TextFieldColors,
    focusedBorderThickness: Dp,
    unfocusedBorderThickness: Dp,
): State<BorderStroke> {
    val focused by interactionSource.collectIsFocusedAsState()
    val indicatorColor = colors.indicatorColor(enabled, isError, interactionSource)
    val targetThickness = if (focused) {
        focusedBorderThickness
    } else {
        unfocusedBorderThickness
    }
    val animatedThickness = if (enabled) {
        animateDpAsState(
            targetValue = targetThickness,
            animationSpec = tween(durationMillis = TextFieldDefaults.AnimationDuration),
            label = "text field border stroke",
        )
    } else {
        rememberUpdatedState(unfocusedBorderThickness)
    }
    return rememberUpdatedState(
        BorderStroke(animatedThickness.value, SolidColor(indicatorColor.value))
    )
}

@Composable
internal fun Decoration(
    contentColor: Color,
    typography: TextStyle? = null,
    content: @Composable () -> Unit,
) {
    val contentWithColor: @Composable () -> Unit = @Composable {
        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            content = content,
        )
    }
    if (typography != null) {
        ProvideTextStyle(typography, contentWithColor)
    } else {
        contentWithColor()
    }
}

@Preview
@Composable
private fun TextFieldPreview() {

    val (value, onValueChange) = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(DmsTheme.colorScheme.background)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            isError = false,
            supportingText = { Text(text = "ASDFakjhkjhkjhsdf") }
        )
    }
}
