package team.aliens.dms_android._base

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.presentation.R

@HiltViewModel
abstract class BaseViewModel<S : BaseUiState, E : BaseEvent>(
    application: Application,
) : AndroidViewModel(application) {

    protected val context: Context
        get() = getApplication()

    // represents view's ui state
    protected abstract val _uiState: MutableState<S>

    val uiState: State<S>
        get() = _uiState

    protected abstract fun onEvent(
        event: E,
    )

    // represents error state
    private val _errorState = MutableEventFlow<String>()
    internal val errorState = _errorState.asEventFlow()

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

    private fun getStringFromErrorEvent(
        errorEvent: ErrorEvent,
    ): String {
        return context.getString(
            when (errorEvent) {
                ErrorEvent.BadRequest -> R.string.BadRequest
                ErrorEvent.NoInternet -> R.string.NoInternetException
                ErrorEvent.InternalServerError -> R.string.ServerException
                ErrorEvent.NullPointer -> R.string.NotFound
                ErrorEvent.TooManyRequests -> R.string.TooManyRequest
                ErrorEvent.Unauthorized -> R.string.UnAuthorized
                ErrorEvent.Unknown -> R.string.UnKnownException
            },
        )
    }
}
