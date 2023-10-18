package team.aliens.dms.android.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.dms.android.designsystem.toast.ToastLayout
import team.aliens.dms.android.designsystem.toast.rememberToastState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val autoSignIn by viewModel.autoSignInAvailable.collectAsStateWithLifecycle()
            val toast = rememberToastState()

            ToastLayout(
                modifier = Modifier.fillMaxSize(),
                toastState = toast,
            ) {
                if (autoSignIn != null) {
                    DmsApp(
                        modifier = Modifier.fillMaxSize(),
                        autoSignIn = autoSignIn!!,
                    )
                } else {
                    Text(text = "LOADING, SPLASH")
                }
            }
        }
    }
}

@Composable
private fun DmsApp(
    modifier: Modifier = Modifier,
    autoSignIn: Boolean,
) {
    DmsNavHost(
        modifier = modifier,
        autoSignIn = autoSignIn,
    )
}
