package team.aliens.dms.android.feature.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.designsystem.Checkbox
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.TextField
import team.aliens.dms.android.core.designsystem.typography.Body2
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.composable.AppLogo
import team.aliens.dms.android.core.ui.composable.PasswordTextField
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.signin.navigation.SignInNavigator

@Destination
@Composable
internal fun SignInScreen(
    modifier: Modifier = Modifier,
    navigator: SignInNavigator,
) {
    val viewModel: SignInViewModel = hiltViewModel()

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues),
        ) {
            Banner(Modifier.fillMaxWidth())
            //UserInformationInputs(modifier = Modifier.fillMaxWidth())
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
            modifier = Modifier.padding(start = PaddingDefaults.Medium),
        )
        Body2(
            modifier = Modifier.padding(start = PaddingDefaults.Medium),
            text = stringResource(R.string.app_description),
        )
    }
}

@Composable
private fun UserInformationInputs(
    modifier: Modifier = Modifier,
    id: String,
    idChange: (String) -> Unit,
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
            value = id,
            onValueChange = idChange,
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
                id = id,
                password = password,
                idChange = onIdChange,
                onPasswordChange = onPasswordChange,
                autoSignIn = autoSignIn,
                onAutoSignInChange = onAutoSignInChange,
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