package com.example.dms_android.viewmodel.auth.register.id

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.auth_domain.exception.BadRequestException
import com.example.auth_domain.exception.ConflictException
import com.example.auth_domain.exception.NotFoundException
import com.example.auth_domain.exception.ServerException
import com.example.auth_domain.exception.TooManyRequestException
import com.example.auth_domain.param.ExamineGradeParam
import com.example.auth_domain.usecase.students.DuplicateCheckIdUseCase
import com.example.auth_domain.usecase.students.ExamineGradeUseCase
import com.example.dms_android.R
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.register.event.id.SetIdEvent
import com.example.dms_android.feature.register.state.id.SetIdState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class SetIdViewModel @Inject constructor(
    private val examineGradeUseCase: ExamineGradeUseCase,
    private val duplicateCheckIdUseCase: DuplicateCheckIdUseCase,
) : BaseViewModel<SetIdState, SetIdEvent>() {

    private val _examineGradeEvent = MutableEventFlow<SetIdEvent>()
    val examineGradeEvent = _examineGradeEvent.asEventFlow()

    //TODO : 추후에 Flow로 바꿀 예정
    private val _name: MutableLiveData<String> = MutableLiveData()
    val name: LiveData<String> = _name

    val grade: MutableLiveData<Int> = MutableLiveData()
    val schoolId: MutableLiveData<UUID> = MutableLiveData()
    val classRoom: MutableLiveData<Int> = MutableLiveData()
    val number: MutableLiveData<Int> = MutableLiveData()

    private val parameter = ExamineGradeParam(
        grade = grade.value!!.toInt(),
        schoolId = schoolId.value!!,
        classRoom = classRoom.value!!.toInt(),
        number = number.value!!.toInt(),
    )

    fun examineGrade() {
        viewModelScope.launch {
            kotlin.runCatching {
                examineGradeUseCase.execute(parameter)
            }.onSuccess { response ->
                _name.value = response.name
                SetIdEvent.ExamineGradeSuccess
            }.onFailure {
                when (it) {
                    is BadRequestException -> SetIdEvent.ExamineGradeBadRequest
                    is NotFoundException -> SetIdEvent.ExamineGradeNotFound
                    is ConflictException -> SetIdEvent.ExamineConflict
                    is TooManyRequestException -> SetIdEvent.ErrorMessage(R.string.TooManyRequest.toString())
                    is ServerException -> SetIdEvent.ErrorMessage(R.string.ServerException.toString())
                    else -> SetIdEvent.ErrorMessage(R.string.UnKnownException.toString())
                }
            }
        }
    }

    fun duplicateId(id: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                duplicateCheckIdUseCase.execute(id)
            }.onSuccess {
                SetIdEvent.DuplicateIdSuccess
            }.onFailure {
                when (it) {
                    is BadRequestException -> SetIdEvent.ErrorMessage(R.string.LoginBadRequest.toString())
                    is ConflictException -> SetIdEvent.DuplicateIdConflict
                    is TooManyRequestException -> SetIdEvent.ErrorMessage(R.string.TooManyRequest.toString())
                    is ServerException -> SetIdEvent.ErrorMessage(R.string.ServerException.toString())
                    else -> SetIdEvent.ErrorMessage(R.string.UnKnownException.toString())
                }
            }
        }
    }

    override val initialState: SetIdState
        get() = SetIdState.initial()

    override fun reduceEvent(oldState: SetIdState, event: SetIdEvent) {
        TODO("Not yet implemented")
    }
}