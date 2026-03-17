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
import team.aliens.dms.android.feature.resetpassword.model.ResetPasswordTextFieldError
import team.aliens.dms.android.feature.resetpassword.model.isError

@Composable
internal fun InputNewPasswordContent(
    modifier: Modifier = Modifier,
    password: String,
    passwordConfirm: String,
    passwordTextFieldError: ResetPasswordTextFieldError,
    passwordConfirmTextFieldError: ResetPasswordTextFieldError,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .topPadding(4.dp)
            .horizontalPadding(24.dp),
        verticalArrangement = Arrangement.spacedBy(48.dp),
    ) {
        DmsSymbolContent(
            title = "비밀번호 재설정",
            description = "비밀번호를 다시 설정해주세요.",
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            DmsTextField(
                label = "비밀번호",
                hint = "비밀번호 입력",
                value = password,
                onValueChange = onPasswordChange,
                keyboardType = KeyboardType.Password,
                showVisibleIcon = true,
                isError = passwordTextFieldError.isError(),
                errorMessage = passwordTextFieldError.message,
            )
            DmsTextField(
                label = "비밀번호 확인",
                hint = "비밀번호 재입력",
                value = passwordConfirm,
                onValueChange = onPasswordConfirmChange,
                keyboardType = KeyboardType.Password,
                showVisibleIcon = true,
                isError = passwordConfirmTextFieldError.isError(),
                errorMessage = passwordConfirmTextFieldError.message,
            )
        }
    }
}
