package team.aliens.dms_android.feature.remains

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.format.TextStyle
import java.util.Locale
import java.util.UUID
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.component.LastAppliedItem
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.modifier.dormGradientBackground
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Caption
import team.aliens.design_system.typography.Title3
import team.aliens.dms_android.component.FloatingNotice
import team.aliens.dms_android.component.listFadeBrush
import team.aliens.dms_android.util.TopBar
import team.aliens.domain.model.remains.RemainsApplicationTime
import team.aliens.domain.model.remains.RemainsOption
import team.aliens.presentation.R

@Composable
internal fun RemainsApplicationScreen(
    onPrevious: () -> Unit,
    remainsApplicationViewModel: RemainsApplicationViewModel = hiltViewModel(),
) {
    val uiState by remainsApplicationViewModel.uiState.collectAsStateWithLifecycle()
    val remainsApplicationTime = uiState.remainsApplicationTime

    val onRemainsApplicationClicked = {
        remainsApplicationViewModel.onEvent(RemainsApplicationUiEvent.UpdateRemainsOption)
    }
    val onRemainsOptionSelected = { selectedRemainsOption: RemainsOption ->
        remainsApplicationViewModel.onEvent(
            RemainsApplicationUiEvent.SelectRemainsOption(selectedRemainsOption),
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        TopBar(
            title = stringResource(R.string.remains_apply),
            onPrevious = onPrevious,
        )

        if (remainsApplicationTime != null) {
            RemainsApplicationTimeCard(remainsApplicationTime)
        }

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.BottomCenter,
        ) {
            RemainsItems(
                remainsOptions = uiState.remainsOptions,
                selectedRemainsOptionId = uiState.selectedRemainsOption?.id,
                onRemainsOptionSelected = onRemainsOptionSelected,
            )

            val applicationButtonEnabled = uiState.applicationButtonEnabled

            if (uiState.selectedRemainsOption != null) {
                DormContainedLargeButton(
                    modifier = Modifier.padding(
                        bottom = 57.dp,
                        start = 16.dp,
                        end = 16.dp,
                    ),
                    text = if (applicationButtonEnabled) String.format(
                        stringResource(R.string.application_apply_of),
                        uiState.selectedRemainsOption?.title ?: throw IllegalStateException(),
                    ) else stringResource(R.string.application_completed),
                    color = DormButtonColor.Blue,
                    enabled = applicationButtonEnabled,
                    onClick = onRemainsApplicationClicked,
                )
            }
        }
    }
}

@Composable
private fun RemainsApplicationTime.toFormattedString(): String {
    val startAt = "${startDayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)} $startTime"
    val endAt = "${endDayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)} $endTime"

    return String.format(
        stringResource(R.string.remains_available_remains_application_time_is),
        "$startAt ~ $endAt",
    )
}

@Composable
private fun RemainsApplicationTimeCard(
    remainsApplicationTime: RemainsApplicationTime,
) {
    Box(
        modifier = Modifier.padding(
            top = 8.dp,
            bottom = 30.dp,
            start = 16.dp,
            end = 16.dp,
        ),
    ) {
        FloatingNotice(
            content = remainsApplicationTime.toFormattedString(),
        )
    }
}

@Composable
private fun RemainsItems(
    remainsOptions: List<RemainsOption>,
    selectedRemainsOptionId: UUID?,
    onRemainsOptionSelected: (RemainsOption) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = 120.dp,
            ),
        ) {
            items(remainsOptions) { remainsOption ->
                RemainsOptionCard(
                    remainsOption = remainsOption,
                    selected = remainsOption.id == selectedRemainsOptionId,
                    onClick = onRemainsOptionSelected,
                    currentApplied = remainsOption.applied,
                )
            }
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(16.dp)
                .dormGradientBackground(listFadeBrush),
        )
    }
}

@Composable
private fun RemainsOptionCard(
    remainsOption: RemainsOption,
    selected: Boolean,
    onClick: (RemainsOption) -> Unit,
    currentApplied: Boolean,
) {
    var expanded by remember { mutableStateOf(false) }
    val rotate by animateFloatAsState(
        targetValue = if (expanded) 90f else 270f,
        label = "rotate",
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .dormShadow(
                color = DormTheme.colors.primaryVariant,
                offsetY = 8.dp,
            )
            .clip(
                shape = RoundedCornerShape(10.dp),
            )
            .background(
                color = DormTheme.colors.surface,
                shape = RoundedCornerShape(10.dp),
            )
            .dormClickable {
                onClick(remainsOption)
            }
            .border(
                width = 1.dp,
                color = if (selected) DormTheme.colors.primary else Color.Transparent,
                shape = RoundedCornerShape(10.dp),
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
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Title3(
                modifier = Modifier.padding(
                    vertical = 2.dp,
                ),
                text = remainsOption.title,
                color = if (selected) DormTheme.colors.primary else DormTheme.colors.onSurface,
            )
            if (currentApplied) LastAppliedItem(
                text = stringResource(R.string.application_completed),
            )
            Spacer(Modifier.weight(1f))
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
                text = remainsOption.description,
            )
        }
    }
}