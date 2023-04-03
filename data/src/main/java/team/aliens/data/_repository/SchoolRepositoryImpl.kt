package team.aliens.data._repository

import team.aliens.data._datasource.remote.RemoteSchoolDataSource
import team.aliens.domain._model.school.ExamineSchoolVerificationCodeOutput
import team.aliens.domain._model.school.FetchAvailableFeaturesOutput
import team.aliens.domain._model.school.FetchSchoolVerificationQuestionOutput
import team.aliens.domain._model.school.FetchSchoolsOutput
import team.aliens.domain._model.student.Feature
import team.aliens.domain._repository.SchoolRepository
import java.util.*
import javax.inject.Inject

class SchoolRepositoryImpl @Inject constructor(
    private val remoteSchoolDataSource: RemoteSchoolDataSource,
) : SchoolRepository {

    override suspend fun fetchSchools(): FetchSchoolsOutput {
        return remoteSchoolDataSource.fetchSchools()
    }

    override suspend fun fetchSchoolVerificationQuestion(
        schoolId: UUID,
    ): FetchSchoolVerificationQuestionOutput {
        return remoteSchoolDataSource.fetchSchoolVerificationQuestion(
            schoolId = schoolId,
        )
    }

    override suspend fun examineSchoolVerificationQuestion(
        schoolId: UUID,
        answer: String,
    ) {
        return remoteSchoolDataSource.examineSchoolVerificationQuestion(
            schoolId = schoolId,
            answer = answer,
        )
    }

    override suspend fun examineSchoolVerificationCode(
        schoolCode: String,
    ): ExamineSchoolVerificationCodeOutput {
        return remoteSchoolDataSource.examineSchoolVerificationCode(
            schoolCode = schoolCode,
        )
    }

    override suspend fun fetchAvailableFeatures(): FetchAvailableFeaturesOutput {
        return remoteSchoolDataSource.fetchAvailableFeatures()
    }

    override suspend fun findFeature(): Feature {
        TODO("Not yet implemented")
    }

    override suspend fun findMealFeatureEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun findNoticeFeatureEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun findPointServiceEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun findStudyRoomServiceEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun findRemainsServiceEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun saveFeature(
        feature: Feature,
    ) {
        TODO("Not yet implemented")
    }
}
