package team.aliens.domain.entity.studyroom

data class SeatTypeEntity(
    val types: List<Type>,
) {

    data class Type(
        val color: String,
        val id: String,
        val name: String,
    )
}