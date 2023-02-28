package team.aliens.domain.usecase.user

import team.aliens.domain.param.EditPasswordParam
import team.aliens.domain.repository.UserRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class EditPasswordUseCase @Inject constructor(
    private val userRepository: UserRepository,
): UseCase<EditPasswordParam, Unit>() {
    override suspend fun execute(data: EditPasswordParam) {
        userRepository.editPassword(
            editPasswordParam = data,
        )
    }
}