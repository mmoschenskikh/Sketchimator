package ru.maxultra.sketchimator.core_ui.core_components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import ru.maxultra.sketchimator.R
import ru.maxultra.sketchimator.core_ui.graphics.painter.OutlinedCircleColorPainter
import ru.maxultra.sketchimator.core_ui.theme.SketchimatorTheme
import ru.maxultra.sketchimator.core_ui.theme.tokens.DimenTokens
import ru.maxultra.sketchimator.core_ui.util.DayNightPreview

@Composable
fun IconButton(
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    iconColor: Color = if (enabled) {
        SketchimatorTheme.colorScheme.onBackground
    } else {
        SketchimatorTheme.colorScheme.onBackgroundSecondary
    },
    size: ButtonSize = ButtonSize.Medium,
    contentDescription: String? = null,
) {
    IconButton(
        painter = painterResource(icon),
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        iconColor = iconColor,
        size = size,
        contentDescription = contentDescription,
    )
}


@Composable
fun IconButton(
    painter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    iconColor: Color = Color.Unspecified,
    size: ButtonSize = ButtonSize.Medium,
    contentDescription: String? = null,
) {
    androidx.compose.material3.IconButton(
        modifier = modifier.size(IconButtonDefaultSize),
        onClick = onClick,
        enabled = enabled,
    ) {
        Icon(
            modifier = Modifier.size(ButtonDefaults.iconButtonSize(size)),
            painter = painter,
            tint = iconColor,
            contentDescription = contentDescription,
        )
    }
}

@DayNightPreview
@Composable
private fun IconButtonPreview() {
    SketchimatorTheme {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(DimenTokens.x4),
        ) {
            IconButton(
                icon = R.drawable.ic_curved_arrow_left_24,
                onClick = {},
                size = ButtonSize.Small,
            )
            IconButton(
                icon = R.drawable.ic_curved_arrow_right_24,
                onClick = {},
                size = ButtonSize.Small,
                enabled = false,
            )
            IconButton(
                icon = R.drawable.ic_pause_32,
                onClick = {},
                size = ButtonSize.Medium,
            )
            IconButton(
                icon = R.drawable.ic_play_32,
                onClick = {},
                size = ButtonSize.Medium,
                enabled = false,
            )
            IconButton(
                painter = OutlinedCircleColorPainter(Color.Blue, SketchimatorTheme.colorScheme.highlight),
                onClick = {},
            )
        }
    }
}

val IconButtonDefaultSize = DimenTokens.x10
