package team.aliens.dms.android.feature.signin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.foundation.DmsSymbol
import team.aliens.dms.android.core.designsystem.textfield.DmsTextField
import team.aliens.dms.android.core.designsystem.titleB
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.topPadding

@Composable
internal fun SignIn() {
    SignInScreen()
}

@Composable
private fun SignInScreen() {
    val id = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .navigationBarsPadding()
            .background(DmsTheme.colorScheme.surfaceTint)
            .padding(top = 52.dp)
            .padding(horizontal = 24.dp),
    ) {
        DmsSymbol()
        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = "로그인",
            style = DmsTheme.typography.titleB,
            color = DmsTheme.colorScheme.onTertiaryContainer,
        )
        DmsTextField(
            value = id.value,
            label = "아이디",
            onValueChange = {  id.value = it },
            hint = "아이디 입력",
        )
    }
}
