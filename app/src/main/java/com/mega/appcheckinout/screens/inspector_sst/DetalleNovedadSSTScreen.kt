// screens/inspector_sst/DetalleNovedadSSTScreen.kt
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.models.*
import com.mega.appcheckinout.ui.theme.BotonVolver
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleNovedadSSTScreen(
    novedad: Novedad,
    onVolver: () -> Unit,
    onAprobar: () -> Unit,
    onRechazar: (String) -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    var mostrarDialogoAprobar by remember { mutableStateOf(false) }
    var mostrarDialogoRechazar by remember { mutableStateOf(false) }
    var motivoRechazo by remember { mutableStateOf("") }

    val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

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
                text = "DETALLE DE NOVEDAD",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        // Contenido
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card principal con estado
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
                    // Estado
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = when(novedad.estado) {
                                EstadoNovedad.PENDIENTE -> Color(0xFFFF9800)
                                EstadoNovedad.APROBADO -> Color(0xFF4CAF50)
                                EstadoNovedad.RECHAZADO -> Color(0xFFF44336)
                                EstadoNovedad.INFORMACION_ADICIONAL -> Color(0xFF2196F3)
                            }
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = novedad.estado.displayName.uppercase(),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Tipo de novedad
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = when(novedad.tipoNovedad) {
                                TipoNovedad.INCAPACIDAD_ARL,
                                TipoNovedad.INCAPACIDAD_EPS -> Icons.Default.Warning
                                TipoNovedad.PERMISO -> Icons.Default.Check
                                TipoNovedad.LICENCIA -> Icons.Default.DateRange
                                else -> Icons.Default.Info
                            },
                            contentDescription = null,
                            tint = colorPrimario,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = novedad.tipoNovedad.displayName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorPrimario
                        )
                    }
                }
            }

            // Información del trabajador
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
                        text = "TRABAJADOR",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Text(
                        text = novedad.trabajadorNombre,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "ID: ${novedad.trabajadorId}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            // Fechas
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
                        text = "PERÍODO",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Fecha inicio",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = novedad.fechaInicio,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Column {
                            Text(
                                text = "Fecha fin",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = novedad.fechaFin,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Descripción
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
                        text = "DESCRIPCIÓN",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Text(
                        text = novedad.descripcion,
                        fontSize = 15.sp,
                        lineHeight = 22.sp
                    )
                }
            }

            // Evidencias
            if (novedad.evidencias.isNotEmpty()) {
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
                            text = "EVIDENCIAS",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorPrimario,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        novedad.evidencias.forEach { evidencia ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable { /* TODO: Abrir evidencia */ },
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFF5F5F5)
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = if (evidencia.tipo == TipoEvidencia.PDF)
                                                Icons.Default.Info
                                            else
                                                Icons.Default.Add,
                                            contentDescription = null,
                                            tint = colorPrimario
                                        )
                                        Column {
                                            Text(
                                                text = evidencia.nombre,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                            Text(
                                                text = evidencia.tipo.name,
                                                fontSize = 12.sp,
                                                color = Color.Gray
                                            )
                                        }
                                    }
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowRight,
                                        contentDescription = "Ver",
                                        tint = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Información de auditoría
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
                        text = "INFORMACIÓN DE REGISTRO",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Column {
                        Text(
                            text = "Creado por",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = novedad.creadoPor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Column {
                        Text(
                            text = "Fecha de creación",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = formatoFecha.format(Date(novedad.fechaCreacion)),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    novedad.revisadoPor?.let { revisor ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Column {
                            Text(
                                text = "Revisado por",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = revisor,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    novedad.fechaRevision?.let { fecha ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Column {
                            Text(
                                text = "Fecha de revisión",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = formatoFecha.format(Date(fecha)),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    if (novedad.estado == EstadoNovedad.RECHAZADO &&
                        !novedad.motivoRechazo.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Motivo de rechazo",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF44336)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = novedad.motivoRechazo,
                            fontSize = 14.sp,
                            color = Color(0xFFF44336)
                        )
                    }
                }
            }

            // Botones de acción (solo si está pendiente)
            if (novedad.estado == EstadoNovedad.PENDIENTE) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { mostrarDialogoRechazar = true },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFF44336)
                        )
                    ) {
                        Icon(Icons.Default.Close, null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Rechazar")
                    }
                    Button(
                        onClick = { mostrarDialogoAprobar = true },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50)
                        )
                    ) {
                        Icon(Icons.Default.Check, null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Aprobar")
                    }
                }
            }
        }
    }

    // Diálogo de aprobación
    if (mostrarDialogoAprobar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoAprobar = false },
            icon = { Icon(Icons.Default.Check, null, tint = Color(0xFF4CAF50)) },
            title = { Text("Aprobar Novedad") },
            text = {
                Column {
                    Text("¿Está seguro de aprobar esta novedad?")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• Trabajador: ${novedad.trabajadorNombre}",
                        fontSize = 14.sp
                    )
                    Text(
                        text = "• Tipo: ${novedad.tipoNovedad.displayName}",
                        fontSize = 14.sp
                    )
                    Text(
                        text = "• Período: ${novedad.fechaInicio} - ${novedad.fechaFin}",
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Las fechas serán marcadas como justificadas en los reportes.",
                        fontSize = 12.sp,
                        color = Color(0xFF4CAF50)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onAprobar()
                        mostrarDialogoAprobar = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    Text("Aprobar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoAprobar = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Diálogo de rechazo
    if (mostrarDialogoRechazar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoRechazar = false },
            icon = { Icon(Icons.Default.Close, null, tint = Color(0xFFF44336)) },
            title = { Text("Rechazar Novedad") },
            text = {
                Column {
                    Text("¿Está seguro de rechazar esta novedad?")
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = motivoRechazo,
                        onValueChange = { motivoRechazo = it },
                        label = { Text("Motivo del rechazo *") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        placeholder = {
                            Text("Ej: Documentación incompleta, firma faltante...")
                        }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (motivoRechazo.isNotBlank()) {
                            onRechazar(motivoRechazo)
                            mostrarDialogoRechazar = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF44336)
                    ),
                    enabled = motivoRechazo.isNotBlank()
                ) {
                    Text("Rechazar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoRechazar = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}