package team.aliens.dms.android.feature.point

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ButtonDefaults
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.OutlinedButton
import team.aliens.dms.android.core.designsystem.ShadowDefaults
import team.aliens.dms.android.core.designsystem.VerticallyFadedLazyColumn
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.data.point.model.Point
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.point.navigation.PointHistoryNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun PointHistoryScreen(
    modifier: Modifier = Modifier,
    navigator: PointHistoryNavigator,
    pointType: PointType,
) {
    val viewModel: PointHistoryViewModel = hiltViewModel()
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchPoints(pointType)
    }

    DmsScaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = { Text(text = stringResource(id = R.string.point_history)) },
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
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            PointFilter(
                modifier = Modifier
                    .fillMaxWidth()
                    .topPadding(),
                selectedType = uiState.selectedPointType,
                onPointTypeSelected = { type ->
                    viewModel.postIntent(PointHistoryIntent.UpdateSelectedPointType(type))
                },
            )
            Text(
                modifier = Modifier.startPadding(),
                text = stringResource(
                    id = R.string.format_point,
                    uiState.totalPoints ?: 0, // TODO
                ),
                style = DmsTheme.typography.headline1,
                color = DmsTheme.colorScheme.surfaceVariant,
            )
            PointList(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                points = uiState.points,
            )
        }
    }
}

@Composable
private fun PointFilter(
    modifier: Modifier = Modifier,
    selectedType: PointType,
    onPointTypeSelected: (PointType) -> Unit,
) {
    Row(
        modifier = modifier.horizontalPadding(),
        horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace),
    ) {
        PointType.entries.forEach { type ->
            val selected = selectedType == type
            if (selected) {
                ContainedButton(
                    onClick = { },
                    fillMinSize = false,
                ) {
                    Text(text = type.text)
                }
            } else {
                OutlinedButton(
                    onClick = { onPointTypeSelected(type) },
                    colors = ButtonDefaults.outlinedGrayButtonColors(),
                    fillMinSize = false,
                ) {
                    Text(text = type.text)
                }
            }
        }
    }
}

private val PointType.text: String
    @Composable inline get() = stringResource(
        id = when (this) {
            PointType.ALL -> R.string.all_point
            PointType.BONUS -> R.string.bonus_point
            PointType.MINUS -> R.string.minus_point
        },
    )

@Composable
private fun PointList(
    modifier: Modifier = Modifier,
    points: List<Point>,
) {
    VerticallyFadedLazyColumn(
        modifier = modifier,
    ) {
        items(points) { point ->
            PointCard(
                modifier = Modifier.fillMaxWidth(),
                point = point,
            )
        }
    }
}

@Composable
private fun PointCard(
    modifier: Modifier = Modifier,
    point: Point,
) {
    Card(
        modifier = modifier
            .horizontalPadding()
            .verticalPadding(PaddingDefaults.ExtraSmall),
        shape = DmsTheme.shapes.surfaceSmall,
        colors = CardDefaults.cardColors(
            containerColor = DmsTheme.colorScheme.surface,
            contentColor = DmsTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = ShadowDefaults.SmallElevation),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .topPadding(),
                text = "${point.date}",
                color = DmsTheme.colorScheme.onSurfaceVariant,
                style = DmsTheme.typography.caption,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = point.name,
                    color = DmsTheme.colorScheme.onSurface,
                    style = DmsTheme.typography.body2,
                )
                Text(
                    text = "${
                        if (point.type == PointType.MINUS) {
                            point.score * -1
                        } else {
                            point.score
                        }
                    }",
                    color = when (point.type) {
                        PointType.ALL -> throw IllegalStateException()
                        PointType.BONUS -> DmsTheme.colorScheme.primary
                        PointType.MINUS -> DmsTheme.colorScheme.error
                    },
                    style = DmsTheme.typography.body2,
                )
            }
        }
    }
}
