package team.aliens.dms.android.wear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import team.aliens.dms.android.wear.ui.WearApp
import team.aliens.dms.android.wear.ui.WearTheme

class WearMainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearTheme {
                WearApp()
            }
        }
    }
}
