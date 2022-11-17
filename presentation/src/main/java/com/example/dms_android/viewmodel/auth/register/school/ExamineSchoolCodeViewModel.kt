package com.example.dms_android.viewmodel.auth.register.school

import androidx.lifecycle.MutableLiveData
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ExamineSchoolCodeViewModel @Inject constructor(
    private val remoteSchoolCodeUseCase: RemoteSchoolCodeUseCase,
) : BaseViewModel<ExamineSchoolCodeState, ExamineSchoolCodeEvent>() {

    private val _examineSchoolCodeEvent = MutableEventFlow<ExamineSchoolCodeEvent>()
    val examineSchoolCodeEvent = _examineSchoolCodeEvent.asEventFlow()

    //TODO: sharedFlow로 바꿔야됨
    var schoolId : MutableLiveData<UUID> = MutableLiveData()

    fun examineSchoolCode(schoolCode: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteSchoolCodeUseCase.execute(schoolCode)

            }.onSuccess { response ->
                schoolId.value = response.schoolId
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
        TODO()
    }
}