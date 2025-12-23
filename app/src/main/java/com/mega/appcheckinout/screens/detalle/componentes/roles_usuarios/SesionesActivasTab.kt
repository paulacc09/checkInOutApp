package com.mega.appcheckinout.screens.detalle.componentes.roles_usuarios

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
import androidx.compose.ui.unit.dp
import com.mega.appcheckinout.models.RolUsuario
import com.mega.appcheckinout.models.SesionActiva
import java.text.SimpleDateFormat
import java.util.*
import com.mega.appcheckinout.data.DatosEjemplo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SesionesActivasTab() {
    var filtroRol by remember { mutableStateOf<RolUsuario?>(null) }
    var filtroDispositivo by remember { mutableStateOf<String?>(null) }
    var mostrarDialogoConfirmacion by remember { mutableStateOf<SesionActiva?>(null) }

    // Lista de sesiones activas (en producción vendría del backend)
    val sesionesActivas = remember {
        mutableStateListOf<SesionActiva>().apply {
            addAll(DatosEjemplo.sesionesActivas)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Encabezado con contador
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Sesiones Activas",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${sesionesActivas.size} usuarios conectados",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Botón actualizar
            IconButton(
                onClick = { /* TODO: Recargar sesiones */ }
            ) {
                Icon(Icons.Default.Refresh, "Actualizar")
            }
        }

        // Filtros rápidos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Filtro por rol
            FilterChip(
                selected = filtroRol != null,
                onClick = { /* TODO: Mostrar diálogo selector de rol */ },
                label = { Text(filtroRol?.displayName ?: "Todos los roles") },
                leadingIcon = if (filtroRol != null) {
                    { Icon(Icons.Default.Close, null) }
                } else null
            )

            // Filtro por dispositivo
            FilterChip(
                selected = filtroDispositivo != null,
                onClick = { /* TODO: Mostrar diálogo selector de dispositivo */ },
                label = { Text(filtroDispositivo ?: "Todos los dispositivos") },
                leadingIcon = if (filtroDispositivo != null) {
                    { Icon(Icons.Default.Close, null) }
                } else null
            )
        }

        // Lista de sesiones
        if (sesionesActivas.isEmpty()) {
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
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "No hay sesiones activas",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(sesionesActivas) { sesion ->
                    TarjetaSesionActiva(
                        sesion = sesion,
                        onCerrarSesion = { mostrarDialogoConfirmacion = it },
                        onVerHistorial = { /* TODO */ }
                    )
                }
            }
        }
    }

    // Diálogo de confirmación para cerrar sesión
    mostrarDialogoConfirmacion?.let { sesion ->
        AlertDialog(
            onDismissRequest = { mostrarDialogoConfirmacion = null },
            icon = { Icon(Icons.Default.Warning, null, tint = MaterialTheme.colorScheme.error) },
            title = { Text("Cerrar Sesión Remota") },
            text = {
                Column {
                    Text("¿Está seguro de cerrar la sesión de:")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = sesion.nombreUsuario,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Dispositivo: ${sesion.dispositivo}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "El usuario deberá volver a iniciar sesión.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        sesionesActivas.remove(sesion)
                        mostrarDialogoConfirmacion = null
                        // TODO: Llamar al backend para cerrar sesión
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Cerrar Sesión")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoConfirmacion = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun TarjetaSesionActiva(
    sesion: SesionActiva,
    onCerrarSesion: (SesionActiva) -> Unit,
    onVerHistorial: (SesionActiva) -> Unit
) {
    val formatoHora = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val tiempoConectado = (System.currentTimeMillis() - sesion.horaInicio) / 60000 // minutos
    val tiempoInactivo = (System.currentTimeMillis() - sesion.ultimaActividad) / 60000

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (tiempoInactivo > 30) {
                MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Encabezado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Indicador de estado
                    Surface(
                        modifier = Modifier.size(12.dp),
                        shape = MaterialTheme.shapes.small,
                        color = if (tiempoInactivo < 5) Color(0xFF4CAF50) else Color(0xFFFF9800)
                    ) {}

                    Column {
                        Text(
                            text = sesion.nombreUsuario,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Badge(
                            containerColor = when (sesion.rol) {
                                RolUsuario.ADMINISTRADOR -> Color(0xFF6200EE)
                                RolUsuario.ENCARGADO -> Color(0xFF03DAC5)
                                RolUsuario.INSPECTOR_SST -> Color(0xFFFF9800)
                                RolUsuario.OPERATIVO -> Color(0xFF9E9E9E)
                            }
                        ) {
                            Text(sesion.rol.displayName)
                        }
                    }
                }

                IconButton(
                    onClick = { onCerrarSesion(sesion) },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(Icons.Default.Close, "Cerrar sesión")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Información de la sesión
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    InfoSesion(
                        icono = Icons.Default.Phone,
                        etiqueta = "Dispositivo",
                        valor = sesion.dispositivo
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoSesion(
                        icono = Icons.Default.Home,
                        etiqueta = "Ubicación",
                        valor = sesion.ubicacion
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoSesion(
                        icono = Icons.Default.Info,
                        etiqueta = "IP",
                        valor = sesion.ip
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    InfoSesion(
                        icono = Icons.Default.Info,
                        etiqueta = "Inicio de sesión",
                        valor = formatoHora.format(Date(sesion.horaInicio))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoSesion(
                        icono = Icons.Default.Info,
                        etiqueta = "Tiempo conectado",
                        valor = "$tiempoConectado min"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoSesion(
                        icono = Icons.Default.Info,
                        etiqueta = "Última actividad",
                        valor = "Hace $tiempoInactivo min",
                        color = if (tiempoInactivo > 30) MaterialTheme.colorScheme.error else null
                    )
                }
            }

            // Botón ver historial
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(
                onClick = { onVerHistorial(sesion) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.Info, null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Ver historial completo")
            }
        }
    }
}

@Composable
fun InfoSesion(
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    etiqueta: String,
    valor: String,
    color: Color? = null
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = color ?: MaterialTheme.colorScheme.onSurfaceVariant
        )
        Column {
            Text(
                text = etiqueta,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = valor,
                style = MaterialTheme.typography.bodySmall,
                color = color ?: MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
        }
    }
}