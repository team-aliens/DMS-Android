package team.aliens.dms.android.feature.latestudy.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.labelM

@Composable
fun LateStudyCalendarSection(
    currentMonth: YearMonth,
    selectedDates: Set<LocalDate>,
    onPrevMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    onDateClick: (LocalDate) -> Unit,
    onDateRangeSelect: (LocalDate, LocalDate) -> Unit,
    onPastDateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LateStudySectionCard(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "일정",
                color = DmsTheme.colorScheme.onBackground,
                style = DmsTheme.typography.bodyB,
            )

            Text(
                text = "(이번 주 월~목 · 길게 눌러 드래그해 여러 날짜 선택)",
                color = DmsTheme.colorScheme.inverseSurface,
                style = DmsTheme.typography.labelM,
            )
        }

        Spacer(modifier = Modifier.size(10.dp))

        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onPrevMonthClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "이전 달",
                    tint = DmsTheme.colorScheme.onSurfaceVariant,
                )
            }

            Text(
                text = "${currentMonth.year} ${currentMonth.monthValue}월",
                color = DmsTheme.colorScheme.onBackground,
                style = DmsTheme.typography.bodyB,
            )

            IconButton(onClick = onNextMonthClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "다음 달",
                    tint = DmsTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        CalendarDayHeader()

        CalendarGrid(
            currentMonth = currentMonth,
            selectedDates = selectedDates,
            onDateClick = onDateClick,
            onDateRangeSelect = onDateRangeSelect,
            onPastDateClick = onPastDateClick,
        )

        Spacer(modifier = Modifier.size(4.dp))
    }
}

@Composable
private fun CalendarDayHeader() {
    val days = listOf("일", "월", "화", "수", "목", "금", "토")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        days.forEachIndexed { index, day ->
            val color = when (index) {
                0 -> DmsTheme.colorScheme.errorContainer
                6 -> DmsTheme.colorScheme.onPrimary
                5 -> DmsTheme.colorScheme.onSurfaceVariant
                else -> DmsTheme.colorScheme.onBackground
            }

            Box(
                modifier = Modifier.size(32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = day,
                    color = color,
                    style = DmsTheme.typography.caption,
                )
            }
        }
    }
}

