package com.example.dms_android.viewmodel.auth.register.school

import androidx.lifecycle.viewModelScope
import com.example.auth_domain.exception.BadRequestException
import com.example.auth_domain.exception.ServerException
import com.example.auth_domain.exception.TooManyRequestException
import com.example.auth_domain.exception.UnauthorizedException
import com.example.auth_domain.usecase.schools.RemoteSchoolCodeUseCase
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.register.event.school.ExamineSchoolCodeEvent
import com.example.dms_android.feature.register.state.school.ExamineSchoolCodeState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExamineSchoolCodeViewModel @Inject constructor(
    private val remoteSchoolCodeUseCase: RemoteSchoolCodeUseCase
) : BaseViewModel<ExamineSchoolCodeState, ExamineSchoolCodeEvent>() {

    fun setSchoolCode(schoolCode: String){
        sendEvent(ExamineSchoolCodeEvent.InputSchoolCode(schoolCode))
    }

    private val _examineSchoolCodeEvent = MutableEventFlow<ExamineSchoolCodeEvent>()
    val examineSchoolCodeEvent = _examineSchoolCodeEvent.asEventFlow()

    fun examineSchoolCode(schoolCode: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteSchoolCodeUseCase.execute(schoolCode)
            }.onSuccess {
                ExamineSchoolCodeEvent.ExamineSchoolCodeSuccess
            }.onFailure {
                when (it) {
                    is BadRequestException -> ExamineSchoolCodeEvent.BadRequestException
                    is UnauthorizedException -> ExamineSchoolCodeEvent.UnAuthorizedException
                    is TooManyRequestException -> ExamineSchoolCodeEvent.TooManyRequestException
                    is ServerException -> ExamineSchoolCodeEvent.InternalServerException
                    else -> ExamineSchoolCodeEvent.UnknownException
                }
            }
        }
    }

    override val initialState: ExamineSchoolCodeState
        get() = ExamineSchoolCodeState.initial()

    override fun reduceEvent(oldState: ExamineSchoolCodeState, event: ExamineSchoolCodeEvent) {
        when (event) {
            is ExamineSchoolCodeEvent.InputSchoolCode -> {
                setState(oldState.copy(schoolCode = event.schoolCode))
            }
            else -> {

            }
        }
    }
}