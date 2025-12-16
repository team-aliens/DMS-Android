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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.TextFieldDefaults.IconDefaultSizeModifier

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = DmsTheme.typography.body2,
    hint: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) {
        1
    } else {
        Int.MAX_VALUE
    },
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors(),
) {
    val transformedText = remember(value, visualTransformation) {
        visualTransformation.filter(AnnotatedString(value))
    }.text.text
    val isFocused = interactionSource.collectIsFocusedAsState().value

    val inputState = when {
        isFocused -> InputPhase.Focused
        transformedText.isEmpty() -> InputPhase.UnfocusedEmpty
        else -> InputPhase.UnfocusedNotEmpty
    }

    val textColor = textStyle.color.takeOrElse {
        colors.textColor(
            enabled = enabled,
            isError = isError,
            interactionSource = interactionSource,
        ).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    BasicTextField(
        value = value,
        modifier = modifier,
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
            TextFieldDefaults.TextFieldLayout(
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                inputState = inputState,
                isTextEmpty = value.isNotBlank(),
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                hint = hint,
                supportingText = supportingText,
                trailingIcon = trailingIcon,
                shape = shape,
                content = innerTextField,
            )
        },
    )
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
    val focusedHintColor: Color,
    val unfocusedHintColor: Color,
    val disabledHintColor: Color,
    val errorHintColor: Color,
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
            },
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
            rememberUpdatedState(newValue = targetValue)
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
            },
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

    @Composable
    internal fun hintColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            !enabled -> disabledHintColor
            isError -> errorHintColor
            focused -> focusedHintColor
            else -> unfocusedHintColor
        }
        return rememberUpdatedState(targetValue)
    }

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
    private val VerticalPadding = 12.dp

    val ContentPadding = PaddingValues(
        start = HorizontalPadding,
        top = VerticalPadding,
        end = HorizontalPadding,
        bottom = VerticalPadding,
    )

    val IconDefaultSizeModifier = Modifier.size(
        width = 24.dp,
        height = 24.dp,
    )

    private val SupportingTextHorizontalPadding = 4.dp
    private val SupportingTextVerticalPadding = 2.dp

    val SupportingTextPadding = PaddingValues(
        start = SupportingTextHorizontalPadding,
        top = SupportingTextVerticalPadding,
        end = SupportingTextHorizontalPadding,
        bottom = SupportingTextVerticalPadding,
    )

    val shape: Shape
        @Composable get() = DmsTheme.shapes.small

    val MinHeight = 52.dp

    val UnfocusedBorderThickness = 1.dp

    val FocusedBorderThickness = 2.dp

    const val AnimationDuration = 150

    val TopPadding = 8.dp

    const val DisabledInputOpacity = 0.38f

    const val DisabledHintTextOpacity = 0.38f

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
        focusedHintColor: Color = Color.Transparent,
        unfocusedHintColor: Color = DmsTheme.colorScheme.onSurfaceVariant,
        disabledHintColor: Color = unfocusedHintColor.copy(alpha = DisabledHintTextOpacity),
        errorHintColor: Color = Color.Transparent,
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
        disabledSupportingTextColor: Color = focusedSupportingTextColor.copy(alpha = DisabledSupportingOpacity),
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
        focusedHintColor = focusedHintColor,
        unfocusedHintColor = unfocusedHintColor,
        disabledHintColor = disabledHintColor,
        errorHintColor = errorHintColor,
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
    fun TextFieldLayout(
        modifier: Modifier = Modifier,
        enabled: Boolean,
        inputState: InputPhase,
        isTextEmpty: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
        colors: TextFieldColors,
        hint: @Composable (() -> Unit)?,
        supportingText: (@Composable () -> Unit)? = null,
        trailingIcon: @Composable (() -> Unit)? = null,
        focusedBorderThickness: Dp = FocusedBorderThickness,
        unfocusedBorderThickness: Dp = UnfocusedBorderThickness,
        shape: Shape,
        content: @Composable () -> Unit,
    ) = Column {
        val trailingIconColor = colors.trailingIconColor(
            enabled = enabled,
            isError = isError,
            interactionSource = interactionSource,
        ).value

        val decoratedTrailing: @Composable (() -> Unit)? = trailingIcon?.let {
            @Composable {
                Decoration(
                    contentColor = trailingIconColor,
                    content = it,
                )
            }
        }

        DecorationBox(
            modifier = modifier,
            enabled = enabled,
            inputState = inputState,
            isTextEmpty = isTextEmpty,
            isError = isError,
            interactionSource = interactionSource,
            colors = colors,
            focusedBorderThickness = focusedBorderThickness,
            unfocusedBorderThickness = unfocusedBorderThickness,
            hint = hint,
            trailing = decoratedTrailing,
            shape = shape,
            content = content,
        )

        if (supportingText != null) {
            val supportingTextColor = colors.supportingTextColor(
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
            ).value

            Row(
                modifier = Modifier.padding(SupportingTextPadding),
            ) {
                Decoration(
                    contentColor = supportingTextColor,
                    typography = DmsTheme.typography.caption,
                    content = supportingText,
                )
            }
        }
    }

    @Composable
    fun DecorationBox(
        modifier: Modifier = Modifier,
        enabled: Boolean,
        inputState: InputPhase,
        isTextEmpty: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
        colors: TextFieldColors,
        focusedBorderThickness: Dp,
        unfocusedBorderThickness: Dp,
        hint: @Composable (() -> Unit)?,
        trailing: @Composable (() -> Unit)? = null,
        shape: Shape,
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

        val hintColor: @Composable (InputPhase) -> Color = {
            if (!isTextEmpty) {
                colors.hintColor(
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                ).value
            } else {
                Color.Transparent
            }
        }

        Decoration(
            contentColor = DmsTheme.colorScheme.onSurface,
            typography = DmsTheme.typography.caption,
        ) {
            Row(
                modifier = modifier
                    .defaultMinSize(minHeight = MinHeight)
                    .border(
                        border = borderStroke.value,
                        shape = shape,
                    )
                    .padding(ContentPadding),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (hint != null) {
                        Decoration(
                            contentColor = hintColor(inputState),
                            typography = DmsTheme.typography.body2,
                        ) {
                            hint()
                        }
                    }
                    content()
                }

                if (trailing != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = IconDefaultSizeModifier,
                        contentAlignment = Alignment.Center,
                    ) {
                        trailing()
                    }
                }
            }
        }
    }
}

