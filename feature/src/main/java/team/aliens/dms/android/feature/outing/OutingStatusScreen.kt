package team.aliens.dms.android.feature.outing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import org.threeten.bp.LocalDate
import team.aliens.dms.android.core.designsystem.ButtonDefaults
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.ShadowDefaults
import team.aliens.dms.android.core.designsystem.TextButton
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.outing.navigation.OutingNavigator
import team.aliens.dms.android.shared.date.util.now
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun OutingStatusScreen(
    navigator: OutingNavigator,
    viewModel: OutingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            DmsTopAppBar(
                title = { Text(text = stringResource(id = R.string.outing_application)) },
                navigationIcon = {
                    IconButton(onClick = navigator::navigateUp) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(id = R.string.top_bar_back_button),
                        )
                    }
                },
            )
        },
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .startPadding()
                    .topPadding(),
                text = stringResource(id = R.string.outing_recent_application),
                color = DmsTheme.colorScheme.icon,
            )
            OutingInformationCard(
                outingId = UUID.randomUUID(),
                title = "어쩌구",
                date = now.toLocalDate(),
                time = "22:40~25:50",
                companionNames = listOf("박준수", "벅즁수", "박박수", "준박수"),
                reason = "배고파서배고파서배고파서배고파서배고파서배고파서배고파서배고파서배고파서배고파서배고파서배고파서배고파서",
            ) {}
        }
    }
}

@Composable
private fun OutingInformationCard(
    modifier: Modifier = Modifier,
    outingId: UUID,
    title: String,
    date: LocalDate,
    time: String,
    companionNames: List<String>,
    reason: String?,
    onCancelApplication: (id: UUID) -> Unit,
) {

    Card(
        modifier = modifier
            .horizontalPadding()
            .verticalPadding(PaddingDefaults.ExtraSmall),
        shape = DmsTheme.shapes.surfaceMedium,
        colors = CardDefaults.cardColors(
            containerColor = DmsTheme.colorScheme.surface,
            contentColor = DmsTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = ShadowDefaults.SmallElevation),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
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
                // TODO
                Text(
                    text = date.toString(),
                    style = DmsTheme.typography.caption,
                    color = DmsTheme.colorScheme.primary,
                )
                TextButton(
                    onClick = { onCancelApplication(outingId) },
                    colors = ButtonDefaults.textErrorButtonColors(),
                ) {
                    Text(text = stringResource(id = R.string.outing_cancel_application))
                }
            }
            Text(
                modifier = Modifier.startPadding(),
                text = title,
                style = DmsTheme.typography.title3,
                fontWeight = FontWeight.Bold,
            )
            Text(
                modifier = Modifier.startPadding(),
                text = time,
                style = DmsTheme.typography.body2,
            )
            if (companionNames.isNotEmpty()) {
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace),
                ) {
                    item {
                        Text(
                            modifier = Modifier.startPadding(),
                            text = stringResource(id = R.string.outing_companion_names),
                            style = DmsTheme.typography.body2,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    itemsIndexed(companionNames) { index, name ->
                        Text(
                            text = if (index == companionNames.lastIndex) {
                                name
                            } else {
                                "$name, "
                            },
                            style = DmsTheme.typography.body2,
                        )
                    }
                }
            }
            if (reason != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding()
                        .bottomPadding(),
                    colors = CardDefaults.cardColors(
                        containerColor = DmsTheme.colorScheme.background,
                        contentColor = DmsTheme.colorScheme.onBackground,
                    ),
                ) {
                    Text(
                        modifier = Modifier
                            .horizontalPadding()
                            .verticalPadding(),
                        text = reason,
                    )
                }
            }
        }
    }
}
