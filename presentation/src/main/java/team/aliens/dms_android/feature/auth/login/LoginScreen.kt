package team.aliens.dms_android.feature.auth.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.button.DormTextCheckBox
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body4
import team.aliens.design_system.typography.Caption
import team.aliens.dms_android.feature.RegisterActivity
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.dms_android.viewmodel.auth.login.SignInViewModel
import team.aliens.dms_android.viewmodel.auth.login.SignInViewModel.Event
import team.aliens.presentation.R

@Composable
fun LoginScreen(
    scaffoldState: ScaffoldState,
    navController: NavController,
    signInViewModel: SignInViewModel = hiltViewModel(),
) {

    val toast = rememberToast()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        signInViewModel.signInViewEffect.collect { event ->
            when (event) {
                Event.NavigateToHome -> {
                    navController.navigate(route = NavigationRoute.Main)
                }
                else -> {
                    toast(
                        getStringFromEvent(
                            context = context,
                            event = event,
                        ),
                    )
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
        AutoLogin(signInViewModel)
        AddFunction()
        LoginButton(signInViewModel, scaffoldState)
    }

    BackPressHandle()
}

private fun getStringFromEvent(
    context: Context,
    event: Event,
): String {
    return context.getString(
        when (event) {
            Event.WrongRequest -> R.string.BadRequest
            Event.NotCorrectPassword -> R.string.CheckPassword
            Event.UserNotFound -> R.string.LoginNotFound
            Event.TooManyRequest -> R.string.TooManyRequest
            Event.ServerException -> R.string.ServerException
            Event.NoInternetException -> R.string.NoInternetException
            Event.UnKnownException -> R.string.UnKnownException
            else -> throw IllegalArgumentException()
        },
    )
}

@Composable
private fun BackPressHandle() {
    val backHandlingEnabled by remember { mutableStateOf(true) }
    val activity = (LocalContext.current as Activity)
    BackHandler(backHandlingEnabled) {
        activity.finish()
    }
}

@Composable
fun MainTitle() {
    Column(
        modifier = Modifier.padding(start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Image(
            modifier = Modifier
                .padding(top = 92.dp)
                .height(34.dp)
                .width(97.dp),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
        )
        Body4(
            text = stringResource(
                id = R.string.AppDescription,
            ),
        )
    }
}

@Composable
fun TextField(
    signInViewModel: SignInViewModel,
) {

    var idValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }

    Box(contentAlignment = Alignment.TopStart) {
        Column(
            modifier = Modifier.padding(start = 16.dp, top = 6.dp, end = 16.dp),
        ) {
            Spacer(
                modifier = Modifier.height(52.dp),
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
                modifier = Modifier.height(36.dp),
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
fun AutoLogin(
    signInViewModel: SignInViewModel,
) {

    var checked by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.TopStart,
    ) {
        Column(
            modifier = Modifier.padding(start = 22.dp),
        ) {
            Spacer(
                modifier = Modifier.height(25.dp),
            )
            DormTextCheckBox(
                text = stringResource(id = R.string.AutoLogin),
                checked = checked,
                onCheckedChange = {
                    checked = !checked
                    signInViewModel.state.value.autoLogin = checked
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
                text = stringResource(id = R.string.DoRegister),
                onClick = {
                    mContext.startActivity(Intent(mContext, RegisterActivity::class.java))
                },
            )
            Caption(text = "|")
            Caption(text = stringResource(id = R.string.FindId))
            Caption(text = "|")
            Caption(text = stringResource(id = R.string.ChangePassword))
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
        DormContainedLargeButton(text = stringResource(id = R.string.Login),
            color = DormButtonColor.Blue,
            onClick = {
                if (signInViewModel.state.value.id != "" && signInViewModel.state.value.password != "") {
                    signInViewModel.postSignIn()
                }
            })
    }
}
