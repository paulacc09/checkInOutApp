// screens/inspector_sst/AsistenciaDiariaScreen.kt
package com.mega.appcheckinout.screens.inspector_sst

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
import com.mega.appcheckinout.screens.detalle.componentes.sst.TarjetaAsistenciaActiva
import com.mega.appcheckinout.screens.detalle.componentes.sst.TarjetaResumenDiario
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AsistenciaDiariaScreen(
    obraAsignada: String,
    onVolver: () -> Unit,
    onVerPerfil: (RegistroAsistenciaTemp) -> Unit = {},
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Estados
    var jornadaAbierta by remember { mutableStateOf(false) }
    var horaApertura by remember { mutableStateOf<String?>(null) }
    var mostrarDialogoAdvertencia by remember { mutableStateOf(false) }
    var trabajadoresSinSalida by remember { mutableStateOf(0) }

    val formatoHora = SimpleDateFormat("HH:mm", Locale.getDefault())

    // Trabajadores de la obra
    val trabajadoresObra = remember {
        DatosEjemplo.getTrabajadoresPorObra("1")
    }

    // Asistencias del día (estado simulado)
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

    fun intentarCerrarJornada() {
        val pendientesSalida = asistenciasHoy.filter {
            it.estado == EstadoMarcaje.INGRESADO
        }

        if (pendientesSalida.isNotEmpty()) {
            trabajadoresSinSalida = pendientesSalida.size
            mostrarDialogoAdvertencia = true
        } else {
            jornadaAbierta = false
            horaApertura = null
        }
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
            // Estado de jornada
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (jornadaAbierta)
                        Color(0xFF4CAF50).copy(alpha = 0.1f)
                    else
                        Color(0xFFF44336).copy(alpha = 0.1f)
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = if (jornadaAbierta) Icons.Default.CheckCircle
                        else Icons.Default.Lock,
                        contentDescription = null,
                        tint = if (jornadaAbierta) Color(0xFF4CAF50)
                        else Color(0xFFF44336),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (jornadaAbierta) "JORNADA ABIERTA"
                        else "JORNADA CERRADA",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (jornadaAbierta) Color(0xFF4CAF50)
                        else Color(0xFFF44336)
                    )
                    if (jornadaAbierta && horaApertura != null) {
                        Text(
                            text = "Abierta desde: $horaApertura",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (jornadaAbierta) {
                                intentarCerrarJornada()
                            } else {
                                jornadaAbierta = true
                                horaApertura = formatoHora.format(Date())
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (jornadaAbierta) Color(0xFFF44336)
                            else Color(0xFF4CAF50)
                        ),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Icon(
                            imageVector = if (jornadaAbierta) Icons.Default.Lock
                            else Icons.Default.PlayArrow,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (jornadaAbierta) "Cerrar Jornada"
                            else "Abrir Jornada",
                            fontSize = 16.sp
                        )
                    }
                }
            }

            // Resumen del día
            TarjetaResumenDiario(
                totalTrabajadores = trabajadoresObra.size,
                presentes = asistenciasHoy.count {
                    it.estado != EstadoMarcaje.PENDIENTE
                },
                ausentes = asistenciasHoy.count {
                    it.estado == EstadoMarcaje.PENDIENTE
                },
                novedadesPendientes = DatosEjemplo.novedades.count {
                    it.estado == com.mega.appcheckinout.models.EstadoNovedad.PENDIENTE
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            // Lista de asistencias
            Text(
                text = "ASISTENCIAS DE HOY",
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
                        onVerPerfil = { onVerPerfil(registro) },
                        colorPrimario = colorPrimario
                    )
                }
            }
        }
    }

    // Diálogo de advertencia al cerrar jornada
    if (mostrarDialogoAdvertencia) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoAdvertencia = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = Color(0xFFFF9800)
                )
            },
            title = { Text("Advertencia") },
            text = {
                Column {
                    Text("Hay $trabajadoresSinSalida trabajador(es) sin marcar salida.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "¿Desea cerrar la jornada de todos modos?",
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        jornadaAbierta = false
                        horaApertura = null
                        mostrarDialogoAdvertencia = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF44336)
                    )
                ) {
                    Text("Cerrar de todos modos")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoAdvertencia = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}