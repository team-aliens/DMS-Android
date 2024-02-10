package team.aliens.dms.android.feature.editpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.composable.AppLogo
import team.aliens.dms.android.feature.R

@Composable
fun Banner(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
    ) {
        AppLogo()
        Text(
            text = stringResource(R.string.edit_password_old_password),
            style = DmsTheme.typography.body2,
        )
    }
}
