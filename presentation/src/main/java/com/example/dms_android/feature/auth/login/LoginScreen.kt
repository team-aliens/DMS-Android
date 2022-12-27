package com.example.dms_android.feature.auth.login

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.design_system.button.DormButtonColor
import com.example.design_system.button.DormContainedLargeButton
import com.example.design_system.button.DormTextCheckBox
import com.example.design_system.color.DormColor
import com.example.design_system.textfield.DormTextField
import com.example.design_system.toast.rememberToast
import com.example.design_system.typography.Body4
import com.example.design_system.typography.ButtonText
import com.example.design_system.typography.Caption
import com.example.design_system.typography.SubTitle2
import com.example.dms_android.R
import com.example.dms_android.feature.RegisterActivity
import com.example.dms_android.feature.navigator.NavigationRoute
import com.example.dms_android.viewmodel.auth.login.SignInViewModel
import com.example.dms_android.viewmodel.auth.login.SignInViewModel.Event

@Composable
fun LoginScreen(
    scaffoldState: ScaffoldState,
    navController: NavController,
    signInViewModel: SignInViewModel = hiltViewModel(),
) {

    val toast = rememberToast()

    val badRequestComment = stringResource(id = R.string.LoginBadRequest)
    val unAuthorizedComment = stringResource(id = R.string.LoginUnAuthorized)
    val notFoundComment = stringResource(id = R.string.LoginNotFound)
    val tooManyRequestComment = stringResource(id = R.string.TooManyRequest)
    val serverException = stringResource(id = R.string.ServerException)
    val noInternetException = stringResource(id = R.string.NoInternetException)

    LaunchedEffect(Unit) {
        signInViewModel.signInViewEffect.collect {
            when (it) {
                is Event.NavigateToHome -> {
                    navController.navigate(
                        route = NavigationRoute.Main
                    )
                }

                is Event.WrongRequest -> {
                    toast(badRequestComment)
                }

                is Event.NotCorrectPassword -> {
                    toast(unAuthorizedComment)
                }

                is Event.UserNotFound -> {
                    toast(notFoundComment)
                }

                is Event.TooManyRequest -> {
                    toast(tooManyRequestComment)
                }

                is Event.ServerException -> {
                    toast(serverException)
                }

                is Event.NoInternetException -> {
                    toast(noInternetException)
                }

                is Event.UnKnownException -> {
                    toast(noInternetException)
                }
            }
        }
    }

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
                        top = 92.dp
                    )
                    .height(34.dp)
                    .width(97.dp),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "MainLogo",
            )
            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )
            Body4(text = "더 편한 기숙사 생활을 위해")
        }
    }
}

@Composable
fun TextField(
    signInViewModel: SignInViewModel,
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
    val mContext = LocalContext.current
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
            Caption(
                text = "회원가입하기",
                onClick = {
                    mContext.startActivity(Intent(mContext, RegisterActivity::class.java))
                },
            )
            Caption(text = "|")
            Caption(text = "아이디 찾기")
            Caption(text = "|")
            Caption(text = "비밀번호 변경")
        }
    }
}

@Composable
fun LoginButton(
    signInViewModel: SignInViewModel,
    scaffoldState: ScaffoldState,
) {

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
            onClick = {
                if (signInViewModel.state.value.id != "" && signInViewModel.state.value.password != "") {
                    signInViewModel.postSignIn()
                }
            }
        )
    }
}
