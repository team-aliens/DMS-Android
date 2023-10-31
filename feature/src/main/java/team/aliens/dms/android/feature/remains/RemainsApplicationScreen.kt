package team.aliens.dms.android.feature.remains

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import org.threeten.bp.DayOfWeek
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.ShadowDefaults
import team.aliens.dms.android.core.designsystem.clickable
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.composable.FloatingNotice
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.data.remains.model.RemainsOption
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.remains.navigator.RemainsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun RemainsApplicationScreen(
    modifier: Modifier = Modifier,
    navigator: RemainsNavigator,
) {
    val viewModel: RemainsApplicationViewModel = hiltViewModel()
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    DmsScaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = { Text(text = stringResource(id = R.string.remains_application)) },
                navigationIcon = {
                    IconButton(onClick = navigator::popBackStack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(id = R.string.top_bar_back_button),
                        )
                    }
                },
            )
        },
    ) { padValues ->
        Column(
            modifier = Modifier.padding(padValues),
        ) {
            // TODO
            uiState.remainsApplicationTime?.let { applicationTime ->
                FloatingNotice(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding(),
                    text = stringResource(
                        id = R.string.format_remains_available_remains_application_time,
                        applicationTime.startDayOfWeek.text,
                        applicationTime.startTime,
                        applicationTime.endDayOfWeek.text,
                        applicationTime.endTime,
                    ),
                )
            }
            RemainsOptionList(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                options = uiState.remainsOptions,
                selectedRemainsOption = uiState.selectedRemainsOption,
                onRemainsOptionSelected = { index ->
                    viewModel.postIntent(RemainsApplicationIntent.UpdateSelectedRemainsOption(index))
                },
            )
        }
    }
}

// TODO move to util in core-ui
@Stable
private val DayOfWeek.text: String
    @Composable inline get() = stringResource(
        id = when (this) {
            DayOfWeek.MONDAY -> R.string.monday_abb
            DayOfWeek.TUESDAY -> R.string.tuesday_abb
            DayOfWeek.WEDNESDAY -> R.string.wednesday_abb
            DayOfWeek.THURSDAY -> R.string.thursday_abb
            DayOfWeek.FRIDAY -> R.string.friday_abb
            DayOfWeek.SATURDAY -> R.string.saturday_abb
            DayOfWeek.SUNDAY -> R.string.sunday_abb
        },
    )

@Composable
private fun RemainsOptionList(
    modifier: Modifier = Modifier,
    options: List<RemainsOption>,
    selectedRemainsOption: RemainsOption?,
    onRemainsOptionSelected: (RemainsOption) -> Unit,
) {
    val (expandedIndex, onExpandedIndexChange) = remember { mutableStateOf<Int?>(null) }
    LazyColumn(
        modifier = modifier,
    ) {
        itemsIndexed(options) { index, option ->
            val expanded = index == expandedIndex
            RemainsOptionCard(
                modifier = Modifier.fillMaxWidth(),
                remainsOption = option,
                selected = option.id == selectedRemainsOption?.id,
                expanded = expanded,
                onExpand = {
                    onExpandedIndexChange(
                        if (expanded) {
                            null
                        } else {
                            index
                        }
                    )
                },
                onClick = { onRemainsOptionSelected(option) },
            )
        }
    }
}

@Composable
private fun RemainsOptionCard(
    modifier: Modifier = Modifier,
    remainsOption: RemainsOption,
    selected: Boolean,
    expanded: Boolean,
    onExpand: () -> Unit,
    onClick: () -> Unit,
) {
    val rotate by animateFloatAsState(
        targetValue = if (expanded) {
            180f
        } else {
            0f
        },
        label = "rotate",
    )
    Card(
        modifier = modifier
            .horizontalPadding()
            .verticalPadding(PaddingDefaults.ExtraSmall),
        shape = DmsTheme.shapes.surfaceSmall,
        colors = CardDefaults.cardColors(
            containerColor = DmsTheme.colorScheme.surface,
            contentColor = DmsTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation =
            if (selected) {
                ShadowDefaults.MediumElevation
            } else {
                ShadowDefaults.SmallElevation
            },
        ),
        border = if (selected) {
            BorderStroke(
                width = 1.dp,
                color = DmsTheme.colorScheme.primary,
            )
        } else {
            null
        },
    ) {
        Column(
            modifier = Modifier.clickable(onClick = onClick),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .startPadding()
                        .verticalPadding(),
                    text = remainsOption.title,
                    style = DmsTheme.typography.title2,
                    color = if (selected) {
                        DmsTheme.colorScheme.primary
                    } else {
                        DmsTheme.colorScheme.onSurface
                    },
                )
                IconButton(onClick = onExpand) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .rotate(rotate),
                        painter = painterResource(
                            id = team.aliens.dms.android.core.designsystem.R.drawable.ic_down
                        ),
                        contentDescription = stringResource(id = R.string.remains_expand_remains_option_card),
                    )
                }
            }
            AnimatedVisibility(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                visible = expanded,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .bottomPadding(),
                    text = remainsOption.description,
                    style = DmsTheme.typography.body3,
                    color = DmsTheme.colorScheme.surfaceVariant,
                )
            }
        }
    }
}

/*

@Composable
private fun RemainsApplicationTime.toFormattedString(): String {
    val startAt = "${startDayOfWeek.displayName} $startTime"
    val endAt = "${endDayOfWeek.displayName} $endTime"

    return String.format(
        stringResource(R.string.remains_available_remains_application_time_is),
        "$startAt ~ $endAt",
    )
}

private val DayOfWeek.displayName: String
    @Composable get() = stringResource(
        when (this) {
            DayOfWeek.MONDAY -> R.string.monday_abb
            DayOfWeek.TUESDAY -> R.string.tuesday_abb
            DayOfWeek.WEDNESDAY -> R.string.wednesday_abb
            DayOfWeek.THURSDAY -> R.string.thursday_abb
            DayOfWeek.FRIDAY -> R.string.friday_abb
            DayOfWeek.SATURDAY -> R.string.saturday_abb
            DayOfWeek.SUNDAY -> R.string.sunday_abb
        },
    )

@Composable
private fun RemainsApplicationTimeCard(
    remainsApplicationTime: RemainsApplicationTime,
) {
    FloatingNotice(
        modifier = Modifier.padding(
            top = 8.dp,
            bottom = 30.dp,
            start = 16.dp,
            end = 16.dp,
        ),
        text = remainsApplicationTime.toFormattedString(),
    )
}

@Composable
private fun RemainsItems(
    modifier: Modifier = Modifier,
    remainsOptions: List<RemainsOption>,
    selectedRemainsOptionId: UUID?,
    onRemainsOptionSelected: (RemainsOption) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
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
                color = DmsTheme.colors.primaryVariant,
                offsetY = 8.dp,
            )
            .clip(
                shape = RoundedCornerShape(10.dp),
            )
            .background(
                color = DmsTheme.colors.surface,
                shape = RoundedCornerShape(10.dp),
            )
            .dormClickable {
                onClick(remainsOption)
            }
            .border(
                width = 1.dp,
                color = if (selected) DmsTheme.colors.primary else Color.Transparent,
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
                color = if (selected) DmsTheme.colors.primary else DmsTheme.colors.onSurface,
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
*/
