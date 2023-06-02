package team.aliens.dms_android.feature.notice

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import java.util.UUID
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body1
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.typography.Body4
import team.aliens.design_system.typography.ButtonText
import team.aliens.design_system.typography.OverLine
import team.aliens.dms_android.common.LocalAvailableFeatures
import team.aliens.dms_android.constans.Extra
import team.aliens.domain.model._common.Order
import team.aliens.domain.model.notice.Notice
import team.aliens.presentation.R

@Composable
internal fun NoticesScreen(
    navController: NavController,
    noticesViewModel: NoticesViewModel = hiltViewModel(),
) {

    val uiState by noticesViewModel.uiState.collectAsStateWithLifecycle()

    val isNoticeServiceEnabled = LocalAvailableFeatures.current[Extra.isNoticeServiceEnabled]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DormTheme.colors.background,
            )
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Space(space = 24.dp)
        Body1(
            text = stringResource(R.string.Notice),
        )
        Space(space = 20.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            OrderButton(
                order = uiState.order,
                noticesViewModel = noticesViewModel,
            )
        }
        Notices(
            notices = uiState.notices,
        ) { noticeId ->
            navController.navigate("noticeDetails/${noticeId}")
        }
    }
}

@Composable
private fun OrderButton(
    order: Order,
    noticesViewModel: NoticesViewModel,
) {
    val text = getStringByOrder(order = order)

    Button(
        onClick = {
            noticesViewModel.onEvent(NoticesUiEvent.SetOrder)
        },
        border = BorderStroke(
            width = 1.dp,
            color = DormTheme.colors.onBackground,
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = DormTheme.colors.background,
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp,
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ButtonText(
                text = text,
            )
        }
    }
}

@Composable
private fun Notices(
    notices: List<Notice>,
    onNoticeClick: (UUID) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
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
}

@Composable
private fun Notice(
    notice: Notice,
    onNoticeClick: (UUID) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(6.dp))
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

private fun String.toNoticeDate() = StringBuilder().apply {
    with(this@toNoticeDate.split("T")) {
        append(get(0))
        append(" ")
        append(get(1).split(":")[0])
        append(":")
        append(get(1).split(":")[1])
    }
}.toString()

@Composable
private fun getStringByOrder(
    order: Order,
) = when (order) {
    Order.NEW -> stringResource(id = R.string.latest_order)
    Order.OLD -> stringResource(id = R.string.oldest_order)
    else -> throw IllegalArgumentException()
}