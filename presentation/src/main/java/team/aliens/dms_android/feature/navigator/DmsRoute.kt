package team.aliens.dms_android.feature.navigator

sealed class DmsRoute(
    val route: String,
) {
    object Home : DmsRoute(
        route = "home",
    ) {
        @Deprecated("제거 필요") // fixme 제거
        const val Main = "main"
        const val PointList = "pointList"
        const val UploadImage = "uploadImage"
        const val StudyRoomList = "studyRoomList"
        const val StudyRoomDetail = "studyRoomDetail/{seatId}/{timeSlot}"
        const val RemainApplication = "remainApplication"
        const val NoticeDetail = "noticeDetail/{noticeId}"
        const val MyPageChangePassword = "myPageChangePassword"
    }

    object SignUp : DmsRoute(
        route = "signUp",
    ) {
        const val ExamineSchoolCode = "examineSchoolCode"
        const val SchoolQuestion = "schoolQuestion"
        const val SignUpEmail = "signUpEmail"
        const val SignUpEmailVerify = "signUpEmailVerify"
        const val SignUpId = "signUpId"
        const val SignUpPassword = "signUpPassword"
        const val SignUpProfile = "signUpProfile"
        const val SignUpPolicy = "signUpPolicy"
    }

    object Auth : DmsRoute(
        route = "auth",
    ) {
        const val SignIn = "signIn"
        const val EditPassword = "editPassword"
        const val ComparePassword = "comparePassword"
        const val FindId = "findId"
        const val Identification = "identification"
        const val ResetPassword = "resetPassword"
    }
}
