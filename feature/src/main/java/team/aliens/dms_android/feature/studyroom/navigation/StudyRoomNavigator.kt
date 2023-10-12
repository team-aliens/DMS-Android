package team.aliens.dms_android.feature.studyroom.navigation

import java.util.UUID

interface StudyRoomNavigator {
    fun openStudyRoomDetails(studyRoomId: UUID, timeslot: UUID)

    fun popBackStack()
}
