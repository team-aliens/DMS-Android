package team.aliens.remote.api.path

internal sealed class DormHttpPath(
    val path: String,
) {

    object Auth : DormHttpPath(
        path = "auth",
    ) {
        val SignIn = "/${this.path}/tokens"
        val SendEmailVerificationCode = "/${this.path}/code"
        val CheckEmailVerificationCode = "/${this.path}code"
        val ReissueToken = "/${this.path}/reissue"
        val VerifyEmail = "/${this.path}/email"
        val CheckIdExists = "/${this.path}/account-id"
    }

    object Student : DormHttpPath(
        path = "students",
    ) {
        val SignUp = "/${this.path}/signup"
        val CheckStudentNumber = "/${this.path}/name"
        val FindId = "/${this.path}/account-id/{school-id}"
        val ResetPassword = "/${this.path}/password/initialization"
        val CheckIdDuplication = "/${this.path}/account-id/duplication"
        val CheckEmailDuplication = "/${this.path}/email/duplication"
        val FetchMyPage = "/${this.path}/profile"
        val EditProfile = "/${this.path}/profile"
        val Withdraw = "/${this.path}"
    }

    object Users : DormHttpPath(
        path = "users",
    ) {
        val EditPassword = "/${this.path}/password"
        val ComparePassword = "/${this.path}/password"
    }

    object Schools : DormHttpPath(
        path = "schools",
    ) {
        val FetchSchools = "/${this.path}/"
        val FetchSchoolVerificationQuestion = "/${this.path}/question/{school-id}"
        val ExamineSchoolVerificationQuestion = "/${this.path}/answer/{school-id}"
        val ExamineSchoolVerificationCode = "/${this.path}/code"
        val FetchAvailableFeatures = "/${this.path}/available-features"
    }

    object Files : DormHttpPath(
        path = "files",
    ) {
        val UploadFile = "/${this.path}"
    }

    object Meals : DormHttpPath(
        path = "meals",
    ) {
        val FetchMeals = "/${this.path}/{date}"
    }

    object Notices : DormHttpPath(
        path = "notices",
    ) {
        val FetchWhetherNewNoticeExists = "/${this.path}/status"
        val FetchNoticeDetails = "/${this.path}/{notice-id}"
        val FetchNotices = "/${this.path}"
    }

    object Points : DormHttpPath(
        path = "points",
    ) {
        val FetchPoints = "/${this.path}"
    }

    object StudyRooms : DormHttpPath(
        path = "study-rooms",
    ) {

        private const val Seats = "seats"
        private const val Students = "students"

        val FetchStudyRoomApplicationTime = "/${this.path}/available-time"
        val ApplySeat = "/${this.path}/$Seats/{seat-id}"
        val CancelSeat = "/${this.path}/$Seats"
        val FetchStudyRooms = "/${this.path}/list/$Students"
        val FetchStudyRoomDetails = "/${this.path}/{study-room-id}/$Students"
        val FetchCurrentAppliedStudyRoom = "/${this.path}/my"
        val FetchSeatTypes = "/${this.path}/types"
        val FetchAvailableStudyRoomTimes = "/${this.path}/time-slots"
    }

    object Remains : DormHttpPath(
        path = "remains",
    ) {
        val UpdateRemainsOption = "/${this.path}/{remain-option-id}"
        val FetchCurrentAppliedRemainsOption = "/${this.path}/my"
        val FetchRemainsApplicationTime = "/${this.path}/available-time"
        val FetchRemainsOptions = "/${this.path}/options"
    }
}