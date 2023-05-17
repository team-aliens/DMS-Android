package team.aliens.dms_android.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.design_system.theme.DormTheme
import team.aliens.dms_android.common.LocalAvailableFeatures
import team.aliens.dms_android.constans.Extra
import team.aliens.dms_android.feature.navigator.DmsApp
import team.aliens.dms_android.feature.navigator.DmsRoute
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DormTheme(
                darkTheme = isSystemInDarkTheme(),
            ) {
                val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()

                val startDestination = if (uiState.autoSignInSuccess) {
                    DmsRoute.Home.route
                } else {
                    DmsRoute.Auth.route
                }

                val availableFeatures = uiState.feature.run {
                    mutableMapOf(
                        Extra.isMealServiceEnabled to mealService,
                        Extra.isNoticeServiceEnabled to noticeService,
                        Extra.isPointServiceEnabled to pointService,
                        Extra.isStudyRoomEnabled to studyRoomService,
                        Extra.isRemainServiceEnabled to remainsService,
                    )
                }

                CompositionLocalProvider(
                    values = arrayOf(LocalAvailableFeatures provides availableFeatures),
                ) {
                    DmsApp(
                        initialRoute = startDestination,
                    )
                }
            }
        }
    }
}
