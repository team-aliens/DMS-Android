package team.aliens.dms_android.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.design_system.color.DormColor
import com.example.design_system.icon.DormIcon
import com.example.design_system.modifier.dormClickable
import com.example.design_system.typography.SubTitle2
import com.example.dms_android.R

private val IconSize = DpSize(width = 24.dp, height = 24.dp)

@Composable
fun TopBar(
    title: String,
    onPrevious: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .height(70.dp)
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .background(color = DormColor.Gray100),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .size(IconSize)
                .dormClickable {
                    if (onPrevious != null) {
                        onPrevious()
                    }
                },
            painter = painterResource(id = DormIcon.BackArrow.drawableId),
            contentDescription = stringResource(id = R.string.backButton),
        )
        Spacer(modifier = Modifier.width(32.dp))
        SubTitle2(text = title)
    }
}
