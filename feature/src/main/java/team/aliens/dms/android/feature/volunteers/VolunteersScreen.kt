package team.aliens.dms.android.feature.volunteers

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.volunteers.navigation.VolunteersNavigator
import team.aliens.dms.android.network.BuildConfig

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun VolunteersScreen(
    modifier: Modifier = Modifier,
    navigator: VolunteersNavigator,
    viewModel: VolunteersViewModel = hiltViewModel(),
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val theme = if (isSystemInDarkTheme()) "dark" else "light"
    var isTokenSet = false

    Scaffold(
        modifier = modifier.background(color = DmsTheme.colorScheme.background),
        topBar = {
            DmsTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.volunteers))
                },
                navigationIcon = {
                    IconButton(onClick = navigator::navigateUp) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(id = R.string.top_bar_back_button),
                        )
                    }
                },
            )
        },
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues)
                .imePadding(),
        ) {
            AndroidView(
                modifier = Modifier.weight(1f),
                factory = {
                    WebView(it).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        )
                        settings.javaScriptEnabled = true
                        webViewClient = object : WebViewClient() {
                            private val redirectedUrls = mutableSetOf<String>()

                            override fun onPageFinished(
                                view: WebView?,
                                url: String?,
                            ) {
                                if (!isTokenSet) {
                                    view?.evaluateJavascript(
                                        "window.setAuthToken('${uiState.accessToken}', '${uiState.refreshToken}')",
                                        null,
                                    )
                                    isTokenSet = true
                                }
                            }

                            override fun doUpdateVisitedHistory(
                                view: WebView?,
                                url: String?,
                                isReload: Boolean,
                            ) {
                                super.doUpdateVisitedHistory(view, url, isReload)

                                if (url == null || isReload || redirectedUrls.contains(url)) return

                                // 이미 theme 있으면 통과
                                if (url.contains("theme=")) {
                                    return
                                }

                                // volunteer URL인데 theme 없으면 리다이렉트
                                if (url.contains("/volunteer")) {
                                    val separator = if (url.contains("?")) "&" else "?"
                                    val urlWithTheme = "$url${separator}theme=$theme"

                                    redirectedUrls.add(urlWithTheme)
                                    view?.loadUrl(urlWithTheme)
                                }
                            }
                        }
                        loadUrl("${BuildConfig.TERMS_URL}volunteer/application?theme=$theme")
                    }
                },
            )
        }
    }
}
