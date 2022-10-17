package com.example.auth_domain.usecase.auth

import com.example.auth_domain.param.CheckEmailCodeParam
import com.example.auth_domain.repository.UserRepository
import com.example.auth_domain.usecase.UseCase
import javax.inject.Inject

class RemoteCheckEmailUseCase @Inject constructor(
    private val userRepository: UserRepository,
): UseCase<CheckEmailCodeParam, Unit>() {
    override suspend fun execute(data: CheckEmailCodeParam) {
        userRepository.checkEmailCode(data)
    }
}
