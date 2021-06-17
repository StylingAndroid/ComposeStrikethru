package com.stylingandroid.compose.strikethru.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.stylingandroid.compose.listdetail.ui.theme.SaGreen
import com.stylingandroid.compose.listdetail.ui.theme.SaGreenDark
import com.stylingandroid.compose.listdetail.ui.theme.SaPurple
import com.stylingandroid.compose.listdetail.ui.theme.SaPurpleDark
import com.stylingandroid.compose.listdetail.ui.theme.SaPurpleLight

private val DarkColorPalette = darkColors(
    primary = SaGreen,
    primaryVariant = SaGreenDark,
    secondary = SaPurple,
    secondaryVariant = SaPurpleDark,

    onBackground = SaPurpleLight
)

private val LightColorPalette = lightColors(
    primary = SaGreen,
    primaryVariant = SaGreenDark,
    secondary = SaPurple,
    secondaryVariant = SaPurpleLight,

    onBackground = SaGreen
)

@Composable
fun StrikethruTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = colors.primaryVariant
        )
        systemUiController.setNavigationBarColor(
            color = colors.primaryVariant,
            darkIcons = colors.isLight
        )
    }
}
