package team.aliens.dms.android.feature.outing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalTime
import team.aliens.dms.android.core.designsystem.Button
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.core.designsystem.ModalBottomSheet
import team.aliens.dms.android.core.designsystem.Picker
import team.aliens.dms.android.core.designsystem.PickerState
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.ShadowDefaults
import team.aliens.dms.android.core.designsystem.TextField
import team.aliens.dms.android.core.designsystem.VerticallyFadedLazyColumn
import team.aliens.dms.android.core.designsystem.rememberPickerState
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.collectInLaunchedEffectWithLifecycle
import team.aliens.dms.android.core.ui.composable.FloatingNotice
import team.aliens.dms.android.core.ui.endPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.data.student.model.Student
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.outing.navigation.OutingNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun OutingApplicationScreen(
    navigator: OutingNavigator,
    viewModel: OutingViewModel,
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val (outingTypeMenuExpanded, onChangeOutingTypeMenuExpanded) = remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val toast = LocalToast.current

    val timeSheetState = rememberModalBottomSheetState()
    val (shouldShowTimePicker, onChangeShouldShowTimePicker) = remember {
        mutableStateOf(false)
    }
    val startHourValuesPickerState = rememberPickerState()
    val startMinuteValuesPickerState = rememberPickerState()
    val endHourValuesPickerState = rememberPickerState()
    val endMinuteValuesPickerState = rememberPickerState()

    if (shouldShowTimePicker) {
        ModalBottomSheet(
            sheetState = timeSheetState,
            onDismissRequest = { onChangeShouldShowTimePicker(false) },
            properties = ModalBottomSheetDefaults.properties(
                shouldDismissOnBackPress = false,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 320.dp),
            ) {
                TimePickerSpinner(
                    startHourValuesPickerState = startHourValuesPickerState,
                    startMinuteValuesPickerState = startMinuteValuesPickerState,
                    endHourValuesPickerState = endHourValuesPickerState,
                    endMinuteValuesPickerState = endMinuteValuesPickerState,
                    startTime = uiState.selectedOutingStartTime,
                    endTime = uiState.selectedOutingEndTime,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding()
                        .bottomPadding(),
                    onClick = {
                        viewModel.postIntent(
                            OutingIntent.UpdateOutingStartTime(
                                value = LocalTime.of(
                                    startHourValuesPickerState.selectedItem.toInt(),
                                    startMinuteValuesPickerState.selectedItem.toInt(),
                                )
                            )
                        )
                        viewModel.postIntent(
                            OutingIntent.UpdateOutingEndTime(
                                value = LocalTime.of(
                                    endHourValuesPickerState.selectedItem.toInt(),
                                    endMinuteValuesPickerState.selectedItem.toInt(),
                                )
                            )
                        )
                        scope.launch {
                            timeSheetState.hide()
                            onChangeShouldShowTimePicker(false)
                        }
                    },
                ) {
                    Text(text = stringResource(id = R.string.accept))
                }
            }
        }
    }

    val (shouldShowCompanionListDialog, onShouldShowCompanionListDialogChange) = remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()
    if (shouldShowCompanionListDialog) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { onShouldShowCompanionListDialogChange(false) },
            properties = ModalBottomSheetDefaults.properties(
                shouldDismissOnBackPress = false,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(
                        max = 640.dp,
                    ),
            ) {
                // TODO: 딜레이 서치
                val (studentFilterValue, onStudentFilterValueChange) = remember { mutableStateOf("") }
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding()
                        .topPadding(),
                    hint = { Text(text = stringResource(R.string.outing_application_search_with_number_or_name)) },
                    value = studentFilterValue,
                    onValueChange = onStudentFilterValueChange,
                )

                StudentList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 240.dp),
                    students = uiState.students?.filter {
                        it.name.contains(studentFilterValue) || it.gradeClassNumber.contains(
                            studentFilterValue
                        )
                    },
                    selectedStudents = uiState.selectedStudents,
                    onSelectStudent = { student ->
                        // TODO: Select를 Pair에 포함시켜서 탐색 시간 단축
                        viewModel.postIntent(
                            OutingIntent.SelectStudent(
                                student = student,
                                select = !uiState.selectedStudents.contains(student),
                            ),
                        )
                    },
                )
                // TODO
                Text(
                    modifier = Modifier
                        .startPadding()
                        .topPadding(),
                    text = stringResource(R.string.outing_application_selected_companions),
                    style = DmsTheme.typography.caption,
                )
                StudentList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 240.dp),
                    students = uiState.selectedStudents,
                    // TODO: students 파라미터를 List<Pair<Boolean(selected), Student>>로 변경하기
                    selectedStudents = emptyList(),
                    onSelectStudent = { student ->
                        viewModel.postIntent(
                            OutingIntent.SelectStudent(
                                student = student,
                                select = false,
                            ),
                        )
                    },
                    selectedOnly = true,
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding()
                        .bottomPadding(),
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                            onShouldShowCompanionListDialogChange(false)
                        }
                    },
                ) {
                    Text(
                        text = if (uiState.selectedStudents.isEmpty()) {
                            stringResource(id = R.string.close)
                        } else {
                            stringResource(
                                id = R.string.outing_format_select_companions,
                                uiState.selectedStudents.size,
                            )
                        },
                    )
                }
            }
        }
    }
    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            OutingSideEffect.OutingTypeNotSelected -> toast.showErrorToast(
                message = context.getString(R.string.outing_application_error_outing_type_not_selected),
            )

            OutingSideEffect.OutingApplicationTimeError -> toast.showErrorToast(
                message = context.getString(R.string.outing_application_error_outing_application_time_error),
            )

            is OutingSideEffect.OutingApplicationSuccess -> navigator.navigateUp()
            else -> {/* explicit blank */
            }
        }
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
                    modifier = Modifier.horizontalPadding(),
                    text = stringResource(
                        id = R.string.outing_format_application_time,
                        startTime,
                        endTime,
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
                Text(
                    modifier = Modifier.startPadding(),
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.outing_date),
                )
                Text(
                    modifier = Modifier.endPadding(),
                    text = stringResource(
                        id = R.string.format_date_yyyy_mm_dd_day_of_week,
                        uiState.outingDate.year,
                        uiState.outingDate.month.value,
                        uiState.outingDate.dayOfMonth,
                        uiState.outingDate.dayOfWeek.text,
                    ),
                )
            }
            Spacer(modifier = Modifier.height(DefaultVerticalSpace))
            OutingInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .startPadding()
                    .endPadding(),
                text = { Text(text = stringResource(id = R.string.outing_start_time)) },
                indicator = { OutingInputDefaults.Indicator() },
            ) {
                val startTime = remember(uiState.selectedOutingStartTime) {
                    uiState.selectedOutingStartTime
                }
                val endTime = remember(uiState.selectedOutingEndTime) {
                    uiState.selectedOutingEndTime
                }
                TextField(
                    trailingIcon = {
                        IconButton(
                            onClick = { onChangeShouldShowTimePicker(true) },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_next),
                                contentDescription = stringResource(id = R.string.outing_application_select_outing_time),
                                tint = DmsTheme.colorScheme.icon,
                            )
                        }
                    },
                    value = stringResource(
                        id = R.string.outing_format_time,
                        startTime.hour,
                        startTime.minute,
                        endTime.hour,
                        endTime.minute,
                    ),
                    onValueChange = { },
                    readOnly = true,
                )
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
                                            onChangeOutingTypeMenuExpanded(false)
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
                        value = if (uiState.selectedStudents.isEmpty()) {
                            stringResource(id = R.string.outing_add_companions)
                        } else {
                            StringBuilder().apply {
                                uiState.selectedStudents.forEachIndexed { index, student ->
                                    this.append("${student.gradeClassNumber} ${student.name} ")
                                    if (index != uiState.selectedStudents.lastIndex) {
                                        this.append(',')
                                    }
                                }
                            }.toString()
                        },
                        onValueChange = { /* explicit blank */ },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(
                                onClick = { onShouldShowCompanionListDialogChange(true) },
                            ) {
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
                TextField(
                    modifier = Modifier.fillMaxSize(),
                    value = uiState.reason,
                    onValueChange = { viewModel.postIntent(OutingIntent.UpdateReason(value = it)) },
                    singleLine = false,
                    hint = {
                        Text(text = stringResource(id = R.string.outing_hint_please_enter_description))
                    },
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = { viewModel.postIntent(OutingIntent.ApplyOuting) },
            ) {
                Text(text = stringResource(id = R.string.outing_do_application))
            }
        }
    }
}

