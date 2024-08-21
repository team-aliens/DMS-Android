package team.aliens.dms.android.data.notice.mapper

import team.aliens.dms.android.core.database.entity.NoticeEntity
import team.aliens.dms.android.data.notice.model.Notice
import team.aliens.dms.android.network.notice.model.FetchNoticeDetailsResponse
import team.aliens.dms.android.network.notice.model.FetchNoticesResponse
import team.aliens.dms.android.shared.date.toLocalDateTime

internal fun List<NoticeEntity>.toModel() = this.map(NoticeEntity::toModel)

private fun NoticeEntity.toModel() = Notice(
    id = this.id,
    title = this.title,
    content = this.content,
    createdAt = this.createdAt,
)

internal fun FetchNoticesResponse.toModel(): List<Notice> =
    this.notices.map(FetchNoticesResponse.NoticeResponse::toModel)

private fun FetchNoticesResponse.NoticeResponse.toModel(): Notice = Notice(
    id = this.id,
    title = this.title,
    content = null,
    createdAt = this.createdAt.toLocalDateTime(),
)

internal fun FetchNoticeDetailsResponse.toModel(): Notice = Notice(
    id = this.noticeId,
    title = this.title,
    content = this.content,
    createdAt = this.createdAt.toLocalDateTime(),
)
