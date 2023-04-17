package team.aliens.domain.usecase.student

import team.aliens.domain._model.student.FetchMyPageOutput
import team.aliens.domain._repository.StudentRepository
import javax.inject.Inject

class FetchMyPageUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(): FetchMyPageOutput {
        return studentRepository.fetchMyPage()
    }
}
