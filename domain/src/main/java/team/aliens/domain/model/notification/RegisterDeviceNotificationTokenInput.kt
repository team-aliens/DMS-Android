package team.aliens.domain.model.notification

/**
 * An input used when registering device notification token
 * @property token device's token
 */
data class RegisterDeviceNotificationTokenInput(
    val token: String,
)
