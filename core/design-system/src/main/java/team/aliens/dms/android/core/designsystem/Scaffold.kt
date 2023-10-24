package team.aliens.dms.android.core.designsystem

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

@Composable
fun DmsScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    containerColor: Color = DmsTheme.colorScheme.background,
    contentColor: Color = DmsTheme.colorScheme.background,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit,
) = Scaffold(
    modifier = modifier,
    topBar = topBar,
    bottomBar = bottomBar,
    containerColor = containerColor,
    contentColor = contentColor,
    contentWindowInsets = contentWindowInsets,
    content = content,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DmsBottomSheetScaffold(
    sheetContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    sheetPeekHeight: Dp = BottomSheetDefaults.SheetPeekHeight,
    sheetShape: Shape = BottomSheetDefaults.ExpandedShape,
    sheetContainerColor: Color = DmsTheme.colorScheme.surface,
    sheetContentColor: Color = DmsTheme.colorScheme.surface,
    sheetTonalElevation: Dp = BottomSheetDefaults.Elevation,
    sheetShadowElevation: Dp = BottomSheetDefaults.Elevation,
    sheetDragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle(color = DmsTheme.colorScheme.onSurface) },
    sheetSwipeEnabled: Boolean = true,
    topBar: @Composable (() -> Unit)? = null,
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    containerColor: Color = DmsTheme.colorScheme.surface,
    contentColor: Color = DmsTheme.colorScheme.surface,
    content: @Composable (PaddingValues) -> Unit,
) = BottomSheetScaffold(
    sheetContent = sheetContent,
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
