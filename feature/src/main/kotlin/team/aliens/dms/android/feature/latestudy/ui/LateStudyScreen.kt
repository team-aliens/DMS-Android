package team.aliens.dms.android.feature.latestudy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import androidx.compose.runtime.setValue
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsLayeredButton
import team.aliens.dms.android.core.designsystem.sTitleB
import team.aliens.dms.android.feature.latestudy.ui.component.LateStudySectionCard
import team.aliens.dms.android.feature.latestudy.ui.component.LateStudyTypeItem
import team.aliens.dms.android.feature.latestudy.ui.component.LateStudyCalendarSection
import team.aliens.dms.android.feature.latestudy.ui.component.LateStudyReasonSection
import team.aliens.dms.android.feature.latestudy.ui.component.LateStudyTeacherSection
import java.time.YearMonth
import java.time.LocalDate
import TeacherResponse


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

    var teachers by remember { mutableStateOf<List<TeacherResponse>>(emptyList()) }

    var reason by remember { mutableStateOf("") }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
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

        LateStudyTeacherSection(
            value = teacherKeyword,
            onValueChange = {
                teacherKeyword = it
                selectedTeacherId = null
                selectedTeacherName = null
            },
            teachers = filteredTeachers,
            onTeacherClick = { teacher ->
                teacherKeyword = teacher.teacherName
                selectedTeacherName = teacher.teacherName
                selectedTeacherId = teacher.teacherId
            },
        )

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
            modifier = Modifier
                .fillMaxWidth(),
            text = "신청하기",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            enabled = true,
            onClick = { },
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}
