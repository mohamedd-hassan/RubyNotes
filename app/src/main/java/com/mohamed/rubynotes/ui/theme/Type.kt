package com.mohamed.rubynotes.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mohamed.rubynotes.R

val poppinsFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_medium, FontWeight.Medium)
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        color = Color.Black,
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        color = Color.Black,
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 9.5.sp,
        letterSpacing = 0.12.sp
    ),
    bodyLarge = TextStyle(
        color = secondaryLight,
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 17.sp,
        letterSpacing = 0.12.sp
    ),
    bodyMedium = TextStyle(
        color = secondaryLight,
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 17.sp,
    )

    /* Other default text styles to override

    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

