package com.example.design_system.constans

import androidx.compose.ui.unit.Constraints

internal fun Constraints.asLoose(
    height: Boolean = true,
    width: Boolean = false,
) = copy(
    minHeight = if (height) 0 else minHeight,
    minWidth = if (width) 0 else minWidth,
)