package team.aliens.dms.android.feature.studyroom.navigation

import java.util.UUID

interface StudyRoomNavigator {
    fun openStudyRoomDetails(
        studyRoomId: UUID,
        timeslot: UUID,
    )

    fun navigateUp()
}
