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
import kotlinx.coroutines.launch
import team.aliens.design_system.annotation.DormDeprecated
import team.aliens.design_system.modifier.dormClickable

/**
 * TODO(limsaehyun): CalendarView가 아닌 직접 구현한 DormCalenderView 사용 필요
 */
@DormDeprecated
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DormCalendar(
    bottomSheetState: ModalBottomSheetState,
    onChangeDate: (String) -> Unit,
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
                    it.setOnDateChangeListener { _, year, month, day ->
                        val formedMonth = (month + 1).toString().padStart(2, '0')
                        val formedDay = day.toString().padStart(2, '0')
                        onChangeDate("${year}-${formedMonth}-${formedDay}")
                    }
                },
            )
        },
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PreviewDormCalendar() {
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    DormCalendar(
        bottomSheetState = bottomSheetState,
        onChangeDate = { date ->
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