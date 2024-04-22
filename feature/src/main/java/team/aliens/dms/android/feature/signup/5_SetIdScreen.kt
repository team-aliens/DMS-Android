package team.aliens.dms.android.feature.signup

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.delay
import team.aliens.dms.android.core.designsystem.AlertDialog
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsIcon
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
import team.aliens.dms.android.core.ui.topPadding
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
    val (studentName, setStudentName) = remember { mutableStateOf("") }
    var shouldShowUserConfirmationCard by remember { mutableStateOf(false) }
    val onShouldShowUserConfirmationCardChange = remember {
        { studentName: String ->
            setStudentName(studentName)
            shouldShowUserConfirmationCard = true
        }
    }
    val toast = LocalToast.current
    val context = LocalContext.current

    val (studentConfirmed, onStudentConfirmedChange) = remember { mutableStateOf(false) }

    val (gradeClassNumberError, setGradeClassNumberError) = remember { mutableStateOf(false) }

    LaunchedEffect(uiState.grade, uiState.classroom, uiState.number) {
        if (gradeClassNumberError) {
            setGradeClassNumberError(false)
        }
        if (uiState.grade.isNotEmpty() && uiState.classroom.isNotEmpty() && uiState.number.isNotEmpty()) {
            delay(300L)
            viewModel.postIntent(SignUpIntent.ExamineStudent)
        }
    }

    val (shouldShowQuitSignUpDialog, onShouldShowQuitSignUpDialogChange) = remember {
        mutableStateOf(false)
    }
    if (shouldShowQuitSignUpDialog) {
        AlertDialog(
            title = { Text(text = stringResource(id = R.string.sign_up)) },
            text = { Text(text = stringResource(id = R.string.sign_up_info_are_you_sure_you_quit_sign_up)) },
            onDismissRequest = { /* explicit blank */ },
            confirmButton = {
                TextButton(
                    onClick = navigator::popUpToSignUp,
                ) {
                    Text(text = stringResource(id = R.string.accept))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onShouldShowQuitSignUpDialogChange(false) },
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            },
        )
    }

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is SignUpSideEffect.UserFound -> onShouldShowUserConfirmationCardChange(sideEffect.studentName)
            SignUpSideEffect.UserNotFound -> setGradeClassNumberError(true)
            SignUpSideEffect.IdAvailable -> navigator.openSignUpSetPassword()
            SignUpSideEffect.IdDuplicated -> toast.showErrorToast(
                message = context.getString(R.string.sign_up_set_id_error_student_duplicated),
            )

            else -> {/* explicit blank */
            }
        }
    }

    DmsScaffold(
        topBar = {
            DmsTopAppBar(
                navigationIcon = {
                    IconButton(onClick = navigator::navigateUp) {
                        Icon(
                            painter = painterResource(id = DmsIcon.Back),
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
            Banner(
                modifier = Modifier
                    .fillMaxWidth()
                    .topPadding(BannerDefaults.DefaultTopSpace)
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
                    value = uiState.grade,
                    hint = { Text(text = stringResource(id = R.string.sign_up_set_id_hint_grade)) },
                    onValueChange = { viewModel.postIntent(SignUpIntent.UpdateGrade(value = it)) },
                    supportingText = {},
                    isError = gradeClassNumberError,
                    enabled = !studentConfirmed,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.NumberPassword,
                    ),
                )
                TextField(
                    modifier = Modifier.weight(1f),
                    value = uiState.classroom,
                    hint = { Text(text = stringResource(id = R.string.sign_up_set_id_hint_class)) },
                    onValueChange = { viewModel.postIntent(SignUpIntent.UpdateClass(value = it)) },
                    supportingText = {},
                    isError = gradeClassNumberError,
                    enabled = !studentConfirmed,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.NumberPassword,
                    ),
                )
                TextField(
                    modifier = Modifier.weight(1f),
                    value = uiState.number,
                    hint = { Text(text = stringResource(id = R.string.sign_up_set_id_hint_number)) },
                    onValueChange = { viewModel.postIntent(SignUpIntent.UpdateNumber(value = it)) },
                    supportingText = {},
                    isError = gradeClassNumberError,
                    enabled = !studentConfirmed,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.NumberPassword,
                    ),
                )
            }
            Spacer(modifier = Modifier.height(DefaultVerticalSpace))
            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = shouldShowUserConfirmationCard,
            ) {
                StudentInformationCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding(),
                    studentName = studentName,
                    onConfirmClick = {
                        onStudentConfirmedChange(true)
                        shouldShowUserConfirmationCard = false
                    },
                )
            }
            Spacer(modifier = Modifier.height(DefaultVerticalSpace))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                value = uiState.id,
                hint = { Text(text = stringResource(id = R.string.sign_up_set_id_please_enter_id)) },
                onValueChange = { viewModel.postIntent(SignUpIntent.UpdateId(value = it)) },
                enabled = studentConfirmed,
            )
            Spacer(modifier = Modifier.weight(3f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = { viewModel.postIntent(SignUpIntent.ConfirmId) },
                enabled = uiState.id.isNotEmpty() && studentConfirmed,
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
    BackHandler {
        onShouldShowQuitSignUpDialogChange(true)
    }
}

@Composable
private fun StudentInformationCard(
    modifier: Modifier = Modifier,
    studentName: String,
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
                text = stringResource(
                    id = R.string.sign_up_set_id_format_authentication_information_student_name,
                    studentName
                ),
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
