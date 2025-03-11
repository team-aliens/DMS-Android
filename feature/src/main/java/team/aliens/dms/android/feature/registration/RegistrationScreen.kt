package team.aliens.dms.android.feature.registration

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.point.navigation.PointHistoryNavigator


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RegistrationScreen(
    modifier: Modifier = Modifier,
    navigator: PointHistoryNavigator,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = { Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("My App")
                } },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = team.aliens.dms.android.core.designsystem.R.drawable.chevronleft),
                            contentDescription = stringResource(R.string.application_submit)
                        )
                    }
                },
            )
        },
    ) { padValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padValues)
        ) {

        }
    }
}
