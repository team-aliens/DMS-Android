package com.example.dms_android.viewmodel.home

import androidx.lifecycle.viewModelScope
import com.example.auth_domain.exception.BadRequestException
import com.example.auth_domain.exception.ServerException
import com.example.auth_domain.exception.TooManyRequestException
import com.example.auth_domain.exception.UnauthorizedException
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.register.event.home.MealEvent
import com.example.dms_android.feature.register.state.home.MealState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import com.example.feature_domain.exception.ForbiddenException
import com.example.feature_domain.usecase.meal.RemoteMealUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class MealViewModel @Inject constructor(
    private val mealUseCase: RemoteMealUseCase,
) : BaseViewModel<MealState, MealEvent>() {

    private val _mealEvent = MutableEventFlow<MealEvent>()
    val mealEvent = _mealEvent.asEventFlow()

    private val dateTime = LocalDate.now()

    fun fetchMeal() {
        viewModelScope.launch {
            kotlin.runCatching {
                mealUseCase.execute(dateTime)
            }.onSuccess {
                MealEvent.MealSuccess
            }.onFailure {
                when (it) {
                    is BadRequestException -> MealEvent.BadRequestException
                    is UnauthorizedException -> MealEvent.UnAuthorizedException
                    is ForbiddenException -> MealEvent.ForbiddenException
                    is TooManyRequestException -> MealEvent.TooManyRequestException
                    is ServerException -> MealEvent.InternalServerException
                    else -> MealEvent.UnknownException
                }
            }
        }
    }

    private fun event(event: MealEvent) {
        viewModelScope.launch {
            _mealEvent.emit(event)
        }
    }

    override val initialState: MealState
        get() = MealState.initial()

    override fun reduceEvent(oldState: MealState, event: MealEvent) {
        TODO("Not yet implemented")
    }
}