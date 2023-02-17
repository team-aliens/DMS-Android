package team.aliens.dms_android.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.design_system.R
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.modifier.dormShadow
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
                color = DormColor.Gray500,
                offsetY = 1.dp,
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(100),
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .size(26.dp),
                painter = painterResource(id = R.drawable.coloricnotice),
                contentDescription = "NoticeIcon",
            )
            Spacer(modifier = Modifier.width(13.dp))
            Body5(text = content)
        }
    }
}