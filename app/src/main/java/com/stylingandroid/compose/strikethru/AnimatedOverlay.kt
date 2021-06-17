package com.stylingandroid.compose.strikethru

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Suppress("MagicNumber")
fun Modifier.animatedOverlay(animatedOverlay: AnimatedOverlay) = this.then(
    Modifier
        .graphicsLayer {
            // This is required to render to an offscreen buffer
            // The Clear blend mode will not work without it
            alpha = 0.99f
        }
        .drawWithContent {
            drawContent()
            animatedOverlay.drawOverlay(this)
        }
)

interface AnimatedOverlay {
    fun drawOverlay(drawScope: DrawScope)
}

class StrikethruOverlay(
    private val color: Color = Color.Unspecified,
    private var widthDp: Dp = 4.dp,
    private val getProgress: () -> Float
) : AnimatedOverlay {

    @Suppress("MagicNumber")
    override fun drawOverlay(drawScope: DrawScope) {
        with(drawScope) {
            val width = widthDp.toPx()
            val halfWidth = width / 2f
            val progressHeight = size.height * getProgress()
            rotate(-45f) {
                drawLine(
                    color = color,
                    start = Offset(size.center.x + halfWidth, 0f),
                    end = Offset(size.center.x + halfWidth, progressHeight),
                    strokeWidth = width,
                    blendMode = BlendMode.Clear
                )
                drawLine(
                    color = color,
                    start = Offset(size.center.x - halfWidth, 0f),
                    end = Offset(size.center.x - halfWidth, progressHeight),
                    strokeWidth = width
                )
            }
        }
    }
}
