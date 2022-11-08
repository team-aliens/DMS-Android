package com.example.feature_data.remote.url

object DmsUrl {

    const val meal = "meals"

    const val notices = "notices"

    object Notice {
        const val NewNoticeBoolean = "$notices/status"
        const val NoticeDetail = "$notices/{notice-id}"
    }
}