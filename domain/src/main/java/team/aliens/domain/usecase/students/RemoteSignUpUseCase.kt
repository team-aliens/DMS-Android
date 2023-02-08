package team.aliens.domain.usecase.students

import com.example.domain.param.RegisterParam
import com.example.domain.repository.StudentsRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemoteSignUpUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository,
): UseCase<RegisterParam, Unit>() {
    override suspend fun execute(data: RegisterParam) =
        studentsRepository.register(data)
}
