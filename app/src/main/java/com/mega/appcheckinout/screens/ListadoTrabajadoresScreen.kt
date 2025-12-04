package com.mega.appcheckinout.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.models.Trabajador           // ← Modelo BÁSICO
import com.mega.appcheckinout.models.TrabajadorCompleto    // ← Modelo COMPLETO
import com.mega.appcheckinout.tarjetas.TarjetaTrabajador
import com.mega.appcheckinout.ui.theme.BotonVolver


/**
 * IMPORTANTE:
 * - TrabajadorCompleto: Se usa en el LISTADO (tiene todos los campos)
 * - Trabajador: Se usa en PERFIL y EDITAR (solo campos básicos)
 *
 * El flujo es:
 * 1. ListadoTrabajadores maneja TrabajadorCompleto (lista completa)
 * 2. Al hacer click en "Ver perfil" → convierte a Trabajador con .toTrabajador()
 * 3. VerPerfilScreen recibe Trabajador (solo campos básicos)
 */


// ==================== PANTALLA 9: Listado de Trabajadores ====================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoTrabajadoresScreen(
    onVolver: () -> Unit,
    onRegistrarPersonal: () -> Unit,
    onRegistrarBiometrico: () -> Unit,
    onVerPerfil: (Trabajador) -> Unit,
    onEditarTrabajador: (Trabajador) -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Estados para bÃºsqueda y filtros
    var textoBusqueda by remember { mutableStateOf("") }
    var mostrarFiltros by remember { mutableStateOf(false) }
    var filtroObra by remember { mutableStateOf("Todas") }
    var filtroRol by remember { mutableStateOf("Todos") }
    var filtroEstadoBiometria by remember { mutableStateOf("Todos") }
    var expandirFiltroObra by remember { mutableStateOf(false) }
    var expandirFiltroRol by remember { mutableStateOf(false) }
    var expandirFiltroBiometria by remember { mutableStateOf(false) }

    // MenÃº contextual para cada trabajador
    var trabajadorMenuExpanded by remember { mutableStateOf<String?>(null) }
    var mostrarDialogoEliminar by remember { mutableStateOf<TrabajadorCompleto?>(null) }


    // Datos de ejemplo ampliados
    val trabajadoresCompletos = remember {
        listOf(
            TrabajadorCompleto("1234567890", "García", "Rodríguez", "Juan", "Carlos", "Cédula",
                "Operativo", "Latero", "Mandarino - Ibagué", true, "Activo"),
            TrabajadorCompleto("0987654321", "Martínez", "López", "María", "Fernanda", "Cédula",
                "Inspector SST", "", "Bosque robledal - Rionegro", true, "Activo"),
            TrabajadorCompleto("1122334455", "González", "Pérez", "Pedro", "Antonio", "Cédula",
                "Operativo", "Mampostero", "Mandarino - IbaguÃ©", false, "Activo"),
            TrabajadorCompleto("5544332211", "Ramírez", "Torres", "Ana", "María", "Cédula",
                "Encargado", "", "Hacienda Nakare - Villavicencio", true, "Activo"),
            TrabajadorCompleto("6677889900", "Sánchez", "Vargas", "Luis", "Fernando", "Pasaporte",
                "Operativo", "Rematador", "Pomelo - Bogotá", false, "Activo"),
            TrabajadorCompleto("9988776655", "Díaz", "Castro", "Carolina", "", "Cédula",
                "Operativo", "Aseo", "Mandarino - Ibagué", true, "Inactivo"),
            TrabajadorCompleto("3344556677", "Morales", "Ruiz", "Jorge", "Andrés", "Cédula",
                "Inspector SST", "", "Bosque robledal - Rionegro", false, "Activo")
        )
    }

    // Opciones para filtros
    val obras = listOf("Todas", "Mandarino - Ibagué", "Bosque robledal - Rionegro",
        "Hacienda Nakare - Villavicencio", "Pomelo - Bogotá")
    val roles = listOf("Todos", "Operativo", "Inspector SST", "Encargado")
    val estadosBiometria = listOf("Todos", "Registrado", "Pendiente")

    // Filtrar trabajadores
    val trabajadoresFiltrados = trabajadoresCompletos.filter { trabajador ->
        val coincideBusqueda = textoBusqueda.isEmpty() ||
                trabajador.nombreCompleto().contains(textoBusqueda, ignoreCase = true) ||
                trabajador.id.contains(textoBusqueda, ignoreCase = true)

        val coincideObra = filtroObra == "Todas" || trabajador.obraAsignada == filtroObra
        val coincideRol = filtroRol == "Todos" || trabajador.rol == filtroRol
        val coincideBiometria = filtroEstadoBiometria == "Todos" ||
                (filtroEstadoBiometria == "Registrado" && trabajador.biometriaRegistrada) ||
                (filtroEstadoBiometria == "Pendiente" && !trabajador.biometriaRegistrada)

        coincideBusqueda && coincideObra && coincideRol && coincideBiometria
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EFF5))
    ) {
        // Header con bÃºsqueda y filtros
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFB8D4E3))
        ) {
            // Fila superior: Volver, TÃ­tulo, BÃºsqueda, Filtros
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
                    text = "LISTA DE TRABAJADORES",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorPrimario,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(onClick = { /* Activar bÃºsqueda */ }) {
                        Icon(
                            painter = painterResource(android.R.drawable.ic_menu_search),
                            contentDescription = "Buscar",
                            tint = colorPrimario
                        )
                    }

                    IconButton(onClick = { mostrarFiltros = !mostrarFiltros }) {
                        Icon(
                            painter = painterResource(android.R.drawable.ic_menu_sort_by_size),
                            contentDescription = "Filtros",
                            tint = if (mostrarFiltros) Color(0xFF2196F3) else colorPrimario
                        )
                    }
                }
            }

            // Barra de bÃºsqueda
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                placeholder = { Text("Buscar por nombre o cédula...", fontSize = 14.sp) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_menu_search),
                        contentDescription = null,
                        tint = colorPrimario
                    )
                },
                trailingIcon = {
                    if (textoBusqueda.isNotEmpty()) {
                        IconButton(onClick = { textoBusqueda = "" }) {
                            Icon(
                                painter = painterResource(android.R.drawable.ic_menu_close_clear_cancel),
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

            // Panel de filtros desplegable
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

                        // Filtro por Obra
                        ExposedDropdownMenuBox(
                            expanded = expandirFiltroObra,
                            onExpandedChange = { expandirFiltroObra = it }
                        ) {
                            OutlinedTextField(
                                value = filtroObra,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Obra", fontSize = 14.sp) },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirFiltroObra)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colorPrimario
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandirFiltroObra,
                                onDismissRequest = { expandirFiltroObra = false }
                            ) {
                                obras.forEach { obra ->
                                    DropdownMenuItem(
                                        text = { Text(obra, fontSize = 14.sp) },
                                        onClick = {
                                            filtroObra = obra
                                            expandirFiltroObra = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Filtro por Rol
                        ExposedDropdownMenuBox(
                            expanded = expandirFiltroRol,
                            onExpandedChange = { expandirFiltroRol = it }
                        ) {
                            OutlinedTextField(
                                value = filtroRol,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Rol", fontSize = 14.sp) },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirFiltroRol)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colorPrimario
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandirFiltroRol,
                                onDismissRequest = { expandirFiltroRol = false }
                            ) {
                                roles.forEach { rol ->
                                    DropdownMenuItem(
                                        text = { Text(rol, fontSize = 14.sp) },
                                        onClick = {
                                            filtroRol = rol
                                            expandirFiltroRol = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Filtro por Estado BiometrÃ­a
                        ExposedDropdownMenuBox(
                            expanded = expandirFiltroBiometria,
                            onExpandedChange = { expandirFiltroBiometria = it }
                        ) {
                            OutlinedTextField(
                                value = filtroEstadoBiometria,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Estado Biometría", fontSize = 14.sp) },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirFiltroBiometria)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colorPrimario
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandirFiltroBiometria,
                                onDismissRequest = { expandirFiltroBiometria = false }
                            ) {
                                estadosBiometria.forEach { estado ->
                                    DropdownMenuItem(
                                        text = { Text(estado, fontSize = 14.sp) },
                                        onClick = {
                                            filtroEstadoBiometria = estado
                                            expandirFiltroBiometria = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // BotÃ³n limpiar filtros
                        TextButton(
                            onClick = {
                                filtroObra = "Todas"
                                filtroRol = "Todos"
                                filtroEstadoBiometria = "Todos"
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Contador de resultados
                Text(
                    text = "${trabajadoresFiltrados.size} trabajador(es) encontrado(s)",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Lista de trabajadores
                if (trabajadoresFiltrados.isEmpty()) {
                    // Mensaje cuando no hay resultados
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "No se encontraron trabajadores",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Intenta con otros criterios de búsqueda",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(trabajadoresFiltrados) { trabajadorCompleto ->
                            TarjetaTrabajador(
                                trabajador = trabajadorCompleto,
                                colorPrimario = colorPrimario,
                                colorSecundario = colorSecundario,
                                menuExpanded = trabajadorMenuExpanded == trabajadorCompleto.id,
                                onMenuExpandir = {
                                    trabajadorMenuExpanded = if (trabajadorMenuExpanded == trabajadorCompleto.id) {
                                        null
                                    } else {
                                        trabajadorCompleto.id
                                    }
                                },
                                onVerPerfil = { onVerPerfil(trabajadorCompleto.toTrabajador()) },
                                onEditar = { onEditarTrabajador(trabajadorCompleto.toTrabajador()) },
                                onEliminar = { mostrarDialogoEliminar = trabajadorCompleto },
                                onAsignarObra = { /* AcciÃ³n futura */ }
                            )
                        }
                    }
                }
            }

            // BotÃ³n flotante "Nuevo Trabajador"
            FloatingActionButton(
                onClick = onRegistrarPersonal,
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
                        painter = painterResource(android.R.drawable.ic_input_add),
                        contentDescription = "Agregar",
                        tint = Color.White
                    )
                    Text(
                        text = "Nuevo Trabajador",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // DiÃ¡logo de confirmaciÃ³n para eliminar
    if (mostrarDialogoEliminar != null) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEliminar = null },
            title = { Text("Confirmar eliminación") },
            text = {
                Text("¿Estás seguro de que deseas desactivar a ${mostrarDialogoEliminar!!.nombreCompleto()}?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        // AquÃ­ irÃ­a la lÃ³gica para eliminar/desactivar
                        mostrarDialogoEliminar = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Desactivar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
