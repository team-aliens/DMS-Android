package team.aliens.dms_android.feature._legacy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.design_system.R
import team.aliens.dms_android.design_system.extension.Space
import team.aliens.dms_android.design_system.modifier.dormShadow
import team.aliens.dms_android.design_system.theme.DormTheme
import team.aliens.dms_android.design_system.typography.Caption

@Composable
fun FloatingNotice(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(
                minHeight = 50.dp,
            )
            .dormShadow(
                color = DormTheme.colors.primaryVariant,
                offsetY = 1.dp,
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
                    vertical = 4.dp,
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
            Caption(text = text)
        }
    }
}