package team.aliens.dms.android.feature.vote.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.foundation.DmsIcon
import team.aliens.dms.android.core.designsystem.startPadding
import team.aliens.dms.android.core.designsystem.tab.DmsTab
import team.aliens.dms.android.core.designsystem.tab.DmsTabRow
import team.aliens.dms.android.core.designsystem.util.clickable
import team.aliens.dms.android.data.student.model.Student
import java.time.LocalDateTime

@Composable
internal fun StudentContent(
    modifier: Modifier = Modifier,
    title: String,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    students: List<Student>,
    selectItem: String,
    onSelect: (String) -> Unit,
) {
    val grades = listOf("1학년", "2학년", "3학년")
    val pagerState = rememberPagerState(
        pageCount = { grades.size },
        initialPage = 0,
    )
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        TitleContent(
            title = title,
            startTime = startTime,
            endTime = endTime,
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.4.dp,
            color = DmsTheme.colorScheme.onSurfaceVariant,
        )
        DmsTabRow(
            selectedTabIndex = tabIndex,
        ) {
            grades.forEachIndexed { index, text ->
                DmsTab(
                    selected = tabIndex == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = text,
                )
            }
        }
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
            beyondViewportPageCount = 1,
        ) { page ->
            val selectGrade = page + 1
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                val filteredStudents = students.filter { it.gradeClassNumber.startsWith("$selectGrade") }
                items(
                    items = filteredStudents,
                    key = { student -> student.id },
                ) { student ->
                    StudentItem(
                        profileImageUrl = student.profileImageUrl,
                        name = student.name,
                        gcn = student.gradeClassNumber,
                        isSelected = selectItem == student.id.toString(),
                        onClick = { onSelect(student.id.toString()) },
                    )
                }
            }
        }
    }
}

@Composable
private fun StudentItem(
    modifier: Modifier = Modifier,
    profileImageUrl: String,
    name: String,
    gcn: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor = if (isSelected) {
        DmsTheme.colorScheme.surfaceVariant
    } else {
        DmsTheme.colorScheme.background
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(
                horizontal = 24.dp,
                vertical = 18.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(profileImageUrl)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Text(
            modifier = Modifier.startPadding(12.dp),
            text = "$gcn $name",
            style = DmsTheme.typography.bodyB,
            color = DmsTheme.colorScheme.inverseOnSurface,
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(DmsIcon.Forward),
            tint = DmsTheme.colorScheme.scrim,
            contentDescription = null,
        )
    }
}
