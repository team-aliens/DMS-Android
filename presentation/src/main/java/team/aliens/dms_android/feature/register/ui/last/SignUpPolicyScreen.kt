package team.aliens.dms_android.feature.register.ui.last

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormCheckBox
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Caption
import team.aliens.dms_android.component.AppLogo
import team.aliens.presentation.R

@Composable
fun SignUpPolicyScreen(
    navController: NavController,
) {

    val profileImageUrl by remember { mutableStateOf("https://team-aliens-webview.dsm-dms.com/sign-up-policy") }

    var isChecked by remember { mutableStateOf(false) }

    val onCheckedChange = { value: Boolean ->
        isChecked = !isChecked
    }

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
            modifier = Modifier.fillMaxHeight(),
        ) {
            AppLogo()
            Spacer(modifier = Modifier.height(8.dp))
            Body2(
                text = stringResource(
                    id = R.string.CheckRegisterPolicy
                )
            )
            Spacer(modifier = Modifier.height(36.dp))
            Column(
                modifier = Modifier.height(374.dp)
            ) {
                AndroidView(
                    factory = {
                        WebView(it).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            webViewClient = WebViewClient()

                            settings.javaScriptEnabled = true

                            loadUrl(profileImageUrl)
                        }
                    },
                    update = {
                        it.loadUrl(profileImageUrl)
                    },
                )
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.39f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DormCheckBox(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange,
                )
                Spacer(modifier = Modifier.width(14.dp))
                Caption(
                    modifier = Modifier.padding(bottom = 1.dp),
                    text = stringResource(id = R.string.CheckAllPolicy)),
            }
            Spacer(modifier = Modifier.height(16.dp))
            DormContainedLargeButton(
                text = stringResource(id = R.string.Check),
                color = DormButtonColor.Blue,
                enabled = isChecked,
            ) {

            }
        }
    }
}