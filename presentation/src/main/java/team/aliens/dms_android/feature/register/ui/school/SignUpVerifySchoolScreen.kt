package team.aliens.dms_android.feature.register.ui.school

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Body3
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.register.event.school.ExamineSchoolCodeEvent
import team.aliens.dms_android.viewmodel.auth.register.school.ExamineSchoolCodeViewModel
import team.aliens.presentation.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpVerifySchoolScreen(
    navController: NavController,
    examineSchoolCodeViewModel: ExamineSchoolCodeViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val keyboardController = LocalSoftwareKeyboardController.current

    var verificationCode by remember { mutableStateOf("") }

    val onVerificationCodeChange = { value: String ->
        verificationCode = value
        if(verificationCode.length == 8){
            keyboardController?.hide()
        }
    }

    val toast = rememberToast()

    LaunchedEffect(Unit) {
        examineSchoolCodeViewModel.examineSchoolCodeEvent.collect {
            when (it) {
                is ExamineSchoolCodeEvent.ExamineSchoolCodeSuccess -> {
                    navController.navigate("")
                }
                else -> {
                    toast(
                        getStringFromEvent(
                            context = context,
                            event = it,
                        ),
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 92.dp,
                start = 16.dp,
            ),
    ) {
        AppLogo()
        Spacer(modifier = Modifier.height(8.dp))
        Body2(
            text = stringResource(id = R.string.SchoolVerificationCode)
        )
        Spacer(modifier = Modifier.height(100.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BasicTextField(
                value = verificationCode,
                onValueChange = onVerificationCodeChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                decorationBox = {
                    Row {
                        repeat(8) { index ->
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
                            Spacer(modifier = Modifier.width(24.dp))
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
            Body3(
                text = stringResource(id = R.string.EmailEightCode),
                color = DormColor.Gray500,
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.8f))
            DormContainedLargeButton(
                text = stringResource(id = R.string.Verification),
                color = DormButtonColor.Blue,
                enabled = verificationCode.length == 8,
            ) {
                examineSchoolCodeViewModel.examineSchoolCode(verificationCode)
            }
        }
    }
}

private fun getStringFromEvent(
    context: Context,
    event: ExamineSchoolCodeEvent,
): String =
    when (event) {
        is ExamineSchoolCodeEvent.BadRequestException -> {
            context.getString(R.string.BadRequest)
        }
        is ExamineSchoolCodeEvent.MissMatchSchoolCode -> {
            context.getString(R.string.NoSameCode)
        }
        is ExamineSchoolCodeEvent.TooManyRequestException -> {
            context.getString(R.string.TooManyRequest)
        }
        is ExamineSchoolCodeEvent.ServerException -> {
            context.getString(R.string.ServerException)
        }
        else -> {
            context.getString(R.string.UnKnownException)
        }
    }
