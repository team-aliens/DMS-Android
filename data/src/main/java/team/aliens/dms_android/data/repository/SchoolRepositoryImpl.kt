package team.aliens.dms_android.data.repository

import team.aliens.dms_android.data.datasource.local.LocalSchoolDataSource
import team.aliens.dms_android.data.datasource.remote.RemoteSchoolDataSource
import team.aliens.dms.android.domain.model.school.ExamineSchoolVerificationCodeInput
import team.aliens.dms.android.domain.model.school.ExamineSchoolVerificationCodeOutput
import team.aliens.dms.android.domain.model.school.ExamineSchoolVerificationQuestionInput
import team.aliens.dms.android.domain.model.school.FetchAvailableFeaturesOutput
import team.aliens.dms.android.domain.model.school.FetchSchoolVerificationQuestionInput
import team.aliens.dms.android.domain.model.school.FetchSchoolVerificationQuestionOutput
import team.aliens.dms.android.domain.model.school.FetchSchoolsOutput
import team.aliens.dms.android.domain.model.student.Features
import team.aliens.dms.android.domain.repository.SchoolRepository
import javax.inject.Inject

class SchoolRepositoryImpl @Inject constructor(
    private val localSchoolDataSource: LocalSchoolDataSource,
    private val remoteSchoolDataSource: RemoteSchoolDataSource,
) : SchoolRepository {

    override suspend fun fetchSchools(): FetchSchoolsOutput {
        return remoteSchoolDataSource.fetchSchools()
    }

    override suspend fun fetchSchoolVerificationQuestion(
        input: FetchSchoolVerificationQuestionInput,
    ): FetchSchoolVerificationQuestionOutput {
        return remoteSchoolDataSource.fetchSchoolVerificationQuestion(
            input = input,
        )
    }

    override suspend fun examineSchoolVerificationQuestion(
        input: ExamineSchoolVerificationQuestionInput,
    ) {
        return remoteSchoolDataSource.examineSchoolVerificationQuestion(
            input = input,
        )
    }

    override suspend fun examineSchoolVerificationCode(
        input: ExamineSchoolVerificationCodeInput,
    ): ExamineSchoolVerificationCodeOutput {
        return remoteSchoolDataSource.examineSchoolVerificationCode(
            input = input,
        )
    }

    override suspend fun fetchAvailableFeatures(): FetchAvailableFeaturesOutput {
        return remoteSchoolDataSource.fetchAvailableFeatures()
    }

    override suspend fun findFeatures(): Features {
        return localSchoolDataSource.findFeatures()
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

    override suspend fun saveFeatures(
        features: Features,
    ) {
        localSchoolDataSource.saveFeatures(
            features = features,
        )
    }
}
