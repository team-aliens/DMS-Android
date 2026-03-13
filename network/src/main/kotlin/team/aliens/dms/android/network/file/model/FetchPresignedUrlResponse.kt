package team.aliens.dms.android.network.file.model

import com.google.gson.annotations.SerializedName

data class FetchPresignedUrlResponse(
    @SerializedName("file_upload_url") val fileUploadUrl: String,
    @SerializedName("file_url") val fileUrl: String,
)
