package team.aliens.dms.android.core.designsystem

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.Date

/*

import android.widget.CalendarView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import team.aliens.dms.android.shared.date.util.dateOf
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DormCalendarLayout(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
    bottomSheetState: BottomSheetScaffoldState,
    onDateChange: (newDate: Date) -> Unit,
    selectedDate: Date,
    content: @Composable () -> Unit,
) {
    DmsBottomSheetScaffold(
        modifier = modifier.fillMaxSize(),
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
                    it.run {
                        date = selectedDate.time
                        setOnDateChangeListener { _, year, month, dayOfMonth ->
                            val date = dateOf(
                                year = year,
                                month = month,
                                dayOfMonth = dayOfMonth,
                            )
                            onDateChange(date)
                        }
                    }
                },
            )
        },
    ) { content() }
}
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DmsCalendarScaffold(
    selectedDate: Date,
    onSelectedDateChange: (newDate: Date) -> Unit,
    modifier: Modifier = Modifier,
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    sheetPeekHeight: Dp = BottomSheetDefaults.SheetPeekHeight,
    sheetShape: Shape = BottomSheetDefaults.ExpandedShape,
    sheetContainerColor: Color = DmsTheme.colorScheme.surface,
    sheetContentColor: Color = DmsTheme.colorScheme.surface,
    sheetTonalElevation: Dp = BottomSheetDefaults.Elevation,
    sheetShadowElevation: Dp = BottomSheetDefaults.Elevation,
    sheetDragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    sheetSwipeEnabled: Boolean = true,
    topBar: @Composable (() -> Unit)? = null,
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    containerColor: Color = DmsTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(containerColor),
    content: @Composable (PaddingValues) -> Unit,
) = DmsBottomSheetScaffold(
    sheetContent = {
        DmsCalendar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 100.dp),
            selectedDate = selectedDate,
            onSelectedDateChange = onSelectedDateChange,
        )
    },
    modifier = modifier,
    scaffoldState = scaffoldState,
    sheetPeekHeight = sheetPeekHeight,
    sheetShape = sheetShape,
    sheetContainerColor = sheetContainerColor,
    sheetContentColor = sheetContentColor,
    sheetTonalElevation = sheetTonalElevation,
    sheetShadowElevation = sheetShadowElevation,
    sheetDragHandle = sheetDragHandle,
    sheetSwipeEnabled = sheetSwipeEnabled,
    topBar = topBar,
    snackbarHost = snackbarHost,
    containerColor = containerColor,
    contentColor = contentColor,
    content = content,
)

