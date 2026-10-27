package team.aliens.dms.android.feature.latestudy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeParseException
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsButton
import team.aliens.dms.android.core.designsystem.labelB
import team.aliens.dms.android.core.designsystem.sTitleB
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.latestudy.ui.component.LateStudySectionCard
import team.aliens.dms.android.feature.latestudy.viewmodel.LateStudyDetailUiState
import team.aliens.dms.android.feature.latestudy.viewmodel.LateStudyDetailViewModel
import team.aliens.dms.android.network.latestudy.model.StudyApplicationStatusResponse

@Composable
fun LateStudyDetailScreen(
    onBack: () -> Unit,
    onCancel: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: LateStudyDetailViewModel = hiltViewModel()
    val state = viewModel.uiState
    var isCancelDialogVisible by remember { mutableStateOf(false) }
    val currentOnBack by rememberUpdatedState(onBack)
    val currentOnShowSnackBar by rememberUpdatedState(onShowSnackBar)

    when (state) {
        LateStudyDetailUiState.Loading -> LoadingContent()
        LateStudyDetailUiState.Empty -> LaunchedEffect(state) {
            currentOnShowSnackBar(DmsSnackBarType.ERROR, "새벽 자습 신청 내역이 없어요")
            currentOnBack()
        }
        LateStudyDetailUiState.Error -> LaunchedEffect(state) {
            currentOnShowSnackBar(DmsSnackBarType.ERROR, "새벽 자습 신청 내역을 불러오지 못했어요")
            currentOnBack()
        }
        is LateStudyDetailUiState.Success -> {
            LateStudyDetailContent(
                application = state.application,
                onBack = onBack,
                onCancelClick = { isCancelDialogVisible = true },
                isCancelling = viewModel.isCancelling,
            )
        }
    }

    if (isCancelDialogVisible) {
        CancelLateStudyDialog(
            isCancelling = viewModel.isCancelling,
            onDismissRequest = { isCancelDialogVisible = false },
            onConfirm = {
                viewModel.cancelApplication(
                    onSuccess = onCancel,
                    onFailure = { message ->
                        isCancelDialogVisible = false
                        onShowSnackBar(DmsSnackBarType.ERROR, message)
                    },
                )
            },
        )
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = DmsTheme.colorScheme.onPrimaryContainer)
    }
}

@Composable
private fun LateStudyDetailContent(
    application: StudyApplicationStatusResponse,
    onBack: () -> Unit,
    onCancelClick: () -> Unit,
    isCancelling: Boolean,
) {
    val selectedDates = remember(application.applicationDates) { application.applicationDates.toLocalDates() }
    var displayedMonth by remember(selectedDates) {
        mutableStateOf(selectedDates.firstOrNull()?.let(YearMonth::from) ?: YearMonth.now())
    }
    val canCancel = application.status == PENDING_STATUS

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 10.dp, vertical = 16.dp)
                .padding(bottom = if (canCancel) 112.dp else 0.dp),
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "뒤로가기",
                    tint = DmsTheme.colorScheme.onBackground,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "새벽 자습 신청 상세",
                color = DmsTheme.colorScheme.onBackground,
                style = DmsTheme.typography.sTitleB,
                modifier = Modifier.padding(horizontal = 14.dp),
            )

            Spacer(modifier = Modifier.height(20.dp))

            LateStudySectionCard(
                modifier = Modifier.padding(horizontal = 0.dp),
            ) {
                DetailValueSection(
                    title = "담당 선생님",
                    value = application.teacherName.orUnavailable(),
                )

                Spacer(modifier = Modifier.height(16.dp))

                DetailValueSection(
                    title = "유형",
                    value = application.typeName.orUnavailable(),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "일정",
                    color = DmsTheme.colorScheme.inverseOnSurface,
                    style = DmsTheme.typography.bodyB,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )

                Spacer(modifier = Modifier.height(10.dp))

                DetailCalendar(
                    month = displayedMonth,
                    selectedDates = selectedDates,
                    onPreviousMonth = { displayedMonth = displayedMonth.minusMonths(1) },
                    onNextMonth = { displayedMonth = displayedMonth.plusMonths(1) },
                )

                Spacer(modifier = Modifier.height(16.dp))

                DetailReasonSection(reason = application.reason.orUnavailable())
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        if (canCancel) {
            LateStudySectionCard(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding(),
            ) {
                DmsButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = "신청 취소하기",
                    buttonType = ButtonType.Contained,
                    buttonColor = ButtonColor.Error,
                    enabled = !isCancelling,
                    isLoading = isCancelling,
                    onClick = onCancelClick,
                )
            }
        }
    }
}

