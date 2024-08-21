package team.aliens.dms.android.data.student.mapper

import team.aliens.dms.android.data.student.model.MyPage
import team.aliens.dms.android.network.student.model.FetchMyPageResponse
import team.aliens.dms.android.shared.model.Sex

internal fun FetchMyPageResponse.toModel(): MyPage = MyPage(
    schoolName = this.schoolName,
    studentName = this.studentName,
    gradeClassNumber = this.gradeClassNumber,
    profileImageUrl = this.profileImageUrl,
    sex = Sex.valueOf(this.sex),
    bonusPoint = this.bonusPoint,
    minusPoint = this.minusPoint,
    phrase = this.phrase,
)
