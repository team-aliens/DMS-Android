package team.aliens.dms_android.feature.feature.home.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.extension.Space
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body4
import team.aliens.dms_android.feature.component.AppLogo
import team.aliens.dms_android.feature.util.TopBar
import team.aliens.dms_android.feature.R

@Deprecated("no usage")
@Composable
fun CheckOriginPwScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormTheme.colors.background),
    ) {
        TopBar(
            title = stringResource(R.string.ChangePassword),
        )
        CheckPwTitle()
        Space(space = 110.dp)
        CheckPwValue()
    }
}

@Composable
fun CheckPwTitle() {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(start = 24.dp),
    ) {

        Space(space = 40.dp)

        AppLogo(
            darkIcon = isSystemInDarkTheme(),
        )

        Space(space = 20.dp)

        Body4(
            text = stringResource(R.string.OriginPw),
        )
    }
}

@Composable
fun CheckPwValue() {

    var passwordValue by remember { mutableStateOf("") }

    DormTextField(
        modifier = Modifier.padding(
            start = 24.dp,
            end = 24.dp,
        ),
        value = passwordValue,
        onValueChange = { passwordValue = it },
        hint = stringResource(R.string.EnterPassword),
        isPassword = true,
    )

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, bottom = 60.dp),
    ) {
        DormContainedLargeButton(
            text = stringResource(R.string.Next),
            color = DormButtonColor.Blue,
            enabled = false,
        ) {

        }
    }
}

@Preview
@Composable
fun CheckPwPreView() {
    CheckOriginPwScreen()
}
