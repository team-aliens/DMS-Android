package team.aliens.dms.android.feature.resetpassword.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.foundation.DmsSymbolContent
import team.aliens.dms.android.core.designsystem.horizontalPadding
import team.aliens.dms.android.core.designsystem.textfield.DmsTextField
import team.aliens.dms.android.core.designsystem.topPadding

@Composable
internal fun InputUserInfoContent(
    modifier: Modifier = Modifier,
    name: String,
    email: String,
    hashEmail: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
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
            description = "이름과 이메일을 입력해주세요!",
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            DmsTextField(
                label = "이름",
                hint = "이름 입력",
                value = name,
                onValueChange = onNameChange,
                showClearIcon = true,
            )
            DmsTextField(
                label = "이메일",
                hint = hashEmail,
                value = email,
                onValueChange = onEmailChange,
                keyboardType = KeyboardType.Email,
                showClearIcon = true,
            )
        }
    }
}
