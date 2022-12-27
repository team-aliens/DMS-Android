package com.example.dms_android.feature.studyroom

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import com.example.dms_android.viewmodel.home.MealViewModel
import com.example.domain.exception.BadRequestException
import com.example.domain.exception.ForbiddenException
import com.example.domain.exception.ServerException
import com.example.domain.exception.TooManyRequestException
import com.example.domain.exception.UnauthorizedException
import com.example.local_domain.entity.meal.MealEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StudyRoomViewModel @Inject constructor(
    // val studyRoomUseCase: StudyRoomUseCase
): BaseViewModel<StudyRoomState, StudyRoomEvent>() {

    private val _studyRoomEffect = MutableEventFlow<Event>()
    val studyRoomEffect = _studyRoomEffect.asEventFlow()

    override val initialState: StudyRoomState
        get() = StudyRoomState.initial()

    fun fetchStudyRoomList() {
        viewModelScope.launch {
            kotlin.runCatching {
                // studyRoomUseCase.execute()
            }.onSuccess {
                event(Event.FetchStudyRoomList)
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(Event.BadRequestException)
                    is UnauthorizedException -> event(Event.UnAuthorizedTokenException)
                    is ForbiddenException -> event(Event.CannotConnectException)
                    is TooManyRequestException -> event(Event.TooManyRequestException)
                    is NullPointerException -> event(Event.NullPointException)
                    is ServerException -> event(Event.InternalServerException)
                    else -> event(Event.UnknownException)
                }
            }
        }
    }

    private fun event(event:Event) {
        viewModelScope.launch {
            _studyRoomEffect.emit(event)
        }
    }

    sealed class Event {
        object FetchStudyRoomList: Event()
        object BadRequestException : Event()
        object UnAuthorizedTokenException : Event()
        object CannotConnectException : Event()
        object TooManyRequestException : Event()
        object NullPointException: Event()
        object InternalServerException : Event()
        object UnknownException : Event()
    }

    override fun reduceEvent(oldState: StudyRoomState, event: StudyRoomEvent) {
        TODO("Not yet implemented")
    }
}