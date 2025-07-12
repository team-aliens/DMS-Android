package team.aliens.dms.android.feature.volunteers

import android.graphics.Bitmap
import android.os.Message
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebResourceRequest
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.ramcosta.composedestinations.BuildConfig
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.volunteers.navigation.VolunteersNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun VolunteersScreen(
    modifier: Modifier = Modifier,
    webViewUrl: String,
    accessToken: String,
    navigator: VolunteersNavigator,
) {
    val extraToken = mutableMapOf<String, String>()
    extraToken.put("Authorization", accessToken)
    Log.d("TEST", "$accessToken\n$webViewUrl")
    val theme = if (isSystemInDarkTheme()) {
        "dark"
    } else {
        "light"
    }
    var isRedirected = false

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
                                        loadUrl("https://webview.dms-dsm.com/volunteer/history?theme=$theme", extraToken)
                                    }
                                    "https://webview.dms-dsm.com/volunteers" -> {
                                        Log.d("TEST", "봉시 신청 페이지")
                                        isRedirected = true
                                        loadUrl("https://webview.dms-dsm.com/volunteer/application?theme=$theme", extraToken)
                                    }
                                    else -> {
                                    }
                                }
                            }
                        }
                        loadUrl( "https://webview.dms-dsm.com/volunteer/application?theme=$theme", extraToken)
                    }
                },
            )
        }
    }
}
