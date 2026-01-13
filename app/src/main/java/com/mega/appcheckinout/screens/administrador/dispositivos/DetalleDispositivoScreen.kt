package com.mega.appcheckinout.screens.administrador.dispositivos

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
import com.mega.appcheckinout.models.*
import com.mega.appcheckinout.ui.theme.BotonVolver
import com.mega.appcheckinout.data.DatosEjemplo
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleDispositivoScreen(
    dispositivo: Dispositivo,
    onVolver: () -> Unit,
    onCambiarEstado: (EstadoDispositivo) -> Unit = {},
    onEliminar: () -> Unit = {},
    colorPrimario: Color,
    colorSecundario: Color
) {
    var mostrarMenuAcciones by remember { mutableStateOf(false) }
    var mostrarDialogoCambioEstado by remember { mutableStateOf(false) }
    var mostrarDialogoEliminar by remember { mutableStateOf(false) }
    var tabSeleccionada by remember { mutableStateOf(0) }

    val formatoFecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val tabs = listOf("Información", "Actividad reciente")

    // Logs de ejemplo (en producción vendrían filtrados por dispositivo)
    val logsDispositivo = remember {
        DatosEjemplo.logsDispositivos.filter { it.dispositivoId == dispositivo.id }
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
                text = "DETALLE DE DISPOSITIVO",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Box {
                IconButton(onClick = { mostrarMenuAcciones = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Más opciones",
                        tint = colorPrimario
                    )
                }

                DropdownMenu(
                    expanded = mostrarMenuAcciones,
                    onDismissRequest = { mostrarMenuAcciones = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Cambiar estado") },
                        onClick = {
                            mostrarMenuAcciones = false
                            mostrarDialogoCambioEstado = true
                        },
                        leadingIcon = { Icon(Icons.Default.Edit, null) }
                    )

                    if (dispositivo.estado != EstadoDispositivo.BLOQUEADO) {
                        DropdownMenuItem(
                            text = { Text("Bloquear dispositivo", color = Color(0xFFFF9800)) },
                            onClick = {
                                mostrarMenuAcciones = false
                                onCambiarEstado(EstadoDispositivo.BLOQUEADO)
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Lock, null, tint = Color(0xFFFF9800))
                            }
                        )
                    }

                    Divider()

                    DropdownMenuItem(
                        text = { Text("Eliminar dispositivo", color = Color.Red) },
                        onClick = {
                            mostrarMenuAcciones = false
                            mostrarDialogoEliminar = true
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Delete, null, tint = Color.Red)
                        }
                    )
                }
            }
        }

        // Card principal con información
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Icono y nombre
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Surface(
                        modifier = Modifier.size(64.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(dispositivo.estado.color).copy(alpha = 0.2f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = when(dispositivo.tipo) {
                                    TipoDispositivo.TABLET -> Icons.Default.Phone
                                    TipoDispositivo.TELEFONO -> Icons.Default.Phone
                                    TipoDispositivo.BIOMETRICO -> Icons.Default.Lock
                                },
                                contentDescription = null,
                                tint = Color(dispositivo.estado.color),
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = dispositivo.nombre,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = dispositivo.tipo.displayName,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(dispositivo.estado.color)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = dispositivo.estado.displayName,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Tabs
        TabRow(
            selectedTabIndex = tabSeleccionada,
            containerColor = Color.White,
            contentColor = colorPrimario
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = tabSeleccionada == index,
                    onClick = { tabSeleccionada = index },
                    text = { Text(title) }
                )
            }
        }

        // Contenido según tab
        when (tabSeleccionada) {
            0 -> TabInformacionDispositivo(
                dispositivo = dispositivo,
                formatoFecha = formatoFecha,
                colorPrimario = colorPrimario
            )
            1 -> TabActividadDispositivo(
                logs = logsDispositivo,
                formatoFecha = formatoFecha,
                colorPrimario = colorPrimario
            )
        }
    }

    // Diálogo cambio de estado
    if (mostrarDialogoCambioEstado) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoCambioEstado = false },
            title = { Text("Cambiar Estado") },
            text = {
                Column {
                    EstadoDispositivo.values().forEach { estado ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = dispositivo.estado == estado,
                                onClick = {
                                    onCambiarEstado(estado)
                                    mostrarDialogoCambioEstado = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = estado.displayName,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = when(estado) {
                                        EstadoDispositivo.ACTIVO -> "El dispositivo funciona normalmente"
                                        EstadoDispositivo.INACTIVO -> "Temporalmente desactivado"
                                        EstadoDispositivo.BLOQUEADO -> "Bloqueado por seguridad"
                                    },
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { mostrarDialogoCambioEstado = false }) {
                    Text("Cerrar")
                }
            }
        )
    }

    // Diálogo eliminar
    if (mostrarDialogoEliminar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEliminar = false },
            icon = { Icon(Icons.Default.Warning, null, tint = Color.Red) },
            title = { Text("Eliminar Dispositivo") },
            text = {
                Column {
                    Text("¿Está seguro de eliminar este dispositivo?")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = dispositivo.nombre,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Esta acción no se puede deshacer. El dispositivo ya no podrá registrar asistencias.",
                        fontSize = 12.sp,
                        color = Color.Red
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onEliminar()
                        mostrarDialogoEliminar = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoEliminar = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun TabInformacionDispositivo(
    dispositivo: Dispositivo,
    formatoFecha: SimpleDateFormat,
    colorPrimario: Color
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Obra asignada
        SeccionInfo(titulo = "Ubicación", colorPrimario = colorPrimario) {
            InfoRow(
                label = "Obra",
                value = dispositivo.obraNombre,
                icon = Icons.Default.Home
            )
        }

        // Responsable
        SeccionInfo(titulo = "Responsable", colorPrimario = colorPrimario) {
            InfoRow(
                label = "Persona encargada",
                value = dispositivo.responsableNombre,
                icon = Icons.Default.Person
            )
        }

        // Métodos de marcaje
        SeccionInfo(titulo = "Métodos de marcaje habilitados", colorPrimario = colorPrimario) {
            dispositivo.metodosHabilitados.forEach { metodo ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = metodo.displayName,
                        fontSize = 14.sp
                    )
                }
            }
        }

        // Fechas
        SeccionInfo(titulo = "Información de registro", colorPrimario = colorPrimario) {
            InfoRow(
                label = "Fecha de registro",
                value = formatoFecha.format(Date(dispositivo.fechaRegistro)),
                icon = Icons.Default.DateRange
            )

            dispositivo.ultimoUso?.let { ultimoUso ->
                Spacer(modifier = Modifier.height(12.dp))
                InfoRow(
                    label = "Último uso",
                    value = formatoFecha.format(Date(ultimoUso)),
                    icon = Icons.Default.Info
                )
            }
        }

        // Motivo de bloqueo (si aplica)
        if (dispositivo.estado == EstadoDispositivo.BLOQUEADO && !dispositivo.motivoBloqueo.isNullOrEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF44336).copy(alpha = 0.1f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = Color(0xFFF44336)
                        )
                        Text(
                            text = "MOTIVO DE BLOQUEO",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF44336)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = dispositivo.motivoBloqueo,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun TabActividadDispositivo(
    logs: List<LogDispositivo>,
    formatoFecha: SimpleDateFormat,
    colorPrimario: Color
) {
    if (logs.isEmpty()) {
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
                    text = "Sin actividad registrada",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(logs) { log ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = log.accion,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = formatoFecha.format(Date(log.timestamp)),
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Usuario: ${log.usuarioNombre}",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                        log.detalles?.let { detalles ->
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = detalles,
                                fontSize = 12.sp,
                                color = colorPrimario
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SeccionInfo(
    titulo: String,
    colorPrimario: Color,
    content: @Composable ColumnScope.() -> Unit
) {
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
                text = titulo.uppercase(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            content()
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color.Gray
        )
        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}