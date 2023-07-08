package team.aliens.dms_android.feature.signup.ui.id

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.extension.RatioSpace
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.LocalToast
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.typography.ButtonText
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.signup.SignUpIntent
import team.aliens.dms_android.feature.signup.SignUpSideEffect
import team.aliens.dms_android.feature.signup.SignUpViewModel
import team.aliens.presentation.R

@Composable
internal fun SetIdScreen(
    onNavigateToSetPassword: () -> Unit,
    signUpViewModel: SignUpViewModel,
) {

    val state by signUpViewModel.stateFlow.collectAsStateWithLifecycle()

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
                    onNavigateToSetPassword()
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
        Body2(text = stringResource(id = R.string.SetId))
        Space(space = 60.dp)
        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(22.dp)
            ) {
                DormTextField(
                    modifier = Modifier.fillMaxWidth(0.292f),
                    value = state.grade,
                    onValueChange = onGradeChange,
                    hint = stringResource(id = R.string.Grade),
                    keyboardType = KeyboardType.NumberPassword,
                    error = state.studentNumberMismatchError,
                    imeAction = ImeAction.Next,
                )
                DormTextField(
                    modifier = Modifier.fillMaxWidth(0.45f),
                    value = state.classRoom,
                    onValueChange = onClassRoomChange,
                    hint = stringResource(id = R.string.ClassRoom),
                    keyboardType = KeyboardType.NumberPassword,
                    error = state.studentNumberMismatchError,
                    imeAction = ImeAction.Next,
                )
                DormTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.number,
                    onValueChange = onNumberChange,
                    hint = stringResource(id = R.string.Number),
                    keyboardType = KeyboardType.NumberPassword,
                    error = state.studentNumberMismatchError,
                    imeAction = ImeAction.Next,
                )
            }
            AnimatedVisibility(
                visible = state.checkedStudentName,
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
                    Body3(text = "${state.studentName}님이 맞으신가요?")
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
                   if(it.isFocused) {
                       signUpViewModel.postIntent(SignUpIntent.SetId.ExamineStudentNumber)
                   }
                },
                value = state.accountId,
                onValueChange = onAccountIdChange,
                hint = stringResource(id = R.string.EnterId),
                error = state.conflictAccountIdError,
                errorDescription = stringResource(id = R.string.sign_up_id_error_id_conflict),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                },
                imeAction = ImeAction.Done,
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.imePadding(),
            ) {
                DormContainedLargeButton(
                    text = stringResource(id = R.string.Next),
                    color = DormButtonColor.Blue,
                    enabled = state.idConfirmButtonEnabled,
                ) {
                    signUpViewModel.postIntent(SignUpIntent.SetId.CheckIdDuplication)
                }
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}