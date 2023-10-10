package team.aliens.dms_android.domain.usecase.notification

import team.aliens.dms_android.domain.model.notification.RegisterDeviceNotificationTokenInput
import team.aliens.dms_android.domain.repository.NotificationRepository
import javax.inject.Inject

class RegisterDeviceNotificationTokenUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke(
        registerDeviceNotificationTokenInput: RegisterDeviceNotificationTokenInput,
    ) {
        notificationRepository.registerDeviceNotificationToken(
            input = registerDeviceNotificationTokenInput,
        )
    }
}
