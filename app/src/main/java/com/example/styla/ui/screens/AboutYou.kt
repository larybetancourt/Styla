package com.example.styla.ui.screens

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.styla.R
import com.example.styla.ui.theme.AbhayaLibre
import com.example.styla.ui.viewmodel.AboutYouViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutYouScreen(
    onBack: () -> Unit = {},
    onGoToStart: () -> Unit = {}
) {
    val Background = Color(0xFFEDE7D4)
    val FieldBorder = Color(0xFF2D2D2D)
    val Primary = Color(0xFF9B5D38)

    val vm: AboutYouViewModel = viewModel()
    val ui by vm.ui.collectAsStateWithLifecycle()

    var genderMenu by remember { mutableStateOf(false) }
    var styleMenu by remember { mutableStateOf(false) }
    val showDatePicker = remember { mutableStateOf(false) }

    // Formateador dd/MM/aa
    val formatter = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            DateTimeFormatter.ofPattern("dd/MM/yy", Locale("es", "ES"))
        else null
    }

    // Navega cuando se guarde correctamente
    LaunchedEffect(ui.saved) {
        if (ui.saved) onGoToStart()
    }

    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(inner)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            Text(
                text = "Cuéntanos sobre ti",
                fontSize = 32.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontFamily = AbhayaLibre,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )

            Text(
                text = "Crearemos una experiencia personalizada",
                fontSize = 14.sp,
                color = Color(0xFF4B4B4B),
                textAlign = TextAlign.Center,
                fontFamily = AbhayaLibre,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
            )

            Spacer(Modifier.height(28.dp))

            // -------- Género --------
            ExposedDropdownMenuBox(
                expanded = genderMenu,
                onExpandedChange = { genderMenu = !genderMenu },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = ui.gender,
                    onValueChange = { /* readOnly */ },
                    readOnly = true,
                    placeholder = { Text("Género", fontFamily = AbhayaLibre, color = Color(0xFF4B4B4B)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderMenu) },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = FieldBorder,
                        unfocusedBorderColor = FieldBorder,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Background,
                        unfocusedContainerColor = Background,
                        focusedPlaceholderColor = Color(0xFF8C8C8C),
                        unfocusedPlaceholderColor = Color(0xFF8C8C8C)
                    )
                )
                ExposedDropdownMenu(
                    expanded = genderMenu,
                    onDismissRequest = { genderMenu = false }
                ) {
                    listOf("Femenino", "Masculino", "Prefiero no decir").forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, fontFamily = AbhayaLibre) },
                            onClick = {
                                vm.onGenderChange(option)
                                genderMenu = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // -------- Cumpleaños --------
            OutlinedTextField(
                value = ui.birthday,
                onValueChange = { raw ->
                    val formatted = formatDateInput(raw) // dd/MM/yy en vivo
                    vm.onBirthdayChange(formatted)
                },
                placeholder = { Text("Cumpleaños (dd/mm/aa)", fontFamily = AbhayaLibre, color = Color(0xFF4B4B4B)) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { showDatePicker.value = true }) {
                        Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = "Seleccionar fecha",
                            tint = Color.Black
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FieldBorder,
                    unfocusedBorderColor = FieldBorder,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Background,
                    unfocusedContainerColor = Background,
                    focusedPlaceholderColor = Color(0xFF8C8C8C),
                    unfocusedPlaceholderColor = Color(0xFF8C8C8C)
                )
            )

            Spacer(Modifier.height(16.dp))

            // -------- Estilo --------
            ExposedDropdownMenuBox(
                expanded = styleMenu,
                onExpandedChange = { styleMenu = !styleMenu },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = ui.style,
                    onValueChange = { /* readOnly */ },
                    readOnly = true,
                    placeholder = { Text("Preferencias de estilo", fontFamily = AbhayaLibre, color = Color(0xFF4B4B4B)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = styleMenu) },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = FieldBorder,
                        unfocusedBorderColor = FieldBorder,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Background,
                        unfocusedContainerColor = Background,
                        focusedPlaceholderColor = Color(0xFF8C8C8C),
                        unfocusedPlaceholderColor = Color(0xFF8C8C8C)
                    )
                )
                ExposedDropdownMenu(
                    expanded = styleMenu,
                    onDismissRequest = { styleMenu = false }
                ) {
                    listOf("minimalista", "casual", "clásico", "romántico", "formal").forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.replaceFirstChar { it.uppercase() }, fontFamily = AbhayaLibre) },
                            onClick = {
                                vm.onStyleChange(option)
                                styleMenu = false
                            }
                        )
                    }
                }
            }

            // Error
            if (ui.error != null) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = ui.error!!,
                    color = Color(0xFFB00020),
                    textAlign = TextAlign.Center,
                    fontFamily = AbhayaLibre,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(24.dp))


            // Botón "Listo"
            if (ui.style.isNotBlank()) {
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { vm.save() },
                    enabled = !ui.loading,
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(48.dp)
                ) {
                    Text("Listo", color = Color.White, fontFamily = AbhayaLibre)
                }
            }

            Spacer(Modifier.height(24.dp))

            Image(
                painter = painterResource(id = R.drawable.aboutyou),
                contentDescription = "Imagen",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .height(300.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        // DatePickerDialog
        if (showDatePicker.value) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showDatePicker.value = false },
                confirmButton = {
                    TextButton(onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && formatter != null) {
                            val text = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                                .format(formatter) // dd/MM/aa
                            vm.onBirthdayChange(text)
                        }
                        showDatePicker.value = false
                    }) { Text("OK", fontFamily = AbhayaLibre) }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker.value = false }) { Text("Cancelar", fontFamily = AbhayaLibre) }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}


private fun formatDateInput(input: String): String {
    val digits = input.filter { it.isDigit() }.take(6) // ddMMyy
    val out = StringBuilder()
    for (i in digits.indices) {
        out.append(digits[i])
        if ((i == 1 || i == 3) && i != digits.lastIndex) out.append('/')
    }
    return out.toString()
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xFFEDE7D4
)
@Composable
fun AboutYouScreenPreview() {
    AboutYouScreen()
}
