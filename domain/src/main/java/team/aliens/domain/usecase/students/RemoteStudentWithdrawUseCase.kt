package team.aliens.domain.usecase.students

import team.aliens.domain.repository.StudentsRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteStudentWithdrawUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository,
) : UseCase<Unit, Unit>(){
    override suspend fun execute(data: Unit) {
        studentsRepository.withdraw()
    }
}
