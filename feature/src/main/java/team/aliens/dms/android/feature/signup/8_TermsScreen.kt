package team.aliens.dms.android.feature.signup

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.Checkbox
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun TermsScreen(
    modifier: Modifier = Modifier,
    navigator: SignUpNavigator,
    termsUrl: TermsUrl,
    viewModel: SignUpViewModel,
) {
    val (agreeOnTerm, onAgreeOnTermChange) = remember { mutableStateOf(false) }
    val theme = if (isSystemInDarkTheme()) {
        "dark"
    } else {
        "light"
    }

    DmsScaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(title = { Text(text = stringResource(id = R.string.sign_up_terms)) })
        },
        containerColor = DmsTheme.colorScheme.surface,
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues)
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            AndroidView(
                modifier = Modifier.weight(1f),
                factory = {
                    WebView(it).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = WebViewClient()

                        settings.javaScriptEnabled = true

                        loadUrl(termsUrl.value + "?theme=$theme")
                    }
                },
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = agreeOnTerm,
                    onCheckedChange = onAgreeOnTermChange,
                )
                Text(text = stringResource(id = R.string.sign_up_agree_all_terms))
            }
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = {

                },
                enabled = agreeOnTerm,
            ) {
                Text(text = stringResource(id = R.string.sign_up_terms_finish_sign_up))
            }
        }
    }
    /*
        val uiState by signUpViewModel.stateFlow.collectAsStateWithLifecycle()

        val termsUrl by remember { mutableStateOf("https://webview.aliens-dms.com/policy/privacy") }

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
                    onMainBtnClick = navigator::openSignIn,
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

                                loadUrl(termsUrl)
                            }
                        },
                        update = {
                            it.loadUrl(termsUrl)
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
        }*/
}
