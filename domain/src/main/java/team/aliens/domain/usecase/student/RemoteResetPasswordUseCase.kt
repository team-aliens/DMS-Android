package team.aliens.domain.usecase.student

import team.aliens.domain.param.ResetPasswordParam
import team.aliens.domain.repository.StudentsRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteResetPasswordUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository,
) : UseCase<ResetPasswordParam, Unit>() {
    override suspend fun execute(data: ResetPasswordParam) {
        studentsRepository.resetPassword(data)
    }
}