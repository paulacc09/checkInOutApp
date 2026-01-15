// screens/encargado/novedades/ConsultarNovedadesScreen.kt
package com.mega.appcheckinout.screens.encargado.novedades

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.mega.appcheckinout.models.*
import com.mega.appcheckinout.screens.detalle.componentes.sst.TarjetaNovedadSST
import com.mega.appcheckinout.screens.detalle.componentes.encargado.AlertaPermisoLimitado
import com.mega.appcheckinout.screens.detalle.componentes.encargado.TipoAlerta

/**
 * Pantalla de Consulta de Novedades para Encargado
 *
 * PERMISOS:
 * - ✓ Puede consultar novedades de su obra
 * - ✓ Puede crear nuevas novedades
 * - ✗ NO puede aprobar/rechazar novedades (solo Inspector/Administrador)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultarNovedadesScreen(
    obraAsignada: String,
    onVolver: () -> Unit,
    onVerDetalle: (Novedad) -> Unit,
    onCrearNovedad: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    var filtroEstado by remember { mutableStateOf("Todas") }
    var expandirFiltro by remember { mutableStateOf(false) }

    val estadosFiltro = listOf("Todas", "Pendientes", "Aprobadas", "Rechazadas")

    // Novedades de la obra (solo de la obra asignada)
    val novedadesObra = remember {
        DatosEjemplo.novedades.filter { it.obraId == "1" }
    }

    val novedadesFiltradas = when(filtroEstado) {
        "Pendientes" -> novedadesObra.filter { it.estado == EstadoNovedad.PENDIENTE }
        "Aprobadas" -> novedadesObra.filter { it.estado == EstadoNovedad.APROBADO }
        "Rechazadas" -> novedadesObra.filter { it.estado == EstadoNovedad.RECHAZADO }
        else -> novedadesObra
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
                text = "CONSULTAR NOVEDADES",
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
            // Alerta de permisos
            AlertaPermisoLimitado(
                titulo = "Consulta de Novedades",
                mensaje = "Puedes consultar y crear novedades de tu obra. La aprobación es realizada por Inspector/a SST o Administrador.",
                tipo = TipoAlerta.INFO
            )

            // Filtros y botón crear
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Filtro de estado
                ExposedDropdownMenuBox(
                    expanded = expandirFiltro,
                    onExpandedChange = { expandirFiltro = it },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = filtroEstado,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Filtrar", fontSize = 14.sp) },
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
                        onDismissRequest = { expandirFiltro = false }
                    ) {
                        estadosFiltro.forEach { estado ->
                            DropdownMenuItem(
                                text = { Text(estado) },
                                onClick = {
                                    filtroEstado = estado
                                    expandirFiltro = false
                                }
                            )
                        }
                    }
                }

                // Botón crear novedad
                Button(
                    onClick = onCrearNovedad,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorPrimario
                    )
                ) {
                    Icon(Icons.Default.Add, null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Crear", fontSize = 14.sp)
                }
            }

            // Resumen estadístico
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    EstadisticaNovedad(
                        label = "Total",
                        count = novedadesObra.size,
                        color = colorPrimario
                    )
                    EstadisticaNovedad(
                        label = "Pendientes",
                        count = novedadesObra.count { it.estado == EstadoNovedad.PENDIENTE },
                        color = Color(0xFFFF9800)
                    )
                    EstadisticaNovedad(
                        label = "Aprobadas",
                        count = novedadesObra.count { it.estado == EstadoNovedad.APROBADO },
                        color = Color(0xFF4CAF50)
                    )
                }
            }

            // Lista de novedades
            Text(
                text = "${novedadesFiltradas.size} novedad(es)",
                fontSize = 14.sp,
                color = Color.Gray
            )

            if (novedadesFiltradas.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )
                        Text(
                            text = "No hay novedades",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                        Text(
                            text = "Crea una nueva novedad con el botón +",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(novedadesFiltradas) { novedad ->
                        TarjetaNovedadSST(
                            novedad = novedad,
                            onClick = { onVerDetalle(novedad) },
                            colorPrimario = colorPrimario
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EstadisticaNovedad(
    label: String,
    count: Int,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count.toString(),
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