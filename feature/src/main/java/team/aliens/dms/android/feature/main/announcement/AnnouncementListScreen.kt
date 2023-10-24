package team.aliens.dms.android.feature.main.announcement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import org.threeten.bp.LocalDateTime
import team.aliens.dms.android.core.designsystem.ButtonDefaults
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.OutlinedButton
import team.aliens.dms.android.core.designsystem.ShadowDefaults
import team.aliens.dms.android.core.designsystem.clickable
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.data.notice.model.Notice
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.shared.model.Order
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun AnnouncementListScreen(
    modifier: Modifier = Modifier,
    onNavigateToNoticeDetails: (noticeId: UUID) -> Unit,
) {
    var order by remember { mutableStateOf(Order.NEW) }
    val onOrderChange = {
        order = when (order) {
            Order.NEW -> Order.OLD
            Order.OLD -> Order.NEW
        }
    }
    DmsScaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(title = { Text(text = stringResource(id = R.string.announcement)) })
        },
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues),
            verticalArrangement = Arrangement.spacedBy(PaddingDefaults.Small),
        ) {
            OrderButton(
                order = order,
                onOrderChange = onOrderChange,
            )
            NoticeList(
                modifier = Modifier.weight(1f),
                notices = listOf(
                    Notice(
                        UUID.randomUUID(), "asdf", "asdf", LocalDateTime.now()
                    )
                ), // TODO
                onNavigateToNoticeDetails = onNavigateToNoticeDetails,
            )
        }
    }
}

@Composable
private fun OrderButton(
    modifier: Modifier = Modifier,
    order: Order,
    onOrderChange: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier
            .horizontalPadding()
            .topPadding(),
        onClick = onOrderChange,
        colors = ButtonDefaults.outlinedGrayButtonColors(),
    ) {
        Text(text = order.text)
    }
}

private val Order.text: String
    @Composable inline get() = when (this) {
        Order.NEW -> stringResource(R.string.order_latest)
        Order.OLD -> stringResource(R.string.order_oldest)
    }

@Composable
private fun NoticeList(
    modifier: Modifier = Modifier,
    notices: List<Notice>,
    onNavigateToNoticeDetails: (noticeId: UUID) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(notices) { notice ->
            NoticeCard(
                modifier = Modifier.fillMaxWidth(),
                notice = notice,
                onClick = onNavigateToNoticeDetails,
            )
        }
    }
}

@Composable
private fun NoticeCard(
    modifier: Modifier = Modifier,
    notice: Notice,
    onClick: (noticeId: UUID) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .horizontalPadding()
            .verticalPadding(),
        shape = DmsTheme.shapes.surfaceSmall,
        colors = CardDefaults.outlinedCardColors(
            containerColor = DmsTheme.colorScheme.surface,
            contentColor = DmsTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = ShadowDefaults.SmallElevation),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(notice.id) },
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .topPadding(),
                text = notice.title,
                color = DmsTheme.colorScheme.onSurface,
                style = DmsTheme.typography.body2,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                text = notice.createdAt.text,
                color = DmsTheme.colorScheme.onSurfaceVariant,
                style = DmsTheme.typography.caption,
            )
        }
    }
}

private val LocalDateTime.text: String
    @Composable inline get() = "${this.year}-${this.monthValue}-${this.dayOfMonth} ${this.hour}:${this.minute}"
