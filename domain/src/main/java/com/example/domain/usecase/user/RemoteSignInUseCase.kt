package com.example.domain.usecase.user

import com.example.domain.entity.user.AuthInfoEntity
import com.example.domain.param.LoginParam
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteSignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
): UseCase<LoginParam, Flow<AuthInfoEntity>>() {
    override suspend fun execute(data: LoginParam): Flow<AuthInfoEntity> =
        userRepository.login(data)
}
