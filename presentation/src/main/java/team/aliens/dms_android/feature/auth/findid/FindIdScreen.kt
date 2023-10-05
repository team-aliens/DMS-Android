package team.aliens.dms_android.feature.auth.findid

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.dialog.DormCustomDialog
import team.aliens.design_system.dialog.DormSingleButtonDialog
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body2
import team.aliens.dms_android.component.AppLogo
import team.aliens.domain.model.school.FetchSchoolsOutput
import team.aliens.dms_android.presentation.R

@Composable
fun FindIdScreen(
    onNavigateToSignIn: () -> Unit,
    findIdViewModel: FindIdViewModel = hiltViewModel(),
) {

    val focusManager = LocalFocusManager.current

    var nameState by remember { mutableStateOf("") }
    var gradeState by remember { mutableStateOf("") }
    var classRoomState by remember { mutableStateOf("") }
    var numberState by remember { mutableStateOf("") }
    var findIdDialogState by remember { mutableStateOf(false) }
    var errorState by remember { mutableStateOf(false) }

    var isDropdownMenuExpanded by remember { mutableStateOf(false) }
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

    val toast = rememberToast()
    val context = LocalContext.current

    if (findIdDialogState) {
        DormCustomDialog(
            onDismissRequest = {},
        ) {
            DormSingleButtonDialog(
                content = stringResource(id = R.string.SendIdToEmail, findIdViewModel.email),
                mainBtnText = stringResource(id = R.string.GoLoginScreen),
                onMainBtnClick = onNavigateToSignIn,
                mainBtnTextColor = DormTheme.colors.primary,
            )
        }
    }

    LaunchedEffect(Unit) {
        findIdViewModel.findIdEvent.collect { event ->
            when (event) {
                is FetchSchools -> {
                    schoolList.addAll(event.fetchSchoolsOutput.schools)
                }

                SuccessFindId -> {
                    findIdDialogState = true
                }

                FindIdNoInternetException -> {
                    toast(context.getString(R.string.NoInternetException))
                }

                FindIdServerException -> {
                    toast(context.getString(R.string.ServerException))
                }

                FindIdTooManyRequest -> {
                    toast(context.getString(R.string.TooManyRequest))
                }

                FindIdUnknownException -> {
                    toast(context.getString(R.string.UnKnownException))
                }

                FindIdNeedLoginException -> {
                    toast(context.getString(R.string.NeedAccount))
                }

                FindIdBadRequest -> {
                    toast(context.getString(R.string.BadRequest))
                }

                FindIdNotFound -> {
                    toast(context.getString(R.string.ChangePasswordNotFound))
                }

                FindIdUnauthorized -> {
                    toast(context.getString(R.string.MissMatchAccountInfo))
                }
            }
        }
    }

    Column( // todo refactor
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
            )
            .dormClickable(
                rippleEnabled = false,
            ) {
                focusManager.clearFocus()
            }
    ) {
        Space(space = 92.dp)
        FindIdHeader()
        Space(space = 60.dp)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Box() {
                Row(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .height(46.dp)
                        .fillMaxWidth()
                        .clickable {
                            isDropdownMenuExpanded = !isDropdownMenuExpanded
                        }
                        .border(
                            width = 1.dp,
                            shape = MaterialTheme.shapes.small,
                            color = DormTheme.colors.primaryVariant,
                        )

                        .padding(start = 16.dp, end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Body2(
                        text = selectedSchool.name,
                        color = DormTheme.colors.primaryVariant,
                    )
                    Icon(
                        painterResource(id = team.aliens.design_system.R.drawable.ic_down),
                        contentDescription = null,
                    )
                }

                DropdownMenu(
                    expanded = isDropdownMenuExpanded,
                    onDismissRequest = { isDropdownMenuExpanded = false },
                ) {
                    schoolList.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                isDropdownMenuExpanded = false
                                selectedSchool = item
                            }
                        ) {
                            Text(item.name)
                        }
                    }
                }
            }

            Space(space = 37.dp)
            DormTextField(
                value = nameState,
                onValueChange = { name -> nameState = name },
                hint = stringResource(id = R.string.Name),
                imeAction = ImeAction.Next
            )
            Space(space = 37.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DormTextField(
                    modifier = Modifier.width(100.dp),
                    value = gradeState,
                    onValueChange = { grade -> gradeState = grade },
                    hint = stringResource(id = R.string.Grade),
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Next,
                    error = errorState
                )
                DormTextField(
                    modifier = Modifier.width(100.dp),
                    value = classRoomState,
                    onValueChange = { classRoom -> classRoomState = classRoom },
                    hint = stringResource(id = R.string.ClassRoom),
                    error = errorState,
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Next
                )
                DormTextField(
                    modifier = Modifier.width(100.dp),
                    value = numberState,
                    onValueChange = { number -> numberState = number },
                    hint = stringResource(id = R.string.Number),
                    keyboardType = KeyboardType.NumberPassword,
                    error = errorState,
                    keyboardActions = KeyboardActions {
                        focusManager.clearFocus()
                    },
                    imeAction = ImeAction.Done,
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight(0.81f),
            verticalArrangement = Arrangement.Bottom,
        ) {
            DormContainedLargeButton(
                text = stringResource(id = R.string.FindId),
                color = DormButtonColor.Blue,
                enabled = nameState.isNotEmpty() && gradeState.isNotEmpty() && classRoomState.isNotEmpty() && numberState.isNotEmpty()
            ) {
                findIdViewModel.findId(
                    schoolId = selectedSchool.id ?: throw IllegalStateException(),
                    name = nameState,
                    grade = gradeState.toInt(),
                    classRoom = classRoomState.toInt(),
                    number = numberState.toInt(),
                )
            }
        }
    }
}

@Composable
fun FindIdHeader() {
    Column(
        modifier = Modifier
            .wrapContentWidth()
            .height(60.dp),
        verticalArrangement = Arrangement.SpaceBetween
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
