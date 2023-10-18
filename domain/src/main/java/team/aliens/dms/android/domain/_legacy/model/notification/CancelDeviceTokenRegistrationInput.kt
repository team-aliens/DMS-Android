package team.aliens.dms.android.domain._legacy.model.notification

import java.util.UUID

data class CancelDeviceTokenRegistrationInput(
    val deviceId: UUID,
)
