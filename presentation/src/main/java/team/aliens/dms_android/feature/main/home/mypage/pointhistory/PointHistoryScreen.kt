package team.aliens.dms_android.feature.main.home.mypage.pointhistory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedDefaultButton
import team.aliens.design_system.button.DormOutlinedDefaultButton
import team.aliens.design_system.layout.VerticallyFadedLazyColumn
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body4
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.Headline2
import team.aliens.design_system.typography.OverLine
import team.aliens.dms_android.util.TopBar
import team.aliens.domain.model._common.PointType
import team.aliens.domain.model.point.Point
import team.aliens.presentation.R

@Composable
internal fun PointHistoryScreen(
    onPrevious: () -> Unit,
    pointHistoryViewModel: PointHistoryViewModel = hiltViewModel(),
) {
    val uiState by pointHistoryViewModel.stateFlow.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .background(DormTheme.colors.background)
            .fillMaxSize(),
    ) {
        TopBar(
            title = stringResource(R.string.my_page_check_point_history),
            onPrevious = onPrevious,
        )
        Spacer(Modifier.height(18.dp))
        PointFilter(
            selectedType = uiState.selectedType,
            onFilterChange = { pointType: PointType ->
                pointHistoryViewModel.postIntent(PointHistoryIntent.UpdateFilter(pointType))
            },
        )
        Spacer(Modifier.height(18.dp))
        Headline2(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = String.format(
                stringResource(R.string.my_page_points_of),
                uiState.totalPoints,
            ),
        )
        Points(
            modifier = Modifier.weight(1f),
            points = uiState.points,
        )
    }
}

@Stable
private val filterButtons = listOf(
    PointTypeRadioButton(PointType.ALL),
    PointTypeRadioButton(PointType.BONUS),
    PointTypeRadioButton(PointType.MINUS),
)

@Composable
private fun PointFilter(
    selectedType: PointType,
    onFilterChange: (PointType) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        PointTypeRadioGroup(
            selectedType = selectedType,
            onFilterChange = onFilterChange,
        )
    }
}

@JvmInline
private value class PointTypeRadioButton(
    val pointType: PointType,
)

private val PointTypeRadioButton.text: String
    @Composable inline get() = stringResource(
        when (this.pointType) {
            PointType.ALL -> R.string.all_point
            PointType.BONUS -> R.string.bonus_point
            PointType.MINUS -> R.string.minus_point
        },
    )


@Composable
private fun PointTypeRadioGroup(
    selectedType: PointType,
    onFilterChange: (PointType) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        filterButtons.forEach { button ->
            if (selectedType == button.pointType) {
                DormContainedDefaultButton(
                    text = button.text,
                    color = DormButtonColor.Blue,
                    onClick = {
                        /* explicit blank */
                    },
                )
            } else {
                DormOutlinedDefaultButton(
                    text = button.text,
                    color = DormButtonColor.Gray,
                ) {
                    onFilterChange(button.pointType)
                }
            }
        }
    }
}

@Composable
private fun Points(
    modifier: Modifier = Modifier,
    points: List<Point>,
) {
    VerticallyFadedLazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(points) { point ->
            PointInformation(
                modifier = Modifier.padding(horizontal = 16.dp),
                point = point,
            )
        }
    }
}

@Composable
private fun PointInformation(
    modifier: Modifier = Modifier,
    point: Point,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .dormShadow(DormTheme.colors.primaryVariant)
            .clip(RoundedCornerShape(10.dp))
            .background(DormTheme.colors.surface)
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val (_, month, date) = point.date.split("-")

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            OverLine(
                text = String.format(
                    stringResource(R.string.my_page_date_of),
                    month.toInt(),
                    date.toInt(),
                ),
                color = DormTheme.colors.primaryVariant,
            )

            Body5(
                text = point.name,
            )
        }
        when (point.type) {
            PointType.BONUS -> {
                Body4(
                    text = "+" + point.score,
                    color = DormTheme.colors.primary,
                )
            }

            PointType.MINUS -> {
                Body4(
                    text = "-" + point.score,
                    color = DormTheme.colors.error,
                )
            }

            else -> throw IllegalStateException()
        }
    }
}
