package team.aliens.dms_android.feature.auth.login

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
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
    navController: NavController,
    signInViewModel: SignInViewModel = hiltViewModel(),
) {

    val toast = rememberToast()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        signInViewModel.signInViewEffect.collect { event ->
            when (event) {
                Event.NavigateToHome -> {
                    navController.navigate(NavigationRoute.Main) {
                        popUpTo(NavigationRoute.Login) {
                            inclusive = true
                        }
                    }
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

    var idState by remember {
        mutableStateOf("")
    }

    val onIdChange = { value: String ->
        idState = value
        signInViewModel.setId(value)
    }

    var passwordState by remember {
        mutableStateOf("")
    }

    val onPasswordChange = { value: String ->
        passwordState = value
        signInViewModel.setPassword(value)
    }

    var autoLoginState by remember {
        mutableStateOf(false)
    }

    val onAutoLoginStateChange = { value: Boolean ->
        autoLoginState = value
        signInViewModel.state.value.autoLogin = value
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DormColor.Gray100)
            .padding(
                top = 92.dp,
                start = 16.dp,
                end = 16.dp,
            ),
    ) {

        Image(
            modifier = Modifier
                .height(34.dp)
                .width(97.dp),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
        )

        Spacer(
            modifier = Modifier.height(8.dp),
        )

        Body4(
            text = stringResource(
                id = R.string.AppDescription,
            ),
        )

        Spacer(
            modifier = Modifier.height(52.dp),
        )

        DormTextField(
            value = idState,
            onValueChange = onIdChange,
            hint = stringResource(id = R.string.ID),
        )

        Spacer(
            modifier = Modifier.height(36.dp),
        )

        DormTextField(
            value = passwordState,
            onValueChange = onPasswordChange,
            isPassword = true,
            hint = stringResource(id = R.string.Password),
        )

        Spacer(
            modifier = Modifier.height(30.dp),
        )

        DormTextCheckBox(
            modifier = Modifier.padding(
                start = 6.dp,
            ),
            text = stringResource(id = R.string.AutoLogin),
            checked = autoLoginState,
            onCheckedChange = onAutoLoginStateChange,
        )

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
                    context.startActivity(
                        Intent(
                            context,
                            RegisterActivity::class.java,
                        ),
                    )
                },
            )

            Caption(text = "|")

            Caption(
                text = stringResource(
                    id = R.string.FindId,
                ),
                onClick = {
                    // todo implement and link find id screen
                },
            )

            Caption(text = "|")

            Caption(
                text = stringResource(
                    id = R.string.ChangePassword,
                ),
                onClick = {
                    navController.navigate(NavigationRoute.ChangePassword)
                },
            )
        }

        Spacer(
            modifier = Modifier.weight(1f),
        )

        DormContainedLargeButton(
            text = stringResource(id = R.string.Login),
            color = DormButtonColor.Blue,
            onClick = {

                if (signInViewModel.state.value.id.isBlank()) {

                    toast(
                        context.getString(R.string.PleaseEnterId)
                    )

                    return@DormContainedLargeButton
                }

                if (signInViewModel.state.value.password.isBlank()) {

                    toast(
                        context.getString(R.string.PleaseEnterPassword)
                    )

                    return@DormContainedLargeButton
                }

                signInViewModel.postSignIn()
            },
        )

        Spacer(
            modifier = Modifier.height(57.dp),
        )
    }
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
