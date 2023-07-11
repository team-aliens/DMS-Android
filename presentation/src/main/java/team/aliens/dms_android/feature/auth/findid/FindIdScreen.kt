package team.aliens.dms_android.feature.auth.findid

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.LocalToast
import team.aliens.design_system.typography.Body2
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.extension.collectInLaunchedEffectWithLifeCycle
import team.aliens.domain.model.school.FetchSchoolsOutput
import team.aliens.presentation.R

@Composable
internal fun FindIdScreen(
    findIdViewModel: FindIdViewModel = hiltViewModel(),
    onNavigateToSignIn: () -> Unit,
) {
    val uiState by findIdViewModel.stateFlow.collectAsStateWithLifecycle()
    val (findIdDialogState, setFindIdDialogState) = remember { mutableStateOf(false) }

    val toast = LocalToast.current
    val context = LocalContext.current

    findIdViewModel.sideEffectFlow.collectInLaunchedEffectWithLifeCycle { sideEffect ->
        when (sideEffect) {
            FindIdSideEffect.BadRequest -> toast.showErrorToast(
                message = context.getString(R.string.find_id_error_check_class_and_grade_and_number),
            )

            FindIdSideEffect.NotFound -> toast.showErrorToast(
                message = context.getString(R.string.find_id_error_account_not_found),
            )

            // todo remind
            is FindIdSideEffect.FindIdSuccess -> {
                sideEffect.email
                onNavigateToSignIn()
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.height(92.dp))
        FindIdHeader(Modifier)
        Spacer(Modifier.height(60.dp))
        // drop down
        SchoolsDropDownMenu(
            modifier = Modifier,
        )
        Space(space = 37.dp)
        UserInformationInputs(
            modifier = Modifier,
            gradeValue = uiState.grade,
            classRoomValue = uiState.classRoom,
            numberValue = uiState.number,
            nameValue = uiState.name,
            onGradeChange = { newGrade: String ->
                findIdViewModel.postIntent(FindIdIntent.UpdateGrade(newGrade))
            },
            onClassRoomChange = { newClassRoom: String ->
                findIdViewModel.postIntent(FindIdIntent.UpdateClassRoom(newClassRoom))
            },
            onNumberChange = { newNumber: String ->
                findIdViewModel.postIntent(FindIdIntent.UpdateNumber(newNumber))
            },
            onNameChange = { newName: String ->
                findIdViewModel.postIntent(FindIdIntent.UpdateName(newName))
            },
            gradeError = uiState.gradeError,
            classRoomError = uiState.classRoomError,
            numberError = uiState.numberError,
            nameError = uiState.nameError,
        )
        Spacer(Modifier.weight(1f))
        DormContainedLargeButton(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 57.dp),
            text = stringResource(id = R.string.find_id),
            color = DormButtonColor.Blue,
            enabled = uiState.findIdButtonEnabled,
            onClick = { findIdViewModel.postIntent(FindIdIntent.FindId) },
        )
    }
}

@Composable
private fun SchoolsDropDownMenu(
    modifier: Modifier = Modifier,
) {
    var isDropDownMenuExpended by remember { mutableStateOf(false) }
    var schoolList = remember {
        mutableStateListOf<FetchSchoolsOutput.SchoolInformation>()
    }

    var selectedSchool by remember {
        mutableStateOf(
            FetchSchoolsOutput.SchoolInformation(
                name = "",
                address = "",
            ),
        )
    }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterEnd,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .clickable {
                        isDropDownMenuExpended = !isDropDownMenuExpended
                    }
                    .border(
                        width = 1.dp,
                        shape = MaterialTheme.shapes.small,
                        color = DormTheme.colors.primaryVariant,
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Body2(
                    modifier = Modifier.weight(1f),
                    text = (if (selectedSchool.name != null) selectedSchool.name else {
                        stringResource(id = R.string.find_id_select_school)
                    }),
                    color = DormTheme.colors.primaryVariant,
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_down),
                    contentDescription = null,
                )
                DropdownMenu(
                    modifier = Modifier.weight(1f),
                    expanded = isDropDownMenuExpended,
                    onDismissRequest = { isDropDownMenuExpended = false },
                ) {
                    schoolList.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                isDropDownMenuExpended = false
                                selectedSchool = item
                            },
                        ) {
                            Text(text = item.name)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UserInformationInputs(
    modifier: Modifier,
    gradeValue: String,
    numberValue: String,
    classRoomValue: String,
    nameValue: String,
    onGradeChange: (String) -> Unit,
    onClassRoomChange: (String) -> Unit,
    onNumberChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    gradeError: Boolean,
    classRoomError: Boolean,
    numberError: Boolean,
    nameError: Boolean,
) {
    val focusManager = LocalFocusManager.current

    DormTextField(
        modifier = Modifier.padding(horizontal = 16.dp),
        value = nameValue,
        onValueChange = onNameChange,
        hint = stringResource(id = R.string.find_id_name),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            },
        ),
        error = nameError,
    )
    Spacer(Modifier.height(16.dp))
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(36.dp),
    ) {
        DormTextField(
            modifier = Modifier.fillMaxWidth(0.292f),
            value = gradeValue,
            onValueChange = onGradeChange,
            hint = stringResource(id = R.string.find_id_grade),
            error = gradeError,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.NumberPassword,
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                },
            ),
        )
        DormTextField(
            modifier = Modifier.fillMaxWidth(0.45f),
            value = classRoomValue,
            onValueChange = onClassRoomChange,
            hint = stringResource(id = R.string.find_id_class_room),
            error = classRoomError,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.NumberPassword,
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                },
            ),
        )
        DormTextField(
            modifier = Modifier.fillMaxWidth(),
            value = numberValue,
            onValueChange = onNumberChange,
            hint = stringResource(id = R.string.find_id_number),
            error = numberError,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.NumberPassword,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                },
            ),
        )
    }
}

@Composable
private fun FindIdHeader(modifier: Modifier) {
    Column(
        modifier = modifier
            .wrapContentWidth()
            .height(60.dp)
            .padding(
                start = 16.dp,
            ), verticalArrangement = Arrangement.SpaceBetween
    ) {

        AppLogo(
            darkIcon = isSystemInDarkTheme(),
        )

        Body2(
            text = stringResource(
                id = R.string.FindId,
            ),
        )
    }
}