package team.aliens.dms_android.feature.mypage

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.dialog.DormCustomDialog
import team.aliens.design_system.dialog.DormDoubleButtonDialog
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.Caption
import team.aliens.design_system.typography.Headline3
import team.aliens.design_system.typography.SubTitle1
import team.aliens.dms_android.component.changeBottomSheetState
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.dms_android.viewmodel.mypage.MyPageViewModel
import team.aliens.domain.enums.BottomSheetType
import team.aliens.presentation.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyPageScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    myPageViewModel: MyPageViewModel = hiltViewModel(),
) {

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val myPageState = myPageViewModel.state.collectAsState().value.myPageEntity


    var signOutDialogState by remember {
        mutableStateOf(false)
    }

    if (signOutDialogState) {
        DormCustomDialog(
            onDismissRequest = {
                /* explicit blank */
            },
        ) {
            DormDoubleButtonDialog(
                content = "정말 로그아웃 하시겠습니까?",
                mainBtnText = "확인",
                subBtnText = "취소",
                onMainBtnClick = {

                    navController.navigate(NavigationRoute.Login) {
                        popUpTo(navController.currentDestination?.route!!) {
                            inclusive = true
                        }
                    }

                    myPageViewModel.signOut()
                },
                onSubBtnClick = {
                    signOutDialogState = false
                },
            )
        }
    }

    val onSignOutButtonClick = {
        signOutDialogState = true
    }

    LaunchedEffect(Unit) {
        myPageViewModel.myPageViewEffect.collect { event ->
            scaffoldState.snackbarHostState.showSnackbar(
                message = getStringFromEvent(
                    context = context,
                    event = event,
                ),
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormColor.Gray200)
            .padding(
                vertical = 60.dp,
                horizontal = 16.dp,
            ),
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Column {

                // 사용자 정보
                SubTitle1(
                    text = "${myPageState.gcn} ${myPageState.name}",
                )

                Spacer(
                    modifier = Modifier.height(10.dp),
                )

                // 학교 정보
                Body5(
                    text = myPageState.schoolName,
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd,
            ) {

                // 사용자 프로필
                AsyncImage(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .clickable {
                            changeBottomSheetState(
                                coroutineScope = scope,
                                bottomSheetState = bottomSheetState,
                                bottomSheetType = BottomSheetType.Show,
                            )
                        },
                    model = myPageState.profileImageUrl,
                    contentDescription = null,
                )

                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(
                        id = R.drawable.ic_mypage_edit,
                    ),
                    contentDescription = null,
                )
            }
        }


        Spacer(
            modifier = Modifier.height(60.dp),
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .clip(
                    RoundedCornerShape(5.dp),
                )
                .background(DormColor.Lighten200)
                .padding(12.dp),
            contentAlignment = Alignment.CenterStart,
        ) {

            // 문구
            Caption(
                text = myPageState.phrase,
            )
        }


        Spacer(
            modifier = Modifier.height(10.dp),
        )


        Row(
            modifier = Modifier.height(90.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {

            // 상점
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(
                        RoundedCornerShape(10.dp),
                    )
                    .border(
                        color = DormColor.DormPrimary,
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                    )
                    .background(
                        color = DormColor.Lighten200,
                    )
                    .padding(
                        vertical = 14.dp,
                        horizontal = 20.dp,
                    ),
            ) {

                Caption(
                    text = stringResource(
                        id = R.string.PlusPoint,
                    ),
                    color = DormColor.Darken200,
                )

                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Headline3(
                        text = myPageState.bonusPoint.toString(),
                        color = DormColor.Darken200,
                    )
                }
            }

            // 벌점
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(
                        RoundedCornerShape(10.dp),
                    )
                    .border(
                        color = DormColor.Error,
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                    )
                    .background(
                        color = DormColor.LightenError,
                    )
                    .padding(
                        vertical = 14.dp,
                        horizontal = 20.dp,
                    ),
            ) {

                Caption(
                    text = stringResource(
                        id = R.string.MinusPoint,
                    ),
                    color = DormColor.Error,
                )

                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Headline3(
                        text = myPageState.minusPoint.toString(),
                        color = DormColor.Error,
                    )
                }
            }
        }


        Spacer(
            modifier = Modifier.height(10.dp),
        )


        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(10.dp),
                    )
                    .background(DormColor.Gray100),
            ) {

                // 상벌점 내역 확인
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(NavigationRoute.PointList)
                        }
                        .padding(
                            vertical = 14.dp,
                            horizontal = 16.dp,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Body5(
                        text = stringResource(
                            id = R.string.CheckPointList,
                        ),
                    )
                }

                Divider(
                    modifier = Modifier
                        .padding(
                            horizontal = 10.dp,
                        )
                        .background(DormColor.Gray100),
                )

                // 비밀번호 변경
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(NavigationRoute.PointList)
                        }
                        .padding(
                            vertical = 14.dp,
                            horizontal = 16.dp,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Body5(
                        text = stringResource(
                            id = R.string.ChangePassword,
                        ),
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(10.dp),
                    )
                    .background(DormColor.Gray100),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DormColor.Gray100)
                        .clickable {
                            onSignOutButtonClick()
                        }
                        .padding(
                            vertical = 14.dp,
                            horizontal = 16.dp,
                        ),
                ) {

                    // 로그아웃
                    Body5(
                        text = stringResource(
                            id = R.string.Logout,
                        ),
                        color = DormColor.Error,
                    )
                }
            }
        }
    }
}

private fun getStringFromEvent(
    context: Context,
    event: MyPageViewModel.Event,
): String {
    return context.getString(
        when (event) {
            is MyPageViewModel.Event.UnAuthorizedTokenException -> R.string.LoginUnauthorized
            is MyPageViewModel.Event.CannotConnectException -> R.string.LoginNotFound
            is MyPageViewModel.Event.TooManyRequestException -> R.string.TooManyRequest
            is MyPageViewModel.Event.InternalServerException -> R.string.ServerException
            else -> R.string.UnKnownException
        },
    )
}
