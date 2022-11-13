package com.example.feature_data.remote.url

object DmsUrl {

    const val meal = "/meals"
    const val notices = "/notices"
    const val students = "/students"

    object Notice {
        const val NewNoticeBoolean = "$notices/status"
        const val NoticeDetail = "$notices/{notice-id}"
    }

    object MyPage {
        const val MyPage = "${students}/profile"
        const val Point = "/point"
    }
}
