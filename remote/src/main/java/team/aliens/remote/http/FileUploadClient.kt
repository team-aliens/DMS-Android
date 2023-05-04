package team.aliens.remote.http

import okhttp3.OkHttpClient
import okhttp3.Request
import team.aliens.remote.util.toOctetStreamRequestBody
import java.io.File
import java.io.IOException
import javax.inject.Inject

class FileUploadClient @Inject constructor() : OkHttpClient() {

    operator fun invoke(
        file: File,
        fileUploadUrl: String,
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
        file: File,
        fileUploadUrl: String,
    ): Request {

        val parsedFile = file.toOctetStreamRequestBody()

        return Request.Builder().url(
            fileUploadUrl,
        ).post(
            parsedFile,
        ).build()
    }
}
