package team.aliens.dms.android.core.designsystem.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsButton
import team.aliens.dms.android.core.designsystem.horizontalPadding
import team.aliens.dms.android.core.designsystem.util.clickable
import team.aliens.dms.android.core.designsystem.verticalPadding
import team.aliens.dms.android.shared.date.toDate
import team.aliens.dms.android.shared.date.toLocalDate
import java.time.LocalDate
import java.time.YearMonth
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun DmsCalendar(
    selectDate: String,
    onSelectedDateChange: (newDate: LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    var updateDate by remember { mutableStateOf(selectDate) }
    val calendarState = rememberCalendarState(
        startMonth = YearMonth.now().minusYears(1),
        endMonth = YearMonth.now().plusYears(1),
        firstVisibleMonth = YearMonth.now(),
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(DmsTheme.colorScheme.surfaceTint)
            .navigationBarsPadding(),
    ) {
        HorizontalCalendar(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f),
            state = calendarState,
            dayContent = { day ->
                Day(
                    day = day,
                    isSelected = day.date.toString() == updateDate,
                    onDayClick = { updateDate = it.toString() },
                )
            },
        )

        DmsButton(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .verticalPadding(12.dp),
            text = "확인",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            onClick = { onSelectedDateChange(updateDate.toLocalDate()) },
        )
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    onDayClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isOtherMonthDay = day.position != DayPosition.MonthDate
    val textColor = when {
        isSelected -> DmsTheme.colorScheme.surface
        isOtherMonthDay -> DmsTheme.colorScheme.onSurfaceVariant
        else -> DmsTheme.colorScheme.inverseSurface
    }
    val backgroundColor = when {
        isSelected -> DmsTheme.colorScheme.onPrimaryContainer
        else -> Color.Transparent
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onDayClick(day.date) })
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .background(
                    color = backgroundColor,
                    shape = CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                style = DmsTheme.typography.bodyM,
                color = textColor,
                textAlign = TextAlign.Center,
            )
        }
    }
}
