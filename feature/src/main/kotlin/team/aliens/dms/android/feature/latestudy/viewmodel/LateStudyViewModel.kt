package team.aliens.dms.android.feature.latestudy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms.android.data.latestudy.model.StudyType
import team.aliens.dms.android.data.latestudy.repository.LateStudyRepository
import TeacherResponse
import javax.inject.Inject

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
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchTeachers() {
        viewModelScope.launch {
            try {
                teachers = lateStudyRepository.fetchTeachers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun selectStudyType(typeId: String) {
        selectedTypeId = typeId
    }
}
