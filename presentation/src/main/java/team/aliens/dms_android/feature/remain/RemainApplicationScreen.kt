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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.SubTitle2
import team.aliens.dms_android.component.FloatingNotice
import team.aliens.dms_android.component.LastAppliedItem
import team.aliens.dms_android.util.DayOfWeek
import team.aliens.dms_android.util.TopBar
import team.aliens.dms_android.viewmodel.remain.RemainApplicationViewModel
import team.aliens.dms_android.viewmodel.remain.RemainApplicationViewModel.Event
import team.aliens.domain.entity.remain.RemainOptionsEntity
import team.aliens.presentation.R

@Composable
fun RemainApplicationScreen(
    navController: NavController,
    remainApplicationViewModel: RemainApplicationViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    var lastAppliedItem by remember { mutableStateOf("") }
    var noticeContent by remember { mutableStateOf("") }

    val remainOptions = remember { mutableStateListOf<RemainOptionsEntity.RemainOptionEntity>() }

    val toast = rememberToast()
    LaunchedEffect(key1 = remainApplicationViewModel) {
        with(remainApplicationViewModel) {
            fetchAvailableRemainTime()
            fetchRemainOptions()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LaunchedEffect(Unit) {
            remainApplicationViewModel.remainApplicationEffect.collect {
                when (it) {
                    is Event.UpdateRemainOption -> {
                        toast(context.getString(R.string.CompleteApply))
                    }
                    is Event.AvailableRemainTime -> {
                        noticeContent = it.availableRemainTimeEntity.run {
                            "잔류 신청 시간은 ${DayOfWeek.valueOf(startDayOfWeek.toString()).week}" +
                                    " ${startsAt.split(':')[0]}:${startsAt.split(':')[1]} ~" +
                                    " ${DayOfWeek.valueOf(endDayOfWeek.toString()).week} " +
                                    " ${endsAt.split(':')[0]}:${endsAt.split(':')[1]}" +
                                    " 까지 입니다."
                        }
                    }
                    is Event.RemainOptions -> {
                        lastAppliedItem = it.remainOptionsEntity.selectedOption ?: ""
                        remainOptions.addAll(it.remainOptionsEntity.remainOptionEntities)
                    }
                    is Event.BadRequestException -> {
                        toast(context.getString(R.string.BadRequest))
                    }
                    is Event.NotFoundException -> {
                        toast(context.getString(R.string.NotFound))
                    }
                    is Event.TooManyRequestException -> {
                        toast(context.getString(R.string.TooManyRequest))
                    }
                    is Event.ServerException -> {
                        toast(context.getString(R.string.ServerException))
                    }
                    is Event.UnauthorizedException -> {
                        toast(context.getString(R.string.UnAuthorized))
                    }
                    else -> toast(context.getString(R.string.UnKnownException))
                }
            }
        }

        var selectedItem by remember { mutableStateOf(lastAppliedItem) }
        var expandedItem by remember { mutableStateOf("") }
        var isButtonVisible by remember { mutableStateOf(false) }

        TopBar(title = stringResource(id = R.string.RemainApplication)) {
            navController.popBackStack()
        }

        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            if (noticeContent.isNotBlank()) {
                FloatingNotice(content = noticeContent)
                Spacer(modifier = Modifier.height(10.dp))
            }
            Column(
                modifier = Modifier.fillMaxHeight(0.9f)
            ) {
                LazyColumn {
                    itemsIndexed(
                        items = remainOptions,
                    ) { index, item ->
                        item.run {
                            val isItemExpanded = (expandedItem == item.title)
                            val isItemSelected = (selectedItem == item.title)

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
                            Spacer(modifier = Modifier.height(20.dp))
                            ApplicationCard(
                                title = item.title,
                                content = item.description,
                                borderColor = borderColor,
                                rotationState = rotationState,
                                onSelect = {
                                    selectedItem = item.title
                                    remainApplicationViewModel.setRemainOption(
                                        remainOptionId = item.id,
                                    )
                                    isButtonVisible = true
                                },
                                onUnfold = {
                                    expandedItem = if (isItemExpanded) {
                                        ""
                                    } else {
                                        item.title
                                    }
                                },
                                isContentVisible = isItemExpanded,
                                hasLastApplied = (lastAppliedItem == item.title),
                            )
                            if (remainOptions.size == index + 1) {
                                Spacer(modifier = Modifier.height(30.dp))
                            }
                        }
                    }
                }
            }

            val buttonText = if (selectedItem == lastAppliedItem) {
                stringResource(id = R.string.CompleteApplication)
            } else if (lastAppliedItem.isBlank()) {
                "$selectedItem ${stringResource(id = R.string.DoApply)}"
            } else {
                "${selectedItem}${stringResource(id = R.string.ChangeTo)}"
            }

            if (isButtonVisible) {
                DormContainedLargeButton(
                    text = buttonText,
                    color = DormButtonColor.Blue,
                    enabled = (selectedItem != lastAppliedItem),
                ) {
                    remainApplicationViewModel.updateRemainOption()
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
    hasLastApplied: Boolean,
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
                if (hasLastApplied) {
                    Spacer(modifier = Modifier.width(20.dp))
                    LastAppliedItem(stringResource(id = R.string.CompleteApplication))
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