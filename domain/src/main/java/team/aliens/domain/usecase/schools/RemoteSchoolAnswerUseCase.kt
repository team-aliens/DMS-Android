package team.aliens.domain.usecase.schools

import team.aliens.domain.param.SchoolAnswerParam
import team.aliens.domain.repository.SchoolsRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteSchoolAnswerUseCase @Inject constructor(
    private val schoolsRepository: SchoolsRepository,
) : UseCase<SchoolAnswerParam, Unit>() {
    override suspend fun execute(data: SchoolAnswerParam) {
        schoolsRepository.compareSchoolAnswer(data)
    }
}