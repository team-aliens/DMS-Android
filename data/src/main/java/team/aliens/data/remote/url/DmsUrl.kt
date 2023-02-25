package team.aliens.data.remote.url

object DmsUrl {

    const val meal = "/meals/{date}"
    const val notices = "/notices"
    const val students = "/students"
    const val auth = "/auth"
    const val schools = "/schools"
    const val studyRoom = "/study-rooms"
    const val remains = "/remains"
    const val uploadFile = "/files"

    object User {
        const val login = "$auth/tokens"
        const val emailCode = "$auth/code"
        const val reissueToken = "$auth/reissue"
        const val compareEmail = "$auth/email"
        const val checkId = "$auth/account-id"
    }

    object Students {
        const val register = "$students/signup"
        const val duplicateCheckId = "$students/account-id/duplication"
        const val duplicateCheckEmail = "$students/email/duplication"
        const val resetPassword = "$students/password/initialization"
        const val examineGrade = "$students/name"
    }

    object Schools {
        const val schoolQuestion = "$schools/question/{school-id}"
        const val schoolAnswer = "$schools/answer/{school-id}"
        const val schoolCode = "$schools/code"
    }

    object Notice {
        const val NewNoticeBoolean = "$notices/status"
        const val NoticeDetail = "$notices/{notice-id}"
    }

    object MyPage {
        const val MyPage = "${students}/profile"
        const val Point = "/points"
    }

    object StudyRoom {
        const val Apply = "$studyRoom/seats/{seat-id}"
        const val fetchApplyTime = "$studyRoom/available-time"
        const val CancelApply = "$studyRoom/seats"
        const val StudyRoomList = "$studyRoom/list/students"
        const val StudyRoomType = "$studyRoom/types"
        const val StudyRoomDetail = "$studyRoom/{study-room-id}/students"
    }

    object Remain {
        const val updateRemainOption = "$remains/{remain-option-id}"
        const val fetchCurrentRemainOption = "$remains/my"
        const val fetchAvailableRemainTime = "$remains/available-time"
        const val fetchRemainOptions = "$remains/options"
    }
}
