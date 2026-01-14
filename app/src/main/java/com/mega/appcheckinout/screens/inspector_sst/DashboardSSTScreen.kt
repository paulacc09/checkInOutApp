package com.mega.appcheckinout.screens.inspector_sst

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.ui.theme.BotonGestion
import com.mega.appcheckinout.ui.theme.BotonAccesoRapido

/**
 * Dashboard para Inspector/a SST
 *
 * Funcionalidades principales:
 * - Control de asistencia diaria (abrir/cerrar jornada)
 * - Gestión de novedades (crear, aprobar, rechazar)
 * - Registro de personal
 * - Reportes y estadísticas de su obra asignada
 */
@Composable
fun DashboardSSTScreen(
    obraAsignada: String, // Nombre de la obra asignada al inspector
    onCerrarSesion: () -> Unit,
    onAbrirAsistencia: () -> Unit,
    onCerrarAsistencia: () -> Unit,
    onMarcarAsistencia: () -> Unit,
    onGestionNovedades: () -> Unit,
    onRegistrarPersonal: () -> Unit,
    onReportes: () -> Unit,
    onVerPersonal: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Estado de la jornada (en producción vendría de la BD)
    var jornadaAbierta by remember { mutableStateOf(false) }
    var horaApertura by remember { mutableStateOf<String?>(null) }

    // Estadísticas de ejemplo (en producción desde BD)
    val totalTrabajadores = 45
    val presentesHoy = 38
    val novedadesPendientes = 3
    val incidentesAbiertos = 1

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
            Text(
                text = "CHECKINOUT",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_info_details),
                    contentDescription = "Info",
                    tint = colorPrimario,
                    modifier = Modifier.clickable { /* Info */ }
                )
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_preferences),
                    contentDescription = "Configuración",
                    tint = colorPrimario,
                    modifier = Modifier.clickable { /* Configuración */ }
                )
                Icon(
                    painter = painterResource(android.R.drawable.ic_lock_power_off),
                    contentDescription = "Cerrar Sesión",
                    tint = Color.Red,
                    modifier = Modifier.clickable { onCerrarSesion() }
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Información de obra asignada
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorSecundario.copy(alpha = 0.3f)
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = colorPrimario,
                        modifier = Modifier.size(32.dp)
                    )
                    Column {
                        Text(
                            text = "OBRA ASIGNADA",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = obraAsignada,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorPrimario
                        )
                    }
                }
            }

            // Control de jornada
            Text(
                text = "CONTROL DE JORNADA",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (jornadaAbierta) Color(0xFF4CAF50).copy(alpha = 0.1f)
                    else Color(0xFFF44336).copy(alpha = 0.1f)
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
                        tint = if (jornadaAbierta) Color(0xFF4CAF50) else Color(0xFFF44336),
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = if (jornadaAbierta) "JORNADA ABIERTA" else "JORNADA CERRADA",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (jornadaAbierta) Color(0xFF4CAF50) else Color(0xFFF44336)
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
                                onCerrarAsistencia()
                                jornadaAbierta = false
                                horaApertura = null
                            } else {
                                onAbrirAsistencia()
                                jornadaAbierta = true
                                horaApertura = java.text.SimpleDateFormat("HH:mm",
                                    java.util.Locale.getDefault()).format(java.util.Date())
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
                            else Icons.Default.PlayArrow, // o CheckCircle, o Add
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (jornadaAbierta) "Cerrar Jornada" else "Abrir Jornada",
                            fontSize = 16.sp
                        )
                    }
                }
            }

            // Estadísticas rápidas
            Text(
                text = "RESUMEN DEL DÍA",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                EstadisticaCard(
                    titulo = "Total",
                    valor = totalTrabajadores.toString(),
                    color = colorPrimario,
                    modifier = Modifier.weight(1f)
                )
                EstadisticaCard(
                    titulo = "Presentes",
                    valor = presentesHoy.toString(),
                    color = Color(0xFF4CAF50),
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                EstadisticaCard(
                    titulo = "Novedades",
                    valor = novedadesPendientes.toString(),
                    color = Color(0xFFFF9800),
                    modifier = Modifier.weight(1f)
                )
                EstadisticaCard(
                    titulo = "Incidentes",
                    valor = incidentesAbiertos.toString(),
                    color = Color(0xFFF44336),
                    modifier = Modifier.weight(1f)
                )
            }

            // Acciones rápidas
            Text(
                text = "ACCIONES RÁPIDAS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BotonAccesoRapido(
                    texto = "Marcar Asistencia",
                    colorFondo = colorSecundario,
                    modifier = Modifier.weight(1f),
                    onClick = onMarcarAsistencia,
                    enabled = jornadaAbierta
                )

                BotonAccesoRapido(
                    texto = "Registrar Personal",
                    colorFondo = colorSecundario,
                    modifier = Modifier.weight(1f),
                    onClick = onRegistrarPersonal
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Gestión principal
            Text(
                text = "GESTIÓN",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Fila 1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    BotonGestion(
                        texto = "Gestión de Novedades",
                        colorFondo = colorSecundario,
                        modifier = Modifier.weight(1f),
                        onClick = onGestionNovedades
                    )
                    BotonGestion(
                        texto = "Ver Personal Asignado",
                        colorFondo = colorSecundario,
                        modifier = Modifier.weight(1f),
                        onClick = onVerPersonal
                    )
                }

                // Fila 2
                BotonGestion(
                    texto = "Reportes y Exportación",
                    colorFondo = colorSecundario,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onReportes
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun EstadisticaCard(
    titulo: String,
    valor: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = valor,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = titulo,
                fontSize = 14.sp,
                color = color
            )
        }
    }
}

// Extensión del BotonAccesoRapido para soportar enabled
@Composable
fun BotonAccesoRapido(
    texto: String,
    colorFondo: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(80.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorFondo,
            disabledContainerColor = Color.Gray
        ),
        shape = RoundedCornerShape(12.dp),
        enabled = enabled
    ) {
        Text(
            text = texto,
            fontSize = 14.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            lineHeight = 18.sp
        )
    }
}