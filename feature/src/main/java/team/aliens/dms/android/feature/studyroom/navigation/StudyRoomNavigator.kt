package team.aliens.dms.android.feature.studyroom.navigation

import java.util.UUID

interface StudyRoomNavigator {

    // TODO: fix to custom navigation argument type
    fun openStudyRoomDetails(
        studyRoomId: UUID,
        studyRoomName: String,
        timeslot: UUID,
        studyRoomApplicationStartTime: String,
        studyRoomApplicationEndTime: String,
    )

    fun navigateUp()
}
