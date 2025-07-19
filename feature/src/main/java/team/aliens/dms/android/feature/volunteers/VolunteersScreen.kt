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
    val accessTokens = "eyJKV1QiOiJhY2Nlc3MiLCJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJmZmFlNDhmNS1lNGYwLTExZWUtYjMyMi03ZDRmZmIxYWIzZDciLCJhdXRob3JpdHkiOiJTVFVERU5UIiwiaWF0IjoxNzUyOTM0ODY1LCJleHAiOjE3NTI5Mzg0NjV9.sPtt3_2k1yHnq-E3icWhoFvL5d118R_oX63J72-Pp7eBCKSvB20o_ld4f07oo5jZE8FMziCgNY3Ap-p9bjyyYA"
    val refreshTokens = "eyJKV1QiOiJyZWZyZXNoIiwiYWxnIjoiSFM1MTIifQ.eyJqdGkiOiJmZmFlNDhmNS1lNGYwLTExZWUtYjMyMi03ZDRmZmIxYWIzZDciLCJpYXQiOjE3NTI5MzQ4NjUsImV4cCI6MTc1NDE0MDQ2NX0.QkKNVxye-Dmfd3YJ3gEHhtMtDEUo4kivnRY27ubxz_ys5kvYsHfTbIMUt2PZBNuh3Yr4WgRoCYAeic44iTIq7Q"
    val theme = if (isSystemInDarkTheme()) {
        "dark"
    } else {
        "light"
    }
    var isRedirected = false


    val cookieManager = CookieManager.getInstance()
    cookieManager.setAcceptCookie(true)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //cookieManager.setAcceptThirdPartyCookies(, true)
    }

    cookieManager.setCookie("https://webview.dms-dsm.com", "Authorization=bearer $accessTokens")
    cookieManager.flush()

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
                            override fun onLoadResource(view: WebView?, url: String?) {
                                Log.d("TEST", url.toString())
                                Log.d("TEST", isRedirected.toString())
                                if (isRedirected) return
                                when (url) {
                                    "https://webview.dms-dsm.com/volunteers/my/application" -> {
                                        Log.d("TEST", "봉사 내역 확인 페이지")
                                        isRedirected = true
                                        loadUrl("https://webview.dms-dsm.com/volunteer/history?theme=$theme")
                                    }
                                    "https://webview.dms-dsm.com/volunteers" -> {
                                        Log.d("TEST", "봉시 신청 페이지")
                                        isRedirected = true
                                        loadUrl("https://webview.dms-dsm.com/volunteer/application?theme=$theme")
                                    }
                                    else -> {
                                    }
                                }
                            }
                        }
                        evaluateJavascript("""window.setAuthToken("$accessTokens", "$refreshTokens")""", null)
                        loadUrl( "https://webview.dms-dsm.com/volunteer/application?theme=$theme")
                    }
                },
            )
        }
    }
}
