package team.aliens.dms_android.feature.auth.findid

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body2
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.presentation.R
import java.util.*

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
                mainBtnTextColor = DormColor.DormPrimary,
            )
        }
    }

    LaunchedEffect(Unit) {
        findIdViewModel.findIdEvent.collect {
            when (it) {
                SuccessFindId -> findIdDialogState = true
                FindIdNoInternetException -> toast(context.getString(R.string.NoInternetException))
                FindIdServerException -> toast(context.getString(R.string.ServerException))
                FindIdTooManyRequest -> toast(context.getString(R.string.TooManyRequest))
                FindIdUnknownException -> toast(context.getString(R.string.UnKnownException))
                FindIdNeedLoginException -> toast(context.getString(R.string.NeedAccount))
                FindIdBadRequest -> toast(context.getString(R.string.BadRequest))
                FindIdNotFound -> toast(context.getString(R.string.NotFound))
                FindIdUnauthorized -> toast(context.getString(R.string.MissMatchAccountInfo))
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        FindIdHeader()
        Spacer(modifier = Modifier.height(60.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            SchoolDropdownMenu()
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
                    schoolId = UUID.fromString("918bffd6-6c7e-11ed-a1eb-0242ac120002"),
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
        Image(
            modifier = Modifier
                .height(34.dp)
                .width(97.dp),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
        )
        Body2(
            text = stringResource(
                id = R.string.FindId,
            ),
            color = DormColor.Gray600
        )
    }
}

@Composable
fun SchoolDropdownMenu() {
    var isDropdownMenuExpanded by remember { mutableStateOf(false) }
    val schoolList = listOf("대덕소프트웨어마이스터고등학교")
    var schoolName: String by remember { mutableStateOf(schoolList[0]) }

    Box() {
        Row(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    shape = MaterialTheme.shapes.small,
                    color = DormColor.Gray500,
                )
                .clickable {
                    isDropdownMenuExpanded = !isDropdownMenuExpanded
                }
                .height(46.dp)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Body2(
                text = schoolName,
                color = DormColor.Gray500
            )
            Icon(painterResource(id = R.drawable.ic_down), contentDescription = null)
        }

        DropdownMenu(
            expanded = isDropdownMenuExpanded,
            onDismissRequest = { isDropdownMenuExpanded = false }) {
            schoolList.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        isDropdownMenuExpanded = false
                        schoolName = item
                    }
                ) {
                    Text(item)
                }
            }
        }
    }


}