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
import team.aliens.dms.android.core.network.exception.ConflictException
import team.aliens.dms.android.core.network.exception.NetworkException
import team.aliens.dms.android.data.latestudy.model.StudyType
import team.aliens.dms.android.data.latestudy.repository.LateStudyRepository
import team.aliens.dms.android.network.latestudy.model.SubmitLateStudyRequest
import team.aliens.dms.android.network.latestudy.model.TeacherResponse
import team.aliens.dms.android.shared.exception.UnknownException

@HiltViewModel
class LateStudyViewModel @Inject constructor(
    private val lateStudyRepository: LateStudyRepository,
) : ViewModel() {

    var studyTypes by mutableStateOf<List<StudyType>>(emptyList())
        private set

    var selectedTypeId by mutableStateOf<String?>(null)
        private set

    var teachers by mutableStateOf<List<TeacherResponse>>(emptyList())
        private set

    var isSubmitting by mutableStateOf(false)
        private set

    init {
        fetchStudyTypes()
        fetchTeachers()
    }

    private fun fetchStudyTypes() {
        viewModelScope.launch {
            try {
                studyTypes = lateStudyRepository.fetchStudyTypes()
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchTeachers() {
        viewModelScope.launch {
            try {
                teachers = lateStudyRepository.fetchTeachers()
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun selectStudyType(typeId: String) {
        selectedTypeId = typeId
    }

    fun submitLateStudy(
        teacherId: String,
        typeId: String,
        reason: String,
        startDate: String,
        endDate: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        if (isSubmitting) return

        viewModelScope.launch {
            isSubmitting = true

            try {
                lateStudyRepository.submitLateStudy(
                    SubmitLateStudyRequest(
                        teacherId = teacherId,
                        typeId = typeId,
                        reason = reason,
                        startDate = startDate,
                        endDate = endDate,
                    ),
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: ConflictException) {
                Log.w(TAG, "이미 새벽 자습을 신청했습니다.", e)
                onFailure("이미 새벽 자습을 신청했습니다.")
                return@launch
            } catch (e: IOException) {
                handleSubmitFailure(e, onFailure)
                return@launch
            } catch (e: NetworkException) {
                handleSubmitFailure(e, onFailure)
                return@launch
            } catch (e: UnknownException) {
                handleSubmitFailure(e, onFailure)
                return@launch
            } finally {
                isSubmitting = false
            }

            onSuccess()
        }
    }

    private fun handleSubmitFailure(
        throwable: Throwable,
        onFailure: (String) -> Unit,
    ) {
        Log.e(TAG, "새벽 자습 신청에 실패했습니다.", throwable)
        onFailure("새벽 자습 신청에 실패했습니다.")
    }

    private companion object {
        const val TAG = "LateStudyViewModel"
    }
}
