package team.aliens.dms_android.feature._feature.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Body3
import team.aliens.dms_android.feature.component.AppLogo
import team.aliens.dms_android.feature.R

@Destination
@Composable
internal fun EnterSchoolVerificationCodeScreen(
    onNavigateToEnterSchoolQuestion: () -> Unit,
    signUpViewModel: SignUpViewModel = hiltViewModel(),
) {

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
                    onNavigateToEnterSchoolQuestion()
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
    }
}