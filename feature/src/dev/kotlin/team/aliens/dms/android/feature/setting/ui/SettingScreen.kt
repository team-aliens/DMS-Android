package team.aliens.dms.android.feature.setting.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.R
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.button.DmsItemButton
import team.aliens.dms.android.feature.setting.ui.component.SettingRotateContent

@Composable
internal fun Setting(
    onBackPressed: () -> Unit,
) {
    val rotated = remember { mutableStateOf(true) }

    SettingScreen(
        rotated = rotated.value,
        onClick = { rotated.value = !rotated.value },
        onBackPressed = onBackPressed,
    )
}

@Composable
private fun SettingScreen(
    rotated: Boolean,
    onClick: () -> Unit,
    onBackPressed: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        DmsTopAppBar(
            onBackPressed = onBackPressed,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DmsTheme.colorScheme.background)
                .padding(horizontal = 10.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            DmsItemButton(
                iconRes = R.drawable.img_3d_key,
                text = "비밀번호 재설정",
                onClick = { },
            )
            DmsItemButton(
                iconRes = R.drawable.img_calendar,
                text = "프로필 사진 변경",
                onClick = { },
            )
            SettingRotateContent(
                iconRes = R.drawable.img_repeat,
                text = "푸시 알림 켜기",
                rotated = rotated,
                onClick = onClick,
            )
            DmsItemButton(
                iconRes = R.drawable.img_3d_out,
                text = "로그아웃",
                onClick = { },
            )
        }
    }
}
