package team.aliens.dms.android.feature.remain.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.R
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsButton
import team.aliens.dms.android.core.designsystem.card.DmsApplicationCard
import team.aliens.dms.android.core.designsystem.horizontalPadding
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.designsystem.topPadding
import team.aliens.dms.android.core.designsystem.verticalPadding
import team.aliens.dms.android.core.ui.util.toLocale
import team.aliens.dms.android.feature.remain.ui.component.DmsFloatingNotice
import team.aliens.dms.android.feature.remain.viewmodel.RemainApplicationState
import team.aliens.dms.android.feature.remain.viewmodel.RemainApplicationViewModel
import java.util.UUID

@Composable
internal fun RemainApplication(
    onNavigateBack: (String?) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val remainApplicationViewModel: RemainApplicationViewModel = hiltViewModel()
    val state by remainApplicationViewModel.uiState.collectAsStateWithLifecycle()
    
    RemainApplicationScreen(
        onNavigateBack = onNavigateBack,
        state = state,
        setSelectRemainsOption = remainApplicationViewModel::setSelectRemainsOption,
        changeRemainsOption =  {
            remainApplicationViewModel.changeRemainsOption(onShowSnackBar)
        },
    )
}

@Composable
private fun RemainApplicationScreen(
    onNavigateBack: (String?) -> Unit,
    state: RemainApplicationState,
    setSelectRemainsOption: (UUID?) -> Unit,
    changeRemainsOption: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background)
            .systemBarsPadding(),
    ) {
        DmsTopAppBar(
            title = "잔류 신청",
            onBackPressed = { onNavigateBack(state.selectedRemainTitle) },
        )
        DmsFloatingNotice(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 12.dp,
                    start = 24.dp,
                    end = 24.dp,
                ),
            text = "잔류 신청 시간은 ${state.remainsApplicationTime.startDayOfWeek.toLocale()} ${state.remainsApplicationTime.startTime} ~ ${state.remainsApplicationTime.endDayOfWeek.toLocale()} ${state.remainsApplicationTime.endTime} 까지 입니다.",
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .topPadding(30.dp)
                .horizontalPadding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(state.remainsOptions) { remainsOption ->
                val icon = when (remainsOption.title) {
                    "금요일 귀가" -> R.drawable.img_night_bus
                    "토요일 귀가" -> R.drawable.img_bus
                    "토요일 귀사" -> R.drawable.img_home
                    "주말 잔류" -> R.drawable.img_small_home
                    else -> R.drawable.img_bus
                }
                val appliedTitle = if (remainsOption.applied) "신청됨" else null
                DmsApplicationCard(
                    title = remainsOption.title,
                    description = remainsOption.description,
                    isSelected = state.selectRemainsOptionId == remainsOption.id,
                    iconRes = icon,
                    appliedTitle = appliedTitle,
                    onClick = { setSelectRemainsOption(remainsOption.id) },
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        DmsButton(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .verticalPadding(16.dp),
            text = "변경하기",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            enabled = state.selectRemainsOptionId != null,
            onClick = changeRemainsOption,
        )
    }
}
