package com.example.submarino.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.submarino.R

val Rokkit = FontFamily(
    Font(R.font.rokkitt_regular),
    Font(R.font.rokkitt_black, FontWeight.Black),
    Font(R.font.rokkitt_blackitalic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.rokkitt_bold, FontWeight.Bold),
    Font(R.font.rokkitt_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.rokkitt_extrabold, FontWeight.ExtraBold),
    Font(R.font.rokkitt_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.rokkitt_extralight, FontWeight.ExtraLight),
    Font(R.font.rokkitt_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.rokkitt_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.rokkitt_light, FontWeight.Light),
    Font(R.font.rokkitt_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.rokkitt_medium, FontWeight.Medium),
    Font(R.font.rokkitt_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.rokkitt_semibold, FontWeight.SemiBold),
    Font(R.font.rokkitt_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.rokkitt_thin, FontWeight.Thin),
    Font(R.font.rokkitt_thinitalic, FontWeight.Thin, FontStyle.Italic)
)
// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Rokkit,
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Rokkit,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Rokkit,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic,
        fontSize = 20.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Rokkit,
        fontWeight = FontWeight.Black,
        fontSize = 22.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Rokkit,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Rokkit,
        fontWeight = FontWeight.ExtraBold,
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Rokkit,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Rokkit,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Rokkit,
        fontWeight = FontWeight.Medium,
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Rokkit,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Rokkit,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Rokkit,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Rokkit,
        fontWeight = FontWeight.Light,
        fontSize = 18.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Rokkit,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Rokkit,
        fontWeight = FontWeight.Light,
        fontStyle = FontStyle.Italic,
        fontSize = 10.sp
    )
)