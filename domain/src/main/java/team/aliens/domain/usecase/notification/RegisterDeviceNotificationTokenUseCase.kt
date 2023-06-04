package team.aliens.domain.usecase.notification

import team.aliens.domain.model.notification.RegisterDeviceNotificationTokenInput
import team.aliens.domain.repository.NotificationRepository
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
