package com.mega.appcheckinout.screens.administrador.reportes

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
import com.mega.appcheckinout.models.TrabajadorCompleto
import com.mega.appcheckinout.ui.theme.BotonVolver
import com.mega.appcheckinout.data.DatosEjemplo

/**
 * Pantalla de Reportes y Exportación
 *
 * Funcionalidades:
 * - Filtros: obra, rango de fechas, rol/estado
 * - Tipos de reporte: Asistencia diaria, Personal activo, Asignaciones vigentes
 * - Exportación: Vista previa, CSV, PDF
 */

data class RegistroAsistencia(
    val trabajador: TrabajadorCompleto,
    val fecha: String,
    val horaEntrada: String,
    val horaSalida: String,
    val horasTrabajadas: Double,
    val observaciones: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportesScreen(
    onVolver: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Estados de filtros
    var obraSeleccionada by remember { mutableStateOf("Todas") }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    var filtroRolEstado by remember { mutableStateOf("Todos") }
    var tipoReporte by remember { mutableStateOf("Asistencia diaria") }

    // Estados de expansión de dropdowns
    var expandirObra by remember { mutableStateOf(false) }
    var expandirFiltro by remember { mutableStateOf(false) }
    var expandirTipoReporte by remember { mutableStateOf(false) }

    // Estado de vista previa
    var mostrarVistaPrevia by remember { mutableStateOf(false) }
    var datosReporte by remember { mutableStateOf<List<Any>>(emptyList()) }

    // Datos de ejemplo
    val obras = listOf("Todas", "Mandarino - Ibagué", "Bosque robledal - Rionegro",
        "Hacienda Nakare - Villavicencio", "Pomelo - Bogotá")
    val filtrosRolEstado = listOf("Todos", "Operativo", "Inspector SST", "Encargado", "Activo", "Inactivo")
    val tiposReporte = listOf("Asistencia diaria", "Personal activo", "Asignaciones vigentes")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EFF5))
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFB8D4E3))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
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
                    text = "REPORTES Y EXPORTACIÓN",
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
        }

        // Contenido con scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Sección: Filtros del reporte
            SeccionFiltros(
                obraSeleccionada = obraSeleccionada,
                fechaInicio = fechaInicio,
                fechaFin = fechaFin,
                filtroRolEstado = filtroRolEstado,
                obras = obras,
                filtrosRolEstado = filtrosRolEstado,
                expandirObra = expandirObra,
                expandirFiltro = expandirFiltro,
                onObraChange = { obraSeleccionada = it },
                onFechaInicioChange = { fechaInicio = it },
                onFechaFinChange = { fechaFin = it },
                onFiltroChange = { filtroRolEstado = it },
                onExpandirObraChange = { expandirObra = it },
                onExpandirFiltroChange = { expandirFiltro = it },
                colorPrimario = colorPrimario
            )

            // Sección: Tipos de reporte
            SeccionTiposReporte(
                tipoReporte = tipoReporte,
                tiposReporte = tiposReporte,
                expandido = expandirTipoReporte,
                onTipoChange = { tipoReporte = it },
                onExpandidoChange = { expandirTipoReporte = it },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            // Sección: Botones de acción
            SeccionBotones(
                onGenerarVistaPrevia = {
                    // Generar datos según tipo de reporte
                    datosReporte = generarDatosReporte(tipoReporte)
                    mostrarVistaPrevia = true
                },
                onExportarCSV = {
                    // TODO: Implementar exportación CSV
                },
                onExportarPDF = {
                    // TODO: Implementar exportación PDF
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            // Vista previa del reporte
            if (mostrarVistaPrevia) {
                SeccionVistaPrevia(
                    tipoReporte = tipoReporte,
                    datos = datosReporte,
                    onCerrar = { mostrarVistaPrevia = false },
                    colorPrimario = colorPrimario
                )
            }
        }
    }
}

