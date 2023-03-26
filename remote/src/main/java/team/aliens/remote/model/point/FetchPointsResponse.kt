package team.aliens.remote.model.point

import com.google.gson.annotations.SerializedName

data class FetchPointsResponse(
    @SerializedName("total_point") val totalPoint: Int,
    @SerializedName("points") val points: List<Point>,
) {
    data class Point(
        @SerializedName("date") val date: String,
        @SerializedName("type") val type: String,
        @SerializedName("name") val name: String,
        @SerializedName("score") val score: Int,
    )
}
