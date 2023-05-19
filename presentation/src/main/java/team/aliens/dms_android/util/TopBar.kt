package team.aliens.dms_android.util

// todo 적절한 위치로 이동

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.aliens.design_system.extension.Space
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body1
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
            .background(color = DormTheme.colors.surface),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Space(space = 24.dp)
        Icon(
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
            tint = DormTheme.colors.onBackground,
        )
        Space(space = 32.dp)
        Body1(text = title)
    }
}
