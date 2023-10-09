package team.aliens.dms_android.domain.repository

import team.aliens.domain.model.school.ExamineSchoolVerificationCodeInput
import team.aliens.domain.model.school.ExamineSchoolVerificationCodeOutput
import team.aliens.domain.model.school.ExamineSchoolVerificationQuestionInput
import team.aliens.domain.model.school.FetchAvailableFeaturesOutput
import team.aliens.domain.model.school.FetchSchoolVerificationQuestionInput
import team.aliens.domain.model.school.FetchSchoolVerificationQuestionOutput
import team.aliens.domain.model.school.FetchSchoolsOutput
import team.aliens.domain.model.student.Features

interface SchoolRepository {

    suspend fun fetchSchools(): FetchSchoolsOutput

    suspend fun fetchSchoolVerificationQuestion(
        input: FetchSchoolVerificationQuestionInput,
    ): FetchSchoolVerificationQuestionOutput

    suspend fun examineSchoolVerificationQuestion(
        input: ExamineSchoolVerificationQuestionInput,
    )

    suspend fun examineSchoolVerificationCode(
        input: ExamineSchoolVerificationCodeInput,
    ): ExamineSchoolVerificationCodeOutput

    suspend fun fetchAvailableFeatures(): FetchAvailableFeaturesOutput

    suspend fun findFeatures(): Features

    suspend fun findMealFeatureEnabled(): Boolean

    suspend fun findNoticeFeatureEnabled(): Boolean

    suspend fun findPointServiceEnabled(): Boolean

    suspend fun findStudyRoomServiceEnabled(): Boolean

    suspend fun findRemainsServiceEnabled(): Boolean

    suspend fun saveFeatures(features: Features)
}
