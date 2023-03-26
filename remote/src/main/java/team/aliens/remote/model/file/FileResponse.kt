package team.aliens.remote.model.file

import com.google.gson.annotations.SerializedName

data class FileResponse(
    @SerializedName("file_url") val fileUrl: String,
)
