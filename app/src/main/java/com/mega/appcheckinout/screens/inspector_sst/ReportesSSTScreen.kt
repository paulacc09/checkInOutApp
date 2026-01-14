// screens/inspector_sst/ReportesSSTScreen.kt
package com.mega.appcheckinout.screens.inspector_sst

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.mega.appcheckinout.screens.administrador.reportes.RegistroAsistencia
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportesSSTScreen(
    obraAsignada: String,
    onVolver: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Estados de filtros
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    var filtroRol by remember { mutableStateOf("Todos") }
    var expandirFiltroRol by remember { mutableStateOf(false) }

    // Estados de vista previa
    var mostrarVistaPrevia by remember { mutableStateOf(false) }
    var tipoReporteActual by remember { mutableStateOf("Asistencia diaria") }

    val roles = listOf("Todos", "Operativo", "Encargado", "Inspector SST")

    // Datos de ejemplo
    val trabajadoresObra = remember {
        DatosEjemplo.getTrabajadoresPorObra("1")
    }

    val registrosAsistencia = remember {
        DatosEjemplo.getRegistrosAsistencia()
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

        // Contenido con scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card informativa
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
                        Text(
                            text = "Reportes limitados a su obra asignada",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Sección: Filtros
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "FILTROS DEL REPORTE",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Rango de fechas
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = fechaInicio,
                            onValueChange = { fechaInicio = it },
                            label = { Text("Fecha inicio", fontSize = 14.sp) },
                            placeholder = { Text("DD/MM/AAAA", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorPrimario
                            ),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = null,
                                    tint = colorPrimario
                                )
                            }
                        )
                        OutlinedTextField(
                            value = fechaFin,
                            onValueChange = { fechaFin = it },
                            label = { Text("Fecha fin", fontSize = 14.sp) },
                            placeholder = { Text("DD/MM/AAAA", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorPrimario
                            ),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = null,
                                    tint = colorPrimario
                                )
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Filtro por rol
                    ExposedDropdownMenuBox(
                        expanded = expandirFiltroRol,
                        onExpandedChange = { expandirFiltroRol = it }
                    ) {
                        OutlinedTextField(
                            value = filtroRol,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Filtrar por rol", fontSize = 14.sp) },
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
                                    text = { Text(rol, fontSize = 14.sp) },
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

            // Sección: Botones de acción
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
                        color = colorPrimario,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Button(
                        onClick = {
                            if (fechaInicio.isNotBlank() && fechaFin.isNotBlank()) {
                                mostrarVistaPrevia = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorPrimario),
                        shape = RoundedCornerShape(8.dp),
                        enabled = fechaInicio.isNotBlank() && fechaFin.isNotBlank()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Generar vista previa", fontSize = 15.sp)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { /* TODO: Exportar CSV */ },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorSecundario),
                            shape = RoundedCornerShape(8.dp),
                            enabled = mostrarVistaPrevia
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Exportar CSV", fontSize = 13.sp)
                        }
                        Button(
                            onClick = { /* TODO: Exportar PDF */ },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373)),
                            shape = RoundedCornerShape(8.dp),
                            enabled = mostrarVistaPrevia
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Exportar PDF", fontSize = 13.sp)
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
                            text = "Asistencia Diaria - $obraAsignada",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Período: $fechaInicio - $fechaFin",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // Vista previa de registros
                        VistaAsistenciaDiariaSST(
                            registros = registrosAsistencia.filter { registro ->
                                if (filtroRol == "Todos") true
                                else registro.trabajador.rol == filtroRol
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun VistaAsistenciaDiariaSST(
    registros: List<RegistroAsistencia>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 400.dp)
    ) {
        registros.take(5).forEach { registro ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = registro.trabajador.nombreCompleto(),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Entrada: ${registro.horaEntrada}", fontSize = 11.sp)
                        Text("Salida: ${registro.horaSalida}", fontSize = 11.sp)
                        Text("${registro.horasTrabajadas}h", fontSize = 11.sp, color = Color.Gray)
                    }
                    if (registro.observaciones.isNotEmpty()) {
                        Text(
                            text = "Obs: ${registro.observaciones}",
                            fontSize = 10.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
        if (registros.size > 5) {
            Text(
                text = "+ ${registros.size - 5} registros más",
                fontSize = 11.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}