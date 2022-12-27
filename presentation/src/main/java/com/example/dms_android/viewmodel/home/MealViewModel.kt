package com.example.dms_android.viewmodel.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.register.event.home.MealEvent
import com.example.dms_android.feature.register.state.home.MealList
import com.example.dms_android.feature.register.state.home.MealState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import com.example.dms_android.viewmodel.notice.NoticeViewModel
import com.example.domain.exception.BadRequestException
import com.example.domain.exception.ForbiddenException
import com.example.domain.exception.ServerException
import com.example.domain.exception.TooManyRequestException
import com.example.domain.exception.UnauthorizedException
import com.example.domain.usecase.meal.RemoteMealUseCase
import com.example.local_domain.entity.meal.MealEntity
import com.example.local_domain.usecase.meal.LocalMealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val localMealUseCase: LocalMealUseCase,
    private val remoteMealUseCase: RemoteMealUseCase
) : BaseViewModel<MealState, MealEvent>() {

    private val _mealEvent = MutableEventFlow<Event>()
    val mealEvent = _mealEvent.asEventFlow()

    fun fetchMeal(date: LocalDate) {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteMealUseCase.execute(date)
                localMealUseCase.execute(date.toString())
            }.onSuccess {
                setState(
                    state = state.value.copy(
                        mealList = MealList(
                            breakfast = it.breakfast,
                            lunch = it.lunch,
                            dinner = it.dinner,
                        )
                    )
                )
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(Event.BadRequestException)
                    is UnauthorizedException -> event(Event.UnAuthorizedTokenException)
                    is ForbiddenException -> event(Event.CannotConnectException)
                    is TooManyRequestException -> event(Event.TooManyRequestException)
                    is ServerException -> event(Event.InternalServerException)
                    else -> event(Event.UnknownException)
                }
            }
        }
    }

    fun fetchLocalMeal(date: LocalDate,) {
        viewModelScope.launch {
            kotlin.runCatching {
                val response = localMealUseCase.execute(date.toString())
                state.value.mealList.breakfast = response.breakfast
                state.value.mealList.lunch = response.lunch
                state.value.mealList.dinner = response.dinner
            }
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _mealEvent.emit(event)
        }
    }

    override val initialState: MealState
        get() = MealState.initial()

    override fun reduceEvent(oldState: MealState, event: MealEvent) {
        TODO("Not yet implemented")
    }

    fun updateDay(day: LocalDate) {
        setState(
            state = state.value.copy(
                today = day,
            )
        )
        fetchMeal(day)
    }

    sealed class Event {
        data class FetchMealSuccess(val mealEntity: MealEntity): Event()
        object BadRequestException : Event()
        object UnAuthorizedTokenException : Event()
        object CannotConnectException : Event()
        object TooManyRequestException : Event()
        object InternalServerException : Event()
        object UnknownException : Event()
    }
}