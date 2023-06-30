package team.aliens.dms_android.feature.signup.ui.id

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
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
import team.aliens.design_system.extension.RatioSpace
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.typography.ButtonText
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.DmsRoute
import team.aliens.dms_android.feature.signup.event.id.SetIdEvent
import team.aliens.presentation.R
import java.util.UUID

const val idFormatPattern = "^[a-zA-Z]*$"

@Composable
fun SetIdScreen(
    navController: NavController,
    setIdViewModel: SetIdViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

    var grade by remember { mutableStateOf("") }
    var classRoom by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }

    var userName by remember { mutableStateOf("") }
    var isNameShowed by remember { mutableStateOf(false) }

    var isGradeError by remember { mutableStateOf(false) }
    var isIdError by remember { mutableStateOf(false) }

    var isNameChecked by remember { mutableStateOf(false) }

    val toast = rememberToast()

    var errorDescription by remember { mutableStateOf("") }

    val onGradeChange = { value: String ->
        grade = value
    }

    val onClassRoomChange = { value: String ->
        classRoom = value
    }

    val onNumberChange = { value: String ->
        number = value
    }

    val onUserIdChange = { value: String ->
        if (value.length != userId.length) isIdError = false
        userId = value
    }

    LaunchedEffect(Unit) {
        setIdViewModel.schoolId =
            UUID.fromString(navController.previousBackStackEntry?.arguments?.getString("schoolId"))
        setIdViewModel.examineGradeEvent.collect {
            when (it) {
                is SetIdEvent.ExamineGradeName -> {
                    isNameShowed = true
                    isGradeError = false
                    userName = it.examineStudentNumberOutput.name
                }
                is SetIdEvent.ExamineGradeNotFoundException -> {
                    isNameShowed = false
                    isGradeError = true
                    toast(context.getString(R.string.CheckGrade))
                }
                is SetIdEvent.DuplicateIdSuccess -> {
                    if (!isNameChecked) toast(context.getString(R.string.CheckGrade))
                    else {
                        navController.currentBackStackEntry?.arguments?.run {
                            putString(
                                "schoolCode",
                                navController.previousBackStackEntry?.arguments?.getString("schoolCode")
                            )
                            putString(
                                "schoolAnswer",
                                navController.previousBackStackEntry?.arguments?.getString("schoolAnswer")
                            )
                            putString(
                                "email",
                                navController.previousBackStackEntry?.arguments?.getString("email")
                            )
                            putString(
                                "authCode",
                                navController.previousBackStackEntry?.arguments?.getString("authCode")
                            )
                            putInt("classRoom", classRoom.toInt())
                            putInt("grade", grade.toInt())
                            putInt("number", number.toInt())
                            putString("accountId", userId)
                        }
                        navController.navigate(DmsRoute.SignUp.SetPassword)
                    }
                }
                is SetIdEvent.DuplicateIdConflictException -> {
                    isIdError = true
                    errorDescription = context.getString(R.string.UsingId)
                }
                else -> {
                    toast(
                        getStringFromEvent(
                            context = context,
                            event = it,
                        )
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
        AppLogo()
        Space(space = 8.dp)
        Body2(
            text = stringResource(id = R.string.SetId)
        )
        Space(space = 60.dp)
        Column(modifier = Modifier.fillMaxHeight(0.812f)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(22.dp)
            ) {
                DormTextField(
                    modifier = Modifier.fillMaxWidth(0.292f),
                    value = grade,
                    onValueChange = onGradeChange,
                    hint = stringResource(id = R.string.Grade),
                    keyboardType = KeyboardType.NumberPassword,
                    error = isGradeError,
                    imeAction = ImeAction.Next,
                )
                DormTextField(
                    modifier = Modifier.fillMaxWidth(0.45f),
                    value = classRoom,
                    onValueChange = onClassRoomChange,
                    hint = stringResource(id = R.string.ClassRoom),
                    keyboardType = KeyboardType.NumberPassword,
                    error = isGradeError,
                    imeAction = ImeAction.Next,
                )
                DormTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = number,
                    onValueChange = onNumberChange,
                    hint = stringResource(id = R.string.Number),
                    keyboardType = KeyboardType.NumberPassword,
                    error = isGradeError,
                    imeAction = ImeAction.Next,
                )
            }
            AnimatedVisibility(
                visible = isNameShowed,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 12.dp,
                        )
                        .height(54.dp)
                        .background(
                            color = DormTheme.colors.background,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Space(space = 16.dp)
                    Body3(text = "${userName}님이 맞으신가요?")
                    RatioSpace(width = 0.8f)
                    ButtonText(
                        modifier = Modifier.dormClickable(
                            rippleEnabled = false,
                        ) {
                            isNameChecked = true
                            isNameShowed = false
                            isGradeError = false
                        },
                        text = stringResource(id = R.string.Check),
                    )
                }
            }
            Space(space = 26.dp)
            DormTextField(
                modifier = Modifier
                    .onFocusChanged {
                        if (it.isFocused) {
                            callExamineGrade(
                                grade = grade.trim(),
                                classRoom = classRoom.trim(),
                                number = number.trim(),
                                setIdViewModel = setIdViewModel,
                            )
                        }
                    },
                value = userId,
                onValueChange = onUserIdChange,
                hint = stringResource(id = R.string.EnterId),
                error = isIdError,
                errorDescription = errorDescription,
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                },
                imeAction = ImeAction.Done,
            )
        }
        DormContainedLargeButton(
            text = stringResource(id = R.string.Next),
            color = DormButtonColor.Blue,
            enabled = grade.isNotEmpty() && classRoom.isNotEmpty() && number.isNotEmpty() && userId.isNotEmpty()
        ) {
            if (!isNameChecked) toast(context.getString(R.string.CheckName))
            else if (userId.length > 20) {
                isIdError = true
                errorDescription = context.getString(R.string.IdError)
            } else {
                setIdViewModel.duplicateId(userId)
            }
        }
    }
}

private fun callExamineGrade(
    grade: String,
    classRoom: String,
    number: String,
    setIdViewModel: SetIdViewModel,
) {
    if (grade.isNotEmpty() && classRoom.isNotEmpty() && number.isNotEmpty()) {
        setIdViewModel.examineGrade(
            grade = Integer.parseInt(grade),
            classRoom = Integer.parseInt(classRoom),
            number = Integer.parseInt(number),
        )
    }
}

private fun getStringFromEvent(
    context: Context,
    event: SetIdEvent,
): String = when (event) {
    is SetIdEvent.ExamineGradeBadRequestException -> {
        context.getString(R.string.BadRequest)
    }
    is SetIdEvent.ExamineGradeConflictException -> {
        context.getString(R.string.CheckGrade)
    }
    is SetIdEvent.ExamineGradeTooManyRequestException -> {
        context.getString(R.string.TooManyRequest)
    }
    is SetIdEvent.ExamineGradeInterServerException -> {
        context.getString(R.string.ServerException)
    }
    is SetIdEvent.DuplicateIdBadRequestException -> {
        context.getString(R.string.BadRequest)
    }
    is SetIdEvent.DuplicateIdTooManyRequestException -> {
        context.getString(R.string.TooManyRequest)
    }
    is SetIdEvent.DuplicateIdInterServerException -> {
        context.getString(R.string.ServerException)
    }
    else -> context.getString(R.string.UnKnownException)
}
