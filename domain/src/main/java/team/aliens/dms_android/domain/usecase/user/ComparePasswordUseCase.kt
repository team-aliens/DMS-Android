package team.aliens.dms_android.domain.usecase.user

import team.aliens.dms_android.domain.model.user.ComparePasswordInput
import team.aliens.dms_android.domain.repository.UserRepository
import javax.inject.Inject

class ComparePasswordUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        comparePasswordInput: ComparePasswordInput,
    ) {
        userRepository.comparePassword(
            input = comparePasswordInput,
        )
    }
}
