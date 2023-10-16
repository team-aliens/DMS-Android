package team.aliens.dms.android.network._legacy.http

import team.aliens.dms.android.network._legacy.common.HttpMethod
import team.aliens.dms.android.network.common.HttpPath

object IgnoreRequestWrapper {
    val ignoreRequests: ArrayList<Request> = arrayListOf(

        // Auth
        Request(
            method = team.aliens.dms.android.network._legacy.common.HttpMethod.POST,
            path = HttpPath.Auth.SignIn,
        ),
        Request(
            method = team.aliens.dms.android.network._legacy.common.HttpMethod.POST,
            path = HttpPath.Auth.SendEmailVerificationCode,
        ),
        Request(
            method = team.aliens.dms.android.network._legacy.common.HttpMethod.GET,
            path = HttpPath.Auth.VerifyEmail,
        ),
        Request(
            method = team.aliens.dms.android.network._legacy.common.HttpMethod.GET,
            path = HttpPath.Auth.CheckIdExists,
        ),
        Request(
            method = team.aliens.dms.android.network._legacy.common.HttpMethod.GET,
            path = HttpPath.Auth.CheckEmailVerificationCode,
        ),

        // Student
        Request(
            method = team.aliens.dms.android.network._legacy.common.HttpMethod.POST,
            path = HttpPath.Student.SignUp,
        ),
        Request(
            method = team.aliens.dms.android.network._legacy.common.HttpMethod.GET,
            path = HttpPath.Student.ExamineStudentNumber,
        ),
        Request(
            method = team.aliens.dms.android.network._legacy.common.HttpMethod.GET,
            path = HttpPath.Student.FindId,
        ),
        Request(
            method = team.aliens.dms.android.network._legacy.common.HttpMethod.PATCH,
            path = HttpPath.Student.ResetPassword,
        ),
        Request(
            method = team.aliens.dms.android.network._legacy.common.HttpMethod.GET,
            path = HttpPath.Student.CheckIdDuplication,
        ),
        Request(
            method = team.aliens.dms.android.network._legacy.common.HttpMethod.GET,
            path = HttpPath.Student.CheckEmailDuplication,
        ),

        // School
        Request(
            method = team.aliens.dms.android.network._legacy.common.HttpMethod.GET,
            path = HttpPath.School.FetchSchools,
        ),
        Request(
            method = team.aliens.dms.android.network._legacy.common.HttpMethod.GET,
            path = HttpPath.School.FetchSchoolVerificationQuestion,
        ),
        Request(
            method = team.aliens.dms.android.network._legacy.common.HttpMethod.GET,
            path = HttpPath.School.ExamineSchoolVerificationQuestion,
        ),
        Request(
            method = team.aliens.dms.android.network._legacy.common.HttpMethod.GET,
            path = HttpPath.School.ExamineSchoolVerificationCode,
        ),

        // File
        Request(
            method = team.aliens.dms.android.network._legacy.common.HttpMethod.POST,
            path = HttpPath.File.UploadFile,
        ),
    )
}
