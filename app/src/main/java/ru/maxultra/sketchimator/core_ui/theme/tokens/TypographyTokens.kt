package ru.maxultra.sketchimator.core_ui.theme.tokens

import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.LineHeightStyle.Alignment
import androidx.compose.ui.text.style.LineHeightStyle.Trim

object TypographyTokens {

    private const val FontFeatureSettingsProportionalWidthAndEqualHeight = "pnum, lnum"
    private val PlatformTextStyle = PlatformTextStyle(includeFontPadding = false)
    private val LineHeightStyle = LineHeightStyle(alignment = Alignment.Bottom, trim = Trim.Both)

    val Body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = TypeScaleTokens.Body1FontSize,
        lineHeight = TypeScaleTokens.Body1LineHeight,
        letterSpacing = TypeScaleTokens.Body1Tracking,
        platformStyle = PlatformTextStyle,
        lineHeightStyle = LineHeightStyle,
        fontFeatureSettings = FontFeatureSettingsProportionalWidthAndEqualHeight,
    )
}
