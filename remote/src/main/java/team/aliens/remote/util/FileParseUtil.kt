package team.aliens.remote.util

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

internal fun File.toMultipart(
    name: String,
    mediaType: String,
): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        name = name,
        filename = this.name,
        body = this.asRequestBody(
            mediaType.toMediaType()
        ),
    )
}

internal fun File.toRequestBody(
    mediaType: String,
): RequestBody {
    return this.asRequestBody(
        contentType = mediaType.toMediaType(),
    )
}
