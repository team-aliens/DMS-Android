package team.aliens.dms_android.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DmsApp(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun DmsApp(
    modifier: Modifier = Modifier,
) {
    // TODO: auto sign in
    val autoSignIn by remember { mutableStateOf(false) }

    DmsNavHost(
        modifier = modifier,
        autoSignIn = autoSignIn,
    )
}
