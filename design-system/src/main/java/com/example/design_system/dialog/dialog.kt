package com.example.design_system.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.design_system.button.DormButtonColor
import com.example.design_system.button.DormContainedLargeButton
import com.example.design_system.color.DormColor
import com.example.design_system.icon.DormIcon
import com.example.design_system.typography.Body5

@Composable
fun DormCustomDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        content()
    }
}

@Stable
private val DormSurveyDialogPadding = PaddingValues(
    horizontal = 16.dp,
    vertical = 20.dp,
)

@Stable
private val DormSurveyDialogShape: Shape = RoundedCornerShape(6.dp)

@Composable
fun DormSurveyDialog(
    icon: DormIcon,
    iconDescription: String? = null,
    title: String,
    content: String,
    btnText: String,
    onDismissRequest: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                shape = DormSurveyDialogShape,
                color = DormColor.Gray200,
            )
            .border(
                width = 1.dp,
                shape = DormSurveyDialogShape,
                color = DormColor.Gray300,
            ),
    ) {
        Column(
            modifier = Modifier.padding(
                DormSurveyDialogPadding,
            ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(
                        id = icon.drawableId,
                    ),
                    contentDescription = iconDescription,
                )

                Spacer(modifier = Modifier.width(16.dp))

                Body5(
                    text = title,
                    color = DormColor.Error,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Body5(
                text = content,
                color = DormColor.Gray700,
            )

            Spacer(modifier = Modifier.height(32.dp))

            DormContainedLargeButton(
                text = btnText,
                color = DormButtonColor.Blue,
            ) {
                onDismissRequest()
            }
        }

    }
}

@Preview
@Composable
fun PreviewDialog() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        DormCustomDialog(
            onDismissRequest = { },
        ) {
            DormSurveyDialog(
                icon = DormIcon.Dinner,
                title = "설문이 조기종료 되었습니다.",
                content = "사유 : 이러이러한 사유로 설문이 종료되었습니다.",
                btnText = "확인",
            ) {
            }
        }
    }
}