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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
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
                // TODO: fix
                Text(
                    text = stringResource(
                        id = R.string.my_page_format_gcn_name,
                        gradeClassNumber ?: 2211,
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
) {
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

@Stable
private val userOptions: List<Option> = listOf(
    Option(
        titleRes = R.string.my_page_check_point_history,
        onClick = {},
    ),
    Option(
        titleRes = R.string.my_page_edit_password,
        onClick = {},
    ),
)

@Stable
private val signOutOption: List<Option> = listOf(
    Option(
        titleRes = R.string.my_page_sign_out,
        onClick = {},
    ),
)

@Stable
private val withdrawalOption: List<Option> = listOf(
    Option(
        titleRes = R.string.my_page_withdrawal,
        onClick = {},
    ),
)

@Stable
private val themeOption: List<Option> = listOf(
    Option(
        titleRes = R.string.my_page_theme_settings,
        onClick = {},
    )
)

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
                    .clickable { }
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

/*
    val uiState by myPageViewModel.stateFlow.collectAsStateWithLifecycle()
    val myPageInformation = uiState.myPage
    var signOutDialogState by remember { mutableStateOf(false) }
    var withdrawDialogState by remember { mutableStateOf(false) }
    var profileDialogState by remember { mutableStateOf(false) }

    if (signOutDialogState) {
        DormCustomDialog(
            onDismissRequest = {
                *//* explicit blank *//*
            },
        ) {
            DormDoubleButtonDialog(
                content = stringResource(R.string.my_page_are_you_sure_you_sign_out),
                mainBtnText = stringResource(R.string.accept),
                subBtnText = stringResource(R.string.cancel),
                onMainBtnClick = {
                    myPageViewModel.postIntent(MyPageIntent.SignOut)
                    navigator.openUnauthorizedNav()
                    signOutDialogState = false
                },
                onSubBtnClick = {
                    signOutDialogState = false
                },
            )
        }
    }
    if (withdrawDialogState) {
        DormCustomDialog(
            onDismissRequest = {
                *//* explicit blank *//*
            },
        ) {
            DormDoubleButtonDialog(
                content = stringResource(R.string.my_page_are_you_sure_you_withdraw),
                mainBtnText = stringResource(R.string.accept),
                subBtnText = stringResource(R.string.cancel),
                onMainBtnClick = {
                    myPageViewModel.postIntent(MyPageIntent.Withdraw)
                    navigator.openUnauthorizedNav()
                    withdrawDialogState = false
                },
                onSubBtnClick = {
                    withdrawDialogState = false
                },
            )
        }
    }
    if (profileDialogState) {
        SelectImageTypeDialog(
            onCancel = {
                profileDialogState = false
            },
            onTakePhoto = { },
            onSelectPhoto = { },
            onDialogDismiss = {
                profileDialogState = false
            },
        )
    }
    Column(
        modifier = modifier
            .background(DormTheme.colors.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        myPageInformation?.run {
            Spacer(Modifier.height(24.dp))
            UserInformation(
                modifier = Modifier.fillMaxWidth(),
                gradeClassNumber = gradeClassNumber,
                name = name,
                sex = sex,
                school = schoolName,
                profileImageUrl = profileImageUrl,
                onChangeProfileImage = { profileDialogState = !profileDialogState },
            )
            // TODO
            if (true) PointsInformation(
                modifier = Modifier.fillMaxWidth(),
                phrase = phrase,
                bonusPoint = bonusPoint,
                minusPoint = minusPoint,
            )
        }
        Options(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            // TODO
            onPointHistoryClicked = if (true) navigator::openPointHistory else null,
            onEditPasswordClicked = navigator::openEditPasswordNav,
            onSignOutClicked = { signOutDialogState = !signOutDialogState },
            onWithdrawClicked = { withdrawDialogState = !withdrawDialogState },
        )
    }*//*
@Composable
private fun UserInformation(
    modifier: Modifier = Modifier,
    gradeClassNumber: String,
    name: String,
    sex: Sex,
    school: String,
    profileImageUrl: String,
    onChangeProfileImage: () -> Unit,
) {
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(0.8f),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Title1(
                    text = "$gradeClassNumber $name",
                )
                Spacer(Modifier.width(20.dp))
                LastAppliedItem(
                    modifier = DefaultAppliedTagSize,
                    text = when (sex) {
                        Sex.MALE -> stringResource(R.string.male)
                        Sex.FEMALE -> stringResource(R.string.female)
                        else -> throw IllegalStateException()
                    },
                    backgroundColor = when (sex) {
                        Sex.MALE -> DormColor.Lighten200
                        Sex.FEMALE -> DormColor.LightenError
                        else -> throw IllegalStateException()
                    },
                    textColor = when (sex) {
                        Sex.MALE -> DormColor.DormPrimary
                        Sex.FEMALE -> DormColor.Error
                        else -> throw IllegalStateException()
                    },
                )
            }
            Spacer(Modifier.height(10.dp))
            Body5(
                text = school,
            )
        }
        Box(
            modifier = Modifier.weight(0.2f),
            contentAlignment = Alignment.BottomEnd,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .dormClickable { onChangeProfileImage() },
                model = profileImageUrl,
                contentDescription = null,
            )
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(R.drawable.ic_mypage_edit),
                contentDescription = null,
            )
        }
    }
}*//*

private enum class PointCardType {
    BONUS, MINUS,
    ;
}

@Composable
private fun PointsInformation(
    modifier: Modifier = Modifier,
    phrase: String,
    bonusPoint: Int,
    minusPoint: Int,
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        PhraseCard(
            modifier = Modifier.fillMaxWidth(),
            phrase = phrase,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PointCard(
                modifier = Modifier.weight(1f),
                type = PointCardType.BONUS,
                point = bonusPoint,
            )
            PointCard(
                modifier = Modifier.weight(1f),
                type = PointCardType.MINUS,
                point = minusPoint,
            )
        }
    }
}*//*
@Composable
private fun PhraseCard(
    modifier: Modifier = Modifier,
    phrase: String,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .background(
                DmsTheme.colors.secondary,
            )
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp,
            ),
        contentAlignment = Alignment.CenterStart,
    ) {
        Caption(
            text = phrase,
            color = DormColor.Gray1000,
        )
    }
}*//*
@Composable
private fun PointCard(
    modifier: Modifier = Modifier,
    type: PointCardType,
    point: Int,
) {
    val textColor = when (type) {
        PointCardType.BONUS -> DmsTheme.colors.primary
        PointCardType.MINUS -> DmsTheme.colors.error
    }
    Column(
        modifier = modifier
            .dormShadow(DmsTheme.colors.primaryVariant)
            .clip(RoundedCornerShape(10.dp))
            .border(
                color = when (type) {
                    PointCardType.BONUS -> DmsTheme.colors.primary
                    PointCardType.MINUS -> DmsTheme.colors.error
                },
                width = 1.dp,
                shape = RoundedCornerShape(10.dp),
            )
            .background(
                color = DmsTheme.colors.surface, // todo
            )
            .padding(
                vertical = 14.dp,
                horizontal = 20.dp,
            ),
    ) {
        Caption(
            text = stringResource(
                when (type) {
                    PointCardType.BONUS -> R.string.bonus_point
                    PointCardType.MINUS -> R.string.minus_point
                }
            ),
            color = textColor,
        )
        Spacer(Modifier.height(24.dp))
        Headline3(
            modifier = Modifier.fillMaxWidth(),
            text = point.toString(),
            textAlign = TextAlign.End,
            color = textColor,
        )
    }
}*//*

// TODO 리스트 디자인시스템으로 커버 필요
@Composable
private fun Options(
    modifier: Modifier = Modifier,
    onPointHistoryClicked: (() -> Unit)?,
    onEditPasswordClicked: () -> Unit,
    onSignOutClicked: () -> Unit,
    onWithdrawClicked: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (onPointHistoryClicked != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .dormShadow(DmsTheme.colors.primaryVariant)
                    .clip(RoundedCornerShape(10.dp))
                    .background(DmsTheme.colors.surface),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .dormClickable { onPointHistoryClicked() }
                        .padding(
                            vertical = 14.dp,
                            horizontal = 16.dp,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Body5(
                        text = stringResource(R.string.my_page_check_point_history),
                    )
                }
                Divider(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    color = DmsTheme.colors.secondaryVariant,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .dormClickable { onEditPasswordClicked() }
                        .padding(
                            vertical = 14.dp,
                            horizontal = 16.dp,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Body5(
                        text = stringResource(R.string.change_password),
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .dormShadow(DmsTheme.colors.primaryVariant)
                    .clip(RoundedCornerShape(10.dp))
                    .background(DmsTheme.colors.surface),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .dormClickable { onEditPasswordClicked() }
                        .padding(
                            vertical = 14.dp,
                            horizontal = 16.dp,
                        ),
                ) {
                    Body5(
                        text = stringResource(R.string.change_password),
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .dormShadow(DmsTheme.colors.primaryVariant)
                .clip(RoundedCornerShape(10.dp))
                .background(DmsTheme.colors.surface),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .dormClickable { onSignOutClicked() }
                    .padding(
                        vertical = 14.dp,
                        horizontal = 16.dp,
                    ),
            ) {
                Body5(
                    text = stringResource(R.string.my_page_sign_out),
                    color = DmsTheme.colors.error,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .dormShadow(DmsTheme.colors.primaryVariant)
                .clip(RoundedCornerShape(10.dp))
                .background(DmsTheme.colors.surface),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .dormClickable { onWithdrawClicked() }
                    .padding(
                        vertical = 14.dp,
                        horizontal = 16.dp,
                    ),
            ) {
                Body5(
                    text = stringResource(R.string.my_page_withdrawal),
                    color = DmsTheme.colors.error,
                )
            }
        }
    }
}
*/
