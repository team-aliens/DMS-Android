package team.aliens.dms.android.feature.latestudy.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.labelM
import java.time.YearMonth
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun LateStudyCalendarSection(
    currentMonth: YearMonth,
    onPrevMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
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
                text = "(새벽 자습은 금, 토, 일요일은 불가능합니다)",
                color = DmsTheme.colorScheme.inverseSurface,
                style = DmsTheme.typography.labelM,
            )
        }

        Spacer(modifier = Modifier.size(10.dp))

        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "이전 달",
                tint = DmsTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.clickable(onClick = onPrevMonthClick),
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = "${currentMonth.year} ${currentMonth.monthValue}월",
                color = DmsTheme.colorScheme.onBackground,
                style = DmsTheme.typography.bodyB,
            )

            Spacer(modifier = Modifier.size(4.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "다음 달",
                tint = DmsTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.clickable(onClick = onNextMonthClick),
            )
        }

        CalendarDayHeader()

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
private fun CalendarDateCell(
    date: LocalDate,
    isCurrentMonth: Boolean,
    modifier: Modifier = Modifier,
) {
    val textColor = when {
        !isCurrentMonth && date.dayOfWeek == DayOfWeek.SUNDAY -> DmsTheme.colorScheme.errorContainer
        !isCurrentMonth && date.dayOfWeek == DayOfWeek.SATURDAY -> DmsTheme.colorScheme.onPrimary
        !isCurrentMonth -> DmsTheme.colorScheme.inverseSurface
        date.dayOfWeek == DayOfWeek.SUNDAY -> DmsTheme.colorScheme.errorContainer
        date.dayOfWeek == DayOfWeek.SATURDAY -> DmsTheme.colorScheme.onPrimary
        date.dayOfWeek == DayOfWeek.FRIDAY -> DmsTheme.colorScheme.onSurfaceVariant
        else -> DmsTheme.colorScheme.onBackground
    }

    Box(
        modifier = modifier.size(32.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            color = textColor,
            style = DmsTheme.typography.caption,
        )
    }
}
