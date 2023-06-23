package team.aliens.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import team.aliens.design_system.annotation.DormDeprecated
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body1
import team.aliens.design_system.typography.Body2

private val BottomSheetShape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 16.dp,
)

@DormDeprecated
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DormBottomSheetDialog(
    useHandle: Boolean = false,
    sheetState: ModalBottomSheetState,
    sheetContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
        sheetShape = BottomSheetShape,
        sheetContent = {
            DormBottomSheetContent(
                useHandle = useHandle,
                sheetContent = sheetContent,
            )
        },
        sheetBackgroundColor = DormTheme.colors.background,
        scrimColor = Color.Transparent,
    ) {
        content()
    }
}

@DormDeprecated
@Composable
private fun DormBottomSheetContent(
    useHandle: Boolean,
    sheetContent: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        DormBottomSheetHandle(
            useHandle = useHandle,
        )
        sheetContent()
    }
}

private val BottomSheetContentPadding = PaddingValues(
    vertical = 8.dp,
)

private val BottomSheetHandleSize = DpSize(
    width = 40.dp,
    height = 4.dp,
)

private val BottomSheetHandleShape = RoundedCornerShape(
    size = 2.dp,
)

@DormDeprecated
@Composable
private fun DormBottomSheetHandle(
    useHandle: Boolean,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(BottomSheetContentPadding),
        contentAlignment = Alignment.Center,
    ) {
        if (useHandle) {
            Box(
                modifier = Modifier
                    .size(BottomSheetHandleSize)
                    .clip(BottomSheetHandleShape)
                    .background(
                        color = DormTheme.colors.primaryVariant,
                    ),
            )
        }
    }
}


@DormDeprecated
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DormBottomSheetDialogWithButtonLayout(
    sheetState: ModalBottomSheetState,
    sheetContent: @Composable () -> Unit,
    btnText: String,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
        sheetContent = {
            DormBottomSheetContent(
                useHandle = false,
                sheetContent = {
                    Column {

                        sheetContent()

                        Divider(
                            modifier = Modifier
                                .padding(
                                    horizontal = 10.dp,
                                )
                                .fillMaxWidth(),
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clickable {
                                    scope.launch {
                                        sheetState.hide()
                                    }
                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            Body2(
                                text = btnText,
                            )
                        }
                    }
                },
            )
        },
        sheetBackgroundColor = Color.Transparent,
        content = content,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun DormCancellableBottomSheetDialogPreview() {

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Expanded)

    DormBottomSheetDialogWithButtonLayout(
        sheetState = sheetState, sheetContent = {
            Body1(text = "HIHI")

        }, btnText = "취소"
    ) {

    }
}

