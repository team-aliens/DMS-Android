package team.aliens.remote.common

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
        path = this.Auth,
    ) {
        const val SignIn = "/${Domain.Auth}/tokens"
        const val SendEmailVerificationCode = "/${Domain.Auth}/code"
        const val CheckEmailVerificationCode = "/${Domain.Auth}/code"
        const val ReissueToken = "/${Domain.Auth}/reissue"
        const val VerifyEmail = "/${Domain.Auth}/email"
        const val CheckIdExists = "/${Domain.Auth}/account-id"
    }

    object Student : HttpPath(
        path = this.Students,
    ) {
        const val SignUp = "/${Domain.Students}/signup"
        const val ExamineStudentNumber = "/${Domain.Students}/name"
        const val FindId = "/${Domain.Students}/account-id/{school-id}"
        const val ResetPassword = "/${Domain.Students}/password/initialization"
        const val CheckIdDuplication = "/${Domain.Students}/account-id/duplication"
        const val CheckEmailDuplication = "/${Domain.Students}/email/duplication"
        const val FetchMyPage = "/${Domain.Students}/profile"
        const val EditProfile = "/${Domain.Students}/profile"
        const val Withdraw = "/${Domain.Students}"
    }

    object User : HttpPath(
        path = this.Users,
    ) {
        const val EditPassword = "/${Domain.Users}/password"
        const val ComparePassword = "/${Domain.Users}/password"
    }

    object School : HttpPath(
        path = this.Schools,
    ) {
        const val FetchSchools = "/${Domain.Schools}"
        const val FetchSchoolVerificationQuestion = "/${Domain.Schools}/question/{school-id}"
        const val ExamineSchoolVerificationQuestion = "/${Domain.Schools}/answer/{school-id}"
        const val ExamineSchoolVerificationCode = "/${Domain.Schools}/code"
        const val FetchAvailableFeatures = "/${Domain.Schools}/available-features"
    }

    object File : HttpPath(
        path = this.Files,
    ) {
        const val UploadFile = "/${Domain.Files}"
        const val FetchPreSignedUrl = "/${Domain.Files}/url"
    }

    object Meal : HttpPath(
        path = this.Meals,
    ) {
        const val FetchMeals = "/${Domain.Meals}/{date}"
    }

    object Notice : HttpPath(
        path = this.Notices,
    ) {
        const val FetchWhetherNewNoticesExist = "/${Domain.Notices}/status"
        const val FetchNoticeDetails = "/${Domain.Notices}/{notice-id}"
        const val FetchNotices = "/${Domain.Notices}"
    }

    object Point : HttpPath(
        path = this.Points,
    ) {
        const val FetchPoints = "/${Domain.Points}"
    }

    object StudyRoom : HttpPath(
        path = this.StudyRooms,
    ) {

        private const val Seats = "seats"
        private const val Students = "students"

        const val FetchStudyRoomApplicationTime = "/${Domain.StudyRooms}/available-time"
        const val ApplySeat = "/${Domain.StudyRooms}/$Seats/{seat-id}"
        const val CancelSeat = "/${Domain.StudyRooms}/$Seats/{seat-id}"
        const val FetchStudyRooms = "/${Domain.StudyRooms}/list/$Students"
        const val FetchStudyRoomDetails = "/${Domain.StudyRooms}/{study-room-id}/$Students"
        const val FetchCurrentAppliedStudyRoom = "/${Domain.StudyRooms}/my"
        const val FetchSeatTypes = "/${Domain.StudyRooms}/types"
        const val FetchAvailableStudyRoomTimes = "/${Domain.StudyRooms}/time-slots"
    }

    object Remains : HttpPath(
        path = this.Remains,
    ) {
        const val UpdateRemainsOption = "/${Domain.Remains}/{remain-option-id}"
        const val FetchCurrentAppliedRemainsOption = "/${Domain.Remains}/my"
        const val FetchRemainsApplicationTime = "/${Domain.Remains}/available-time"
        const val FetchRemainsOptions = "/${Domain.Remains}/options"
    }

    object Notification : HttpPath(
        path = this.Notification,
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