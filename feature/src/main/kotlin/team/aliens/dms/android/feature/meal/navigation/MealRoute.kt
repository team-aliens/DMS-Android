package team.aliens.dms.android.feature.meal.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.feature.meal.ui.Meal

@Composable
fun MealRoute(
    onNavigateBack: () -> Unit,
) {
    Meal(
        onNavigateBack = onNavigateBack,
    )
}
