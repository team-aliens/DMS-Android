package team.aliens.dms.android.feature.voting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.voting.navigation.VotingNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun VotingDetailScreen(
    modifier: Modifier = Modifier,
    navigator: VotingNavigator,
    votingDetailViewModel: VotingViewModel = hiltViewModel()
) {
    val uiState by votingDetailViewModel.stateFlow.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(stringResource(R.string.voting_submit))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigator::navigateUp) {
                        Icon(
                            painter = painterResource(team.aliens.dms.android.core.designsystem.R.drawable.chevronleft),
                            contentDescription = stringResource(R.string.voting_submit)
                        )
                    }
                },
            )
        }
    ) { padValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padValues)
        ) {
            Text(
                modifier = modifier
                    .padding(start = 24.dp, top = 20.dp, bottom = 8.dp),
                text = stringResource(R.string.voting_option_check),
                style = DmsTheme.typography.headline3
            )
        }
    }
}

@Composable
fun StudentButton() {
    OutlinedButton(
        modifier = Modifier,
        onClick = {},
        colors = ButtonColors(
            containerColor = Color.White,
            contentColor = DmsTheme.colorScheme.primary,
            disabledContainerColor = DmsTheme.colorScheme.primary,
            disabledContentColor = Color.White,
        ),
        border = BorderStroke(1.dp, DmsTheme.colorScheme.primary),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 14.dp),
            text = "3학년"
        )
    }
}

@Preview
@Composable
fun Preview() {
    DmsTheme {
        StudentButton()
    }
}
