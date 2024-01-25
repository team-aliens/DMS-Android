package team.aliens.dms.android.feature.studyroom.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.layout.VerticallyFadedLazyColumn
import team.aliens.dms.android.core.designsystem.rememberToastState
import team.aliens.dms.android.core.ui.composable.FloatingNotice
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.studyroom.navigation.StudyRoomNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun StudyRoomListScreen(
    modifier: Modifier = Modifier,
    navigator: StudyRoomNavigator,
    viewModel: StudyRoomListViewModel = hiltViewModel(),
) {
    val toastState = rememberToastState()
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    DmsScaffold(modifier = modifier, topBar = {
        DmsTopAppBar(
            navigationIcon = {
                IconButton(onClick = navigator::popBackStack) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                        contentDescription = stringResource(id = R.string.top_bar_back_button),
                    )
                }
            },
            title = {
                Text(text = stringResource(id = R.string.study_room_application))
            },
        )
    }) { padValues ->
        Column(
            modifier = Modifier
                .padding(padValues)
                .fillMaxSize(),
        ) {
            uiState.studyRoomApplicationTime?.let { studyRoomApplicationTime ->
                FloatingNotice(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding(),
                    text = stringResource(
                        id = R.string.format_study_room_application_time,
                        studyRoomApplicationTime.startAt,
                        studyRoomApplicationTime.endAt,
                    ),
                )
            }
            VerticallyFadedLazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {

            }
        }
    }
}
