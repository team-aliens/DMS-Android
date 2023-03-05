package team.aliens.dms_android.feature.register.ui.last

import android.content.Context
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormCheckBox
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.dialog.DormCustomDialog
import team.aliens.design_system.dialog.DormSingleButtonDialog
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Caption
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.dms_android.feature.register.event.SignUpEvent
import team.aliens.dms_android.viewmodel.auth.register.SignUpViewModel
import team.aliens.domain.param.RegisterParam
import team.aliens.presentation.R

@Composable
fun SignUpPolicyScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val toast = rememberToast()

    val profileImageUrl by remember { mutableStateOf("https://webview.aliens-dms.com/policy/privacy") }

    var isChecked by remember { mutableStateOf(false) }

    val onCheckedChange = { value: Boolean ->
        isChecked = !isChecked
    }

    var signUpDialogState by remember { mutableStateOf(false) }

    if (signUpDialogState) {
        DormCustomDialog(
            onDismissRequest = {},
        ) {
            DormSingleButtonDialog(
                content = stringResource(id = R.string.CompleteRegister),
                mainBtnText = stringResource(id = R.string.GoLogin),
                onMainBtnClick = {
                    navController.navigate(NavigationRoute.Login) {
                        popUpTo(NavigationRoute.Login) {
                            inclusive = true
                        }
                    }
                },
                mainBtnTextColor = DormTheme.colors.primary,
            )
        }
    }

    LaunchedEffect(Unit) {
        signUpViewModel.signUpViewEvent.collect {
            when (it) {
                is SignUpEvent.SignUpSuccess -> {
                    signUpDialogState = true
                    signUpDialogState = true
                }
                else -> {
                    toast(getStringFromEvent(
                        context = context,
                        event = it,
                    ))
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DormTheme.colors.surface,
            )
            .padding(
                top = 108.dp,
                start = 16.dp,
                end = 16.dp,
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
        ) {
            AppLogo(
                darkIcon = isSystemInDarkTheme(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Body2(text = stringResource(id = R.string.CheckRegisterPolicy))
            Spacer(modifier = Modifier.height(36.dp))
            Column(modifier = Modifier.height(374.dp)) {
                AndroidView(
                    factory = {
                        WebView(it).apply {
                            layoutParams =
                                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT)
                            webViewClient = WebViewClient()

                            settings.javaScriptEnabled = true

                            loadUrl(profileImageUrl)
                        }
                    },
                    update = {
                        it.loadUrl(profileImageUrl)
                    },
                )
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.39f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DormCheckBox(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange,
                )
                Spacer(modifier = Modifier.width(14.dp))
                Caption(
                    modifier = Modifier.padding(bottom = 1.dp),
                    text = stringResource(id = R.string.CheckAllPolicy),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            DormContainedLargeButton(
                text = stringResource(id = R.string.Check),
                color = DormButtonColor.Blue,
                enabled = isChecked,
            ) {
                navController.previousBackStackEntry?.arguments?.run {
                    signUpViewModel.signUp(registerParam = RegisterParam(
                        schoolCode = getString("schoolCode").toString(),
                        schoolAnswer = getString("schoolAnswer").toString(),
                        email = getString("email").toString(),
                        authCode = getString("authCode").toString(),
                        classRoom = getInt("classRoom"),
                        grade = getInt("grade"),
                        number = getInt("number"),
                        accountId = getString("accountId").toString(),
                        password = getString("password").toString(),
                        profileImageUrl = getString("profileImageUrl").toString(),
                    ))
                }
            }
        }
    }
}

private fun getStringFromEvent(
    context: Context,
    event: SignUpEvent,
): String = when (event) {
    is SignUpEvent.BadRequestException -> {
        context.getString(R.string.BadRequest)
    }
    is SignUpEvent.UnAuthorizedException -> {
        context.getString(R.string.SignUpUnAuthorized)
    }
    is SignUpEvent.ConflictException -> {
        context.getString(R.string.SignUpConflict)
    }
    is SignUpEvent.InternalServerException -> {
        context.getString(R.string.ServerException)
    }
    is SignUpEvent.NotFoundException -> {
        context.getString(R.string.NotFound)
    }
    is SignUpEvent.TooManyRequestsException -> {
        context.getString(R.string.TooManyRequest)
    }
    else -> {
        context.getString(R.string.UnKnownException)
    }
}