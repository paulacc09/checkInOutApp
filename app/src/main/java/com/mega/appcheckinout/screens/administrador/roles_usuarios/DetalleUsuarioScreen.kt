package com.mega.appcheckinout.screens.administrador.roles_usuarios

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mega.appcheckinout.models.EstadoUsuario
import com.mega.appcheckinout.models.RolUsuario
import com.mega.appcheckinout.models.Usuario
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleUsuarioScreen(
    usuario: Usuario,
    onEditar: (Usuario) -> Unit,
    onBloquear: (Usuario) -> Unit,
    onEliminar: (Usuario) -> Unit,
    onRestablecerContraseña: (Usuario) -> Unit,
    onCerrarSesiones: (Usuario) -> Unit,
    onVolver: () -> Unit
) {
    var mostrarMenuAcciones by remember { mutableStateOf(false) }
    var mostrarDialogoEliminar by remember { mutableStateOf(false) }
    var mostrarDialogoBloquear by remember { mutableStateOf(false) }
    var mostrarDialogoRestablecerPassword by remember { mutableStateOf(false) }
    var mostrarDialogoCerrarSesiones by remember { mutableStateOf(false) }

    val formatoFecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Usuario") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                actions = {
                    // Botón de acciones
                    IconButton(onClick = { mostrarMenuAcciones = true }) {
                        Icon(Icons.Default.MoreVert, "Más acciones")
                    }

                    DropdownMenu(
                        expanded = mostrarMenuAcciones,
                        onDismissRequest = { mostrarMenuAcciones = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Editar usuario") },
                            onClick = {
                                onEditar(usuario)
                                mostrarMenuAcciones = false
                            },
                            leadingIcon = { Icon(Icons.Default.Edit, null) }
                        )

                        if (usuario.rol != RolUsuario.OPERATIVO) {
                            DropdownMenuItem(
                                text = { Text("Restablecer contraseña") },
                                onClick = {
                                    mostrarDialogoRestablecerPassword = true
                                    mostrarMenuAcciones = false
                                },
                                leadingIcon = { Icon(Icons.Default.Lock, null) }
                            )

                            DropdownMenuItem(
                                text = { Text("Cerrar todas las sesiones") },
                                onClick = {
                                    mostrarDialogoCerrarSesiones = true
                                    mostrarMenuAcciones = false
                                },
                                leadingIcon = { Icon(Icons.Default.Close, null) }
                            )
                        }

                        Divider()

                        DropdownMenuItem(
                            text = {
                                Text(
                                    if (usuario.estado == EstadoUsuario.BLOQUEADO) "Desbloquear usuario"
                                    else "Bloquear usuario"
                                )
                            },
                            onClick = {
                                mostrarDialogoBloquear = true
                                mostrarMenuAcciones = false
                            },
                            leadingIcon = {
                                Icon(
                                    if (usuario.estado == EstadoUsuario.BLOQUEADO) Icons.Default.Check
                                    else Icons.Default.Warning,
                                    null
                                )
                            }
                        )

                        Divider()

                        DropdownMenuItem(
                            text = { Text("Eliminar usuario", color = MaterialTheme.colorScheme.error) },
                            onClick = {
                                mostrarDialogoEliminar = true
                                mostrarMenuAcciones = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Delete,
                                    null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Encabezado del perfil
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = when (usuario.rol) {
                        RolUsuario.ADMINISTRADOR -> Color(0xFF6200EE).copy(alpha = 0.1f)
                        RolUsuario.ENCARGADO -> Color(0xFF03DAC5).copy(alpha = 0.1f)
                        RolUsuario.INSPECTOR_SST -> Color(0xFFFF9800).copy(alpha = 0.1f)
                        RolUsuario.OPERATIVO -> Color(0xFF9E9E9E).copy(alpha = 0.1f)
                    }
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar
                    Surface(
                        modifier = Modifier.size(80.dp),
                        shape = MaterialTheme.shapes.medium,
                        color = when (usuario.rol) {
                            RolUsuario.ADMINISTRADOR -> Color(0xFF6200EE)
                            RolUsuario.ENCARGADO -> Color(0xFF03DAC5)
                            RolUsuario.INSPECTOR_SST -> Color(0xFFFF9800)
                            RolUsuario.OPERATIVO -> Color(0xFF9E9E9E)
                        }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = usuario.nombreCompleto.first().uppercase(),
                                style = MaterialTheme.typography.displayMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = usuario.nombreCompleto,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${usuario.tipoDocumento} ${usuario.numeroDocumento}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Badge(
                            containerColor = when (usuario.rol) {
                                RolUsuario.ADMINISTRADOR -> Color(0xFF6200EE)
                                RolUsuario.ENCARGADO -> Color(0xFF03DAC5)
                                RolUsuario.INSPECTOR_SST -> Color(0xFFFF9800)
                                RolUsuario.OPERATIVO -> Color(0xFF9E9E9E)
                            }
                        ) {
                            Text(usuario.rol.displayName)
                        }

                        Badge(
                            containerColor = when (usuario.estado) {
                                EstadoUsuario.ACTIVO -> Color(0xFF4CAF50)
                                EstadoUsuario.INACTIVO -> Color(0xFF9E9E9E)
                                EstadoUsuario.BLOQUEADO -> Color(0xFFF44336)
                            }
                        ) {
                            Text(usuario.estado.displayName)
                        }
                    }
                }
            }

            // Información de contacto
            SeccionDetalle(titulo = "Información de Contacto") {
                DatoDetalle(
                    icono = Icons.Default.Email,
                    etiqueta = "Email",
                    valor = usuario.email.ifEmpty { "No registrado" }
                )
                DatoDetalle(
                    icono = Icons.Default.Phone,
                    etiqueta = "Teléfono",
                    valor = usuario.telefono.ifEmpty { "No registrado" }
                )
            }

            // Credenciales de acceso (solo si no es operativo)
            if (usuario.rol != RolUsuario.OPERATIVO) {
                SeccionDetalle(titulo = "Credenciales de Acceso") {
                    DatoDetalle(
                        icono = Icons.Default.AccountCircle,
                        etiqueta = "Nombre de usuario",
                        valor = usuario.nombreUsuario
                    )
                    DatoDetalle(
                        icono = Icons.Default.Lock,
                        etiqueta = "Requiere cambio de contraseña",
                        valor = if (usuario.requiereCambioContraseña) "Sí" else "No"
                    )
                    usuario.ultimoAcceso?.let {
                        DatoDetalle(
                            icono = Icons.Default.Info,
                            etiqueta = "Último acceso",
                            valor = formatoFecha.format(Date(it))
                        )
                    }
                }
            }

            // Asignación de obra
            if (usuario.obraAsignadaNombre.isNotEmpty()) {
                SeccionDetalle(titulo = "Asignación de Obra") {
                    DatoDetalle(
                        icono = Icons.Default.Home,
                        etiqueta = "Obra actual",
                        valor = usuario.obraAsignadaNombre
                    )
                }
            }

            // Configuración de seguridad
            if (usuario.rol != RolUsuario.OPERATIVO) {
                SeccionDetalle(titulo = "Configuración de Seguridad") {
                    DatoDetalle(
                        icono = Icons.Default.Lock,
                        etiqueta = "Autenticación de dos factores (2FA)",
                        valor = if (usuario.autenticacion2FA) "Habilitada" else "Deshabilitada",
                        color = if (usuario.autenticacion2FA) Color(0xFF4CAF50) else null
                    )
                    DatoDetalle(
                        icono = Icons.Default.Phone,
                        etiqueta = "Múltiples sesiones",
                        valor = if (usuario.permitirMultiplesSesiones) "Permitido" else "No permitido"
                    )
                    DatoDetalle(
                        icono = Icons.Default.Info,
                        etiqueta = "Cambio de contraseña periódico",
                        valor = if (usuario.requiereCambioContraseñaCada90Dias) "Cada 90 días" else "No configurado"
                    )
                }
            }

            // Información de auditoría
            SeccionDetalle(titulo = "Información de Auditoría") {
                DatoDetalle(
                    icono = Icons.Default.Info,
                    etiqueta = "Fecha de creación",
                    valor = formatoFecha.format(Date(usuario.fechaCreacion))
                )
                DatoDetalle(
                    icono = Icons.Default.Person,
                    etiqueta = "Creado por",
                    valor = usuario.creadoPor
                )
                if (usuario.estado == EstadoUsuario.BLOQUEADO && usuario.motivoBloqueo.isNotEmpty()) {
                    DatoDetalle(
                        icono = Icons.Default.Warning,
                        etiqueta = "Motivo de bloqueo",
                        valor = usuario.motivoBloqueo,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            // Botones de acción rápida
            Spacer(modifier = Modifier.height(8.dp))

            if (usuario.rol != RolUsuario.OPERATIVO) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(

                        onClick = { mostrarDialogoRestablecerPassword = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Lock, null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Restablecer Contraseña")
                    }

                    OutlinedButton(
                        onClick = { mostrarDialogoCerrarSesiones = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Close, null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Cerrar Sesiones")
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onEditar(usuario) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Edit, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Editar Usuario")
                }

                Button(
                    onClick = { mostrarDialogoBloquear = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (usuario.estado == EstadoUsuario.BLOQUEADO) {
                            Color(0xFF4CAF50)
                        } else {
                            MaterialTheme.colorScheme.error
                        }
                    )
                ) {
                    Icon(
                        if (usuario.estado == EstadoUsuario.BLOQUEADO) Icons.Default.Check else Icons.Default.Warning,
                        null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (usuario.estado == EstadoUsuario.BLOQUEADO) "Desbloquear" else "Bloquear")
                }
            }
        }
    }

    // Diálogo: Eliminar usuario
    if (mostrarDialogoEliminar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEliminar = false },
            icon = { Icon(Icons.Default.Warning, null, tint = MaterialTheme.colorScheme.error) },
            title = { Text("Eliminar Usuario") },
            text = {
                Column {
                    Text("¿Está seguro de eliminar a:")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = usuario.nombreCompleto,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Esta acción no se puede deshacer. El usuario perderá acceso al sistema inmediatamente.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onEliminar(usuario)
                        mostrarDialogoEliminar = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
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

    // Diálogo: Bloquear/Desbloquear usuario
    if (mostrarDialogoBloquear) {
        var motivoBloqueo by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { mostrarDialogoBloquear = false },
            icon = {
                Icon(
                    if (usuario.estado == EstadoUsuario.BLOQUEADO) Icons.Default.Check else Icons.Default.Warning,
                    null,
                    tint = if (usuario.estado == EstadoUsuario.BLOQUEADO) Color(0xFF4CAF50) else MaterialTheme.colorScheme.error
                )
            },
            title = {
                Text(
                    if (usuario.estado == EstadoUsuario.BLOQUEADO) "Desbloquear Usuario"
                    else "Bloquear Usuario"
                )
            },
            text = {
                Column {
                    Text(
                        if (usuario.estado == EstadoUsuario.BLOQUEADO) {
                            "¿Desea desbloquear a ${usuario.nombreCompleto}?"
                        } else {
                            "¿Está seguro de bloquear a ${usuario.nombreCompleto}?"
                        }
                    )

                    if (usuario.estado != EstadoUsuario.BLOQUEADO) {
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = motivoBloqueo,
                            onValueChange = { motivoBloqueo = it },
                            label = { Text("Motivo del bloqueo") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onBloquear(usuario)
                        mostrarDialogoBloquear = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (usuario.estado == EstadoUsuario.BLOQUEADO) {
                            Color(0xFF4CAF50)
                        } else {
                            MaterialTheme.colorScheme.error
                        }
                    )
                ) {
                    Text(
                        if (usuario.estado == EstadoUsuario.BLOQUEADO) "Desbloquear"
                        else "Bloquear"
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoBloquear = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Diálogo: Restablecer contraseña
    if (mostrarDialogoRestablecerPassword) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoRestablecerPassword = false },
            icon = { Icon(Icons.Default.Lock, null) },
            title = { Text("Restablecer Contraseña") },
            text = {
                Column {
                    Text("Se generará una nueva contraseña temporal para:")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = usuario.nombreCompleto,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Se enviará un correo a ${usuario.email} con las nuevas credenciales.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    onRestablecerContraseña(usuario)
                    mostrarDialogoRestablecerPassword = false
                }) {
                    Text("Restablecer")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoRestablecerPassword = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Diálogo: Cerrar sesiones
    if (mostrarDialogoCerrarSesiones) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoCerrarSesiones = false },
            icon = { Icon(Icons.Default.Close, null, tint = MaterialTheme.colorScheme.error) },
            title = { Text("Cerrar Todas las Sesiones") },
            text = {
                Column {
                    Text("Se cerrarán todas las sesiones activas de:")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = usuario.nombreCompleto,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "El usuario deberá volver a iniciar sesión en todos sus dispositivos.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onCerrarSesiones(usuario)
                        mostrarDialogoCerrarSesiones = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Cerrar Sesiones")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoCerrarSesiones = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun SeccionDetalle(
    titulo: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Divider()
            content()
        }
    }
}

@Composable
fun DatoDetalle(
    icono: ImageVector,
    etiqueta: String,
    valor: String,
    color: Color? = null
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = color ?: MaterialTheme.colorScheme.onSurfaceVariant
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = etiqueta,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = valor,
                style = MaterialTheme.typography.bodyMedium,
                color = color ?: MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
        }
    }
}