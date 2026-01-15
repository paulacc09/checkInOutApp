package com.mega.appcheckinout.screens.encargado

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
 * Dashboard para Encargado de Obra
 *
 * Funcionalidades principales:
 * - Vista general de su obra asignada
 * - Acceso rápido a gestión de personal de su obra
 * - Control de asistencia (solo en ausencia de Inspector SST)
 * - Consulta de novedades
 * - Reportes limitados a su obra
 *
 * PERMISOS LIMITADOS:
 * - Solo puede ver/gestionar datos de SU obra asignada
 * - No puede crear/editar trabajadores
 * - No puede aprobar novedades
 * - Puede marcar asistencia SOLO en ausencia de Inspector SST
 */
@Composable
fun DashboardEncargadoScreen(
    obraAsignada: String, // Nombre de la obra asignada al encargado
    nombreEncargado: String = "Encargado", // Nombre del usuario logueado
    onCerrarSesion: () -> Unit,
    onVerPersonal: () -> Unit,
    onAsistenciaDiaria: () -> Unit,
    onConsultarNovedades: () -> Unit,
    onReportes: () -> Unit,
    onDetalleObra: () -> Unit,
    onSolicitarTraspaso: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Estadísticas de ejemplo (en producción desde BD)
    val totalTrabajadores = 32
    val presentesHoy = 28
    val ausentesHoy = 4
    val novedadesPendientes = 2

    // Estado para verificar si hay Inspector presente
    var inspectorPresente by remember { mutableStateOf(true) }

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
            Column {
                Text(
                    text = "CHECKINOUT",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorPrimario
                )
                Text(
                    text = "Encargado",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }

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
            // Bienvenida personalizada
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Surface(
                        modifier = Modifier.size(64.dp),
                        shape = RoundedCornerShape(32.dp),
                        color = colorPrimario.copy(alpha = 0.2f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = colorPrimario,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "¡Bienvenido!",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = nombreEncargado,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorPrimario
                        )
                        Text(
                            text = "Encargado de obra",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Información de obra asignada
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clickable(onClick = onDetalleObra),
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
                    Column(modifier = Modifier.weight(1f)) {
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
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Ver detalles",
                        tint = colorPrimario
                    )
                }
            }

            // Alerta si no hay inspector presente
            if (!inspectorPresente) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFF9800).copy(alpha = 0.1f)
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
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = Color(0xFFFF9800),
                            modifier = Modifier.size(24.dp)
                        )
                        Column {
                            Text(
                                text = "Inspector SST ausente",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF9800)
                            )
                            Text(
                                text = "Puede gestionar asistencia temporalmente",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            // Estadísticas del día
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
                    titulo = "Ausentes",
                    valor = ausentesHoy.toString(),
                    color = Color(0xFFF44336),
                    modifier = Modifier.weight(1f)
                )
                EstadisticaCard(
                    titulo = "Novedades",
                    valor = novedadesPendientes.toString(),
                    color = Color(0xFFFF9800),
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
                    texto = "Ver Personal",
                    colorFondo = colorSecundario,
                    modifier = Modifier.weight(1f),
                    onClick = onVerPersonal
                )

                BotonAccesoRapido(
                    texto = "Asistencia Diaria",
                    colorFondo = if (inspectorPresente)
                        Color.Gray
                    else
                        colorSecundario,
                    modifier = Modifier.weight(1f),
                    onClick = onAsistenciaDiaria,
                    enabled = !inspectorPresente
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
                        texto = "Consultar Novedades",
                        colorFondo = colorSecundario,
                        modifier = Modifier.weight(1f),
                        onClick = onConsultarNovedades
                    )
                    BotonGestion(
                        texto = "Solicitar Traspaso",
                        colorFondo = colorSecundario,
                        modifier = Modifier.weight(1f),
                        onClick = onSolicitarTraspaso
                    )
                }

                // Fila 2
                BotonGestion(
                    texto = "Reportes de mi Obra",
                    colorFondo = colorSecundario,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onReportes
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nota informativa de permisos
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2196F3).copy(alpha = 0.1f)
                ),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color(0xFF2196F3),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Tus permisos están limitados a tu obra asignada. Para acciones administrativas contacta al Administrador.",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        lineHeight = 14.sp
                    )
                }
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