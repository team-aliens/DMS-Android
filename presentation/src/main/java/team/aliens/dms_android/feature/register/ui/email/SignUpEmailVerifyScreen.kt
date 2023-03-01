package team.aliens.dms_android.feature.register.ui.email

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.typography.ButtonText
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.viewmodel.auth.register.email.RegisterEmailViewModel
import team.aliens.presentation.R

@Composable
fun SignUpEmailVerifyScreen(
    navController: NavController,
    registerEmailViewModel: RegisterEmailViewModel = hiltViewModel(),
) {

    val focusManager = LocalFocusManager.current

    var verificationCode by remember { mutableStateOf("") }

    var time by remember { mutableStateOf("3 : 00") }

    var isRunningTimer by remember { mutableStateOf(true) }

    var email by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        email =
            navController.previousBackStackEntry?.savedStateHandle?.get<String>("email").toString()
    }

    LaunchedEffect(isRunningTimer) {
        run loop@{
            repeat(180) {
                if (!isRunningTimer) {
                    isRunningTimer = true
                    return@loop
                }
                delay(1000L)
                val totalSecond = 179 - it
                val minutes = totalSecond / 60
                val seconds = totalSecond % 60
                time = "$minutes : $seconds"
            }
        }
    }

    val onVerificationCodeChange = { value: String ->
        if (value.length <= 6) {
            verificationCode = value
            if (value.length == 6) {
                focusManager.clearFocus()
            } else {
            }
        } else {
            verificationCode = value.take(8)
        }
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
        Body2(text = stringResource(id = R.string.VerificationCode))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 100.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BasicTextField(
                value = verificationCode,
                onValueChange = onVerificationCodeChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                decorationBox = {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        items(6) { index ->
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(shape = CircleShape)
                                    .background(
                                        color = if (verificationCode.length - 1 >= index) {
                                            DormColor.Gray600
                                        } else DormColor.Gray400,
                                    ),
                            )
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
            Body3(
                text = stringResource(id = R.string.EmailSixCode),
                color = DormColor.Gray500,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Body3(
                text = time,
                color = DormColor.DormPrimary,
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.652f))
            ButtonText(
                modifier = Modifier
                    .dormClickable(
                        rippleEnabled = false,
                    ) {
                        isRunningTimer = false
                    },
                text = stringResource(id = R.string.ResendVerificationCode),
                color = DormColor.Gray600,
            )
            Spacer(modifier = Modifier.height(26.dp))
            DormContainedLargeButton(
                text = stringResource(id = R.string.Verification),
                color = DormButtonColor.Blue,
                enabled = verificationCode.isNotEmpty() && isRunningTimer
            ) {

            }
        }
    }
}
