package com.example.dms_android.component

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import com.example.domain.enums.BottomSheetType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
