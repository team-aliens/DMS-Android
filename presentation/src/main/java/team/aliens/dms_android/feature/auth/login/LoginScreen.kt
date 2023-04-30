package team.aliens.dms_android.feature.auth.login

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.button.DormTextCheckBox
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.ToastType
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Caption
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.constans.Extra
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.dms_android.util.compose.ShowToast
import team.aliens.dms_android.viewmodel.auth.login.SignInViewModel
import team.aliens.dms_android.viewmodel.auth.login.SignInViewModel.Event
import team.aliens.presentation.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    signInViewModel: SignInViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

    val keyboardController = LocalSoftwareKeyboardController.current

    var signInButtonState by remember {
        mutableStateOf(false)
    }

    var toastMessage by remember { mutableStateOf("") }

    ShowToast(
        message = toastMessage,
        toastType = ToastType.ERROR,
    )

    signInButtonState = signInViewModel.signInButtonState.collectAsState().value

    LaunchedEffect(Unit) {
        signInViewModel.signInViewEffect.collect { event ->
            when (event) {
                is Event.NavigateToHome -> {
                    navController.currentBackStackEntry?.arguments?.apply {
                        putBoolean(
                            Extra.isMealServiceEnabled,
                            event.userVisibleInformEntity.mealService
                        )
                        putBoolean(
                            Extra.isNoticeServiceEnabled,
                            event.userVisibleInformEntity.noticeService
                        )
                        putBoolean(
                            Extra.isPointServiceEnabled,
                            event.userVisibleInformEntity.pointService
                        )
                        putBoolean(
                            Extra.isStudyRoomEnabled,
                            event.userVisibleInformEntity.studyRoomService
                        )
                        putBoolean(
                            Extra.isRemainServiceEnabled,
                            event.userVisibleInformEntity.remainService
                        )
                    }
                    navController.navigate(NavigationRoute.Main) {
                        popUpTo(NavigationRoute.Login) {
                            inclusive = true
                        }
                    }
                }

                else -> {
                    toastMessage = getStringFromEvent(
                        context = context,
                        event = event,
                    )
                }
            }
        }
    }

    var autoLoginState by remember {
        mutableStateOf(false)
    }

    val onAutoLoginStateChange = { value: Boolean ->
        autoLoginState = value
        signInViewModel.state.value.autoLogin = value
    }

    var idState by remember {
        mutableStateOf("")
    }

    val onIdChange by remember {
        mutableStateOf(
            { value: String ->
                idState = value
                signInViewModel.setId(value)
            },
        )
    }

    var passwordState by remember {
        mutableStateOf("")
    }

    val onPasswordChange by remember {
        mutableStateOf(
            { value: String ->
                passwordState = value
                signInViewModel.setPassword(value)
            },
        )
    }

    Box {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = DormTheme.colors.surface)
            .padding(16.dp)
            .dormClickable(
                rippleEnabled = false,
            ) {
                focusManager.clearFocus()
            }) {

            Space(space = 92.dp)

            AppLogo(darkIcon = isSystemInDarkTheme())

            Space(space = 8.dp)

            Body2(
                text = stringResource(
                    id = R.string.AppDescription,
                ),
            )

            Space(space = 60.dp)

            // 아이디
            DormTextField(
                value = idState,
                onValueChange = onIdChange,
                hint = stringResource(id = R.string.ID),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }),
            )

            Space(space = 36.dp)

            // 비밀번호
            DormTextField(
                value = passwordState,
                onValueChange = onPasswordChange,
                isPassword = true,
                hint = stringResource(id = R.string.Password),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                }),
            )

            Space(space = 30.dp)

            DormTextCheckBox(
                modifier = Modifier.padding(
                    start = 6.dp,
                ),
                text = stringResource(id = R.string.AutoLogin),
                checked = autoLoginState,
                onCheckedChange = onAutoLoginStateChange,
            )

            Space(space = 26.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(CenterHorizontally)
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Caption(
                    text = stringResource(id = R.string.DoRegister),
                    onClick = {
                        navController.navigate(NavigationRoute.VerifySchool)
                    },
                    color = DormTheme.colors.primaryVariant,
                )

                Caption(
                    text = "|",
                    color = DormTheme.colors.primaryVariant,
                )

                Caption(
                    text = stringResource(
                        id = R.string.FindId,
                    ),
                    onClick = {
                        navController.navigate(NavigationRoute.FindId)
                    },
                    color = DormTheme.colors.primaryVariant,
                )

                Caption(
                    text = "|",
                    color = DormTheme.colors.primaryVariant,
                )

                Caption(
                    text = stringResource(
                        id = R.string.ChangePassword,
                    ),
                    onClick = {
                        navController.navigate(NavigationRoute.Identification)
                    },
                    color = DormTheme.colors.primaryVariant,
                )
            }

            Space(ratio = 1f)

            DormContainedLargeButton(
                text = stringResource(id = R.string.Login),
                color = DormButtonColor.Blue,
                enabled = signInButtonState,
                onClick = {

                    signInViewModel.disableSignInButton()

                    signInViewModel.postSignIn()
                },
            )

            Space(space = 57.dp)
        }
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