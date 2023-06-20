package team.aliens.dms_android.common

import androidx.compose.runtime.staticCompositionLocalOf
import team.aliens.dms_android.constans.Extra

internal val LocalAvailableFeatures = staticCompositionLocalOf { mutableMapOf<String, Boolean>() }

internal fun initLocalAvailableFeatures(
    container: MutableMap<String, Boolean>,
    mealService: Boolean,
    noticeService: Boolean,
    pointService: Boolean,
    studyRoomService: Boolean,
    remainsService: Boolean,
) {
    container.run {
        set(
            Extra.isMealServiceEnabled,
            mealService,
        )
        set(
            Extra.isNoticeServiceEnabled,
            noticeService,
        )
        set(
            Extra.isPointServiceEnabled,
            pointService,
        )
        set(
            Extra.isStudyRoomEnabled,
            studyRoomService,
        )
        set(
            Extra.isRemainServiceEnabled,
            remainsService,
        )
    }
}