@Composable
private fun CalendarGrid(
    currentMonth: YearMonth,
    selectedDates: Set<LocalDate>,
    onDateClick: (LocalDate) -> Unit,
    onDateRangeSelect: (LocalDate, LocalDate) -> Unit,
    onPastDateClick: () -> Unit,
) {
    val dates = buildCalendarDates(currentMonth)
    val density = LocalDensity.current
    val horizontalPadding = with(density) { 16.dp.toPx() }
    val rowHeight = with(density) { 52.dp.toPx() }
    val haptic = LocalHapticFeedback.current
    var rangeStartDate by remember { mutableStateOf<LocalDate?>(null) }
    var rangeEndDate by remember { mutableStateOf<LocalDate?>(null) }
    val visibleSelectedDates = selectedDates + selectableDatesBetween(
        firstDate = rangeStartDate,
        secondDate = rangeEndDate,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(dates) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { position ->
                        val date = calendarDateAtPosition(
                            position = position,
                            dates = dates,
                            gridWidth = size.width.toFloat(),
                            horizontalPadding = horizontalPadding,
                            rowHeight = rowHeight,
                        )
                        if (date?.isBefore(LocalDate.now()) == true) {
                            onPastDateClick()
                        }
                        rangeStartDate = date?.takeIf(::isSelectableDate)
                        rangeEndDate = rangeStartDate
                        if (rangeStartDate != null) {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                    },
                    onDrag = { change, _ ->
                        rangeEndDate = calendarDateAtPosition(
                            position = change.position,
                            dates = dates,
                            gridWidth = size.width.toFloat(),
                            horizontalPadding = horizontalPadding,
                            rowHeight = rowHeight,
                        )?.takeIf(::isSelectableDate) ?: rangeEndDate
                    },
                    onDragEnd = {
                        val startDate = rangeStartDate
                        val endDate = rangeEndDate
                        if (startDate != null && endDate != null) {
                            onDateRangeSelect(startDate, endDate)
                        }
                        rangeStartDate = null
                        rangeEndDate = null
                    },
                    onDragCancel = {
                        rangeStartDate = null
                        rangeEndDate = null
                    },
                )
            },
    ) {
        dates.chunked(7).forEach { week ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
            ) {
                week.forEachIndexed { index, item ->
                    val isSelectable = item.isCurrentMonth && isSelectableDate(item.date)
                    val isSelected = item.date in visibleSelectedDates
                    val isConnectedToPrevious = isSelected &&
                        index > 0 &&
                        week[index - 1].date in visibleSelectedDates
                    val isConnectedToNext = isSelected &&
                        index < week.lastIndex &&
                        week[index + 1].date in visibleSelectedDates

                    CalendarDateCell(
                        date = item.date,
                        isCurrentMonth = item.isCurrentMonth,
                        isSelected = isSelected,
                        isConnectedToPrevious = isConnectedToPrevious,
                        isConnectedToNext = isConnectedToNext,
                        onClick = {
                            handleCalendarDateClick(
                                date = item.date,
                                isSelectable = isSelectable,
                                onDateClick = onDateClick,
                                onPastDateClick = onPastDateClick,
                            )
                        },
                        isEnabled = item.isCurrentMonth,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun CalendarDateCell(
    date: LocalDate,
    isCurrentMonth: Boolean,
    isSelected: Boolean,
    isConnectedToPrevious: Boolean,
    isConnectedToNext: Boolean,
    onClick: () -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val textColor = calendarDateTextColor(
        date = date,
        isCurrentMonth = isCurrentMonth,
        isSelected = isSelected,
    )

    Box(
        modifier = modifier.height(40.dp),
        contentAlignment = Alignment.Center,
    ) {
        ConnectedDateBackground(
            isConnectedToPrevious = isConnectedToPrevious,
            isConnectedToNext = isConnectedToNext,
        )

        SelectedDateBackground(isSelected = isSelected)

        CalendarDateText(
            date = date,
            textColor = textColor,
            isEnabled = isEnabled,
            onClick = onClick,
        )
    }
}

@Composable
private fun ConnectedDateBackground(
    isConnectedToPrevious: Boolean,
    isConnectedToNext: Boolean,
) {
    val previousColor by animateColorAsState(
        targetValue = if (isConnectedToPrevious) {
            DmsTheme.colorScheme.primaryContainer
        } else {
            Color.Transparent
        },
        label = "previous selection connector",
    )
    val nextColor by animateColorAsState(
        targetValue = if (isConnectedToNext) {
            DmsTheme.colorScheme.primaryContainer
        } else {
            Color.Transparent
        },
        label = "next selection connector",
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(28.dp),
    ) {
        if (isConnectedToPrevious) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight()
                    .background(previousColor),
            )
        }

        if (isConnectedToNext) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight()
                    .background(nextColor),
            )
        }
    }
}

@Composable
private fun SelectedDateBackground(
    isSelected: Boolean,
) {
    val size by animateDpAsState(
        targetValue = if (isSelected) 28.dp else 0.dp,
        label = "selected date size",
    )
    val color by animateColorAsState(
        targetValue = if (isSelected) {
            DmsTheme.colorScheme.onPrimaryContainer
        } else {
            Color.Transparent
        },
        label = "selected date color",
    )

    Box(
        modifier = Modifier
            .size(size)
            .background(
                color = color,
                shape = CircleShape,
            ),
    )
}

@Composable
private fun CalendarDateText(
    date: LocalDate,
    textColor: Color,
    isEnabled: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clickable(
                enabled = isEnabled,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            color = textColor,
            style = DmsTheme.typography.caption,
        )
    }
}

@Composable
private fun calendarDateTextColor(
    date: LocalDate,
    isCurrentMonth: Boolean,
    isSelected: Boolean,
): Color {
    return when {
        isSelected -> DmsTheme.colorScheme.background
        !isCurrentMonth && date.dayOfWeek == DayOfWeek.SUNDAY -> DmsTheme.colorScheme.onError
        !isCurrentMonth && date.dayOfWeek == DayOfWeek.SATURDAY -> DmsTheme.colorScheme.onPrimary
        !isCurrentMonth -> DmsTheme.colorScheme.inverseSurface
        date.dayOfWeek == DayOfWeek.SUNDAY -> DmsTheme.colorScheme.onError
        date.dayOfWeek == DayOfWeek.SATURDAY -> DmsTheme.colorScheme.onPrimary
        date.dayOfWeek == DayOfWeek.FRIDAY -> DmsTheme.colorScheme.onSurfaceVariant
        else -> DmsTheme.colorScheme.onBackground
    }
}

