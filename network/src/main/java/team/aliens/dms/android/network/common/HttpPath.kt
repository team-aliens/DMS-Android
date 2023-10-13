package team.aliens.dms.android.network.common

internal sealed class HttpPath(
    val path: String,
) {

    companion object Domain {
        const val Auth = "auth"
        const val Students = "students"
        const val Users = "users"
        const val Schools = "schools"
        const val Files = "files"
        const val Meals = "meals"
        const val Notices = "notices"
        const val Points = "points"
        const val StudyRooms = "study-rooms"
        const val Remains = "remains"
        const val Notification = "notifications"
    }

    object Path {
        const val schoolId = "{school-id}"
        const val noticeId = "{notice-id}"
        const val seatId = "{seat-id}"
        const val studyRoomId = "{study-room-id}"
        const val remainOptionId = "{remain-option-id}"
        const val date = "{date}"
    }

    object Auth : HttpPath(
        path = Domain.Auth,
    ) {
        const val SignIn = "/${Domain.Auth}/tokens"
        const val SendEmailVerificationCode = "/${Domain.Auth}/code"
        const val CheckEmailVerificationCode = "/${Domain.Auth}/code"
        const val ReissueToken = "/${Domain.Auth}/reissue"
        const val VerifyEmail = "/${Domain.Auth}/email"
        const val CheckIdExists = "/${Domain.Auth}/account-id"
    }

    object Student : HttpPath(
        path = Students,
    ) {
        const val SignUp = "/$Students/signup"
        const val ExamineStudentNumber = "/$Students/name"
        const val FindId = "/$Students/account-id/{school-id}"
        const val ResetPassword = "/$Students/password/initialization"
        const val CheckIdDuplication = "/$Students/account-id/duplication"
        const val CheckEmailDuplication = "/$Students/email/duplication"
        const val FetchMyPage = "/$Students/profile"
        const val EditProfile = "/$Students/profile"
        const val Withdraw = "/$Students"
    }

    object User : HttpPath(
        path = Users,
    ) {
        const val EditPassword = "/$Users/password"
        const val ComparePassword = "/$Users/password"
    }

    object School : HttpPath(
        path = Schools,
    ) {
        const val FetchSchools = "/$Schools"
        const val FetchSchoolVerificationQuestion = "/$Schools/question/{school-id}"
        const val ExamineSchoolVerificationQuestion = "/$Schools/answer/{school-id}"
        const val ExamineSchoolVerificationCode = "/$Schools/code"
        const val FetchAvailableFeatures = "/$Schools/available-features"
    }

    object File : HttpPath(
        path = Files,
    ) {
        const val UploadFile = "/$Files"
        const val FetchPreSignedUrl = "/$Files/url"
    }

    object Meal : HttpPath(
        path = Meals,
    ) {
        const val FetchMeals = "/$Meals/{date}"
    }

    object Notice : HttpPath(
        path = Notices,
    ) {
        const val FetchWhetherNewNoticesExist = "/$Notices/status"
        const val FetchNoticeDetails = "/$Notices/{notice-id}"
        const val FetchNotices = "/$Notices"
    }

    object Point : HttpPath(
        path = Points,
    ) {
        const val FetchPoints = "/$Points"
    }

    object StudyRoom : HttpPath(
        path = StudyRooms,
    ) {

        private const val Seats = "seats"
        private const val Students = "students"

        const val FetchStudyRoomApplicationTime = "/$StudyRooms/available-time"
        const val ApplySeat = "/$StudyRooms/$Seats/{seat-id}"
        const val CancelSeat = "/$StudyRooms/$Seats/{seat-id}"
        const val FetchStudyRooms = "/$StudyRooms/list/$Students"
        const val FetchStudyRoomDetails = "/$StudyRooms/{study-room-id}/$Students"
        const val FetchCurrentAppliedStudyRoom = "/$StudyRooms/my"
        const val FetchSeatTypes = "/$StudyRooms/types"
        const val FetchAvailableStudyRoomTimes = "/$StudyRooms/time-slots"
    }

    object Remains : HttpPath(
        path = Domain.Remains,
    ) {
        const val UpdateRemainsOption = "/${Domain.Remains}/{remain-option-id}"
        const val FetchCurrentAppliedRemainsOption = "/${Domain.Remains}/my"
        const val FetchRemainsApplicationTime = "/${Domain.Remains}/available-time"
        const val FetchRemainsOptions = "/${Domain.Remains}/options"
    }

    object Notification : HttpPath(
        path = Domain.Notification,
    ) {
        const val RegisterDeviceToken = "/${Domain.Notification}/token"
        const val CancelDeviceTokenRegistration = "/${Domain.Notification}/token"
        const val SubscribeTopic = "/${Domain.Notification}/topic"
        const val UnsubscribeTopic = "/${Domain.Notification}/topic"
        const val BatchUpdateTopic = "/${Domain.Notification}/topic"
        const val FetchNotificationTopicsStatus = "/${Domain.Notification}/topic"
        const val FetchNotifications = "/${Domain.Notification}"
    }
}