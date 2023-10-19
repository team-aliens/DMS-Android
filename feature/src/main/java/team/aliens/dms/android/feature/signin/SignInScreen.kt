package team.aliens.dms.android.feature.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.ui.composable.AppLogo
import team.aliens.dms.android.core.designsystem.typography.Body2
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
            Banner(modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun Banner(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AppLogo(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Body2(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(R.string.app_description)
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
    idError: Boolean,
    passwordError: Boolean,
) {/*
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
        error = idError,
        *//*
                errorDescription = idErrorMessage,*//*
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
        error = passwordError,
        *//*
                errorDescription = passwordErrorMessage,*//*
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
    Spacer(Modifier.height(28.dp))*/
}/*

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