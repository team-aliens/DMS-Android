package team.aliens.dms_android.feature._legacy.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.R
import team.aliens.dms_android.feature._legacy.util.MutableEventFlow
import team.aliens.dms_android.feature._legacy.util.asEventFlow
import javax.inject.Inject

@Deprecated("legacy")
abstract class BaseViewModel2<S : UiState, E : UiEvent> : ViewModel() {

    @Inject
    lateinit var application: Application

    // represents view's ui state
    protected abstract val _uiState: MutableStateFlow<S>
    val uiState: StateFlow<S>
        get() = _uiState.asStateFlow()

    protected fun setState(
        reducer: S.() -> S,
    ) {
        val newState = _uiState.value.reducer()
        _uiState.value = newState
    }

    open fun onEvent(
        event: E,
    ) {
        /* explicit blank */
    }

    // represents error state
    @Deprecated(level = DeprecationLevel.WARNING, message = "deprecated by refactoring")
    private val _errorState = MutableEventFlow<String>()

    @Deprecated(level = DeprecationLevel.WARNING, message = "deprecated by refactoring")
    val errorState = _errorState.asEventFlow()

    @Deprecated(level = DeprecationLevel.WARNING, message = "deprecated by refactoring")
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

    @Deprecated(level = DeprecationLevel.WARNING, message = "deprecated by refactoring")
    protected fun emitErrorEventFromThrowable(
        throwable: Throwable?,
    ) {
        emitErrorEvent(
            errorEvent = getErrorEventFromThrowable(
                throwable = throwable,
            ),
        )
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "deprecated by refactoring")
    protected fun getErrorEventFromThrowable(
        throwable: Throwable?,
    ): ErrorEvent {
        return when (throwable) {
            else -> ErrorEvent.Unknown
        }
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "deprecated by refactoring")
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
