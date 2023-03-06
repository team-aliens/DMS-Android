package team.aliens.dms_android.feature.auth.findid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.exception.*
import team.aliens.domain.param.FindIdParam
import team.aliens.domain.usecase.schools.FetchSchoolsUseCase
import team.aliens.domain.usecase.students.FindIdUseCase
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FindIdViewModel @Inject constructor(
    private val findIdUseCase: FindIdUseCase,
    private val fetchSchoolsUseCase: FetchSchoolsUseCase,
) : ViewModel() {

    init {
        fetchSchools()
    }

    private val _findIdEvent = MutableEventFlow<FindIdEvent>()
    internal val findIdEvent = _findIdEvent.asEventFlow()

    lateinit var email: String

    internal fun findId(schoolId: UUID, name: String, grade: Int, classRoom: Int, number: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                email = findIdUseCase.execute(
                    FindIdParam(
                        schoolId, name, grade, classRoom, number,
                    )
                ).email
            }.onSuccess {
                event(SuccessFindId)
            }.onFailure {
                event(
                    getEventFromThrowable(it),
                )
            }
        }
    }

    private fun fetchSchools() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchSchoolsUseCase.execute(Unit)
            }.onSuccess {
                event(FetchSchools(it))
            }.onFailure {

            }
        }
    }


    fun event(event: FindIdEvent) {
        viewModelScope.launch {
            _findIdEvent.emit(event)
        }
    }
}

private fun getEventFromThrowable(throwable: Throwable): FindIdEvent {
    return when (throwable) {
        is NoInternetException -> FindIdNoInternetException
        is TooManyRequestException -> FindIdTooManyRequest
        is ServerException -> FindIdServerException
        is NeedLoginException -> FindIdNeedLoginException
        is NotFoundException -> FindIdNotFound
        is BadRequestException -> FindIdBadRequest
        is UnauthorizedException -> FindIdUnauthorized
        else -> FindIdUnknownException
    }
}
