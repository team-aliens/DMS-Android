package team.aliens.dms.android.feature.point

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.data.point.model.Point
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.designsystem.layout.VerticallyFadedLazyColumn
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.point.navigation.PointHistoryNavigator

@Destination
@Composable
internal fun PointHistoryScreen(
    modifier: Modifier = Modifier,
    navigator: PointHistoryNavigator,
    // pointHistoryViewModel: PointHistoryViewModel = hiltViewModel(),
) {/*
    val uiState by pointHistoryViewModel.stateFlow.collectAsStateWithLifecycle()
    val selectedType = uiState.selectedType

    Column(
        modifier = modifier
            .background(DormTheme.colors.background)
            .fillMaxSize(),
    ) {
        TopBar(
            title = stringResource(R.string.my_page_check_point_history),
            onPrevious = navigator::popBackStack,
        )
        Spacer(Modifier.height(18.dp))
        PointFilter(
            selectedType = selectedType,
            onFilterChange = { pointType: PointType ->
                pointHistoryViewModel.postIntent(PointHistoryIntent.UpdateFilter(pointType))
            },
        )
        Spacer(Modifier.height(18.dp))
        Headline2(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = String.format(
                stringResource(R.string.my_page_points_of),
                when (selectedType) {
                    PointType.ALL -> uiState.totalAllPoints
                    PointType.BONUS -> uiState.totalBonusPoints
                    PointType.MINUS -> uiState.totalMinusPoints
                },
            ),
        )
        Points(
            modifier = Modifier.weight(1f),
            points = when (selectedType) {
                PointType.ALL -> uiState.allPoints
                PointType.BONUS -> uiState.bonusPoints
                PointType.MINUS -> uiState.minusPoints
            },
        )
    }*/
}/*

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
                        *//* explicit blank *//*
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
) {*//*
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

            else -> {
                // explicit blank
            }
        }
    }*//*
}
*/