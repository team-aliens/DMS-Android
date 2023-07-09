package team.aliens.domain.model.notification

import java.util.UUID

data class CancelDeviceTokenRegistrationInput(
    val deviceId: UUID,
)
