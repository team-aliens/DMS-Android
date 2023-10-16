package team.aliens.dms.android.data.notice.mapper

import org.threeten.bp.LocalDateTime
import team.aliens.dms.android.core.database.entity.NoticeEntity
import team.aliens.dms.android.domain.model.notice.Notice

internal fun NoticeEntity.toDomain() = Notice(
    id = this.id,
    title = this.title,
    content = this.content,
    createdAt = this.createdAt.toString(), // TODO: fix
)

internal fun List<NoticeEntity>.toDomain() = this.map(NoticeEntity::toDomain)

internal fun Notice.toData() = NoticeEntity(
    id = this.id!!,
    title = this.title,
    content = this.content,
    createdAt = LocalDateTime.now(), // FIXME: this.createdAt
)

internal fun List<Notice>.toData() = this.map(Notice::toData)
