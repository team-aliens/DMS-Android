package com.example.dms_android.viewmodel.auth.register.school

import androidx.lifecycle.viewModelScope
import com.example.auth_domain.usecase.schools.RemoteSchoolCodeUseCase
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.register.event.school.ExamineSchoolCodeEvent
import com.example.dms_android.feature.register.state.school.ExamineSchoolCodeState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import com.example.domain.exception.BadRequestException
import com.example.domain.exception.ServerException
import com.example.domain.exception.TooManyRequestException
import com.example.domain.exception.UnauthorizedException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ExamineSchoolCodeViewModel @Inject constructor(
    private val remoteSchoolCodeUseCase: RemoteSchoolCodeUseCase,
) : BaseViewModel<ExamineSchoolCodeState, ExamineSchoolCodeEvent>() {

    private val _examineSchoolCodeEvent = MutableEventFlow<ExamineSchoolCodeEvent>()
    val examineSchoolCodeEvent = _examineSchoolCodeEvent.asEventFlow()

    var schoolId: UUID = UUID(0, 0)

    fun examineSchoolCode(schoolCode: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                schoolId = remoteSchoolCodeUseCase.execute(schoolCode).schoolId
                remoteSchoolCodeUseCase.execute(schoolCode)
            }.onSuccess {
                event(ExamineSchoolCodeEvent.ExamineSchoolCodeSuccess)
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(ExamineSchoolCodeEvent.BadRequestException)
                    is UnauthorizedException -> event(ExamineSchoolCodeEvent.UnAuthorizedException)
                    is TooManyRequestException -> event(ExamineSchoolCodeEvent.TooManyRequestException)
                    is ServerException -> event(ExamineSchoolCodeEvent.InternalServerException)
                    else -> event(ExamineSchoolCodeEvent.UnknownException)
                }
            }
        }
    }

    private fun event(event: ExamineSchoolCodeEvent) {
        viewModelScope.launch {
            _examineSchoolCodeEvent.emit(event)
        }
    }

    override val initialState: ExamineSchoolCodeState
        get() = ExamineSchoolCodeState.initial()

    override fun reduceEvent(oldState: ExamineSchoolCodeState, event: ExamineSchoolCodeEvent) {
        TODO()
    }
}