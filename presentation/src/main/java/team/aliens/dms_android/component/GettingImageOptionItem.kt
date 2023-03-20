package team.aliens.dms_android.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.design_system.extension.Space
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body2

@Composable
internal fun GettingImageOptionItem(
    icon: Int,
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(
                id = icon,
            ),
            tint = DormTheme.colors.primaryVariant,
            contentDescription = null,
        )

        Space(space = 16.dp)

        Body2(
            text = text,
            color = DormTheme.colors.primaryVariant,
        )
    }
}
