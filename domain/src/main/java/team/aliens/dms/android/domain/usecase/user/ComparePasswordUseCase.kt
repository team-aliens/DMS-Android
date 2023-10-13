package team.aliens.dms.android.domain.usecase.user

import team.aliens.dms.android.domain.model.user.ComparePasswordInput
import team.aliens.dms.android.domain.repository.UserRepository
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
