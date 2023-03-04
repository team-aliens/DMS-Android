package team.aliens.dms_android.feature.register.ui.last

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.ButtonText
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.image.GettingImageOptionDialog
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.dms_android.util.SelectImageType
import team.aliens.presentation.R

@Composable
fun SignUpProfileScreen(
    navController: NavController,
) {

    var profileImageUrl by remember { mutableStateOf("https://image-dms.s3.ap-northeast-2.amazonaws.com/59fd0067-93ef-4bcb-8722-5bc8786c5156%7C%7C%E1%84%83%E1%85%A1%E1%84%8B%E1%85%AE%E1%86%AB%E1%84%85%E1%85%A9%E1%84%83%E1%85%B3.png") }

    var isSelectedImage by remember { mutableStateOf(false) }

    var setProfileDialogState by remember { mutableStateOf(false) }

    if (setProfileDialogState) {
        GettingImageOptionDialog(
            onCancel = {
                setProfileDialogState = false
            },
            onTakePhoto = {

            },
            onSelectPhoto = {
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
                    currentBackStackEntry?.arguments?.putBoolean("isSignUp", true)
                    navigate(
                        NavigationRoute.ConfirmImage +
                                "/${SelectImageType.SELECT_FROM_GALLERY.ordinal}",
                    )
                }
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 108.dp,
                start = 16.dp,
                end = 16.dp,
            ),
    ) {
        AppLogo()
        Spacer(modifier = Modifier.height(8.dp))
        Body2(
            text = stringResource(id = R.string.ProfileImage)
        )
        Spacer(modifier = Modifier.height(80.dp))
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
                AsyncImage(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .clickable {
                            setProfileDialogState = true
                        },
                    model = profileImageUrl,
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
            Spacer(modifier = Modifier.fillMaxHeight(0.62f))
            ButtonText(
                modifier = Modifier
                    .dormClickable(
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
                            navigate(NavigationRoute.SignUpPolicy)
                        }
                    },
                text = stringResource(id = R.string.SettingLater),
            )
            Spacer(modifier = Modifier.height(30.dp))
            DormContainedLargeButton(
                text = stringResource(id = R.string.Next),
                color = DormButtonColor.Blue,
                enabled = isSelectedImage,
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
                    Log.d("TEST",
                        navController.currentBackStackEntry?.arguments?.toString().toString()
                    )
                    navigate(NavigationRoute.SignUpPolicy)
                }
            }
        }
    }
}