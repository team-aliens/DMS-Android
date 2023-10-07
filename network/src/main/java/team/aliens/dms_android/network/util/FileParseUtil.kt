package team.aliens.dms_android.network.util

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import team.aliens.network.common.HttpProperty
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

internal fun File.toOctetStreamRequestBody(): RequestBody {
    return this.readBytes().toRequestBody(
        HttpProperty.Header.ContentType.Application.OctetStream.toMediaType(),
    )
}
