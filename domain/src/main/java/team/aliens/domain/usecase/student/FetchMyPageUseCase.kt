package team.aliens.domain.usecase.student

import team.aliens.domain.model.student.FetchMyPageOutput
import team.aliens.domain.repository.StudentRepository
import javax.inject.Inject

class FetchMyPageUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(): FetchMyPageOutput {
        return studentRepository.fetchMyPage()
    }
}
