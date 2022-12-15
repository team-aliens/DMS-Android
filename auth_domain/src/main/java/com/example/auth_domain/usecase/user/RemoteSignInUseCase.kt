package com.example.auth_domain.usecase.user

import androidx.lifecycle.MutableLiveData
import com.example.auth_domain.entity.AuthInfoEntity
import com.example.auth_domain.param.LoginParam
import com.example.auth_domain.repository.UserRepository
import com.example.auth_domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteSignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
): UseCase<LoginParam, Flow<AuthInfoEntity>>() {
    override suspend fun execute(data: LoginParam): Flow<AuthInfoEntity> =
        userRepository.login(data)
}
