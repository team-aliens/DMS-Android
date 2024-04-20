package team.aliens.dms.android.feature.remains

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import team.aliens.dms.android.core.designsystem.ButtonDefaults
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.RoundedButton
import team.aliens.dms.android.core.designsystem.ShadowDefaults
import team.aliens.dms.android.core.designsystem.clickable
import team.aliens.dms.android.core.designsystem.VerticallyFadedLazyColumn
import team.aliens.dms.android.core.designsystem.shadow
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.composable.FloatingNotice
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.data.remains.model.RemainsOption
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.remains.navigator.RemainsNavigator
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun RemainsApplicationScreen(
    modifier: Modifier = Modifier,
    navigator: RemainsNavigator,
) {
    val viewModel: RemainsApplicationViewModel = hiltViewModel()
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = { Text(text = stringResource(id = R.string.remains_application)) },
                navigationIcon = {
                    IconButton(onClick = navigator::navigateUp) {
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
                selectedOptionId = uiState.selectedRemainsOptionId,
                onRemainsOptionSelected = { optionId ->
                    viewModel.postIntent(
                        RemainsApplicationIntent.UpdateSelectedRemainsOption(
                            optionId
                        )
                    )
                },
            )
            uiState.selectedRemainsOptionId?.let { remainsOption ->
                ContainedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding()
                        .bottomPadding(),
                    onClick = { viewModel.postIntent(RemainsApplicationIntent.UpdateRemainsOption) },
                    enabled = uiState.applicationButtonEnabled,
                ) {
                    Text(text = stringResource(id = R.string.remains_apply))
                }
            }
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
    selectedOptionId: UUID?,
    onRemainsOptionSelected: (UUID) -> Unit,
) {
    val (expandedIndex, onExpandedIndexChange) = remember { mutableStateOf<Int?>(null) }
    VerticallyFadedLazyColumn(
        modifier = modifier,
    ) {
        itemsIndexed(options) { index, option ->
            val expanded = index == expandedIndex
            RemainsOptionCard(
                modifier = Modifier.fillMaxWidth(),
                remainsOption = option,
                selected = option.id == selectedOptionId,
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
                onClick = { onRemainsOptionSelected(option.id) },
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
            .verticalPadding(PaddingDefaults.ExtraSmall)
            .shadow(),
        shape = DmsTheme.shapes.surfaceSmall,
        colors = CardDefaults.cardColors(
            containerColor = DmsTheme.colorScheme.surface,
            contentColor = DmsTheme.colorScheme.onSurface,
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
                horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace),
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
                if (remainsOption.applied) {
                    RoundedButton(
                        onClick = {},
                        colors = ButtonDefaults.roundedButtonColors(),
                        fillMinSize = false,
                        contentPadding = PaddingValues(
                            horizontal = PaddingDefaults.Medium,
                            vertical = PaddingDefaults.ExtraSmall,
                        ),
                    ) {
                        Text(text = stringResource(id = R.string.remains_applied))
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
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
