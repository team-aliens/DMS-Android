package team.aliens.dms.android.feature.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.VerificationCodeInput
import team.aliens.dms.android.core.ui.Banner
import team.aliens.dms.android.core.ui.BannerDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator

@Destination
@Composable
internal fun EnterSchoolVerificationCodeScreen(
    modifier: Modifier = Modifier,
    navigator: SignUpNavigator,
    viewModel: SignUpViewModel,
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    DmsScaffold(
        modifier = modifier,
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues)
                .imePadding(),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Banner(
                modifier = Modifier
                    .fillMaxWidth()
                    .startPadding(),
                message = {
                    BannerDefaults.DefaultText(
                        text = stringResource(id = R.string.sign_up_confirm_school_please_enter_school_verification_code),
                    )
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            VerificationCodeInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                totalLength = 8,
                text = uiState.schoolVerificationCode,
                onValueChange = { code ->
                    viewModel.postIntent(SignUpIntent.UpdateSchoolVerificationCode(value = code))
                },
            )
            Spacer(modifier = Modifier.weight(3f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                // FIXME: 서버 연동
                onClick = navigator::openEnterSchoolVerificationQuestion,
            ) {
                Text(text = stringResource(id = R.string.sign_up_authorize))
            }
        }
    }
}
