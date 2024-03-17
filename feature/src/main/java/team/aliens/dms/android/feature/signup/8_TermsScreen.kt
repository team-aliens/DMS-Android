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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.Checkbox
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.collectInLaunchedEffectWithLifecycle
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

    val toast = LocalToast.current
    val context = LocalContext.current

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            SignUpSideEffect.SignUpFailure -> toast.showErrorToast(
                message = context.getString(R.string.sign_up_error_id_format)
            )

            // TODO: 모달 구현
            SignUpSideEffect.SignedUp -> navigator.openSignIn()
            else -> {/* explicit blank */
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.sign_up_terms))
                },
                navigationIcon = {
                    IconButton(onClick = navigator::popUpToSetProfileImage) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(id = R.string.top_bar_back_button),
                        )
                    }
                },
            )
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
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
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
                onClick = { viewModel.postIntent(SignUpIntent.SignUp) },
                enabled = agreeOnTerm,
            ) {
                Text(text = stringResource(id = R.string.sign_up_terms_finish_sign_up))
            }
        }
    }
}
