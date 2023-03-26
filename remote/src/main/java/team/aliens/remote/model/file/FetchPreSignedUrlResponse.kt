package team.aliens.remote.model.file

import com.google.gson.annotations.SerializedName

data class FetchPreSignedUrlResponse(
    @SerializedName("file_upload_url") val fileUploadUrl: String,
    @SerializedName("file_url") val fileUrl: String,
)
