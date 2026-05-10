package team.aliens.dms.android.feature.latestudy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.launch
import team.aliens.dms.android.data.latestudy.model.StudyType
import team.aliens.dms.android.data.latestudy.repository.LateStudyRepository
import team.aliens.dms.android.network.latestudy.model.SubmitLateStudyRequest
import team.aliens.dms.android.network.latestudy.model.TeacherResponse

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

    init {
        fetchStudyTypes()
        fetchTeachers()
    }

    private fun fetchStudyTypes() {
        viewModelScope.launch {
            try {
                studyTypes = lateStudyRepository.fetchStudyTypes()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchTeachers() {
        viewModelScope.launch {
            try {
                teachers = lateStudyRepository.fetchTeachers()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Exception) {
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
        viewModelScope.launch {
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
                onSuccess()
            } catch (e: IOException) {
                e.printStackTrace()
                onFailure("새벽 자습 신청에 실패했습니다.")
            } catch (e: Exception) {
                e.printStackTrace()
                onFailure("이미 새벽 자습을 신청했습니다.")
            }
        }
    }
}
