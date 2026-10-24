package team.aliens.dms.android.feature.latestudy.viewmodel

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
                onFailure("새벽 자습 신청 취소에 실패했어요")
            } catch (exception: NetworkException) {
                onFailure("새벽 자습 신청 취소에 실패했어요")
            } catch (exception: Exception) {
                onFailure("새벽 자습 신청 취소에 실패했어요")
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
                uiState = LateStudyDetailUiState.Error
            } catch (exception: NetworkException) {
                uiState = LateStudyDetailUiState.Error
            } catch (exception: Exception) {
                uiState = LateStudyDetailUiState.Error
            }
        }
    }
}

sealed interface LateStudyDetailUiState {
    data object Loading : LateStudyDetailUiState
    data object Empty : LateStudyDetailUiState
    data object Error : LateStudyDetailUiState
    data class Success(val application: StudyApplicationStatusResponse) : LateStudyDetailUiState
}
