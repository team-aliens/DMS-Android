package team.aliens.data.intercepter

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import org.threeten.bp.LocalDateTime
import team.aliens.data.remote.url.DmsHttpProperties
import team.aliens.data.remote.url.DmsUrl
import team.aliens.data.util.LocalDateTimeEx
import team.aliens.local_database.localutil.toLocalDateTime
import team.aliens.local_database.param.UserPersonalKeyParam
import team.aliens.local_database.storage.declaration.UserDataStorage
import javax.inject.Inject

private val ignorePath = listOf(
    DmsUrl.User.login,
    DmsUrl.User.reissueToken,
    DmsUrl.User.emailCode,
    DmsUrl.User.compareEmail,
    DmsUrl.Students.register,
    DmsUrl.Students.examineGrade,
    DmsUrl.Students.duplicateCheckId,
    DmsUrl.Students.duplicateCheckEmail,
    DmsUrl.Schools.schoolCode,
    "${DmsUrl.schools}/question",
    "${DmsUrl.schools}/answer",
)

class AuthorizationInterceptor @Inject constructor(
    private val userDataStorage: UserDataStorage,
    private val tokenReissueClient: TokenReissueClient,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val path = request.url.encodedPath

        if (ignorePath.any { path.contains(it) }) return chain.proceed(request)

        val expiredAt: LocalDateTime = try {
            runBlocking {
                userDataStorage.fetchAccessTokenExpiredAt().toLocalDateTime()
            }
        } catch (e: Exception) { // todo refactor
            LocalDateTime.of(
                1970,
                1,
                1,
                0,
                0,
            )
        }

        val currentTime = LocalDateTimeEx.getNow()

        if (currentTime.isAfter(expiredAt)) {

            val refreshToken = userDataStorage.fetchRefreshToken()

            val token = tokenReissueClient(
                refreshToken = refreshToken,
            )

            runBlocking {
                userDataStorage.setPersonalKey(
                    personalKeyParam = UserPersonalKeyParam(
                        accessToken = token.accessToken,
                        refreshToken = token.refreshToken,
                        accessTokenExpiredAt = token.accessTokenExpiredAt.toLocalDateTime(),
                        refreshTokenExpiredAt = token.refreshTokenExpiredAt.toLocalDateTime(),
                    ),
                )
            }
        }

        val accessToken = userDataStorage.fetchAccessToken()

        return chain.proceed(
            request.newBuilder().addHeader(
                DmsHttpProperties.Header.AUTHORIZATION,
                DmsHttpProperties.Prefix.BEARER + accessToken,
            ).build(),
        )
    }
}
