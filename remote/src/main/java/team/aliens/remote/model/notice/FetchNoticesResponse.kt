package team.aliens.remote.model.notice

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model.notice.FetchNoticesOutput
import java.util.*

data class FetchNoticesResponse(
    @SerializedName("notices") val noticeResponses: List<NoticeResponse>,
) {
    data class NoticeResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("title") val title: String,
        @SerializedName("created_at") val createdAt: String,
    )
}

internal fun FetchNoticesResponse.toDomain(): FetchNoticesOutput {
    return FetchNoticesOutput(
        notices = this.noticeResponses.toDomain(),
    )
}

internal fun FetchNoticesResponse.NoticeResponse.toDomain(): FetchNoticesOutput.NoticeInformation {
    return FetchNoticesOutput.NoticeInformation(
        id = this.id,
        title = this.title,
        createdAt = this.createdAt,
    )
}

internal fun List<FetchNoticesResponse.NoticeResponse>.toDomain(): List<FetchNoticesOutput.NoticeInformation> {
    return this.map(FetchNoticesResponse.NoticeResponse::toDomain)
}
