package team.aliens.dms.android.domain.repository

import team.aliens.dms.android.domain.model.school.ExamineSchoolVerificationCodeInput
import team.aliens.dms.android.domain.model.school.ExamineSchoolVerificationCodeOutput
import team.aliens.dms.android.domain.model.school.ExamineSchoolVerificationQuestionInput
import team.aliens.dms.android.domain.model.school.FetchAvailableFeaturesOutput
import team.aliens.dms.android.domain.model.school.FetchSchoolVerificationQuestionInput
import team.aliens.dms.android.domain.model.school.FetchSchoolVerificationQuestionOutput
import team.aliens.dms.android.domain.model.school.FetchSchoolsOutput
import team.aliens.dms.android.domain.model.student.Features

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
