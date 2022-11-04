package com.example.dms_android.feature.register.screen.last

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.auth_domain.enum.BottomSheetType
import com.example.design_system.button.DormButtonColor
import com.example.design_system.button.DormContainedLargeButton
import com.example.design_system.color.DormColor
import com.example.design_system.icon.DormIcon
import com.example.design_system.typography.Body4
import com.example.design_system.typography.NotoSansFamily
import com.example.dms_android.R
import com.example.dms_android.util.fetchImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileImageScreen() {
    val bottomSheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden
    )
    val defaultImage = rememberImagePainter(R.drawable.addimage)
    val curImage by remember { mutableStateOf<Painter>(defaultImage) }
    val a: File? = null
    val scope = rememberCoroutineScope()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            ContentLayout(
                coroutineScope,
                curImage,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background),
        ) {
            MainValue()
            PickImage(bottomSheetState, scope, paint = curImage, a)
            EnterNextPageView()
        }
    }
}

@Composable
fun MainValue() {
    Box(
        contentAlignment = Alignment.TopStart,
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Image(
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        bottom = 12.dp,
                    )
                    .size(24.dp),
                painter = painterResource(id = DormIcon.BackArrow.drawableId),
                contentDescription = stringResource(id = R.string.backButton),
            )
            Image(
                modifier = Modifier
                    .padding(
                        top = 32.dp,
                        bottom = 7.dp,
                    )
                    .height(85.dp)
                    .width(85.dp),
                painter = painterResource(id = R.drawable.ic_information_toast),
                contentDescription = stringResource(id = R.string.MainLogo),
            )
            Spacer(
                modifier = Modifier
                    .height(1.dp)
            )
            Body4(
                text = stringResource(id = R.string.ProfileImage),
                color = DormColor.Gray600,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PickImage(
    state: ModalBottomSheetState,
    scope: CoroutineScope,
    paint: Painter,
    image: File?,
) {
    val profileImage by remember { mutableStateOf(paint) }
    val imageGet by remember { mutableStateOf(image) }
    Row(
        modifier = Modifier
            .padding(top = 60.dp)
            .height(150.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
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
                        .size(150.dp)
                        .clip(CircleShape),
                    painter = if (imageGet == null) {
                        profileImage
                    } else {
                        rememberImagePainter(imageGet)
                    },
                    contentDescription = stringResource(id = R.string.AddImageButton)
                )
            }
            Image(
                modifier = Modifier
                    .padding(start = 108.dp)
                    .size(30.dp),
                painter = painterResource(id = R.drawable.addplusimage),
                contentDescription = stringResource(id = R.string.AddImageButton),
            )
        }
    }
}

@Composable
fun EnterNextPageView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.SettingLater),
            color = DormColor.Gray600,
            fontFamily = NotoSansFamily,
            fontWeight = FontWeight(600),
        )

        DormContainedLargeButton(
            modifier = Modifier
                .padding(top = 20.dp)
                .height(37.dp)
                .width(328.dp),
            text = stringResource(id = R.string.Next),
            color = DormButtonColor.Blue,
        ) {
            TODO("ViewModel Function")
        }

    }
}

@Composable
fun ContentLayout(
    coroutineScope: CoroutineScope,
    paint: Painter,
) {
    var profileImage by remember { mutableStateOf(paint) }
    var getFile by remember { mutableStateOf<File?>(null) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = DormColor.Gray100),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Button(
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(74.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = DormColor.Gray100,
                contentColor = DormColor.Gray800,
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = stringResource(id = R.string.CameraIcon),
                )
                Text(
                    text = stringResource(id = R.string.TakePhoto),
                    color = DormColor.Gray800,
                    fontFamily = NotoSansFamily,
                    fontWeight = FontWeight.Normal,
                )
            }
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    fetchImage(context)?.let { getFile = it }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(74.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = DormColor.Gray100,
                contentColor = DormColor.Gray800,
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_photo),
                    contentDescription = stringResource(id = R.string.PhotoIcon),
                )
                Text(
                    text = stringResource(id = R.string.ChoosePhoto),
                    color = DormColor.Gray800,
                    fontFamily = NotoSansFamily,
                    fontWeight = FontWeight.Normal,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
fun changeBottomSheetState(
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    bottomSheetType: BottomSheetType,
) {
    coroutineScope.launch {
        when (bottomSheetType) {
            BottomSheetType.Hide -> bottomSheetState.hide()
            BottomSheetType.Show -> bottomSheetState.show()
        }
    }
}

@Preview
@Composable
fun ProfileImageScreenPreView() {
    ProfileImageScreen()
}
