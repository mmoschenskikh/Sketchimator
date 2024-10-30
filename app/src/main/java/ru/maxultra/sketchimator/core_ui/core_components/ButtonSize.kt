package ru.maxultra.sketchimator.core_ui.core_components

import ru.maxultra.sketchimator.core_ui.theme.tokens.DimenTokens

enum class ButtonSize {
    Medium,
    Small,
}

object ButtonDefaults {

    fun iconButtonSize(size: ButtonSize) = when (size) {
        ButtonSize.Small -> DimenTokens.x6
        ButtonSize.Medium -> DimenTokens.x8
    }
}
