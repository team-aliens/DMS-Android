package team.aliens.dms_android.feature.register.ui.id

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.typography.ButtonText
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.dms_android.feature.register.event.email.RegisterEmailEvent
import team.aliens.dms_android.feature.register.event.id.SetIdEvent
import team.aliens.dms_android.viewmodel.auth.register.id.SetIdViewModel
import team.aliens.presentation.R
import java.util.*

@Composable
fun SignUpIdScreen(
    navController: NavController,
    setIdViewModel: SetIdViewModel = hiltViewModel(),
) {

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    var grade by remember { mutableStateOf("") }
    var classRoom by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }

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
        userId = value
    }

    val valueList = arrayListOf(
        Triple(
            first = grade,
            second = onGradeChange,
            third = stringResource(id = R.string.Grade),
        ),
        Triple(
            first = classRoom,
            second = onClassRoomChange,
            third = stringResource(id = R.string.ClassRoom)
        ),
        Triple(
            first = number,
            second = onNumberChange,
            third = stringResource(id = R.string.Number)
        ),
    )

    var userName by remember { mutableStateOf("") }
    var isNameShowed by remember { mutableStateOf(false) }

    var isGradeError by remember { mutableStateOf(false) }
    var isIdError by remember { mutableStateOf(false) }

    var isNameChecked by remember { mutableStateOf(false) }

    val toast = rememberToast()

    LaunchedEffect(Unit) {
        setIdViewModel.schoolId = UUID.fromString(navController.previousBackStackEntry?.arguments?.getString("schoolId"))
        setIdViewModel.examineGradeEvent.collect {
            when (it) {
                is SetIdEvent.ExamineGradeName -> {
                    isNameShowed = true
                    isGradeError = false
                    userName = it.examineGradeEntity.name
                }
                is SetIdEvent.ExamineGradeNotFoundException -> {
                    isNameShowed = false
                    isGradeError = true
                    toast(context.getString(R.string.CheckGrade))
                }
                is SetIdEvent.DuplicateIdSuccess -> {
                    if(!isNameChecked) toast(context.getString(R.string.CheckGrade))
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
                        navController.navigate(NavigationRoute.SignUpPassword)
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
            .padding(
                top = 108.dp,
                start = 16.dp,
                end = 16.dp,
            ),
    ) {
        AppLogo()
        Spacer(modifier = Modifier.height(8.dp))
        Body2(
            text = stringResource(id = R.string.SetId)
        )
        Spacer(modifier = Modifier.height(60.dp))
        Column(modifier = Modifier.fillMaxHeight(0.812f)) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 21.dp,
                ),
            ) {
                items(valueList.size) { index ->
                    DormTextField(
                        modifier = Modifier.width(112.dp),
                        value = valueList[index].first,
                        onValueChange = valueList[index].second,
                        hint = valueList[index].third,
                        keyboardType = KeyboardType.Number,
                        error = isGradeError,
                    )
                }
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
                            color = DormColor.Gray200,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Body3(text = "${userName}님이 맞으신가요?")
                    Spacer(modifier = Modifier.fillMaxWidth(0.8f))
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
            Spacer(modifier = Modifier.height(24.dp))
            DormTextField(
                modifier = Modifier
                    .onFocusChanged {
                        if (it.isFocused) {
                            callExamineGrade(
                                grade = grade,
                                classRoom = classRoom,
                                number = number,
                                setIdViewModel = setIdViewModel,
                            )
                        }
                    },
                value = userId,
                onValueChange = onUserIdChange,
                hint = stringResource(id = R.string.EnterId),
                error = isIdError,
                errorDescription = errorDescription,
            )
        }
        DormContainedLargeButton(
            text = stringResource(id = R.string.Next),
            color = DormButtonColor.Blue,
            enabled = grade.isNotEmpty() && classRoom.isNotEmpty() && number.isNotEmpty() && userId.isNotEmpty()
        ) {
            if(userId.length > 20){
                isIdError = true

            }
            setIdViewModel.duplicateId(userId)
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
            grade = Integer.parseInt(grade.trim()),
            classRoom = Integer.parseInt(classRoom.trim()),
            number = Integer.parseInt(number.trim()),
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
