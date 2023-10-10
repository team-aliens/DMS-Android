package team.aliens.dms_android.feature._feature.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms_android.design_system.button.DormButtonColor
import team.aliens.dms_android.design_system.button.DormContainedLargeButton
import team.aliens.dms_android.design_system.extension.Space
import team.aliens.dms_android.design_system.modifier.dormClickable
import team.aliens.dms_android.design_system.textfield.DormTextField
import team.aliens.dms_android.design_system.theme.DormTheme
import team.aliens.dms_android.design_system.typography.Body2
import team.aliens.dms_android.design_system.typography.ButtonText
import team.aliens.dms_android.design_system.typography.Caption
import team.aliens.dms_android.feature.component.AppLogo
import team.aliens.dms_android.feature.R

@Destination
@Composable
internal fun EnterSchoolVerificationQuestionScreen(
    onNavigateToSendVerificationEmail: () -> Unit,
    onNavigateToSignInWithInclusive: () -> Unit,
    signUpViewModel: SignUpViewModel,
) {

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
                    onNavigateToSendVerificationEmail()
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
                            onClick = onNavigateToSignInWithInclusive,
                        ),
                    text = stringResource(id = R.string.sign_in),
                )
            }
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}