package team.aliens.local.datastore.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.last
import team.aliens.domain._exception.LocalException
import team.aliens.domain._model.auth.Token
import team.aliens.domain._model.student.Feature
import team.aliens.local.datastore.common.DataStoreProperty.Key.Auth.AccessToken
import team.aliens.local.datastore.common.DataStoreProperty.Key.Auth.AccessTokenExpiredAt
import team.aliens.local.datastore.common.DataStoreProperty.Key.Auth.AutoSignIn
import team.aliens.local.datastore.common.DataStoreProperty.Key.Auth.Feature.MealService
import team.aliens.local.datastore.common.DataStoreProperty.Key.Auth.Feature.NoticeService
import team.aliens.local.datastore.common.DataStoreProperty.Key.Auth.Feature.PointService
import team.aliens.local.datastore.common.DataStoreProperty.Key.Auth.Feature.RemainsService
import team.aliens.local.datastore.common.DataStoreProperty.Key.Auth.Feature.StudyRoomService
import team.aliens.local.datastore.common.DataStoreProperty.Key.Auth.RefreshToken
import team.aliens.local.datastore.common.DataStoreProperty.Key.Auth.RefreshTokenExpiredAt

class AuthDataStorageImpl(
    private val authDataStore: DataStore<Preferences>,
) : AuthDataStorage {

    override suspend fun findToken(): Token {

        val fetchedAccessToken = this.findAccessToken()
        val fetchedAccessTokenExpiredAt = this.findAccessTokenExpiredAt()
        val fetchedRefreshToken = this.findRefreshToken()
        val fetchedRefreshTokenExpiredAt = this.findRefreshTokenExpiredAt()

        return Token(
            accessToken = fetchedAccessToken,
            accessTokenExpiredAt = fetchedAccessTokenExpiredAt,
            refreshToken = fetchedRefreshToken,
            refreshTokenExpiredAt = fetchedRefreshTokenExpiredAt,
        )
    }

    override suspend fun findAccessToken(): String {
        return authDataStore.data.last()[AccessToken] ?: throw LocalException.AccessTokenNotFound
    }

    override suspend fun findAccessTokenExpiredAt(): String {
        return authDataStore.data.last()[AccessTokenExpiredAt]
            ?: throw LocalException.AccessTokenExpiredNotFound
    }

    override suspend fun findRefreshToken(): String {
        return authDataStore.data.last()[RefreshToken] ?: throw LocalException.RefreshTokenNotFound
    }

    override suspend fun findRefreshTokenExpiredAt(): String {
        return authDataStore.data.last()[RefreshTokenExpiredAt]
            ?: throw LocalException.RefreshTokenExpiredAtNotFound
    }

    override suspend fun saveToken(
        token: Token,
    ) {
        authDataStore.edit {
            with(token) {
                it[AccessToken] = this.accessToken
                it[AccessTokenExpiredAt] = this.accessTokenExpiredAt
                it[RefreshToken] = this.refreshToken
                it[RefreshTokenExpiredAt] = this.refreshTokenExpiredAt
            }
        }
    }

    override suspend fun clearToken() {
        authDataStore.edit {
            it.clear()
        }
    }

    override suspend fun findAutoSignInOption(): Boolean {
        return authDataStore.data.last()[AutoSignIn] ?: false
    }

    override suspend fun updateAutoSignInOption(
        autoSignIn: Boolean,
    ) {
        authDataStore.edit {
            it[AutoSignIn] = autoSignIn
        }
    }

    override suspend fun findFeature(): Feature {

        val fetchedMealOption = this.findMealFeatureEnabled()
        val fetchedNoticeOption = this.findNoticeFeatureEnabled()
        val fetchedPointOption = this.findPointServiceEnabled()
        val fetchedStudyRoomOption = this.findStudyRoomServiceEnabled()
        val fetchedRemainsOption = this.findRemainsServiceEnabled()

        return Feature(
            mealService = fetchedMealOption,
            noticeService = fetchedNoticeOption,
            pointService = fetchedPointOption,
            studyRoomService = fetchedStudyRoomOption,
            remainsService = fetchedRemainsOption,
        )
    }

    override suspend fun findMealFeatureEnabled(): Boolean {
        return authDataStore.data.last()[MealService] ?: false
    }

    override suspend fun findNoticeFeatureEnabled(): Boolean {
        return authDataStore.data.last()[NoticeService] ?: false
    }

    override suspend fun findPointServiceEnabled(): Boolean {
        return authDataStore.data.last()[PointService] ?: false
    }

    override suspend fun findStudyRoomServiceEnabled(): Boolean {
        return authDataStore.data.last()[StudyRoomService] ?: false
    }

    override suspend fun findRemainsServiceEnabled(): Boolean {
        return authDataStore.data.last()[RemainsService] ?: false
    }

    override suspend fun saveFeature(
        feature: Feature,
    ) {
        authDataStore.edit {
            it[MealService] = feature.mealService
            it[NoticeService] = feature.noticeService
            it[PointService] = feature.pointService
            it[StudyRoomService] = feature.studyRoomService
            it[RemainsService] = feature.remainsService
        }
    }
}
