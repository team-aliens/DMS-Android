package com.example.dms_android.viewmodel.auth.register.school

import androidx.lifecycle.viewModelScope
import com.example.auth_domain.exception.BadRequestException
import com.example.auth_domain.exception.NotFoundException
import com.example.auth_domain.exception.ServerException
import com.example.auth_domain.exception.TooManyRequestException
import com.example.auth_domain.exception.UnauthorizedException
import com.example.auth_domain.param.SchoolAnswerParam
import com.example.auth_domain.usecase.schools.RemoteSchoolAnswerUseCase
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.register.event.school.CompareSchoolAnswerEvent
import com.example.dms_android.feature.register.state.school.CompareSchoolAnswerState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CompareSchoolAnswerViewModel @Inject constructor(
    private val remoteSchoolAnswerUseCase: RemoteSchoolAnswerUseCase
) : BaseViewModel<CompareSchoolAnswerState, CompareSchoolAnswerEvent>() {

    private var schoolId : UUID = TODO("schoolID 전달 받기")

    fun setSchoolAnswer(schoolAnswer: String) {
        sendEvent(CompareSchoolAnswerEvent.InputSchoolAnswer(schoolAnswer))
    }

    private val parameter =
        SchoolAnswerParam(
            schoolId = schoolId,
            answer = state.value.schoolAnswer,
        )

    private val _compareSchoolAnswerEvent = MutableEventFlow<CompareSchoolAnswerEvent>()
    val compareSchoolAnswerEvent = _compareSchoolAnswerEvent.asEventFlow()

    fun compareSchoolAnswer() {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteSchoolAnswerUseCase.execute(parameter)
            }.onSuccess {
                CompareSchoolAnswerEvent.CompareSchoolAnswerSuccess
            }.onFailure {
                when (it) {
                    is BadRequestException -> CompareSchoolAnswerEvent.BadRequestException
                    is UnauthorizedException -> CompareSchoolAnswerEvent.UnAuthorizedException
                    is NotFoundException -> CompareSchoolAnswerEvent.NotFoundException
                    is TooManyRequestException -> CompareSchoolAnswerEvent.TooManyRequestException
                    is ServerException -> CompareSchoolAnswerEvent.InternalServerException
                    else -> CompareSchoolAnswerEvent.UnKnownException
                }
            }
        }
    }


    override val initialState: CompareSchoolAnswerState
        get() = CompareSchoolAnswerState.initial()

    override fun reduceEvent(oldState: CompareSchoolAnswerState, event: CompareSchoolAnswerEvent) {
        when(event){
            is CompareSchoolAnswerEvent.InputSchoolAnswer -> {
                setState(oldState.copy(schoolAnswer = event.schoolAnswer))
            }
            else -> {
                TODO("else")
            }
        }
    }
}