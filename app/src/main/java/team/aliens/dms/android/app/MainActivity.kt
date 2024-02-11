package team.aliens.dms.android.app

import android.os.Bundle
import android.view.WindowManager
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
import team.aliens.dms.android.core.designsystem.DmsTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )

        setContent {
            // FIXME: app 전역 state로 관리하도록 변경
            val autoSignIn by viewModel.autoSignInAvailable.collectAsStateWithLifecycle()

            DmsTheme {
                DmsNavHost(
                    modifier = Modifier.fillMaxSize(),
                    autoSignIn = autoSignIn,
                )
            }
        }
    }
}
