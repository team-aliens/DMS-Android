package team.aliens.dms.android.domain._legacy.model.student

import team.aliens.dms.android.domain.model._common.Sex
import team.aliens.dms.android.domain.model.mypage.MyPage

/**
 * A response of fetching my page
 * @property schoolName the student's school name
 * @property studentName the student's name
 * @property gradeClassNumber the student's grade, class and number
 * @property profileImageUrl the student's profile image url
 * @property sex the student's sex
 * @property bonusPoint the student's total bonus point
 * @property minusPoint the student's total minus point
 * @property phrase the phrase of student's school
 */
data class FetchMyPageOutput(
    val schoolName: String,
    val studentName: String,
    val gradeClassNumber: String,
    val profileImageUrl: String,
    val sex: Sex,
    val bonusPoint: Int,
    val minusPoint: Int,
    val phrase: String,
)

fun FetchMyPageOutput.toModel(): MyPage {
    return MyPage(
        schoolName = this.schoolName,
        name = this.studentName,
        gradeClassNumber = this.gradeClassNumber,
        profileImageUrl = this.profileImageUrl,
        sex = this.sex,
        bonusPoint = this.bonusPoint,
        minusPoint = this.minusPoint,
        phrase = this.phrase,
    )
}
