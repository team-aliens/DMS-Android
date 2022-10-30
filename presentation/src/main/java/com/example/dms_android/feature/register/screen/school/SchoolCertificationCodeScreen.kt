package com.example.dms_android.feature.register.screen.school

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.design_system.button.DormButtonColor
import com.example.design_system.button.DormContainedLargeButton
import com.example.design_system.color.DormColor
import com.example.design_system.icon.DormIcon
import com.example.design_system.typography.Body4
import com.example.design_system.typography.Body5
import com.example.dms_android.R
import com.example.dms_android.feature.register.event.school.ExamineSchoolCodeEvent
import com.example.dms_android.feature.register.screen.component.OTP_VIEW_TYPE_UNDERLINE
import com.example.dms_android.feature.register.screen.component.OtpView
import com.example.dms_android.util.EventFlow
import com.example.dms_android.util.observeWithLifecycle
import com.example.dms_android.viewmodel.auth.register.SignUpViewModel
import com.example.dms_android.viewmodel.auth.register.school.ExamineSchoolCodeViewModel

private var onBtnActive = false

@Composable
fun SchoolCertificationCodeScreen(
    scaffoldState: ScaffoldState,
    examineSchoolCodeViewModel: ExamineSchoolCodeViewModel = hiltViewModel(),
    signUpViewModel: SignUpViewModel = hiltViewModel(),
) {
    HandleViewEffect(
        scaffoldState = scaffoldState,
        effect = examineSchoolCodeViewModel.examineSchoolCodeEvent
    )
    val onTvActive by rememberSaveable {
        mutableStateOf(false)
    }
    var otpValue by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 77.dp)
        ) {
            Image(
                painter = painterResource(id = DormIcon.Applicate.drawableId),
                contentDescription = null,
                modifier = Modifier.size(49.dp),
            )
            Spacer(
                modifier = Modifier
                    .height(7.dp)
            )
            Body4(text = stringResource(R.string.SchoolVerificationCode), color = DormColor.Gray600)
            Spacer(
                modifier = Modifier
                    .height(100.dp)
            )
            OtpView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                otpText = otpValue,
                onOtpTextChange = {
                    otpValue = it
                    examineSchoolCodeViewModel.setSchoolCode(schoolCode = otpValue)
                    signUpViewModel.setSchoolCode(schoolCode = otpValue)
                    if ((examineSchoolCodeViewModel.state.value.schoolCode.length == 8)) {
                        examineSchoolCodeViewModel.examineSchoolCode(schoolCode = otpValue)
                    }
                },
                type = OTP_VIEW_TYPE_UNDERLINE,
                password = true,
                containerSize = 24.dp,
                passwordChar = "⚫",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                charColor = DormColor.Gray600,
            )
            Spacer(
                modifier = Modifier
                    .height(40.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Body5(
                    text = if (!onTvActive) stringResource(R.string.EmailEightCode) else
                        stringResource(
                            R.string.NotSameName
                        ),
                    color = if (!onTvActive) DormColor.Gray500 else DormColor.Error,
                )
            }

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    DormContainedLargeButton(
                        text = stringResource(R.string.verification),
                        color = DormButtonColor.Blue,
                        enabled = onBtnActive,
                        onClick = {
                            //TODO : 다음페이지로 넘어가기
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HandleViewEffect(
    scaffoldState: ScaffoldState,
    effect: EventFlow<ExamineSchoolCodeEvent>,
) {
    val badRequestComment = stringResource(id = R.string.LoginBadRequest)
    val unAuthorizedComment = stringResource(id = R.string.LoginUnAuthorized)
    val tooManyRequestComment = stringResource(id = R.string.TooManyRequest)
    val serverException = stringResource(id = R.string.ServerException)
    val unKnownException = stringResource(id = R.string.UnKnownException)

    effect.observeWithLifecycle(action = {
        when (it) {
            is ExamineSchoolCodeEvent.ExamineSchoolCodeSuccess -> {
                onBtnActive = true
            }

            is ExamineSchoolCodeEvent.BadRequestException -> {
                onBtnActive = false
                scaffoldState.snackbarHostState.showSnackbar(
                    message = badRequestComment,
                    duration = SnackbarDuration.Short
                )
            }

            is ExamineSchoolCodeEvent.UnAuthorizedException -> {
                onBtnActive = false
                scaffoldState.snackbarHostState.showSnackbar(
                    message = unAuthorizedComment,
                    duration = SnackbarDuration.Short
                )
            }

            is ExamineSchoolCodeEvent.TooManyRequestException -> {
                onBtnActive = false
                scaffoldState.snackbarHostState.showSnackbar(
                    message = tooManyRequestComment,
                    duration = SnackbarDuration.Short
                )
            }

            is ExamineSchoolCodeEvent.InternalServerException -> {
                onBtnActive = false
                scaffoldState.snackbarHostState.showSnackbar(
                    message = serverException,
                    duration = SnackbarDuration.Short
                )
            }

            is ExamineSchoolCodeEvent.UnknownException -> {
                onBtnActive = false
                scaffoldState.snackbarHostState.showSnackbar(
                    message = unKnownException,
                    duration = SnackbarDuration.Short
                )
            }

            else -> {
                onBtnActive = false
            }
        }
    })
}

