package team.aliens.dms_android.feature.feature.auth.findid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.util.MutableEventFlow
import team.aliens.dms_android.feature.util.asEventFlow
import team.aliens.domain.model.student.FindIdInput
import team.aliens.domain.usecase.school.FetchSchoolsUseCase
import team.aliens.domain.usecase.student.FindIdUseCase
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
        else -> FindIdUnknownException
    }
}