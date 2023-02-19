package team.aliens.dms_android.feature.remain

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.typography.ButtonText
import team.aliens.design_system.typography.SubTitle2
import team.aliens.dms_android.component.FloatingNotice
import team.aliens.dms_android.util.TopBar

// TODO 테스트 더미 값 추후 삭제
data class StayApplicationInformation(
    val stayApplicationTitle: String,
    val staApplicationContent: String,
)

@Composable
fun RemainApplicationScreen(
    navController: NavController,
) {

    // TODO 테스트 더미 값들
    // TODO 추후 서버와 연동 과정에서 삭제하고 작업 진행

    val stayApplicationItems by remember {
        // TODO Immutable 객체로 변경하기
        mutableStateOf(
            mutableListOf(
                StayApplicationInformation("title1", "content1"),
                StayApplicationInformation("title2", "content2"),
                StayApplicationInformation("title3", "content3"),
            ),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // TODO 서버로부터 이전 신청 값 받아오기
        val lastAppliedItem = ""

        var selectedItem by remember { mutableStateOf(lastAppliedItem) }
        var expandedItem by remember { mutableStateOf("") }
        var isButtonVisible by remember { mutableStateOf(false) }

        if (lastAppliedItem.isNotBlank()) {
            isButtonVisible = true
        }

        // TODO string resource 로 빼기 (conflict 방지)
        TopBar(title = "잔류 신청") {
            navController.popBackStack()
        }

        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {

            // TODO 서버로부터 받은 값 가공하여 넣기
            val noticeContent = ""

            Spacer(modifier = Modifier.height(8.dp))

            if (noticeContent.isNotBlank()) {
                FloatingNotice(content = noticeContent)
                Spacer(modifier = Modifier.height(30.dp))
            }

            Column(
                modifier = Modifier.fillMaxHeight(0.9f)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    itemsIndexed(
                        items = stayApplicationItems,
                    ) { index, item ->
                        item.run {

                            val isItemExpanded = (expandedItem == stayApplicationTitle)
                            val isItemSelected = (selectedItem == stayApplicationTitle)

                            val borderColor = if (isItemSelected) {
                                DormColor.DormPrimary
                            } else {
                                DormColor.Gray100
                            }

                            val rotationState by animateFloatAsState(
                                targetValue = if (isItemExpanded) {
                                    90f
                                } else {
                                    270f
                                }
                            )
                            // TODO 서버 Response 값에 맞게 값들들 수정
                            //Spacer(modifier = Modifier.height(20.dp))
                            ApplicationCard(
                                title = stayApplicationTitle,
                                content = staApplicationContent,
                                borderColor = borderColor,
                                rotationState = rotationState,
                                onSelect = {
                                    selectedItem = stayApplicationTitle
                                    isButtonVisible = true
                                },
                                onUnfold = {
                                    expandedItem = if (isItemExpanded) {
                                        ""
                                    } else {
                                        stayApplicationTitle
                                    }
                                },
                                isContentVisible = isItemExpanded,
                                hasLastApplied = (lastAppliedItem == stayApplicationTitle),
                            )
                            if (stayApplicationItems.size == index + 1) {
                                Spacer(modifier = Modifier.height(30.dp))
                            }
                        }
                    }
                }
            }

            // TODO string resource 로 빼주기 (conflict 방지)
            val buttonText = if (selectedItem == lastAppliedItem) {
                "신청 완료"
            } else if (lastAppliedItem.isBlank()) {
                "$selectedItem 신청하기"
            } else {
                "${selectedItem}로 변경하기"
            }

            if (isButtonVisible) {
                DormContainedLargeButton(
                    text = buttonText,
                    color = DormButtonColor.Blue,
                    enabled = (selectedItem != lastAppliedItem),
                ) {
                    // TODO 항목 신청 로직 구현
                }
            }
        }
    }
}

@Composable
fun ApplicationCard(
    title: String,
    content: String,
    borderColor: Color,
    rotationState: Float,
    onSelect: () -> Unit,
    onUnfold: () -> Unit,
    isContentVisible: Boolean,
    hasLastApplied: Boolean = false,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeightIn(60.dp)
            .dormShadow(
                color = DormColor.Gray500,
                offsetY = 1.dp,
            )
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp),
            )
            .background(color = DormColor.Gray100)
            .dormClickable { onSelect() },
    ) {
        Spacer(
            modifier = Modifier.height(14.dp)
        )
        Box(
            modifier = Modifier.height(32.dp),
            contentAlignment = Alignment.CenterEnd,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.96f)
                    .padding(start = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SubTitle2(text = title)
                // TODO 신청 메인 화면에 동일 컴포넌트가 있음 -> 따로 빼주기
                if (hasLastApplied) {
                    Spacer(modifier = Modifier.width(20.dp))
                    Box(
                        modifier = Modifier
                            .size(
                                width = 92.dp,
                                height = 34.dp,
                            )
                            .background(
                                color = DormColor.Lighten200,
                                shape = RoundedCornerShape(100)
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        // TODO string resource 로 빼기 (conflict 방지)
                        ButtonText(
                            text = "신청 완료",
                            color = DormColor.DormPrimary,
                        )
                    }
                }
            }
            Image(
                painterResource(id = DormIcon.Backward.drawableId),
                contentDescription = null,
                modifier = Modifier
                    .dormClickable(
                        rippleEnabled = false,
                    ) {
                        onUnfold()
                    }
                    .rotate(rotationState),
            )
        }
        AnimatedVisibility(
            visible = isContentVisible,
        ) {
            Text(
                modifier = Modifier.padding(
                    top = 30.dp,
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 20.dp,
                ),
                text = content,
            )
        }
    }
}