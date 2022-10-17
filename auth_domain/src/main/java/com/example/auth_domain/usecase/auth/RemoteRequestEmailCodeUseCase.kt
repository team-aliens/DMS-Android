package com.example.auth_domain.usecase.auth

import com.example.auth_domain.param.RequestEmailCodeParam
import com.example.auth_domain.repository.UserRepository
import com.example.auth_domain.usecase.UseCase
import javax.inject.Inject

class RemoteRequestEmailCodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
): UseCase<RequestEmailCodeParam, Unit>() {
    override suspend fun execute(data: RequestEmailCodeParam) {
        userRepository.requestEmailCode(data)
    }
}
