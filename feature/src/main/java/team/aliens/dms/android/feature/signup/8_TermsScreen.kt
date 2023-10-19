package team.aliens.dms.android.feature.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator

@Destination
@Composable
internal fun TermsScreen(
    modifier: Modifier = Modifier,
    navigator: SignUpNavigator,
    // signUpViewModel: SignUpViewModel,
) {
/*
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
    }*/
}
