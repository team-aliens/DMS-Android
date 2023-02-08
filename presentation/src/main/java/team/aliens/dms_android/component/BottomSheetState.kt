package team.aliens.dms_android.component

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import team.aliens.domain.enums.BottomSheetType

@OptIn(ExperimentalMaterialApi::class)
fun changeBottomSheetState(
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    bottomSheetType: BottomSheetType,
) {
    coroutineScope.launch {
        when (bottomSheetType) {
            BottomSheetType.Hide -> bottomSheetState.hide()
            BottomSheetType.Show -> bottomSheetState.show()
        }
    }
}
