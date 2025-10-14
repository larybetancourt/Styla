package com.example.styla.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.styla.ui.theme.AbhayaLibre
import com.example.styla.R
import com.example.styla.ui.theme.StylaTheme

@Composable
fun Signup(
    onNavigateToRegistro: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        // Mitad superior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.portadacc),
                contentDescription = "Fondo portada",
                modifier = Modifier
                    .fillMaxSize()
                    .scale(1.3f)
                    .offset(x = (-30).dp, y = 52.dp),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xFFEDE7D4))
                        )
                    )
            )

            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.Black
                )
            }

            Image(
                painter = painterResource(id = R.drawable.styla),
                contentDescription = "Logo Styla",
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = 130.dp)
                    .size(300.dp)
            )
        }

        // Mitad inferior
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEDE7D4)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Miles de outfits y marcas exclusivas\ndisponibles para ti",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontFamily = AbhayaLibre
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                // Botón Facebook
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(50))
                        .border(1.dp, Color.Black, RoundedCornerShape(50))
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.facebook),
                        contentDescription = "Facebook",
                        tint = Color.Black,
                        modifier = Modifier.size(50.dp)
                    )
                    Text("Facebook", fontSize = 18.sp, fontFamily = AbhayaLibre, color = Color.Black)
                }

                // Botón Gmail
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(50))
                        .border(1.dp, Color.Black, RoundedCornerShape(50))
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.gmail),
                        contentDescription = "Gmail",
                        tint = Color.Black,
                        modifier = Modifier.size(50.dp)
                    )
                    Text("Gmail", fontSize = 18.sp, fontFamily = AbhayaLibre, color = Color.Black)
                }
            }

            // Separador con "o"
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(Color.Black)
                )
                Text(
                    "  o  ",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontFamily = AbhayaLibre
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(Color.Black)
                )
            }

            // Botón de registrarse
            Button(
                onClick = onNavigateToRegistro,
                modifier = Modifier
                    .shadow(
                        elevation = 15.dp,
                        shape = RoundedCornerShape(12.dp),
                        clip = false
                    )
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9B5C2E))
            ) {
                Text(
                    text = "Regístrate con email",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = AbhayaLibre
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun CoverScreenPreview() {
    StylaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFEDE7D4)
        ) {
            Signup (
                onNavigateToRegistro = {},
                onNavigateToLogin = {},
                onBack = {}
            )
            }
        }
}