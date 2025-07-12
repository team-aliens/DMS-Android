package team.aliens.dms.android.feature.volunteers

import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
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
    navigator: VolunteersNavigator,
) {
    val extraToken = mutableMapOf<String, String>()
    extraToken.put("access_token", "eyJKV1QiOiJhY2Nlc3MiLCJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI2NTM3MzczMC02MjY2LTYzMzMtMmQzNy02MjMxMzYyZDMxMzEiLCJhdXRob3JpdHkiOiJNQU5BR0VSIiwiaWF0IjoxNzUyMzI2ODU2LCJleHAiOjE3NTIzMzA0NTZ9.U8zPGrhmTocZUYhAHGd7sSdUb9z1ajemYHuhI_KWysljxsCpmYNtGesW6cyhfkZ6fjC5_CSfcXu7O6vUu_UL_A")
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
                                    "https://webview.dms-dsm.com/volunteer" -> {
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
