package team.aliens.dms_android.feature.auth.comparepassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.typography.Body2
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.util.TopBar
import team.aliens.dms_android.viewmodel.changepw.ChangePasswordViewModel
import team.aliens.presentation.R

@Composable
fun ComparePasswordScreen(
    navController: NavController,
    changePasswordViewModel: ChangePasswordViewModel = hiltViewModel(),
) {

    var password by remember { mutableStateOf("")}

    val onPasswordChange = { value: String ->
        password = value
        // TODO 뷰모델 함수 호출해주기
    }

    LaunchedEffect(Unit){
        // TODO 뷰모델 event collect 해주기
    }

    Column {
        TopBar(title = stringResource(id = R.string.ChangePassword)){
            navController.popBackStack()
        }
        Column(
            modifier = Modifier.padding(
                top = 50.dp,
                start = 16.dp,
                end = 16.dp,
            )
        ){
            AppLogo()
            Spacer(modifier = Modifier.height(32.dp))
            Body2(text = stringResource(id = R.string.OriginPw))
            Spacer(modifier = Modifier.height(100.dp))
            DormTextField(
                value = password,
                onValueChange = onPasswordChange,
                isPassword = true,
                hint = stringResource(id = R.string.Password),
                errorDescription = stringResource(id = R.string.CheckPassword),
                // TODO 에러 처리 해주기
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.8f))
            DormContainedLargeButton(
                text = stringResource(id = R.string.Next),
                color = DormButtonColor.Blue,
                enabled = password.isNotEmpty(),
            ){
                // TODO 뷰모델 함수 호출해주기
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    ComparePasswordScreen(navController = rememberNavController())
}