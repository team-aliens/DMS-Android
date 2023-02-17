package team.aliens.domain.repository

import team.aliens.domain.entity.remain.AvailableRemainTimeEntity
import team.aliens.domain.entity.remain.CurrentRemainOptionEntity
import team.aliens.domain.entity.remain.RemainOptionsEntity
import java.util.*

interface RemainRepository {

    suspend fun updateRemainOption(
        remainOptionId: UUID,
    )

    suspend fun fetchCurrentRemainOption(): CurrentRemainOptionEntity

    suspend fun fetchAvailableRemainTime(): AvailableRemainTimeEntity

    suspend fun fetchRemainOptions(): RemainOptionsEntity
}
