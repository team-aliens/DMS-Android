package com.example.auth_domain.usecase.auth

import com.example.auth_domain.param.LoginParam
import com.example.auth_domain.repository.UserRepository
import com.example.auth_domain.usecase.UseCase
import javax.inject.Inject

class RemoteSignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
): UseCase<LoginParam, Unit>() {
    override suspend fun execute(data: LoginParam) {
        userRepository.login(data)
    }
}
