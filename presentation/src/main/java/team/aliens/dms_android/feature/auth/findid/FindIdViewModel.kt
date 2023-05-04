package team.aliens.dms_android.feature.auth.findid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain._model.student.FindIdInput
import team.aliens.domain.exception.BadRequestException
import team.aliens.domain.exception.NeedLoginException
import team.aliens.domain.exception.NoInternetException
import team.aliens.domain.exception.NotFoundException
import team.aliens.domain.exception.ServerException
import team.aliens.domain.exception.TooManyRequestException
import team.aliens.domain.exception.UnauthorizedException
import team.aliens.domain.usecase.school.FetchSchoolsUseCase
import team.aliens.domain.usecase.student.FindIdUseCase
import java.util.UUID
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
                email = findIdUseCase(
                    FindIdInput(
                        schoolId = schoolId,
                        studentName = name,
                        grade = grade,
                        classRoom = classRoom,
                        number = number,
                    ),
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
                fetchSchoolsUseCase()
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
