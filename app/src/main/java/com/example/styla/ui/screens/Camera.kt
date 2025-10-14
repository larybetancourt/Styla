package com.example.styla.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.styla.R
import com.example.styla.ui.theme.AbhayaLibre

// Colores locales para esta pantalla
private val primaryColor = Color(0xFF9C5A2D)
private val backgroundColor = Color(0xFFF3E9D4)
private val cardColor = Color(0xFFE7DCCC)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    onBack: () -> Unit = {}   // <-- callback para volver a Start
) {
    var price by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    val logoResource = try { R.drawable.styla } catch (_: Exception) { android.R.drawable.ic_menu_edit }

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {   // <-- vuelve atrás
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.Black
                        )
                    }
                },
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 56.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = logoResource),
                            contentDescription = "STYLA Logo",
                            modifier = Modifier.width(100.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            // Título
            Text(
                text = "Sube la foto de tu prenda",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = AbhayaLibre,
                color = primaryColor,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // 1) Área para imagen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(cardColor)
                    .border(2.dp, primaryColor.copy(alpha = 0.5f), RoundedCornerShape(15.dp))
                    .clickable { /* TODO: abrir cámara/galería */ },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = "Agregar Foto",
                        tint = primaryColor,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Agregar Foto del Artículo",
                        fontFamily = AbhayaLibre,
                        fontSize = 18.sp,
                        color = primaryColor
                    )
                }
            }


            Spacer(modifier = Modifier.height(40.dp))

            // 4) Publicar
            Button(
                onClick = { /* TODO: publicar */ },
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text(
                    "Subir",
                    color = Color.White,
                    fontFamily = AbhayaLibre,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Preview Agregar Artículo")
@Composable
fun PreviewAddItemScreen() {
    AddItemScreen()
}
