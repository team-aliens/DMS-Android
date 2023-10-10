package team.aliens.dms_android.domain.usecase.point

import team.aliens.dms_android.domain.model.point.FetchPointsInput
import team.aliens.dms_android.domain.model.point.FetchPointsOutput
import team.aliens.dms_android.domain.repository.PointRepository
import javax.inject.Inject

class FetchPointsUseCase @Inject constructor(
    private val pointRepository: PointRepository,
) {
    suspend operator fun invoke(
        fetchPointsInput: FetchPointsInput,
    ): FetchPointsOutput {
        return pointRepository.fetchPoints(
            input = fetchPointsInput,
        )
    }
}
