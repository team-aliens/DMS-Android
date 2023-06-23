package team.aliens.design_system.component

import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import java.util.Date
import team.aliens.design_system.R
import team.aliens.design_system.annotation.DormDeprecated
import team.aliens.design_system.utils.makeDate

/**
 * TODO(limsaehyun): CalendarView가 아닌 직접 구현한 DormCalenderView 사용 필요
 */
@DormDeprecated
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DormCalendarLayout(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
    bottomSheetState: ModalBottomSheetState,
    onDateChange: (date: Date) -> Unit,
    content: @Composable () -> Unit,
) {
    DormBottomSheetDialog(
        modifier = modifier.fillMaxSize(),
        useHandle = true,
        sheetState = bottomSheetState,
        sheetContent = {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .wrapContentWidth(Alignment.CenterHorizontally),
                factory = {
                    val weekDayTheme =
                        if (!darkTheme) R.style.CalendarWeekLight else R.style.CalendarWeekDark
                    val calTheme =
                        if (!darkTheme) R.style.CalendarLight else R.style.CalendarDark

                    CalendarView(ContextThemeWrapper(it, calTheme)).apply {
                        weekDayTextAppearance = weekDayTheme
                    }
                },
                update = {
                    it.setOnDateChangeListener { _, year, month, dayOfMonth ->
                        val date = makeDate(
                            year = year,
                            month = month,
                            dayOfMonth = dayOfMonth,
                        )
                        onDateChange(date)
                    }
                },
            )
        },
        content = content,
    )
}
