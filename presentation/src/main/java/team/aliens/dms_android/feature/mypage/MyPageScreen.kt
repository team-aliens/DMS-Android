package team.aliens.dms_android.feature.mypage

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.component.DefaultAppliedTagSize
import team.aliens.design_system.dialog.DormCustomDialog
import team.aliens.design_system.dialog.DormDoubleButtonDialog
import team.aliens.design_system.extension.Space
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.Caption
import team.aliens.design_system.typography.Headline3
import team.aliens.design_system.typography.Title1
import team.aliens.dms_android.common.LocalAvailableFeatures
import team.aliens.design_system.component.LastAppliedItem
import team.aliens.dms_android.constans.Extra
import team.aliens.dms_android.feature.image.GettingImageOptionDialog
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.dms_android.util.SelectImageType
import team.aliens.dms_android.viewmodel.mypage.MyPageViewModel
import team.aliens.presentation.R

@Composable
fun MyPageScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    myPageViewModel: MyPageViewModel = hiltViewModel(),
) {

    LaunchedEffect(navController.currentDestination) {
        myPageViewModel.fetchMyPage()
    }

    val context = LocalContext.current

    val isPointServiceEnabled = LocalAvailableFeatures.current[Extra.isPointServiceEnabled]

    var myPageState by remember {
        mutableStateOf(MyPageEntity())
    }

    LaunchedEffect(Unit) {
        myPageViewModel.state.value.myPageEntity.collect {
            myPageState = it
        }
    }

    var signOutDialogState by remember {
        mutableStateOf(false)
    }

    val onSignOutButtonClick by remember {
        mutableStateOf(
            {
                signOutDialogState = true
            },
        )
    }

    if (signOutDialogState) {
        DormCustomDialog(
            onDismissRequest = {
                /* explicit blank */
            },
        ) {
            DormDoubleButtonDialog(
                content = stringResource(R.string.AreYouSureYouLogOut),
                mainBtnText = stringResource(R.string.Check),
                subBtnText = stringResource(R.string.Cancel),
                onMainBtnClick = {
                    myPageViewModel.signOut()
                },
                onSubBtnClick = {
                    signOutDialogState = false
                },
            )
        }
    }

    var withdrawalDialogState by remember {
        mutableStateOf(false)
    }

    val onWithdrawalButtonClick by remember {
        mutableStateOf(
            {
                withdrawalDialogState = true
            },
        )
    }

    if (withdrawalDialogState) {
        DormCustomDialog(
            onDismissRequest = { /* explicit blank */ },
        ) {
            DormDoubleButtonDialog(
                content = stringResource(R.string.AreYouSureYouWithdraw),
                mainBtnText = stringResource(R.string.Check),
                subBtnText = stringResource(R.string.Cancel),
                onMainBtnClick = {
                    myPageViewModel.withdraw()
                },
                onSubBtnClick = {
                    withdrawalDialogState = false
                },
            )
        }
    }


    var setProfileDialogState by remember {
        mutableStateOf(false)
    }

    val onSetProfileDialogDismiss = {
        setProfileDialogState = false
    }

    val onPasswordChangeClicked = {
        navController.navigate(NavigationRoute.ComparePassword)
    }

    if (setProfileDialogState) {
        GettingImageOptionDialog(
            onCancel = {
                setProfileDialogState = false
            },
            onTakePhoto = {
                navController.navigate(
                    NavigationRoute.ConfirmImage + "/${SelectImageType.TAKE_PHOTO.ordinal}",
                )
            },
            onSelectPhoto = {
                navController.navigate(
                    NavigationRoute.ConfirmImage + "/${SelectImageType.SELECT_FROM_GALLERY.ordinal}",
                )
            },
            onDialogDismiss = onSetProfileDialogDismiss,
        )
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

    LaunchedEffect(Unit) {
        myPageViewModel.signOutEvent.collect {
            navController.navigate(NavigationRoute.Login) {
                popUpTo(navController.currentDestination?.route!!) {
                    inclusive = true
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        myPageViewModel.withdrawEvent.collect {
            navController.navigate(NavigationRoute.Login) {
                popUpTo(navController.currentDestination?.route!!) {
                    inclusive = true
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DormTheme.colors.background,
            )
            .padding(
                vertical = 66.dp,
                horizontal = 16.dp,
            ),
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Column {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // 사용자 정보
                    Title1(
                        text = "${myPageState.gcn} ${myPageState.name}",
                    )
                    Space(space = 20.dp)

                    LastAppliedItem(
                        modifier = DefaultAppliedTagSize,
                        // fixme
                        text = myPageState.sex.gender,
                        backgroundColor = if (myPageState.sex == Gender.MALE) {
                            DormColor.Lighten200
                        } else DormColor.LightenError,
                        textColor = if (myPageState.sex == Gender.MALE) {
                            DormColor.DormPrimary
                        } else DormColor.Error,
                    )
                }
                Space(space = 10.dp)

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
                            setProfileDialogState = true
                        },
                    model = myPageState.profileImageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
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


        Space(space = 60.dp)

        if (isPointServiceEnabled!!) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .clip(
                        RoundedCornerShape(5.dp),
                    )
                    .background(
                        DormTheme.colors.secondary,
                    )
                    .padding(12.dp),
                contentAlignment = Alignment.CenterStart,
            ) {

                // 문구
                Caption(
                    text = myPageState.phrase,
                    color = DormColor.Gray1000,
                )
            }


            Space(space = 10.dp)


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
                            color = DormTheme.colors.primary,
                            width = 1.dp,
                            shape = RoundedCornerShape(10.dp),
                        )
                        .background(
                            color = DormTheme.colors.surface,
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
                        color = DormTheme.colors.onSecondary,
                    )

                    Box(
                        contentAlignment = Alignment.BottomEnd,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Headline3(
                            text = myPageState.bonusPoint.toString(),
                            color = DormTheme.colors.onSecondary,
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
                            color = DormTheme.colors.error,
                            width = 1.dp,
                            shape = RoundedCornerShape(10.dp),
                        )
                        .background(
                            color = DormTheme.colors.surface, // todo
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
        }


        Space(space = 10.dp)


        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

            if (isPointServiceEnabled) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(10.dp),
                        )
                        .background(
                            DormTheme.colors.surface,
                        ),
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
                            ),
                        color = DormTheme.colors.secondaryVariant,
                    )

                    // 비밀번호 변경
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onPasswordChangeClicked() }
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
            } else {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(10.dp),
                        )
                        .background(
                            DormTheme.colors.surface,
                        ),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onPasswordChangeClicked()
                            }
                            .padding(
                                vertical = 14.dp,
                                horizontal = 16.dp,
                            ),
                    ) {

                        // 비밀번호 변경
                        Body5(
                            text = stringResource(
                                id = R.string.change_password,
                            ),
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(10.dp),
                    )
                    .background(
                        DormTheme.colors.surface,
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
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
                        color = DormTheme.colors.error,
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(10.dp),
                    )
                    .background(
                        DormTheme.colors.surface,
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onWithdrawalButtonClick()
                        }
                        .padding(
                            vertical = 14.dp,
                            horizontal = 16.dp,
                        ),
                ) {

                    // 회원 탈퇴
                    Body5(
                        text = stringResource(
                            id = R.string.Withdrawal,
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
