package com.example.styla.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.styla.R
import com.example.styla.ui.theme.AbhayaLibre
import com.example.styla.ui.theme.StylaTheme
import kotlinx.coroutines.delay

private data class Slide(val imageRes: Int, val caption: String)

private val IMAGE_HEIGHT = 520.dp

@Composable
fun WelcomeScreen(
    onCreateAccountClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val typo = MaterialTheme.typography

    val slides = remember {
        listOf(
            Slide(R.drawable.image1, "Descubre, combina y sorprende\ncada día con tus looks"),
            Slide(R.drawable.image2, "Tu prenda, infinitas ideas"),
            Slide(R.drawable.image3, "Tu estilo siempre en movimiento:\nCompra y vende en un click")
        )
    }

    val pagerState = rememberPagerState(initialPage = 0) { slides.size }

    // Autoplay cada 3 sg
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            if (!pagerState.isScrollInProgress) {
                val target = (pagerState.currentPage + 1) % slides.size
                pagerState.animateScrollToPage(
                    page = target,
                    animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primaryContainer)
            .verticalScroll(rememberScrollState())
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ---------- Carrusel  ----------
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(IMAGE_HEIGHT)
        ) { page ->
            val slide = slides[page]
            Image(
                painter = painterResource(slide.imageRes),
                contentDescription = "slide $page",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // ---------- Indicadores + caption + botones ----------
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp, top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Puntos
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(slides.size) { idx ->
                    val isActive = pagerState.currentPage == idx
                    Dot(
                        size = 12.dp,
                        color = if (isActive) colors.secondary else colors.outlineVariant
                    )
                    if (idx != slides.lastIndex) Spacer(Modifier.width(10.dp))
                }
            }

            Spacer(Modifier.height(40.dp))


            Text(
                text = slides[pagerState.currentPage].caption,
                style = typo.titleLarge.copy(
                    fontFamily = AbhayaLibre,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.onPrimaryContainer,
                    lineHeight = 22.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(50.dp))

            // Botones
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ElevatedButton(
                    onClick = onCreateAccountClick,
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = colors.surface,
                        contentColor = colors.onSurface
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 12.dp
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Text(
                        "Crear cuenta",
                        style = typo.labelLarge.copy(
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                ElevatedButton(
                    onClick = onLoginClick,
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = colors.surface,
                        contentColor = colors.onSurface
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 12.dp
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Text(
                        "Inicio de sesión",
                        style = typo.labelLarge.copy(
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }

        Spacer(Modifier.height(28.dp))

        // ---------- Barra de marcas ----------
        BrandsStrip(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .background(colors.secondaryContainer)
        )

        Spacer(Modifier.height(24.dp))

        // ---------- Sección final ----------
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 16.dp)
        ) {
            PromoRow(
                leftImage = R.drawable.shop_photo,
                rightText = "Compra las prendas que\nte hagan falta de tus\ntiendas favoritas"
            )
            Spacer(Modifier.height(24.dp))
            PromoRow(
                leftText = "Vende las prendas\nque ya no uses",
                rightImage = R.drawable.closet_photo
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}

/** Punto del indicador */
@Composable
private fun Dot(size: Dp, color: Color) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(50))
            .background(color)
    )
}

/** Barra inferior*/
@Composable
private fun BrandsStrip(modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colorScheme
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BrandLogo(R.drawable.zara, "ZARA", colors.onSecondaryContainer)
        BrandLogo(R.drawable.bershka, "BERSHKA", colors.onSecondaryContainer)
        BrandLogo(R.drawable.hm, "H&M", colors.onSecondaryContainer)
    }
}

@Composable
private fun BrandLogo(drawableRes: Int, fallback: String, tint: Color) {
    val painter = runCatching { painterResource(drawableRes) }.getOrNull()
    if (painter != null) {
        Image(
            painter = painter,
            contentDescription = fallback,
            modifier = Modifier.height(80.dp),
            contentScale = ContentScale.Fit
        )
    } else {
        Text(
            text = fallback,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.SemiBold,
                color = tint
            )
        )
    }
}

/** Fila con imagen y texto */
@Composable
private fun PromoRow(
    leftImage: Int? = null,
    rightImage: Int? = null,
    leftText: String? = null,
    rightText: String? = null
) {
    val colors = MaterialTheme.colorScheme
    val typo = MaterialTheme.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp),  // ya tenemos padding en el padre
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Izquierda
        Box(modifier = Modifier.weight(1f)) {
            when {
                leftImage != null -> Image(
                    painter = painterResource(leftImage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(6.dp))
                )
                leftText != null -> Text(
                    text = leftText,
                    style = typo.titleMedium.copy(
                        color = colors.onPrimaryContainer,
                        lineHeight = 22.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        // Derecha
        Box(modifier = Modifier.weight(1f)) {
            when {
                rightImage != null -> Image(
                    painter = painterResource(rightImage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(6.dp))
                )
                rightText != null -> Text(
                    text = rightText,
                    style = typo.titleMedium.copy(
                        color = colors.onPrimaryContainer,
                        lineHeight = 22.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

/* -------------------------- PREVIEW -------------------------- */

@Preview(showBackground = true, backgroundColor = 0xFFEDE7D4)
@Composable
private fun WelcomePreview_Light() {
    StylaTheme(darkTheme = false, dynamicColor = false) {
        WelcomeScreen(onCreateAccountClick = {}, onLoginClick = {})
    }
}
