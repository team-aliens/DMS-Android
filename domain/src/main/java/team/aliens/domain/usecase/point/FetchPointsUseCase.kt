package team.aliens.domain.usecase.point

import team.aliens.domain.model.point.FetchPointsInput
import team.aliens.domain.model.point.FetchPointsOutput
import team.aliens.domain.repository.PointRepository
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
