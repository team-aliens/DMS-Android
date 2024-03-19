package team.aliens.dms.android.feature.outing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import org.threeten.bp.DayOfWeek
import team.aliens.dms.android.core.designsystem.AlertDialog
import team.aliens.dms.android.core.designsystem.Button
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.TextButton
import team.aliens.dms.android.core.designsystem.TextField
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.composable.FloatingNotice
import team.aliens.dms.android.core.ui.endPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.outing.navigation.OutingNavigator
import team.aliens.dms.android.shared.date.util.now

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun OutingApplicationScreen(
    navigator: OutingNavigator,
    viewModel: OutingViewModel,
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    val (shouldShowStartTimePicker, onChangeShouldShowStartTimePicker) = remember {
        mutableStateOf(false)
    }
    val startTimePickerState = rememberTimePickerState()

    if (shouldShowStartTimePicker) {
        AlertDialog(
            confirmButton = {
                TextButton(onClick = { onChangeShouldShowStartTimePicker(false) }) {
                    Text(text = stringResource(id = R.string.close))
                }
            },
            onDismissRequest = { onChangeShouldShowStartTimePicker(false) },
            text = { TimePicker(state = startTimePickerState) },
        )
    }

    val (shouldShowEndTimePicker, onChangeShouldShowEndTimePicker) = remember {
        mutableStateOf(false)
    }
    val endTimePickerState = rememberTimePickerState()

    if (shouldShowEndTimePicker) {
        AlertDialog(
            confirmButton = {
                TextButton(onClick = { onChangeShouldShowEndTimePicker(false) }) {
                    Text(text = stringResource(id = R.string.close))
                }
            },
            onDismissRequest = { onChangeShouldShowEndTimePicker(false) },
            text = { TimePicker(state = endTimePickerState) },
        )
    }

    Scaffold(
        topBar = {
            DmsTopAppBar(
                title = { Text(text = stringResource(id = R.string.outing_application)) },
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
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues),
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            if (uiState.outingApplicationTime != null) {
                val startTime = uiState.outingApplicationTime!!.startTime
                val endTime = uiState.outingApplicationTime!!.endTime
                FloatingNotice(
                    text = stringResource(
                        id = R.string.outing_format_application_time,
                        startTime.hour,
                        startTime.minute,
                        endTime.hour,
                        endTime.minute,
                    ),
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace),
            ) {
                Spacer(modifier = Modifier.startPadding())
                OutingInputDefaults.Indicator()
                Text(
                    text = stringResource(id = R.string.outing_is_required),
                    style = DmsTheme.typography.caption,
                )
                Spacer(modifier = Modifier.endPadding())
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace),
            ) {
                val capturedNow = remember { now }
                Text(
                    modifier = Modifier.startPadding(),
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.outing_date),
                )
                Text(
                    modifier = Modifier.endPadding(),
                    text = stringResource(
                        id = R.string.format_date_yyyy_mm_dd_day_of_week,
                        capturedNow.year,
                        capturedNow.month.value,
                        capturedNow.dayOfMonth,
                        capturedNow.dayOfWeek.text,
                    ),
                )
            }
            Spacer(modifier = Modifier.height(DefaultVerticalSpace))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace),
            ) {
                OutingInput(
                    modifier = Modifier
                        .weight(1f)
                        .startPadding(),
                    text = { Text(text = stringResource(id = R.string.outing_start_time)) },
                    indicator = { OutingInputDefaults.Indicator() },
                ) {
                    TextField(
                        trailingIcon = {
                            IconButton(
                                onClick = { onChangeShouldShowStartTimePicker(true) },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_next),
                                    contentDescription = stringResource(
                                        id = R.string.outing_application_select_outing_time,
                                    ),
                                    tint = DmsTheme.colorScheme.icon,
                                )
                            }
                        },
                        value = stringResource(
                            id = R.string.outing_format_duration_h_m,
                            startTimePickerState.hour,
                            startTimePickerState.minute,
                        ),
                        onValueChange = {},
                        readOnly = true,
                    )
                }
                OutingInput(
                    modifier = Modifier
                        .weight(1f)
                        .endPadding(),
                    text = { Text(text = stringResource(id = R.string.outing_end_time)) },
                    indicator = { OutingInputDefaults.Indicator() },
                ) {
                    TextField(
                        trailingIcon = {
                            IconButton(
                                onClick = { onChangeShouldShowEndTimePicker(true) },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_next),
                                    contentDescription = stringResource(
                                        id = R.string.outing_application_select_outing_time,
                                    ),
                                    tint = DmsTheme.colorScheme.icon,
                                )
                            }
                        },
                        value = stringResource(
                            id = R.string.outing_format_duration_h_m,
                            endTimePickerState.hour,
                            endTimePickerState.minute,
                        ),
                        onValueChange = {},
                        readOnly = true,
                    )
                }
            }

            val (outingTypeMenuExpanded, onChangeOutingTypeMenuExpanded) = remember {
                mutableStateOf(false)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace),
            ) {
                OutingInput(
                    modifier = Modifier
                        .weight(0.4f)
                        .startPadding(),
                    text = { Text(text = stringResource(id = R.string.outing_type)) },
                    indicator = { OutingInputDefaults.Indicator() },
                ) {
                    ExposedDropdownMenuBox(
                        expanded = outingTypeMenuExpanded,
                        onExpandedChange = onChangeOutingTypeMenuExpanded,
                    ) {
                        TextField(
                            modifier = Modifier.menuAnchor(),
                            value = uiState.selectedOutingType
                                ?: stringResource(id = R.string.outing_select_outing_type),
                            onValueChange = {/* explicit blank */ },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = outingTypeMenuExpanded)
                            },
                            readOnly = true,
                        )
                        ExposedDropdownMenu(
                            expanded = outingTypeMenuExpanded,
                            onDismissRequest = { onChangeOutingTypeMenuExpanded(false) },
                        ) {
                            if (uiState.outingTypes != null) {
                                uiState.outingTypes!!.forEach { outingType ->
                                    DropdownMenuItem(
                                        text = { Text(text = outingType) },
                                        onClick = {
                                            viewModel.postIntent(
                                                OutingIntent.UpdateSelectedOutingType(outingType),
                                            )
                                        },
                                    )
                                }
                            } else {
                                DropdownMenuItem(
                                    text = {
                                        Text(text = stringResource(id = R.string.outing_error_there_is_no_outing_types))
                                    },
                                    onClick = { /* explicit blank */ },
                                )
                            }
                        }
                    }
                }
                OutingInput(
                    modifier = Modifier
                        .weight(0.6f)
                        .endPadding(),
                    text = { Text(text = stringResource(id = R.string.outing_companion_names)) },
                ) {
                    TextField(
                        value = stringResource(id = R.string.outing_add_companions),
                        onValueChange = { /* explicit blank */ },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    painter = painterResource(id = team.aliens.dms.android.core.designsystem.R.drawable.ic_plus),
                                    contentDescription = stringResource(id = R.string.outing_add_companions),
                                )
                            }
                        },
                    )
                }
            }

            OutingInput(
                modifier = Modifier
                    .weight(1f)
                    .horizontalPadding(),
            ) {
                var a by remember {
                    mutableStateOf("")
                }
                TextField(
                    modifier = Modifier.fillMaxSize(),
                    value = a,
                    onValueChange = { a = it },
                    singleLine = false,
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .startPadding()
                    .endPadding()
                    .bottomPadding(),
                onClick = { /*TODO*/ },
            ) {
                Text(text = stringResource(id = R.string.outing_do_application))
            }
        }
    }
}

@Composable
private fun OutingInput(
    modifier: Modifier = Modifier,
    text: (@Composable () -> Unit)? = null,
    indicator: (@Composable () -> Unit)? = null,
    input: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
    ) {
        Row {
            if (text != null) {
                text()
            }
            if (indicator != null) {
                indicator()
            }
        }
        input()
    }
}

object OutingInputDefaults {

    val IndicatorSize = DpSize(
        width = 4.dp,
        height = 4.dp,
    )

    val IndicatorColor: Color
        @Composable get() = DmsTheme.colorScheme.primary

    @Composable
    fun Indicator(
        size: DpSize = IndicatorSize,
        color: Color = IndicatorColor,
    ) = Box(
        modifier = Modifier.padding(4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .background(
                    color = color,
                    shape = CircleShape,
                ),
        )
    }
}

// TODO: move to :core:ui
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
