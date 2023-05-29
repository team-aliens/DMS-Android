package team.aliens.dms_android.feature.mypage.pointhistory

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

/*
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedDefaultButton
import team.aliens.design_system.button.DormOutlinedDefaultButton
import team.aliens.design_system.extension.Space
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Headline2
import team.aliens.dms_android.util.TopBar
import team.aliens.dms_android.feature.mypage.MyPageViewModel
import team.aliens.domain.model._common.PointType
import team.aliens.domain.model.point.FetchPointsOutput
import team.aliens.presentation.R

@Composable
fun PointHistoryScreen(
    navController: NavController,
    myPageViewModel: MyPageViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        myPageViewModel.fetchPointList(PointType.ALL)
    }

    var selectedType by remember {
        mutableStateOf(PointType.ALL)
    }

    LaunchedEffect(Unit) {
        myPageViewModel.pointTypeEvent.collect {
            if (it is MyPageViewModel.Event.PointTypeEvent) {
                selectedType = it.pointType
            }
        }
    }


    val point = remember { mutableStateListOf<FetchPointsOutput.PointInformation>() }

    var totalPoint by remember { mutableStateOf(0) }

    val toast = rememberToast()

    val badRequestComment = stringResource(id = R.string.BadRequest)
    val unAuthorizedComment = stringResource(id = R.string.LoginUnauthorized)
    val forbiddenException = stringResource(id = R.string.LoginNotFound)
    val tooManyRequestComment = stringResource(id = R.string.TooManyRequest)
    val serverException = stringResource(id = R.string.ServerException)
    val unKnownException = stringResource(id = R.string.UnKnownException)
    LaunchedEffect(Unit) {
        myPageViewModel.pointViewEffect.collect {
            when (it) {
                is MyPageViewModel.Event.FetchPointList -> {
                    point.clear()
                    point.addAll(it.fetchPointsOutput.points)
                    totalPoint = it.fetchPointsOutput.totalPoint
                }

                is MyPageViewModel.Event.BadRequestException -> {
                    toast(badRequestComment)
                }

                is MyPageViewModel.Event.UnAuthorizedTokenException -> {
                    toast(unAuthorizedComment)
                }

                is MyPageViewModel.Event.CannotConnectException -> {
                    toast(forbiddenException)
                }

                is MyPageViewModel.Event.TooManyRequestException -> {
                    toast(tooManyRequestComment)
                }

                is MyPageViewModel.Event.InternalServerException -> {
                    toast(serverException)
                }

                is MyPageViewModel.Event.UnknownException -> {
                    toast(unKnownException)
                }

                is MyPageViewModel.Event.NullPointException -> {
                    toast("null")
                }

                else -> {
                    toast(unKnownException)
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DormTheme.colors.background,
            ),
    ) {

        TopBar(
            title = stringResource(
                id = R.string.CheckPoint,
            ),
        ) {
            navController.popBackStack()
        }

        // point filter
        RadioButton(
            myPageViewModel = myPageViewModel,
            selectedType = selectedType,
        )

        // points
        PointListValue(
            totalPoint = totalPoint,
            point = point,
        )
    }
}

class PointButton(
    val type: PointType,
)

@Stable
private val pointListButtons = arrayListOf(
    PointButton(PointType.ALL),
    PointButton(PointType.BONUS),
    PointButton(PointType.MINUS),
)

@SuppressLint("RememberReturnType")
@Composable
fun RadioButton(
    myPageViewModel: MyPageViewModel,
    selectedType: PointType,
) {
    Row(
        modifier = Modifier
            .padding(start = 24.dp, top = 50.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        PointTypeRadioGroup(
            myPageViewModel = myPageViewModel,
            selectedType = selectedType,
        )
    }
}


@Composable
private fun PointTypeRadioGroup(
    myPageViewModel: MyPageViewModel,
    selectedType: PointType,
) {
    pointListButtons.forEach { button ->
        if (selectedType == button.type) {
            DormContainedDefaultButton(
                modifier = Modifier,
                text = stringResource(repeatedPointButtonText(button.type)),
                color = DormButtonColor.Blue,
            ) {
                myPageViewModel.fetchPointList(button.type)
            }
        } else {
            DormOutlinedDefaultButton(
                modifier = Modifier,
                text = stringResource(repeatedPointButtonText(button.type)),
                color = DormButtonColor.Gray,
            ) {
                myPageViewModel.fetchPointList(button.type)
            }
        }
    }
}


fun repeatedPointButtonText(buttonType: PointType): Int {
    return when (buttonType) {
        PointType.ALL -> R.string.all_point
        PointType.BONUS -> R.string.bonus_point
        PointType.MINUS -> R.string.minus_point
    }
}

@Composable
fun PointListValue(
    totalPoint: Int,
    point: List<FetchPointsOutput.PointInformation>,
) {
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp)
        ) {
            Space(space = 44.dp)
            Headline2(text = " ${totalPoint}점")
        }
        Space(space = 40.dp)
        PointList(points = point)
    }
}*/

@Composable
internal fun PointHistoryScreen(
    onBackToMyPage: () -> Unit,
    pointHistoryViewModel: PointHistoryViewModel = hiltViewModel(),
) {
    val uiState by pointHistoryViewModel.uiState.collectAsStateWithLifecycle()

    val onFetchPoints = { pointType: PointType ->
        pointHistoryViewModel.onEvent(PointHistoryUiEvent.FetchPoints(pointType))
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopBar(
            title = stringResource(R.string.my_page_check_point_history),
            onPrevious = onBackToMyPage,
        )
        Spacer(Modifier.height(36.dp))
        PointFilter(
            selectedType = uiState.selectedType,
            onFilterChange = onFetchPoints,
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
            buttons = filterButtons,
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
    buttons: List<PointTypeRadioButton>,
    selectedType: PointType,
    onFilterChange: (PointType) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        buttons.forEach { button ->
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
    LazyColumn(
        modifier = Modifier
            .weight(1f)
            .padding(
                horizontal = 16.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            if (points.isNotEmpty())
                Spacer(Modifier.height(54.dp))
        }

        repeat(10) {// todo
            items(points) { point ->
                PointInformation(point)
            }
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
