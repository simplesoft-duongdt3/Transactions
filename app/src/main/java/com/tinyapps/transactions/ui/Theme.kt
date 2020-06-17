package com.tinyapps.transactions.ui

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.graphics.Color
import androidx.ui.material.MaterialTheme
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette

private val DarkColorPalette = darkColorPalette(
    primary = purple200,
    primaryVariant = purple700,
    secondary = teal200,
    background = Color.Black
)

private val LightColorPalette = lightColorPalette(
    primary = purple500,
    primaryVariant = purple700,
    secondary = teal200,
    background = gray

    /* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/
)

@Composable
fun TransactionsTheme(darkTheme: MutableState<Boolean>, content: @Composable() () -> Unit) {
    val colors = if (darkTheme.value) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}