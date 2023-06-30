package team.aliens.dms_android.feature.signup.ui.school

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.extension.RatioSpace
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Body3
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.DmsRoute
import team.aliens.dms_android.feature.signup.SignUpIntent
import team.aliens.dms_android.feature.signup.SignUpSideEffect
import team.aliens.dms_android.feature.signup.SignUpViewModel
import team.aliens.dms_android.feature.signup.event.school.ExamineSchoolCodeEvent
import team.aliens.presentation.R

@Composable
internal fun EnterSchoolVerificationCodeScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel = hiltViewModel(),
) {

    val state by signUpViewModel.stateFlow.collectAsState()

    val schoolVerificationCode = state.schoolVerificationCode

    val focusManager = LocalFocusManager.current

    val focusRequester = remember { FocusRequester() }

    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    val onVerificationCodeChange = { verificationCode: String ->
        if (verificationCode.length == 8) {
            focusManager.clearFocus()
            signUpViewModel.postIntent(
                intent = SignUpIntent.VerifySchool.SetSchoolVerificationCode(verificationCode),
            )
        } else {
            isError = false
        }
    }

    val toast = rememberToast()

    LaunchedEffect(Unit) {
        signUpViewModel.sideEffectFlow.collect{
            when(it){
                is SignUpSideEffect.SignUpVerifySchoolSideEffect.SuccessVerifySchoolCode -> {
                    navController.navigate(DmsRoute.SignUp.SchoolVerificationQuestion)
                }

                is SignUpSideEffect.SignUpVerifySchoolSideEffect.MismatchVerifySchoolCode -> {

                }

                else -> {

                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DormTheme.colors.surface,
            )
            .padding(
                top = 108.dp,
                start = 16.dp,
                end = 16.dp,
            )
            .dormClickable(
                rippleEnabled = false,
            ) {
                focusManager.clearFocus()
            },
    ) {
        AppLogo(
            darkIcon = isSystemInDarkTheme(),
        )
        Space(space = 8.dp)
        Body2(
            text = stringResource(id = R.string.SchoolVerificationCode)
        )
        Space(space = 100.dp)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BasicTextField(
                value = schoolVerificationCode,
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
                                        color = if (schoolVerificationCode.length - 1 >= index) {
                                            DormColor.Gray600
                                        } else DormColor.Gray400,
                                    ),
                            )
                        }
                    }
                }
            )
            Space(space = 40.dp)
            Body3(
                text = if (isError) {
                    stringResource(id = R.string.NoSameCode)
                } else stringResource(id = R.string.EmailEightCode),
                color = if (isError) {
                    DormColor.Error
                } else DormColor.Gray500,
            )
            RatioSpace(height = 0.767f)
            DormContainedLargeButton(
                text = stringResource(id = R.string.Verification),
                color = DormButtonColor.Blue,
                enabled = (schoolVerificationCode.length == 8 && !isError),
            ) {
                signUpViewModel.postIntent(intent = SignUpIntent.VerifySchool)
            }
        }
    }
}

private fun getStringFromEvent(
    context: Context,
    event: ExamineSchoolCodeEvent,
): String =
    when (event) {
        is ExamineSchoolCodeEvent.BadRequestException -> {
            context.getString(R.string.BadRequest)
        }
        is ExamineSchoolCodeEvent.TooManyRequestException -> {
            context.getString(R.string.TooManyRequest)
        }
        is ExamineSchoolCodeEvent.ServerException -> {
            context.getString(R.string.ServerException)
        }
        else -> {
            context.getString(R.string.UnKnownException)
        }
    }
