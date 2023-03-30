package team.aliens.dms_android.feature

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.design_system.theme.DormTheme
import team.aliens.dms_android.base.BaseActivity
import team.aliens.dms_android.common.LocalAvailableFeatures
import team.aliens.dms_android.constans.Extra
import team.aliens.dms_android.feature.navigator.RootDms
import team.aliens.presentation.R
import team.aliens.presentation.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val secondIntent = intent
            val route = secondIntent.getStringExtra("route")

            var availableFeatures = staticCompositionLocalOf {
                emptyMap<String, Boolean>()
            }

            if (route != null) {
                DormTheme(
                    darkTheme = isSystemInDarkTheme(),
                ) {
                    availableFeatures = staticCompositionLocalOf {
                        mapOf(
                            Extra.isEnableMealService to intent.getBooleanExtra(
                                Extra.isEnableMealService, false,
                            ),
                            Extra.isEnableNoticeService to intent.getBooleanExtra(
                                Extra.isEnableNoticeService, false,
                            ),
                            Extra.isEnablePointService to intent.getBooleanExtra(
                                Extra.isEnablePointService, false,
                            ),
                            Extra.isEnableStudyRoomService to intent.getBooleanExtra(
                                Extra.isEnableStudyRoomService, false,
                            ),
                            Extra.isEnableRemainService to intent.getBooleanExtra(
                                Extra.isEnableRemainService, false,
                            ),
                        )
                    }

                    CompositionLocalProvider(
                        values = arrayOf(LocalAvailableFeatures provides availableFeatures.current),
                    ) {
                        RootDms(
                            route = route,
                        )
                    }
                }
            }
        }
    }

    override fun initView() {
        /* explicit blank */
    }
}