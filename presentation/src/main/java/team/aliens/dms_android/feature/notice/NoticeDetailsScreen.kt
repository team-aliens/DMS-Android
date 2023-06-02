package team.aliens.dms_android.feature.notice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import java.util.UUID
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.Caption
import team.aliens.design_system.typography.Title3
import team.aliens.dms_android.util.TopBar
import team.aliens.presentation.R

@Composable
internal fun NoticeDetailsScreen(
    navController: NavController,
    noticeId: String,
    noticesViewModel: NoticesViewModel = hiltViewModel(),
) {

    val uiState = noticesViewModel.uiState.collectAsStateWithLifecycle()

    val noticeDetails = uiState.value.noticeDetails

    LaunchedEffect(Unit) {
        noticesViewModel.onEvent(NoticesUiEvent.FetchNoticeDetails(UUID.fromString(noticeId)))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DormTheme.colors.background,
            ),
    ) {
        TopBar(
            title = stringResource(R.string.Announcement),
        ) {
            navController.popBackStack()
        }
        Column(
            modifier = Modifier
                .padding(
                    top = 40.dp,
                    start = 16.dp,
                    end = 16.dp,
                )
                .fillMaxHeight(),
            horizontalAlignment = Alignment.Start,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Title3(text = noticeDetails.title)
                Caption(
                    text = noticeDetails.createdAt.toNoticeDate(),
                    color = DormTheme.colors.primaryVariant,
                )
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = DormTheme.colors.secondaryVariant,
                )
            }
            Body5(
                modifier = Modifier
                    .verticalScroll(
                        rememberScrollState(),
                    )
                    .padding(
                        top = 8.dp,
                    ),
                text = noticeDetails.content!!,
            )
        }
    }
}
