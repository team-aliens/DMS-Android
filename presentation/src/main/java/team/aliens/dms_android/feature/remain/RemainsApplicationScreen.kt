package team.aliens.dms_android.feature.remain

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale
import java.util.UUID
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.component.LastAppliedItem
import team.aliens.design_system.extension.Space
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.ToastType
import team.aliens.design_system.typography.Caption
import team.aliens.design_system.typography.Title3
import team.aliens.dms_android.component.FloatingNotice
import team.aliens.dms_android.feature.application.rememberDmsAppState
import team.aliens.dms_android.util.TopBar
import team.aliens.presentation.R

@Stable
val ApplicationCardRadius = RoundedCornerShape(
    size = 10.dp,
)

@Composable
internal fun RemainsApplicationScreen(
    navController: NavController,
    remainsApplicationViewModel: _RemainsApplicationViewModel = hiltViewModel(),
) {

    val appState = rememberDmsAppState()

    val state = remainsApplicationViewModel.uiState.collectAsState().value

    val lastAppliedItemTitle = state.currentAppliedRemainsOption

    var selectedItemId by remember {
        mutableStateOf("")
    }

    var currentSelectedItemTitle by remember {
        mutableStateOf("")
    }

    var buttonEnabled by remember {
        mutableStateOf(false)
    }

    buttonEnabled = currentSelectedItemTitle != lastAppliedItemTitle

    LaunchedEffect(Unit) {
        with(remainsApplicationViewModel) {
            onEvent(
                event = _RemainsApplicationEvent.FetchRemainsOptions,
            )
            onEvent(
                event = _RemainsApplicationEvent.FetchAvailableRemainsTime,
            )
            onEvent(
                event = _RemainsApplicationEvent.FetchCurrentAppliedRemainsOption,
            )

            uiState.collect {

                val errorMessage = it.remainsApplicationErrorMessage

                if (errorMessage.isNotEmpty()) {
                    appState.toastManager.setMessage(
                        message = errorMessage,
                        ToastType.ERROR,
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopBar(
            title = stringResource(id = R.string.remain_apply),
        )
        Box(
            modifier = Modifier
                .fillMaxHeight(0.95f)
                .padding(
                    top = 12.dp,
                    start = 16.dp,
                    end = 16.dp,
                ),
            contentAlignment = Alignment.BottomCenter,
        ) {

            val context = LocalContext.current

            Column(
                modifier = Modifier.fillMaxSize(),
            ) {

                val remainApplicationTime = state.remainsApplicationTimeOutput

                if (remainApplicationTime.startTime.isNotBlank()) {
                    FloatingNotice(
                        content = setRemainApplicationAvailableTime(
                            startDayOfWeek = remainApplicationTime.startDayOfWeek,
                            startTime = remainApplicationTime.startTime,
                            endDayOfWeek = remainApplicationTime.endDayOfWeek,
                            endTime = remainApplicationTime.endTime,
                            context = context,
                        ),
                    )
                }
                Space(space = 12.dp)
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(
                        bottom = 48.dp,
                    )
                ) {
                    itemsIndexed(
                        items = state.remainsOptionsOutput.remainOptions,
                    ) { _, item ->
                        ApplicationCard(
                            text = item.title,
                            content = item.description,
                            isSelected = selectedItemId == item.id.toString(),
                            onSelect = {
                                selectedItemId = item.id.toString()
                                currentSelectedItemTitle = item.title
                            },
                            isLastApplied = lastAppliedItemTitle == item.title,
                        )
                    }
                }
            }

            if (selectedItemId.isNotEmpty()) {
                DormContainedLargeButton(
                    text = setButtonTextByRemainsState(
                        buttonEnabled = buttonEnabled,
                        lastAppliedItemTitle = lastAppliedItemTitle,
                        currentSelectedItemTitle = currentSelectedItemTitle,
                        context = context,
                    ),
                    color = DormButtonColor.Blue,
                    enabled = buttonEnabled
                ) {
                    remainsApplicationViewModel.onEvent(
                        event = _RemainsApplicationEvent.UpdateRemainsOption(
                            remainsOptionId = UUID.fromString(selectedItemId),
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun ApplicationCard(
    text: String,
    content: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
    isLastApplied: Boolean,
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    val rotate by animateFloatAsState(
        targetValue = if (expanded) 90f
        else 270f
    )

    val borderColor = if (isSelected) DormTheme.colors.primary
    else DormTheme.colors.surface

    val textColor = if (isSelected) DormTheme.colors.primary
    else DormTheme.colors.onSurface

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(
                minHeight = 60.dp,
            )
            .clip(
                shape = ApplicationCardRadius,
            )
            .dormShadow(
                color = DormTheme.colors.primaryVariant,
                offsetX = 8.dp,
                offsetY = 8.dp,
            )
            .background(
                color = DormTheme.colors.surface,
                shape = ApplicationCardRadius,
            )
            .dormClickable {
                onSelect()
            }
            .border(
                width = 1.dp,
                color = borderColor,
                shape = ApplicationCardRadius,
            )
            .padding(
                vertical = 16.dp,
                horizontal = 20.dp,
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Title3(
                    text = text,
                    color = textColor,
                )
                Space(space = 20.dp)
                if (isLastApplied) {
                    LastAppliedItem(
                        text = stringResource(id = R.string.application_completed),
                    )
                }
            }
            Image(
                modifier = Modifier
                    .rotate(
                        degrees = rotate,
                    )
                    .dormClickable(
                        rippleEnabled = false,
                    ) {
                        expanded = !expanded
                    },
                painter = painterResource(id = DormIcon.Backward.drawableId),
                contentDescription = null,
            )
        }
        AnimatedVisibility(
            visible = expanded,
        ) {
            Caption(
                modifier = Modifier.padding(
                    top = 24.dp,
                ),
                text = content,
            )
        }
    }
}

private fun setRemainApplicationAvailableTime(
    startDayOfWeek: DayOfWeek,
    startTime: String,
    endDayOfWeek: DayOfWeek,
    endTime: String,
    context: Context,
): String {
    return context.getString(
        R.string.remain_time_application_available,
        "${
            startDayOfWeek.getDisplayName(
                TextStyle.SHORT,
                Locale.KOREA,
            )
        } $startTime ~ ${
            endDayOfWeek.getDisplayName(
                TextStyle.SHORT,
                Locale.KOREA,
            )
        } $endTime"
    )
}

private fun setButtonTextByRemainsState(
    buttonEnabled: Boolean,
    lastAppliedItemTitle: String,
    currentSelectedItemTitle: String,
    context: Context,
): String = if (!buttonEnabled) context.getString(R.string.application_completed)
else if (lastAppliedItemTitle != currentSelectedItemTitle) context.getString(
    R.string.remain_change_to,
    currentSelectedItemTitle
)
else context.getString(R.string.remain_do_apply, currentSelectedItemTitle)