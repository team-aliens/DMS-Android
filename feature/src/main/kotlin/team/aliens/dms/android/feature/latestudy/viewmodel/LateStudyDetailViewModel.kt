package team.aliens.dms.android.feature.latestudy.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.network.exception.NetworkException
import team.aliens.dms.android.data.latestudy.repository.LateStudyRepository
import team.aliens.dms.android.network.latestudy.model.StudyApplicationStatusResponse
import team.aliens.dms.android.shared.exception.UnknownException

@HiltViewModel
class LateStudyDetailViewModel @Inject constructor(
    private val lateStudyRepository: LateStudyRepository,
) : ViewModel() {

    var uiState by mutableStateOf<LateStudyDetailUiState>(LateStudyDetailUiState.Loading)
        private set

    var isCancelling by mutableStateOf(false)
        private set

    init {
        fetchApplication()
    }

    fun cancelApplication(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        if (isCancelling) return

        viewModelScope.launch {
            isCancelling = true
            try {
                lateStudyRepository.cancelMyStudyApplication()
                onSuccess()
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: IOException) {
                handleCancelFailure(exception, onFailure)
            } catch (exception: NetworkException) {
                handleCancelFailure(exception, onFailure)
            } catch (exception: UnknownException) {
                handleCancelFailure(exception, onFailure)
            } finally {
                isCancelling = false
            }
        }
    }

    private fun fetchApplication() {
        viewModelScope.launch {
            try {
                val application = lateStudyRepository.fetchMyStudyApplicationStatus()
                uiState = application?.let(LateStudyDetailUiState::Success) ?: LateStudyDetailUiState.Empty
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: IOException) {
                handleFetchFailure(exception)
            } catch (exception: NetworkException) {
                handleFetchFailure(exception)
            } catch (exception: UnknownException) {
                handleFetchFailure(exception)
            }
        }
    }

    private fun handleCancelFailure(
        throwable: Throwable,
        onFailure: (String) -> Unit,
    ) {
        Log.e(TAG, "새벽 자습 신청 취소에 실패했습니다.", throwable)
        onFailure("새벽 자습 신청 취소에 실패했어요")
    }

    private fun handleFetchFailure(throwable: Throwable) {
        Log.e(TAG, "새벽 자습 신청 내역 조회에 실패했습니다.", throwable)
        uiState = LateStudyDetailUiState.Error
    }

    private companion object {
        const val TAG = "LateStudyDetailViewModel"
    }
}

sealed interface LateStudyDetailUiState {
    data object Loading : LateStudyDetailUiState
    data object Empty : LateStudyDetailUiState
    data object Error : LateStudyDetailUiState
    data class Success(val application: StudyApplicationStatusResponse) : LateStudyDetailUiState
}
