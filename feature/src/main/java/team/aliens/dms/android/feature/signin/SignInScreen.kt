package team.aliens.dms.android.feature.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.TextField
import team.aliens.dms.android.core.designsystem.typography.Body2
import team.aliens.dms.android.core.ui.composable.AppLogo
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
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AppLogo(
            modifier = Modifier.padding(start = 16.dp),
        )
        Body2(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(R.string.app_description)
        )
    }
}

@Composable
private fun UserInformationInputs(
    modifier: Modifier = Modifier,
    accountIdValue: String,
    onAccountIdChange: (String) -> Unit,
    passwordValue: String,
    onPasswordChange: (String) -> Unit,
    autoSignIn: Boolean,
    onAutoSignInChange: (Boolean) -> Unit,
    idError: Exception? = null,
    passwordError: Exception? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = accountIdValue,
            onValueChange = onAccountIdChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SignInPreview() {
    val (id, onIdChange) = remember { mutableStateOf("") }
    val (password, onPasswordChange) = remember { mutableStateOf("") }
    val (autoSignIn, onAutoSignInChange) = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(DmsTheme.colorScheme.background)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        UserInformationInputs(
            accountIdValue = id,
            passwordValue = password,
            onAccountIdChange = onIdChange,
            onPasswordChange = onPasswordChange,
            autoSignIn = autoSignIn,
            onAutoSignInChange = onAutoSignInChange,
        )
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