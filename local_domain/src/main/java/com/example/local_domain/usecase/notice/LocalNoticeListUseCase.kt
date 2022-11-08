package com.example.local_domain.usecase.notice

import com.example.local_domain.entity.meal.MealEntity
import com.example.local_domain.entity.notice.NoticeListLocalEntity
import com.example.local_domain.repository.meal.LocalMealRepository
import com.example.local_domain.repository.notice.LocalNoticeRepository
import com.example.local_domain.usecase.UseCase
import javax.inject.Inject

class LocalNoticeListUseCase @Inject constructor(
    private val localNoticeRepository: LocalNoticeRepository
): UseCase<Unit, NoticeListLocalEntity>() {

    override suspend fun execute(data: Unit): NoticeListLocalEntity =
        localNoticeRepository.fetchNoticeList()
}
