package team.aliens.dms_android.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.typography.Body1
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.typography.SubTitle2
import team.aliens.presentation.R

private val IconSize = DpSize(width = 24.dp, height = 24.dp)

@Composable
fun TopBar(
    title: String,
    onPrevious: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .background(color = DormColor.Gray100),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.padding(start = 24.dp))
        Image(
            modifier = Modifier
                .size(IconSize)
                .dormClickable(
                    rippleEnabled = false,
                ) {
                    if (onPrevious != null) {
                        onPrevious()
                    }
                },
            painter = painterResource(id = DormIcon.BackArrow.drawableId),
            contentDescription = stringResource(id = R.string.BackButton),
        )
        Spacer(modifier = Modifier.width(32.dp))
        Body3(text = title)
    }
}
