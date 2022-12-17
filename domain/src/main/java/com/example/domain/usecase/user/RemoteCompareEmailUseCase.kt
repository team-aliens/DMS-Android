package com.example.domain.usecase.user

import com.example.domain.param.CompareEmailParam
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemoteCompareEmailUseCase @Inject constructor(
    private val userRepository: UserRepository,
): UseCase<CompareEmailParam, Unit>() {
    override suspend fun execute(data: CompareEmailParam) {
        userRepository.compareEmail(data)
    }
}
