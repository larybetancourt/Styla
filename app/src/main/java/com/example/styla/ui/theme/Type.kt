package com.example.styla.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.styla.R

val AbhayaLibre = FontFamily(
    Font(R.font.abhayalibre_regular,  FontWeight.Normal),
    Font(R.font.abhayalibre_medium,   FontWeight.Medium),
    Font(R.font.abhayalibre_semibold, FontWeight.SemiBold),
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = AbhayaLibre,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = AbhayaLibre,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    labelLarge = TextStyle(
        fontFamily = AbhayaLibre,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)
