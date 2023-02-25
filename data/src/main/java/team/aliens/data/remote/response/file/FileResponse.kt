package team.aliens.data.remote.response.file

import com.google.gson.annotations.SerializedName
import team.aliens.domain.entity.file.FileEntity

data class FileResponse(
    @SerializedName("file_url") val fileUrl: String,
)

internal fun FileResponse.toEntity(): FileEntity {
    return FileEntity(
        fileUrl = this.fileUrl,
    )
}
