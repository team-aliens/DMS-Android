package com.example.domain.usecase.studyroom

import com.example.domain.entity.studyroom.ApplySeatTimeEntity
import com.example.domain.repository.StudyRoomRepository
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteFetchApplySeatUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository
): UseCase<Unit, ApplySeatTimeEntity>() {
    override suspend fun execute(data: Unit): ApplySeatTimeEntity =
        studyRoomRepository.fetchApplySeatTime()
}