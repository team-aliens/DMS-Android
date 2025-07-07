package team.aliens.dms.android.feature.volunteers

import android.media.session.MediaSession.Token
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.signup.TermsUrl
import team.aliens.dms.android.feature.volunteers.navigation.VolunteersNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun VolunteersScreen(
    modifier: Modifier = Modifier,
    termsUrl: TermsUrl,
    navigator: VolunteersNavigator,
) {
    val extraToken = mutableMapOf<String, String>()
    extraToken.put("Authorization", "bearer eyJKV1QiOiJhY2Nlc3MiLCJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJmZmFlNDhmNS1lNGYwLTExZWUtYjMyMi03ZDRmZmIxYWIzZDciLCJhdXRob3JpdHkiOiJTVFVERU5UIiwiaWF0IjoxNzUxODgzMDk3LCJleHAiOjE3NTE4ODY2OTd9._QcBCh7v3SRgog_PJCVhmhu8vsUPOfi_O_AIi0u_EgJSfVB2lyRgngUzAZopFIVKDLS2xriSkHcvNy6H0bMd2w")
    val theme = if (isSystemInDarkTheme()) {
        "dark"
    } else {
        "light"
    }
    Log.d("TEST", termsUrl.value)
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
                        webViewClient = WebViewClient()

                        settings.javaScriptEnabled = true

                        loadUrl( "https://webview.dms-dsm.com/volunteer/application" + "?theme=$theme", extraToken)
                    }
                },
            )
        }
    }
}