private fun calendarDateAtPosition(
    position: Offset,
    dates: List<CalendarDateUiModel>,
    gridWidth: Float,
    horizontalPadding: Float,
    rowHeight: Float,
): LocalDate? {
    val contentWidth = gridWidth - (horizontalPadding * 2)
    val gridHeight = ((dates.size + 6) / 7) * rowHeight
    if (contentWidth <= 0f) {
        return null
    }
    if (position.isOutsideCalendarGrid(gridWidth, horizontalPadding, gridHeight)) {
        return null
    }

    val column = ((position.x - horizontalPadding) / (contentWidth / 7)).toInt()
    val row = (position.y / rowHeight).toInt()
    return dates.getOrNull(row * 7 + column)?.date
}

private fun handleCalendarDateClick(
    date: LocalDate,
    isSelectable: Boolean,
    onDateClick: (LocalDate) -> Unit,
    onPastDateClick: () -> Unit,
) {
    when {
        date.isBefore(LocalDate.now()) -> onPastDateClick()
        isSelectable -> onDateClick(date)
    }
}

private fun Offset.isOutsideCalendarGrid(
    gridWidth: Float,
    horizontalPadding: Float,
    gridHeight: Float,
): Boolean {
    return isOutsideHorizontalBounds(gridWidth, horizontalPadding) ||
        isOutsideVerticalBounds(gridHeight)
}

private fun Offset.isOutsideHorizontalBounds(
    gridWidth: Float,
    horizontalPadding: Float,
): Boolean {
    return x < horizontalPadding || x >= gridWidth - horizontalPadding
}

private fun Offset.isOutsideVerticalBounds(gridHeight: Float): Boolean {
    return y < 0f || y >= gridHeight
}

private data class CalendarDateUiModel(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
)

private fun buildCalendarDates(
    currentMonth: YearMonth,
): List<CalendarDateUiModel> {
    val firstDayOfMonth = currentMonth.atDay(1)
    val firstDayOffset = firstDayOfMonth.dayOfWeek.value % 7
    val daysInMonth = currentMonth.lengthOfMonth()
    val previousMonth = currentMonth.minusMonths(1)
    val previousMonthLastDay = previousMonth.lengthOfMonth()
    val result = mutableListOf<CalendarDateUiModel>()

    repeat(firstDayOffset) { index ->
        val day = previousMonthLastDay - firstDayOffset + index + 1
        result.add(
            CalendarDateUiModel(
                date = previousMonth.atDay(day),
                isCurrentMonth = false,
            ),
        )
    }

    for (day in 1..daysInMonth) {
        result.add(
            CalendarDateUiModel(
                date = currentMonth.atDay(day),
                isCurrentMonth = true,
            ),
        )
    }

    var nextMonthDay = 1
    while (result.size % 7 != 0) {
        result.add(
            CalendarDateUiModel(
                date = currentMonth.plusMonths(1).atDay(nextMonthDay++),
                isCurrentMonth = false,
            ),
        )
    }

    return result
}

private fun selectableDatesBetween(
    firstDate: LocalDate?,
    secondDate: LocalDate?,
): Set<LocalDate> {
    if (firstDate == null || secondDate == null) return emptySet()

    val startDate = minOf(firstDate, secondDate)
    val endDate = maxOf(firstDate, secondDate)

    return generateSequence(startDate) { date ->
        date.plusDays(1).takeIf { it <= endDate }
    }
        .filter(::isSelectableDate)
        .toSet()
}

private fun isSelectableDate(date: LocalDate): Boolean {
    val today = LocalDate.now()

    val startOfSelectableWeek = if (
        today.dayOfWeek == DayOfWeek.FRIDAY ||
        today.dayOfWeek == DayOfWeek.SATURDAY ||
        today.dayOfWeek == DayOfWeek.SUNDAY
    ) {
        today.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
    } else {
        today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    }

    val endOfSelectableWeek = startOfSelectableWeek.plusDays(3)

    return !date.isBefore(today) &&
            !date.isBefore(startOfSelectableWeek) &&
            !date.isAfter(endOfSelectableWeek)
}
