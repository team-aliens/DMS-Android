package team.aliens.dms_android.feature._feature.auth.findid

import team.aliens.domain.model.school.FetchSchoolsOutput


sealed interface FindIdEvent

class FetchSchools(val fetchSchoolsOutput: FetchSchoolsOutput) : FindIdEvent

object SuccessFindId : FindIdEvent

object FindIdBadRequest : FindIdEvent

object FindIdUnauthorized : FindIdEvent

object FindIdNotFound : FindIdEvent

object FindIdTooManyRequest : FindIdEvent

object FindIdServerException : FindIdEvent

object FindIdNoInternetException : FindIdEvent

object FindIdNeedLoginException : FindIdEvent

object FindIdUnknownException : FindIdEvent