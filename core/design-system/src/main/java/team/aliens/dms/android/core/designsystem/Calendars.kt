package team.aliens.dms.android.core.designsystem

import android.widget.CalendarView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.threeten.bp.LocalDate
import team.aliens.dms.android.shared.date.toDate
import team.aliens.dms.android.shared.date.util.dateOf

@Composable
fun DmsCalendar(
    selectedDate: LocalDate,
    onSelectedDateChange: (newDate: LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val weekDayTheme = if (darkTheme) {
                R.style.CalendarWeekDark
            } else {
                R.style.CalendarWeekLight
            }
            val calendarTheme = if (darkTheme) {
                R.style.CalendarDark
            } else {
                R.style.CalendarLight
            }

            CalendarView(ContextThemeWrapper(context, calendarTheme)).apply {
                weekDayTextAppearance = weekDayTheme
            }
        },
        update = { calendar ->
            calendar.run {
                date = selectedDate.toDate().time
                setOnDateChangeListener { _, year, month, dayOfMonth ->
                    val date = dateOf(
                        year = year,
                        month = month,
                        day = dayOfMonth,
                    )
                    onSelectedDateChange(date)
                }
            }
        },
    )
}
