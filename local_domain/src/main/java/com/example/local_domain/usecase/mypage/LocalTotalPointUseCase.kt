package com.example.local_domain.usecase.mypage

import com.example.local_domain.param.MyPageParam
import com.example.local_domain.repository.mypage.LocalMyPageRepository
import com.example.local_domain.usecase.UseCase
import javax.inject.Inject

class LocalTotalPointUseCase @Inject constructor(
    private val localMyPageRepository: LocalMyPageRepository
): UseCase<Unit, Int>() {

    override suspend fun execute(data: Unit): Int =
        localMyPageRepository.fetchTotalPoint()
}
