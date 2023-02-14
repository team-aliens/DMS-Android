package team.aliens.dms_android.feature

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.ButtonText
import team.aliens.design_system.typography.NotoSansFamily
import team.aliens.design_system.typography.SubTitle2
import team.aliens.dms_android.util.FloatingNotice
import team.aliens.dms_android.util.TopBar
import team.aliens.presentation.R

// TODO 테스트 더미 값들
// TODO 추후 서버와 연동 과정에서 삭제하고 작업 진행

data class DummyData(
    val title: String,
    val content: String,
)

val list = listOf(
    DummyData("title1", "content1"),
    DummyData("title2", "content2"),
    DummyData("title3", "content3"),
)

@Composable
fun StayApplicationScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // TODO 서버로부터 이전 신청 값 받아오기
        val lastAppliedItem = ""

        var selectedItem by remember { mutableStateOf(lastAppliedItem) }
        var expandedItem by remember { mutableStateOf("") }

        // TODO string resource 로 빼기 (conflict 방지)
        TopBar(title = "잔류 신청")

        Spacer(modifier = Modifier.height(8.dp))

        // TODO 서버로부터 받은 값 가공하여 넣기
        FloatingNotice(content = "")

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier.fillMaxHeight(0.9f)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.Center,
            ) {
                itemsIndexed(
                    items = list,
                ) { index, item ->
                    item.run {

                        val isExpandedItem = (expandedItem == title)
                        val isSelectedItem = (selectedItem == title)

                        val borderColor = if (isSelectedItem) DormColor.DormPrimary
                        else DormColor.Gray100

                        val rotationState by animateFloatAsState(
                            targetValue = if (isExpandedItem) 90f else 270f
                        )
                        // TODO 서버 Response 값에 맞게 값들들 수정
                        Spacer(modifier = Modifier.height(20.dp))
                        ApplicationCard(
                            title = title,
                            content = content,
                            borderColor = borderColor,
                            rotationState = rotationState,
                            onItemClick = { selectedItem = title },
                            onDrawableClick = {
                                expandedItem = if (isExpandedItem) ""
                                else title
                            },
                            isContentVisible = isExpandedItem,
                            hasLastApplied = (lastAppliedItem == title),
                        )
                        if (list.size == index + 1) Spacer(modifier = Modifier.height(30.dp))
                    }
                }
            }
        }

        DormContainedLargeButton(
            // TODO string resource 로 빼주기 (conflict 방지)
            text = if(selectedItem == lastAppliedItem) "신청 취소하기"
            else "$selectedItem 신청하기",
            color =  if(selectedItem == lastAppliedItem) DormButtonColor.LightenRed
            else DormButtonColor.Blue,
        ) {
            // TODO 항목 신청 로직 구현
        }
    }
}

@Composable
fun ApplicationCard(
    title: String,
    content: String,
    borderColor: Color,
    rotationState: Float,
    onItemClick: () -> Unit,
    onDrawableClick: () -> Unit,
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
            .dormClickable { onItemClick() },
    ) {
        Spacer(
            modifier = Modifier.height(
                if (hasLastApplied) 13.dp
                else 16.dp,
            )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 24.dp)
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
            Spacer(
                modifier = Modifier.fillMaxWidth(
                    fraction = if (hasLastApplied) 0.8f
                    else 0.87f,
                )
            )
            Image(
                painterResource(id = DormIcon.Backward.drawableId),
                contentDescription = null,
                modifier = Modifier
                    .dormClickable { onDrawableClick() }
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
                fontFamily = NotoSansFamily,
            )
        }
    }
}

@Preview(
    showSystemUi = true,
)
@Composable
fun Preview() {
    StayApplicationScreen()
}