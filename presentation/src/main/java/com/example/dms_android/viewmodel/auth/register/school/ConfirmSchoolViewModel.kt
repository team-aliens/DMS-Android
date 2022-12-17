package com.example.dms_android.viewmodel.auth.register.school

import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.schools.RemoteSchoolAnswerUseCase
import com.example.domain.usecase.schools.RemoteSchoolQuestionUseCase
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.register.event.school.ConfirmSchoolEvent
import com.example.dms_android.feature.register.state.school.ConfirmSchoolState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import com.example.domain.exception.BadRequestException
import com.example.domain.exception.NotFoundException
import com.example.domain.exception.ServerException
import com.example.domain.exception.TooManyRequestException
import com.example.domain.exception.UnauthorizedException
import com.example.domain.param.SchoolAnswerParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ConfirmSchoolViewModel @Inject constructor(
    private val remoteSchoolAnswerUseCase: RemoteSchoolAnswerUseCase,
    private val remoteSchoolQuestionUseCase: RemoteSchoolQuestionUseCase,
) : BaseViewModel<ConfirmSchoolState, ConfirmSchoolEvent>() {

    var schoolId : UUID = UUID(0,0)
    var schoolAnswer : String = ""
    var question : String = ""

    private val parameter =
        SchoolAnswerParam(
            schoolId = schoolId,
            answer = schoolAnswer,
        )

    private val _confirmSchoolEvent = MutableEventFlow<ConfirmSchoolEvent>()
    val confirmSchoolEvent = _confirmSchoolEvent.asEventFlow()

    fun compareSchoolAnswer() {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteSchoolAnswerUseCase.execute(parameter)
            }.onSuccess {
                event(ConfirmSchoolEvent.CompareSchoolAnswerSuccess)
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(ConfirmSchoolEvent.CompareSchoolBadRequest)
                    is UnauthorizedException -> event(ConfirmSchoolEvent.CompareSchoolUnauthorized)
                    is NotFoundException -> event(ConfirmSchoolEvent.CompareSchoolNotFound)
                    is TooManyRequestException -> event(ConfirmSchoolEvent.TooManyRequestException)
                    is ServerException -> event(ConfirmSchoolEvent.InternalServerException)
                    else -> event(ConfirmSchoolEvent.UnknownException)
                }
            }
        }
    }


    fun schoolQuestion() {
        viewModelScope.launch {
            kotlin.runCatching {
                question = remoteSchoolQuestionUseCase.execute(schoolId).question
                remoteSchoolQuestionUseCase.execute(schoolId)
            }.onSuccess {
                event(ConfirmSchoolEvent.SchoolQuestionSuccess)
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(ConfirmSchoolEvent.SchoolQuestionBadRequest)
                    is NotFoundException -> event(ConfirmSchoolEvent.SchoolQuestionNotFound)
                    is TooManyRequestException -> event(ConfirmSchoolEvent.TooManyRequestException)
                    is ServerException -> event(ConfirmSchoolEvent.InternalServerException)
                    else -> event(ConfirmSchoolEvent.UnknownException)
                }
            }
        }
    }

    private fun event(event: ConfirmSchoolEvent) {
        viewModelScope.launch {
            _confirmSchoolEvent.emit(event)
        }
    }

    override val initialState: ConfirmSchoolState
        get() = ConfirmSchoolState.initial()

    override fun reduceEvent(oldState: ConfirmSchoolState, event: ConfirmSchoolEvent) {
        TODO("Not yet implemented")
    }
}