@Composable
private fun DetailReasonSection(reason: String) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "사유",
                color = DmsTheme.colorScheme.inverseOnSurface,
                style = DmsTheme.typography.bodyB,
            )
            Text(
                text = "${reason.length}/200",
                color = DmsTheme.colorScheme.inverseSurface,
                style = DmsTheme.typography.labelB,
            )
        }

        Text(
            text = reason,
            color = DmsTheme.colorScheme.onBackground,
            style = DmsTheme.typography.bodyM,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(horizontal = 16.dp, vertical = 14.dp)
                .background(
                    color = DmsTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(20.dp),
                )
                .padding(16.dp),
        )
    }
}

@Composable
private fun DetailValueSection(
    title: String,
    value: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = title,
            color = DmsTheme.colorScheme.inverseOnSurface,
            style = DmsTheme.typography.bodyB,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Text(
            text = value,
            color = DmsTheme.colorScheme.onBackground,
            style = DmsTheme.typography.bodyM,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(
                    color = DmsTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(32.dp),
                )
                .padding(horizontal = 16.dp, vertical = 14.dp),
        )
    }
}

@Composable
private fun DetailCalendar(
    month: YearMonth,
    selectedDates: Set<LocalDate>,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onPreviousMonth) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "이전 달",
                    tint = DmsTheme.colorScheme.inverseOnSurface,
                )
            }
            Text(
                text = "${month.year} ${month.monthValue}월",
                color = DmsTheme.colorScheme.onBackground,
                style = DmsTheme.typography.bodyM,
            )
            IconButton(onClick = onNextMonth) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "다음 달",
                    tint = DmsTheme.colorScheme.inverseOnSurface,
                )
            }
        }

        val dayLabels = listOf("일", "월", "화", "수", "목", "금", "토")
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            dayLabels.forEachIndexed { index, label ->
                Text(
                    text = label,
                    color = when (index) {
                        0 -> DmsTheme.colorScheme.onError
                        6 -> DmsTheme.colorScheme.onPrimary
                        5 -> DmsTheme.colorScheme.inverseSurface
                        else -> DmsTheme.colorScheme.onBackground
                    },
                    style = DmsTheme.typography.bodyM,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        val firstDayOffset = month.atDay(1).dayOfWeek.toSundayBasedIndex()
        val days = List(firstDayOffset) { null } + (1..month.lengthOfMonth()).map(month::atDay)
        days.chunked(7).forEach { week ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
            ) {
                week.forEach { date ->
                    CalendarDay(
                        date = date,
                        isSelected = date in selectedDates,
                        modifier = Modifier.weight(1f),
                    )
                }
                repeat(7 - week.size) {
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun CalendarDay(
    date: LocalDate?,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.aspectRatio(1f),
        contentAlignment = Alignment.Center,
    ) {
        if (date != null) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(DmsTheme.colorScheme.primaryContainer, CircleShape),
                )
            }
            Text(
                text = date.dayOfMonth.toString(),
                color = when {
                    isSelected -> DmsTheme.colorScheme.surface
                    date.dayOfWeek == DayOfWeek.SUNDAY -> DmsTheme.colorScheme.onError
                    date.dayOfWeek == DayOfWeek.SATURDAY -> DmsTheme.colorScheme.onPrimary
                    else -> DmsTheme.colorScheme.onBackground
                },
                style = DmsTheme.typography.bodyM,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun CancelLateStudyDialog(
    isCancelling: Boolean,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x33101010))
                .clickable(onClick = onDismissRequest),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(32.dp))
                    .background(DmsTheme.colorScheme.surfaceTint)
                    .clickable(onClick = {})
                    .padding(horizontal = 60.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = "신청 취소하시겠습니까?",
                    style = DmsTheme.typography.bodyM,
                    color = DmsTheme.colorScheme.onBackground,
                )
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    DialogActionButton(
                        text = "확인",
                        color = DmsTheme.colorScheme.onErrorContainer,
                        enabled = !isCancelling,
                        onClick = onConfirm,
                    )
                    DialogActionButton(
                        text = "취소",
                        color = DmsTheme.colorScheme.inverseOnSurface,
                        enabled = !isCancelling,
                        onClick = onDismissRequest,
                    )
                }
            }
        }
    }
}

@Composable
private fun DialogActionButton(
    text: String,
    color: Color,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        color = DmsTheme.colorScheme.surface,
        style = DmsTheme.typography.labelB,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color)
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp),
    )
}

private fun List<String>.toLocalDates(): Set<LocalDate> = mapNotNull { date ->
    try {
        LocalDate.parse(date)
    } catch (_: DateTimeParseException) {
        null
    }
}.toSet()

private fun DayOfWeek.toSundayBasedIndex(): Int = value % 7

private fun String?.orUnavailable(): String = this?.takeIf(String::isNotBlank) ?: "확인할 수 없어요"

private const val PENDING_STATUS = "PENDING"
