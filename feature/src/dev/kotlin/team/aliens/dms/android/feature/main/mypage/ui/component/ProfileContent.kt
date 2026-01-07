package team.aliens.dms.android.feature.main.mypage.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.foundation.DmsIcon
import team.aliens.dms.android.core.designsystem.labelB
import team.aliens.dms.android.core.designsystem.labelM
import team.aliens.dms.android.core.designsystem.startPadding
import team.aliens.dms.android.shared.model.Sex

@Composable
internal fun ProfileContent(
    modifier: Modifier = Modifier,
    gcn: String,
    name: String,
    schoolName: String,
    genderType: Sex,
    profileImageUrl: String?,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = DmsTheme.colorScheme.surfaceTint, shape = RoundedCornerShape(32.dp))
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            model = profileImageUrl ?: DmsIcon.ProfileDefault,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier.startPadding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Text(
                    text = "$gcn $name",
                    style = DmsTheme.typography.bodyB,
                    color = DmsTheme.colorScheme.surfaceContainer,
                )
                GenderTag(genderType = genderType)
            }
            Text(
                text = schoolName,
                style = DmsTheme.typography.labelM,
                color = DmsTheme.colorScheme.inverseOnSurface,
            )
        }
    }
}

@Composable
private fun GenderTag(
    modifier: Modifier = Modifier,
    genderType: Sex,
) {
    val (text, textColor, backgroundColor) = when (genderType) {
        Sex.MALE -> Triple("남", DmsTheme.colorScheme.onPrimaryContainer, DmsTheme.colorScheme.primary)
        Sex.FEMALE -> Triple("여", DmsTheme.colorScheme.onErrorContainer, DmsTheme.colorScheme.error)
        Sex.ALL -> Triple("기타", DmsTheme.colorScheme.tertiaryContainer, DmsTheme.colorScheme.onSurface)
    }

    Box(
        modifier = modifier.background(
            color = backgroundColor,
            shape = RoundedCornerShape(12.dp),
        ).padding(
            horizontal = 12.dp,
            vertical = 8.dp,
        ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = DmsTheme.typography.labelB,
            color = textColor,
        )
    }
}
