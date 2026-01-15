// screens/encargado/personal/SolicitarTraspasoScreen.kt
package com.mega.appcheckinout.screens.encargado.personal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.mega.appcheckinout.models.TrabajadorCompleto
import com.mega.appcheckinout.models.Obra
import com.mega.appcheckinout.screens.detalle.componentes.encargado.AlertaPermisoLimitado
import com.mega.appcheckinout.screens.detalle.componentes.encargado.TipoAlerta

/**
 * Pantalla para Solicitar Traspaso de Trabajador - Encargado
 *
 * RESTRICCIONES:
 * - Solo puede solicitar traspaso de trabajadores de SU obra
 * - NO ejecuta el traspaso directamente (requiere aprobación de Administrador)
 * - La solicitud queda en estado PENDIENTE
 * - Debe justificar obligatoriamente el motivo del traspaso
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolicitarTraspasoScreen(
    obraAsignada: String,
    obraIdActual: String = "1",
    onVolver: () -> Unit,
    onSolicitudEnviada: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    var trabajadorSeleccionado by remember { mutableStateOf<TrabajadorCompleto?>(null) }
    var expandirTrabajador by remember { mutableStateOf(false) }
    var obraDestino by remember { mutableStateOf<Obra?>(null) }
    var expandirObraDestino by remember { mutableStateOf(false) }
    var motivoTraspaso by remember { mutableStateOf("") }
    var fechaTraspaso by remember { mutableStateOf("") }
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    // Trabajadores de la obra actual
    val trabajadoresObra = remember {
        DatosEjemplo.getTrabajadoresPorObra(obraIdActual)
            .filter { it.estado == "Activo" }
    }

    // Obras disponibles (excluyendo la obra actual)
    val obrasDestino = remember {
        DatosEjemplo.getObrasActivas()
            .filter { it.id != obraIdActual }
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
                text = "SOLICITAR TRASPASO",
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
                titulo = "Solicitud de Traspaso",
                mensaje = "La solicitud será enviada al Administrador para su aprobación. El traspaso no se ejecuta automáticamente.",
                tipo = TipoAlerta.WARNING
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
                        text = "DATOS DEL TRASPASO",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario
                    )

                    // Obra origen (solo lectura)
                    OutlinedTextField(
                        value = obraAsignada,
                        onValueChange = {},
                        label = { Text("Obra origen") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        enabled = false,
                        leadingIcon = {
                            Icon(Icons.Default.LocationOn, null)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledBorderColor = Color.Gray,
                            disabledLabelColor = Color.Gray,
                            disabledTextColor = Color.Gray
                        )
                    )

                    // Selector de trabajador
                    ExposedDropdownMenuBox(
                        expanded = expandirTrabajador,
                        onExpandedChange = { expandirTrabajador = it }
                    ) {
                        OutlinedTextField(
                            value = trabajadorSeleccionado?.nombreCompleto() ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Trabajador a traspasar *") },
                            placeholder = { Text("Selecciona un trabajador") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirTrabajador)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorPrimario
                            ),
                            leadingIcon = {
                                Icon(Icons.Default.Person, null)
                            }
                        )
                        ExposedDropdownMenu(
                            expanded = expandirTrabajador,
                            onDismissRequest = { expandirTrabajador = false }
                        ) {
                            if (trabajadoresObra.isEmpty()) {
                                DropdownMenuItem(
                                    text = { Text("No hay trabajadores disponibles") },
                                    onClick = { }
                                )
                            } else {
                                trabajadoresObra.forEach { trabajador ->
                                    DropdownMenuItem(
                                        text = {
                                            Column {
                                                Text(
                                                    trabajador.nombreCompleto(),
                                                    fontWeight = FontWeight.Bold
                                                )
                                                Text(
                                                    "CC: ${trabajador.numeroDocumento} - ${trabajador.rol}",
                                                    fontSize = 12.sp,
                                                    color = Color.Gray
                                                )
                                            }
                                        },
                                        onClick = {
                                            trabajadorSeleccionado = trabajador
                                            expandirTrabajador = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Selector de obra destino
                    ExposedDropdownMenuBox(
                        expanded = expandirObraDestino,
                        onExpandedChange = { expandirObraDestino = it }
                    ) {
                        OutlinedTextField(
                            value = obraDestino?.nombre ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Obra destino *") },
                            placeholder = { Text("Selecciona obra destino") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirObraDestino)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorPrimario
                            ),
                            leadingIcon = {
                                Icon(Icons.Default.LocationOn, null)
                            }
                        )
                        ExposedDropdownMenu(
                            expanded = expandirObraDestino,
                            onDismissRequest = { expandirObraDestino = false }
                        ) {
                            if (obrasDestino.isEmpty()) {
                                DropdownMenuItem(
                                    text = { Text("No hay obras disponibles") },
                                    onClick = { }
                                )
                            } else {
                                obrasDestino.forEach { obra ->
                                    DropdownMenuItem(
                                        text = {
                                            Column {
                                                Text(obra.nombre, fontWeight = FontWeight.Bold)
                                                Text(
                                                    "${obra.ciudad} - ${obra.codigo}",
                                                    fontSize = 12.sp,
                                                    color = Color.Gray
                                                )
                                            }
                                        },
                                        onClick = {
                                            obraDestino = obra
                                            expandirObraDestino = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Fecha estimada de traspaso
                    OutlinedTextField(
                        value = fechaTraspaso,
                        onValueChange = { fechaTraspaso = it },
                        label = { Text("Fecha estimada de traspaso *") },
                        placeholder = { Text("DD/MM/YYYY") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorPrimario
                        ),
                        leadingIcon = {
                            Icon(Icons.Default.DateRange, null)
                        }
                    )

                    // Motivo del traspaso (obligatorio)
                    OutlinedTextField(
                        value = motivoTraspaso,
                        onValueChange = { motivoTraspaso = it },
                        label = { Text("Justificación del traspaso *") },
                        placeholder = { Text("Explica el motivo del traspaso...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        maxLines = 6,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorPrimario
                        )
                    )
                }
            }

            // Información del trabajador seleccionado
            if (trabajadorSeleccionado != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = colorSecundario.copy(alpha = 0.2f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "INFORMACIÓN DEL TRABAJADOR",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorPrimario,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Rol:", fontSize = 12.sp, color = Color.Gray)
                                Text(
                                    trabajadorSeleccionado!!.rol,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            if (trabajadorSeleccionado!!.subCargo.isNotEmpty()) {
                                Column {
                                    Text("Subcargo:", fontSize = 12.sp, color = Color.Gray)
                                    Text(
                                        trabajadorSeleccionado!!.subCargo,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                            if (trabajadorSeleccionado!!.actividad.isNotEmpty()) {
                                Column {
                                    Text("Actividad:", fontSize = 12.sp, color = Color.Gray)
                                    Text(
                                        trabajadorSeleccionado!!.actividad,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Botón enviar solicitud
            Button(
                onClick = {
                    if (trabajadorSeleccionado != null &&
                        obraDestino != null &&
                        fechaTraspaso.isNotBlank() &&
                        motivoTraspaso.isNotBlank() &&
                        motivoTraspaso.length >= 20) {
                        mostrarConfirmacion = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = trabajadorSeleccionado != null &&
                        obraDestino != null &&
                        fechaTraspaso.isNotBlank() &&
                        motivoTraspaso.isNotBlank() &&
                        motivoTraspaso.length >= 20,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorPrimario
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Icon(Icons.Default.Send, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Enviar Solicitud", fontSize = 16.sp)
            }

            if (motivoTraspaso.isNotBlank() && motivoTraspaso.length < 20) {
                Text(
                    text = "⚠ La justificación debe tener al menos 20 caracteres (${motivoTraspaso.length}/20)",
                    fontSize = 12.sp,
                    color = Color(0xFFFF9800),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            // Nota informativa
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2196F3).copy(alpha = 0.1f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color(0xFF2196F3),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "La solicitud será revisada por el Administrador. Recibirás notificación cuando sea aprobada o rechazada.",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        lineHeight = 14.sp
                    )
                }
            }
        }
    }

    // Diálogo de confirmación
    if (mostrarConfirmacion) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacion = false },
            icon = { Icon(Icons.Default.Send, null, tint = colorPrimario) },
            title = { Text("Confirmar Solicitud de Traspaso") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("¿Confirmas enviar esta solicitud de traspaso?", fontWeight = FontWeight.Bold)
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text("• Trabajador: ${trabajadorSeleccionado?.nombreCompleto()}", fontSize = 14.sp)
                    Text("• De: $obraAsignada", fontSize = 14.sp)
                    Text("• A: ${obraDestino?.nombre}", fontSize = 14.sp)
                    Text("• Fecha: $fechaTraspaso", fontSize = 14.sp)
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        "La solicitud quedará en estado PENDIENTE hasta su aprobación.",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // TODO: Enviar solicitud a BD
                        onSolicitudEnviada()
                        mostrarConfirmacion = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorPrimario
                    )
                ) {
                    Text("Enviar Solicitud")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarConfirmacion = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}