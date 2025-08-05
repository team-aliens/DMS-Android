package team.aliens.dms.android.feature.volunteers

import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.jwt.Tokens
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.volunteers.navigation.VolunteersNavigator

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
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.volunteers_submit))
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
                            override fun onPageFinished(
                                view: WebView?,
                                url: String?,
                            ) {
                                if (!isTokenSet) {
                                    view?.evaluateJavascript("window.setAuthToken('$accessToken', '$refreshToken')", null)
                                    isTokenSet = true
                                }
                            }

                            override fun onLoadResource(view: WebView?, url: String?) {
                                when {
                                    url == "https://dev-api.dms-dsm.com/volunteers/my/application" && !url.contains("?theme=") -> {
                                        Log.d("TEST", "봉사 내역 확인 페이지")
                                        isRedirected = true
                                        loadUrl("https://webview.dms-dsm.com/volunteer/history?theme=$theme")
                                    }
                                    url == "https://dev-api.dms-dsm.com/volunteers" && !url.contains("?theme=") -> {
                                        Log.d("TEST", "봉시 신청 페이지")
                                        isRedirected = true
                                        loadUrl("https://webview.dms-dsm.com/volunteer/application?theme=$theme")
                                    }
                                    else -> {
                                    }
                                }
                            }
                        }
                        loadUrl("https://webview.dms-dsm.com/volunteer/application?theme=$theme")
                    }
                },
            )
        }
    }
}
