package team.aliens.dms.android.feature.signup.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsButton
import team.aliens.dms.android.core.designsystem.foundation.DmsSymbolContent
import team.aliens.dms.android.core.designsystem.horizontalPadding
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.designsystem.topPadding
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.feature.signup.viewmodel.TermsSideEffect
import team.aliens.dms.android.feature.signup.viewmodel.TermsState
import team.aliens.dms.android.feature.signup.viewmodel.TermsViewModel

@Composable
internal fun TermsScreen(
    signUpData: SignUpData,
    onBackPressed: () -> Unit,
    navigateToComplete: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: TermsViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val theme = if (isSystemInDarkTheme()) "dark" else "light"

    LaunchedEffect(Unit) {
        viewModel.initialize(signUpData)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is TermsSideEffect.NavigateToComplete -> navigateToComplete()
                is TermsSideEffect.FailSignUp -> onShowSnackBar(DmsSnackBarType.ERROR, "다시 시도해주세요")
            }
        }
    }

    TermsContent(
        onBackPressed = onBackPressed,
        onNextClick = viewModel::postSignUp,
        webViewUrl = "${viewModel.baseUrl}/policy/privacy?theme=$theme",
        state = state,
        onAgreeButtonClick = viewModel::setButtonEnabled,
    )
}

@Composable
private fun TermsContent(
    onBackPressed: () -> Unit,
    onNextClick: () -> Unit,
    webViewUrl: String,
    state: TermsState,
    onAgreeButtonClick: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.surfaceTint)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        DmsTopAppBar(
            title = "회원가입",
            onBackPressed = onBackPressed,
        )
        DmsSymbolContent(
            modifier = Modifier
                .horizontalPadding(24.dp)
                .topPadding(4.dp),
            title = "약관 동의",
            description = "동의 후 DMS를 사용할 수 있습니다.",
        )
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .horizontalPadding(24.dp)
                .topPadding(12.dp),
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    loadUrl(webViewUrl)
                }
            },
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = DmsTheme.colorScheme.background,
        )
        AgreeCheckBox(
            modifier = Modifier.fillMaxWidth(),
            isCheck = state.buttonEnabled,
            onAgreeButtonClick = onAgreeButtonClick,
        )
        DmsButton(
            modifier = Modifier.fillMaxWidth(),
            text = "다음",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            keyboardInteractionEnabled = true,
            onClick = onNextClick,
            enabled = state.buttonEnabled,
            isLoading = state.isLoading,
        )
    }
}

@Composable
private fun AgreeCheckBox(
    modifier: Modifier = Modifier,
    isCheck: Boolean,
    onAgreeButtonClick: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(
            alignment = Alignment.CenterHorizontally,
            space = 12.dp,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "전체 약관 동의",
            style = DmsTheme.typography.bodyM,
            color = DmsTheme.colorScheme.tertiaryContainer,
        )
        Checkbox(
            checked = isCheck,
            onCheckedChange = onAgreeButtonClick,
            colors = CheckboxDefaults.colors(
                checkedColor = DmsTheme.colorScheme.primary,
                uncheckedColor = DmsTheme.colorScheme.onSurfaceVariant,
            ),
        )
    }
}
