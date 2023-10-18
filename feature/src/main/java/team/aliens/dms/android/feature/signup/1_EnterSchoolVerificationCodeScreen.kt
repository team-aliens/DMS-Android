package team.aliens.dms.android.feature.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator

@Destination
@Composable
internal fun EnterSchoolVerificationCodeScreen(
    modifier: Modifier = Modifier,
    navigator: SignUpNavigator,
    // signUpViewModel: SignUpViewModel = hiltViewModel(),
) {/*

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