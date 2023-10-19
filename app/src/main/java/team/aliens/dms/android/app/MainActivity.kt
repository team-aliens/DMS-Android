package team.aliens.dms.android.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.toast.ToastLayout
import team.aliens.dms.android.core.designsystem.toast.rememberToastState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val autoSignIn by viewModel.autoSignInAvailable.collectAsStateWithLifecycle()
            val toast = rememberToastState()

            DmsTheme {
                Surface(
                    color = DmsTheme.colors.background,
                ) {
                    ToastLayout(
                        modifier = Modifier.fillMaxSize(),
                        toastState = toast,
                    ) {
                        DmsApp(
                            modifier = Modifier.fillMaxSize(),
                            autoSignIn = autoSignIn,
                        )
                    }
                }
            }
        }
    }
}

// TODO: useless wrapper
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