@Composable
private fun StudentList(
    modifier: Modifier = Modifier,
    students: List<Student>?,
    selectedStudents: List<Student>,
    selectedOnly: Boolean = false,
    onSelectStudent: (Student) -> Unit,
) {
    VerticallyFadedLazyColumn(
        modifier = modifier,
        topFadeBrush = Brush.verticalGradient(
            colors = listOf(
                DmsTheme.colorScheme.surface,
                Color.Transparent,
            ),
        ),
        bottomFadeBrush = Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,
                DmsTheme.colorScheme.surface,
            ),
        ),
        verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
    ) {
        if (students != null) {
            items(students) { student ->
                val selected = if (selectedOnly) {
                    true
                } else {
                    selectedStudents.any { it == student }
                }
                StudentCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding(),
                    student = student,
                    selected = selected,
                    onClick = { onSelectStudent(student) },
                )
            }
        }
    }
}

@Composable
private fun StudentCard(
    modifier: Modifier = Modifier,
    student: Student,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier.verticalPadding(PaddingDefaults.ExtraSmall),
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
        elevation = CardDefaults.cardElevation(defaultElevation = ShadowDefaults.SmallElevation),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .startPadding()
                    .topPadding()
                    .bottomPadding()
                    .size(24.dp)
                    .clip(CircleShape),
                model = student.profileImageUrl,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .topPadding()
                    .endPadding()
                    .bottomPadding(),
                text = stringResource(
                    id = R.string.outing_application_format_grade_class_number_with_name,
                    student.gradeClassNumber,
                    student.name,
                ),
            )
            if (selected) {
                IconButton(onClick = onClick) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.cancel),
                    )
                }
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

