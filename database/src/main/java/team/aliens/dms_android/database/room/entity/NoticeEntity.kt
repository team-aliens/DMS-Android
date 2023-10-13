package team.aliens.dms_android.database.room.entity

import team.aliens.dms_android.core.database.entity.NoticeEntity
import team.aliens.dms.android.domain.model.notice.Notice

internal fun NoticeEntity.toDomain(): Notice {
    return Notice(
        id = this.id,
        title = this.title,
        content = this.content,
        createdAt = this.createdAt,
    )
}

internal fun List<NoticeEntity>.toDomain(): List<Notice> {
    return this.map(NoticeEntity::toDomain)
}

internal fun Notice.toData(): NoticeEntity {
    return NoticeEntity(
        id = this.id!!, // fixme 고민 필요
        title = this.title,
        content = this.content,
        createdAt = this.createdAt,
    )
}

internal fun Array<Notice>.toData(): Array<NoticeEntity> {
    return this.map(Notice::toData).toTypedArray()
}
