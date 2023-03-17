package team.aliens.dms_android.feature.register.ui.school

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Body3
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.dms_android.feature.register.event.school.ExamineSchoolCodeEvent
import team.aliens.dms_android.viewmodel.auth.register.school.ExamineSchoolCodeViewModel
import team.aliens.presentation.R

@Composable
fun SignUpVerifySchoolScreen(
    navController: NavController,
    examineSchoolCodeViewModel: ExamineSchoolCodeViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

    val focusRequester = remember { FocusRequester() }

    var verificationCode by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    val onVerificationCodeChange = { value: String ->
        if (value.length == 8) {
            focusManager.clearFocus()
            examineSchoolCodeViewModel.examineSchoolCode(value)
        } else {
            isError = false
        }
        verificationCode = value.take(8)
    }

    val toast = rememberToast()

    LaunchedEffect(Unit) {
        examineSchoolCodeViewModel.examineSchoolCodeEvent.collect {
            when (it) {
                is ExamineSchoolCodeEvent.ExamineSchoolCodeSuccess -> {
                    navController.currentBackStackEntry?.arguments?.run {
                        putString("schoolCode", verificationCode)
                        putString("schoolId", examineSchoolCodeViewModel.schoolId.toString())
                    }
                    navController.navigate(NavigationRoute.SchoolQuestion)
                }
                is ExamineSchoolCodeEvent.MissMatchSchoolCode -> {
                    isError = true
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
            .background(
                DormTheme.colors.surface,
            )
            .padding(
                top = 108.dp,
                start = 16.dp,
                end = 16.dp,
            )
            .dormClickable(
                rippleEnabled = false,
            ) {
                focusManager.clearFocus()
            },
    ) {
        AppLogo(
            darkIcon = isSystemInDarkTheme(),
        )
        Space(space = 8.dp)
        Body2(
            text = stringResource(id = R.string.SchoolVerificationCode)
        )
        Space(space = 100.dp)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BasicTextField(
                value = verificationCode,
                modifier = Modifier.focusRequester(focusRequester),
                onValueChange = onVerificationCodeChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                },
                decorationBox = {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        items(8) { index ->
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
            Space(space = 40.dp)
            Body3(
                text = if (isError) {
                    stringResource(id = R.string.NoSameCode)
                } else stringResource(id = R.string.EmailEightCode),
                color = if (isError) {
                    DormColor.Error
                } else DormColor.Gray500,
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.767f))
            DormContainedLargeButton(
                text = stringResource(id = R.string.Verification),
                color = DormButtonColor.Blue,
                enabled = (verificationCode.length == 8 && !isError),
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
