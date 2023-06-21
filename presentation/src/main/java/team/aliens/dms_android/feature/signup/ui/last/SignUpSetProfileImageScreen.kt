package team.aliens.dms_android.feature.signup.ui.last

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.extension.RatioSpace
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.ButtonText
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.main.image.GettingImageOptionDialog
import team.aliens.dms_android.feature.DmsRoute
import team.aliens.dms_android.util.fetchImage
import team.aliens.dms_android.feature.main.image.ConfirmImageViewModel
import team.aliens.presentation.R

const val defaultProfileUrl =
    "https://image-dms.s3.ap-northeast-2.amazonaws.com/59fd0067-93ef-4bcb-8722-5bc8786c5156%7C%7C%E1%84%83%E1%85%A1%E1%84%8B%E1%85%AE%E1%86%AB%E1%84%85%E1%85%A9%E1%84%83%E1%85%B3.png"

@Composable
fun SignUpSetProfileImageScreen(
    navController: NavController,
    confirmImageViewModel: ConfirmImageViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val profileImageUrl by remember { mutableStateOf(null) }

    var isSelectedImage by remember { mutableStateOf(false) }

    var setProfileDialogState by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val state = confirmImageViewModel.state.collectAsState().value

    if (setProfileDialogState) {
        GettingImageOptionDialog(
            onCancel = {
                setProfileDialogState = false
            },
            onTakePhoto = {

            },
            onSelectPhoto = {
                scope.launch {
                    val selectedImage = fetchImage(context)
                    if (selectedImage.toString().isNotEmpty()) {
                        isSelectedImage = true
                    }
                    confirmImageViewModel.setImage(selectedImage ?: return@launch)
                    setProfileDialogState = false
                }
            },
            onDialogDismiss = {},
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DormTheme.colors.surface,
            )
            .padding(
                top = 108.dp,
                start = 16.dp,
                end = 16.dp,
            ),
    ) {
        AppLogo(
            darkIcon = isSystemInDarkTheme(),
        )
        Space(space = 8.dp)
        Body2(text = stringResource(id = R.string.ProfileImage))
        Space(space = 80.dp)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier.size(150.dp),
                contentAlignment = Alignment.BottomEnd,
            ) {

                // 사용자 프로필
                // TODO 이미지 관련 로직 리팩토링 하기
                AsyncImage(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .clickable {
                            setProfileDialogState = true
                        },
                    model = state.selectedImage ?: defaultProfileUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )

                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(
                        id = R.drawable.addplusimage,
                    ),
                    contentDescription = null,
                )
            }
            RatioSpace(height = 0.62f)
            ButtonText(
                modifier = Modifier.dormClickable(
                    rippleEnabled = false,
                ) {
                    navController.run {
                        currentBackStackEntry?.arguments?.putString(
                            "schoolCode",
                            previousBackStackEntry?.arguments?.getString("schoolCode"),
                        )
                        currentBackStackEntry?.arguments?.putString(
                            "schoolAnswer",
                            previousBackStackEntry?.arguments?.getString("schoolAnswer"),
                        )
                        currentBackStackEntry?.arguments?.putString(
                            "email",
                            previousBackStackEntry?.arguments?.getString("email")
                        )
                        currentBackStackEntry?.arguments?.putString(
                            "authCode",
                            previousBackStackEntry?.arguments?.getString("authCode"),
                        )
                        currentBackStackEntry?.arguments?.putInt(
                            "classRoom",
                            previousBackStackEntry?.arguments?.getInt("classRoom")!!,
                        )
                        currentBackStackEntry?.arguments?.putInt(
                            "grade",
                            previousBackStackEntry?.arguments?.getInt("grade")!!,
                        )
                        currentBackStackEntry?.arguments?.putInt(
                            "number",
                            previousBackStackEntry?.arguments?.getInt("number")!!,
                        )
                        currentBackStackEntry?.arguments?.putString(
                            "accountId",
                            previousBackStackEntry?.arguments?.getString("accountId"),
                        )
                        currentBackStackEntry?.arguments?.putString(
                            "password",
                            previousBackStackEntry?.arguments?.getString("password"),
                        )
                        currentBackStackEntry?.arguments?.putString(
                            "profileImageUrl",
                            profileImageUrl,
                        )
                        navigate(DmsRoute.SignUp.Policy)
                    }
                },
                text = stringResource(id = R.string.SettingLater),
            )
            Space(space = 30.dp)
            DormContainedLargeButton(
                text = stringResource(id = R.string.Next),
                color = DormButtonColor.Blue,
                enabled = isSelectedImage,
            ) {
                confirmImageViewModel.uploadImage()
                navController.run {
                    currentBackStackEntry?.arguments?.putString(
                        "schoolCode",
                        previousBackStackEntry?.arguments?.getString("schoolCode"),
                    )
                    currentBackStackEntry?.arguments?.putString(
                        "schoolAnswer",
                        previousBackStackEntry?.arguments?.getString("schoolAnswer"),
                    )
                    currentBackStackEntry?.arguments?.putString(
                        "email",
                        previousBackStackEntry?.arguments?.getString("email")
                    )
                    currentBackStackEntry?.arguments?.putString(
                        "authCode",
                        previousBackStackEntry?.arguments?.getString("authCode"),
                    )
                    currentBackStackEntry?.arguments?.putInt(
                        "classRoom",
                        previousBackStackEntry?.arguments?.getInt("classRoom")!!,
                    )
                    currentBackStackEntry?.arguments?.putInt(
                        "grade",
                        previousBackStackEntry?.arguments?.getInt("grade")!!,
                    )
                    currentBackStackEntry?.arguments?.putInt(
                        "number",
                        previousBackStackEntry?.arguments?.getInt("number")!!,
                    )
                    currentBackStackEntry?.arguments?.putString(
                        "accountId",
                        previousBackStackEntry?.arguments?.getString("accountId"),
                    )
                    currentBackStackEntry?.arguments?.putString(
                        "password",
                        previousBackStackEntry?.arguments?.getString("password"),
                    )
                    currentBackStackEntry?.arguments?.putString(
                        "profileImageUrl",
                        confirmImageViewModel.profileImageUrl,
                    )
                    navigate(DmsRoute.SignUp.Policy)
                }
            }
        }
    }
}