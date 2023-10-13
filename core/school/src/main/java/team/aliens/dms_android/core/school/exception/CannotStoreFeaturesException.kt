package team.aliens.dms_android.core.school.exception

import team.aliens.dms.android.core.datastore.exception.TransformFailureException

sealed class CannotStoreFeaturesException(message: String?) : TransformFailureException(message)

// class CannotStore
