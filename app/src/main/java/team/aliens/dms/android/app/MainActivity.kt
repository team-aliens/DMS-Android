package team.aliens.dms.android.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.dms.android.app.navigation.DmsNavHost
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.ToastLayout
import team.aliens.dms.android.core.designsystem.rememberToastState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val autoSignIn by viewModel.autoSignInAvailable.collectAsStateWithLifecycle()
            val toast = rememberToastState()

            DmsTheme {
                ToastLayout(
                    modifier = Modifier.fillMaxSize(),
                    toastState = toast,
                ) {
                    DmsScaffold {
                        DmsNavHost(
                            modifier = Modifier.fillMaxSize(),
                            autoSignIn = autoSignIn,
                        )
                    }
                }
            }
        }
    }
}
