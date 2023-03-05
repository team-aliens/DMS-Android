package team.aliens.dms_android.feature.notice

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.Caption
import team.aliens.design_system.typography.Title3
import team.aliens.dms_android.util.TopBar
import team.aliens.dms_android.viewmodel.notice.NoticeViewModel
import team.aliens.presentation.R

@SuppressLint("RememberReturnType")
@Composable
fun NoticeDetailScreen(
    navController: NavController,
    noticeId: String,
    noticeViewModel: NoticeViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        noticeViewModel.fetchNoticeDetail(noticeId)
    }

    val noticeDetailState = noticeViewModel.noticeDetailViewEffect.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DormTheme.colors.background,
            ),
    ) {

        TopBar(
            title = stringResource(
                id = R.string.Announcement,
            ),
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

                // title
                Title3(
                    text = noticeDetailState.value.title,
                )

                // date
                Caption(
                    text = noticeDetailState.value.createAt,
                    color = DormTheme.colors.primaryVariant,
                )

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = DormTheme.colors.secondaryVariant,
                )
            }

            // Content
            Body5(
                modifier = Modifier
                    .verticalScroll(
                        rememberScrollState(),
                    )
                    .padding(
                        top = 8.dp,
                    ),
                text = noticeDetailState.value.content,
            )
        }
    }
}
