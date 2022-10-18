package com.example.dms_android.feature.auth.login

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.design_system.button.DormButtonColor
import com.example.dms_android.R
import com.example.design_system.color.DormColor
import com.example.design_system.textfield.DormTextField
import com.example.design_system.button.DormTextCheckBox
import com.example.design_system.button.DormContainedLargeButton
import com.example.design_system.typography.Body4
import com.example.design_system.typography.Body6
import com.example.design_system.typography.Title5
import com.example.dms_android.util.EventFlow
import com.example.dms_android.util.observeWithLifecycle
import com.example.dms_android.viewmodel.auth.login.SignInViewModel

@Composable
fun LoginScreen(
    scaffoldState: ScaffoldState,
    signInViewModel: SignInViewModel = hiltViewModel()
) {
    HandleViewEffect(
        scaffoldState = scaffoldState,
        effect = signInViewModel.signInViewEvent
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DormColor.Gray100),
    ) {
        MainTitle()
        TextField(signInViewModel)
        AutoLogin()
        AddFunction()
        LoginButton(signInViewModel, scaffoldState)
    }
}

@Composable
private fun HandleViewEffect(
    effect: EventFlow<SignInEvent>,
    scaffoldState: ScaffoldState
) {
    val badRequestComment = "잘못된 형식의 요청입니다. 다시 시도해주세요."
    val unAuthorizedComment = "잘못된 비밀번호입니다. 다시 시도해주세요."
    val notFoundComment = "해당 아이디는 존재하지 않는 아이디입니다."
    val tooManyRequestComment = "너무 많은 요청이 감지되었습니다. 잠시뒤에 다시 시도해주세요."
    val serverException = "서버와의 통신에서 오류가 발생하였습니다. 잠시 후에 다시 시도해주세요."
    val unKnownException = "알 수 없는 에러가 발생하였습니다."

    effect.observeWithLifecycle(action = {
        when (it) {
            is SignInEvent.SignInSuccess -> {
                TODO("FeatureNavigator")
            }

            is SignInEvent.BadRequestException -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = badRequestComment,
                    duration = SnackbarDuration.Short
                )
            }

            is SignInEvent.UnAuthorizedException -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = unAuthorizedComment,
                    duration = SnackbarDuration.Short
                )
            }

            is SignInEvent.NotFoundException -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = notFoundComment,
                    duration = SnackbarDuration.Short
                )
            }

            is SignInEvent.TooManyRequestException -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = tooManyRequestComment,
                    duration = SnackbarDuration.Short
                )
            }

            is SignInEvent.InternalServerException -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = serverException,
                    duration = SnackbarDuration.Short
                )
            }

            is SignInEvent.UnKnownException -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = unKnownException,
                    duration = SnackbarDuration.Short
                )
            }

            else -> {}
        }
    })
    BackPressHandle()
}

@Composable
private fun BackPressHandle() {
    val backHandlingEnabled by remember { mutableStateOf(true) }
    val activity = (LocalContext.current as? Activity)
    BackHandler(backHandlingEnabled) {
        activity?.finish()
    }
}

@Composable
fun MainTitle() {
    Box(
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                modifier = Modifier
                    .padding(
                        top = 56.dp,
                        bottom = 7.dp
                    )
                    .height(85.dp)
                    .width(85.dp),
                painter = painterResource(id = R.drawable.ic_information_toast),
                contentDescription = "MainLogo",
            )
            Title5(text = "DMS For Android")
            Spacer(
                modifier = Modifier
                    .height(1.dp)
            )
            Body4(text = "더 편한 기숙사 생활을 위해")
        }
    }
}

@Composable
fun TextField(
    signInViewModel: SignInViewModel
) {

    var idValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }

    Box(
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 6.dp,
                    end = 16.dp
                ),
        ) {
            Spacer(
                modifier = Modifier
                    .height(52.dp),
            )
            DormTextField(
                value = idValue,
                onValueChange = {
                    idValue = it
                    signInViewModel.setId(idValue)
                },
                hint = stringResource(id = R.string.Login),
            )
            Spacer(
                modifier = Modifier
                    .height(36.dp),
            )
            DormTextField(
                value = passwordValue,
                onValueChange = {
                    passwordValue = it
                    signInViewModel.setPassword(passwordValue)
                },
                isPassword = true,
                hint = stringResource(id = R.string.Password),
            )
        }
    }
}

@Composable
fun AutoLogin() {

    var checked by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.TopStart,
    ) {
        Column(
            modifier = Modifier
                .padding(start = 22.dp),
        ) {
            Spacer(
                modifier = Modifier
                    .height(25.dp),
            )
            DormTextCheckBox(
                text = stringResource(id = R.string.AutoLogin),
                checked = checked,
                onCheckedChange = {
                    checked = !checked
                },
            )
        }
    }
}

@Composable
fun AddFunction() {
    Box(
        contentAlignment = Alignment.TopCenter,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(CenterHorizontally)
                .padding(
                    start = 10.dp,
                    top = 24.dp,
                    end = 10.dp,
                ),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Body6(text = "회원가입하기")
            Body6(text = "|")
            Body6(text = "아이디 찾기")
            Body6(text = "|")
            Body6(text = "비밀번호 변경")
        }
    }
}

@Composable
fun LoginButton(
    signInViewModel: SignInViewModel,
    scaffoldState: ScaffoldState,
) {
    val blankComment = "아이디와 비밀번호를 입력해주세요."

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 60.dp,
            )
            .fillMaxSize(),
    ) {
        DormContainedLargeButton(
            text = stringResource(id = R.string.Login),
            color = DormButtonColor.Blue,
        ) {
            if ((signInViewModel.state.value.id != "") && (signInViewModel.state.value.password != "")) {
                signInViewModel.signIn()
            }
        }
    }
}
