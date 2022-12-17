package com.example.domain.usecase.user

import com.example.domain.param.RequestEmailCodeParam
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemoteRequestEmailCodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
): UseCase<RequestEmailCodeParam, Unit>() {
    override suspend fun execute(data: RequestEmailCodeParam) {
        userRepository.requestEmailCode(data)
    }
}
