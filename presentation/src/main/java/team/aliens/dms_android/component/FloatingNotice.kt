package team.aliens.dms_android.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.design_system.R
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body5

@Composable
fun FloatingNotice(
    content: String,
) {
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .dormShadow(
                color = DormTheme.colors.primaryVariant,
                offsetY = 8.dp,
            )
            .background(
                color = DormTheme.colors.surface,
                shape = RoundedCornerShape(100),
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.size(26.dp),
                painter = painterResource(id = R.drawable.coloricnotice),
                contentDescription = "NoticeIcon",
            )
            Space(space = 13.dp)
            Body5(text = content)
        }
    }
}