package team.aliens.dms.android.feature.remains

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.feature.remains.navigator.RemainsNavigator

@Destination
@Composable
internal fun RemainsApplicationScreen(
    modifier: Modifier = Modifier,
    navigator: RemainsNavigator,
    // remainsApplicationViewModel: RemainsApplicationViewModel = hiltViewModel(),
) {/*
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
        modifier = modifier
            .fillMaxSize()
            .background(DormTheme.colors.background),
        verticalArrangement = Arrangement.Center,
    ) {
        TopBar(
            title = stringResource(R.string.remains_apply),
            onPrevious = navigator::popBackStack,
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
    }*/
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
