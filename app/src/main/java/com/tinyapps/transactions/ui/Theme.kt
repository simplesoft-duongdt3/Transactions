package com.tinyapps.transactions.ui

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.ui.graphics.Color
import androidx.ui.material.MaterialTheme
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette

private val DarkColorPalette = darkColorPalette(
    primary = purple200,
    primaryVariant = purple700,
    secondary = filter,
    background = Color.Black
)

private val LightColorPalette = lightColorPalette(
    primary = purple500,
    primaryVariant = purple700,
    secondary = filter,
    background = Color.White
)


@Composable
fun TransactionsTheme(darkTheme: MutableState<Boolean>, content: @Composable() () -> Unit) {

    val colors = if (darkTheme.value) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    if (darkTheme.value) {
        gradientStart = Color.Black
        gradientEnd = Color.DarkGray
        accountBackground = Color.Black
        textPrimary = Color.White
        textSecondary = Color.Gray
    }else{
        gradientStart = Color(0xFFF2F2F2)
        gradientEnd =  Color(0xFFFDFDFD)
        accountBackground = Color(0xFFF2F4F5)
        textPrimary = Color(0xFF35526B)
        textSecondary = Color(0xFF6D8194)
    }


    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}