package team.aliens.dms.android.feature.main.application

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.RoundedButton
import team.aliens.dms.android.core.designsystem.ShadowDefaults
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.topPadding

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun ApplicationScreen(
    modifier: Modifier = Modifier,
    onNavigateToStudyRoomList: () -> Unit,
    onNavigateToRemains: () -> Unit,
) {
    DmsScaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(title = { Text(text = "신청") })
        },
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues),
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            Spacer(modifier = Modifier.height(DefaultVerticalSpace))
            ApplicationCard(
                title = "자습실",
                appliedTitle = "1층 나온실",
                description = "어쩌구저쩌구 네가 잔류하든 말든 나는 기숙사를 지킨다~어쩌구저쩌구 네가 잔류하든 말든 나는 기숙사를 지킨다~",
                buttonText = "자습실 신청하기",
                onButtonClick = {},
            )
            ApplicationCard(
                title = "잔류",
                appliedTitle = "금요귀사",
                description = "어쩌구저쩌구 네가 잔류하든 말든 나는 기숙사를 지킨다~어쩌구저쩌구 네가 잔류하든 말든 나는 기숙사를 지킨다~",
                buttonText = "잔류 신청하기",
                onButtonClick = {},
            )
        }
    }
}

@Composable
private fun ApplicationCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    appliedTitle: String,
    buttonText: String,
    onButtonClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .horizontalPadding(),
        shape = DmsTheme.shapes.surfaceSmall,
        colors = CardDefaults.outlinedCardColors(
            containerColor = DmsTheme.colorScheme.surface,
            contentColor = DmsTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = ShadowDefaults.SmallElevation),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .topPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    color = DmsTheme.colorScheme.onSurface,
                    style = DmsTheme.typography.title2,
                )
                RoundedButton(
                    onClick = { /*TODO*/ },
                    fillMinSize = false,
                ) {
                    Text(text = appliedTitle)
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                text = description,
                style = DmsTheme.typography.body2,
                color = DmsTheme.colorScheme.onSurface,
            )
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = onButtonClick,
            ) {
                Text(text = buttonText)
            }
        }
    }
}
