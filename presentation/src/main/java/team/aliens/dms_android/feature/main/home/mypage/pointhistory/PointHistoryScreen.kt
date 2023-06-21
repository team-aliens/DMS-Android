package team.aliens.dms_android.feature.main.home.mypage.pointhistory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
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
import team.aliens.design_system.modifier.dormGradientBackground
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body4
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.Headline2
import team.aliens.design_system.typography.OverLine
import team.aliens.dms_android.component.listFadeBrush
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
        Spacer(Modifier.height(36.dp))
        PointFilter(
            selectedType = uiState.selectedType,
            onFilterChange = { pointType: PointType ->
                pointHistoryViewModel.postIntent(PointHistoryIntent.FetchPoints(pointType))
            },
        )
        Spacer(Modifier.height(36.dp))
        Headline2(
            modifier = Modifier.padding(
                horizontal = 16.dp,
            ),
            text = String.format(
                stringResource(R.string.my_page_points_of),
                uiState.totalPoint,
            ),
        )
        Points(
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
            .padding(
                horizontal = 16.dp,
            ),
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
    @Composable get() = stringResource(
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
private fun ColumnScope.Points(
    points: List<Point>,
) {
    val pointsNotEmpty = points.isNotEmpty()

    Box(
        modifier = Modifier.weight(1f),
        contentAlignment = Alignment.TopCenter,
    ) {
        if (pointsNotEmpty) {
            LazyColumn(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                item {
                    Spacer(Modifier.height(54.dp))
                }
                items(points) { point ->
                    PointInformation(point)
                }
                item {
                    Spacer(Modifier.height(54.dp))
                }
            }

            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .dormGradientBackground(listFadeBrush),
            )
        } else {
            // todo discuss when 'points' is empty
            // and remove these codes
            // ㅋㅋㅋ 이거 짱 귀여운데 리뷰어 분들 보여드리고 지울게요

            /*var isVisible by remember {
                mutableStateOf(false)
            }

            @Suppress("CoroutineCreationDuringComposition")
            rememberCoroutineScope().launch {
                delay(5000L)
                isVisible = true
            }

            this@Points.AnimatedVisibility(
                visible = isVisible,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        modifier = Modifier
                            .size(32.dp),
                        painter = painterResource(R.drawable.img_dummy),
                        contentDescription = "whaaaaaat?!??!?",
                    )
                    OverLine(
                        text = "짠! 야옹맨이 너의 점수를 가져갔으니 안심하라구!",
                    )
                }
            }*/
        }
    }
}

@Composable
private fun PointInformation(
    point: Point,
) {
    Box(
        modifier = Modifier
            .dormShadow(
                color = DormTheme.colors.secondaryVariant,
                offsetY = 1.dp,
            )
            .clip(
                RoundedCornerShape(10.dp),
            )
            .background(
                color = DormTheme.colors.surface,
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                vertical = 14.dp,
                horizontal = 16.dp,
            ),
        contentAlignment = Alignment.CenterStart,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            val (_, month, date) = point.date.split("-")

            OverLine(
                text = String.format(
                    stringResource(R.string.my_page_date_of),
                    month.toInt(),
                    date.toInt(),
                ),
                color = DormTheme.colors.primaryVariant,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Body5(
                    text = point.name,
                )

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
    }
}
