package com.example.styla.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.styla.R
import com.example.styla.ui.theme.AbhayaLibre
import com.example.styla.ui.theme.StylaTheme
import com.example.styla.ui.viewmodel.FormViewModel
import androidx.compose.material3.Checkbox
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle


@Composable
fun Form(
    onBack: () -> Unit = {},
    onSuccess: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {}
) {
    val vm: FormViewModel = viewModel()
    val ui by vm.ui.collectAsStateWithLifecycle()

    var showPassword by remember { mutableStateOf(false) }
    var showPassword2 by remember { mutableStateOf(false) }

    Scaffold(containerColor = Color(0xFFEDE7D4)) { inner ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = 24.dp)
                .padding(top = 12.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 48.dp, bottom = 92.dp)
                    .offset(y = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título
                Text(
                    text = "Vamos a\nregistrarte",
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                    color = Color(0xFF1E1E1E),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = AbhayaLibre
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Los campos con * son obligatorios",
                    fontSize = 14.sp,
                    color = Color(0xFF4B4B4B),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = AbhayaLibre
                )

                Spacer(modifier = Modifier.height(24.dp))

                val textFieldShape = RoundedCornerShape(12.dp)
                val containerColor = Color(0xFFF6F2EC)
                val borderColor = Color(0xFF2D2D2D)
                val placeholderColor = Color(0xFF8C8C8C)

                // Nombre
                OutlinedTextField(
                    value = ui.nombre,
                    onValueChange = vm::onNombreChange,
                    placeholder = { Text("Nombre*", fontFamily = AbhayaLibre) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = textFieldShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = borderColor,
                        unfocusedBorderColor = borderColor,
                        focusedPlaceholderColor = placeholderColor,
                        unfocusedPlaceholderColor = placeholderColor,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Correo
                OutlinedTextField(
                    value = ui.correo,
                    onValueChange = vm::onCorreoChange,
                    placeholder = { Text("Correo electrónico*", fontFamily = AbhayaLibre) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = textFieldShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = borderColor,
                        unfocusedBorderColor = borderColor,
                        focusedPlaceholderColor = placeholderColor,
                        unfocusedPlaceholderColor = placeholderColor,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Contraseña
                OutlinedTextField(
                    value = ui.password,
                    onValueChange = vm::onPasswordChange,
                    placeholder = { Text("Crea una contraseña*", fontFamily = AbhayaLibre) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = textFieldShape,
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = if (showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = null
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = borderColor,
                        unfocusedBorderColor = borderColor,
                        focusedPlaceholderColor = placeholderColor,
                        unfocusedPlaceholderColor = placeholderColor,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Confirmar contraseña
                OutlinedTextField(
                    value = ui.confirmPassword,
                    onValueChange = vm::onConfirmPasswordChange,
                    placeholder = { Text("Confirma tu contraseña*", fontFamily = AbhayaLibre) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = textFieldShape,
                    visualTransformation = if (showPassword2) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPassword2 = !showPassword2 }) {
                            Icon(
                                imageVector = if (showPassword2) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = null
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = borderColor,
                        unfocusedBorderColor = borderColor,
                        focusedPlaceholderColor = placeholderColor,
                        unfocusedPlaceholderColor = placeholderColor,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // T&C ficticios
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = ui.acceptedTerms,
                        onCheckedChange = vm::onTermsChange
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "Acepto Términos y condiciones",
                        fontSize = 14.sp,
                        color = Color(0xFF1E1E1E),
                        fontFamily = AbhayaLibre
                    )
                }


                // Error
                ui.error?.let {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = it,
                        color = Color(0xFFB00020),
                        fontFamily = AbhayaLibre,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                val Beige = Color(0xFFE7DBC7)

                // Botón CREAR
                ElevatedButton(
                    onClick = { if (!ui.loading) vm.submit() },
                    enabled = !ui.loading && ui.acceptedTerms,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFF9B5D38), disabledContainerColor = Beige.copy(alpha = 0.60f))
                ) {
                    if (ui.loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(22.dp)
                        )
                    } else {
                        Text(
                            text = "CREAR",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = AbhayaLibre
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                val linkColor = Color(0xFF9C5A2D) // tu marrón/primario

                val annotated = buildAnnotatedString {
                    append("¿Ya tienes una cuenta? ")
                    pushStringAnnotation(tag = "login", annotation = "login")
                    withStyle(
                        SpanStyle(
                            color = linkColor,
                            fontWeight = FontWeight.SemiBold,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("Ingresa aquí")
                    }
                    pop()
                }

                ClickableText(
                    text = annotated,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF1E1E1E),
                        textAlign = TextAlign.Center,
                        fontFamily = AbhayaLibre
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { offset ->
                        annotated
                            .getStringAnnotations(tag = "login", start = offset, end = offset)
                            .firstOrNull()
                            ?.let { onNavigateToLogin() }   // ⬅️ navega al login
                    }
                )

                Spacer(modifier = Modifier.height(36.dp))
            }

            // Logo inferior
            Image(
                painter = painterResource(id = R.drawable.stylalogo),
                contentDescription = "Icono Styla",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
            )

            // Flecha atrás
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.Black
                )
            }

            // POPUP de éxito → luego navegarás a Start desde onOk
            if (ui.success) {
                SuccessDialog(
                    fullName = ui.nombre,
                    onOk = {
                        vm.consumeSuccess()
                        onSuccess()
                    }
                )
            }
        }
    }
}

/** Diálogo “¡Hola {nombre}!” */
@Composable
private fun SuccessDialog(
    fullName: String,
    onOk: () -> Unit
) {
    val firstName = fullName.trim().substringBefore(" ").ifEmpty { "!" }

    Dialog(onDismissRequest = onOk) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color(0xFFF6F0E6))
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "¡Hola $firstName!",
                    fontFamily = AbhayaLibre,
                    fontSize = 26.sp,
                    color = Color(0xFF1E1E1E),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Tu cuenta ha sido creada\nexitosamente",
                    fontFamily = AbhayaLibre,
                    fontSize = 18.sp,
                    color = Color(0xFF1E1E1E),
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = onOk,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF9B5D38),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .defaultMinSize(minWidth = 96.dp)
                        .height(40.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Text("OK", fontFamily = AbhayaLibre)
                }
            }
        }
    }
}

/* --------- Preview opcional --------- */
@Preview(showBackground = true, backgroundColor = 0xFFEDE7D4)
@Composable
private fun FormPreview() {
    StylaTheme {
        Form()
    }
}
