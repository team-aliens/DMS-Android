package com.example.data.intercepter

import android.util.Log
import com.example.data.remote.response.user.SignInResponse
import com.example.data.util.LocalDateTimeEx
import com.example.domain.exception.NeedLoginException
import com.example.local_database.param.UserPersonalKeyParam
import com.example.local_database.storage.declaration.UserDataStorage
import com.example.local_database.localutil.toLocalDateTime
import com.google.gson.Gson
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val userDataStorage: UserDataStorage,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath
        val ignorePath = listOf(
            "/auth/tokens",
            "/auth/code",
            "/auth/email",
            "/students/signup",
            "/students/name",
            "/students/account-id/duplication",
            "/schools/code",
        )
        if (ignorePath.contains(path)) return chain.proceed(request)
        if (path.contains("/schools/answer/")) return chain.proceed(request)
        if (path.contains("/schools/question/")) return chain.proceed(request)

        val expiredAt =
            runBlocking { userDataStorage.fetchAccessTokenExpiredAt().toLocalDateTime() }
        val currentTime = LocalDateTimeEx.getNow()

        if (expiredAt.isBefore(currentTime)) {
            val client = OkHttpClient()
            val refreshToken = userDataStorage.fetchRefreshToken()

            val tokenRefreshRequest = Request.Builder()
                .url("http://3.39.162.197:8080/users/reissue")
                .put("".toRequestBody("application/json".toMediaTypeOrNull()))
                .addHeader("Refresh-Token", "Bearer $refreshToken")
                .build()
            val response = client.newCall(tokenRefreshRequest).execute()

            if (response.isSuccessful) {
                val token = Gson().fromJson(
                    response.body!!.toString(),
                    SignInResponse::class.java
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
            } else throw NeedLoginException()
        }

        val accessToken = userDataStorage.fetchAccessToken()
        Log.d("123123", accessToken)
        return chain.proceed(
            request.newBuilder().addHeader(
                "Authorization",
                "Bearer $accessToken"
            ).build()
        )
    }
}