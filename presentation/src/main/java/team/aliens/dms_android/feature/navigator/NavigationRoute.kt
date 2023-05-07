package team.aliens.dms_android.feature.navigator

sealed class NavigationRoute(
    val route: String,
) {
    object Main : NavigationRoute("Main") {
        const val Main = "main"
    }

    object SignUp : NavigationRoute("SignUp") {
        const val VerifySchool = "verifySchool"
        const val SchoolQuestion = "schoolQuestion"
        const val SignUpEmail = "signUpEmail"
        const val SignUpEmailVerify = "signUpEmailVerify"
        const val SignUpId = "signUpId"
        const val SignUpPassword = "signUpPassword"
        const val SignUpProfile = "signUpProfile"
        const val SignUpPolicy = "signUpPolicy"
    }

    object Auth : NavigationRoute("Auth") {
        const val Login = "login"
        const val ChangePassword = "changePassword"
        const val ComparePassword = "comparePassword"
        const val FindId = "findId"
        const val Identification = "identification"
        const val ChangePasswordVerifyEmail = "changePasswordVerifyEmail"
        const val MyPageChangePassword = "myPageChangePassword"
    }

    object MyPage : NavigationRoute("MyPage") {
        const val PointList = "pointList"
        const val ConfirmImage = "confirmImage"
    }

    object Application : NavigationRoute("Application") {
        const val StudyRoom = "studyRoom"
        const val StudyRoomDetail = "studyRoomDetail/{seatId}/{timeSlot}"
        const val RemainApplication = "remainApplication"
    }

    object Notice : NavigationRoute("Notice") {
        const val NoticeDetail = "noticeDetail/{noticeId}"
    }
}
