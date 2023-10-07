package team.aliens.dms_android.network.http

import team.aliens.network.common.HttpMethod
import team.aliens.network.common.HttpPath

object IgnoreRequestWrapper {
    val ignoreRequests: ArrayList<Request> = arrayListOf(

        // Auth
        Request(
            method = HttpMethod.POST,
            path = HttpPath.Auth.SignIn,
        ),
        Request(
            method = HttpMethod.POST,
            path = HttpPath.Auth.SendEmailVerificationCode,
        ),
        Request(
            method = HttpMethod.GET,
            path = HttpPath.Auth.VerifyEmail,
        ),
        Request(
            method = HttpMethod.GET,
            path = HttpPath.Auth.CheckIdExists,
        ),
        Request(
            method = HttpMethod.GET,
            path = HttpPath.Auth.CheckEmailVerificationCode,
        ),

        // Student
        Request(
            method = HttpMethod.POST,
            path = HttpPath.Student.SignUp,
        ),
        Request(
            method = HttpMethod.GET,
            path = HttpPath.Student.ExamineStudentNumber,
        ),
        Request(
            method = HttpMethod.GET,
            path = HttpPath.Student.FindId,
        ),
        Request(
            method = HttpMethod.PATCH,
            path = HttpPath.Student.ResetPassword,
        ),
        Request(
            method = HttpMethod.GET,
            path = HttpPath.Student.CheckIdDuplication,
        ),
        Request(
            method = HttpMethod.GET,
            path = HttpPath.Student.CheckEmailDuplication,
        ),

        // School
        Request(
            method = HttpMethod.GET,
            path = HttpPath.School.FetchSchools,
        ),
        Request(
            method = HttpMethod.GET,
            path = HttpPath.School.FetchSchoolVerificationQuestion,
        ),
        Request(
            method = HttpMethod.GET,
            path = HttpPath.School.ExamineSchoolVerificationQuestion,
        ),
        Request(
            method = HttpMethod.GET,
            path = HttpPath.School.ExamineSchoolVerificationCode,
        ),

        // File
        Request(
            method = HttpMethod.POST,
            path = HttpPath.File.UploadFile,
        ),
    )
}
