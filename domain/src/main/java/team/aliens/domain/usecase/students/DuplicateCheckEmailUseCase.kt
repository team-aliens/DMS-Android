package team.aliens.domain.usecase.students

import team.aliens.domain.repository.StudentsRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class DuplicateCheckEmailUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository,
) : UseCase<String, Unit>() {
    override suspend fun execute(data: String) {
        studentsRepository.duplicateCheckEmail(data)
    }
}