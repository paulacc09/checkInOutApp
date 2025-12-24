package com.mega.appcheckinout.screens.administrador.gestion_obras

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.ui.theme.BotonVolver
import java.util.UUID
import com.mega.appcheckinout.data.DatosEjemplo

@Composable
fun CrearObraScreen(
    onVolver: () -> Unit,
    onObraCreada: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Estados del formulario
    var codigo by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFinEstimada by remember { mutableStateOf("") }
    var responsableSISO by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    // Estados de validación
    var mostrarError by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    // Lista de ciudades (puede venir de base de datos)
    val ciudades = DatosEjemplo.ciudades

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EFF5))
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFB8D4E3))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BotonVolver(
                onClick = onVolver,
                colorIcono = Color.White,
                colorFondo = colorPrimario
            )

            Text(
                text = "CREAR NUEVA OBRA",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        // Formulario con scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título de sección
            Text(
                text = "DATOS DE LA OBRA",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Código único de obra
            OutlinedTextField(
                value = codigo,
                onValueChange = { codigo = it.uppercase() },
                label = { Text("Código único de obra *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                )
            )

            // Nombre del proyecto
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del proyecto *") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                )
            )

            // Constructora
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Constructora *") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                )
            )

            // Ciudad (Dropdown)
            // Ciudad (Dropdown alternativo)
            var expandedCiudad by remember { mutableStateOf(false) }

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = ciudad,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Ciudad *") },
                    trailingIcon = {
                        IconButton(onClick = { expandedCiudad = !expandedCiudad }) {
                            Icon(
                                painter = painterResource(
                                    if (expandedCiudad) R.drawable.arrow_up_float
                                    else R.drawable.arrow_down_float
                                ),
                                contentDescription = "Seleccionar ciudad"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorPrimario,
                        focusedLabelColor = colorPrimario
                    )
                )

                DropdownMenu(
                    expanded = expandedCiudad,
                    onDismissRequest = { expandedCiudad = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ciudades.forEach { ciudadItem ->
                        DropdownMenuItem(
                            text = { Text(ciudadItem) },
                            onClick = {
                                ciudad = ciudadItem
                                expandedCiudad = false
                            }
                        )
                    }
                }
            }

            // Dirección específica
            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección específica *") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                )
            )

            // Fecha de inicio
            OutlinedTextField(
                value = fechaInicio,
                onValueChange = { fechaInicio = it },
                label = { Text("Fecha de inicio (DD/MM/YYYY) *") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("01/01/2025") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                )
            )

            // Fecha estimada de finalización
            OutlinedTextField(
                value = fechaFinEstimada,
                onValueChange = { fechaFinEstimada = it },
                label = { Text("Fecha estimada de finalización (DD/MM/YYYY)") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("31/12/2025") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                )
            )

            // Responsable SISO
            OutlinedTextField(
                value = responsableSISO,
                onValueChange = { responsableSISO = it },
                label = { Text("Responsable SISO *") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                )
            )

            // Descripción breve
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción breve") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 4,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                )
            )

            // Mensaje de error
            if (mostrarError) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = mensajeError,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Botón Cancelar
                Button(
                    onClick = onVolver,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cancelar", fontSize = 16.sp)
                }

                // Botón Guardar Obra
                Button(
                    onClick = {
                        // Validación de campos obligatorios
                        when {
                            codigo.isBlank() -> {
                                mostrarError = true
                                mensajeError = "El código de obra es obligatorio"
                            }
                            nombre.isBlank() -> {
                                mostrarError = true
                                mensajeError = "El nombre del proyecto es obligatorio"
                            }
                            ciudad.isBlank() -> {
                                mostrarError = true
                                mensajeError = "La ciudad es obligatoria"
                            }
                            direccion.isBlank() -> {
                                mostrarError = true
                                mensajeError = "La dirección es obligatoria"
                            }
                            fechaInicio.isBlank() -> {
                                mostrarError = true
                                mensajeError = "La fecha de inicio es obligatoria"
                            }
                            responsableSISO.isBlank() -> {
                                mostrarError = true
                                mensajeError = "El responsable SISO es obligatorio"
                            }
                            else -> {
                                // TODO: Guardar en base de datos
                                // Generar ID único
                                val idObra = UUID.randomUUID().toString()

                                // Aquí guardarías en tu base de datos
                                println("Obra creada: $idObra - $nombre")

                                mostrarError = false
                                onObraCreada()
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorPrimario
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = codigo.isNotBlank() && nombre.isNotBlank() &&
                            ciudad.isNotBlank() && direccion.isNotBlank() &&
                            fechaInicio.isNotBlank() && responsableSISO.isNotBlank()
                ) {
                    Text("Guardar Obra", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}