package com.example.auth_domain.usecase.managers

import com.example.auth_domain.param.FindIdParam
import com.example.auth_domain.repository.ManagersRepository
import com.example.auth_domain.usecase.UseCase
import javax.inject.Inject

class RemoteFindIdUseCase @Inject constructor(
    private val managersRepository: ManagersRepository
): UseCase<FindIdParam, Unit>() {
    override suspend fun execute(data: FindIdParam) {
        managersRepository.findId(data)
    }
}
