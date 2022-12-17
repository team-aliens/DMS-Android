package com.example.domain.usecase.mypage

import com.example.domain.entity.mypage.MyPageEntity
import com.example.domain.repository.MyPageRepository
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteMyPageUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository
): UseCase<Unit, Flow<MyPageEntity>>() {
    override suspend fun execute(data: Unit): Flow<MyPageEntity> =
        myPageRepository.fetchMyPage()
}
