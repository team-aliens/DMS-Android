package team.aliens.dms.android.feature.main.mypage

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ButtonDefaults
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.Gray10
import team.aliens.dms.android.core.designsystem.RoundedButton
import team.aliens.dms.android.core.designsystem.ShadowDefaults
import team.aliens.dms.android.core.designsystem.clickable
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.LargeVerticalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.endPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature._legacy.extension.collectInLaunchedEffectWithLifeCycle
import team.aliens.dms.android.shared.model.Sex

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun MyPageScreen(
    modifier: Modifier = Modifier,
    onNavigateToEditProfileImage: () -> Unit,
    onNavigateToPointHistory: () -> Unit,
    onNavigateToEditPassword: () -> Unit,
    onNavigateToUnauthorizedNav: () -> Unit,
) {
    val viewModel: MyPageViewModel = hiltViewModel()
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifeCycle { sideEffect ->
        when (sideEffect) {
            MyPageSideEffect.SignOutSuccess -> onNavigateToUnauthorizedNav()
            MyPageSideEffect.WithdrawalSuccess -> onNavigateToUnauthorizedNav()
        }
    }

    DmsScaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = { },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
            )
        },
    ) { padValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(padValues),
            verticalArrangement = Arrangement.spacedBy(LargeVerticalSpace),
        ) {
            UserInformation(
                modifier = Modifier.fillMaxWidth(),
                gradeClassNumber = uiState.myPage?.gradeClassNumber,
                studentName = uiState.myPage?.studentName,
                sex = uiState.myPage?.sex,
                schoolName = uiState.myPage?.schoolName,
                profileImageUrl = uiState.myPage?.profileImageUrl,
                onNavigateToEditProfileImage = onNavigateToEditProfileImage,
            )
            PhraseCard(
                modifier = Modifier.fillMaxWidth(),
                phrase = uiState.myPage?.phrase,
            )
            PointCards(
                modifier = Modifier.fillMaxWidth(),
                bonusPoint = uiState.myPage?.bonusPoint,
                minusPoint = uiState.myPage?.minusPoint,
            )
            Options(
                modifier = Modifier.fillMaxWidth(),
                onNavigateToPointHistory = onNavigateToPointHistory,
                onNavigateToEditPassword = onNavigateToEditPassword,
                onSignOutClick = {
                    // TODO: sign out modal
                    viewModel.postIntent(MyPageIntent.SignOut)
                },
                onWithdrawalClick = {
                    // TODO: withdrawal modal
                    viewModel.postIntent(MyPageIntent.Withdraw)
                },
                onThemeSettingsClick = {

                },
            )
            // TODO: measure navigation bar height
            Spacer(modifier = Modifier.height(84.dp))
        }
    }
}

@Composable
private fun UserInformation(
    modifier: Modifier = Modifier,
    gradeClassNumber: String?,
    studentName: String?,
    sex: Sex?,
    schoolName: String?,
    profileImageUrl: String?,
    onNavigateToEditProfileImage: () -> Unit,
) {
    Row(
        modifier = modifier
            .horizontalPadding()
            .topPadding(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(
                        id = R.string.my_page_format_gcn_name,
                        gradeClassNumber ?: 2211,
                        // TODO: fix
                        studentName ?: "박준수",
                    ),
                    style = DmsTheme.typography.title1,
                    color = DmsTheme.colorScheme.onBackground,
                )
                RoundedButton(
                    onClick = {},
                    colors = when (sex) {
                        Sex.MALE -> ButtonDefaults.roundedButtonColors()
                        Sex.FEMALE -> ButtonDefaults.roundedRefuseButtonColors()
                        Sex.ALL -> throw IllegalArgumentException()
                        // TODO: fix
                        null -> ButtonDefaults.roundedGrayButtonColors()
                    },
                    fillMinSize = false,
                    contentPadding = PaddingValues(
                        horizontal = PaddingDefaults.Medium,
                        vertical = PaddingDefaults.ExtraSmall,
                    ),
                ) {
                    // TODO: Fix
                    Text(text = sex?.text ?: "성별")
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                // TODO: Fix
                text = schoolName ?: "대덕소프트웨어마이스터고등학교",
                style = DmsTheme.typography.body2,
                color = DmsTheme.colorScheme.surfaceVariant,
            )
        }
        Box(
            modifier = Modifier.size(64.dp),
            contentAlignment = Alignment.BottomEnd,
        ) {
            Image(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onNavigateToEditProfileImage),
                painter = rememberAsyncImagePainter(
                    model = profileImageUrl ?: R.drawable.img_profile_default
                ),
                contentDescription = stringResource(id = R.string.profile_image),
            )
            Image(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onNavigateToEditProfileImage),
                painter = painterResource(id = R.drawable.ic_my_page_edit),
                contentDescription = null,
            )
        }
    }
}

private val Sex.text: String
    @Composable inline get() = stringResource(
        id = when (this) {
            Sex.MALE -> R.string.male
            Sex.FEMALE -> R.string.female
            Sex.ALL -> throw IllegalStateException()
        },
    )

@Composable
private fun PhraseCard(
    modifier: Modifier = Modifier,
    phrase: String?,
) {
    Card(
        modifier = modifier.horizontalPadding(),
        shape = DmsTheme.shapes.surfaceSmall,
        colors = CardDefaults.cardColors(
            containerColor = DmsTheme.colorScheme.primaryContainer,
            contentColor = Gray10,
        ),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = ShadowDefaults.SmallElevation),
    ) {
        Text(
            modifier = Modifier
                .horizontalPadding()
                .verticalPadding(PaddingDefaults.Medium),
            // TODO
            text = phrase ?: "힘 내십쇼",
            style = DmsTheme.typography.caption,
        )
    }
}

