package team.aliens.dms.android.core.designsystem.textfield

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.core.designsystem.button.DmsIconButton
import team.aliens.dms.android.core.designsystem.foundation.DmsIcon
import team.aliens.dms.android.core.designsystem.labelM

@Composable
fun DmsTextField(
    modifier: Modifier = Modifier,
    label: String? = null,
    value: String,
    hint: String = "",
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLength: Int = Int.MAX_VALUE,
    isError: Boolean = false,
    errorMessage: String? = null,
    showVisibleIcon: Boolean = false,
    showClearIcon: Boolean = false,
) {
    var isFocused by remember { mutableStateOf(false) }
    val labelColor by animateColorAsState(
        targetValue = if (isFocused || value.isNotEmpty()) {
            DmsTheme.colorScheme.onPrimaryContainer
        } else {
            DmsTheme.colorScheme.secondaryContainer
        },
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        label?.let { label ->
            Text(
                text = label,
                style = DmsTheme.typography.labelM,
                color = labelColor,
            )
        }
        TextField(
            modifier = Modifier.onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
            value = value,
            hint = hint,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            singleLine = singleLine,
            imeAction = imeAction,
            keyboardType = keyboardType,
            keyboardActions = keyboardActions,
            maxLength = maxLength,
            isError = isError,
            showVisibleIcon = showVisibleIcon,
            showClearIcon = showClearIcon,
            isFocused = isFocused,
        )
        if (isError) {
            errorMessage?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    style = DmsTheme.typography.labelM,
                    color = DmsTheme.colorScheme.outline,
                )
            }
        }
    }
}

@Composable
private fun TextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    readOnly: Boolean,
    singleLine: Boolean,
    imeAction: ImeAction,
    keyboardType: KeyboardType,
    keyboardActions: KeyboardActions,
    maxLength: Int,
    isError: Boolean,
    showVisibleIcon: Boolean,
    showClearIcon: Boolean,
    isFocused: Boolean,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val hintAlpha by animateFloatAsState(
        targetValue = if (value.isEmpty()) {
            1f
        } else {
            0f
        },
    )
    var visible by remember { mutableStateOf(false) }
    val (visualTransformation, icon) = if (visible || !showVisibleIcon) {
        VisualTransformation.None to DmsIcon.PasswordInvisible
    } else {
        PasswordVisualTransformation() to DmsIcon.PasswordVisible
    }

    val lineColor by animateColorAsState(
        targetValue = if (isError) {
            DmsTheme.colorScheme.onErrorContainer
        } else if (isFocused || value.isNotEmpty()) {
            DmsTheme.colorScheme.onPrimaryContainer
        } else {
            DmsTheme.colorScheme.onSurfaceVariant
        },
    )

    BasicTextField(
        value = value.take(maxLength),
        onValueChange = { newValue ->
            if (newValue.length <= maxLength) {
                onValueChange(newValue)
            }
        },
        modifier = modifier,
        textStyle = DmsTheme.typography.bodyM.copy(color = DmsTheme.colorScheme.onBackground),
        singleLine = singleLine,
        enabled = enabled,
        readOnly = readOnly,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction,
        ),
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(DmsTheme.colorScheme.onBackground),
    ) { innerTextField ->
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    innerTextField()
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.alpha(hintAlpha),
                            text = hint,
                            style = DmsTheme.typography.bodyM,
                            color = DmsTheme.colorScheme.inverseOnSurface,
                        )
                    }
                }
                Row(
                    modifier = Modifier.height(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    if (showVisibleIcon) {
                        DmsIconButton(
                            resource = icon,
                            tint = DmsTheme.colorScheme.inverseSurface,
                            onClick = { visible = !visible },
                        )
                    }
                    if (showClearIcon && value.isNotEmpty()) {
                        DmsIconButton(
                            resource = DmsIcon.Cancel,
                            tint = DmsTheme.colorScheme.inverseSurface,
                            onClick = { onValueChange("") },
                        )
                    }
                }
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = lineColor,
            )
        }
    }
}
