package com.mega.appcheckinout.screens.administrador.roles_usuarios

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
import com.mega.appcheckinout.models.EventoAuditoria
import com.mega.appcheckinout.models.RolUsuario
import com.mega.appcheckinout.models.TipoAccion
import java.text.SimpleDateFormat
import java.util.*
import com.mega.appcheckinout.data.DatosEjemplo
import androidx.compose.foundation.background



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuditoriaAccesosTab(colorPrimario: Color = Color(0xFF4A6FA5)) {
    var fechaInicio by remember { mutableStateOf<Long?>(null) }
    var fechaFin by remember { mutableStateOf<Long?>(null) }
    var usuarioFiltro by remember { mutableStateOf("") }
    var tipoAccionFiltro by remember { mutableStateOf<TipoAccion?>(null) }
    var estadoFiltro by remember { mutableStateOf<Boolean?>(null) }
    var mostrarFiltros by remember { mutableStateOf(false) }

    // Lista de eventos (en producción vendría del backend)
    val eventos = remember {
        mutableStateListOf<EventoAuditoria>().apply {
            addAll(DatosEjemplo.eventosAuditoria)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EFF5))
            .padding(16.dp)
    ) {
        // Encabezado
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Registro de Auditoría",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${eventos.size} eventos registrados",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // Botón filtros
                IconButton(onClick = { mostrarFiltros = !mostrarFiltros }) {
                    Icon(Icons.Default.Search, "Filtros")
                }

                // Botón exportar
                IconButton(onClick = { /* TODO: Exportar CSV */ }) {
                    Icon(Icons.Default.Share, "Exportar")
                }
            }
        }

        // Panel de filtros (desplegable)
        if (mostrarFiltros) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Filtros Avanzados",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Filtro por usuario
                    OutlinedTextField(
                        value = usuarioFiltro,
                        onValueChange = { usuarioFiltro = it },
                        label = { Text("Buscarusuario") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        leadingIcon = { Icon(Icons.Default.Search, null) },
                        singleLine = true
                    )
                    // Filtros en fila
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Tipo de acción
                        FilterChip(
                            selected = tipoAccionFiltro != null,
                            onClick = { /* TODO: Selector de tipo */ },
                            label = { Text(tipoAccionFiltro?.displayName ?: "Tipo de acción") },
                            modifier = Modifier.weight(1f)
                        )

                        // Estado
                        FilterChip(
                            selected = estadoFiltro != null,
                            onClick = {
                                estadoFiltro = when (estadoFiltro) {
                                    null -> true
                                    true -> false
                                    false -> null
                                }
                            },
                            label = {
                                Text(
                                    when (estadoFiltro) {
                                        true -> "Exitosos"
                                        false -> "Fallidos"
                                        null -> "Todos"
                                    }
                                )
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Botones de acción
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                usuarioFiltro = ""
                                tipoAccionFiltro = null
                                estadoFiltro = null
                                fechaInicio = null
                                fechaFin = null
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Clear, null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Limpiar")
                        }

                        Button(
                            onClick = { mostrarFiltros = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Check, null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Aplicar")
                        }
                    }
                }
            }
        }

        // Lista de eventos
        if (eventos.isEmpty()) {
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
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "No hay eventos registrados",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    eventos.filter {
                        (usuarioFiltro.isEmpty() || it.nombreUsuario.contains(usuarioFiltro, ignoreCase = true)) &&
                                (tipoAccionFiltro == null || it.accion == tipoAccionFiltro) &&
                                (estadoFiltro == null || it.exitoso == estadoFiltro)
                    }
                ) { evento ->
                    TarjetaEventoAuditoria(evento)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botones de exportación
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { /* TODO: Exportar CSV */ },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Share, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Exportar CSV")
            }

            Button(
                onClick = { /* TODO: Exportar PDF */ },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Share, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Generar Reporte PDF")
            }
        }
    }
}
@Composable
fun TarjetaEventoAuditoria(evento: EventoAuditoria) {
    val formatoFecha = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (evento.exitoso) {
                MaterialTheme.colorScheme.surface
            } else {
                MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f)
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Icono de acción
            Surface(
                modifier = Modifier.size(40.dp),
                shape = MaterialTheme.shapes.small,
                color = when {
                    !evento.exitoso -> MaterialTheme.colorScheme.errorContainer
                    evento.accion == TipoAccion.LOGIN_EXITOSO -> Color(0xFF4CAF50).copy(alpha = 0.2f)
                    evento.accion == TipoAccion.LOGOUT -> Color(0xFF9E9E9E).copy(alpha = 0.2f)
                    evento.accion in listOf(TipoAccion.CREACION_USUARIO, TipoAccion.EDICION_USUARIO) -> Color(0xFF2196F3).copy(alpha = 0.2f)
                    evento.accion in listOf(TipoAccion.BLOQUEO_CUENTA, TipoAccion.ELIMINACION_USUARIO) -> Color(0xFFF44336).copy(alpha = 0.2f)
                    else -> MaterialTheme.colorScheme.primaryContainer
                }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = when (evento.accion) {
                            TipoAccion.LOGIN_EXITOSO -> Icons.Default.Person
                            TipoAccion.LOGIN_FALLIDO -> Icons.Default.Warning
                            TipoAccion.LOGOUT -> Icons.Default.Close
                            TipoAccion.CAMBIO_CONTRASEÑA -> Icons.Default.Lock
                            TipoAccion.CREACION_USUARIO -> Icons.Default.Add
                            TipoAccion.EDICION_USUARIO -> Icons.Default.Edit
                            TipoAccion.ELIMINACION_USUARIO -> Icons.Default.Delete
                            TipoAccion.BLOQUEO_CUENTA -> Icons.Default.Warning
                            TipoAccion.DESBLOQUEO_CUENTA -> Icons.Default.Check
                            TipoAccion.MODIFICACION_PERMISOS -> Icons.Default.Settings
                            TipoAccion.CIERRE_SESION_REMOTO -> Icons.Default.Close
                        },
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Información del evento
            Column(modifier = Modifier.weight(1f)) {
                // Usuario y rol
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = evento.nombreUsuario,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Badge(
                        containerColor = when (evento.rol) {
                            RolUsuario.ADMINISTRADOR -> Color(0xFF6200EE)
                            RolUsuario.ENCARGADO -> Color(0xFF03DAC5)
                            RolUsuario.INSPECTOR_SST -> Color(0xFFFF9800)
                            RolUsuario.OPERATIVO -> Color(0xFF9E9E9E)
                        }
                    ) {
                        Text(
                            text = evento.rol.displayName,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }

                // Acción
                Text(
                    text = evento.accion.displayName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = if (evento.exitoso) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.error
                    }
                )

                // Descripción
                Text(
                    text = evento.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Metadatos
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Info,
                            null,
                            modifier = Modifier.size(12.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = formatoFecha.format(Date(evento.timestamp)),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Phone,
                            null,
                            modifier = Modifier.size(12.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = evento.dispositivo,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Info,
                            null,
                            modifier = Modifier.size(12.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = evento.ip,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Estado
            Surface(
                shape = MaterialTheme.shapes.small,
                color = if (evento.exitoso) {
                    Color(0xFF4CAF50).copy(alpha = 0.2f)
                } else {
                    MaterialTheme.colorScheme.errorContainer
                }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (evento.exitoso) Icons.Default.Check else Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = if (evento.exitoso) Color(0xFF4CAF50) else MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = if (evento.exitoso) "Éxito" else "Fallo",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (evento.exitoso) Color(0xFF4CAF50) else MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}