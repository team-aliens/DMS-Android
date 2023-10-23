package team.aliens.dms.android.core.school.network

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import team.aliens.dms.android.core.school.exception.CannotFetchFeaturesException
import team.aliens.dms.android.core.school.network.model.FeaturesResponse
import javax.inject.Inject

class FeaturesFetchingManager @Inject constructor(
    private val fetchUrl: String,
    private val httpLoggingInterceptor: HttpLoggingInterceptor,
    baseHttpClient: OkHttpClient,
) {
    private val client: OkHttpClient by lazy {
        baseHttpClient.newBuilder().apply {
            addInterceptor(httpLoggingInterceptor)
        }.build()
    }

    operator fun invoke(accessToken: String): FeaturesResponse {
        val request = buildFeaturesFetchingRequest(accessToken)
        val response = client.newCall(request).execute()

        return if (response.isSuccessful) {
            response.body.toFeaturesResponse()
        } else {
            throw CannotFetchFeaturesException()
        }
    }

    private fun ResponseBody?.toFeaturesResponse(): FeaturesResponse {
        requireNotNull(this)
        return Gson().fromJson(this.string(), FeaturesResponse::class.java)
    }

    private fun buildFeaturesFetchingRequest(accessToken: String): Request =
        Request.Builder().url(fetchUrl).put(
            body = String().toRequestBody("application/json".toMediaType()),
        ).addHeader(
            name = "access-token",
            value = accessToken,
        ).build()
}
