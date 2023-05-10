package team.aliens.dms_android.feature.pointlist

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.button.DormOutlineLargeButton
import team.aliens.design_system.extension.Space
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Headline2
import team.aliens.dms_android.util.TopBar
import team.aliens.dms_android.feature.mypage.MyPageViewModel
import team.aliens.domain._model._common.PointType
import team.aliens.domain._model.point.FetchPointsOutput
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
        PointRadioButton(myPageViewModel, selectedType)

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


@Stable
private val PointTypeButtonSize = DpSize(
    width = 80.dp,
    height = 44.dp,
)

@SuppressLint("RememberReturnType")
@Composable
fun PointRadioButton(
    myPageViewModel: MyPageViewModel,
    selectedType: PointType
) {
    Row(
        Modifier.padding(start = 24.dp, top = 50.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        PointTypeButtonGroup(
            myPageViewModel = myPageViewModel,
            selectedType = selectedType,
        )
    }
}


@Composable
private fun PointTypeButtonGroup(
    myPageViewModel: MyPageViewModel,
    selectedType: PointType,
) {
    pointListButtons.forEach { button ->
        if (selectedType == button.type) {
            DormContainedLargeButton(
                modifier = Modifier
                    .size(PointTypeButtonSize),
                text = stringResource(
                    id = when (button.type) {
                        PointType.ALL -> R.string.all_point
                        PointType.BONUS -> R.string.bonus_point
                        PointType.MINUS -> R.string.minus_point
                    }
                ),
                color = DormButtonColor.Blue,
            ) {
                myPageViewModel.fetchPointList(button.type)
            }
        } else {
            DormOutlineLargeButton(
                modifier = Modifier
                    .size(PointTypeButtonSize),
                text = stringResource(
                    id = when (button.type) {
                        PointType.ALL -> R.string.all_point
                        PointType.BONUS -> R.string.bonus_point
                        PointType.MINUS -> R.string.minus_point
                    }
                ),
                color = DormButtonColor.Gray,
            ) {
                myPageViewModel.fetchPointList(button.type)
            }
        }
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
}
