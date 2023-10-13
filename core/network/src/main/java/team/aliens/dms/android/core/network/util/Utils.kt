package team.aliens.dms.android.core.network.util

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun File.toMultipart(
    name: String,
    mediaType: String,
): MultipartBody.Part = MultipartBody.Part.createFormData(
    name = name,
    filename = this.name,
    body = this.asRequestBody(mediaType.toMediaType()),
)

fun File.toOctetStreamRequestBody(): RequestBody =
    this.readBytes().toRequestBody("application/octet-stream".toMediaType())
