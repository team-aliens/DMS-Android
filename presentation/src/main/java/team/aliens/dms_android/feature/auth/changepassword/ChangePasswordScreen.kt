package team.aliens.dms_android.feature.auth.changepassword

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.typography.Body4
import team.aliens.dms_android.util.EventFlow
import team.aliens.dms_android.util.observeWithLifecycle
import team.aliens.dms_android.viewmodel.changepw.ChangePasswordViewModel
import team.aliens.presentation.R

@Composable
fun ChangePasswordScreen(
    changePasswordViewModel: ChangePasswordViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DormColor.Gray100),
    ) {
        MainValue()
        PasswordTextField()
        ScanNewPasswordButton(changePasswordViewModel)
    }
}

@Composable
private fun HandleViewEffect(
    effect: EventFlow<ChangePasswordEvent>,
    scaffoldState: ScaffoldState,
) {

    val badRequestComment = stringResource(id = R.string.BadRequest)
    val unAuthorizedComment = stringResource(id = R.string.NoSameCode)
    val notFoundComment = stringResource(id = R.string.ChangePasswordNotFound)
    val tooManyRequestComment = stringResource(id = R.string.TooManyRequest)
    val serverException = stringResource(id = R.string.ServerException)
    val unKnownException = stringResource(id = R.string.UnKnownException)

    effect.observeWithLifecycle(action = {
        when (it) {
            is ChangePasswordEvent.ChangePasswordSuccess -> {
                TODO("FeatureNavigator")
            }

            is ChangePasswordEvent.BadRequestException -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = badRequestComment,
                    duration = SnackbarDuration.Short
                )
            }

            is ChangePasswordEvent.UnAuthorizedException -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = unAuthorizedComment,
                    duration = SnackbarDuration.Short
                )
            }

            is ChangePasswordEvent.NotFoundException -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = notFoundComment,
                    duration = SnackbarDuration.Short
                )
            }

            is ChangePasswordEvent.TooManyRequestException -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = tooManyRequestComment,
                    duration = SnackbarDuration.Short
                )
            }

            is ChangePasswordEvent.InternalServerException -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = serverException,
                    duration = SnackbarDuration.Short
                )
            }

            is ChangePasswordEvent.UnKnownException -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = unKnownException,
                    duration = SnackbarDuration.Short
                )
            }

            else -> {}
        }
    })
    BackPressHandle()
}

@Composable
private fun BackPressHandle() {
    val backHandlingEnabled by remember { mutableStateOf(true) }
    val activity = (LocalContext.current as? Activity)
    BackHandler(backHandlingEnabled) {
        activity?.finish()
    }
}

@Composable
fun MainValue() {
    Box(
        contentAlignment = Alignment.TopStart,
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Image(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 12.dp)
                    .height(24.dp)
                    .width(24.dp),
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                contentDescription = stringResource(id = R.string.BackButton),
            )
            Image(
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 7.dp)
                    .height(85.dp)
                    .width(85.dp),
                painter = painterResource(id = R.drawable.ic_information_toast),
                contentDescription = stringResource(id = R.string.MainLogo),
            )
            Spacer(modifier = Modifier.height(1.dp))
            Body4(
                text = stringResource(id = R.string.SetNewPassword),
                color = DormColor.Gray600,
            )
        }
    }
}

@Composable
fun PasswordTextField() {

    var passwordValue by remember { mutableStateOf("") }
    var passwordCheckValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            top = 90.dp,
            end = 16.dp,
        ),
    ) {
        DormTextField(
            value = passwordValue,
            onValueChange = {
                passwordValue = it
            },
            isPassword = true,
            hint = stringResource(id = R.string.ScanNewPassword),
        )
        Spacer(modifier = Modifier.height(37.dp))
        DormTextField(
            value = passwordCheckValue,
            onValueChange = {
                passwordCheckValue = it
            },
            isPassword = true,
            hint = stringResource(id = R.string.CheckScanNewPassword),
        )
    }
}

@Composable
fun ScanNewPasswordButton(
    changePasswordViewModel: ChangePasswordViewModel,
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
        DormContainedLargeButton(
            text = stringResource(id = R.string.Check),
            color = DormButtonColor.Blue,
        ) {
            TODO("두개의 TextField에 Null 검사 후 눌리게 구현해야함.")
        }
    }
}

@Preview
@Composable
fun ChangePasswordScreenPreView() {
    ChangePasswordScreen()
}
