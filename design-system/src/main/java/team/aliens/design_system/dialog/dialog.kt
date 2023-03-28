package team.aliens.design_system.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.extension.Space
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.typography.Body5

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
private val DormDoubleButtonDialogShape: Shape = RoundedCornerShape(10.dp)

@Stable
private val DormDoubleButtonDialogSubButtonShape: Shape = RoundedCornerShape(
    bottomStart = 10.dp,
)

@Stable
private val DormDoubleButtonDialogMainButtonShape: Shape = RoundedCornerShape(
    bottomEnd = 10.dp,
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

                Space(space = 16.dp)

                Body5(
                    text = title,
                    color = DormColor.Error,
                )
            }

            Space(space = 20.dp)

            Body5(
                text = content,
                color = DormColor.Gray700,
            )

            Space(space = 32.dp)

            DormContainedLargeButton(
                text = btnText,
                color = DormButtonColor.Blue,
            ) {
                onDismissRequest()
            }
        }

    }
}

@Composable
fun DormBottomAlignedContainedLargeButtonDialog(
    btnText: String,
    btnColor: DormButtonColor,
    onBtnClick: () -> Unit,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .dormClickable {
                onDismissRequest()
            },
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = DormTheme.colors.surface,
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 40.dp,
                ),
        ) {

            content()

            Space(space = 50.dp)

            DormContainedLargeButton(
                text = btnText,
                color = btnColor,
                onClick = onBtnClick,
            )
        }
    }
}

@Composable
fun DormBottomAlignedSingleButtonDialog(
    btnText: String,
    onBtnClick: () -> Unit,
    btnTextColor: Color = DormTheme.colors.onBackground,
    onBackgroundPress: () -> Unit,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .dormClickable {
                onBackgroundPress()
            },
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            Modifier.background(
                color = DormTheme.colors.surface,
            ),
        ) {

            content()

            Divider(
                modifier = Modifier
                    .padding(
                        horizontal = 10.dp,
                    )
                    .fillMaxWidth(),
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable {
                        onBtnClick()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Body2(
                    color = btnTextColor,
                    text = btnText,
                )
            }
        }
    }
}

@Composable
fun DormDoubleButtonDialog(
    content: String,
    mainBtnText: String,
    subBtnText: String,
    onMainBtnClick: () -> Unit,
    onSubBtnClick: () -> Unit,
    mainBtnTextColor: Color = DormTheme.colors.error,
    subBtnTextColor: Color = DormTheme.colors.onSurface,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(
                DormTheme.colors.surface,
                DormDoubleButtonDialogShape,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Space(space = 50.dp)

        Body3(
            text = content,
        )

        Space(space = 40.dp)

        Divider(
            modifier = Modifier
                .background(
                    DormTheme.colors.secondaryVariant,
                )
                .height(1.dp),
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(
                        DormDoubleButtonDialogSubButtonShape,
                    )
                    .clickable {
                        onSubBtnClick()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Body3(
                    text = subBtnText,
                    color = subBtnTextColor,
                )
            }

            Divider(
                modifier = Modifier
                    .background(
                        DormTheme.colors.secondaryVariant,
                    )
                    .fillMaxHeight()
                    .width(1.dp),
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(
                        DormDoubleButtonDialogMainButtonShape,
                    )
                    .clickable {
                        onMainBtnClick()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Body3(
                    text = mainBtnText,
                    color = mainBtnTextColor,
                )
            }
        }
    }
}

@Composable
fun DormSingleButtonDialog(
    content: String,
    mainBtnText: String,
    onMainBtnClick: () -> Unit,
    mainBtnTextColor: Color = DormTheme.colors.error,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(
                DormTheme.colors.surface,
                DormDoubleButtonDialogShape,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Space(space = 50.dp)

        Body3(
            text = content,
        )

        Space(space = 40.dp)

        Divider(
            modifier = Modifier
                .background(
                    DormTheme.colors.secondaryVariant,
                )
                .height(1.dp),
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(
                        DormDoubleButtonDialogSubButtonShape,
                    )
                    .clickable {
                        onMainBtnClick()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Body3(
                    text = mainBtnText,
                    color = mainBtnTextColor,
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(
    showSystemUi = true,
    showBackground = true,
)
@Composable
fun PreviewDialog() {
    Column {
        /*DormCustomDialog(
            onDismissRequest = { },
        ) {
            DormSurveyDialog(
                icon = DormIcon.Dinner,
                title = "설문이 조기종료 되었습니다.",
                content = "사유 : 이러이러한 사유로 설문이 종료되었습니다.",
                btnText = "확인",
            ) {}
        }*/
        /*DormCustomDialog(onDismissRequest = { }) {
            DormDoubleButtonDialog(content = "정말 로그아웃 하시겠습니까?",
                mainBtnText = "확인",
                subBtnText = "취소",
                onMainBtnClick = { *//*TODO*//* },
                onSubBtnClick = { *//*TODO*//* })
        }*/

        DormCustomDialog(
            onDismissRequest = {},
        ) {
            DormBottomAlignedSingleButtonDialog(
                btnText = "취소",
                onBtnClick = { },
                onBackgroundPress = {

                }
            ) {

            }
        }
    }
}
