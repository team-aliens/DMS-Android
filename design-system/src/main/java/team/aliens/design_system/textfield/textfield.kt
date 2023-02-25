package team.aliens.design_system.textfield

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Caption
import team.aliens.design_system.typography.DormTypography
import team.aliens.design_system.utils.runIf

/**
 * [TODO]
 * 기본적으로 로직이 깔끔하지 못해
 * 추후에 개선이 필요함.
 */
@Composable
fun DormTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    error: Boolean = false,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    onClick: (() -> Unit)? = null,
    hint: String? = null,
    errorDescription: String? = null,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    val borderColor: Color = if (error) DormColor.Error
    else if (isFocused) DormColor.DormPrimary
    else DormColor.Gray500

    val borderWidth = if (isFocused) 2.dp
    else 1.dp

    val textfieldWidth = if (isPassword) 0.9f
    else 1f


    Column {
        Box(
            modifier = modifier.clip(
                shape = MaterialTheme.shapes.small,
            ).height(44.dp).background(
                color = Color.White,
            ).border(
                width = borderWidth,
                shape = MaterialTheme.shapes.small,
                color = borderColor,
            ).wrapContentHeight(
                align = Alignment.CenterVertically,
            ).runIf(
                condition = onClick != null,
            ) {
                composed {
                    dormClickable(
                        rippleEnabled = false,
                    ) {
                        onClick!!
                    }
                }
            },
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth(textfieldWidth)
                        .padding(horizontal = 14.dp)
                        .background(color = Color.Transparent)
                        .onFocusChanged {
                            isFocused = it.isFocused
                        },
                    value = value,
                    onValueChange = onValueChange,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = imeAction,
                    ),
                    singleLine = true,
                    visualTransformation = if (!passwordVisible && isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                    maxLines = 1,
                    textStyle = DormTypography.body4,
                    decorationBox = { innerTextField ->
                        if (value.isEmpty() && hint != null) {
                            Body2(
                                text = hint,
                                color = DormColor.Gray500,
                            )
                        }
                        innerTextField()
                    },
                )
                if (isPassword) {
                    Image(
                        modifier = Modifier.dormClickable(
                            rippleEnabled = true,
                        ) {
                            passwordVisible = !passwordVisible
                        },
                        painter = if (passwordVisible) painterResource(
                            id = DormIcon.Password_Visible.drawableId,
                        ) else painterResource(
                            id = DormIcon.Password_InVisible.drawableId,
                        ),
                        contentDescription = null,
                        alpha = if (value.isNotEmpty()) 1f else 0f,
                    )
                }
            }
        }

        if (errorDescription != null) {
            Box(
                modifier = Modifier.padding(
                    start = 3.dp,
                    top = 6.dp,
                ),
            ) {
                Caption(
                    text = errorDescription,
                    color = DormColor.Error
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDormTextField() {
    var value by remember { mutableStateOf(String()) }
    var value2 by remember { mutableStateOf(String()) }
    var value3 by remember { mutableStateOf(String()) }
    var value4 by remember { mutableStateOf(String()) }

    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
    ) {
        // default text field
        DormTextField(
            value = value,
            onValueChange = { value = it },
        )

        Spacer(
            modifier = Modifier.height(
                height = 15.dp,
            ),
        )

        // password text field
        DormTextField(
            value = value2,
            onValueChange = { value2 = it },
            isPassword = true,
        )

        Spacer(
            modifier = Modifier.height(
                height = 15.dp,
            ),
        )

        // error text field
        DormTextField(
            value = value3,
            onValueChange = { value3 = it },
            errorDescription = "특수문자는 사용할 수 없습니다!",
        )

        Spacer(
            modifier = Modifier.height(
                height = 15.dp,
            ),
        )

        // description text field
        DormTextField(
            value = value4,
            onValueChange = { value4 = it },
            errorDescription = "비밀번호는 4자리 이상 입력해주세요.",
            hint = "비밀번호",
        )
    }
}