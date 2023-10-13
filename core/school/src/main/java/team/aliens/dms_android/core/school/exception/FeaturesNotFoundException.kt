package team.aliens.dms_android.core.school.exception

import team.aliens.dms.android.core.datastore.exception.SearchFailureException

sealed class FeaturesNotFoundException(message: String?) : SearchFailureException(message)
