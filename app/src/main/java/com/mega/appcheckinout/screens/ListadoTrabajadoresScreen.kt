package com.mega.appcheckinout.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.models.TrabajadorCompleto
import com.mega.appcheckinout.tarjetas.TarjetaTrabajador
import com.mega.appcheckinout.ui.theme.BotonVolver

/**
 * Vista mejorada de listado de trabajadores
 * - Alterna entre vista de tarjetas y vista de tabla
 * - Preparado para exportar a PDF/CSV
 */

enum class VistaListado {
    TARJETAS,
    TABLA
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoTrabajadoresScreen(
    onVolver: () -> Unit,
    onRegistrarPersonal: () -> Unit,
    onRegistrarBiometrico: () -> Unit,
    onVerPerfil: (TrabajadorCompleto) -> Unit,
    onEditarTrabajador: (TrabajadorCompleto) -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Estados para búsqueda y filtros
    var textoBusqueda by remember { mutableStateOf("") }
    var mostrarFiltros by remember { mutableStateOf(false) }
    var filtroObra by remember { mutableStateOf("Todas") }
    var filtroRol by remember { mutableStateOf("Todos") }
    var filtroEstadoBiometria by remember { mutableStateOf("Todos") }
    var expandirFiltroObra by remember { mutableStateOf(false) }
    var expandirFiltroRol by remember { mutableStateOf(false) }
    var expandirFiltroBiometria by remember { mutableStateOf(false) }

    // Nuevo: Estado para alternar vista
    var vistaActual by remember { mutableStateOf(VistaListado.TARJETAS) }

    // Nuevo: Estado para menú de exportación
    var mostrarMenuExportar by remember { mutableStateOf(false) }

    // Menú contextual para cada trabajador
    var trabajadorMenuExpanded by remember { mutableStateOf<String?>(null) }
    var mostrarDialogoEliminar by remember { mutableStateOf<TrabajadorCompleto?>(null) }

    // Datos de ejemplo
    val trabajadoresCompletos = remember {
        listOf(
            TrabajadorCompleto(
                id = "1",
                primerNombre = "Juan",
                segundoNombre = "Carlos",
                primerApellido = "García",
                segundoApellido = "Rodríguez",
                fechaNacimiento = "15/03/1990",
                tipoDocumento = "Cédula",
                numeroDocumento = "1234567890",
                telefono = "3001234567",
                direccion = "Calle 50 #23-45, Ibagué",
                rol = "Operativo",
                subCargo = "Latero",
                actividad = "Muros",
                obraAsignada = "Mandarino - Ibagué",
                arl = "Sura",
                eps = "Sanitas",
                fechaExamen = "01/01/2024",
                fechaCursoAlturas = "15/01/2024",
                biometriaRegistrada = true,
                estado = "Activo"
            ),
            TrabajadorCompleto(
                id = "2",
                primerNombre = "María",
                segundoNombre = "Fernanda",
                primerApellido = "Martínez",
                segundoApellido = "López",
                fechaNacimiento = "22/07/1985",
                tipoDocumento = "Cédula",
                numeroDocumento = "0987654321",
                telefono = "3109876543",
                direccion = "Carrera 10 #15-20, Rionegro",
                rol = "Inspector SST",
                subCargo = "",
                actividad = "",
                obraAsignada = "Bosque robledal - Rionegro",
                arl = "Positiva",
                eps = "Compensar",
                fechaExamen = "10/02/2024",
                fechaCursoAlturas = "20/02/2024",
                biometriaRegistrada = true,
                estado = "Activo"
            ),
            TrabajadorCompleto(
                id = "3",
                primerNombre = "Pedro",
                segundoNombre = "Antonio",
                primerApellido = "González",
                segundoApellido = "Pérez",
                fechaNacimiento = "05/11/1992",
                tipoDocumento = "Cédula",
                numeroDocumento = "1122334455",
                telefono = "3201122334",
                direccion = "Avenida 30 #12-34, Ibagué",
                rol = "Operativo",
                subCargo = "Mampostero",
                actividad = "Muros",
                obraAsignada = "Mandarino - Ibagué",
                arl = "Sura",
                eps = "Nueva EPS",
                fechaExamen = "15/03/2024",
                fechaCursoAlturas = "25/03/2024",
                biometriaRegistrada = false,
                estado = "Activo"
            ),
            TrabajadorCompleto(
                id = "4",
                primerNombre = "Ana",
                segundoNombre = "María",
                primerApellido = "Ramírez",
                segundoApellido = "Torres",
                fechaNacimiento = "18/09/1988",
                tipoDocumento = "Cédula",
                numeroDocumento = "5544332211",
                telefono = "3155443322",
                direccion = "Calle 20 #8-15, Villavicencio",
                rol = "Encargado",
                subCargo = "",
                actividad = "",
                obraAsignada = "Hacienda Nakare - Villavicencio",
                arl = "Colmena",
                eps = "Salud Total",
                fechaExamen = "05/04/2024",
                fechaCursoAlturas = "12/04/2024",
                biometriaRegistrada = true,
                estado = "Activo"
            ),
            TrabajadorCompleto(
                id = "5",
                primerNombre = "Luis",
                segundoNombre = "Fernando",
                primerApellido = "Sánchez",
                segundoApellido = "Vargas",
                fechaNacimiento = "30/01/1995",
                tipoDocumento = "Pasaporte",
                numeroDocumento = "6677889900",
                telefono = "3206677889",
                direccion = "Transversal 5 #40-20, Bogotá",
                rol = "Operativo",
                subCargo = "Rematador",
                actividad = "Parqueadero",
                obraAsignada = "Pomelo - Bogotá",
                arl = "Positiva",
                eps = "Compensar",
                fechaExamen = "20/04/2024",
                fechaCursoAlturas = "28/04/2024",
                biometriaRegistrada = false,
                estado = "Activo"
            ),
            TrabajadorCompleto(
                id = "6",
                primerNombre = "Carolina",
                segundoNombre = "",
                primerApellido = "Díaz",
                segundoApellido = "Castro",
                fechaNacimiento = "12/06/1991",
                tipoDocumento = "Cédula",
                numeroDocumento = "9988776655",
                telefono = "3159988776",
                direccion = "Diagonal 25 #18-30, Ibagué",
                rol = "Operativo",
                subCargo = "Aseo",
                actividad = "",
                obraAsignada = "Mandarino - Ibagué",
                arl = "Sura",
                eps = "Sanitas",
                fechaExamen = "01/05/2024",
                fechaCursoAlturas = "10/05/2024",
                biometriaRegistrada = true,
                estado = "Inactivo"
            ),
            TrabajadorCompleto(
                id = "7",
                primerNombre = "Jorge",
                segundoNombre = "Andrés",
                primerApellido = "Morales",
                segundoApellido = "Ruiz",
                fechaNacimiento = "25/04/1987",
                tipoDocumento = "Cédula",
                numeroDocumento = "3344556677",
                telefono = "3103344556",
                direccion = "Carrera 15 #22-10, Rionegro",
                rol = "Inspector SST",
                subCargo = "",
                actividad = "",
                obraAsignada = "Bosque robledal - Rionegro",
                arl = "Colmena",
                eps = "Nueva EPS",
                fechaExamen = "15/05/2024",
                fechaCursoAlturas = "22/05/2024",
                biometriaRegistrada = false,
                estado = "Activo"
            )
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
                trabajador.numeroDocumento.contains(textoBusqueda, ignoreCase = true)

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
        // Header con búsqueda y filtros
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFB8D4E3))
        ) {
            // Fila superior: Volver, Título, Controles
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
                    text = "LISTADO DE TRABAJADORES",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorPrimario,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Botón cambiar vista
                    IconButton(onClick = {
                        vistaActual = if (vistaActual == VistaListado.TARJETAS) {
                            VistaListado.TABLA
                        } else {
                            VistaListado.TARJETAS
                        }
                    }) {
                        Icon(
                            painter = painterResource(
                                if (vistaActual == VistaListado.TARJETAS)
                                    android.R.drawable.ic_menu_view
                                else
                                    android.R.drawable.ic_menu_agenda
                            ),
                            contentDescription = "Cambiar vista",
                            tint = colorPrimario
                        )
                    }

                    // Botón exportar
                    Box {
                        IconButton(onClick = { mostrarMenuExportar = !mostrarMenuExportar }) {
                            Icon(
                                painter = painterResource(android.R.drawable.ic_menu_save),
                                contentDescription = "Exportar",
                                tint = colorPrimario
                            )
                        }

                        DropdownMenu(
                            expanded = mostrarMenuExportar,
                            onDismissRequest = { mostrarMenuExportar = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Exportar a PDF") },
                                onClick = {
                                    // TODO: Implementar exportación PDF
                                    mostrarMenuExportar = false
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(android.R.drawable.ic_menu_save),
                                        contentDescription = null
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Exportar a CSV") },
                                onClick = {
                                    // TODO: Implementar exportación CSV
                                    mostrarMenuExportar = false
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(android.R.drawable.ic_menu_save),
                                        contentDescription = null
                                    )
                                }
                            )
                        }
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

            // Barra de búsqueda
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

                        // Filtros (mantener código existente)
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

        // Contenido principal - Alternar entre vistas
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

                // Alternar entre vista de tarjetas y tabla
                when (vistaActual) {
                    VistaListado.TARJETAS -> {
                        VistaTarjetas(
                            trabajadores = trabajadoresFiltrados,
                            colorPrimario = colorPrimario,
                            colorSecundario = colorSecundario,
                            trabajadorMenuExpanded = trabajadorMenuExpanded,
                            onMenuExpandir = { id ->
                                trabajadorMenuExpanded = if (trabajadorMenuExpanded == id) null else id
                            },
                            onVerPerfil = onVerPerfil,
                            onEditar = onEditarTrabajador,
                            onEliminar = { mostrarDialogoEliminar = it }
                        )
                    }
                    VistaListado.TABLA -> {
                        VistaTabla(
                            trabajadores = trabajadoresFiltrados,
                            colorPrimario = colorPrimario,
                            onVerPerfil = onVerPerfil,
                            onEditar = onEditarTrabajador
                        )
                    }
                }
            }

            // Botón flotante "Nuevo Trabajador"
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

    // Diálogo de confirmación para eliminar
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
                        // TODO: Implementar lógica para eliminar/desactivar
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

// ========== VISTA DE TARJETAS (Original) ==========
@Composable
private fun VistaTarjetas(
    trabajadores: List<TrabajadorCompleto>,
    colorPrimario: Color,
    colorSecundario: Color,
    trabajadorMenuExpanded: String?,
    onMenuExpandir: (String) -> Unit,
    onVerPerfil: (TrabajadorCompleto) -> Unit,
    onEditar: (TrabajadorCompleto) -> Unit,
    onEliminar: (TrabajadorCompleto) -> Unit
) {
    if (trabajadores.isEmpty()) {
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
            items(trabajadores) { trabajador ->
                TarjetaTrabajador(
                    trabajador = trabajador,
                    colorPrimario = colorPrimario,
                    colorSecundario = colorSecundario,
                    menuExpanded = trabajadorMenuExpanded == trabajador.id,
                    onMenuExpandir = { onMenuExpandir(trabajador.id) },
                    onVerPerfil = { onVerPerfil(trabajador) },
                    onEditar = { onEditar(trabajador) },
                    onEliminar = { onEliminar(trabajador) },
                    onAsignarObra = { /* Acción futura */ }
                )
            }
            // Espacio adicional para el FAB
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

// ========== VISTA DE TABLA (Nueva) ==========
@Composable
private fun VistaTabla(
    trabajadores: List<TrabajadorCompleto>,
    colorPrimario: Color,
    onVerPerfil: (TrabajadorCompleto) -> Unit,
    onEditar: (TrabajadorCompleto) -> Unit
) {
    if (trabajadores.isEmpty()) {
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
        Card(
            modifier = Modifier.fillMaxSize(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Encabezados de la tabla (fijos con scroll horizontal)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorPrimario)
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = 12.dp, horizontal = 8.dp)
                ) {
                    EncabezadoTabla("ID", 80.dp)
                    EncabezadoTabla("Apellidos", 150.dp)
                    EncabezadoTabla("Nombres", 150.dp)
                    EncabezadoTabla("Tipo Doc.", 100.dp)
                    EncabezadoTabla("Documento", 120.dp)
                    EncabezadoTabla("Rol", 120.dp)
                    EncabezadoTabla("Obra", 200.dp)
                    EncabezadoTabla("Biometría", 100.dp)
                    EncabezadoTabla("Acciones", 120.dp)
                }

                Divider(color = Color.Gray, thickness = 1.dp)

                // Filas de datos con scroll
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(trabajadores) { trabajador ->
                        FilaTrabajador(
                            trabajador = trabajador,
                            colorPrimario = colorPrimario,
                            onVerPerfil = { onVerPerfil(trabajador) },
                            onEditar = { onEditar(trabajador) }
                        )
                        Divider(color = Color.LightGray, thickness = 0.5.dp)
                    }
                    // Espacio adicional para el FAB
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }
    }
}

@Composable
private fun EncabezadoTabla(texto: String, ancho: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .width(ancho)
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = texto,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun FilaTrabajador(
    trabajador: TrabajadorCompleto,
    colorPrimario: Color,
    onVerPerfil: () -> Unit,
    onEditar: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (trabajador.estado == "Activo") Color.White else Color(0xFFFFF3E0))
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 8.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ID
        CeldaTabla(trabajador.id, 80.dp)

        // Apellidos
        CeldaTabla(trabajador.apellidosCompletos(), 150.dp)

        // Nombres
        CeldaTabla(trabajador.nombresCompletos(), 150.dp)

        // Tipo de Documento
        CeldaTabla(trabajador.tipoDocumento, 100.dp)

        // Número de Documento
        CeldaTabla(trabajador.numeroDocumento, 120.dp)

        // Rol
        CeldaTabla(trabajador.rol, 120.dp)

        // Obra
        CeldaTabla(trabajador.obraAsignada, 200.dp)

        // Biometría
        Box(
            modifier = Modifier
                .width(100.dp)
                .padding(horizontal = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = if (trabajador.biometriaRegistrada)
                            Color(0xFF4CAF50) else Color(0xFFF44336),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = if (trabajador.biometriaRegistrada) "Sí" else "No",
                    fontSize = 11.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Acciones
        Box(
            modifier = Modifier
                .width(120.dp)
                .padding(horizontal = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón Ver
                IconButton(
                    onClick = onVerPerfil,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_menu_view),
                        contentDescription = "Ver",
                        tint = colorPrimario,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Botón Editar
                IconButton(
                    onClick = onEditar,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_menu_edit),
                        contentDescription = "Editar",
                        tint = Color(0xFFFF9800),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CeldaTabla(texto: String, ancho: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .width(ancho)
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = texto,
            fontSize = 12.sp,
            color = Color.Black,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}