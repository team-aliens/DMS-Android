package team.aliens.dms.android.feature.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator

@Destination
@Composable
internal fun SetIdScreen(
    modifier: Modifier = Modifier,
    navigator: SignUpNavigator,
    // signUpViewModel: SignUpViewModel,
) {/*

    val uiState by signUpViewModel.stateFlow.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    val toast = LocalToast.current
    val conflictStudentErrorMessage =
        stringResource(id = R.string.sign_up_id_error_conflict_student)
    val notFoundStudentErrorMessage =
        stringResource(id = R.string.sign_up_id_error_not_found_student)

    val moveFocus = {
        focusManager.moveFocus(FocusDirection.Next)
    }

    val onGradeChange = { grade: String ->
        signUpViewModel.postIntent(SignUpIntent.SetId.SetGrade(grade = grade))
        if (grade.length == 1) moveFocus()
    }

    val onClassRoomChange = { classRoom: String ->
        signUpViewModel.postIntent(SignUpIntent.SetId.SetClassRoom(classRoom = classRoom))
        if (classRoom.length == 1) moveFocus()
    }

    val onNumberChange = { number: String ->
        signUpViewModel.postIntent(SignUpIntent.SetId.SetNumber(number = number))
        if (number.length == 2) moveFocus()
    }

    val onAccountIdChange = { accountId: String ->
        signUpViewModel.postIntent(SignUpIntent.SetId.SetAccountId(accountId = accountId))
    }

    LaunchedEffect(Unit) {
        signUpViewModel.sideEffectFlow.collect {
            when (it) {
                is SignUpSideEffect.SetId.SuccessVerifyStudent -> {
                    navigator.openSignUpSetPassword()
                }

                is SignUpSideEffect.SetId.NotFoundStudent -> {
                    toast.showErrorToast(notFoundStudentErrorMessage)
                }

                is SignUpSideEffect.SetId.ConflictStudent -> {
                    toast.showErrorToast(conflictStudentErrorMessage)
                }

                else -> {}
            }
        }
    }

    Column(
        modifier = modifier
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
        Body2(text = stringResource(id = R.string.sign_up_id_set))
        Space(space = 60.dp)
        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(22.dp)
            ) {
                DormTextField(
                    modifier = Modifier.fillMaxWidth(0.292f),
                    value = uiState.grade,
                    onValueChange = onGradeChange,
                    hint = stringResource(id = R.string.Grade),
                    error = uiState.studentNumberMismatchError,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.NumberPassword,
                    ),
                )
                DormTextField(
                    modifier = Modifier.fillMaxWidth(0.45f),
                    value = uiState.classRoom,
                    onValueChange = onClassRoomChange,
                    hint = stringResource(id = R.string.ClassRoom),
                    error = uiState.studentNumberMismatchError,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.NumberPassword,
                    ),
                )
                DormTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.number,
                    onValueChange = onNumberChange,
                    hint = stringResource(id = R.string.Number),
                    error = uiState.studentNumberMismatchError,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.NumberPassword,
                    ),
                )
            }
            AnimatedVisibility(
                visible = uiState.checkedStudentName,
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
                    Body3(text = "${uiState.studentName}님이 맞으신가요?")
                    RatioSpace(width = 0.8f)
                    ButtonText(
                        modifier = Modifier.dormClickable(
                            rippleEnabled = false,
                        ) {
                            signUpViewModel.postIntent(
                                SignUpIntent.SetId.SetCheckedStudentName(
                                    false
                                )
                            )
                        },
                        text = stringResource(id = R.string.Check),
                    )
                }
            }
            Space(space = 26.dp)
            DormTextField(
                modifier = Modifier.onFocusChanged {
                    if (it.isFocused) {
                        signUpViewModel.postIntent(SignUpIntent.SetId.ExamineStudentNumber)
                    }
                },
                value = uiState.accountId,
                onValueChange = onAccountIdChange,
                hint = stringResource(id = R.string.EnterId),
                error = uiState.conflictAccountIdError,
                errorDescription = stringResource(id = R.string.sign_up_id_error_id_conflict),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.imePadding(),
            ) {
                DormContainedLargeButton(
                    text = stringResource(id = R.string.next),
                    color = DormButtonColor.Blue,
                    enabled = uiState.idButtonEnabled,
                ) {
                    signUpViewModel.postIntent(SignUpIntent.SetId.CheckIdDuplication)
                }
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }*/
}