package team.aliens.remote.common

internal sealed class DormHttpPath(
    val path: String,
) {

    private companion object {
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
    }

    object Auth : DormHttpPath(
        path = this.Auth,
    ) {
        const val SignIn = "/${Companion.Auth}/tokens"
        const val SendEmailVerificationCode = "/${Companion.Auth}/code"
        const val CheckEmailVerificationCode = "/${Companion.Auth}/code"
        const val ReissueToken = "/${Companion.Auth}/reissue"
        const val VerifyEmail = "/${Companion.Auth}/email"
        const val CheckIdExists = "/${Companion.Auth}/account-id"
    }

    object Student : DormHttpPath(
        path = this.Students,
    ) {
        const val SignUp = "/${Companion.Students}/signup"
        const val ExamineStudentNumber = "/${Companion.Students}/name"
        const val FindId = "/${Companion.Students}/account-id/{school-id}"
        const val ResetPassword = "/${Companion.Students}/password/initialization"
        const val CheckIdDuplication = "/${Companion.Students}/account-id/duplication"
        const val CheckEmailDuplication = "/${Companion.Students}/email/duplication"
        const val FetchMyPage = "/${Companion.Students}/profile"
        const val EditProfile = "/${Companion.Students}/profile"
        const val Withdraw = "/${Companion.Students}"
    }

    object Users : DormHttpPath(
        path = this.Users,
    ) {
        const val EditPassword = "/${Companion.Users}/password"
        const val ComparePassword = "/${Companion.Users}/password"
    }

    object Schools : DormHttpPath(
        path = this.Schools,
    ) {
        const val FetchSchools = "/${Companion.Schools}"
        const val FetchSchoolVerificationQuestion = "/${Companion.Schools}/question/{school-id}"
        const val ExamineSchoolVerificationQuestion = "/${Companion.Schools}/answer/{school-id}"
        const val ExamineSchoolVerificationCode = "/${Companion.Schools}/code"
        const val FetchAvailableFeatures = "/${Companion.Schools}/available-features"
    }

    object Files : DormHttpPath(
        path = this.Files,
    ) {
        const val UploadFile = "/${Companion.Files}"
        const val FetchPreSignedUrl = "/${Companion.Files}/url"
    }

    object Meals : DormHttpPath(
        path = this.Meals,
    ) {
        const val FetchMeals = "/${Companion.Meals}/{date}"
    }

    object Notices : DormHttpPath(
        path = this.Notices,
    ) {
        const val FetchWhetherNewNoticesExist = "/${Companion.Notices}/status"
        const val FetchNoticeDetails = "/${Companion.Notices}/{notice-id}"
        const val FetchNotices = "/${Companion.Notices}"
    }

    object Points : DormHttpPath(
        path = this.Points,
    ) {
        const val FetchPoints = "/${Companion.Points}"
    }

    object StudyRooms : DormHttpPath(
        path = this.StudyRooms,
    ) {

        private const val Seats = "seats"
        private const val Students = "students"

        const val FetchStudyRoomApplicationTime = "/${Companion.StudyRooms}/available-time"
        const val ApplySeat = "/${Companion.StudyRooms}/$Seats/{seat-id}"
        const val CancelSeat = "/${Companion.StudyRooms}/$Seats"
        const val FetchStudyRooms = "/${Companion.StudyRooms}/list/$Students"
        const val FetchStudyRoomDetails = "/${Companion.StudyRooms}/{study-room-id}/$Students"
        const val FetchCurrentAppliedStudyRoom = "/${Companion.StudyRooms}/my"
        const val FetchSeatTypes = "/${Companion.StudyRooms}/types"
        const val FetchAvailableStudyRoomTimes = "/${Companion.StudyRooms}/time-slots"
    }

    object Remains : DormHttpPath(
        path = this.Remains,
    ) {
        const val UpdateRemainsOption = "/${Companion.Remains}/{remain-option-id}"
        const val FetchCurrentAppliedRemainsOption = "/${Companion.Remains}/my"
        const val FetchRemainsApplicationTime = "/${Companion.Remains}/available-time"
        const val FetchRemainsOptions = "/${Companion.Remains}/options"
    }
}