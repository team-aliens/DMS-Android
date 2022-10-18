package com.example.auth_domain.usecase.managers

import com.example.auth_domain.param.ChangePasswordParam
import com.example.auth_domain.repository.ManagersRepository
import com.example.auth_domain.usecase.UseCase
import javax.inject.Inject

class RemoteChangePasswordUseCase @Inject constructor(
    private val managersRepository: ManagersRepository
): UseCase<ChangePasswordParam, Unit>() {
    override suspend fun execute(data: ChangePasswordParam) {
        managersRepository.changePassword(data)
    }
}
