package com.example.domain.usecase.studyroom

import com.example.domain.entity.studyroom.StudyRoomDetailEntity
import com.example.domain.entity.studyroom.StudyRoomListEntity
import com.example.domain.repository.StudyRoomRepository
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteFetchStudyRoomDetailUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<String, StudyRoomDetailEntity>() {
    override suspend fun execute(data: String): StudyRoomDetailEntity =
        studyRoomRepository.fetchStudyRoomDetail(data)
}