package team.aliens.dms.android.feature.resetpassword

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.delay
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.ShadowDefaults
import team.aliens.dms.android.core.designsystem.TextField
import team.aliens.dms.android.core.ui.Banner
import team.aliens.dms.android.core.ui.BannerDefaults
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.collectInLaunchedEffectWithLifecycle
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.resetpassword.navigation.ResetPasswordNavigator
import team.aliens.dms.android.shared.validator.checkIfEmailValid

// TODO Pop backstack
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AccountVerificationScreen(
    modifier: Modifier = Modifier,
    navigator: ResetPasswordNavigator,
    viewModel: ResetPasswordViewModel,
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val toast = LocalToast.current
    val context = LocalContext.current
    val (idChecked, onChangeIdChecked) = rememberSaveable { mutableStateOf(false) }

    val isAccountIdError by rememberSaveable(uiState.accountId) { mutableStateOf(false) } // TODO :: sideeffect로 true 구현

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            ResetPasswordSideEffect.AccountIdExists -> {
                onChangeIdChecked(true)
            }

            ResetPasswordSideEffect.AccountIdNotExists -> toast.showErrorToast(
                message = context.getString(R.string.reset_password_account_verification_account_id_does_not_exist),
            )

            ResetPasswordSideEffect.EmailVerificationUserNotFound -> toast.showErrorToast(
                message = context.getString(R.string.reset_password_account_verification_error_user_not_found),
            )

            ResetPasswordSideEffect.InvalidEmailFormat -> toast.showErrorToast(
                message = context.getString(R.string.reset_password_account_verification_error_invalid_email_format)
            )

            ResetPasswordSideEffect.SendEmailVerificationCodeSuccess -> navigator.openResetPasswordEnterEmailVerificationCode()

            else -> { /* explicit blank */ }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.openSignIn() },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(id = R.string.top_bar_back_button),
                        )
                    }
                },
            )
        },
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            Banner(
                modifier = Modifier
                    .fillMaxWidth()
                    .topPadding(BannerDefaults.DefaultTopSpace)
                    .startPadding(),
                message = {
                    BannerDefaults.DefaultText(text = stringResource(id = R.string.reset_password_account_verification_identification_verification))
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                value = uiState.accountId,
                hint = {
                    Text(text = stringResource(id = R.string.reset_password_account_verification_enter_account_id))
                },
                onValueChange = { viewModel.postIntent(ResetPasswordIntent.UpdateAccountId(value = it)) },
                supportingText = if (isAccountIdError) {
                    { Text(text = stringResource(id = R.string.reset_password_account_verification_enter_account_id_invalid_format)) }
                } else {
                    null
                },
                isError = isAccountIdError,
                readOnly = idChecked,
            )
            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = idChecked,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
                ) {
                    AccountAssertionCard(
                        modifier = Modifier
                            .horizontalPadding()
                            .verticalPadding()
                            .fillMaxWidth(),
                        hashedEmail = uiState.hashedEmail,
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalPadding(),
                        value = uiState.studentName,
                        hint = {
                            Text(text = stringResource(id = R.string.reset_password_account_verification_enter_student_name))
                        },
                        onValueChange = {
                            viewModel.postIntent(ResetPasswordIntent.UpdateStudentName(value = it))
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalPadding(),
                        value = uiState.email,
                        hint = {
                            Text(text = stringResource(id = R.string.reset_password_account_verification_enter_email))
                        },
                        onValueChange = {
                            viewModel.postIntent(ResetPasswordIntent.UpdateEmail(value = it))
                        },
                    )
                }
            }
            Spacer(modifier = Modifier.weight(3f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = {

                    viewModel.postIntent(
                        ResetPasswordIntent.SendEmailVerificationCode(
                            uiState.email,
                        ),
                    )
                },
                enabled = uiState.accountId.isNotEmpty() && uiState.studentName.isNotEmpty() && uiState.email.isNotEmpty(),
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
    BackHandler {
        navigator.openSignIn()
    }
}

@Composable
private fun AccountAssertionCard(
    modifier: Modifier = Modifier,
    hashedEmail: String,
) {
    Card(
        modifier = modifier,
        shape = DmsTheme.shapes.surfaceSmall,
        colors = CardDefaults.cardColors(
            containerColor = DmsTheme.colorScheme.surface,
            contentColor = DmsTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = ShadowDefaults.SmallElevation),
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            Text(
                modifier = Modifier.startPadding(),
                text = stringResource(id = R.string.reset_password_account_verification_success_account_id_matches_email),
            )
            Text(
                modifier = Modifier.startPadding(),
                text = hashedEmail,
                color = DmsTheme.colorScheme.primary,
            )
        }
    }
}
