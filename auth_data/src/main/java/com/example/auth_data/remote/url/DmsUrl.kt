package com.example.auth_data.remote.url

object DmsUrl {

    const val auth = "auth"
    const val students = "students"
    const val managers = "managers"

    object User {
        const val login = auth
        const val emailCode = "$auth/code"
        const val refreshToken = "$auth/reissue"
        const val compareEmail = "$auth/email"
        const val checkId = "$auth/account-id"
    }

    object Students {
        const val register = "$students/signup"
    }

    object Managers {
        const val findId = "$managers/account-id"
        const val changePassword = "$managers/password/initialization"
    }
}