@Composable
private fun TimePickerSpinner(
    modifier: Modifier = Modifier,
    startHourValuesPickerState: PickerState,
    startMinuteValuesPickerState: PickerState,
    endHourValuesPickerState: PickerState,
    endMinuteValuesPickerState: PickerState,
    startTime: LocalTime,
    endTime: LocalTime,
) {
    val startHourValues = remember { (0..23).map { it.toString() } }
    val startMinuteValues = remember { (0..5).map { (it * 10).toString() } }
    val endHourValues = remember { (0..23).map { it.toString() } }
    val endMinuteValues = remember { (0..5).map { (it * 10).toString() } }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Picker(
            modifier = Modifier.weight(1f),
            state = startHourValuesPickerState,
            items = startHourValues,
            visibleItemsCount = 5,
            startIndex = startTime.hour,
            textStyle = DmsTheme.typography.body1,
            textModifier = Modifier.padding(vertical = 10.dp),
        )
        Text(
            text = ":",
            fontSize = 30.sp,
            modifier = Modifier
                .weight(1f),
            textAlign = TextAlign.Center,
            style = DmsTheme.typography.body1,
        )
        Picker(
            modifier = Modifier.weight(1f),
            state = startMinuteValuesPickerState,
            items = startMinuteValues,
            visibleItemsCount = 5,
            startIndex = startTime.minute / 10,
            textStyle = DmsTheme.typography.body1,
            textModifier = Modifier.padding(vertical = 10.dp),
        )
        Text(
            modifier = Modifier
                .weight(2f),
            text = "~",
            textAlign = TextAlign.Center,
        )
        Picker(
            modifier = Modifier.weight(1f),
            state = endHourValuesPickerState,
            items = endHourValues,
            visibleItemsCount = 5,
            startIndex = endTime.hour,
            textStyle = DmsTheme.typography.body1,
            textModifier = Modifier.padding(vertical = 10.dp),
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = ":",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
        )
        Picker(
            modifier = Modifier.weight(1f),
            state = endMinuteValuesPickerState,
            items = endMinuteValues,
            visibleItemsCount = 5,
            startIndex = endTime.minute / 10,
            textStyle = DmsTheme.typography.body1,
            textModifier = Modifier.padding(vertical = 10.dp),
        )
    }
}

object OutingInputDefaults {

    private val IndicatorSize = DpSize(
        width = 4.dp,
        height = 4.dp,
    )

    private val IndicatorColor: Color
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
