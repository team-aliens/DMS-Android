package team.aliens.dms.android.wear.data

import kotlinx.collections.immutable.persistentListOf
import team.aliens.dms.android.wear.model.WearNoticeUiModel
import team.aliens.dms.android.wear.model.WearSnapshot

internal object WearStubRepository {
    fun loadSnapshot(): WearSnapshot = WearSnapshot(
        primaryMealLabel = "점심",
        primaryMealMenu = persistentListOf(
            "치킨마요덮밥",
            "미소국",
            "계절 샐러드",
        ),
        statusTitle = "잔류 상태",
        statusValue = "오늘 잔류 예정",
        syncedAt = "10:30",
        notices = persistentListOf(
            WearNoticeUiModel(
                title = "주말 점호 시간 변경",
                dateText = "오늘",
                isImportant = true,
            ),
            WearNoticeUiModel(
                title = "자습실 사용 안내",
                dateText = "어제",
                isImportant = false,
            ),
        ),
    )
}
