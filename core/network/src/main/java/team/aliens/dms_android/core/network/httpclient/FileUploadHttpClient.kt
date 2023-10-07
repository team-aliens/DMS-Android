package team.aliens.dms_android.core.network.httpclient

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import team.aliens.dms_android.core.network.util.toOctetStreamRequestBody
import java.io.File
import java.io.IOException

// TODO: consider defining class internal
class FileUploadHttpClient(
    private val httpLoggingInterceptor: HttpLoggingInterceptor,
    baseHttpClient: OkHttpClient,
) : OkHttpClient() {
    init {
        this.apply {
            /* config http client */
        }
    }

    operator fun invoke(
        fileUploadUrl: String,
        file: File,
    ) {
        val request = buildFileUploadRequest(
            file = file,
            fileUploadUrl = fileUploadUrl,
        )
        val response = newCall(request).execute()

        if (response.isSuccessful) {
            checkNotNull(response.body)
        } else {
            throw IOException()
        }
    }

    private fun buildFileUploadRequest(
        fileUploadUrl: String,
        file: File,
    ): Request = Request.Builder().url(fileUploadUrl).post(
        body = file.toOctetStreamRequestBody(),
    ).build()
}
