package com.example.styla.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.styla.R


val StylaBackground = Color(0xFFF7F2EB)
val StylaAccent = Color(0xFF8B5A3D)
val StylaTextDark = Color(0xFF1E1E1E)

@Composable
fun StylaLaunchScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StylaBackground)
            .padding(horizontal = 32.dp, vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Logo "STYLA" como imagen
        Image(
            painter = painterResource(id = R.drawable.styla),
            contentDescription = "Styla Logo",
            modifier = Modifier
                .width(1000.dp)
                .height(100.dp)
                .padding(bottom = 8.dp)
        )

        // 2. Descripción
        val description = "Styla es la aplicación que está revolucionando la forma en la que vivimos la moda y gestionamos nuestro closet. Más que una app, es un espacio creativo y funcional donde puedes inspirarte, comprar, vender y compartir tu estilo con una comunidad que ama la moda tanto como tú."
        Text(
            text = description,
            color = StylaAccent,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif,
            lineHeight = 24.sp,
            modifier = Modifier.padding(bottom = 64.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        // 3. Créditos
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Créditos:",
                color = StylaTextDark.copy(alpha = 0.7f),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Lary Mariana Betancourt Avila",
                color = StylaTextDark,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif
            )
            Text(
                text = "Laura Sofía Sánchez Bolaños",
                color = StylaTextDark,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StylaLaunchScreenPreview() {
    StylaLaunchScreen()
}