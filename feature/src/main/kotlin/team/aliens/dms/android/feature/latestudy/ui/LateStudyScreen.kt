package team.aliens.dms.android.feature.latestudy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import java.time.LocalDate
import java.time.YearMonth
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsLayeredButton
import team.aliens.dms.android.core.designsystem.sTitleB
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.ui.navigation.LocalResultStore
import team.aliens.dms.android.data.latestudy.model.StudyType
import team.aliens.dms.android.feature.latestudy.ui.component.LateStudyCalendarSection
import team.aliens.dms.android.feature.latestudy.ui.component.LateStudyReasonSection
import team.aliens.dms.android.feature.latestudy.ui.component.LateStudySectionCard
import team.aliens.dms.android.feature.latestudy.ui.component.LateStudyTeacherSection
import team.aliens.dms.android.feature.latestudy.ui.component.LateStudyTypeItem
import team.aliens.dms.android.feature.latestudy.viewmodel.LateStudyViewModel
import team.aliens.dms.android.network.latestudy.model.TeacherResponse

@Composable
fun LateStudyScreen(
    onBack: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: LateStudyViewModel = hiltViewModel()
    val focusManager = LocalFocusManager.current
    val resultStore = LocalResultStore.current

    var selectedTypeId by remember { mutableStateOf<String?>(null) }
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }
    var teacherKeyword by remember { mutableStateOf("") }
    var selectedTeacherId by remember { mutableStateOf<String?>(null) }
    var isDropdownVisible by remember { mutableStateOf(false) }
    var reason by remember { mutableStateOf("") }

    val studyTypes = viewModel.studyTypes
    val teachers = viewModel.teachers
    val filteredTeachers = remember(teacherKeyword, teachers) {
        if (teacherKeyword.isBlank()) {
            emptyList()
        } else {
            teachers.filter { teacher ->
                teacher.name.contains(teacherKeyword)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 14.dp, vertical = 16.dp),
    ) {
        LateStudyHeader(onBack = onBack)

        TeacherSearchSection(
            teacherKeyword = teacherKeyword,
            onKeywordChange = {
                teacherKeyword = it
                selectedTeacherId = null
                isDropdownVisible = true
            },
            filteredTeachers = filteredTeachers,
            isDropdownVisible = isDropdownVisible,
            onTeacherClick = { teacher ->
                teacherKeyword = teacher.name
                selectedTeacherId = teacher.id
                isDropdownVisible = false
                focusManager.clearFocus()
            },
        )

        StudyTypeSection(
            studyTypes = studyTypes,
            selectedTypeId = selectedTypeId,
            onTypeClick = { selectedTypeId = it },
        )

        DateSelectSection(
            currentMonth = currentMonth,
            startDate = startDate,
            endDate = endDate,
            onPrevMonthClick = { currentMonth = currentMonth.minusMonths(1) },
            onNextMonthClick = { currentMonth = currentMonth.plusMonths(1) },
            onDateClick = { clickedDate ->
                when {
                    startDate == null -> startDate = clickedDate
                    endDate == null && !clickedDate.isBefore(startDate) -> endDate = clickedDate
                    else -> {
                        startDate = clickedDate
                        endDate = null
                    }
                }
            },
        )

        LateStudyReasonSection(
            value = reason,
            onValueChange = { reason = it },
        )

        Spacer(modifier = Modifier.height(20.dp))

        SubmitButton(
            enabled = selectedTeacherId != null &&
                    selectedTypeId != null &&
                    startDate != null &&
                    reason.isNotBlank(),
            onClick = {
                if (
                    selectedTeacherId != null &&
                    selectedTypeId != null &&
                    startDate != null &&
                    reason.isNotBlank()
                ) {
                    viewModel.submitLateStudy(
                        teacherId = selectedTeacherId!!,
                        typeId = selectedTypeId!!,
                        reason = reason,
                        startDate = startDate.toString(),
                        endDate = (endDate ?: startDate).toString(),
                    )

                    resultStore.setResult(
                        resultKey = "late_study_application_result",
                        result = "신청 중",
                    )

                    onBack()
                } else {
                    onShowSnackBar(
                        DmsSnackBarType.ERROR,
                        "모두 선택해주세요",
                    )
                }
            },
        )
    }
}

