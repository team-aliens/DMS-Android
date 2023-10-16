package team.aliens.dms.android.network._legacy.common

internal sealed class DormHttpPath(
    val path: String,
) {

    companion object {
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

    object Auth : team.aliens.dms.android.network._legacy.common.DormHttpPath(
        path = team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Auth,
    ) {
        const val SignIn = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Auth}/tokens"
        const val SendEmailVerificationCode = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Auth}/code"
        const val CheckEmailVerificationCode = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Auth}/code"
        const val ReissueToken = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Auth}/reissue"
        const val VerifyEmail = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Auth}/email"
        const val CheckIdExists = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Auth}/account-id"
    }

    object Student : team.aliens.dms.android.network._legacy.common.DormHttpPath(
        path = "students",
    ) {
        const val SignUp = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Students}/signup"
        const val CheckStudentNumber = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Students}/name"
        const val FindId = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Students}/account-id/{school-id}"
        const val ResetPassword = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Students}/password/initialization"
        const val CheckIdDuplication = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Students}/account-id/duplication"
        const val CheckEmailDuplication = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Students}/email/duplication"
        const val FetchMyPage = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Students}/profile"
        const val EditProfile = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Students}/profile"
        const val Withdraw = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Students}"
    }

    object Users : team.aliens.dms.android.network._legacy.common.DormHttpPath(
        path = "users",
    ) {
        const val EditPassword = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Users}/password"
        const val ComparePassword = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Users}/password"
    }

    object Schools : team.aliens.dms.android.network._legacy.common.DormHttpPath(
        path = "schools",
    ) {
        const val FetchSchools = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Schools}"
        const val FetchSchoolVerificationQuestion = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Schools}/question/{school-id}"
        const val ExamineSchoolVerificationQuestion = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Schools}/answer/{school-id}"
        const val ExamineSchoolVerificationCode = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Schools}/code"
        const val FetchAvailableFeatures = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Schools}/available-features"
    }

    object Files : team.aliens.dms.android.network._legacy.common.DormHttpPath(
        path = "files",
    ) {
        const val UploadFile = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Files}"
    }

    object Meals : team.aliens.dms.android.network._legacy.common.DormHttpPath(
        path = "meals",
    ) {
        const val FetchMeals = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Meals}/{date}"
    }

    object Notices : team.aliens.dms.android.network._legacy.common.DormHttpPath(
        path = "notices",
    ) {
        const val FetchWhetherNewNoticeExists = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Notices}/status"
        const val FetchNoticeDetails = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Notices}/{notice-id}"
        const val FetchNotices = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Notices}"
    }

    object Points : team.aliens.dms.android.network._legacy.common.DormHttpPath(
        path = "points",
    ) {
        const val FetchPoints = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Points}"
    }

    object StudyRooms : team.aliens.dms.android.network._legacy.common.DormHttpPath(
        path = "study-rooms",
    ) {

        private const val Seats = "seats"
        private const val Students = "students"

        const val FetchStudyRoomApplicationTime = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.StudyRooms}/available-time"
        const val ApplySeat = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.StudyRooms}/${team.aliens.dms.android.network._legacy.common.DormHttpPath.StudyRooms.Seats}/{seat-id}"
        const val CancelSeat = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.StudyRooms}/${team.aliens.dms.android.network._legacy.common.DormHttpPath.StudyRooms.Seats}"
        const val FetchStudyRooms = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.StudyRooms}/list/${team.aliens.dms.android.network._legacy.common.DormHttpPath.StudyRooms.Students}"
        const val FetchStudyRoomDetails = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.StudyRooms}/{study-room-id}/${team.aliens.dms.android.network._legacy.common.DormHttpPath.StudyRooms.Students}"
        const val FetchCurrentAppliedStudyRoom = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.StudyRooms}/my"
        const val FetchSeatTypes = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.StudyRooms}/types"
        const val FetchAvailableStudyRoomTimes = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.StudyRooms}/time-slots"
    }

    object Remains : team.aliens.dms.android.network._legacy.common.DormHttpPath(
        path = "remains",
    ) {
        const val UpdateRemainsOption = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Remains}/{remain-option-id}"
        const val FetchCurrentAppliedRemainsOption = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Remains}/my"
        const val FetchRemainsApplicationTime = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Remains}/available-time"
        const val FetchRemainsOptions = "/${team.aliens.dms.android.network._legacy.common.DormHttpPath.Companion.Remains}/options"
    }
}