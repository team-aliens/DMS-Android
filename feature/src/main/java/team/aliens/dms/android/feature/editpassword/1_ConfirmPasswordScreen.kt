package team.aliens.dms.android.feature.editpassword

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.design_system.button.DormButtonColor
import team.aliens.dms.android.design_system.button.DormContainedLargeButton
import team.aliens.dms.android.design_system.extension.Space
import team.aliens.dms.android.design_system.modifier.dormClickable
import team.aliens.dms.android.design_system.textfield.DormTextField
import team.aliens.dms.android.design_system.theme.DormTheme
import team.aliens.dms.android.design_system.toast.rememberToast
import team.aliens.dms.android.design_system.typography.Body2
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature._legacy.AppLogo
import team.aliens.dms.android.feature._legacy.util.TopBar
import team.aliens.dms.android.feature.editpassword.navigation.EditPasswordNavigator
import team.aliens.dms.android.feature.resetpassword.ChangePasswordViewModel

@Destination
@Composable
internal fun ConfirmPasswordScreen(
    modifier: Modifier = Modifier,
    navigator: EditPasswordNavigator,
) {
    val viewModel: EditPasswordViewModel = hiltViewModel()
/*
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    val toast = rememberToast()

    var password by remember { mutableStateOf("") }

    var isError by remember { mutableStateOf(false) }

    val onPasswordChange = { value: String ->
        if (value.length != password.length) isError = false
        password = value
        changePasswordViewModel.setCurrentPassword(password)
    }

    LaunchedEffect(Unit) {
        changePasswordViewModel.editPasswordEffect.collect {
            when (it) {
                is ChangePasswordViewModel.Event.ComparePasswordSuccess -> navigator.openEditPasswordSetPasswordNav()

                is ChangePasswordViewModel.Event.UnauthorizedException -> {
                    isError = true
                }

                else -> {
                    toast(
                        getStringFromEvent(
                            context = context,
                            event = it,
                        ),
                    )
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                DormTheme.colors.background,
            )
            .dormClickable(
                rippleEnabled = false,
            ) {
                focusManager.clearFocus()
            },
    ) {
        TopBar(
            title = stringResource(R.string.ChangePassword),
            onPrevious = navigator::popBackStack,
        )
        Column(
            modifier = Modifier.padding(
                top = 46.dp,
                start = 16.dp,
                end = 16.dp,
            )
        ) {

            AppLogo(
                darkIcon = isSystemInDarkTheme(),
            )
            Space(space = 32.dp)
            Body2(
                text = stringResource(id = R.string.OriginPw),
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.84f)
                    .padding(top = 80.dp)
            ) {
                DormTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    isPassword = true,
                    hint = stringResource(id = R.string.Password),
                    errorDescription = stringResource(id = R.string.CheckPassword),
                    error = isError,
                    keyboardActions = KeyboardActions {
                        focusManager.clearFocus()
                    })
            }
            DormContainedLargeButton(
                text = stringResource(id = R.string.Next),
                color = DormButtonColor.Blue,
                enabled = (password.isNotEmpty() && !isError),
            ) {
                changePasswordViewModel.comparePassword()
            }
        }
    }*/
}

private fun getStringFromEvent(
    context: Context,
    event: ChangePasswordViewModel.Event,
): String = when (event) {
    is ChangePasswordViewModel.Event.BadRequestException -> {
        context.getString(R.string.BadRequest)
    }

    is ChangePasswordViewModel.Event.ForbiddenException -> {
        context.getString(R.string.Forbidden)
    }

    is ChangePasswordViewModel.Event.TooManyRequestException -> {
        context.getString(R.string.TooManyRequest)
    }

    is ChangePasswordViewModel.Event.ServerException -> {
        context.getString(R.string.ServerException)
    }

    else -> context.getString(R.string.UnKnownException)
}