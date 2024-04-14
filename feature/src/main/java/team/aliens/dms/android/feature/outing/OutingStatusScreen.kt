package team.aliens.dms.android.feature.outing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.AlertDialog
import team.aliens.dms.android.core.designsystem.Button
import team.aliens.dms.android.core.designsystem.ButtonDefaults
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.ShadowDefaults
import team.aliens.dms.android.core.designsystem.TextButton
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.outing.navigation.OutingNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun OutingStatusScreen(
    navigator: OutingNavigator,
    viewModel: OutingViewModel,
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val (shouldShowCancelOutingApplicationDialog, onChangeShouldShowCancelOutingDialog) = remember {
        mutableStateOf(false)
    }
    val toast = LocalToast.current
    val context = LocalContext.current
    if (shouldShowCancelOutingApplicationDialog) {
        AlertDialog(
            title = { Text(text = stringResource(id = R.string.outing_cancel_application)) },
            text = { Text(text = stringResource(id = R.string.outing_are_you_sure_you_cancel_application)) },
            onDismissRequest = { onChangeShouldShowCancelOutingDialog(false) },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.postIntent(OutingIntent.CancelCurrentApplication)
                        onChangeShouldShowCancelOutingDialog(false)
                    },
                ) {
                    Text(text = stringResource(id = R.string.accept))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onChangeShouldShowCancelOutingDialog(false) },
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            },
        )
    }

    LaunchedEffect(uiState.applicationId != null) {
        viewModel.postIntent(OutingIntent.FetchCurrentAppliedOutingApplication)
    }

    Scaffold(
        topBar = {
            DmsTopAppBar(
                title = { Text(text = stringResource(id = R.string.outing_application)) },
                navigationIcon = {
                    IconButton(onClick = navigator::navigateUp) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(id = R.string.top_bar_back_button),
                        )
                    }
                },
            )
        },
    ) { padValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .startPadding()
                        .verticalPadding(),
                    text = stringResource(id = R.string.outing_recent_application),
                    color = DmsTheme.colorScheme.icon,
                )
                uiState.currentAppliedOutingApplication?.let { outingApplication ->
                    OutingInformationCard(
                        title = outingApplication.type,
                        date = outingApplication.date,
                        time = stringResource(
                            id = R.string.outing_format_duration_h_m,
                            outingApplication.startTime,
                            outingApplication.endTime
                        ),
                        companionNames = outingApplication.companionNames,
                        reason = outingApplication.reason,
                        onCancelApplication = { onChangeShouldShowCancelOutingDialog(true) },
                    )
                } ?: Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .topPadding(),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.outing_failed_to_fetch_current_applied_outing_application)
                )
            }
            if (uiState.currentAppliedOutingApplication == null) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .bottomPadding()
                        .horizontalPadding(),
                    onClick = navigator::openOutingApplication,
                ) {
                    Text(text = stringResource(id = R.string.outing_do_application))
                }
            }
        }
    }
}

@Composable
private fun OutingInformationCard(
    modifier: Modifier = Modifier,
    title: String,
    date: String,
    time: String,
    companionNames: List<String>,
    reason: String?,
    onCancelApplication: () -> Unit,
) {

    Card(
        modifier = modifier
            .horizontalPadding()
            .verticalPadding(PaddingDefaults.ExtraSmall),
        shape = DmsTheme.shapes.surfaceMedium,
        colors = CardDefaults.cardColors(
            containerColor = DmsTheme.colorScheme.surface,
            contentColor = DmsTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = ShadowDefaults.SmallElevation),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .topPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = date,
                    style = DmsTheme.typography.caption,
                    color = DmsTheme.colorScheme.primary,
                )
                TextButton(
                    onClick = onCancelApplication,
                    colors = ButtonDefaults.textErrorButtonColors(),
                ) {
                    Text(text = stringResource(id = R.string.outing_cancel_application))
                }
            }
            Text(
                modifier = Modifier.startPadding(),
                text = title,
                style = DmsTheme.typography.title3,
                fontWeight = FontWeight.Bold,
            )
            Text(
                modifier = Modifier.startPadding(),
                text = time,
                style = DmsTheme.typography.body2,
            )
            if (companionNames.isNotEmpty()) {
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace),
                ) {
                    item {
                        Text(
                            modifier = Modifier.startPadding(),
                            text = stringResource(id = R.string.outing_companion_names),
                            style = DmsTheme.typography.body2,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    itemsIndexed(companionNames) { index, name ->
                        Text(
                            text = if (index == companionNames.lastIndex) {
                                name
                            } else {
                                "$name, "
                            },
                            style = DmsTheme.typography.body2,
                        )
                    }
                }
            }
            if (reason != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding()
                        .bottomPadding(),
                    colors = CardDefaults.cardColors(
                        containerColor = DmsTheme.colorScheme.background,
                        contentColor = DmsTheme.colorScheme.onBackground,
                    ),
                ) {
                    Text(
                        modifier = Modifier
                            .horizontalPadding()
                            .verticalPadding(),
                        text = reason,
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(DefaultVerticalSpace))
            }
        }
    }
}
