package com.example.dms_android.feature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.auth_domain.enum.BottomSheetType
import com.example.design_system.color.DormColor
import com.example.design_system.typography.Body5
import com.example.design_system.typography.Caption
import com.example.design_system.typography.Headline3
import com.example.design_system.typography.SubTitle1
import com.example.dms_android.R
import com.example.dms_android.component.changeBottomSheetState
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyPageScreen(
    navController: NavController
) {

    val bottomSheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormColor.Gray200),
    ) {
        UserInformation(bottomSheetState, scope)
        WarningPoint()
        PointBox()
        MyPageBlock()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserInformation(
    state: ModalBottomSheetState,
    scope: CoroutineScope,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 55.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .padding(start = 24.dp)
        ) {
            SubTitle1(
                text = "2115 유현명"
            )
            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )
            Body5(text = stringResource(id = R.string.ExampleSchool))
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
                Image(
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
                    painter = painterResource(id = R.drawable.addimage),
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
fun WarningPoint() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(start = 24.dp, end = 24.dp, top = 20.dp)
            .clip(RoundedCornerShape(25))
            .border(
                color = DormColor.Lighten200,
                width = 1.dp,
                shape = RoundedCornerShape(25)
            )
            .background(DormColor.Lighten200),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .width(20.dp),
            )
            Caption(text = stringResource(id = R.string.ExampleWarning))
        }
    }
}

@Composable
fun PointBox() {
    Row() {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(start = 24.dp, top = 20.dp, end = 5.dp)
                .height(90.dp)
                .clip(RoundedCornerShape(25))
                .border(
                    color = DormColor.DormPrimary,
                    width = 1.dp,
                    shape = RoundedCornerShape(25)
                )
                .background(DormColor.Gray100),
            contentAlignment = Alignment.TopStart,
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 14.dp)
                    .fillMaxWidth()
            ) {
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
                Body5(
                    text = stringResource(id = R.string.PlusPoint),
                    color = DormColor.Gray600
                )
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 24.dp, bottom = 18.dp)
                ) {
                    Headline3(
                        text = stringResource(id = R.string.ExamplePlusPoint)
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
                    color = DormColor.DormPrimary,
                    width = 1.dp,
                    shape = RoundedCornerShape(25)
                )
                .background(DormColor.Gray100),
            contentAlignment = Alignment.TopStart,
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 14.dp)
                    .fillMaxSize()
            ) {
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
                Body5(
                    text = stringResource(id = R.string.MinusPoint),
                    color = DormColor.Gray600
                )
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 24.dp, bottom = 18.dp)
                ) {
                    Headline3(
                        text = stringResource(id = R.string.ExamplePlusPoint)
                    )
                }
            }
        }
    }
}

@Composable
fun MyPageBlock() {
    Column() {
        Spacer(
            modifier = Modifier
                .height(15.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)
                .height(110.dp)
                .clip(RoundedCornerShape(20))
                .border(
                    color = DormColor.Gray100,
                    width = 1.dp,
                    shape = RoundedCornerShape(20)
                )
                .background(DormColor.Gray100),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                contentAlignment = Alignment.CenterStart
            ) {
                Row() {
                    Spacer(
                        modifier = Modifier
                            .width(20.dp)
                    )
                    Body5(text = stringResource(id = R.string.CheckPointList))
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(start = 15.dp, end = 15.dp)
                    .background(DormColor.Gray300)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart
            ) {
                Row() {
                    Spacer(
                        modifier = Modifier
                            .width(20.dp)
                    )
                    Body5(text = stringResource(id = R.string.ChangePassword))
                }
            }
        }

        Column() {
            Spacer(
                modifier = Modifier
                    .height(15.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(start = 24.dp, end = 24.dp)
                    .clip(RoundedCornerShape(20))
                    .border(
                        color = DormColor.Gray100,
                        width = 1.dp,
                        shape = RoundedCornerShape(20)
                    )
                    .background(DormColor.Gray100),
                contentAlignment = Alignment.CenterStart
            ) {
                Row() {
                    Spacer(
                        modifier = Modifier
                            .width(20.dp)
                    )
                    Body5(
                        text = stringResource(id = R.string.Logout),
                        color = DormColor.Error,
                    )
                }
            }
        }
    }
}