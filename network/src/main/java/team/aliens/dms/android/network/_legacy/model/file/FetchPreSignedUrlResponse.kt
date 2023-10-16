package team.aliens.dms.android.network._legacy.model.file

import com.google.gson.annotations.SerializedName
import team.aliens.dms.android.domain.model.file.FetchPreSignedUrlOutput

data class FetchPreSignedUrlResponse(
    @SerializedName("file_upload_url") val fileUploadUrl: String,
    @SerializedName("file_url") val fileUrl: String,
)

internal fun FetchPreSignedUrlResponse.toDomain(): FetchPreSignedUrlOutput {
    return FetchPreSignedUrlOutput(
        fileUploadUrl = this.fileUploadUrl,
        fileUrl = this.fileUrl,
    )
}
