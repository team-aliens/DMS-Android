package team.aliens.dms.android.feature.findid

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.ui.composable.AppLogo
import team.aliens.dms.android.core.designsystem.typography.Body2
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.findid.navigator.FindIdNavigator

@Destination
@Composable
fun FindIdScreen(
    modifier: Modifier = Modifier,
    navigator: FindIdNavigator,
    // findIdViewModel: FindIdViewModel = hiltViewModel(),
) {/*

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
                onMainBtnClick = navigator::popBackStack,
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
        modifier = modifier
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
                        painterResource(id = team.aliens.dms.android.designsystem.R.drawable.ic_down),
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
    }*/
}
