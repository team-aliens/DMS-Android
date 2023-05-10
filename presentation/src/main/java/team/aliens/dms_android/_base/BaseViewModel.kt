package team.aliens.dms_android._base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.exception.*
import team.aliens.presentation.R
import javax.inject.Inject

abstract class BaseViewModel<S : UiState, E : Event> : ViewModel() {

    @Inject
    lateinit var application: Application

    // represents view's ui state
    protected abstract val _uiState: MutableStateFlow<S>
    val uiState: StateFlow<S>
        get() = _uiState.asStateFlow()

    abstract fun onEvent(
        event: E,
    )

    // represents error state
    private val _errorState = MutableEventFlow<String>()
    val errorState = _errorState.asEventFlow()

    protected fun emitErrorEvent(
        errorEvent: ErrorEvent,
    ) {
        viewModelScope.launch {
            _errorState.emit(
                getStringFromErrorEvent(
                    errorEvent = errorEvent,
                ),
            )
        }
    }

    protected fun emitErrorEventFromThrowable(
        throwable: Throwable?,
    ) {
        emitErrorEvent(
            errorEvent = getErrorEventFromThrowable(
                throwable = throwable,
            ),
        )
    }

    protected fun getErrorEventFromThrowable(
        throwable: Throwable?,
    ): ErrorEvent {
        return when (throwable) {
            is BadRequestException -> ErrorEvent.BadRequest
            is NullPointerException -> ErrorEvent.NullPointer
            is UnauthorizedException -> ErrorEvent.Unauthorized
            is NoInternetException -> ErrorEvent.NoInternet
            is TooManyRequestException -> ErrorEvent.TooManyRequests
            is TimeoutException -> ErrorEvent.TimeOut
            is ServerException -> ErrorEvent.InternalServerError
            else -> ErrorEvent.Unknown
        }
    }

    private fun getStringFromErrorEvent(
        errorEvent: ErrorEvent,
    ): String {
        return application.getString(
            when (errorEvent) {
                ErrorEvent.BadRequest -> R.string.BadRequest
                ErrorEvent.NoInternet -> R.string.NoInternetException
                ErrorEvent.InternalServerError -> R.string.ServerException
                ErrorEvent.NullPointer -> R.string.NotFound
                ErrorEvent.TooManyRequests -> R.string.TooManyRequest
                ErrorEvent.Unauthorized -> R.string.UnAuthorized
                ErrorEvent.Unknown -> R.string.UnKnownException
                ErrorEvent.TimeOut -> R.string.error_timeout
            },
        )
    }
}
