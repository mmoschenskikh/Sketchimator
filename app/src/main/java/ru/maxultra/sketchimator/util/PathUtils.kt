package ru.maxultra.sketchimator.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.IntSize

object PathUtils {

    fun Path.isOutOfBoundsByX(
        frameSize: IntSize,
        offsetX: Float,
    ): Boolean = when {
        offsetX <= 0 -> this.getBounds().left + offsetX <= 0 // moving left
        offsetX > 0 -> this.getBounds().right + offsetX >= frameSize.width // moving right
        else -> false
    }

    fun Path.isOutOfBoundsByY(
        frameSize: IntSize,
        offsetY: Float,
    ): Boolean = when {
        offsetY <= 0 -> this.getBounds().top + offsetY <= 0 // moving up
        offsetY > 0 -> this.getBounds().bottom + offsetY >= frameSize.height // moving down
        else -> false
    }

    fun buildTrianglePath(
        firstPoint: Offset,
        secondPoint: Offset,
        thirdPoint: Offset,
    ): Path = Path().apply {
        moveTo(firstPoint.x, firstPoint.y)
        lineTo(secondPoint.x, secondPoint.y)
        lineTo(thirdPoint.x, thirdPoint.y)
        lineTo(firstPoint.x, firstPoint.y)
        close()
    }
}
