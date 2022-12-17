package com.example.data.remote.url

object DmsUrl {

    const val meal = "meals"
    const val notices = "notices"
    const val students = "students"
    const val auth = "auth"
    const val schools = "schools"

    object User {
        const val login = "auth/tokens"
        const val emailCode = "$auth/code"
        const val refreshToken = "$auth/reissue"
        const val compareEmail = "$auth/email"
        const val checkId = "$auth/account-id"
    }

    object Students {
        const val register = "$students/signup"
        const val duplicateCheckId = "$students/account-id/duplication"
        const val duplicateCheckEmail = "$students/email/duplication"
        const val resetPassword = "$students/password/initialization"
        const val examineGrade = "$students/name"
    }

    object Schools {
        const val schoolQuestions= "$schools/question"
        const val schoolAnswer = "$schools/answer"
        const val schoolCode = "$schools/code"
    }

    object Notice {
        const val NewNoticeBoolean = "$notices/status"
        const val NoticeDetail = "$notices/{notice-id}"
    }

    object MyPage {
        const val MyPage = "${students}/profile"
        const val Point = "point"
    }
}
