package team.aliens.dms_android.feature.register.ui.last

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import team.aliens.design_system.typography.Body2
import team.aliens.dms_android.component.AppLogo
import team.aliens.presentation.R

@Composable
fun SignUpPolicyScreen(
    navController: NavController,
) {

    val profileImageUrl by remember { mutableStateOf("https://team-aliens-webview.dsm-dms.com/sign-up-policy") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 108.dp,
                start = 16.dp,
                end = 16.dp,
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(0.843f)
        ) {
            AppLogo()
            Spacer(modifier = Modifier.height(8.dp))
            Body2(
                text = stringResource(
                    id = R.string.CheckRegisterPolicy
                )
            )
            Spacer(modifier = Modifier.height(36.dp))
            AndroidView(factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()

                    settings.javaScriptEnabled = true

                    loadUrl(profileImageUrl)
                }
            }, update = {
                it.loadUrl(profileImageUrl)
            })
        }
    }
}