package team.aliens.dms.android.core.school.exception

import team.aliens.dms.android.core.datastore.exception.LoadFailureException

sealed class FeaturesNotFoundException(message: String?) : LoadFailureException(message)
