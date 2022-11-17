package com.example.dms_android.viewmodel.auth.register.school

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.auth_domain.exception.BadRequestException
import com.example.auth_domain.exception.NotFoundException
import com.example.auth_domain.exception.ServerException
import com.example.auth_domain.exception.TooManyRequestException
import com.example.auth_domain.exception.UnauthorizedException
import com.example.auth_domain.param.SchoolAnswerParam
import com.example.auth_domain.usecase.schools.RemoteSchoolAnswerUseCase
import com.example.auth_domain.usecase.schools.RemoteSchoolQuestionUseCase
import com.example.dms_android.R
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.register.event.school.ConfirmSchoolEvent
import com.example.dms_android.feature.register.state.school.ConfirmSchoolState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ConfirmSchoolViewModel @Inject constructor(
    private val remoteSchoolAnswerUseCase: RemoteSchoolAnswerUseCase,
    private val remoteSchoolQuestionUseCase: RemoteSchoolQuestionUseCase,
) : BaseViewModel<ConfirmSchoolState, ConfirmSchoolEvent>() {

    //TODO: sharedFlow로 바꿔야됨
    var schoolAnswer: MutableLiveData<String> = MutableLiveData()
    var inputSchoolId: MutableLiveData<UUID> = MutableLiveData()

    private val _question: MutableLiveData<String> = MutableLiveData()
    val question: LiveData<String> = _question

    private val parameter =
        inputSchoolId.value?.let {
            SchoolAnswerParam(
                schoolId = it,
                answer = schoolAnswer.value.toString(),
            )
        }

    private val _confirmSchoolEvent = MutableEventFlow<ConfirmSchoolEvent>()
    val confirmSchoolEvent = _confirmSchoolEvent.asEventFlow()

    fun compareSchoolAnswer() {
        viewModelScope.launch {
            kotlin.runCatching {
                if (parameter != null) {
                    remoteSchoolAnswerUseCase.execute(parameter)
                }
            }.onSuccess {
                ConfirmSchoolEvent.CompareSchoolAnswerSuccess
            }.onFailure {
                when (it) {
                    is BadRequestException -> ConfirmSchoolEvent.CompareSchoolBadRequest
                    is UnauthorizedException -> ConfirmSchoolEvent.CompareSchoolUnauthorized
                    is NotFoundException -> ConfirmSchoolEvent.CompareSchoolNotFound
                    is TooManyRequestException -> ConfirmSchoolEvent.ErrorMessage(R.string.TooManyRequest.toString())
                    is ServerException -> ConfirmSchoolEvent.ErrorMessage(R.string.ServerException.toString())
                    else -> ConfirmSchoolEvent.ErrorMessage(R.string.UnKnownException.toString())
                }
            }
        }
    }


    fun schoolQuestion() {
        viewModelScope.launch {
            kotlin.runCatching {
                inputSchoolId.value?.let { remoteSchoolQuestionUseCase.execute(it) }
            }.onSuccess { response ->
                ConfirmSchoolEvent.SchoolQuestionSuccess
                _question.value = response?.question
            }.onFailure {
                when (it) {
                    is BadRequestException -> ConfirmSchoolEvent.ErrorMessage(R.string.LoginBadRequest.toString())
                    is NotFoundException -> ConfirmSchoolEvent.ErrorMessage(R.string.CompareSchoolNotFound.toString())
                    is TooManyRequestException -> ConfirmSchoolEvent.ErrorMessage(R.string.TooManyRequest.toString())
                    is ServerException -> ConfirmSchoolEvent.ErrorMessage(R.string.ServerException.toString())
                    else -> ConfirmSchoolEvent.ErrorMessage(R.string.UnKnownException.toString())
                }
            }
        }
    }

    override val initialState: ConfirmSchoolState
        get() = ConfirmSchoolState.initial()

    override fun reduceEvent(oldState: ConfirmSchoolState, event: ConfirmSchoolEvent) {
        TODO("Not yet implemented")
    }
}