@Composable
private fun PointCards(
    modifier: Modifier = Modifier,
    bonusPoint: Int?,
    minusPoint: Int?,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace),
    ) {
        PointCard(
            modifier = Modifier
                .weight(1f)
                .startPadding(),
            type = PointCardType.BONUS,
            // TODO: fix
            point = bonusPoint ?: 50,
        )
        PointCard(
            modifier = Modifier
                .weight(1f)
                .endPadding(),
            type = PointCardType.MINUS,
            // TODO: fix
            point = minusPoint ?: -50,
        )
    }
}

@Composable
private fun PointCard(
    modifier: Modifier = Modifier,
    type: PointCardType,
    point: Int,
) {
    OutlinedCard(
        modifier = modifier,
        shape = DmsTheme.shapes.surfaceMedium,
        colors = CardDefaults.outlinedCardColors(
            containerColor = type.containerColor,
            contentColor = type.contentColor,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = type.borderColor,
        ),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = ShadowDefaults.SmallElevation),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(LargeVerticalSpace),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .startPadding()
                    .topPadding(),
                text = type.text,
                style = DmsTheme.typography.caption,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .endPadding()
                    .bottomPadding(),
                text = "$point",
                style = DmsTheme.typography.headline3,
                textAlign = TextAlign.End,
            )
        }
    }
}

@Immutable
private enum class PointCardType {
    BONUS, MINUS,
    ;

    val containerColor: Color
        @Composable inline get() = when (this) {
            BONUS -> DmsTheme.colorScheme.primaryContainer
            MINUS -> DmsTheme.colorScheme.errorContainer
        }

    val contentColor: Color
        @Composable inline get() = when (this) {
            BONUS -> DmsTheme.colorScheme.onPrimaryContainer
            MINUS -> DmsTheme.colorScheme.onErrorContainer
        }

    val borderColor: Color
        @Composable inline get() = when (this) {
            BONUS -> DmsTheme.colorScheme.onPrimaryContainer
            MINUS -> DmsTheme.colorScheme.onErrorContainer
        }

    val text: String
        @Composable inline get() = stringResource(
            id = when (this) {
                BONUS -> R.string.bonus_point
                MINUS -> R.string.minus_point
            },
        )
}

@Composable
private fun Options(
    modifier: Modifier = Modifier,
    onNavigateToPointHistory: () -> Unit,
    onNavigateToEditPassword: () -> Unit,
    onSignOutClick: () -> Unit,
    onWithdrawalClick: () -> Unit,
    onThemeSettingsClick: () -> Unit,
) {
    val userOptions = remember {
        listOf(
            Option(
                titleRes = R.string.my_page_check_point_history,
                onClick = onNavigateToPointHistory,
            ),
            Option(
                titleRes = R.string.my_page_edit_password,
                onClick = onNavigateToEditPassword,
            ),
        )
    }
    val signOutOption = remember {
        listOf(
            Option(
                titleRes = R.string.my_page_sign_out,
                onClick = onSignOutClick,
            ),
        )
    }
    val withdrawalOption = remember {
        listOf(
            Option(
                titleRes = R.string.my_page_withdrawal,
                onClick = onWithdrawalClick,
            ),
        )
    }
    val themeOption = remember {
        listOf(
            Option(
                titleRes = R.string.my_page_theme_settings,
                onClick = onThemeSettingsClick,
            ),
        )
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(LargeVerticalSpace),
    ) {
        OptionLayout(
            options = userOptions,
            titleColor = Option.DefaultTitleColor,
        )
        OptionLayout(
            options = signOutOption,
            titleColor = Option.ErrorTitleColor,
        )
        OptionLayout(
            options = withdrawalOption,
            titleColor = Option.ErrorTitleColor,
        )
        OptionLayout(
            options = themeOption,
            titleColor = Option.DefaultTitleColor,
        )
    }
}

@Composable
private fun OptionLayout(
    modifier: Modifier = Modifier,
    options: List<Option>,
    titleColor: Color,
) {
    Card(
        modifier = modifier.horizontalPadding(),
        shape = DmsTheme.shapes.surfaceSmall,
        colors = CardDefaults.cardColors(
            containerColor = DmsTheme.colorScheme.surface,
            contentColor = titleColor,
        ),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = ShadowDefaults.SmallElevation),
    ) {
        options.forEachIndexed { index, option ->
            Text(
                modifier = Modifier
                    .clickable(onClick = option.onClick)
                    .fillMaxWidth()
                    .horizontalPadding()
                    .verticalPadding(PaddingDefaults.Medium),
                text = stringResource(id = option.titleRes),
                style = DmsTheme.typography.body2,
            )
            if (index != options.lastIndex) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding(),
                    color = DmsTheme.colorScheme.line,
                )
            }
        }
    }
}

@Immutable
private class Option(
    @StringRes val titleRes: Int,
    val onClick: () -> Unit,
) {
    companion object {
        val DefaultTitleColor: Color
            @Composable inline get() = DmsTheme.colorScheme.onSurface

        val ErrorTitleColor: Color
            @Composable inline get() = DmsTheme.colorScheme.error
    }
}
