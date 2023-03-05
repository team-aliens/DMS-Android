package team.aliens.domain.usecase.students

import team.aliens.domain.entity.user.FindIdEntity
import team.aliens.domain.param.FindIdParam
import team.aliens.domain.repository.StudentsRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class FindIdUseCase @Inject constructor(
    private val studentRepository: StudentsRepository
) : UseCase<FindIdParam, FindIdEntity>() {
    override suspend fun execute(data: FindIdParam): FindIdEntity =
        studentRepository.findId(data)
}