package team.aliens.dms_android.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.design_system.theme.DormTheme
import team.aliens.dms_android.feature.application.rememberDmsAppState
import team.aliens.dms_android.feature.navigator.DmsApp
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {
    /*
        private fun initExceptionHandler(
            appState: DmsAppState,
        ) {
            Thread.setDefaultUncaughtExceptionHandler(
                DmsExceptionHandler(
                    context = this,
                    appState = appState,
                )
            )
        }
    */

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DormTheme(
                darkTheme = isSystemInDarkTheme(),
            ) {

                val dmsAppState = rememberDmsAppState()

                val autoSignIn = mainViewModel.isSignInSuccess.collectAsStateWithLifecycle()

                if (autoSignIn.value);

                DmsApp(
                    dmsAppState = dmsAppState,
                )

                /*// fixme refactor
                val availableFeatures = mutableMapOf(
                    Extra.isMealServiceEnabled to intent.getBooleanExtra(
                        Extra.isMealServiceEnabled, false,
                    ),
                    Extra.isNoticeServiceEnabled to intent.getBooleanExtra(
                        Extra.isNoticeServiceEnabled, false,
                    ),
                    Extra.isPointServiceEnabled to intent.getBooleanExtra(
                        Extra.isPointServiceEnabled, false,
                    ),
                    Extra.isStudyRoomEnabled to intent.getBooleanExtra(
                        Extra.isStudyRoomEnabled, false,
                    ),
                    Extra.isRemainServiceEnabled to intent.getBooleanExtra(
                        Extra.isRemainServiceEnabled, false,
                    ),
                )

                CompositionLocalProvider(
                    values = arrayOf(LocalAvailableFeatures provides availableFeatures),
                ) {
                    DmsApp()
                }*/
            }
        }
    }
}
