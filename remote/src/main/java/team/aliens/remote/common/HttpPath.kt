package team.aliens.remote.common

private const val uuidRegex = "(\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12})"
private const val dateRegex = "(\\d{4})-(\\d{2})-(\\d{2})"

internal sealed class HttpPath(
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

    object Auth : HttpPath(
        path = this.Auth,
    ) {
        const val SignIn = "/${Companion.Auth}/tokens"
        const val SendEmailVerificationCode = "/${Companion.Auth}/code"
        const val CheckEmailVerificationCode = "/${Companion.Auth}/code"
        const val ReissueToken = "/${Companion.Auth}/reissue"
        const val VerifyEmail = "/${Companion.Auth}/email"
        const val CheckIdExists = "/${Companion.Auth}/account-id"
    }

    object Student : HttpPath(
        path = this.Students,
    ) {
        const val SignUp = "/${Companion.Students}/signup"
        const val ExamineStudentNumber = "/${Companion.Students}/name"
        const val FindId = "/${Companion.Students}/account-id/$uuidRegex"
        const val ResetPassword = "/${Companion.Students}/password/initialization"
        const val CheckIdDuplication = "/${Companion.Students}/account-id/duplication"
        const val CheckEmailDuplication = "/${Companion.Students}/email/duplication"
        const val FetchMyPage = "/${Companion.Students}/profile"
        const val EditProfile = "/${Companion.Students}/profile"
        const val Withdraw = "/${Companion.Students}"
    }

    object User : HttpPath(
        path = this.Users,
    ) {
        const val EditPassword = "/${Companion.Users}/password"
        const val ComparePassword = "/${Companion.Users}/password"
    }

    object School : HttpPath(
        path = this.Schools,
    ) {
        const val FetchSchools = "/${Companion.Schools}"
        const val FetchSchoolVerificationQuestion = "/${Companion.Schools}/question/$uuidRegex"
        const val ExamineSchoolVerificationQuestion = "/${Companion.Schools}/answer/$uuidRegex"
        const val ExamineSchoolVerificationCode = "/${Companion.Schools}/code"
        const val FetchAvailableFeatures = "/${Companion.Schools}/available-features"
    }

    object File : HttpPath(
        path = this.Files,
    ) {
        const val UploadFile = "/${Companion.Files}"
        const val FetchPreSignedUrl = "/${Companion.Files}/url"
    }

    object Meal : HttpPath(
        path = this.Meals,
    ) {
        const val FetchMeals = "/${Companion.Meals}/$dateRegex"
    }

    object Notice : HttpPath(
        path = this.Notices,
    ) {
        const val FetchWhetherNewNoticesExist = "/${Companion.Notices}/status"
        const val FetchNoticeDetails = "/${Companion.Notices}/$uuidRegex"
        const val FetchNotices = "/${Companion.Notices}"
    }

    object Point : HttpPath(
        path = this.Points,
    ) {
        const val FetchPoints = "/${Companion.Points}"
    }

    object StudyRoom : HttpPath(
        path = this.StudyRooms,
    ) {

        private const val Seats = "seats"
        private const val Students = "students"

        const val FetchStudyRoomApplicationTime = "/${Companion.StudyRooms}/available-time"
        const val ApplySeat = "/${Companion.StudyRooms}/$Seats/$uuidRegex"
        const val CancelSeat = "/${Companion.StudyRooms}/$Seats/$uuidRegex"
        const val FetchStudyRooms = "/${Companion.StudyRooms}/list/$Students"
        const val FetchStudyRoomDetails = "/${Companion.StudyRooms}/$uuidRegex/$Students"
        const val FetchCurrentAppliedStudyRoom = "/${Companion.StudyRooms}/my"
        const val FetchSeatTypes = "/${Companion.StudyRooms}/types"
        const val FetchAvailableStudyRoomTimes = "/${Companion.StudyRooms}/time-slots"
    }

    object Remains : HttpPath(
        path = this.Remains,
    ) {
        const val UpdateRemainsOption = "/${Companion.Remains}/$uuidRegex"
        const val FetchCurrentAppliedRemainsOption = "/${Companion.Remains}/my"
        const val FetchRemainsApplicationTime = "/${Companion.Remains}/available-time"
        const val FetchRemainsOptions = "/${Companion.Remains}/options"
    }
}