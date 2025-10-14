package team.aliens.dms.android.feature.volunteers

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.feature.volunteers.navigation.VolunteersNavigator
import team.aliens.dms.android.network.BuildConfig

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun VolunteersScreen(
    modifier: Modifier = Modifier,
    webViewUrl: String,
    accessToken: String,
    refreshToken: String,
    navigator: VolunteersNavigator,
) {
    val accessTokens = "eyJKV1QiOiJhY2Nlc3MiLCJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJmZmFlNDhmNS1lNGYwLTExZWUtYjMyMi03ZDRmZmIxYWIzZDciLCJhdXRob3JpdHkiOiJTVFVERU5UIiwiaWF0IjoxNzU0MzA4NTQzLCJleHAiOjE3NTQzMTIxNDN9.itupcJ2VlqYt9I1rJZsJXvv_FizGpPsDFPzltfbIu_bpfULKYdaU4Hph3jUN3KfLZZ8B48seOXoIGQEnWMKJOA"
    val refreshTokens = "eyJKV1QiOiJyZWZyZXNoIiwiYWxnIjoiSFM1MTIifQ.eyJqdGkiOiJmZmFlNDhmNS1lNGYwLTExZWUtYjMyMi03ZDRmZmIxYWIzZDciLCJpYXQiOjE3NTQzMDg1NDMsImV4cCI6MTc1NTUxNDE0M30.n6xR18jYV7QfBYzcd2_5w924FUujrRj7eqQqfubObomT9DTsUcOFwZW8wjBDzfV7aBxrSKimxVEjq3eSTgXTbA"
    val theme = if (isSystemInDarkTheme()) {
        "dark"
    } else {
        "light"
    }

    var isRedirected = false
    var isTokenSet = false

    Scaffold(
        modifier = modifier.background(color = DmsTheme.colorScheme.background),
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues)
                .imePadding()
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
                                        "window.setAuthToken('$accessToken', '$refreshToken')",
                                        null
                                    )
                                    isTokenSet = true
                                }
                            }

                            override fun doUpdateVisitedHistory(
                                view: WebView?,
                                url: String?,
                                isReload: Boolean
                            ) {
                                super.doUpdateVisitedHistory(view, url, isReload)
                                Log.d(
                                    "WEBVIEW_DEBUG",
                                    "doUpdateVisitedHistory - URL: $url, isReload: $isReload"
                                )

                                if (url == null || isReload || redirectedUrls.contains(url)) return

                                // 이미 theme 있으면 통과
                                if (url.contains("theme=")) {
                                    return
                                }

                                // volunteer URL인데 theme 없으면 리다이렉트
                                if (url.contains("/volunteer")) {
                                    val separator = if (url.contains("?")) "&" else "?"
                                    val urlWithTheme = "$url${separator}theme=$theme"
                                    Log.d("WEBVIEW_DEBUG", "✅ 테마 추가: $urlWithTheme")

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
