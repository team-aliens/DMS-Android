package com.example.auth_domain.usecase.user

import com.example.auth_domain.param.CompareEmailParam
import com.example.auth_domain.repository.UserRepository
import com.example.auth_domain.usecase.UseCase
import javax.inject.Inject

class RemoteCompareEmailUseCase @Inject constructor(
    private val userRepository: UserRepository,
): UseCase<CompareEmailParam, Unit>() {
    override suspend fun execute(data: CompareEmailParam) {
        userRepository.compareEmail(data)
    }
}
