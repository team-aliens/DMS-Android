package team.aliens.data._repository

import team.aliens.data._datasource.local.LocalSchoolDataSource
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
    private val localSchoolDataSource: LocalSchoolDataSource,
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
        return localSchoolDataSource.findFeature()
    }

    override suspend fun findMealFeatureEnabled(): Boolean {
        return localSchoolDataSource.findMealFeatureEnabled()
    }

    override suspend fun findNoticeFeatureEnabled(): Boolean {
        return localSchoolDataSource.findNoticeFeatureEnabled()
    }

    override suspend fun findPointServiceEnabled(): Boolean {
        return localSchoolDataSource.findPointServiceEnabled()
    }

    override suspend fun findStudyRoomServiceEnabled(): Boolean {
        return localSchoolDataSource.findStudyRoomServiceEnabled()
    }

    override suspend fun findRemainsServiceEnabled(): Boolean {
        return localSchoolDataSource.findRemainsServiceEnabled()
    }

    override suspend fun saveFeature(
        feature: Feature,
    ) {
        localSchoolDataSource.saveFeature(
            feature = feature,
        )
    }
}
