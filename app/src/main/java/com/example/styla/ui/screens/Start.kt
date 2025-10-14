package com.example.styla.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Woman
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.styla.R
import com.example.styla.ui.theme.AbhayaLibre
import com.example.styla.ui.viewmodel.StartViewModel
import kotlinx.coroutines.delay


sealed class CategoryIcon {
    data class Vector(val icon: ImageVector) : CategoryIcon()
    data class Resource(val resId: Int) : CategoryIcon()
}
data class CategoryItem(val name: String, val icon: CategoryIcon)
data class NavMenuItem(val label: String, val icon: ImageVector)
data class CarouselItem(val title: String, val description: String, val imageResId: Int)

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun StartScreen(
    onOpenCamera: () -> Unit = {}
) {

    val startVm: StartViewModel = viewModel()
    val ui = startVm.ui.collectAsStateWithLifecycle().value

    val primaryColor = Color(0xFF9C5A2D)
    val backgroundColor = Color(0xFFF3E9D4)
    val cardColor = Color(0xFFE7DCCC)

    val carouselItems = remember {
        listOf(
            CarouselItem(
                title = "Tendencias",
                description = "Conoce las tendencias\npara este otoño 2025",
                imageResId = R.drawable.foto
            ),
            CarouselItem(
                title = "Colorimetría",
                description = "Potencia tu estilo eligiendo los colores que mejor armonicen con tu piel.",
                imageResId = R.drawable.color
            ),
            CarouselItem(
                title = "Siluetas",
                description = "Descubre los cortes y formas que definen la temporada.",
                imageResId = R.drawable.silueta
            )
        )
    }

    val pagerState = rememberPagerState(initialPage = 0) { carouselItems.size }
    val selectedItemIndex = 0

    // Autoplay del carrusel
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            if (!pagerState.isScrollInProgress) {
                val target = (pagerState.currentPage + 1) % carouselItems.size
                pagerState.animateScrollToPage(
                    page = target,
                    animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                )
            }
        }
    }

    val navItems = listOf(
        NavMenuItem("Inicio", Icons.Filled.Home),
        NavMenuItem("Cámara", Icons.Filled.CameraAlt),
        NavMenuItem("Guardado", Icons.Filled.FavoriteBorder),
        NavMenuItem("Perfil", Icons.Filled.Person)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.styla),
                            contentDescription = "STYLA Logo",
                            modifier = Modifier.
                            height(260.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menú", tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor)
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(cardColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    navItems.forEachIndexed { index, item ->
                        val isSelected = index == selectedItemIndex
                        val iconColor = if (isSelected) Color.White else primaryColor
                        val labelColor = if (isSelected) Color.White else Color(0xFF4B4B4B)

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                // 👉 navegación
                                .clickable {
                                    if (index == 1) onOpenCamera()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .widthIn(min = 70.dp, max = 100.dp)
                                        .height(65.dp)
                                        .clip(RoundedCornerShape(25.dp))
                                        .background(primaryColor)
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .widthIn(min = 60.dp)
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label,
                                    tint = iconColor,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.label,
                                    fontFamily = AbhayaLibre,
                                    fontSize = 12.sp,
                                    color = labelColor
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            if (ui.loading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    color = primaryColor,
                    trackColor = Color.Transparent
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Encabezado
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "¡Hola ${ui.firstName}!",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = AbhayaLibre,
                        color = Color.Black
                    )
                    Text(
                        text = "Hoy me veo genial",
                        fontSize = 16.sp,
                        fontFamily = AbhayaLibre,
                        color = Color(0xFF4B4B4B)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Búsqueda
            OutlinedTextField(
                value = "",
                onValueChange = { },
                placeholder = { Text("Buscar", fontFamily = AbhayaLibre, color = Color(0xFF4B4B4B)) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
                trailingIcon = {
                    IconButton(onClick = { /* voz */ }) {
                        Icon(Icons.Filled.Mic, contentDescription = "Voz")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    unfocusedBorderColor = Color(0xFFE0D9C5),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedLeadingIconColor = primaryColor,
                    unfocusedLeadingIconColor = Color(0xFF4B4B4B),
                    focusedTrailingIconColor = primaryColor,
                    unfocusedTrailingIconColor = Color(0xFF4B4B4B),
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Carrusel
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) { page ->
                val item = carouselItems[page]
                CarouselCard(item = item, primaryColor = primaryColor, cardColor = cardColor)
            }

            // Indicadores
            val inactiveDotColor = Color(0xFFC7B197)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                carouselItems.forEachIndexed { index, _ ->
                    val color = if (index == pagerState.currentPage) primaryColor else inactiveDotColor
                    Canvas(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(8.dp)
                    ) {
                        drawCircle(color = color, radius = 4.dp.toPx())
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Categorías
            Text(
                text = "Categorías",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = AbhayaLibre,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            val categories = listOf(
                CategoryItem("Blusas", CategoryIcon.Vector(Icons.Default.Checkroom)),
                CategoryItem("Zapatos", CategoryIcon.Resource(R.drawable.zapato1)),
                CategoryItem("Gafas", CategoryIcon.Resource(R.drawable.gafas)),
                CategoryItem("Vestidos", CategoryIcon.Vector(Icons.Default.Woman))
            )

            LazyRow(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { category ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(
                            shape = CircleShape,
                            color = cardColor,
                            modifier = Modifier.size(60.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                when (val icon = category.icon) {
                                    is CategoryIcon.Vector -> {
                                        Icon(
                                            imageVector = icon.icon,
                                            contentDescription = category.name,
                                            tint = primaryColor,
                                            modifier = Modifier
                                                .size(40.dp)
                                                .padding(8.dp)
                                        )
                                    }
                                    is CategoryIcon.Resource -> {
                                        Image(
                                            painter = painterResource(id = icon.resId),
                                            contentDescription = category.name,
                                            contentScale = ContentScale.Fit,
                                            modifier = Modifier.size(36.dp)
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(category.name, fontFamily = AbhayaLibre, fontSize = 14.sp, color = Color.Black)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // CTA
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                colors = CardDefaults.cardColors(containerColor = primaryColor),
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Quiero vender\nmi ropa",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = AbhayaLibre,
                        color = Color.White,
                        lineHeight = 22.sp
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AttachMoney,
                            contentDescription = "Vender",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Ir a vender",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CarouselCard(item: CarouselItem, primaryColor: Color, cardColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(1.3f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = AbhayaLibre,
                    color = primaryColor,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = item.description,
                    fontSize = 16.sp,
                    fontFamily = AbhayaLibre,
                    color = Color.Black,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { /* detalle */ },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    modifier = Modifier.sizeIn(minWidth = 50.dp, minHeight = 40.dp)
                ) {
                    Text("Ir", color = Color.White, fontFamily = AbhayaLibre, fontSize = 14.sp)
                }
            }
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = item.title,
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    StartScreen()
}
