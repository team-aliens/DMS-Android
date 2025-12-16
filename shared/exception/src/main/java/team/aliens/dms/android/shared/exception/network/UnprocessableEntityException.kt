package team.aliens.dms.android.shared.exception.network

class UnprocessableEntityException : NetworkException(
    code = 422,
    message = "Unprocessable Entity",
)
