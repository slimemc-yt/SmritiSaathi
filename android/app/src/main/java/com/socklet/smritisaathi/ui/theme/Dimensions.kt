package com.socklet.smritisaathi.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

// Custom dimensions for accessibility
object Dimensions {
    // Minimum touch target size (Material guidelines: 48dp)
    val MinTouchTarget = 48.dp

    // Button sizes
    val ButtonHeight = 56.dp
    val ButtonHeightLarge = 72.dp
    val ButtonCornerRadius = 16.dp

    // Spacing
    val Space4 = 4.dp
    val Space8 = 8.dp
    val Space12 = 12.dp
    val Space16 = 16.dp
    val Space20 = 20.dp
    val Space24 = 24.dp
    val Space32 = 32.dp
    val Space40 = 40.dp
    val Space48 = 48.dp

    // Icon sizes
    val IconSmall = 24.dp
    val IconMedium = 32.dp
    val IconLarge = 48.dp
    val IconExtraLarge = 64.dp

    // Card elevation
    val CardElevation = 4.dp

    // Card corner radius
    val CardCornerRadius = 12.dp

    // Screen padding
    val ScreenPadding = 24.dp
}
