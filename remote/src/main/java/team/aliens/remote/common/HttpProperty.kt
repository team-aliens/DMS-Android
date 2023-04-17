package team.aliens.remote.common

internal object HttpProperty {

    object Header {

        const val RefreshToken = "refresh-token"

        object ContentType {

            object Application {
                const val Json = "application/json"
                const val OctetStream ="application/octet-stream"
            }

            object Multipart {
                const val FormData = "multipart/form-data"
            }
        }
    }

    object PathVariable {
        const val Date = "date"
        const val SchoolId = "school-id"
        const val NoticeId = "notice-id"
        const val StudyRoomId = "study-room-id"
        const val SeatId = "seat-id"
        const val RemainOptionId = "remain-option-id"
    }

    object QueryString {
        const val Name = "name"
        const val AccountId = "account_id"
        const val SchoolId = "school_id"
        const val StudyRoomId = "study_room_id"
        const val Email = "email"
        const val Password = "password"
        const val AuthCode = "auth_code"
        const val Answer = "answer"
        const val Type = "type"
        const val Grade = "grade"
        const val ClassRoom = "class_room"
        const val Number = "number"
        const val SchoolCode = "school_code"
        const val File = "file"
        const val FileName = "file_name"
        const val Date = "date"
        const val Order = "order"
        const val Page = "page"
        const val Size = "size"
        const val TimeSlot = "time_slot"
    }

    object Body {
        const val File = "file"
    }
}
