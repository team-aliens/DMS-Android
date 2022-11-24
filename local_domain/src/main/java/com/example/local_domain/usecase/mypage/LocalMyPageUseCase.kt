package com.example.local_domain.usecase.mypage

import com.example.local_domain.entity.meal.MealEntity
import com.example.local_domain.param.MyPageParam
import com.example.local_domain.repository.meal.LocalMealRepository
import com.example.local_domain.repository.mypage.LocalMyPageRepository
import com.example.local_domain.usecase.UseCase
import javax.inject.Inject

class LocalMyPageUseCase @Inject constructor(
    private val localMyPageRepository: LocalMyPageRepository
): UseCase<Unit, MyPageParam>() {

    override suspend fun execute(data: Unit): MyPageParam =
        localMyPageRepository.fetchMyPage()
}
