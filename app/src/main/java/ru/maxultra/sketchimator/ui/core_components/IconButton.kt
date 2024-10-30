package ru.maxultra.sketchimator.ui.core_components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.maxultra.sketchimator.R
import ru.maxultra.sketchimator.ui.theme.SketchimatorTheme
import ru.maxultra.sketchimator.ui.theme.tokens.DimenTokens

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
    androidx.compose.material3.IconButton(
        modifier = modifier.size(ButtonDefaults.iconButtonSize(size)),
        onClick = onClick,
        enabled = enabled,
    ) {
        Icon(
            painter = painterResource(icon),
            tint = iconColor,
            contentDescription = contentDescription,
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun IconButtonPreview() {
    SketchimatorTheme {
        Surface {
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
            }
        }
    }
}
