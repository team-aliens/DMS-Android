package com.example.domain.usecase.studyroom

import com.example.domain.repository.StudyRoomRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemoteCancelApplySeat @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<Unit, Unit>() {
    override suspend fun execute(data: Unit) {
        studyRoomRepository.cancelApplySeat()
    }
}