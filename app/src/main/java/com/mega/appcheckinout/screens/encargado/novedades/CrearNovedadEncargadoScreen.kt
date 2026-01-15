// screens/encargado/novedades/CrearNovedadEncargadoScreen.kt
package com.mega.appcheckinout.screens.encargado.novedades

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.ui.theme.BotonVolver
import com.mega.appcheckinout.data.DatosEjemplo
import com.mega.appcheckinout.models.TipoNovedad
import com.mega.appcheckinout.screens.detalle.componentes.encargado.AlertaPermisoLimitado
import com.mega.appcheckinout.screens.detalle.componentes.encargado.TipoAlerta

/**
 * Pantalla para Crear Novedad - Encargado
 *
 * Permite al encargado registrar novedades (incapacidades, permisos, etc.)
 * Las novedades creadas quedan en estado PENDIENTE hasta aprobación
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearNovedadEncargadoScreen(
    obraAsignada: String,
    onVolver: () -> Unit,
    onGuardar: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    var trabajadorSeleccionado by remember { mutableStateOf("") }
    var expandirTrabajador by remember { mutableStateOf(false) }
    var tipoNovedad by remember { mutableStateOf(TipoNovedad.PERMISO) }
    var expandirTipo by remember { mutableStateOf(false) }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    val trabajadoresObra = remember {
        DatosEjemplo.getTrabajadoresPorObra("1")
    }

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
                text = "CREAR NOVEDAD",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Alerta informativa
            AlertaPermisoLimitado(
                titulo = "Registro de Novedad",
                mensaje = "La novedad quedará en estado PENDIENTE hasta su aprobación por Inspector/a SST o Administrador.",
                tipo = TipoAlerta.INFO
            )

            // Formulario
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "DATOS DE LA NOVEDAD",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario
                    )

                    // Selector de trabajador
                    ExposedDropdownMenuBox(
                        expanded = expandirTrabajador,
                        onExpandedChange = { expandirTrabajador = it }
                    ) {
                        OutlinedTextField(
                            value = trabajadorSeleccionado,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Trabajador *") },
                            placeholder = { Text("Selecciona un trabajador") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirTrabajador)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorPrimario
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandirTrabajador,
                            onDismissRequest = { expandirTrabajador = false }
                        ) {
                            trabajadoresObra.forEach { trabajador ->
                                DropdownMenuItem(
                                    text = { Text(trabajador.nombreCompleto()) },
                                    onClick = {
                                        trabajadorSeleccionado = trabajador.nombreCompleto()
                                        expandirTrabajador = false
                                    }
                                )
                            }
                        }
                    }

                    // Tipo de novedad
                    ExposedDropdownMenuBox(
                        expanded = expandirTipo,
                        onExpandedChange = { expandirTipo = it }
                    ) {
                        OutlinedTextField(
                            value = tipoNovedad.displayName,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Tipo de novedad *") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirTipo)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorPrimario
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandirTipo,
                            onDismissRequest = { expandirTipo = false }
                        ) {
                            TipoNovedad.entries.forEach { tipo ->
                                DropdownMenuItem(
                                    text = { Text(tipo.displayName) },
                                    onClick = {
                                        tipoNovedad = tipo
                                        expandirTipo = false
                                    }
                                )
                            }
                        }
                    }

                    // Fechas
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = fechaInicio,
                            onValueChange = { fechaInicio = it },
                            label = { Text("Fecha inicio *") },
                            placeholder = { Text("DD/MM/YYYY") },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorPrimario
                            ),
                            trailingIcon = {
                                Icon(Icons.Default.DateRange, null)
                            }
                        )
                        OutlinedTextField(
                            value = fechaFin,
                            onValueChange = { fechaFin = it },
                            label = { Text("Fecha fin *") },
                            placeholder = { Text("DD/MM/YYYY") },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorPrimario
                            ),
                            trailingIcon = {
                                Icon(Icons.Default.DateRange, null)
                            }
                        )
                    }

                    // Descripción
                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripción *") },
                        placeholder = { Text("Describe la novedad...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        maxLines = 5,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorPrimario
                        )
                    )

                    // Botón adjuntar evidencia
                    OutlinedButton(
                        onClick = { /* TODO: Adjuntar archivo */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Add, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Adjuntar evidencia (opcional)")
                    }
                }
            }

            // Botón guardar
            Button(
                onClick = {
                    if (trabajadorSeleccionado.isNotBlank() &&
                        fechaInicio.isNotBlank() &&
                        fechaFin.isNotBlank() &&
                        descripcion.isNotBlank()) {
                        mostrarConfirmacion = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = trabajadorSeleccionado.isNotBlank() &&
                        fechaInicio.isNotBlank() &&
                        fechaFin.isNotBlank() &&
                        descripcion.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorPrimario
                )
            ) {
                Icon(Icons.Default.Check, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Registrar Novedad", fontSize = 16.sp)
            }
        }
    }

    // Diálogo de confirmación
    if (mostrarConfirmacion) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacion = false },
            icon = { Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF4CAF50)) },
            title = { Text("Novedad Registrada") },
            text = {
                Column {
                    Text("✓ La novedad ha sido registrada exitosamente")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Estado: PENDIENTE", fontWeight = FontWeight.Bold)
                    Text("Esperando aprobación de Inspector/a SST", fontSize = 14.sp, color = Color.Gray)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onGuardar()
                        mostrarConfirmacion = false
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}