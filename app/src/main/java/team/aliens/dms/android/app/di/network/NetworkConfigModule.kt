package team.aliens.dms.android.app.di.network

import team.aliens.dms.android.network.BuildConfig as NetworkBuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import team.aliens.dms.android.core.jwt.di.TokenReissueUrl
import team.aliens.dms.android.core.jwt.network.IgnoreRequests
import team.aliens.dms.android.core.network.HttpMethod
import team.aliens.dms.android.core.network.HttpRequest
import team.aliens.dms.android.core.network.di.BaseUrl
import team.aliens.dms.android.core.network.httpclient.DefaultInterceptors
import team.aliens.dms.android.core.network.httpclient.GlobalInterceptors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkConfigModule {

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String = NetworkBuildConfig.BASE_URL

    @Provides
    @Singleton
    @TokenReissueUrl
    fun provideTokenReissueUrl(@BaseUrl baseUrl: String): String = "$baseUrl/auth/reissue"

    @Provides
    @Singleton
    fun provideIgnoreRequests(): IgnoreRequests = object : IgnoreRequests {
        override val requests: List<HttpRequest> = listOf(

            // Auth
            HttpRequest(
                method = HttpMethod.POST,
                path = "/auth/tokens",
            ),
            HttpRequest(
                method = HttpMethod.POST,
                path = "/auth/code",
            ),
            HttpRequest(
                method = HttpMethod.GET,
                path = "/auth/code",
            ),
            HttpRequest(
                method = HttpMethod.GET,
                path = "/auth/account-id",
            ),

            // Student
            HttpRequest(
                method = HttpMethod.POST,
                path = "/students/signup",
            ),
            HttpRequest(
                method = HttpMethod.GET,
                path = "/students/name",
            ),
            HttpRequest(
                method = HttpMethod.GET,
                path = "/students/account-id/",
            ),
            HttpRequest(
                method = HttpMethod.PATCH,
                path = "/students/password/initialization",
            ),
            HttpRequest(
                method = HttpMethod.GET,
                path = "/students/account-id/duplication",
            ),
            HttpRequest(
                method = HttpMethod.GET,
                path = "/students/email/duplication",
            ),

            // School
            HttpRequest(
                method = HttpMethod.GET,
                path = "/schools",
            ),
            HttpRequest(
                method = HttpMethod.GET,
                path = "/schools/question/",
            ),
            HttpRequest(
                method = HttpMethod.GET,
                path = "/schools/answer/",
            ),
            HttpRequest(
                method = HttpMethod.GET,
                path = "/schools/code",
            ),
        )
    }

    @Provides
    @Singleton
    fun provideDefaultInterceptors(): DefaultInterceptors = object : DefaultInterceptors {
        override val interceptors: List<Interceptor> = emptyList()
    }

    @Provides
    @Singleton
    fun provideGlobalInterceptors(): GlobalInterceptors = object : GlobalInterceptors {
        override val interceptors: List<Interceptor> = emptyList()
    }
}
