package team.aliens.domain.usecase.students

import team.aliens.domain.param.RegisterParam
import team.aliens.domain.repository.StudentsRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteSignUpUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository,
) : UseCase<RegisterParam, Unit>() {
    override suspend fun execute(data: RegisterParam) = studentsRepository.register(data)
}
