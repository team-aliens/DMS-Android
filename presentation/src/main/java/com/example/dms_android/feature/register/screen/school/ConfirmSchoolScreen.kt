package com.example.dms_android.feature.register.screen.school

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.design_system.button.DormButtonColor
import com.example.design_system.button.DormContainedLargeButton
import com.example.design_system.color.DormColor
import com.example.design_system.icon.DormIcon
import com.example.design_system.textfield.DormTextField
import com.example.design_system.typography.Body4
import com.example.design_system.typography.ButtonText
import com.example.design_system.typography.Caption
import com.example.dms_android.R
import com.example.dms_android.feature.register.event.school.CompareSchoolAnswerEvent
import com.example.dms_android.feature.register.event.school.ExamineSchoolCodeEvent
import com.example.dms_android.feature.register.event.school.SchoolQuestionEvent
import com.example.dms_android.util.EventFlow
import com.example.dms_android.util.observeWithLifecycle
import com.example.dms_android.viewmodel.auth.register.SignUpViewModel
import com.example.dms_android.viewmodel.auth.register.school.CompareSchoolAnswerViewModel
import com.example.dms_android.viewmodel.auth.register.school.SchoolQuestionViewModel

var onActive = false

@Composable
fun ConfirmSchoolScreen(
    scaffoldState: ScaffoldState,
    schoolQuestionViewModel: SchoolQuestionViewModel = hiltViewModel(),
    compareSchoolAnswerViewModel: CompareSchoolAnswerViewModel = hiltViewModel(),
    signUpViewModel: SignUpViewModel = hiltViewModel(),
) {
    HandleViewEffect(
        scaffoldState = scaffoldState,
        effect = schoolQuestionViewModel.schoolQuestionEvent,
    )
    HandleViewEffectTwo(
        scaffoldState = scaffoldState,
        effect = compareSchoolAnswerViewModel.compareSchoolAnswerEvent,
    )
    var replyValue by remember { mutableStateOf("") }
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
            Body4(
                text = stringResource(R.string.question_confirm_school),
                color = DormColor.Gray600,
            )
            Spacer(
                modifier = Modifier
                    .height(60.dp)
            )
            Body4(
                text = TODO("여기에 그 질문 넣어야되는데 어떻게 할지 모르겠음"),
                color = DormColor.Gray700
            )
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            DormTextField(
                value = replyValue,
                onValueChange = {
                    replyValue = it
                    compareSchoolAnswerViewModel.setSchoolAnswer(schoolAnswer = replyValue)
                    compareSchoolAnswerViewModel.compareSchoolAnswer()
                    signUpViewModel.setSchoolAnswer(schoolAnswer = replyValue)
                },
                error = if (onActive) null else stringResource(R.string.inconsistent_school_reply),
                hint = stringResource(R.string.reply),
            )
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 24.dp),
            ) {
                Column() {

                    DormContainedLargeButton(
                        text = stringResource(R.string.verification),
                        color = DormButtonColor.Blue,
                        enabled = onActive,
                    ) {
                    }
                    Spacer(
                        modifier = Modifier
                            .height(17.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 88.dp)
                    ) {
                        Caption(
                            text = stringResource(R.string.already_account),
                            color = DormColor.Gray500
                        )
                        Spacer(
                            modifier = Modifier
                                .width(8.dp)
                        )
                        ButtonText(
                            text = stringResource(R.string.login),
                            color = DormColor.Gray600,
                            onClick = {
                                //TODO: 로그인 화면으로 넘어가기
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HandleViewEffect(
    scaffoldState: ScaffoldState,
    effect: EventFlow<SchoolQuestionEvent>,
) {
    val badRequestComment = stringResource(id = R.string.LoginBadRequest)
    val notFoundComment = stringResource(id = R.string.LoginNotFound)
    val tooManyRequestComment = stringResource(id = R.string.TooManyRequest)
    val serverException = stringResource(id = R.string.ServerException)
    val unKnownException = stringResource(id = R.string.UnKnownException)

    effect.observeWithLifecycle(action = {
        when (it) {
            is SchoolQuestionEvent.SchoolQuestionSuccess -> {
                //TODO: 여기서 schoolQuestion띄워주는 코드 작성하는게 맞는거지?
            }

            is SchoolQuestionEvent.BadRequestException -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = badRequestComment,
                    duration = SnackbarDuration.Short
                )
            }

            is SchoolQuestionEvent.NotFoundException -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = notFoundComment,
                    duration = SnackbarDuration.Short
                )
            }

            is SchoolQuestionEvent.TooManyRequestException -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = tooManyRequestComment,
                    duration = SnackbarDuration.Short
                )
            }

            is SchoolQuestionEvent.InternalServerException -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = serverException,
                    duration = SnackbarDuration.Short
                )
            }

            is SchoolQuestionEvent.UnknownException -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = unKnownException,
                    duration = SnackbarDuration.Short
                )
            }

            else -> {
            }
        }
    })
}

@Composable
fun HandleViewEffectTwo(
    scaffoldState: ScaffoldState,
    effect: EventFlow<CompareSchoolAnswerEvent>,
) {
    val badRequestComment = stringResource(id = R.string.LoginBadRequest)
    val notFoundComment = stringResource(id = R.string.LoginNotFound)
    val unAuthorizedComment = stringResource(id = R.string.LoginUnAuthorized)
    val tooManyRequestComment = stringResource(id = R.string.TooManyRequest)
    val serverException = stringResource(id = R.string.ServerException)
    val unKnownException = stringResource(id = R.string.UnKnownException)

    effect.observeWithLifecycle(action = {
        when (it) {
            is CompareSchoolAnswerEvent.CompareSchoolAnswerSuccess -> {
                onActive = true
            }

            is CompareSchoolAnswerEvent.BadRequestException -> {
                onActive = false
                scaffoldState.snackbarHostState.showSnackbar(
                    message = badRequestComment,
                    duration = SnackbarDuration.Short
                )
            }

            is CompareSchoolAnswerEvent.UnAuthorizedException -> {
                onActive = false
                scaffoldState.snackbarHostState.showSnackbar(
                    message = unAuthorizedComment,
                    duration = SnackbarDuration.Short
                )
            }

            is CompareSchoolAnswerEvent.NotFoundException -> {
                onActive = false
                scaffoldState.snackbarHostState.showSnackbar(
                    message = notFoundComment,
                    duration = SnackbarDuration.Short
                )
            }

            is CompareSchoolAnswerEvent.TooManyRequestException -> {
                onActive = false
                scaffoldState.snackbarHostState.showSnackbar(
                    message = tooManyRequestComment,
                    duration = SnackbarDuration.Short
                )
            }

            is CompareSchoolAnswerEvent.InternalServerException -> {
                onActive = false
                scaffoldState.snackbarHostState.showSnackbar(
                    message = serverException,
                    duration = SnackbarDuration.Short
                )
            }

            is CompareSchoolAnswerEvent.UnKnownException -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = unKnownException,
                    duration = SnackbarDuration.Short
                )
            }

            else -> {
                onActive = false
            }
        }
    })
}





