package team.aliens.dms_android.feature.notice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.util.UUID
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormOutlinedDefaultButton
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.modifier.dormGradientBackground
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body1
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.typography.Body4
import team.aliens.design_system.typography.OverLine
import team.aliens.domain.model._common.Order
import team.aliens.domain.model.notice.Notice
import team.aliens.presentation.R

@Composable
internal fun NoticesScreen(
    onNavigateToNoticeDetailsScreen: (UUID) -> Unit,
    noticesViewModel: NoticesViewModel = hiltViewModel(),
) {
    val uiState by noticesViewModel.uiState.collectAsStateWithLifecycle()

    val onOrderButtonClick = {
        noticesViewModel.onEvent(
            NoticesUiEvent.SetOrder(
                order = when (uiState.order) {
                    Order.NEW -> Order.OLD
                    Order.OLD -> Order.NEW
                }
            ),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DormTheme.colors.background,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(24.dp))
        Body1(text = stringResource(R.string.Notice))
        Spacer(Modifier.height(24.dp))
        OrderButton(
            order = uiState.order,
            onClick = onOrderButtonClick,
        )
        Notices(
            notices = uiState.notices,
            onNoticeClick = onNavigateToNoticeDetailsScreen,
        )
    }
}

@Composable
private fun getStringByOrder(
    order: Order,
) = when (order) {
    Order.NEW -> stringResource(R.string.latest_order)
    Order.OLD -> stringResource(R.string.oldest_order)
    else -> throw IllegalArgumentException()
}

@Composable
private fun OrderButton(
    order: Order,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start,
    ) {
        DormOutlinedDefaultButton(
            text = getStringByOrder(order),
            color = DormButtonColor.Gray,
            onClick = onClick,
        )
    }
}

@Composable
private fun ColumnScope.Notices(
    notices: List<Notice>,
    onNoticeClick: (UUID) -> Unit,
) {
    // FIXME replace with fade brush
    val color = DormTheme.colors
    val pointFadedBackgroundBrush = remember {
        Brush.verticalGradient(
            colors = listOf(
                color.background,
                Color.Transparent,
            ),
        )
    } // FIXME end

    Box(
        modifier = Modifier.weight(1f),
        contentAlignment = Alignment.TopCenter,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(
                top = 20.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 80.dp,
            ),
        ) {
            items(notices) { notice ->
                Notice(
                    notice = notice,
                    onNoticeClick = onNoticeClick,
                )
            }
            if (notices.isEmpty()) {
                item {
                    Body3(text = stringResource(R.string.TheresNoNotices))
                }
            }
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(20.dp)
                .dormGradientBackground(pointFadedBackgroundBrush),
        )
    }
}

@Composable
private fun Notice(
    notice: Notice,
    onNoticeClick: (UUID) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .dormShadow(
                color = DormTheme.colors.secondaryVariant,
                offsetX = 1.dp,
                offsetY = 1.dp,
            )
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
