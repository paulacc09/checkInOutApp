package com.mega.appcheckinout.screens.administrador.dispositivos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.mega.appcheckinout.models.*
import com.mega.appcheckinout.ui.theme.BotonVolver
import com.mega.appcheckinout.data.DatosEjemplo
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionDispositivosScreen(
    onVolver: () -> Unit,
    onRegistrarDispositivo: () -> Unit = {},
    onVerDetalleDispositivo: (Dispositivo) -> Unit = {},
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Estados
    var textoBusqueda by remember { mutableStateOf("") }
    var filtroEstado by remember { mutableStateOf<EstadoDispositivo?>(null) }
    var filtroObra by remember { mutableStateOf("Todas") }
    var mostrarFiltros by remember { mutableStateOf(false) }

    // Datos de ejemplo
    val dispositivos = remember { mutableStateListOf<Dispositivo>().apply {
        addAll(DatosEjemplo.dispositivos)
    }}
    val obras = listOf("Todas") + DatosEjemplo.nombresObras

    // Dispositivos filtrados
    val dispositivosFiltrados = dispositivos.filter { dispositivo ->
        val cumpleBusqueda = textoBusqueda.isEmpty() ||
                dispositivo.nombre.contains(textoBusqueda, ignoreCase = true) ||
                dispositivo.obraNombre.contains(textoBusqueda, ignoreCase = true) ||
                dispositivo.responsableNombre.contains(textoBusqueda, ignoreCase = true)

        val cumpleEstado = filtroEstado == null || dispositivo.estado == filtroEstado
        val cumpleObra = filtroObra == "Todas" || dispositivo.obraNombre == filtroObra

        cumpleBusqueda && cumpleEstado && cumpleObra
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EFF5))
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFB8D4E3))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
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
                    text = "GESTIÓN DE DISPOSITIVOS",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorPrimario,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(onClick = { /* TODO: Actualizar */ }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Actualizar",
                            tint = colorPrimario
                        )
                    }

                    IconButton(onClick = { mostrarFiltros = !mostrarFiltros }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Filtros",
                            tint = if (mostrarFiltros) Color(0xFF2196F3) else colorPrimario
                        )
                    }
                }
            }

            // Barra de búsqueda
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                placeholder = { Text("Buscar dispositivo, obra o responsable...", fontSize = 14.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = colorPrimario
                    )
                },
                trailingIcon = {
                    if (textoBusqueda.isNotEmpty()) {
                        IconButton(onClick = { textoBusqueda = "" }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Limpiar",
                                tint = Color.Gray
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = colorPrimario,
                    unfocusedBorderColor = Color.Gray
                ),
                shape = RoundedCornerShape(25.dp),
                singleLine = true
            )

            // Panel de filtros
            if (mostrarFiltros) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Filtros",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorPrimario,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        // Filtro por obra
                        var expandidoObra by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(
                            expanded = expandidoObra,
                            onExpandedChange = { expandidoObra = it }
                        ) {
                            OutlinedTextField(
                                value = filtroObra,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Obra", fontSize = 14.sp) },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoObra)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colorPrimario
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandidoObra,
                                onDismissRequest = { expandidoObra = false }
                            ) {
                                obras.forEach { obra ->
                                    DropdownMenuItem(
                                        text = { Text(obra, fontSize = 14.sp) },
                                        onClick = {
                                            filtroObra = obra
                                            expandidoObra = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Filtro por estado
                        Text("Estado:", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            EstadoDispositivo.values().forEach { estado ->
                                FilterChip(
                                    selected = filtroEstado == estado,
                                    onClick = {
                                        filtroEstado = if (filtroEstado == estado) null else estado
                                    },
                                    label = { Text(estado.displayName, fontSize = 12.sp) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        TextButton(
                            onClick = {
                                filtroObra = "Todas"
                                filtroEstado = null
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Limpiar filtros", color = colorPrimario, fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        // Contenido principal
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Estadísticas rápidas
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    EstadisticaDispositivo(
                        label = "Total",
                        count = dispositivos.size,
                        color = colorPrimario
                    )
                    EstadisticaDispositivo(
                        label = "Activos",
                        count = dispositivos.count { it.estado == EstadoDispositivo.ACTIVO },
                        color = Color(0xFF4CAF50)
                    )
                    EstadisticaDispositivo(
                        label = "Bloqueados",
                        count = dispositivos.count { it.estado == EstadoDispositivo.BLOQUEADO },
                        color = Color(0xFFF44336)
                    )
                }

                Divider()

                // Contador de resultados
                Text(
                    text = "${dispositivosFiltrados.size} dispositivo(s) encontrado(s)",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                // Lista de dispositivos
                if (dispositivosFiltrados.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "No se encontraron dispositivos",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(dispositivosFiltrados) { dispositivo ->
                            TarjetaDispositivo(
                                dispositivo = dispositivo,
                                onClick = { onVerDetalleDispositivo(dispositivo) },
                                onCambiarEstado = { nuevoEstado ->
                                    // TODO: Actualizar estado en base de datos
                                    val index = dispositivos.indexOfFirst { it.id == dispositivo.id }
                                    if (index != -1) {
                                        dispositivos[index] = dispositivo.copy(estado = nuevoEstado)
                                    }
                                },
                                colorPrimario = colorPrimario
                            )
                        }

                        item { Spacer(modifier = Modifier.height(80.dp)) }
                    }
                }
            }

            // FAB para registrar dispositivo
            FloatingActionButton(
                onClick = onRegistrarDispositivo,
                containerColor = colorPrimario,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar",
                        tint = Color.White
                    )
                    Text(
                        text = "Nuevo Dispositivo",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TarjetaDispositivo(
    dispositivo: Dispositivo,
    onClick: () -> Unit,
    onCambiarEstado: (EstadoDispositivo) -> Unit,
    colorPrimario: Color
) {
    var mostrarMenu by remember { mutableStateOf(false) }
    var mostrarDialogoCambioEstado by remember { mutableStateOf(false) }
    val formatoFecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Icono del tipo de dispositivo
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(8.dp),
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
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Información del dispositivo
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = dispositivo.nombre,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = Color.Gray
                    )
                    Text(
                        text = dispositivo.obraNombre,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = Color.Gray
                    )
                    Text(
                        text = dispositivo.responsableNombre,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }

                if (dispositivo.ultimoUso != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Último uso: ${formatoFecha.format(Date(dispositivo.ultimoUso))}",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }
            }

            // Estado y menú
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(dispositivo.estado.color)
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = dispositivo.estado.displayName,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 11.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box {
                    IconButton(
                        onClick = { mostrarMenu = true },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Opciones",
                            tint = colorPrimario
                        )
                    }

                    DropdownMenu(
                        expanded = mostrarMenu,
                        onDismissRequest = { mostrarMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Ver detalles") },
                            onClick = {
                                mostrarMenu = false
                                onClick()
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Info, null)
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Cambiar estado") },
                            onClick = {
                                mostrarMenu = false
                                mostrarDialogoCambioEstado = true
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Edit, null)
                            }
                        )

                        if (dispositivo.estado != EstadoDispositivo.BLOQUEADO) {
                            DropdownMenuItem(
                                text = { Text("Bloquear dispositivo", color = Color.Red) },
                                onClick = {
                                    mostrarMenu = false
                                    onCambiarEstado(EstadoDispositivo.BLOQUEADO)
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.Lock, null, tint = Color.Red)
                                }
                            )
                        }
                    }
                }
            }
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
                                .clickable {
                                    onCambiarEstado(estado)
                                    mostrarDialogoCambioEstado = false
                                }
                                .padding(vertical = 12.dp),
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
                            Text(estado.displayName)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { mostrarDialogoCambioEstado = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun EstadisticaDispositivo(
    label: String,
    count: Int,
    color: Color
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = color
            )
        }
    }
}