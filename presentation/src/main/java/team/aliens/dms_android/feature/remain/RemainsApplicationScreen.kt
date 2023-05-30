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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import team.aliens.dms_android.feature.application.DmsAppState
import team.aliens.dms_android.util.TopBar
import team.aliens.domain.exception.RemoteException
import team.aliens.presentation.R

@Stable
private val ApplicationCardRadius = RoundedCornerShape(
    size = 10.dp,
)

@Composable
internal fun RemainsApplicationScreen(
    navController: NavController,
    appState: DmsAppState,
    remainsApplicationViewModel: RemainsApplicationViewModel = hiltViewModel(),
) {

    val state = remainsApplicationViewModel.uiState.collectAsState()

    val context = LocalContext.current

    val lastAppliedItemTitle = state.value.currentAppliedRemainsOption

    val currentSelectedItemTitle = state.value.remainsOptionTitle

    val onRemainsApplicationClicked = {
        remainsApplicationViewModel.onEvent(
            event = RemainsApplicationUiEvent.UpdateUiRemainsOption,
        )
    }

    LaunchedEffect(Unit) {
        remainsApplicationViewModel.uiState.collect {

            val error = it.error

            if(error != null) {
                appState.toastManager.setMessage(
                    message = context.getString(
                        when (error) {
                            is RemoteException.BadRequest -> R.string.error_bad_request
                            is RemoteException.Unauthorized -> R.string.error_unauthorized
                            is RemoteException.Forbidden -> R.string.error_forbidden
                            is RemoteException.NotFound -> R.string.error_not_found
                            is RemoteException.TooManyRequests -> R.string.error_too_many_request
                            is RemoteException.InternalServerError -> R.string.error_internal_server
                            else -> R.string.error_unknown
                        }
                    ),
                    toastType = ToastType.ERROR,
                )
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopBar(
            title = stringResource(id = R.string.remains_apply),
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

            Column(
                modifier = Modifier.fillMaxSize(),
            ) {

                val remainApplicationTime = state.value.remainsApplicationTimeOutput

                if (remainApplicationTime != null) {
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

                    val remainsOptions = state.value.remainsOptions

                    items(
                        count = remainsOptions.size,
                    ) { index ->

                        val item = remainsOptions[index]

                        ApplicationCard(
                            text = item.title,
                            content = item.description,
                            isSelected = remainsApplicationViewModel.getRemainsOptionItemState(
                                remainsOptionId = item.id,
                            ),
                            onSelect = {
                                remainsApplicationViewModel.onEvent(
                                    event = RemainsApplicationUiEvent.SetSelectedRemainsOption(
                                        remainsOptionItemIndex = index,
                                    )
                                )
                            },
                            isLastApplied = lastAppliedItemTitle == item.title,
                        )
                    }
                }
            }

            val buttonEnabled = state.value.remainsApplicationButtonEnabled

            if (state.value.remainsOptionId != null) {
                DormContainedLargeButton(
                    text = setButtonTextByRemainsState(
                        buttonEnabled = buttonEnabled,
                        lastAppliedItemTitle = lastAppliedItemTitle,
                        currentSelectedItemTitle = currentSelectedItemTitle,
                        context = context,
                    ),
                    color = DormButtonColor.Blue,
                    enabled = buttonEnabled,
                    onClick = onRemainsApplicationClicked,
                )
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
            .dormShadow(
                color = DormTheme.colors.primaryVariant,
                offsetY = 8.dp,
            )
            .clip(
                shape = ApplicationCardRadius,
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
                horizontal = 24.dp,
            ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Title3(
                    modifier = Modifier.padding(
                        vertical = 2.dp,
                    ),
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
        R.string.remains_available_remains_application_time_is,
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
): String {
    return if (!buttonEnabled) context.getString(R.string.application_completed)
    else if (lastAppliedItemTitle.isBlank()) context.getString(
        R.string.remains_do_apply,
        currentSelectedItemTitle
    )
    else context.getString(
        R.string.remains_change_to,
        currentSelectedItemTitle,
    )
}