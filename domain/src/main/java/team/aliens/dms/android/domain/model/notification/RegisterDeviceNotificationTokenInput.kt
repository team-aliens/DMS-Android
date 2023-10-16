package team.aliens.dms.android.domain.model.notification

/**
 * An input used when registering device notification token
 * @property deviceToken device's token
 */
data class RegisterDeviceNotificationTokenInput(
    val deviceToken: String,
)