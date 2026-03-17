package team.aliens.dms.android.feature.resetpassword.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.foundation.DmsSymbolContent
import team.aliens.dms.android.core.designsystem.horizontalPadding
import team.aliens.dms.android.core.designsystem.textfield.DmsTextField
import team.aliens.dms.android.core.designsystem.topPadding

@Composable
internal fun InputIdContent(
    modifier: Modifier = Modifier,
    accountId: String,
    onAccountIdChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .topPadding(4.dp)
            .horizontalPadding(24.dp),
        verticalArrangement = Arrangement.spacedBy(48.dp),
    ) {
        DmsSymbolContent(
            title = "비밀번호 찾기",
            description = "아이디를 입력해주세요!",
        )
        DmsTextField(
            label = "아이디",
            hint = "아이디 입력",
            value = accountId,
            onValueChange = onAccountIdChange,
            showClearIcon = true,
        )
    }
}
