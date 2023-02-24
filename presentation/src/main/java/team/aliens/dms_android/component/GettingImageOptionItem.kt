package team.aliens.dms_android.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.design_system.color.DormColor
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
            .padding(
                horizontal = 32.dp,
                vertical = 16.dp,
            )
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Icon(
            painter = painterResource(
                id = icon,
            ),
            tint = DormColor.Gray500,
            contentDescription = null,
        )

        Spacer(
            modifier = Modifier.width(16.dp),
        )

        Body2(
            text = text,
            color = DormColor.Gray500,
        )
    }
}
