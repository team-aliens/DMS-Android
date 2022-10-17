package com.example.auth_domain.usecase.auth

import com.example.auth_domain.param.RegisterParam
import com.example.auth_domain.repository.UserRepository
import com.example.auth_domain.usecase.UseCase
import javax.inject.Inject

class RemoteSignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
): UseCase<RegisterParam, Unit>() {
    override suspend fun execute(data: RegisterParam) {
        userRepository.register(data)
    }
}
