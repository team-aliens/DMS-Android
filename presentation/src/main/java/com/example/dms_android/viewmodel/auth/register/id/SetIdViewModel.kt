package com.example.dms_android.viewmodel.auth.register.id

import androidx.lifecycle.viewModelScope
import com.example.domain.exception.BadRequestException
import com.example.domain.exception.ConflictException
import com.example.domain.exception.NotFoundException
import com.example.domain.exception.ServerException
import com.example.domain.exception.TooManyRequestException
import com.example.domain.param.ExamineGradeParam
import com.example.domain.usecase.students.DuplicateCheckIdUseCase
import com.example.domain.usecase.students.ExamineGradeUseCase
import com.example.dms_android.R
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.register.event.id.SetIdEvent
import com.example.dms_android.feature.register.state.id.SetIdState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class SetIdViewModel @Inject constructor(
    private val examineGradeUseCase: ExamineGradeUseCase,
    private val duplicateCheckIdUseCase: DuplicateCheckIdUseCase,
) : BaseViewModel<SetIdState, SetIdEvent>() {

    private val _examineGradeEvent = MutableEventFlow<SetIdEvent>()
    val examineGradeEvent = _examineGradeEvent.asEventFlow()

    var name : String = "d"
    var schoolId: UUID = UUID(0, 0)

    fun examineGrade(grade: Int, classRoom: Int, number: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                examineGradeUseCase.execute(
                    ExamineGradeParam(
                        grade = grade,
                        classRoom = classRoom,
                        number = number,
                        schoolId = schoolId
                    )
                )
            }.onSuccess { response ->
                name = response.name
                SetIdEvent.ExamineGradeSuccess
            }.onFailure {
                when (it) {
                    is BadRequestException -> SetIdEvent.ExamineGradeBadRequestException
                    is NotFoundException -> SetIdEvent.ExamineGradeNotFoundException
                    is ConflictException -> SetIdEvent.ExamineGradeConflictException
                    is TooManyRequestException -> SetIdEvent.ExamineGradeTooManyRequestException
                    is ServerException -> SetIdEvent.ExamineGradeInterServerException
                    else -> SetIdEvent.UnknownException
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
                    is BadRequestException -> SetIdEvent.DuplicateIdBadRequestException
                    is ConflictException -> SetIdEvent.DuplicateIdConflictException
                    is TooManyRequestException -> SetIdEvent.DuplicateIdTooManyRequestException
                    is ServerException -> SetIdEvent.DuplicateIdInterServerException
                    else -> SetIdEvent.UnknownException
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