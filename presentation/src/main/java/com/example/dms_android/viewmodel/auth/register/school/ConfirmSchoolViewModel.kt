package com.example.dms_android.viewmodel.auth.register.school

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.schools.RemoteSchoolAnswerUseCase
import com.example.domain.usecase.schools.RemoteSchoolQuestionUseCase
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.register.event.school.ConfirmSchoolEvent
import com.example.dms_android.feature.register.state.school.ConfirmSchoolState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import com.example.domain.entity.user.SchoolConfirmQuestionEntity
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

@HiltViewModel
class ConfirmSchoolViewModel @Inject constructor(
    private val remoteSchoolAnswerUseCase: RemoteSchoolAnswerUseCase,
    private val remoteSchoolQuestionUseCase: RemoteSchoolQuestionUseCase,
) : BaseViewModel<ConfirmSchoolState, ConfirmSchoolEvent>() {

    private val _confirmSchoolEvent = MutableEventFlow<ConfirmSchoolEvent>()
    val confirmSchoolEvent = _confirmSchoolEvent.asEventFlow()

    var schoolId: UUID = UUID(0, 0)

    fun compareSchoolAnswer(answer: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteSchoolAnswerUseCase.execute(
                    SchoolAnswerParam(
                        schoolId = schoolId,
                        answer = answer
                    )
                )
            }.onSuccess {
                event(ConfirmSchoolEvent.CompareSchoolAnswerSuccess)
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(ConfirmSchoolEvent.CompareSchoolBadRequestException)
                    is UnauthorizedException -> event(ConfirmSchoolEvent.CompareSchoolUnauthorizedException)
                    is NotFoundException -> event(ConfirmSchoolEvent.CompareSchoolNotFoundException)
                    is TooManyRequestException -> event(ConfirmSchoolEvent.CompareSchoolTooManyRequestException)
                    is ServerException -> event(ConfirmSchoolEvent.CompareSchoolInternalServerException)
                    else -> event(ConfirmSchoolEvent.UnknownException)
                }
            }
        }
    }


    fun schoolQuestion(schoolId: UUID) {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteSchoolQuestionUseCase.execute(schoolId).collect {
                    event(ConfirmSchoolEvent.FetchSchoolQuestion(it.toData()))
                }
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(ConfirmSchoolEvent.SchoolQuestionBadRequestException)
                    is NotFoundException -> event(ConfirmSchoolEvent.SchoolQuestionNotFoundException)
                    is TooManyRequestException -> event(ConfirmSchoolEvent.SchoolQuestionTooManyRequestException)
                    is ServerException -> event(ConfirmSchoolEvent.SchoolQuestionInternalServerException)
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

    private fun SchoolConfirmQuestionEntity.toData() =
        SchoolConfirmQuestionEntity(
            question = question
        )

    override val initialState: ConfirmSchoolState
        get() = ConfirmSchoolState.initial()

    override fun reduceEvent(oldState: ConfirmSchoolState, event: ConfirmSchoolEvent) {
        TODO()
    }
}