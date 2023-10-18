package team.aliens.dms.android.domain._legacy.usecase.notification

import team.aliens.dms.android.domain.model.notification.RegisterDeviceNotificationTokenInput
import team.aliens.dms.android.domain.repository.NotificationRepository
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