enum class InputPhase {
    Focused, UnfocusedEmpty, UnfocusedNotEmpty,
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
    val indicatorColor = colors.indicatorColor(
        enabled = enabled,
        isError = isError,
        interactionSource = interactionSource,
    )
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
        rememberUpdatedState(newValue = unfocusedBorderThickness)
    }
    return rememberUpdatedState(
        BorderStroke(
            width = animatedThickness.value,
            brush = SolidColor(indicatorColor.value),
        ),
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
        ProvideTextStyle(
            value = typography,
            content = contentWithColor,
        )
    } else {
        contentWithColor()
    }
}

@Preview
@Composable
private fun TextFieldPreview() {
    val (value, onValueChange) = remember { mutableStateOf("") }
    val (error, onErrorChange) = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(DmsTheme.colorScheme.background)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = value,
            onValueChange = onValueChange,
            isError = error,
            hint = {
                Text(text = "Default Text Field")
            },
            supportingText = {
                if (error) {
                    Text(text = "Please check the ID")
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
            ),
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = value,
            onValueChange = onValueChange,
            isError = error,
            hint = {
                Text(text = "Default Text Field")
            },
            supportingText = {
                if (error) {
                    Text(text = "Please check the ID")
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
            ),
            trailingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        modifier = IconDefaultSizeModifier,
                        painter = painterResource(id = R.drawable.ic_password_visible),
                        contentDescription = null,
                    )
                }
            },
        )

        Spacer(modifier = Modifier.weight(1f))

        ContainedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { onErrorChange(!error) },
            colors = if (error) {
                ButtonDefaults.containedErrorButtonColors()
            } else {
                ButtonDefaults.containedButtonColors()
            },
        ) {
            Text(
                text = if (error) {
                    "오류 완화하기"
                } else {
                    "오류 발생시키기"
                },
            )
        }
    }
}
