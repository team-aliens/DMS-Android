package team.aliens.dms.android.feature.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator

@Destination
@Composable
internal fun EnterSchoolVerificationQuestionScreen(
    modifier: Modifier = Modifier,
    navigator: SignUpNavigator,
    // signUpViewModel: SignUpViewModel,
) {/*

    val uiState by signUpViewModel.stateFlow.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    val onAnswerChange = { schoolAnswer: String ->
        signUpViewModel.postIntent(
            SignUpIntent.SchoolQuestion.SetSchoolAnswer(
                schoolAnswer = schoolAnswer,
            )
        )
    }

    LaunchedEffect(Unit) {
        signUpViewModel.postIntent(SignUpIntent.SchoolQuestion.FetchSchoolQuestion)
        signUpViewModel.sideEffectFlow.collect {
            when (it) {
                is SignUpSideEffect.SchoolQuestion.SuccessVerifySchoolAnswer -> {
                    navigator.openEnterEmail()
                }

                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormTheme.colors.surface)
            .dormClickable(
                rippleEnabled = false,
            ) {
                focusManager.clearFocus()
            },
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(108.dp))
            AppLogo(
                darkIcon = isSystemInDarkTheme(),
            )
            Space(space = 8.dp)
            Body2(text = stringResource(id = R.string.sign_up_confirm_school_question))
            Space(space = 60.dp)
            Body2(text = uiState.schoolQuestion)
            Space(space = 10.dp)
            DormTextField(
                value = uiState.schoolAnswer,
                onValueChange = onAnswerChange,
                hint = stringResource(id = R.string.Reply),
                error = uiState.schoolAnswerMismatchError,
                errorDescription = stringResource(id = R.string.sign_up_confirm_school_error_incorrect_answer),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
            )
            Spacer(modifier = Modifier.weight(1f))
            DormContainedLargeButton(
                text = stringResource(id = R.string.accept),
                color = DormButtonColor.Blue,
                enabled = uiState.schoolAnswerButtonEnabled,
            ) {
                signUpViewModel.postIntent(SignUpIntent.SchoolQuestion.ExamineSchoolAnswer)
            }
            Space(space = 12.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Caption(
                    text = stringResource(id = R.string.sign_up_confirm_school_do_you_have_account_already),
                    color = DormTheme.colors.primaryVariant,
                )
                Space(space = 8.dp)
                ButtonText(
                    modifier = Modifier
                        .padding(top = 1.dp)
                        .dormClickable(
                            rippleEnabled = false,
                            onClick = navigator::openSignIn,
                        ),
                    text = stringResource(id = R.string.sign_in),
                )
            }
            Spacer(modifier = Modifier.height(48.dp))
        }
    }*/
}