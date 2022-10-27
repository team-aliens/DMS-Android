package com.example.auth_data.remote.url

object DmsUrl {

    const val auth = "auth"
    const val students = "students"
    const val schools = "schools"

    object User {
        const val login = auth
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
    }

    object Schools {
        const val schoolQuestions= "$schools/question"
        const val schoolAnswer = "$schools/answer"
        const val schoolCode = "$schools/code"
    }
}
