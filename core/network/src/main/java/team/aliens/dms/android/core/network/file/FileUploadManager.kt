package team.aliens.dms.android.core.network.file

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import team.aliens.dms.android.core.network.util.toOctetStreamRequestBody
import java.io.File
import java.io.IOException

// TODO: consider defining class as FileUploadManager
class FileUploadManager(
    private val httpLoggingInterceptor: HttpLoggingInterceptor,
    baseHttpClient: OkHttpClient,
) {
    private val client: OkHttpClient by lazy {
        baseHttpClient.apply { /* config */ }
    }

    operator fun invoke(
        fileUploadUrl: String,
        file: File,
    ) {
        val request = buildFileUploadRequest(
            file = file,
            fileUploadUrl = fileUploadUrl,
        )
        val response = client.newCall(request).execute()

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
