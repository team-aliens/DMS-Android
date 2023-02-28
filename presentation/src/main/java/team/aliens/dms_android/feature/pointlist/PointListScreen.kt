package team.aliens.dms_android.feature.pointlist

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.button.DormOutlineLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Headline2
import team.aliens.dms_android.util.TopBar
import team.aliens.dms_android.viewmodel.mypage.MyPageViewModel
import team.aliens.domain.entity.mypage.PointListEntity
import team.aliens.domain.enums.PointType
import team.aliens.presentation.R

@Composable
fun PointListScreen(
    navController: NavController,
    myPageViewModel: MyPageViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        myPageViewModel.fetchPointList(PointType.ALL)
    }

    val point = remember { mutableStateListOf<PointListEntity.PointValue>() }

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
                    point.addAll(it.pointListEntity.pointValue)
                    totalPoint = it.pointListEntity.totalPoint
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
            .background(DormColor.Gray200),
    ) {

        TopBar(
            title = stringResource(
                id = R.string.CheckPoint,
            ),
        ) {
            navController.popBackStack()
        }

        // point filter
        DialogBox(myPageViewModel)
        
        // points
        PointListValue(
            totalPoint = totalPoint,
            point = point,
        )
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun DialogBox(
    myPageViewModel: MyPageViewModel,
) {
    var selected by remember { mutableStateOf(PointButtonType.ALL) }

    Row(
        modifier = Modifier.padding(start = 24.dp, top = 50.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        PointTypeButton(
            type = PointButtonType.ALL,
            isSelected = selected == PointButtonType.ALL,
        ) { type ->
            selected = type
            myPageViewModel.fetchPointList(PointType.ALL)
        }
        PointTypeButton(
            type = PointButtonType.PLUS,
            isSelected = selected == PointButtonType.PLUS,
        ) { type ->
            selected = type
            myPageViewModel.fetchPointList(PointType.BONUS)
        }
        PointTypeButton(
            type = PointButtonType.MINUS,
            isSelected = selected == PointButtonType.MINUS,
        ) { type ->
            selected = type
            myPageViewModel.fetchPointList(PointType.MINUS)
        }
    }
}

private enum class PointButtonType(
    val title: String,
) {
    ALL("전체"), PLUS("상점"), MINUS("벌점"),
}

@Stable
private val PointTypeButtonSize = DpSize(
    width = 80.dp,
    height = 44.dp,
)

@Composable
private fun PointTypeButton(
    type: PointButtonType,
    isSelected: Boolean,
    onClicked: (PointButtonType) -> Unit,
) {
    when (isSelected) {
        true -> DormContainedLargeButton(
            modifier = Modifier.size(PointTypeButtonSize),
            text = type.title,
            color = DormButtonColor.Blue,
            enabled = true,
        ) {
            onClicked(type)
        }
        false -> DormOutlineLargeButton(
            modifier = Modifier.size(PointTypeButtonSize),
            text = type.title,
            color = DormButtonColor.Gray,
            enabled = true,
        ) {
            onClicked(type)
        }
    }
}

@Composable
fun PointListValue(
    totalPoint: Int,
    point: MutableList<PointListEntity.PointValue>,
) {
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(44.dp))
            Headline2(text = " ${totalPoint}점")
        }
        Spacer(modifier = Modifier.height(40.dp))
        PointList(points = point)
    }
}
