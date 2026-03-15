package team.aliens.dms.android.network.file.model

import com.google.gson.annotations.SerializedName

data class FetchFileUrlResponse(
    @SerializedName("file_url") val fileUrl: String,
)
