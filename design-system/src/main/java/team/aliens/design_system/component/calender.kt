package team.aliens.design_system.component

import android.widget.CalendarView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.launch
import team.aliens.design_system.modifier.dormClickable

/**
 * TODO(limsaehyun): CalendarView가 아닌 직접 구현한 DormCalenderView 사용 필요
 */
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
                    .fillMaxSize()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                factory = {
                    CalendarView(it)
                },
                update = {
                    it.setOnDateChangeListener { view, year, month, day ->
                        onChangeDate("${day}-${month - 1}-${year}")
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