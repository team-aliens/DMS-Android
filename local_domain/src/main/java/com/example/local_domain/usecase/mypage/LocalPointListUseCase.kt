package com.example.local_domain.usecase.mypage

import com.example.local_domain.entity.mypage.PointListValueEntity
import com.example.local_domain.repository.mypage.LocalMyPageRepository
import com.example.local_domain.usecase.UseCase
import javax.inject.Inject

class LocalPointListUseCase @Inject constructor(
    private val localMyPageRepository: LocalMyPageRepository
): UseCase<String, List<PointListValueEntity>>() {

    override suspend fun execute(data: String): List<PointListValueEntity> =
        localMyPageRepository.fetchPointList(data)
}
