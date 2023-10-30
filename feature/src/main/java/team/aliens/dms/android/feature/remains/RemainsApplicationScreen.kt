package team.aliens.dms.android.feature.remains

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.ui.composable.FloatingNotice
import team.aliens.dms.android.core.ui.horizontalPadding
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
    DmsScaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = { Text(text = stringResource(id = R.string.point_history)) },
                navigationIcon = {
                    IconButton(onClick = navigator::popBackStack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(id = R.string.top_bar_back_button),
                        )
                    }
                },
            )
        }
    ) { padValues ->
        Column(
            modifier = Modifier.padding(padValues),
        ) {
            FloatingNotice(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                text = stringResource(
                    id = R.string.format_remains_available_remains_application_time,
                    "화",
                    "12:30",
                    "목",
                    "15:50",
                ),
            )
            RemainsOptionList(
                modifier = Modifier.fillMaxWidth(),
                options = listOf(),
            )
        }
    }
}

@Composable
private fun RemainsOptionList(
    modifier: Modifier = Modifier,
    options:List<RemainsOption>
) {
    LazyColumn(
        modifier = modifier,
    ){

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