// ========== SECCIONES ==========

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SeccionFiltros(
    obraSeleccionada: String,
    fechaInicio: String,
    fechaFin: String,
    filtroRolEstado: String,
    obras: List<String>,
    filtrosRolEstado: List<String>,
    expandirObra: Boolean,
    expandirFiltro: Boolean,
    onObraChange: (String) -> Unit,
    onFechaInicioChange: (String) -> Unit,
    onFechaFinChange: (String) -> Unit,
    onFiltroChange: (String) -> Unit,
    onExpandirObraChange: (Boolean) -> Unit,
    onExpandirFiltroChange: (Boolean) -> Unit,
    colorPrimario: Color
) {
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

            // Selector de obra
            ExposedDropdownMenuBox(
                expanded = expandirObra,
                onExpandedChange = onExpandirObraChange
            ) {
                OutlinedTextField(
                    value = obraSeleccionada,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Selector de obra", fontSize = 14.sp) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirObra)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorPrimario
                    )
                )
                ExposedDropdownMenu(
                    expanded = expandirObra,
                    onDismissRequest = { onExpandirObraChange(false) }
                ) {
                    obras.forEach { obra ->
                        DropdownMenuItem(
                            text = { Text(obra, fontSize = 14.sp) },
                            onClick = {
                                onObraChange(obra)
                                onExpandirObraChange(false)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Rango de fechas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = fechaInicio,
                    onValueChange = onFechaInicioChange,
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
                    onValueChange = onFechaFinChange,
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

            // Filtro por rol o estado
            ExposedDropdownMenuBox(
                expanded = expandirFiltro,
                onExpandedChange = onExpandirFiltroChange
            ) {
                OutlinedTextField(
                    value = filtroRolEstado,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Filtro por rol o estado", fontSize = 14.sp) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirFiltro)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorPrimario
                    )
                )
                ExposedDropdownMenu(
                    expanded = expandirFiltro,
                    onDismissRequest = { onExpandirFiltroChange(false) }
                ) {
                    filtrosRolEstado.forEach { filtro ->
                        DropdownMenuItem(
                            text = { Text(filtro, fontSize = 14.sp) },
                            onClick = {
                                onFiltroChange(filtro)
                                onExpandirFiltroChange(false)
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SeccionTiposReporte(
    tipoReporte: String,
    tiposReporte: List<String>,
    expandido: Boolean,
    onTipoChange: (String) -> Unit,
    onExpandidoChange: (Boolean) -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "TIPOS DE REPORTE",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            ExposedDropdownMenuBox(
                expanded = expandido,
                onExpandedChange = onExpandidoChange
            ) {
                OutlinedTextField(
                    value = tipoReporte,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Seleccionar tipo", fontSize = 14.sp) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorPrimario
                    )
                )
                ExposedDropdownMenu(
                    expanded = expandido,
                    onDismissRequest = { onExpandidoChange(false) }
                ) {
                    tiposReporte.forEach { tipo ->
                        DropdownMenuItem(
                            text = {
                                Column {
                                    Text(tipo, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                    Text(
                                        text = getDescripcionTipo(tipo),
                                        fontSize = 11.sp,
                                        color = Color.Gray
                                    )
                                }
                            },
                            onClick = {
                                onTipoChange(tipo)
                                onExpandidoChange(false)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Descripción del tipo seleccionado
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = colorSecundario.copy(alpha = 0.2f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = colorPrimario,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = getDescripcionDetallada(tipoReporte),
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

@Composable
private fun SeccionBotones(
    onGenerarVistaPrevia: () -> Unit,
    onExportarCSV: () -> Unit,
    onExportarPDF: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
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
                text = "BOTONES",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Button(
                onClick = onGenerarVistaPrevia,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorPrimario),
                shape = RoundedCornerShape(8.dp)
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
                    onClick = onExportarCSV,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorSecundario),
                    shape = RoundedCornerShape(8.dp)
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
                    onClick = onExportarPDF,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373)),
                    shape = RoundedCornerShape(8.dp)
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
}

@Composable
private fun SeccionVistaPrevia(
    tipoReporte: String,
    datos: List<Any>,
    onCerrar: () -> Unit,
    colorPrimario: Color
) {
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
                IconButton(onClick = onCerrar) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        tint = Color.Gray
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = tipoReporte,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Mostrar datos según tipo
            when (tipoReporte) {
                "Asistencia diaria" -> VistaAsistenciaDiaria(datos as List<RegistroAsistencia>)
                "Personal activo" -> VistaPersonalActivo(datos as List<TrabajadorCompleto>)
                "Asignaciones vigentes" -> VistaAsignacionesVigentes(datos)
            }
        }
    }
}

@Composable
private fun VistaAsistenciaDiaria(registros: List<RegistroAsistencia>) {
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

@Composable
private fun VistaPersonalActivo(trabajadores: List<TrabajadorCompleto>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 400.dp)
    ) {
        trabajadores.take(5).forEach { trabajador ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = trabajador.nombreCompleto(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${trabajador.rol} - ${trabajador.obraAsignada}",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                    Text(
                        text = trabajador.estado,
                        fontSize = 11.sp,
                        color = if (trabajador.estado == "Activo") Color(0xFF4CAF50) else Color.Gray
                    )
                }
            }
        }
        if (trabajadores.size > 5) {
            Text(
                text = "+ ${trabajadores.size - 5} trabajadores más",
                fontSize = 11.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun VistaAsignacionesVigentes(datos: List<Any>) {
    Text(
        text = "Mostrando asignaciones vigentes...",
        fontSize = 12.sp,
        color = Color.Gray,
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

// ========== FUNCIONES AUXILIARES ==========

private fun getDescripcionTipo(tipo: String): String {
    return when (tipo) {
        "Asistencia diaria" -> "Registros de entrada y salida por día"
        "Personal activo" -> "Lista de trabajadores activos"
        "Asignaciones vigentes" -> "Trabajadores asignados a obras"
        else -> ""
    }
}

private fun getDescripcionDetallada(tipo: String): String {
    return when (tipo) {
        "Asistencia diaria" -> "Genera un reporte con los registros de entrada y salida de todos los trabajadores en el rango de fechas seleccionado."
        "Personal activo" -> "Muestra una lista completa de todo el personal activo con sus datos de contacto y asignación actual."
        "Asignaciones vigentes" -> "Lista todas las asignaciones de trabajadores a obras que están vigentes en el período seleccionado."
        else -> ""
    }
}

private fun generarDatosReporte(tipo: String): List<Any> {
    return when (tipo) {
        "Asistencia diaria" -> DatosEjemplo.getRegistrosAsistencia()
        "Personal activo" -> DatosEjemplo.getTrabajadoresActivos()
        "Asignaciones vigentes" -> DatosEjemplo.asignaciones
        else -> emptyList()
    }
}