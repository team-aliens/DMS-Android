package com.example.domain.usecase.studyroom

import com.example.domain.entity.studyroom.StudyRoomListEntity
import com.example.domain.repository.StudyRoomRepository
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteFetchStudyRoomListUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<Unit, Flow<StudyRoomListEntity>>() {
    override suspend fun execute(data: Unit): Flow<StudyRoomListEntity> =
        studyRoomRepository.fetchStudyRoomList()
}