@Composable
private fun LateStudyHeader(
    onBack: () -> Unit,
) {
    IconButton(
        onClick = onBack,
        modifier = Modifier.size(24.dp),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "뒤로가기",
            tint = DmsTheme.colorScheme.onBackground,
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Text(
        text = "새벽 자습 신청",
        color = DmsTheme.colorScheme.onBackground,
        style = DmsTheme.typography.sTitleB,
    )

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun TeacherSearchSection(
    teacherKeyword: String,
    onKeywordChange: (String) -> Unit,
    filteredTeachers: List<TeacherResponse>,
    isDropdownVisible: Boolean,
    onTeacherClick: (TeacherResponse) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(10f),
    ) {
        Column {
            LateStudyTeacherSection(
                value = teacherKeyword,
                onValueChange = onKeywordChange,
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        if (isDropdownVisible && filteredTeachers.isNotEmpty()) {
            TeacherDropdown(
                teachers = filteredTeachers,
                keyword = teacherKeyword,
                onTeacherClick = onTeacherClick,
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun TeacherDropdown(
    teachers: List<TeacherResponse>,
    keyword: String,
    onTeacherClick: (TeacherResponse) -> Unit,
) {
    val dropdownShadowColor = DmsTheme.colorScheme.primary.copy(alpha = 0.28f)
    val highlightColor = DmsTheme.colorScheme.primaryContainer

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .offset(y = 120.dp)
            .zIndex(10f),
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(y = 10.dp)
                .background(
                    color = dropdownShadowColor,
                    shape = RoundedCornerShape(28.dp),
                ),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(28.dp),
                    clip = false,
                )
                .background(
                    color = DmsTheme.colorScheme.surface,
                    shape = RoundedCornerShape(28.dp),
                )
                .heightIn(max = 220.dp)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 12.dp),
        ) {
            teachers.forEach { teacher ->
                TeacherDropdownItem(
                    teacher = teacher,
                    keyword = keyword,
                    highlightColor = highlightColor,
                    onClick = { onTeacherClick(teacher) },
                )
            }
        }
    }
}

@Composable
private fun TeacherDropdownItem(
    teacher: TeacherResponse,
    keyword: String,
    highlightColor: Color,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = "검색",
            tint = DmsTheme.colorScheme.onPrimary,
            modifier = Modifier.size(20.dp),
        )

        Text(
            text = highlightText(
                text = teacher.name,
                keyword = keyword,
                highlightColor = highlightColor,
            ),
            color = DmsTheme.colorScheme.onBackground,
            style = DmsTheme.typography.bodyM,
        )
    }
}

@Composable
private fun StudyTypeSection(
    studyTypes: List<StudyType>,
    selectedTypeId: String?,
    onTypeClick: (String) -> Unit,
) {
    LateStudySectionCard {
        Text(
            text = "유형",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            color = DmsTheme.colorScheme.onBackground,
            style = DmsTheme.typography.bodyB,
        )

        Spacer(modifier = Modifier.height(8.dp))

        studyTypes.forEach { type ->
            LateStudyTypeItem(
                text = type.name,
                selected = selectedTypeId == type.id,
                onClick = { onTypeClick(type.id) },
            )
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
private fun DateSelectSection(
    currentMonth: YearMonth,
    startDate: LocalDate?,
    endDate: LocalDate?,
    onPrevMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    onDateClick: (LocalDate) -> Unit,
) {
    LateStudyCalendarSection(
        currentMonth = currentMonth,
        startDate = startDate,
        endDate = endDate,
        onPrevMonthClick = onPrevMonthClick,
        onNextMonthClick = onNextMonthClick,
        onDateClick = onDateClick,
    )

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
private fun SubmitButton(
    enabled: Boolean,
    onClick: () -> Unit,
) {
    DmsLayeredButton(
        modifier = Modifier.fillMaxWidth(),
        text = "신청하기",
        buttonType = ButtonType.Contained,
        buttonColor = ButtonColor.Primary,
        enabled = enabled,
        onClick = onClick,
    )

    Spacer(modifier = Modifier.height(12.dp))
}

private fun highlightText(
    text: String,
    keyword: String,
    highlightColor: Color,
) = buildAnnotatedString {
    val startIndex = text.indexOf(keyword)

    if (startIndex >= 0 && keyword.isNotEmpty()) {
        append(text.substring(0, startIndex))
        withStyle(
            style = SpanStyle(color = highlightColor),
        ) {
            append(text.substring(startIndex, startIndex + keyword.length))
        }
        append(text.substring(startIndex + keyword.length))
    } else {
        append(text)
    }
}
