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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.core.designsystem.ShadowDefaults
import team.aliens.dms.android.core.designsystem.TextButton
import team.aliens.dms.android.core.designsystem.TextField
import team.aliens.dms.android.core.ui.Banner
import team.aliens.dms.android.core.ui.BannerDefaults
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.collectInLaunchedEffectWithLifecycle
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
    viewModel: SignUpViewModel,
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    var shouldShowUserConfirmationCard by remember { mutableStateOf(false) }
    val (studentName, setStudentName) = remember { mutableStateOf("") }
    val toast = LocalToast.current
    val context = LocalContext.current
    val onShouldShowUserConfirmationCardChange = remember {
        { studentName: String ->
            setStudentName(studentName)
            shouldShowUserConfirmationCard = true
        }
    }

    val (gradeClassNumberError, setGradeClassNumberError) = remember { mutableStateOf(false) }

    LaunchedEffect(uiState.grade, uiState.`class`, uiState.number) {
        setGradeClassNumberError(false)
    }

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is SignUpSideEffect.UserFound -> onShouldShowUserConfirmationCardChange(sideEffect.studentName)
            SignUpSideEffect.UserNotFound -> setGradeClassNumberError(true)
            SignUpSideEffect.IdAvailable -> navigator.openSignUpSetPassword()
            SignUpSideEffect.IdDuplicated -> toast.showErrorToast(
                message = context.getString(R.string.sign_up_set_id_error_student_not_found),
            )

            else -> {/* explicit blank */
            }
        }
    }

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
                message = { BannerDefaults.DefaultText(text = stringResource(id = R.string.sign_up_set_id)) },
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace),
            ) {
                TextField(
                    modifier = Modifier.weight(1f),
                    value = stringResource(id = R.string.format_int, uiState.grade),
                    hint = { Text(text = stringResource(id = R.string.sign_up_set_id_hint_grade)) },
                    onValueChange = { viewModel.postIntent(SignUpIntent.UpdateGrade(value = it)) },
                    supportingText = {},
                    isError = gradeClassNumberError,
                    enabled = uiState.isStudentConfirmed,
                )
                TextField(
                    modifier = Modifier.weight(1f),
                    value = stringResource(id = R.string.format_int, uiState.`class`),
                    hint = { Text(text = stringResource(id = R.string.sign_up_set_id_hint_class)) },
                    onValueChange = { viewModel.postIntent(SignUpIntent.UpdateClass(value = it)) },
                    supportingText = {},
                    isError = gradeClassNumberError,
                    enabled = uiState.isStudentConfirmed,
                )
                TextField(
                    modifier = Modifier.weight(1f),
                    value = stringResource(id = R.string.format_int, uiState.number),
                    hint = { Text(text = stringResource(id = R.string.sign_up_set_id_hint_number)) },
                    onValueChange = { viewModel.postIntent(SignUpIntent.UpdateNumber(value = it)) },
                    supportingText = {},
                    isError = gradeClassNumberError,
                    enabled = uiState.isStudentConfirmed,
                )
            }
            Spacer(modifier = Modifier.height(DefaultVerticalSpace))
            AccountInformationCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                name = studentName,
                onConfirmClick = { viewModel.postIntent(SignUpIntent.ConfirmStudent) },
            )
            Spacer(modifier = Modifier.height(DefaultVerticalSpace))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                value = uiState.id,
                hint = { Text(text = stringResource(id = R.string.sign_up_set_id_please_enter_id)) },
                onValueChange = { viewModel.postIntent(SignUpIntent.UpdateId(value = it)) },
            )
            Spacer(modifier = Modifier.weight(3f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = {},
            ) {
                Text(text = stringResource(id = R.string.next))
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
