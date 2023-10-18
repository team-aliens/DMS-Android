package team.aliens.dms.android.data.notice.mapper

import team.aliens.dms.android.core.database.entity.NoticeEntity
import team.aliens.dms.android.data.notice.model.Notice

internal fun NoticeEntity.toModel() = Notice(
    id = this.id,
    title = this.title,
    content = this.content,
    createdAt = this.createdAt,
)

internal fun List<NoticeEntity>.toModel() = this.map(NoticeEntity::toModel)

internal fun Notice.toEntity() = NoticeEntity(
    id = this.id,
    title = this.title,
    content = this.content,
    createdAt = this.createdAt,
)

internal fun List<Notice>.toEntity() = this.map(Notice::toEntity)
