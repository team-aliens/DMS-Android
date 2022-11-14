package com.example.feature_domain.usecase.mypage

import com.example.feature_domain.entity.mypage.PointListEntity
import com.example.feature_domain.enums.PointType
import com.example.feature_domain.repository.MyPageRepository
import com.example.feature_domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemotePointUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository
): UseCase<PointType, Flow<PointListEntity>>() {
    override suspend fun execute(data: PointType): Flow<PointListEntity> =
        myPageRepository.fetchPointList(data)
}
