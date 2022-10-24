package com.example.auth_domain.usecase.auth

import com.example.auth_domain.param.ResetPasswordParam
import com.example.auth_domain.repository.UserRepository
import com.example.auth_domain.usecase.UseCase
import javax.inject.Inject

class RemoteResetPasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<ResetPasswordParam, Unit>() {
    override suspend fun execute(data: ResetPasswordParam) {
        userRepository.resetPassword(data)
    }
}