package team.aliens.dms.android.feature.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.designsystem.Checkbox
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.core.designsystem.TextField
import team.aliens.dms.android.core.designsystem.clickable
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.composable.AppLogo
import team.aliens.dms.android.core.ui.composable.PasswordTextField
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature._legacy.extension.collectInLaunchedEffectWithLifeCycle
import team.aliens.dms.android.feature.signin.navigation.SignInNavigator

@Destination
@Composable
internal fun SignInScreen(
    modifier: Modifier = Modifier,
    navigator: SignInNavigator,
) {
    val viewModel: SignInViewModel = hiltViewModel()
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    val toast = LocalToast.current

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifeCycle { sideEffect ->
        when (sideEffect) {

            SignInSideEffect.Failure -> {
                // TODO
                toast.showErrorToast("실패")
            }

            is SignInSideEffect.IdError -> {}
            SignInSideEffect.Loading -> {}
            is SignInSideEffect.PasswordError -> {}
            SignInSideEffect.Success -> {
                navigator.openFindId()
            }
        }
    }

    DmsScaffold(
        modifier = modifier.fillMaxSize(),
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Banner(Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.weight(1f))
            UserInformationInputs(
                modifier = Modifier.fillMaxWidth(),
                accountId = uiState.accountId,
                password = uiState.password,
                onAccountIdChange = { accountId ->
                    viewModel.postIntent(SignInIntent.UpdateId(accountId))
                },
                onPasswordChange = { password ->
                    viewModel.postIntent(SignInIntent.UpdatePassword(password))
                },
                autoSignIn = uiState.autoSignIn,
                onAutoSignInChange = { autoSignIn ->
                    viewModel.postIntent(SignInIntent.UpdateAutoSignInOption(autoSignIn))
                },
            )
            UnauthorizedActions(
                modifier = Modifier.fillMaxWidth(),
                onSignUp = navigator::openSignUpNav,
                onFindId = navigator::openFindId,
                onResetPassword = navigator::openResetPasswordNav,
            )
            Spacer(modifier = Modifier.weight(3f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = { viewModel.postIntent(SignInIntent.SignIn) },
                enabled = uiState.signInButtonAvailable,
            ) {
                Text(text = "로그인")
            }
        }
    }
}

@Composable
private fun Banner(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
    ) {
        AppLogo(
            modifier = Modifier.padding(start = PaddingDefaults.Large),
        )
        Text(
            modifier = Modifier.padding(start = PaddingDefaults.Large),
            text = stringResource(R.string.app_description),
            style = DmsTheme.typography.body2,
        )
    }
}

@Composable
private fun UserInformationInputs(
    modifier: Modifier = Modifier,
    accountId: String,
    onAccountIdChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    autoSignIn: Boolean,
    onAutoSignInChange: (Boolean) -> Unit,
    idError: Exception? = null,
    passwordError: Exception? = null,
) {
    val (passwordShowing, onPasswordShowingChange) = remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(),
            value = accountId,
            onValueChange = onAccountIdChange,
            hint = { Text(text = "아이디") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )

        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(),
            value = password,
            onValueChange = onPasswordChange,
            hintText = "비밀번호",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            passwordShowing = passwordShowing,
            onPasswordShowingChange = onPasswordShowingChange,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = autoSignIn,
                onCheckedChange = onAutoSignInChange,
            )
            Text(
                text = "자동 로그인",
                style = DmsTheme.typography.body2,
                color = DmsTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun UnauthorizedActions(
    modifier: Modifier = Modifier,
    onSignUp: () -> Unit,
    onFindId: () -> Unit,
    onResetPassword: () -> Unit,
) {
    ProvideTextStyle(
        value = DmsTheme.typography.caption.copy(
            color = DmsTheme.colorScheme.onSurfaceVariant,
        ),
    ) {
        Row(
            modifier = Modifier.padding(top = PaddingDefaults.Medium) then modifier,
            horizontalArrangement = Arrangement.spacedBy(
                space = DefaultHorizontalSpace,
                alignment = Alignment.CenterHorizontally,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .clip(DmsTheme.shapes.small)
                    .clickable(onClick = onSignUp),
                text = "회원가입",
            )
            Divider(
                modifier = Modifier.size(
                    width = 1.dp,
                    height = 12.dp,
                ),
                color = DmsTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                modifier = Modifier
                    .clip(DmsTheme.shapes.small)
                    .clickable(onClick = onFindId),
                text = "아이디 찾기",
            )
            Divider(
                modifier = Modifier.size(
                    width = 1.dp,
                    height = 12.dp,
                ),
                color = DmsTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                modifier = Modifier
                    .clip(DmsTheme.shapes.small)
                    .clickable(onClick = onResetPassword),
                text = "비밀번호 변경",
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SignInPreview() {
    val (id, onIdChange) = remember { mutableStateOf("") }
    val (password, onPasswordChange) = remember { mutableStateOf("") }
    val (autoSignIn, onAutoSignInChange) = remember { mutableStateOf(false) }
    var buttonAvailable by remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()
    fun disableButton() {
        coroutineScope.launch {
            buttonAvailable = false
            delay(3000L)
            buttonAvailable = true
        }
    }

    Scaffold { padValues ->
        Column(
            modifier = Modifier
                .background(DmsTheme.colorScheme.background)
                .fillMaxSize()
                .padding(padValues)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Banner(Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.weight(1f))
            UserInformationInputs(
                modifier = Modifier.fillMaxWidth(),
                accountId = id,
                password = password,
                onAccountIdChange = onIdChange,
                onPasswordChange = onPasswordChange,
                autoSignIn = autoSignIn,
                onAutoSignInChange = onAutoSignInChange,
            )
            UnauthorizedActions(
                modifier = Modifier.fillMaxWidth(),
                onSignUp = {},
                onFindId = {},
                onResetPassword = {},
            )
            Spacer(modifier = Modifier.weight(5f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = { disableButton() },
                enabled = buttonAvailable,
            ) {
                Text(text = "로그인")
            }
        }
    }
}

/*

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
            color = DmsTheme.colors.primaryVariant,
        )
        AuthActionDivider()
        Caption(
            text = stringResource(R.string.sign_in_find_id),
            onClick = onFindIdClicked,
            color = DmsTheme.colors.primaryVariant,
        )
        AuthActionDivider()
        Caption(
            text = stringResource(R.string.change_password),
            onClick = onResetPasswordClicked,
            color = DmsTheme.colors.primaryVariant,
        )
    }
}

@Composable
private fun AuthActionDivider() {
    Caption(
        text = "|",
        color = DmsTheme.colors.primaryVariant,
    )
}
*/