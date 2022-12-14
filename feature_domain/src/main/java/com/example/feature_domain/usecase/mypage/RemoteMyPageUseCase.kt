package com.example.feature_domain.usecase.mypage

import com.example.feature_domain.entity.mypage.MyPageEntity
import com.example.feature_domain.repository.MyPageRepository
import com.example.feature_domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteMyPageUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository
): UseCase<Unit, Flow<MyPageEntity>>() {
    override suspend fun execute(data: Unit): Flow<MyPageEntity> =
        myPageRepository.fetchMyPage()
}
