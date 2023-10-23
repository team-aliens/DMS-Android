package team.aliens.dms.android.feature.main.announcement

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.feature.main.announcement.navigation.AnnouncementNavigator

/*

private val Order.text: String
    @Composable inline get() = when (this) {
        Order.NEW -> stringResource(R.string.latest_order)
        Order.OLD -> stringResource(R.string.oldest_order)
        else -> throw IllegalArgumentException()
    }
*/

@Destination
@Composable
internal fun AnnouncementListScreen(
    modifier: Modifier = Modifier,
    // navigator: AnnouncementNavigator,
    // announcementsViewModel: AnnouncementsViewModel = hiltViewModel(),
) {/*
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
            onNoticeClick = navigator::openNoticeDetails,
        )
    }*/
}
/*

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
*/
