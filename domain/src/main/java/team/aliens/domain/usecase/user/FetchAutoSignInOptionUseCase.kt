package team.aliens.domain.usecase.user

import team.aliens.domain.repository.UserRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class FetchAutoSignInOptionUseCase
@Inject constructor(
    private val userRepository: UserRepository,
) : UseCase<Unit, Boolean>() {
    override suspend fun execute(data: Unit): Boolean {
        return userRepository.fetchAutoSignInOption()
    }
}