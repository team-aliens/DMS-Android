package team.aliens.data._datasource.local

import team.aliens.domain._model.notice.Notice

interface LocalNoticeDataSource {

    fun fetchNotices(): List<Notice>

    fun fetchNotice(): Notice

    fun saveNotice(
        notice: Notice,
    )

    fun saveNotices(
        vararg notices: Notice,
    )
}
