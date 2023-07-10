package team.aliens.dms_android.feature.signup.terms

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormCheckBox
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.dialog.DormCustomDialog
import team.aliens.design_system.dialog.DormSingleButtonDialog
import team.aliens.design_system.extension.RatioSpace
import team.aliens.design_system.extension.Space
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.LocalToast
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Caption
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.signup.SignUpIntent
import team.aliens.dms_android.feature.signup.SignUpSideEffect
import team.aliens.dms_android.feature.signup.SignUpViewModel
import team.aliens.presentation.R

@Composable
internal fun TermsScreen(
    onNavigateToTerms: () -> Unit,
    onNavigateToSignInWithInclusive: () -> Unit,
    signUpViewModel: SignUpViewModel,
) {

    val uiState by signUpViewModel.stateFlow.collectAsStateWithLifecycle()

    val policyUrl by remember { mutableStateOf("https://webview.aliens-dms.com/policy/privacy") }

    var signUpDialogState by remember { mutableStateOf(false) }

    val onCheckChanged = { checked: Boolean ->
        signUpViewModel.postIntent(SignUpIntent.Terms.SetCheckedPolicy(checked))
    }

    val toast = LocalToast.current

    val alreadyExistsStudentMessage = stringResource(id = R.string.sign_up_error_conflict)
    val emailNotVerified = stringResource(id = R.string.sign_up_error_unauthorized)

    LaunchedEffect(Unit) {
        signUpViewModel.sideEffectFlow.collect {
            when (it) {
                is SignUpSideEffect.Terms.SuccessSignUp -> {
                    signUpDialogState = true
                }

                is SignUpSideEffect.Terms.AlreadyExistsStudent -> {
                    toast.showErrorToast(alreadyExistsStudentMessage)
                }

                is SignUpSideEffect.Terms.EmailNotVerified -> {
                    toast.showErrorToast(emailNotVerified)
                }

                else -> {}
            }
        }
    }

    if (signUpDialogState) {
        DormCustomDialog(
            onDismissRequest = { signUpDialogState = false },
        ) {
            DormSingleButtonDialog(
                content = stringResource(id = R.string.CompleteRegister),
                mainBtnText = stringResource(id = R.string.GoLogin),
                onMainBtnClick = onNavigateToSignInWithInclusive,
                mainBtnTextColor = DormTheme.colors.primary,
            )
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
            AppLogo(darkIcon = isSystemInDarkTheme())
            Space(space = 8.dp)
            Body2(text = stringResource(id = R.string.CheckRegisterPolicy))
            Space(space = 36.dp)
            Column(modifier = Modifier.height(374.dp)) {
                AndroidView(
                    factory = {
                        WebView(it).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            webViewClient = WebViewClient()

                            settings.javaScriptEnabled = true

                            loadUrl(policyUrl)
                        }
                    },
                    update = {
                        it.loadUrl(policyUrl)
                    },
                )
            }
            RatioSpace(height = 0.39f)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DormCheckBox(
                    checked = uiState.checkedPolicy,
                    onCheckedChange = onCheckChanged,
                )
                Space(space = 14.dp)
                Caption(
                    modifier = Modifier.padding(bottom = 1.dp),
                    text = stringResource(id = R.string.CheckAllPolicy),
                )
            }
            Space(space = 16.dp)
            DormContainedLargeButton(
                text = stringResource(id = R.string.Check),
                color = DormButtonColor.Blue,
                enabled = uiState.checkedPolicy,
            ) {
                signUpViewModel.postIntent(SignUpIntent.Terms.SignUp)
            }
        }
    }
}
