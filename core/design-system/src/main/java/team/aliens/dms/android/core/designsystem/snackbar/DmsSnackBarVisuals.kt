package team.aliens.dms.android.core.designsystem.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

data class DmsSnackBarVisuals(
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val message: String,
    override val withDismissAction: Boolean = false,
    val snackBarType: DmsSnackBarType,
) : SnackbarVisuals
