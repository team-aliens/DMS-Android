package team.aliens.dms.android.feature.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.ShadowDefaults
import team.aliens.dms.android.core.designsystem.TextButton
import team.aliens.dms.android.core.designsystem.TextField
import team.aliens.dms.android.core.ui.Banner
import team.aliens.dms.android.core.ui.BannerDefaults
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.endPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun SetIdScreen(
    modifier: Modifier = Modifier,
    navigator: SignUpNavigator,
    // signUpViewModel: SignUpViewModel,
) {
    DmsScaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = navigator::navigateUp) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(id = R.string.top_bar_back_button),
                        )
                    }
                },
            )
        },
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues)
                .imePadding(),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Banner(
                modifier = Modifier
                    .fillMaxWidth()
                    .startPadding(),
                message = {
                    BannerDefaults.DefaultText(
                        text = stringResource(id = R.string.sign_up_set_id),
                    )
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = DefaultHorizontalSpace,
                ),
            ) {
                TextField(
                    modifier = Modifier.weight(1f),
                    value = "",
                    hint = { Text(text = "학년") },
                    onValueChange = {},
                    supportingText = {},
                    isError = false,
                )
                TextField(
                    modifier = Modifier.weight(1f),
                    value = "",
                    hint = { Text(text = "반") },
                    onValueChange = {},
                    supportingText = {},
                    isError = false,
                )
                TextField(
                    modifier = Modifier.weight(1f),
                    value = "",
                    hint = { Text(text = "번호") },
                    onValueChange = {},
                    supportingText = {},
                    isError = false,
                )
            }
            Spacer(modifier = Modifier.height(DefaultVerticalSpace))
            AccountInformationCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                name = "박준수",
                onConfirmClick = {},
            )
            Spacer(modifier = Modifier.height(DefaultVerticalSpace))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                value = "",
                hint = { Text(text = "아이디를 입력해 주세요") },
                onValueChange = {},
                supportingText = {},
                isError = false,
            )
            Spacer(modifier = Modifier.weight(3f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                // FIXME: 서버 연동
                onClick = navigator::openSignUpEnterEmailVerificationCode,
            ) {
                Text(text = stringResource(id = R.string.sign_up_send_email_verification_code))
            }
        }
    }/*

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

@Composable
private fun AccountInformationCard(
    modifier: Modifier = Modifier,
    name: String,
    onConfirmClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = DmsTheme.shapes.surfaceSmall,
        colors = CardDefaults.cardColors(
            containerColor = DmsTheme.colorScheme.surface,
            contentColor = DmsTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = ShadowDefaults.SmallElevation),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.startPadding(),
                text = name + "님이 맞으신가요?",
            )
            TextButton(
                modifier = Modifier.endPadding(),
                onClick = onConfirmClick,
            ) {
                Text(text = stringResource(id = R.string.accept))
            }
        }
    }
}
