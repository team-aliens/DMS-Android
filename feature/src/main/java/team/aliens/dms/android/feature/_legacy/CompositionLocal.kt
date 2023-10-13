package team.aliens.dms.android.feature._legacy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import team.aliens.dms.android.domain.model.student.Features

internal val LocalAvailableFeatures = compositionLocalOf { AvailableFeaturesWrapper }

@Stable
internal object AvailableFeaturesWrapper {
    var features = Features.falseInitialized()

    fun of(features: Features): AvailableFeaturesWrapper {
        AvailableFeaturesWrapper.features = features
        return this
    }

    fun of(
        mealService: Boolean,
        noticeService: Boolean,
        pointService: Boolean,
        studyRoomService: Boolean,
        remainsService: Boolean,
    ): AvailableFeaturesWrapper = of(
        Features(
            mealService = mealService,
            noticeService = noticeService,
            pointService = pointService,
            studyRoomService = studyRoomService,
            remainsService = remainsService,
        ),
    )
}

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
    container: MutableMap<String, Boolean>, features: Features
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
    remainsService: Boolean = false,
): Features {
    return rememberAvailableFeatures(
        features = Features(
            mealService = mealService,
            noticeService = noticeService,
            pointService = pointService,
            studyRoomService = studyRoomService,
            remainsService = remainsService,
        ),
    )
}

@Composable
internal fun rememberAvailableFeatures(
    features: Features,
): Features {
    return remember { features }
}
