package team.aliens.dms_android._feature

sealed class DmsRoute(
    val route: String,
) {
    object Home : DmsRoute(
        route = "home",
    ) {
        @Deprecated("제거 필요") // fixme 제거
        const val Main = "main"
        const val PointHistory = "pointHistory"
        const val UploadProfileImage = "uploadProfileImage"
        const val StudyRooms = "studyRooms"
        const val StudyRoomDetails = "studyRoomDetails/{seatId}/{timeSlot}"
        const val RemainsApplication = "remainsApplication"
        const val NoticeDetails = "noticeDetails/{noticeId}"
        const val MyPageChangePassword = "myPageChangePassword"
    }

    object SignUp : DmsRoute(
        route = "signUp",
    ) {
        const val VerifySchool = "verifySchool"
        const val SchoolVerificationQuestion = "schoolVerificationQuestion"
        const val SendVerificationEmail = "sendVerificationEmail"
        const val CheckEmailVerificationCode = "checkEmailVerificationCode"
        const val SetId = "setId"
        const val SetPassword = "setPassword"
        const val SetProfileImage = "setProfileImage"
        const val Policy = "policy"
    }

    object Auth : DmsRoute(
        route = "auth",
    ) {
        const val SignIn = "signIn"
        const val EditPassword = "editPassword"
        const val ComparePassword = "comparePassword"
        const val ResetPassword = "resetPassword"
        const val FindId = "findId"
        const val UserVerification = "userVerification"
    }
}
