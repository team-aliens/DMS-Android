package team.aliens.dms.android.feature.remain.ui.component

import android.R
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.text.TextAutoSizeDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.widget.TextViewCompat
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.foundation.DmsIcon
import team.aliens.dms.android.core.designsystem.labelM

@Composable
fun DmsFloatingNotice(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes iconResource: Int = DmsIcon.Notification,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = DmsTheme.colorScheme.primary,
                shape = RoundedCornerShape(30.dp),
            ).padding(
                horizontal = 22.dp,
                vertical = 12.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Icon(
            painter = painterResource(iconResource),
            contentDescription = null,
        )
        BasicText(
            text = text,
            style = DmsTheme.typography.labelM.copy(
                color = DmsTheme.colorScheme.onBackground,
            ),
            autoSize = TextAutoSize.StepBased(
                minFontSize = 1.sp,
                maxFontSize = 50.sp,
            ),
            maxLines = 1,
        )
    }
}
