package com.example.design_system.textfield

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.color.DormColor
import com.example.design_system.icon.DormIcon
import com.example.design_system.modifier.dormClickable
import com.example.design_system.typography.Caption
import com.example.design_system.typography.DormTypography
import com.example.design_system.utils.runIf


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
    error: String? = null,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    onClick: (() -> Unit)? = null,
    hint: String? = null,
    description: String? = null,
) {
    val borderColor: Color = if (error == null) DormColor.Gray500 else DormColor.Error

    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        Box(
            modifier = modifier
                .height(44.dp)
                .background(
                    shape = RoundedCornerShape(
                        size = 5.dp,
                    ),
                    color = Color.Transparent,
                )
                .border(
                    width = 1.dp,
                    color = borderColor,
                )
                .wrapContentHeight(
                    align = Alignment.CenterVertically,
                )
                .runIf(
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
                        .fillMaxWidth(0.9f,)
                        .padding(start = 14.dp),
                    value = value,
                    onValueChange = onValueChange,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = imeAction,
                    ),
                    visualTransformation = if (!passwordVisible && isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                    maxLines = 1,
                    textStyle = DormTypography.body4,
                    decorationBox = { innerTextField ->
                        if (value.isEmpty() && hint != null) {
                            Caption(
                                text = hint,
                                color = DormColor.Gray500,
                            )
                        }

                        innerTextField()
                    },
                )
                if (isPassword) {
                    Image(
                        modifier = Modifier
                            .dormClickable(
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

        if (error != null) {
            Box(
                modifier = Modifier.padding(
                    start = 3.dp,
                    top = 6.dp,
                ),
            ) {
                Caption(
                    text = error,
                    color = DormColor.Error,
                )
            }
        }

        if (description != null) {
            Box(
                modifier = Modifier.padding(
                    start = 3.dp,
                    top = 6.dp,
                ),
            ) {
                Caption(
                    text = description,
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
        modifier = Modifier.padding(
            horizontal = 20.dp
        ),
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
            error = "특수문자는 사용할 수 없습니다!",
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
            description = "비밀번호는 4자리 이상 입력해주세요.",
            hint = "비밀번호",
        )
    }
}