package team.aliens.dms_android.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import team.aliens.dms_android.constans.Extra
import team.aliens.domain.model.student.Features

internal val LocalAvailableFeatures = staticCompositionLocalOf { Features.falseInitialized() }

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

internal fun initLocalAvailableFeatures(
    container: MutableMap<String, Boolean>,
    features: Features
) {
    initLocalAvailableFeatures(
        container = container,
        mealService = features.mealService,
        noticeService = features.noticeService,
        pointService = features.pointService,
        studyRoomService = features.studyRoomService,
        remainsService = features.remainsService,
    )
}

@Composable
internal fun rememberAvailableFeatures(
    mealService: Boolean = false,
    noticeService: Boolean = false,
    pointService: Boolean = false,
    studyRoomService: Boolean = false,
    remainsService: Boolean = false
): Features {
    return remember {
        Features(
            mealService = mealService,
            noticeService = noticeService,
            pointService = pointService,
            studyRoomService = studyRoomService,
            remainsService = remainsService,
        )
    }
}
