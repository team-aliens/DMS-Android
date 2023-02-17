package team.aliens.domain.repository

import team.aliens.domain.entity.remain.AvailableRemainTimeEntity
import team.aliens.domain.entity.remain.CurrentRemainOptionEntity
import team.aliens.domain.entity.remain.RemainOptionsEntity
import java.util.*

interface RemainRepository {

    fun updateRemainOption(
        remainOptionId: UUID,
    )

    fun fetchCurrentRemainOption(): CurrentRemainOptionEntity

    fun fetchAvailableRemainTime(): AvailableRemainTimeEntity

    fun fetchRemainOptions(): RemainOptionsEntity
}
