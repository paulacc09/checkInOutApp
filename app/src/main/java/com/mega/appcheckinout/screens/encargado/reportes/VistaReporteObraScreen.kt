// screens/encargado/reportes/VistaReporteObraScreen.kt
package com.mega.appcheckinout.screens.encargado.reportes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.mega.appcheckinout.screens.administrador.reportes.RegistroAsistencia

/**
 * Pantalla de Vista Previa de Reporte para Encargado
 *
 * Muestra una vista previa detallada del reporte antes de exportar
 * Incluye tabla con datos de asistencia y opciones de exportación
 * Solo datos de la obra asignada al encargado
 */
@Composable
fun VistaReporteObraScreen(
    obraAsignada: String,
    tipoReporte: String,
    fechaInicio: String,
    fechaFin: String,
    filtroRol: String = "Todos",
    onVolver: () -> Unit,
    onExportarCSV: () -> Unit,
    onExportarPDF: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Datos de ejemplo (en producción vendrían de BD)
    val registrosAsistencia = remember {
        DatosEjemplo.getRegistrosAsistencia().filter { registro ->
            if (filtroRol == "Todos") true
            else registro.trabajador.rol == filtroRol
        }
    }

    // Estadísticas calculadas
    val totalRegistros = registrosAsistencia.size
    val horasTotales = registrosAsistencia.sumOf { it.horasTrabajadas }
    val promedioHoras = if (totalRegistros > 0) horasTotales / totalRegistros else 0.0
    val registrosCompletos = registrosAsistencia.count {
        it.horaEntrada.isNotEmpty() && it.horaSalida.isNotEmpty()
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
                text = "VISTA PREVIA",
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Encabezado del reporte
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = tipoReporte.uppercase(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = obraAsignada,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Período: $fechaInicio - $fechaFin",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    if (filtroRol != "Todos") {
                        Text(
                            text = "Rol: $filtroRol",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    Text(
                        text = "Generado: ${java.text.SimpleDateFormat("dd/MM/yyyy HH:mm",
                            java.util.Locale.getDefault()).format(java.util.Date())}",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Estadísticas resumen
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "RESUMEN",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        EstadisticaReporte(
                            label = "Registros",
                            valor = totalRegistros.toString(),
                            color = colorPrimario
                        )
                        EstadisticaReporte(
                            label = "Completos",
                            valor = registrosCompletos.toString(),
                            color = Color(0xFF4CAF50)
                        )
                        EstadisticaReporte(
                            label = "Promedio hrs",
                            valor = String.format("%.1f", promedioHoras),
                            color = Color(0xFF2196F3)
                        )
                    }
                }
            }

            // Botones de exportación
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onExportarCSV,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorSecundario
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Share, null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Exportar CSV", fontSize = 14.sp)
                }

                Button(
                    onClick = onExportarPDF,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE57373)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Share, null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Exportar PDF", fontSize = 14.sp)
                }
            }

            // Tabla de datos
            Text(
                text = "DETALLE DE ASISTENCIAS",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario
            )

            // Encabezado de tabla
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = colorPrimario.copy(alpha = 0.1f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Trabajador",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario,
                        modifier = Modifier.weight(2f)
                    )
                    Text(
                        "Entrada",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "Salida",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "Horas",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario,
                        modifier = Modifier.weight(0.8f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Filas de datos
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(registrosAsistencia) { registro ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(2f)) {
                                Text(
                                    text = registro.trabajador.nombreCompleto(),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = registro.trabajador.rol,
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                            Text(
                                text = registro.horaEntrada,
                                fontSize = 12.sp,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = registro.horaSalida,
                                fontSize = 12.sp,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = String.format("%.1f", registro.horasTrabajadas),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (registro.horasTrabajadas >= 8)
                                    Color(0xFF4CAF50)
                                else
                                    Color(0xFFFF9800),
                                modifier = Modifier.weight(0.8f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // Pie de tabla
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = colorPrimario.copy(alpha = 0.1f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "TOTAL",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorPrimario,
                                modifier = Modifier.weight(2f)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = String.format("%.1f", horasTotales),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorPrimario,
                                modifier = Modifier.weight(0.8f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EstadisticaReporte(
    label: String,
    valor: String,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = valor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}