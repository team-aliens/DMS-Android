package team.aliens.dms_android.util.compose

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.design_system.extension.Space

@Composable
fun BoxScope.DormToastWrapper(
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.height(64.dp),
    ) {

        Space(space = 14.dp)

        content()
    }
}