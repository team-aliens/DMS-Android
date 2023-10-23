package team.aliens.dms.android.core.designsystem

import android.widget.CalendarView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.viewinterop.AndroidView
import team.aliens.dms.android.shared.date.util.dateOf
import java.util.Date

@Composable
fun DmsCalendar(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
    selectedDate: Date,
    onSelectedDateChange: (newDate: Date) -> Unit,
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .clip(DmsTheme.shapes.large)
            .wrapContentWidth(Alignment.CenterHorizontally),
        factory = {
            val weekDayTheme = if (!darkTheme) {
                R.style.CalendarWeekLight
            } else {
                R.style.CalendarWeekDark
            }
            val calTheme = if (!darkTheme) {
                R.style.CalendarLight
            } else {
                R.style.CalendarDark
            }

            CalendarView(ContextThemeWrapper(it, calTheme)).apply {
                weekDayTextAppearance = weekDayTheme
            }
        },
        update = { calendar ->
            calendar.run {
                date = selectedDate.time
                setOnDateChangeListener { _, year, month, dayOfMonth ->
                    val date = dateOf(
                        year = year,
                        month = month,
                        dayOfMonth = dayOfMonth,
                    )
                    onSelectedDateChange(date)
                }
            }
        },
    )
}
