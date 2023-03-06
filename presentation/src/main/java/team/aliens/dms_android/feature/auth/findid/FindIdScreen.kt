package team.aliens.dms_android.feature.auth.findid

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.dialog.DormCustomDialog
import team.aliens.design_system.dialog.DormSingleButtonDialog
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body2
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.domain.entity.schools.SchoolEntity
import team.aliens.presentation.R

@Composable
fun FindIdScreen(
    navController: NavController,
    findIdViewModel: FindIdViewModel = hiltViewModel(),
) {

    var nameState by remember { mutableStateOf("") }
    var gradeState by remember { mutableStateOf("") }
    var classRoomState by remember { mutableStateOf("") }
    var numberState by remember { mutableStateOf("") }
    var findIdDialogState by remember { mutableStateOf(false) }
    var errorState by remember { mutableStateOf(false) }

    var isDropdownMenuExpanded by remember { mutableStateOf(false) }
    var schoolList = remember {
        mutableStateListOf<SchoolEntity>()
    }

    var selectedSchool by remember {
        mutableStateOf(
            SchoolEntity(
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
                onMainBtnClick = {
                    navController.navigate(NavigationRoute.Login) {
                        popUpTo(NavigationRoute.Login) {
                            inclusive = true
                        }
                    }
                },
                mainBtnTextColor = DormTheme.colors.primary,
            )
        }
    }

    LaunchedEffect(Unit) {
        findIdViewModel.findIdEvent.collect { event ->
            when (event) {
                is FetchSchools -> {
                    schoolList.addAll(event.schoolsEntity.also {
                        println(it)
                    })
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
            .padding(horizontal = 16.dp) // todo color
    ) {
        FindIdHeader()
        Spacer(modifier = Modifier.height(60.dp))
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
                    Icon(painterResource(
                        id = R.drawable.ic_down,
                    ), contentDescription = null)
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

            Spacer(modifier = Modifier.height(37.dp))
            DormTextField(
                value = nameState,
                onValueChange = { name -> nameState = name },
                hint = stringResource(id = R.string.Name),
                imeAction = ImeAction.Next
            )
            Spacer(modifier = Modifier.height(37.dp))
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
                    imeAction = ImeAction.Done,
                    error = errorState
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 57.dp),
            verticalArrangement = Arrangement.Bottom
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
    Spacer(modifier = Modifier.height(92.dp))
    Column(
        modifier = Modifier
            .wrapContentWidth()
            .height(68.dp),
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
