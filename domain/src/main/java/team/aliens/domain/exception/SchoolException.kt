package team.aliens.domain.exception

sealed class SchoolException(
    message: String,
    val code: Int,
) : RuntimeException(message) {

    object QuestionAnswerMismatch : SchoolException(
        message = "Question and Answer mismatch",
        code = 401,
    )

    object SchoolCodeMismatch : SchoolException(
        message = "School code mismatch",
        code = 401,
    )

    object SchoolNotFound : SchoolException(
        message = "School not found",
        code = 404,
    )
}
