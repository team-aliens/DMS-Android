package team.aliens.dms.android.feature.findid

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.AlertDialog
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsIcon
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.TextField
import team.aliens.dms.android.core.designsystem.clickable
import team.aliens.dms.android.core.ui.Banner
import team.aliens.dms.android.core.ui.BannerDefaults
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.collectInLaunchedEffectWithLifecycle
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.findid.navigator.FindIdNavigator

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun FindIdScreen(
    navigator: FindIdNavigator,
    viewModel: FindIdViewModel = hiltViewModel(),
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val toast = LocalToast.current
    val context = LocalContext.current
    var isDialogShow by remember { mutableStateOf(false) }

    if (isDialogShow) {
        AlertDialog(
            text = {
                Text(
                    text = String.format(
                        stringResource(id = R.string.find_id_send_id_to_email),
                        uiState.email,
                    )
                )
            },
            onDismissRequest = { /* explicit blank */ },
            confirmButton = {
                ContainedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = navigator::navigateUp,
                ) {
                    Text(text = stringResource(id = R.string.find_id_go_to_login_screen))
                }
            },
        )
    }

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            FindIdSideEffect.UserFound -> {
                isDialogShow = true
            }

            FindIdSideEffect.UserNotFound -> toast.showErrorToast(context.getString(R.string.error_not_found))
            FindIdSideEffect.SchoolNotFound -> toast.showErrorToast(context.getString(R.string.find_id_error_school_not_found))
            FindIdSideEffect.SchoolNotSelected -> toast.showErrorToast(context.getString(R.string.find_id_select_school))
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DmsTopAppBar(
                title = { /* explicit blank */ },
                navigationIcon = {
                    IconButton(onClick = navigator::navigateUp) {
                        Icon(
                            painter = painterResource(id = DmsIcon.Back),
                            contentDescription = stringResource(id = R.string.top_bar_back_button),
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .horizontalPadding()
                .imePadding(),
        ) {
            Banner(
                modifier = Modifier
                    .fillMaxWidth()
                    .topPadding(BannerDefaults.DefaultTopSpace),
                message = {
                    Text(text = stringResource(id = R.string.find_id))
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            UserInformationInputs(
                state = uiState,
                viewModel = viewModel,
            )
            Spacer(modifier = Modifier.weight(2f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .bottomPadding(),
                onClick = { viewModel.postIntent(FindIdIntent.FindId) },
                enabled = uiState.buttonEnabled,
            ) {
                Text(text = stringResource(id = R.string.find_id))
            }
        }
    }
}

@Composable
private fun UserInformationInputs(
    state: FindIdUiState,
    viewModel: FindIdViewModel,
) {
    val classFocusRequest = remember { FocusRequester() }
    val numberFocusRequest = remember { FocusRequester() }
    var isDropdownMenuExpanded by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace)
    ) {
        Box {
            TextField(
                modifier = Modifier.clickable(
                    onClick = { isDropdownMenuExpanded = !isDropdownMenuExpanded }
                ),
                value = state.selectedSchool?.name ?: "",
                onValueChange = { },
                readOnly = true,
                hint = { Text(text = stringResource(id = R.string.find_id_select_school)) },
                trailingIcon = {
                    IconButton(onClick = { isDropdownMenuExpanded = !isDropdownMenuExpanded }) {
                        Icon(
                            painter = painterResource(
                                if (isDropdownMenuExpanded) {
                                    DmsIcon.Down
                                } else {
                                    DmsIcon.Up
                                }
                            ),
                            contentDescription = stringResource(id = R.string.find_id_show_menu),
                        )
                    }
                }
            )
            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth(),
                expanded = isDropdownMenuExpanded,
                onDismissRequest = { isDropdownMenuExpanded = false },
            ) {
                state.schoolList?.forEach { school ->
                    DropdownMenuItem(
                        text = { Text(text = school.name) },
                        onClick = {
                            viewModel.postIntent(
                                FindIdIntent.UpdateSchoolId(value = school)
                            )
                            isDropdownMenuExpanded = false
                        },
                        contentPadding = PaddingValues(horizontal = 16.dp),
                    )
                }
            }
        }
        TextField(
            value = state.name,
            onValueChange = { name ->
                viewModel.postIntent(FindIdIntent.UpdateName(name))
            },
            hint = { Text(text = stringResource(id = R.string.find_id_name)) }
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace)
        ) {
            TextField(
                modifier = Modifier
                    .weight(1f),
                value = state.grade,
                onValueChange = { grade ->
                    viewModel.postIntent(FindIdIntent.UpdateGrade(grade))
                    if (grade.isNotEmpty()) classFocusRequest.requestFocus()
                },
                hint = { Text(text = stringResource(id = R.string.find_id_grade)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number,
                ),
            )
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(classFocusRequest),
                value = state.classRoom,
                onValueChange = { classRoom ->
                    viewModel.postIntent(FindIdIntent.UpdateClass(classRoom))
                    if (classRoom.isNotEmpty()) numberFocusRequest.requestFocus()
                },
                hint = { Text(text = stringResource(id = R.string.find_id_class)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number,
                ),
            )
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(numberFocusRequest),
                value = state.number,
                onValueChange = { number ->
                    viewModel.postIntent(FindIdIntent.UpdateNumber(number))
                },
                hint = { Text(text = stringResource(id = R.string.find_id_number)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number,
                ),
            )
        }
    }
}
