package com.stylingandroid.compose.strikethru

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stylingandroid.compose.strikethru.ui.theme.StrikethruTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StrikethruTheme {
                Row {
                    StrikethruIcon()
                    StrikethruImage()
                }
            }
        }
    }
}

@Composable
@Preview
fun StrikethruIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector = Icons.Filled.ShoppingCart
) {
    Surface(color = MaterialTheme.colors.background) {
        var enabled by remember { mutableStateOf(false) }
        val transition = updateTransition(enabled, label = "Transition")
        val progress by transition.animateFloat(label = "Progress") { state ->
            if (state) 1f else 0f
        }
        val overlay = StrikethruOverlay(
            color = LocalContentColor.current,
            widthDp = 4.dp,
            getProgress = { progress }
        )
        Icon(
            modifier = modifier
                .clickable { enabled = !enabled }
                .padding(8.dp)
                .animatedOverlay(overlay)
                .padding(12.dp)
                .size(52.dp),
            imageVector = imageVector,
            tint = LocalContentColor.current,
            contentDescription = null
        )
    }
}

@Composable
@Preview
fun StrikethruImage(modifier: Modifier = Modifier, @DrawableRes drawableId: Int = R.drawable.eye) {
    Surface(color = MaterialTheme.colors.background) {
        var enabled by remember { mutableStateOf(false) }
        val transition = updateTransition(enabled, label = "Transition")
        val progress by transition.animateFloat(label = "Progress") { state ->
            if (state) 1f else 0f
        }
        val overlay = StrikethruOverlay(
            color = LocalContentColor.current,
            widthDp = 4.dp,
            getProgress = { progress }
        )
        Image(
            modifier = modifier
                .clickable { enabled = !enabled }
                .padding(8.dp)
                .animatedOverlay(overlay)
                .size(64.dp),
            painter = painterResource(drawableId),
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            contentDescription = null
        )
    }
}
