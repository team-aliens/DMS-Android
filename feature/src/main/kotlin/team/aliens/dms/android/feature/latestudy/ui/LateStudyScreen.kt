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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
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
    var selectedType by remember { mutableStateOf<String?>(null) }

    val types = listOf(
        "개인 공부",
        "개인 프로젝트",
        "팀 프로젝트",
        "대회 프로젝트",
        "기타",
    )

    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }

    var teacherKeyword by remember { mutableStateOf("") }
    var selectedTeacherId by remember { mutableStateOf<String?>(null) }
    var selectedTeacherName by remember { mutableStateOf<String?>(null) }
    var isDropdownVisible by remember { mutableStateOf(false) }

    val viewModel: LateStudyViewModel = hiltViewModel()

    val teachers = listOf(
        TeacherResponse(id = "1", name = "정은진"),
        TeacherResponse(id = "2", name = "권현진"),
        TeacherResponse(id = "3", name = "양은정"),
        TeacherResponse(id = "4", name = "서무성"),
    )

    // val teachers = viewModel.teachers

    val filteredTeachers =
        if (teacherKeyword.isBlank()) {
            emptyList()
        } else {
            teachers.filter { teacher ->
                teacher.name.contains(teacherKeyword)
            }
        }

    var reason by remember { mutableStateOf("") }

    val dropdownShadowColor = DmsTheme.colorScheme.primary.copy(alpha = 0.28f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 14.dp, vertical = 16.dp),
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
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(10f),
        ) {
            Column {
                LateStudyTeacherSection(
                    value = teacherKeyword,
                    onValueChange = {
                        teacherKeyword = it
                        selectedTeacherId = null
                        selectedTeacherName = null
                        isDropdownVisible = true
                    },
                )

                Spacer(modifier = Modifier.height(20.dp))
            }

            if (isDropdownVisible && filteredTeachers.isNotEmpty()) {
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
                        filteredTeachers.forEach { teacher ->
                            val startIndex = teacher.name.indexOf(teacherKeyword)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        teacherKeyword = teacher.name
                                        selectedTeacherName = teacher.name
                                        selectedTeacherId = teacher.id
                                        isDropdownVisible = false
                                    }
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
                                    text = buildAnnotatedString {
                                        if (startIndex >= 0 && teacherKeyword.isNotEmpty()) {
                                            append(teacher.name.substring(0, startIndex))
                                            withStyle(
                                                SpanStyle(
                                                    color = DmsTheme.colorScheme.primaryContainer,
                                                ),
                                            ) {
                                                append(
                                                    teacher.name.substring(
                                                        startIndex,
                                                        startIndex + teacherKeyword.length,
                                                    ),
                                                )
                                            }
                                            append(
                                                teacher.name.substring(
                                                    startIndex + teacherKeyword.length,
                                                ),
                                            )
                                        } else {
                                            append(teacher.name)
                                        }
                                    },
                                    color = DmsTheme.colorScheme.onBackground,
                                    style = DmsTheme.typography.bodyM,
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LateStudySectionCard {
            Text(
                text = "유형",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                color = DmsTheme.colorScheme.onBackground,
                style = DmsTheme.typography.bodyB,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(Modifier.height(8.dp))

            types.forEach { type ->
                LateStudyTypeItem(
                    text = type,
                    selected = selectedType == type,
                    onClick = { selectedType = type },
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LateStudyCalendarSection(
            currentMonth = currentMonth,
            startDate = startDate,
            endDate = endDate,
            onPrevMonthClick = { currentMonth = currentMonth.minusMonths(1) },
            onNextMonthClick = { currentMonth = currentMonth.plusMonths(1) },
            onDateClick = { clickedDate ->
                when {
                    startDate == null -> {
                        startDate = clickedDate
                    }

                    endDate == null && !clickedDate.isBefore(startDate) -> {
                        endDate = clickedDate
                    }

                    else -> {
                        startDate = clickedDate
                        endDate = null
                    }
                }
            },
        )

        Spacer(modifier = Modifier.height(20.dp))

        LateStudyReasonSection(
            value = reason,
            onValueChange = { reason = it },
        )

        Spacer(modifier = Modifier.height(20.dp))

        DmsLayeredButton(
            modifier = Modifier.fillMaxWidth(),
            text = "신청하기",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            enabled =
                selectedTeacherId != null &&
                        selectedType != null &&
                        startDate != null &&
                        endDate != null &&
                        reason.isNotBlank(),
            onClick = {
                if (
                    selectedTeacherId != null &&
                    selectedType != null &&
                    startDate != null &&
                    endDate != null &&
                    reason.isNotBlank()
                ) {
                    viewModel.submitLateStudy(
                        teacherId = selectedTeacherId!!,
                        typeId = selectedType!!,
                        reason = reason,
                        startDate = startDate.toString(),
                        endDate = endDate.toString(),
                    )
                } else {
                    onShowSnackBar(
                        DmsSnackBarType.ERROR,
                        "모두 선택해주세요",
                    )
                }
            },
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}
