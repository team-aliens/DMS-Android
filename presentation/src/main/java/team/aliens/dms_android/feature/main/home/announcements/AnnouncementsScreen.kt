package team.aliens.dms_android.feature.main.home.announcements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormOutlinedDefaultButton
import team.aliens.design_system.extension.Space
import team.aliens.design_system.layout.VerticallyFadedLazyColumn
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body1
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.typography.Body4
import team.aliens.design_system.typography.OverLine
import team.aliens.dms_android.presentation.R
import team.aliens.domain.model._common.Order
import team.aliens.domain.model.notice.Notice
import java.util.UUID

private val Order.text: String
    @Composable inline get() = when (this) {
        Order.NEW -> stringResource(R.string.latest_order)
        Order.OLD -> stringResource(R.string.oldest_order)
        else -> throw IllegalArgumentException()
    }

@Destination
@Composable
internal fun AnnouncementsScreen(
    modifier: Modifier = Modifier,
    announcementsViewModel: AnnouncementsViewModel = hiltViewModel(),
    onNavigateToNoticeDetails: (UUID) -> Unit,
) {
    val uiState by announcementsViewModel.stateFlow.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val onOrderButtonClick = {
        scope.launch { listState.animateScrollToItem(0) }
        announcementsViewModel.postIntent(
            AnnouncementsIntent.SetOrder(
                order = when (uiState.order) {
                    Order.NEW -> Order.OLD
                    Order.OLD -> Order.NEW
                },
            ),
        )
    }

    Column(
        modifier = modifier
            .background(DormTheme.colors.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(24.dp))
        Body1(text = stringResource(R.string.notice))
        OrderButton(
            text = uiState.order.text,
            onClick = onOrderButtonClick,
        )
        Notices(
            modifier = Modifier.weight(1f),
            notices = uiState.notices,
            listState = listState,
            onNoticeClick = onNavigateToNoticeDetails,
        )
    }
}

@Composable
private fun OrderButton(
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start,
    ) {
        DormOutlinedDefaultButton(
            text = text,
            color = DormButtonColor.Gray,
            onClick = onClick,
        )
    }
}

@Composable
private fun Notices(
    modifier: Modifier = Modifier,
    notices: List<Notice>,
    listState: LazyListState,
    onNoticeClick: (noticeId: UUID) -> Unit,
) {
    VerticallyFadedLazyColumn(
        modifier = modifier.fillMaxSize(),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(notices) { notice ->
            Notice(
                notice = notice,
                onNoticeClick = onNoticeClick,
            )
        }
        // todo add loading effect
        if (notices.isEmpty()) {
            item {
                Body3(text = stringResource(R.string.TheresNoNotices))
            }
        }
    }
}

@Composable
private fun Notice(
    modifier: Modifier = Modifier,
    notice: Notice,
    onNoticeClick: (UUID) -> Unit,
) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .dormShadow(
                color = DormTheme.colors.primaryVariant,
            )
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = DormTheme.colors.surface,
            )
            .dormClickable {
                onNoticeClick(notice.id!!)
            },
        contentAlignment = Alignment.CenterStart,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Body4(
                modifier = Modifier.padding(top = 12.dp),
                text = notice.title,
            )
            Space(space = 4.dp)
            OverLine(
                modifier = Modifier.padding(bottom = 12.dp),
                text = notice.createdAt.toNoticeDate(),
                color = DormTheme.colors.primaryVariant,
            )
        }
    }
}

internal fun String.toNoticeDate() = StringBuilder().apply {
    with(this@toNoticeDate.split("T")) {
        if (this@toNoticeDate.isNotEmpty()) {
            append(this[0])
            append(" ")
            append(this[1].split(":")[0].toInt())
            append(":")
            append(this[1].split(":")[1])
        }
    }
}.toString()
