package team.aliens.dms.android.feature.resetpassword.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.VerificationCodeInput
import team.aliens.dms.android.core.designsystem.VerificationCodeInputDefaults
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsButton
import team.aliens.dms.android.core.designsystem.horizontalPadding
import team.aliens.dms.android.core.designsystem.topPadding
import team.aliens.dms.android.feature.resetpassword.EMAIL_VERIFICATION_CODE_LENGTH
import team.aliens.dms.android.feature.resetpassword.model.ResetPasswordTextFieldError
import team.aliens.dms.android.feature.resetpassword.model.isError

@Composable
internal fun EmailVerificationContent(
    modifier: Modifier = Modifier,
    email: String,
    emailVerificationCode: String,
    remainingSeconds: Int,
    isResendLoading: Boolean,
    textFieldError: ResetPasswordTextFieldError,
    onEmailVerificationCodeChange: (String) -> Unit,
    onResendCode: () -> Unit,
) {
    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60
    val timerText = "%02d:%02d".format(minutes, seconds)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .topPadding(4.dp)
            .horizontalPadding(24.dp),
        verticalArrangement = Arrangement.spacedBy(48.dp),
    ) {
        Column {
            Text(
                text = "이메일 인증",
                style = DmsTheme.typography.bodyB,
                color = DmsTheme.colorScheme.onTertiaryContainer,
            )
            Column(
                modifier = Modifier.topPadding(12.dp),
            ) {
                Text(
                    text = "$email 이메일로 전송된",
                    style = DmsTheme.typography.bodyM,
                    color = DmsTheme.colorScheme.inverseSurface,
                )
                Row {
                    Text(
                        text = "인증번호 ${EMAIL_VERIFICATION_CODE_LENGTH}자리를 ",
                        style = DmsTheme.typography.bodyM,
                        color = DmsTheme.colorScheme.inverseSurface,
                    )
                    Text(
                        text = timerText,
                        style = DmsTheme.typography.bodyM,
                        color = DmsTheme.colorScheme.error,
                    )
                    Text(
                        text = " 내로 입력해주세요.",
                        style = DmsTheme.typography.bodyM,
                        color = DmsTheme.colorScheme.inverseSurface,
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            VerificationCodeInput(
                modifier = Modifier.fillMaxWidth(),
                totalLength = EMAIL_VERIFICATION_CODE_LENGTH,
                text = emailVerificationCode,
                onValueChange = onEmailVerificationCodeChange,
                supportingText = if (textFieldError.isError()) {
                    {
                        VerificationCodeInputDefaults.SupportingText(
                            text = textFieldError.message ?: "",
                            isError = true,
                        )
                    }
                } else null,
            )
            DmsButton(
                text = "인증코드 재발송",
                buttonType = ButtonType.Underline,
                buttonColor = ButtonColor.Gray,
                onClick = onResendCode,
                isLoading = isResendLoading,
            )
        }
    }
}
