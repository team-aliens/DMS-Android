package team.aliens.dms.android.core.notification

import com.google.firebase.messaging.FirebaseMessaging
import team.aliens.dms.android.data.notification.repository.NotificationRepository
import javax.inject.Inject

class DeviceTokenManager @Inject constructor(
    //디바이스 토큰 로컬 저장 provider
) {
    fun getDeviceToken(): String? {
        //디바이스 토큰 리턴
        return null
    }

    fun fetchDeviceToken(deviceToken: String) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                //Handle error
            }
            // 디바이스 토큰 등록
        }
    }
}
