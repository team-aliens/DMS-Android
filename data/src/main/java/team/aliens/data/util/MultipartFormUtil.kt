package team.aliens.data.util

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

internal object MultipartFormUtil {
    fun getFileBody(
        name: String,
        file: File,
    ): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            name = name,
            filename = file.name,
            body = file.asRequestBody(
                "multipart/form-data".toMediaType(),
            )
        )
    }
}
