package team.aliens.dms_android.feature.navigator

sealed class NavigationRoute(
    val route: String,
) {
    object Home : NavigationRoute("home") {
        const val Main = "main"
        const val PointList = "pointList"
        const val UploadImage = "uploadImage"
        const val StudyRoomList = "studyRoomList"
        const val StudyRoomDetail = "studyRoomDetail/{seatId}/{timeSlot}"
        const val RemainApplication = "remainApplication"
        const val NoticeDetail = "noticeDetail/{noticeId}"
    }

    object SignUp : NavigationRoute("signUp") {
        const val ExamineSchoolCode = "examineSchoolCode"
        const val SchoolQuestion = "schoolQuestion"
        const val SignUpEmail = "signUpEmail"
        const val SignUpEmailVerify = "signUpEmailVerify"
        const val SignUpId = "signUpId"
        const val SignUpPassword = "signUpPassword"
        const val SignUpProfile = "signUpProfile"
        const val SignUpPolicy = "signUpPolicy"
    }

    object Auth : NavigationRoute("auth") {
        const val SignIn = "signIn"
        const val ChangePassword = "changePassword"
        const val ComparePassword = "comparePassword"
        const val FindId = "findId"
        const val Identification = "identification"
        const val ChangePasswordVerifyEmail = "changePasswordVerifyEmail"
        const val MyPageChangePassword = "myPageChangePassword"
    }
}
