package team.aliens.dms.android.network.model.file

import com.google.gson.annotations.SerializedName
import team.aliens.dms.android.domain.model.file.UploadFileOutput

data class UploadFileResponse(
    @SerializedName("file_url") val fileUrl: String,
)

internal fun UploadFileResponse.toDomain(): UploadFileOutput {
    return UploadFileOutput(
        fileUrl = this.fileUrl,
    )
}
