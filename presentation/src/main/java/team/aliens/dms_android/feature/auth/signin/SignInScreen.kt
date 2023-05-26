package team.aliens.dms_android.feature.auth.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.button.DormTextCheckBox
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Caption
import team.aliens.dms_android.common.LocalAvailableFeatures
import team.aliens.dms_android.common.initLocalAvailableFeatures
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.application.DmsAppState
import team.aliens.dms_android.feature.application.navigateToHome
import team.aliens.dms_android.feature.navigator.DmsRoute
import team.aliens.presentation.R

/*
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(
    navController: NavController,
    signInViewModel: SignInViewModel = hiltViewModel(),
) {

    val focusManager = LocalFocusManager.current

    val keyboardController = LocalSoftwareKeyboardController.current

    var signInButtonState by remember {
        mutableStateOf(false)
    }

    signInButtonState = signInViewModel.signInButtonState.collectAsState().value

    val localFeatures = LocalAvailableFeatures.current

    val onSignInSuccess = { features: Feature ->
        localFeatures.run {
            set(
                Extra.isMealServiceEnabled,
                features.mealService,
            )
            set(
                Extra.isNoticeServiceEnabled,
                features.noticeService,
            )
            set(
                Extra.isPointServiceEnabled,
                features.pointService,
            )
            set(
                Extra.isStudyRoomEnabled,
                features.studyRoomService,
            )
            set(
                Extra.isRemainServiceEnabled,
                features.remainsService,
            )
        }
    }

    LaunchedEffect(Unit) {
        signInViewModel.signInViewEffect.collect { event ->
            when (event) {
                is Event.NavigateToHome -> {
                    onSignInSuccess(event.features)
                    navController.navigate(DmsRoute.Home.route) {
                        popUpTo(DmsRoute.Auth.SignIn) {
                            inclusive = true
                        }
                    }
                }

                else -> {}
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
                        navController.navigate(DmsRoute.SignUp.VerifySchool)
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
                        navController.navigate(DmsRoute.Auth.FindId)
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
                        navController.navigate(DmsRoute.Auth.UserVerification)
                    },
                    color = DormTheme.colors.primaryVariant,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

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
*/

@Composable
internal fun SignInScreen(
    appState: DmsAppState,
    signInViewModel: SignInViewModel = hiltViewModel(),
) {
    val uiState by signInViewModel.uiState.collectAsStateWithLifecycle()
    val signInButtonEnabled = uiState.signInButtonEnabled
    val signInSuccess = uiState.signInSuccess
    val navController = appState.navController
    val localAvailableFeatures = LocalAvailableFeatures.current

    val onAccountIdChange = { newAccountId: String ->
        signInViewModel.onEvent(SignInUiEvent.UpdateAccountId(newAccountId))
    }
    val onPasswordChange = { newPassword: String ->
        signInViewModel.onEvent(SignInUiEvent.UpdatePassword(newPassword))
    }
    val onAutoSignInOptionChanged = { newAutoSignInOption: Boolean ->
        signInViewModel.onEvent(SignInUiEvent.UpdateAutoSignInOption(newAutoSignInOption))
    }

    val onSignUpClicked = {
        navController.navigate(DmsRoute.SignUp.route)
    }
    val onFindIdClicked = {
        navController.navigate(DmsRoute.Auth.FindId)
    }
    val onResetPasswordClicked = {
        navController.navigate(DmsRoute.Auth.ResetPassword)
    }

    val onSignInButtonClicked = {
        signInViewModel.onEvent(SignInUiEvent.SignIn)
    }

    LaunchedEffect(uiState) {
        if (signInSuccess) {
            initLocalAvailableFeatures(
                container = localAvailableFeatures,
                mealService = uiState.features.mealService,
                noticeService = uiState.features.noticeService,
                pointService = uiState.features.pointService,
                studyRoomService = uiState.features.studyRoomService,
                remainsService = uiState.features.remainsService,
            )

            appState.navigateToHome()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(Modifier.height(92.dp))
        Banner()
        Spacer(Modifier.height(60.dp))
        UserInformationInputs(
            accountIdValue = uiState.accountId,
            passwordValue = uiState.password,
            autoSignInValue = uiState.autoSignIn,
            onAccountIdChange = onAccountIdChange, // 사용자의 행위에 대한 자동 콜백 = 능동형
            onPasswordChange = onPasswordChange,
            onAutoSignInOptionChanged = onAutoSignInOptionChanged, // 사용자의 행위 = 수동형(~ed)
        )
        Spacer(Modifier.height(12.dp))
        AuthActions(
            onSignUpClicked = onSignUpClicked,
            onFindIdClicked = onFindIdClicked,
            onResetPasswordClicked = onResetPasswordClicked,
        )
        Spacer(Modifier.weight(1f))
        DormContainedLargeButton(
            modifier = Modifier.padding(
                horizontal = 16.dp,
            ),
            text = stringResource(R.string.sign_in),
            color = DormButtonColor.Blue,
            enabled = signInButtonEnabled,
            onClick = onSignInButtonClicked,
        )
        Spacer(Modifier.height(57.dp))
    }
}

@Composable
private fun Banner() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        AppLogo()
        Spacer(Modifier.height(8.dp))
        Body2(
            text = stringResource(R.string.app_description),
        )
    }
}

@Composable
private fun UserInformationInputs(
    accountIdValue: String,
    passwordValue: String,
    autoSignInValue: Boolean,
    onAccountIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onAutoSignInOptionChanged: (Boolean) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    // 아이디
    DormTextField(
        modifier = Modifier.padding(
            horizontal = 16.dp
        ),
        value = accountIdValue,
        onValueChange = onAccountIdChange,
        hint = stringResource(R.string.id),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            },
        ),
    )
    Spacer(Modifier.height(32.dp))
    // 비밀번호
    DormTextField(
        modifier = Modifier.padding(
            horizontal = 16.dp
        ),
        value = passwordValue,
        onValueChange = onPasswordChange,
        isPassword = true,
        hint = stringResource(R.string.password),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            },
        ),
    )
    Spacer(Modifier.height(28.dp))
    DormTextCheckBox(
        modifier = Modifier.padding(
            start = 12.dp,
        ),
        text = stringResource(R.string.sign_in_auto_sign_in),
        checked = autoSignInValue,
        onCheckedChange = onAutoSignInOptionChanged,
    )
    Spacer(Modifier.height(28.dp))
}

@Composable
private fun AuthActions(
    onSignUpClicked: () -> Unit,
    onFindIdClicked: () -> Unit,
    onResetPasswordClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(CenterHorizontally)
            .padding(
                horizontal = 10.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Caption(
            text = stringResource(R.string.do_sign_up),
            onClick = onSignUpClicked,
            color = DormTheme.colors.primaryVariant,
        )
        AuthActionDivider()
        Caption(
            text = stringResource(R.string.sign_in_find_id),
            onClick = onFindIdClicked,
            color = DormTheme.colors.primaryVariant,
        )
        AuthActionDivider()
        Caption(
            text = stringResource(R.string.change_password),
            onClick = onResetPasswordClicked,
            color = DormTheme.colors.primaryVariant,
        )
    }
}

@Composable
private fun AuthActionDivider() {
    Caption(
        text = "|",
        color = DormTheme.colors.primaryVariant,
    )
}
