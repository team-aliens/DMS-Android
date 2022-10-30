package com.example.dms_android.viewmodel.auth.register.school

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth_domain.exception.BadRequestException
import com.example.auth_domain.exception.NotFoundException
import com.example.auth_domain.exception.ServerException
import com.example.auth_domain.exception.TooManyRequestException
import com.example.auth_domain.usecase.schools.RemoteSchoolQuestionUseCase
import com.example.dms_android.feature.register.event.school.SchoolQuestionEvent
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SchoolQuestionViewModel @Inject constructor(
    private val schoolQuestionUseCase: RemoteSchoolQuestionUseCase
) : ViewModel() {

    private lateinit var question: String

    private val _schoolQuestionEvent = MutableEventFlow<SchoolQuestionEvent>()
    val schoolQuestionEvent = _schoolQuestionEvent.asEventFlow()

    fun schoolQuestion(schoolId: UUID) {
        viewModelScope.launch {
            kotlin.runCatching {
                question = schoolQuestionUseCase.execute(schoolId).question
            }.onSuccess {
                SchoolQuestionEvent.SchoolQuestionSuccess(question)
            }.onFailure {
                when (it) {
                    is BadRequestException -> SchoolQuestionEvent.BadRequestException
                    is NotFoundException -> SchoolQuestionEvent.NotFoundException
                    is TooManyRequestException -> SchoolQuestionEvent.TooManyRequestException
                    is ServerException -> SchoolQuestionEvent.InternalServerException
                    else -> SchoolQuestionEvent.UnknownException
                }
            }
        }
    }
}