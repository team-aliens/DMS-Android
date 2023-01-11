package com.example.domain.usecase.studyroom

import com.example.domain.entity.studyroom.SeatTypeEntity
import com.example.domain.repository.StudyRoomRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemoteFetchStudyRoomTypeUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<Unit, SeatTypeEntity>() {
    override suspend fun execute(data: Unit): SeatTypeEntity =
        studyRoomRepository.fetchStudyRoomType()
}