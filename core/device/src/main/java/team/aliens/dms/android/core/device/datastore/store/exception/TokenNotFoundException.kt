package team.aliens.dms.android.core.device.datastore.store.exception

import team.aliens.dms.android.core.datastore.exception.LoadFailureException

sealed class TokenNotFoundException(message: String?) : LoadFailureException(message)

class DeviceTokenNotFoundException : TokenNotFoundException("Device token not found")
