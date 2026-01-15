// screens/encargado/asistencia/AsistenciaDiariaEncargadoScreen.kt
package com.mega.appcheckinout.screens.encargado.asistencia

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
import com.mega.appcheckinout.screens.inspector_sst.RegistroAsistenciaTemp
import com.mega.appcheckinout.screens.inspector_sst.EstadoMarcaje
import com.mega.appcheckinout.screens.detalle.componentes.sst.TarjetaAsistenciaActiva
import com.mega.appcheckinout.screens.detalle.componentes.encargado.AlertaPermisoLimitado
import com.mega.appcheckinout.screens.detalle.componentes.encargado.TipoAlerta

/**
 * Pantalla de Asistencia Diaria para Encargado
 *
 * RESTRICCIÓN: Solo puede acceder si NO hay Inspector/a SST presente
 * Permite consultar asistencias del día de su obra
 */
@Composable
fun AsistenciaDiariaEncargadoScreen(
    obraAsignada: String,
    inspectorPresente: Boolean = true,
    onVolver: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    val trabajadoresObra = remember {
        DatosEjemplo.getTrabajadoresPorObra("1")
    }

    // Asistencias del día (simuladas)
    var asistenciasHoy by remember {
        mutableStateOf(
            trabajadoresObra.map { trabajador ->
                RegistroAsistenciaTemp(
                    trabajadorId = trabajador.id,
                    nombre = trabajador.nombreCompleto(),
                    numeroDocumento = trabajador.numeroDocumento,
                    horaIngreso = if (Math.random() > 0.3) "07:00" else null,
                    horaSalida = if (Math.random() > 0.7) "17:00" else null,
                    estado = when {
                        Math.random() > 0.7 -> EstadoMarcaje.COMPLETADO
                        Math.random() > 0.3 -> EstadoMarcaje.INGRESADO
                        else -> EstadoMarcaje.PENDIENTE
                    }
                )
            }
        )
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
                text = "ASISTENCIA DIARIA",
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
            // Alerta de permiso limitado
            if (inspectorPresente) {
                AlertaPermisoLimitado(
                    titulo = "Acceso Restringido",
                    mensaje = "La gestión de asistencia está a cargo del Inspector/a SST. Solo puedes consultar los registros.",
                    tipo = TipoAlerta.WARNING
                )
            } else {
                AlertaPermisoLimitado(
                    titulo = "Modo Temporal",
                    mensaje = "Inspector/a SST ausente. Puedes gestionar asistencia temporalmente.",
                    tipo = TipoAlerta.INFO
                )
            }

            // Resumen del día
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "RESUMEN DEL DÍA",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        EstadisticaMarcaje(
                            label = "Total",
                            count = asistenciasHoy.size,
                            color = colorPrimario
                        )
                        EstadisticaMarcaje(
                            label = "Presentes",
                            count = asistenciasHoy.count {
                                it.estado != EstadoMarcaje.PENDIENTE
                            },
                            color = Color(0xFF4CAF50)
                        )
                        EstadisticaMarcaje(
                            label = "Ausentes",
                            count = asistenciasHoy.count {
                                it.estado == EstadoMarcaje.PENDIENTE
                            },
                            color = Color(0xFFF44336)
                        )
                    }
                }
            }

            // Lista de asistencias
            Text(
                text = "REGISTROS DE HOY",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(asistenciasHoy) { registro ->
                    TarjetaAsistenciaActiva(
                        registro = registro,
                        onVerPerfil = { /* Ver perfil */ },
                        colorPrimario = colorPrimario
                    )
                }
            }
        }
    }
}

@Composable
private fun EstadisticaMarcaje(
    label: String,
    count: Int,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count.toString(),
            fontSize = 28.sp,
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