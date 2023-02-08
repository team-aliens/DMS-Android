package team.aliens.dms_android.feature.mypage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.dms_android.R
import kotlinx.coroutines.CoroutineScope
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.Caption
import team.aliens.design_system.typography.Headline3
import team.aliens.design_system.typography.SubTitle1
import team.aliens.dms_android.component.changeBottomSheetState
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.dms_android.viewmodel.mypage.MyPageViewModel
import team.aliens.domain.enums.BottomSheetType

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyPageScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
) {
    val myPageViewModel: MyPageViewModel = hiltViewModel()

    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    val unAuthorizedComment = stringResource(id = R.string.LoginUnAuthorized)
    val forbiddenException = stringResource(id = R.string.LoginNotFound)
    val tooManyRequestComment = stringResource(id = R.string.TooManyRequest)
    val serverException = stringResource(id = R.string.ServerException)
    val unKnownException = stringResource(id = R.string.UnKnownException)

    val state = myPageViewModel.state.collectAsState().value.myPageEntity

    LaunchedEffect(Unit) {
        myPageViewModel.myPageViewEffect.collect {
            when (it) {
                is MyPageViewModel.Event.NullPointException -> {
                    scaffoldState.snackbarHostState.showSnackbar("null")
                    Log.d("123", "2")
                }
                is MyPageViewModel.Event.UnAuthorizedTokenException -> {
                    scaffoldState.snackbarHostState.showSnackbar(unAuthorizedComment)
                    Log.d("123", "3")
                }
                is MyPageViewModel.Event.CannotConnectException -> {
                    scaffoldState.snackbarHostState.showSnackbar(forbiddenException)
                    Log.d("123", "4")
                }
                is MyPageViewModel.Event.TooManyRequestException -> {
                    scaffoldState.snackbarHostState.showSnackbar(tooManyRequestComment)
                    Log.d("123", "5")
                }
                is MyPageViewModel.Event.InternalServerException -> {
                    scaffoldState.snackbarHostState.showSnackbar(serverException)
                    Log.d("123", "6")
                }
                is MyPageViewModel.Event.UnknownException -> {
                    scaffoldState.snackbarHostState.showSnackbar(unKnownException)
                    Log.d("123", "7")
                }
            }
        }
    }

    LaunchedEffect(key1 = myPageViewModel) {
        myPageViewModel.fetchMyPage()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormColor.Gray200),
    ) {
        UserInformation(bottomSheetState, scope, state)
        WarningPoint(state)
        PointBox(state)
        MyPageBlock(navController)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserInformation(
    state: ModalBottomSheetState,
    scope: CoroutineScope,
    state2: MyPageEntity,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 55.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.padding(start = 24.dp)) {
            SubTitle1(text = "${state2.gcn} ${state2.name}")
            Spacer(modifier = Modifier.height(10.dp))
            Body5(text = state2.schoolName)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.BottomEnd,
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clickable {
                            changeBottomSheetState(
                                coroutineScope = scope,
                                bottomSheetState = state,
                                bottomSheetType = BottomSheetType.Show,
                            )
                        }
                        .size(80.dp)
                        .clip(CircleShape),
                    model = state2.profileImageUrl,
                    contentDescription = stringResource(id = R.string.AddImageButton),
                )
            }
            Image(
                modifier = Modifier
                    .padding(start = 68.dp)
                    .size(16.dp),
                painter = painterResource(id = R.drawable.addplusimage),
                contentDescription = stringResource(id = R.string.AddImageButton),
            )
        }
    }
}

@Composable
fun WarningPoint(
    state2: MyPageEntity,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(start = 24.dp, end = 24.dp, top = 20.dp)
            .clip(RoundedCornerShape(25))
            .border(color = DormColor.Lighten200, width = 1.dp, shape = RoundedCornerShape(25))
            .background(DormColor.Lighten200),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            Spacer(
                modifier = Modifier.width(20.dp),
            )
            Caption(text = state2.phrase)
        }
    }
}

@Composable
fun PointBox(
    state2: MyPageEntity,
) {
    Row() {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(start = 24.dp, top = 20.dp, end = 5.dp)
                .height(90.dp)
                .clip(RoundedCornerShape(25))
                .border(color = DormColor.DormPrimary, width = 1.dp, shape = RoundedCornerShape(25))
                .background(color = DormColor.Lighten200),
            contentAlignment = Alignment.TopStart,
        ) {
            Column(modifier = Modifier
                .padding(start = 14.dp)
                .fillMaxWidth()) {
                Spacer(modifier = Modifier.height(10.dp))
                Body5(
                    text = stringResource(id = R.string.PlusPoint),
                    color = DormColor.Darken200,
                )
                Box(contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 24.dp, bottom = 18.dp)) {
                    Headline3(
                        text = state2.bonusPoint.toString(),
                        color = DormColor.Darken200,
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, top = 20.dp, end = 24.dp)
                .height(90.dp)
                .clip(RoundedCornerShape(25))
                .border(
                    color = DormColor.Error,
                    width = 1.dp,
                    shape = RoundedCornerShape(25),
                )
                .background(DormColor.LightenError),
            contentAlignment = Alignment.TopStart,
        ) {
            Column(modifier = Modifier
                .padding(start = 14.dp)
                .fillMaxSize()) {
                Spacer(modifier = Modifier.height(10.dp))
                Body5(
                    text = stringResource(id = R.string.MinusPoint),
                    color = DormColor.Error,
                )
                Box(contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 24.dp, bottom = 18.dp)) {
                    Headline3(
                        text = state2.minusPoint.toString(),
                        color = DormColor.Error,
                    )
                }
            }
        }
    }
}

@Composable
fun MyPageBlock(
    navController: NavController,
) {
    Column() {
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)
                .height(110.dp)
                .clip(RoundedCornerShape(20))
                .border(color = DormColor.Gray100, width = 1.dp, shape = RoundedCornerShape(20))
                .background(DormColor.Gray100),
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .clickable {
                    navController.navigate(NavigationRoute.PointList)
                }, contentAlignment = Alignment.CenterStart) {
                Row() {
                    Spacer(modifier = Modifier.width(20.dp))
                    Body5(text = stringResource(id = R.string.CheckPointList))
                }
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 15.dp, end = 15.dp)
                .background(DormColor.Gray300))
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart) {
                Row() {
                    Spacer(modifier = Modifier.width(20.dp))
                    Body5(text = stringResource(id = R.string.ChangePassword))
                }
            }
        }

        Column() {
            Spacer(modifier = Modifier.height(15.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(start = 24.dp, end = 24.dp)
                .clip(RoundedCornerShape(20))
                .border(color = DormColor.Gray100, width = 1.dp, shape = RoundedCornerShape(20))
                .background(DormColor.Gray100), contentAlignment = Alignment.CenterStart) {
                Row() {
                    Spacer(modifier = Modifier.width(20.dp))
                    Body5(
                        text = stringResource(id = R.string.Logout),
                        color = DormColor.Error,
                    )
                }
            }
        }
    }
}