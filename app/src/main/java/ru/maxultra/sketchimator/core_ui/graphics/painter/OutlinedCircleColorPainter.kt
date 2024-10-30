package ru.maxultra.sketchimator.core_ui.graphics.painter

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import ru.maxultra.sketchimator.core_ui.theme.tokens.DimenTokens

/**
 * [Painter] implementation used to fill the provided bounds with the circle of the specified color and outline
 */
data class OutlinedCircleColorPainter(
    val color: Color,
    val outlineColor: Color = Color.Unspecified,
    val outlineWidth: Dp = DimenTokens.x05,
) : Painter() {

    override fun DrawScope.onDraw() {
        drawCircle(color = color)
        drawCircle(color = outlineColor, style = Stroke(outlineWidth.toPx()))
    }

    /**
     * Drawing a color does not have an intrinsic size, return [Size.Unspecified] here
     */
    override val intrinsicSize: Size = Size.Unspecified
}
