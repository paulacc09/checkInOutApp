package com.mega.appcheckinout.screens.detalle.componentes.roles_usuarios

import androidx.compose.foundation.clickable
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
import com.mega.appcheckinout.models.*
import java.text.SimpleDateFormat
import java.util.*
import com.mega.appcheckinout.data.DatosEjemplo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaUsuariosTab() {
    var busqueda by remember { mutableStateOf("") }
    var filtroRol by remember { mutableStateOf<RolUsuario?>(null) }
    var filtroEstado by remember { mutableStateOf<EstadoUsuario?>(null) }
    var mostrarDialogoNuevoUsuario by remember { mutableStateOf(false) }
    var usuarioSeleccionado by remember { mutableStateOf<Usuario?>(null) }

    // Lista de usuarios (en producción vendría de ViewModel/Repository)
    val usuarios = remember {
        mutableStateListOf<Usuario>().apply {
            addAll(DatosEjemplo.usuarios)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Barra de búsqueda y filtros
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Buscador
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Buscar por nombre, email o cédula") },
                leadingIcon = { Icon(Icons.Default.Search, "Buscar") },
                singleLine = true
            )

            // Botón filtros
            IconButton(onClick = { /* TODO: Mostrar diálogo de filtros */ }) {
                Icon(Icons.Default.Search, "Filtros")
            }

            // Botón nuevo usuario
            ExtendedFloatingActionButton(
                onClick = { mostrarDialogoNuevoUsuario = true },
                icon = { Icon(Icons.Default.Add, "Agregar") },
                text = { Text("Nuevo Usuario") }
            )
        }

        // Lista de usuarios
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                usuarios.filter {
                    (busqueda.isEmpty() ||
                            it.nombreCompleto.contains(busqueda, ignoreCase = true) ||
                            it.numeroDocumento.contains(busqueda) ||
                            it.email.contains(busqueda, ignoreCase = true)) &&
                            (filtroRol == null || it.rol == filtroRol) &&
                            (filtroEstado == null || it.estado == filtroEstado)
                }
            ) { usuario ->
                TarjetaUsuario(
                    usuario = usuario,
                    onVerPerfil = { usuarioSeleccionado = it },
                    onEditar = { /* TODO */ },
                    onBloquear = { /* TODO */ },
                    onEliminar = { usuarios.remove(it) }
                )
            }
        }
    }

    // Diálogo para crear nuevo usuario
    if (mostrarDialogoNuevoUsuario) {
        // TODO: Implementar pantalla completa CrearEditarUsuarioScreen
        AlertDialog(
            onDismissRequest = { mostrarDialogoNuevoUsuario = false },
            title = { Text("Crear Nuevo Usuario") },
            text = { Text("Aquí iría el formulario completo de CrearEditarUsuarioScreen") },
            confirmButton = {
                Button(onClick = { mostrarDialogoNuevoUsuario = false }) {
                    Text("Cerrar")
                }
            }
        )
    }

    // Mostrar detalle del usuario seleccionado
    usuarioSeleccionado?.let { usuario ->
        // TODO: Navegar a DetalleUsuarioScreen
        AlertDialog(
            onDismissRequest = { usuarioSeleccionado = null },
            title = { Text("Detalle de Usuario") },
            text = {
                Column {
                    Text("Nombre: ${usuario.nombreCompleto}")
                    Text("Rol: ${usuario.rol.displayName}")
                    Text("Estado: ${usuario.estado.displayName}")
                }
            },
            confirmButton = {
                Button(onClick = { usuarioSeleccionado = null }) {
                    Text("Cerrar")
                }
            }
        )
    }
}

@Composable
fun TarjetaUsuario(
    usuario: Usuario,
    onVerPerfil: (Usuario) -> Unit,
    onEditar: (Usuario) -> Unit,
    onBloquear: (Usuario) -> Unit,
    onEliminar: (Usuario) -> Unit
) {
    var mostrarMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onVerPerfil(usuario) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Información del usuario
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = usuario.nombreCompleto.first().uppercase(),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Datos del usuario
                Column {
                    Text(
                        text = usuario.nombreCompleto,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = usuario.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Badge de rol
                        Badge(
                            containerColor = when (usuario.rol) {
                                RolUsuario.ADMINISTRADOR -> Color(0xFF6200EE)
                                RolUsuario.ENCARGADO -> Color(0xFF03DAC5)
                                RolUsuario.INSPECTOR_SST -> Color(0xFFFF9800)
                                RolUsuario.OPERATIVO -> Color(0xFF9E9E9E)
                            }
                        ) {
                            Text(
                                text = usuario.rol.displayName,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }

                        // Badge de estado
                        Badge(
                            containerColor = when (usuario.estado) {
                                EstadoUsuario.ACTIVO -> Color(0xFF4CAF50)
                                EstadoUsuario.INACTIVO -> Color(0xFF9E9E9E)
                                EstadoUsuario.BLOQUEADO -> Color(0xFFF44336)
                            }
                        ) {
                            Text(
                                text = usuario.estado.displayName,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }

                    // Obra asignada (si aplica)
                    if (usuario.obraAsignadaNombre.isNotEmpty()) {
                        Text(
                            text = "Obra: ${usuario.obraAsignadaNombre}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Último acceso
                    usuario.ultimoAcceso?.let {
                        val fecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(it))
                        Text(
                            text = "Último acceso: $fecha",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Menú de acciones
            Box {
                IconButton(onClick = { mostrarMenu = true }) {
                    Icon(Icons.Default.MoreVert, "Más opciones")
                }

                DropdownMenu(
                    expanded = mostrarMenu,
                    onDismissRequest = { mostrarMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Ver perfil completo") },
                        onClick = {
                            onVerPerfil(usuario)
                            mostrarMenu = false
                        },
                        leadingIcon = { Icon(Icons.Default.Info, null) }
                    )
                    DropdownMenuItem(
                        text = { Text("Editar") },
                        onClick = {
                            onEditar(usuario)
                            mostrarMenu = false
                        },
                        leadingIcon = { Icon(Icons.Default.Edit, null) }
                    )
                    DropdownMenuItem(
                        text = { Text("Restablecer contraseña") },
                        onClick = {
                            // TODO
                            mostrarMenu = false
                        },
                        leadingIcon = { Icon(Icons.Default.Lock, null) }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                if (usuario.estado == EstadoUsuario.BLOQUEADO) "Desbloquear"
                                else "Bloquear"
                            )
                        },
                        onClick = {
                            onBloquear(usuario)
                            mostrarMenu = false
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
                        text = { Text("Eliminar", color = MaterialTheme.colorScheme.error) },
                        onClick = {
                            onEliminar(usuario)
                            mostrarMenu = false
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
        }
    }
}