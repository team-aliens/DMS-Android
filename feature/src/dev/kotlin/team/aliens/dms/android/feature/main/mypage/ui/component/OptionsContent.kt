package team.aliens.dms.android.feature.main.mypage.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.core.designsystem.util.clickable
import team.aliens.dms.android.data.point.model.PointType

@Composable
internal fun OptionsContent(
    modifier: Modifier = Modifier,
    onNavigatePointHistory: (PointType) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        val userOptions = listOf(
            Option(
                title = "상벌점 내역 확인",
                onClick = { onNavigatePointHistory(PointType.ALL) },
            ),
            Option(
                title = "비밀번호 변경",
                onClick = { },
            ),
            Option(
                title = "알림 설정",
                onClick = { },
            ),
        )
        val signOutOption = listOf(
            Option(
                title = "로그아웃",
                onClick = { },
            ),
        )
        val withdrawalOption = listOf(
            Option(
                title = "회원 탈퇴",
                onClick = {},
            ),
        )
        val themeOption = listOf(
            Option(
                title = "테마 변경",
                onClick = {},
            ),
        )

        OptionLayout(
            options = userOptions,
            titleColor = Option.DefaultTitleColor,
        )
        OptionLayout(
            options = signOutOption,
            titleColor = Option.ErrorTitleColor,
        )
        OptionLayout(
            options = withdrawalOption,
            titleColor = Option.ErrorTitleColor,
        )
        OptionLayout(
            options = themeOption,
            titleColor = Option.DefaultTitleColor,
        )
    }
}

@Composable
private fun OptionLayout(
    modifier: Modifier = Modifier,
    options: List<Option>,
    titleColor: Color,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = DmsTheme.colorScheme.surfaceTint,
            contentColor = titleColor,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        options.forEachIndexed { index, option ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = option.onClick)
                    .padding(horizontal = 24.dp, vertical = 14.dp),
                text = option.title,
                style = DmsTheme.typography.bodyM,
                color = titleColor,
            )
            if (index != options.lastIndex) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = DmsTheme.colorScheme.surface,
                )
            }
        }
    }
}

@Immutable
private class Option(
    val title: String,
    val onClick: () -> Unit,
) {
    companion object {
        val DefaultTitleColor: Color
            @Composable inline get() = DmsTheme.colorScheme.onTertiaryContainer

        val ErrorTitleColor: Color
            @Composable inline get() = DmsTheme.colorScheme.outline
    }
}
