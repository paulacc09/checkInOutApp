// screens/encargado/reportes/ReportesEncargadoScreen.kt
package com.mega.appcheckinout.screens.encargado.reportes

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
import com.mega.appcheckinout.screens.detalle.componentes.encargado.AlertaPermisoLimitado
import com.mega.appcheckinout.screens.detalle.componentes.encargado.TipoAlerta

/**
 * Pantalla de Reportes para Encargado
 *
 * RESTRICCIONES:
 * - Solo puede generar reportes de SU obra asignada
 * - Tipos de reportes disponibles: Asistencia diaria, semanal, mensual
 * - Puede exportar en CSV/PDF
 * - No tiene acceso a reportes de otras obras
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportesEncargadoScreen(
    obraAsignada: String,
    obraId: String = "1",
    onVolver: () -> Unit,
    onGenerarReporte: (String, String, String) -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    var tipoReporte by remember { mutableStateOf("Asistencia Diaria") }
    var expandirTipo by remember { mutableStateOf(false) }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    var filtroRol by remember { mutableStateOf("Todos") }
    var expandirFiltroRol by remember { mutableStateOf(false) }
    var mostrarVistaPrevia by remember { mutableStateOf(false) }

    val tiposReporte = listOf(
        "Asistencia Diaria",
        "Asistencia Semanal",
        "Asistencia Mensual",
        "Resumen de Personal",
        "Novedades del Período"
    )

    val roles = listOf("Todos", "Operativo", "Inspector SST", "Encargado")

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
                text = "REPORTES",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            IconButton(onClick = { /* Ayuda */ }) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Ayuda",
                    tint = colorPrimario
                )
            }
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
                titulo = "Reportes de Mi Obra",
                mensaje = "Solo puedes generar reportes de $obraAsignada. Para reportes consolidados contacta al Administrador.",
                tipo = TipoAlerta.INFO
            )

            // Card de obra
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = colorSecundario.copy(alpha = 0.2f)
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = colorPrimario,
                        modifier = Modifier.size(24.dp)
                    )
                    Column {
                        Text(
                            text = "OBRA",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = obraAsignada,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorPrimario
                        )
                    }
                }
            }

            // Configuración del reporte
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "CONFIGURACIÓN DEL REPORTE",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario
                    )

                    // Tipo de reporte
                    ExposedDropdownMenuBox(
                        expanded = expandirTipo,
                        onExpandedChange = { expandirTipo = it }
                    ) {
                        OutlinedTextField(
                            value = tipoReporte,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Tipo de reporte") },
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
                            tiposReporte.forEach { tipo ->
                                DropdownMenuItem(
                                    text = { Text(tipo) },
                                    onClick = {
                                        tipoReporte = tipo
                                        expandirTipo = false
                                    }
                                )
                            }
                        }
                    }

                    // Rango de fechas
                    Text(
                        text = "Período",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = fechaInicio,
                            onValueChange = { fechaInicio = it },
                            label = { Text("Fecha inicio", fontSize = 14.sp) },
                            placeholder = { Text("DD/MM/AAAA") },
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
                            label = { Text("Fecha fin", fontSize = 14.sp) },
                            placeholder = { Text("DD/MM/AAAA") },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorPrimario
                            ),
                            trailingIcon = {
                                Icon(Icons.Default.DateRange, null)
                            }
                        )
                    }

                    // Filtro por rol
                    ExposedDropdownMenuBox(
                        expanded = expandirFiltroRol,
                        onExpandedChange = { expandirFiltroRol = it }
                    ) {
                        OutlinedTextField(
                            value = filtroRol,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Filtrar por rol") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirFiltroRol)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorPrimario
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandirFiltroRol,
                            onDismissRequest = { expandirFiltroRol = false }
                        ) {
                            roles.forEach { rol ->
                                DropdownMenuItem(
                                    text = { Text(rol) },
                                    onClick = {
                                        filtroRol = rol
                                        expandirFiltroRol = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Acciones
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "ACCIONES",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario
                    )

                    // Botón generar vista previa
                    Button(
                        onClick = {
                            if (fechaInicio.isNotBlank() && fechaFin.isNotBlank()) {
                                mostrarVistaPrevia = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorPrimario
                        ),
                        shape = RoundedCornerShape(8.dp),
                        enabled = fechaInicio.isNotBlank() && fechaFin.isNotBlank()
                    ) {
                        Icon(Icons.Default.Search, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Generar Vista Previa", fontSize = 15.sp)
                    }

                    // Botones de exportación
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                if (mostrarVistaPrevia) {
                                    // TODO: Exportar CSV
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            enabled = mostrarVistaPrevia,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                Icons.Default.Share,
                                null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("CSV", fontSize = 14.sp)
                        }

                        OutlinedButton(
                            onClick = {
                                if (mostrarVistaPrevia) {
                                    // TODO: Exportar PDF
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            enabled = mostrarVistaPrevia,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                Icons.Default.Share,
                                null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("PDF", fontSize = 14.sp)
                        }
                    }
                }
            }

            // Vista previa del reporte
            if (mostrarVistaPrevia) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "VISTA PREVIA",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorPrimario
                            )
                            IconButton(onClick = { mostrarVistaPrevia = false }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Cerrar",
                                    tint = Color.Gray
                                )
                            }
                        }

                        Divider(modifier = Modifier.padding(vertical = 8.dp))

                        Text(
                            text = "$tipoReporte - $obraAsignada",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Período: $fechaInicio - $fechaFin",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        if (filtroRol != "Todos") {
                            Text(
                                text = "Filtro: $filtroRol",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Mensaje de datos simulados
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
                                    text = "Vista previa con datos de ejemplo. El reporte completo se generará al exportar.",
                                    fontSize = 11.sp,
                                    color = Color.Gray,
                                    lineHeight = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            // Nota informativa
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFF9800).copy(alpha = 0.1f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = Color(0xFFFF9800),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Los reportes solo incluyen datos de tu obra asignada. Para reportes consolidados o comparativos contacta al Administrador.",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        lineHeight = 14.sp
                    )
                }
            }
        }
    }
}