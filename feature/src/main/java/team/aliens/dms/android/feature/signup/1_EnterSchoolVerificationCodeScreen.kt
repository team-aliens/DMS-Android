package team.aliens.dms.android.feature.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.editpassword.Banner
import team.aliens.dms.android.feature.signin.SignInIntent
import team.aliens.dms.android.feature.signin.UnauthorizedActions
import team.aliens.dms.android.feature.signin.UserInformationInputs
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator

@Destination
@Composable
internal fun EnterSchoolVerificationCodeScreen(
    modifier: Modifier = Modifier,
    navigator: SignUpNavigator,
    viewModel: SignUpViewModel,
) {
    DmsScaffold(
        modifier = modifier,
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Banner(Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.weight(1f))
            VerificationCodeInput()
            Spacer(modifier = Modifier.weight(3f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = { viewModel.postIntent(SignInIntent.SignIn) },
                enabled = uiState.signInButtonAvailable,
            ) {
                Text(text = stringResource(id = R.string.sign_up_authorize))
            }
        }
    }
    /*

    val uiState by signUpViewModel.stateFlow.collectAsState()

    val schoolCodeMismatchError = uiState.schoolCodeMismatchError

    val focusManager = LocalFocusManager.current

    val focusRequester = remember { FocusRequester() }

    val onVerificationCodeChange = { schoolCode: String ->
        if(schoolCode.length <= 8) {
            signUpViewModel.postIntent(
                SignUpIntent.VerifySchool.SetSchoolVerificationCode(
                    schoolCode = schoolCode,
                )
            )
            if (schoolCode.length == 8) {
                focusManager.clearFocus()
                signUpViewModel.postIntent(SignUpIntent.VerifySchool.ExamineSchoolVerificationCode)
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        signUpViewModel.sideEffectFlow.collect {
            when (it) {
                is SignUpSideEffect.VerifySchool.SuccessVerifySchoolCode -> {
                    navigator.openEnterSchoolVerificationQuestion()
                }

                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormTheme.colors.surface)
            .dormClickable(rippleEnabled = false) {
                focusManager.clearFocus()
            },
    ) {
        Spacer(modifier = Modifier.height(108.dp))
        Column(
            modifier = Modifier.padding(start = 16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            AppLogo(darkIcon = isSystemInDarkTheme())
            Space(space = 8.dp)
            Body2(text = stringResource(id = R.string.SchoolVerificationCode))
        }
        Space(space = 100.dp)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BasicTextField(
                value = uiState.schoolCode,
                modifier = Modifier.focusRequester(focusRequester),
                onValueChange = onVerificationCodeChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                },
                decorationBox = {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        items(8) { index ->
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(shape = CircleShape)
                                    .background(
                                        color = if (uiState.schoolCode.length - 1 >= index) DormTheme.colors.primaryVariant
                                        else DormTheme.colors.secondaryVariant,
                                    ),
                            )
                        }
                    }
                }
            )
            Space(space = 40.dp)
            Body3(
                text = stringResource(
                    id = if (schoolCodeMismatchError) R.string.sign_up_confirm_school_error_incorrect_school_verification_code
                    else R.string.sign_up_confirm_school_please_enter_school_verification_code,
                ),
                color = if (schoolCodeMismatchError) DormColor.Error
                else DormColor.Gray500,
            )
            Spacer(modifier = Modifier.weight(1f))
            DormContainedLargeButton(
                text = stringResource(id = R.string.Verification),
                color = DormButtonColor.Blue,
                enabled = uiState.schoolCodeButtonEnabled,
            ) {
                signUpViewModel.postIntent(intent = SignUpIntent.VerifySchool.ExamineSchoolVerificationCode)
            }
            Spacer(modifier = Modifier.height(82.dp))
        }
    }*/
}