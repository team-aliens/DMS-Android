package team.aliens.design_system.component

import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import java.util.Date
import kotlinx.coroutines.launch
import team.aliens.design_system.annotation.DormDeprecated
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.utils.makeDate

/**
 * TODO(limsaehyun): CalendarView가 아닌 직접 구현한 DormCalenderView 사용 필요
 */
@DormDeprecated
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DormCalendarLayout(
    bottomSheetState: ModalBottomSheetState,
    onDateChange: (date: Date) -> Unit,
    content: @Composable () -> Unit,
) {
    DormBottomSheetDialog(
        useHandle = true,
        sheetState = bottomSheetState,
        sheetContent = {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 60.dp,
                    )
                    .clip(
                        RoundedCornerShape(
                            10.dp,
                        ),
                    )
                    .background(Color(0x66ffffff))
                    .wrapContentWidth(Alignment.CenterHorizontally),
                factory = {
                    CalendarView(it)
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

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PreviewDormCalendar() {
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    DormCalendarLayout(
        bottomSheetState = bottomSheetState,
        onDateChange = { date ->
            print(date)
        },
    ) {
        Text(text = "clicked!", modifier = Modifier.dormClickable {
            coroutineScope.launch {
                bottomSheetState.show()
            }
        })
    }
}