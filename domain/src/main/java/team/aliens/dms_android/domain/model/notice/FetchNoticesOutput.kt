package team.aliens.dms_android.domain.model.notice

import java.util.UUID

/**
 * A response returned when fetching notices
 * @property notices list of notices of the user
 */
data class FetchNoticesOutput(
    val notices: List<NoticeInformation>,
) {

    /**
     * @property id id of unique notice
     * @property title title of the notice
     * @property createdAt created time of the notice
     */
    data class NoticeInformation(
        val id: UUID,
        val title: String,
        val createdAt: String,
    )
}

fun FetchNoticesOutput.NoticeInformation.toModel(): Notice {
    return Notice(
        id = this.id,
        title = this.title,
        createdAt = this.createdAt,
    )
}

fun List<FetchNoticesOutput.NoticeInformation>.toModel(): List<Notice> {
    return this.map(FetchNoticesOutput.NoticeInformation::toModel)
}

fun FetchNoticesOutput.toModel(): List<Notice> {
    return this.notices.toModel()
}

fun List<FetchNoticesOutput.NoticeInformation>.toTypedArray(): Array<Notice> {
    return this.toModel().toTypedArray()
}
