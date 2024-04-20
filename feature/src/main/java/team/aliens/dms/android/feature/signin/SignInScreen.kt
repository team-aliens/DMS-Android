package team.aliens.dms.android.feature.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.core.designsystem.TextField
import team.aliens.dms.android.core.designsystem.clickable
import team.aliens.dms.android.core.ui.Banner
import team.aliens.dms.android.core.ui.BannerDefaults
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.collectInLaunchedEffectWithLifecycle
import team.aliens.dms.android.core.ui.composable.PasswordTextField
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.data.auth.exception.AuthException
import team.aliens.dms.android.data.auth.exception.BadRequestException
import team.aliens.dms.android.data.auth.exception.PasswordMismatchException
import team.aliens.dms.android.data.auth.exception.UserNotFoundException
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.signin.navigation.SignInNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun SignInScreen(
    modifier: Modifier = Modifier,
    navigator: SignInNavigator,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    val toast = LocalToast.current
    val context = LocalContext.current

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            SignInSideEffect.Failure -> toast.showErrorToast(context.getString(R.string.sign_in_error))
            SignInSideEffect.Loading -> {}
            SignInSideEffect.Success -> navigator.openAuthorizedNav()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            DmsTopAppBar(
                title = { /* explicit blank */ },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
            )
        },
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues)
                .imePadding(),
        ) {
            Banner(
                modifier = Modifier
                    .fillMaxWidth()
                    .startPadding()
                    .topPadding(BannerDefaults.DefaultTopSpace),
                message = {
                    Text(text = stringResource(R.string.app_description))
                },
            )
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
                accountIdError = uiState.accountIdError,
                passwordError = uiState.passwordError,
            )
            UnauthorizedActions(
                modifier = Modifier.fillMaxWidth(),
                onSignUp = navigator::openSignUpNav,
                onFindId = navigator::openFindId,
                onResetPassword = navigator::openResetPasswordNav,
            )
            Spacer(modifier = Modifier.weight(2f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = { viewModel.postIntent(SignInIntent.SignIn) },
                enabled = uiState.signInButtonAvailable,
            ) {
                Text(text = stringResource(id = R.string.sign_in))
            }
        }
    }
}

@Composable
private fun UserInformationInputs(
    modifier: Modifier = Modifier,
    accountId: String,
    onAccountIdChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    accountIdError: AuthException? = null,
    passwordError: AuthException? = null,
) {
    val (passwordShowing, onPasswordShowingChange) = remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
    ) {
        val shouldShowAccountIdError = accountIdError != null
        val shouldShowPasswordError = passwordError != null

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(),
            value = accountId,
            isError = shouldShowAccountIdError,
            supportingText = if (shouldShowAccountIdError) {
                {
                    Text(
                        text = stringResource(
                            id = when (accountIdError) {
                                is UserNotFoundException -> R.string.sign_in_error_user_not_found
                                is BadRequestException -> R.string.sign_in_error_check_format
                                else -> R.string.error_unknown
                            },
                        ),
                    )
                }
            } else {
                null
            },
            onValueChange = onAccountIdChange,
            hint = { Text(text = stringResource(id = R.string.id)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )

        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(),
            value = password,
            isError = shouldShowPasswordError,
            supportingText = if (shouldShowPasswordError) {
                {
                    Text(
                        text = stringResource(
                            id = when (passwordError) {
                                is PasswordMismatchException -> R.string.sign_in_error_password_mismatch
                                is BadRequestException -> R.string.sign_in_error_check_format
                                else -> R.string.error_unknown
                            },
                        )
                    )
                }
            } else {
                null
            },
            onValueChange = onPasswordChange,
            hintText = stringResource(id = R.string.password),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            passwordShowing = passwordShowing,
            onPasswordShowingChange = onPasswordShowingChange,
        )
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
                text = stringResource(id = R.string.sign_in_sign_up),
            )
            VerticalDivider(
                modifier = Modifier.height(12.dp),
                color = DmsTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                modifier = Modifier
                    .clip(DmsTheme.shapes.small)
                    .clickable(onClick = onFindId),
                text = stringResource(id = R.string.sign_in_find_id),
            )
            VerticalDivider(
                modifier = Modifier.height(12.dp),
                color = DmsTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                modifier = Modifier
                    .clip(DmsTheme.shapes.small)
                    .clickable(onClick = onResetPassword),
                text = stringResource(id = R.string.sign_in_reset_password),
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SignInPreview() {
    val (id, onIdChange) = remember { mutableStateOf("") }
    val (password, onPasswordChange) = remember { mutableStateOf("") }
    var buttonAvailable by remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()
    fun disableButton() {
        coroutineScope.launch {
            buttonAvailable = false
            delay(3000L)
            buttonAvailable = true
        }
    }

    androidx.compose.material3.Scaffold { padValues ->
        Column(
            modifier = Modifier
                .background(DmsTheme.colorScheme.background)
                .fillMaxSize()
                .padding(padValues)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Banner(
                modifier = Modifier
                    .fillMaxWidth()
                    .topPadding(BannerDefaults.DefaultTopSpace),
                message = {
                    Text(text = stringResource(R.string.app_description))
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            UserInformationInputs(
                modifier = Modifier.fillMaxWidth(),
                accountId = id,
                password = password,
                onAccountIdChange = onIdChange,
                onPasswordChange = onPasswordChange,
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
