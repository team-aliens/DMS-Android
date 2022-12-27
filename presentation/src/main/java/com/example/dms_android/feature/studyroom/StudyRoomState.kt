package com.example.dms_android.feature.studyroom

import com.example.dms_android.base.MviState

data class StudyRoomState(
    var a: String
): MviState {
    companion object {
        fun initial() =
            StudyRoomState(
                a = ""
            )
    }
}
