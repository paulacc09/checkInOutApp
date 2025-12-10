package com.mega.appcheckinout.screens

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
import com.mega.appcheckinout.models.TrabajadorCompleto
import com.mega.appcheckinout.ui.theme.BotonVolver


/**
 * Pantalla de Asignaciones Activas
 *
 * Funcionalidades:
 * - Muestra las obras con trabajadores asignados
 * - Permite ver los trabajadores por obra
 * - Cambiar asignación de obra
 * - Finalizar asignación
 * - Registrar asistencia
 * - Filtrar por obra, rol o estado
 * - Asignar nuevo trabajador a una obra
 */

data class AsignacionObra(
    val obraId: String,
    val nombreObra: String,
    val ubicacion: String,
    val trabajadoresAsignados: List<TrabajadorCompleto>,
    val fechaInicio: String,
    val estado: String = "Activa"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignacionesActivasScreen(
    onVolver: () -> Unit,
    onVerPerfil: (TrabajadorCompleto) -> Unit,
    onAsignarNuevoTrabajador: (String) -> Unit, // obraId
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Estados
    var filtroObra by remember { mutableStateOf("Todas") }
    var filtroRol by remember { mutableStateOf("Todos") }
    var filtroEstado by remember { mutableStateOf("Todos") }
    var expandirFiltroObra by remember { mutableStateOf(false) }
    var expandirFiltroRol by remember { mutableStateOf(false) }
    var expandirFiltroEstado by remember { mutableStateOf(false) }
    var mostrarFiltros by remember { mutableStateOf(false) }
    var vistaAgrupada by remember { mutableStateOf(true) } // true = agrupada por obra, false = lista

    // Diálogos
    var trabajadorCambiarObra by remember { mutableStateOf<TrabajadorCompleto?>(null) }
    var trabajadorFinalizarAsignacion by remember { mutableStateOf<TrabajadorCompleto?>(null) }
    var trabajadorRegistrarAsistencia by remember { mutableStateOf<TrabajadorCompleto?>(null) }

    // Datos de ejemplo - Obras con trabajadores asignados
    val asignaciones = remember {
        listOf(
            AsignacionObra(
                obraId = "1",
                nombreObra = "Mandarino - Ibagué",
                ubicacion = "Ibagué, Tolima",
                fechaInicio = "01/01/2024",
                trabajadoresAsignados = listOf(
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
                        estado = "Activo"
                    )
                )
            ),
            AsignacionObra(
                obraId = "2",
                nombreObra = "Bosque robledal - Rionegro",
                ubicacion = "Rionegro, Antioquia",
                fechaInicio = "10/02/2024",
                trabajadoresAsignados = listOf(
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
            ),
            AsignacionObra(
                obraId = "3",
                nombreObra = "Hacienda Nakare - Villavicencio",
                ubicacion = "Villavicencio, Meta",
                fechaInicio = "05/04/2024",
                trabajadoresAsignados = listOf(
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
                    )
                )
            ),
            AsignacionObra(
                obraId = "4",
                nombreObra = "Pomelo - Bogotá",
                ubicacion = "Bogotá D.C.",
                fechaInicio = "20/04/2024",
                trabajadoresAsignados = listOf(
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
                    )
                )
            )
        )
    }

    // Obtener lista de obras para filtros
    val obras = listOf("Todas") + asignaciones.map { it.nombreObra }
    val roles = listOf("Todos", "Operativo", "Inspector SST", "Encargado")
    val estados = listOf("Todos", "Activo", "Inactivo")

    // Filtrar asignaciones
    val asignacionesFiltradas = asignaciones.filter { asignacion ->
        val coincideObra = filtroObra == "Todas" || asignacion.nombreObra == filtroObra

        // Si se filtra por rol, verificar que al menos un trabajador tenga ese rol
        val coincideRol = if (filtroRol == "Todos") {
            true
        } else {
            asignacion.trabajadoresAsignados.any { it.rol == filtroRol }
        }

        val coincideEstado = filtroEstado == "Todos" || asignacion.estado == filtroEstado

        coincideObra && coincideRol && coincideEstado
    }.map { asignacion ->
        // Si hay filtro de rol, filtrar también los trabajadores
        if (filtroRol != "Todos") {
            asignacion.copy(
                trabajadoresAsignados = asignacion.trabajadoresAsignados.filter { it.rol == filtroRol }
            )
        } else {
            asignacion
        }
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
            // Barra superior
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
                    text = "ASIGNACIONES ACTIVAS",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorPrimario,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Botón para actualizar - REEMPLAZADO CON MATERIAL ICON
                    IconButton(onClick = { /* TODO: Actualizar datos */ }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Actualizar",
                            tint = colorPrimario
                        )
                    }

                    // Botón alternar vista - REEMPLAZADO CON MATERIAL ICONS
                    IconButton(onClick = { vistaAgrupada = !vistaAgrupada }) {
                        Icon(
                            imageVector = if (vistaAgrupada) Icons.Default.List else Icons.Default.AccountBox,
                            contentDescription = "Cambiar vista",
                            tint = colorPrimario
                        )
                    }

                    // Botón filtros - REEMPLAZADO CON MATERIAL ICON
                    IconButton(onClick = { mostrarFiltros = !mostrarFiltros }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Filtros",
                            tint = if (mostrarFiltros) Color(0xFF2196F3) else colorPrimario
                        )
                    }
                }
            }

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

                        // Filtro por Estado
                        ExposedDropdownMenuBox(
                            expanded = expandirFiltroEstado,
                            onExpandedChange = { expandirFiltroEstado = it }
                        ) {
                            OutlinedTextField(
                                value = filtroEstado,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Estado", fontSize = 14.sp) },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirFiltroEstado)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colorPrimario
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandirFiltroEstado,
                                onDismissRequest = { expandirFiltroEstado = false }
                            ) {
                                estados.forEach { estado ->
                                    DropdownMenuItem(
                                        text = { Text(estado, fontSize = 14.sp) },
                                        onClick = {
                                            filtroEstado = estado
                                            expandirFiltroEstado = false
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
                                filtroEstado = "Todos"
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (vistaAgrupada) {
                // Vista agrupada por obra
                items(asignacionesFiltradas) { asignacion ->
                    TarjetaObraConTrabajadores(
                        asignacion = asignacion,
                        colorPrimario = colorPrimario,
                        colorSecundario = colorSecundario,
                        onVerPerfil = onVerPerfil,
                        onCambiarObra = { trabajadorCambiarObra = it },
                        onFinalizarAsignacion = { trabajadorFinalizarAsignacion = it },
                        onRegistrarAsistencia = { trabajadorRegistrarAsistencia = it },
                        onAsignarNuevo = { onAsignarNuevoTrabajador(asignacion.obraId) }
                    )
                }
            } else {
                // Vista de lista simple (todos los trabajadores)
                val todosTrabajadores = asignacionesFiltradas.flatMap { it.trabajadoresAsignados }
                items(todosTrabajadores) { trabajador ->
                    TarjetaTrabajadorAsignado(
                        trabajador = trabajador,
                        colorPrimario = colorPrimario,
                        colorSecundario = colorSecundario,
                        onVerPerfil = { onVerPerfil(trabajador) },
                        onCambiarObra = { trabajadorCambiarObra = trabajador },
                        onFinalizarAsignacion = { trabajadorFinalizarAsignacion = trabajador },
                        onRegistrarAsistencia = { trabajadorRegistrarAsistencia = trabajador }
                    )
                }
            }

            // Espacio al final
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }

    // Diálogos
    if (trabajadorCambiarObra != null) {
        DialogoCambiarObra(
            trabajador = trabajadorCambiarObra!!,
            obrasDisponibles = obras.filter { it != "Todas" && it != trabajadorCambiarObra!!.obraAsignada },
            onConfirmar = { nuevaObra ->
                // TODO: Implementar cambio de obra
                trabajadorCambiarObra = null
            },
            onDismiss = { trabajadorCambiarObra = null },
            colorPrimario = colorPrimario
        )
    }

    if (trabajadorFinalizarAsignacion != null) {
        DialogoFinalizarAsignacion(
            trabajador = trabajadorFinalizarAsignacion!!,
            onConfirmar = {
                // TODO: Implementar finalizar asignación
                trabajadorFinalizarAsignacion = null
            },
            onDismiss = { trabajadorFinalizarAsignacion = null }
        )
    }

    if (trabajadorRegistrarAsistencia != null) {
        DialogoRegistrarAsistencia(
            trabajador = trabajadorRegistrarAsistencia!!,
            onConfirmar = {
                // TODO: Implementar registrar asistencia
                trabajadorRegistrarAsistencia = null
            },
            onDismiss = { trabajadorRegistrarAsistencia = null },
            colorPrimario = colorPrimario
        )
    }
}

// ========== COMPONENTES ==========

@Composable
private fun TarjetaObraConTrabajadores(
    asignacion: AsignacionObra,
    colorPrimario: Color,
    colorSecundario: Color,
    onVerPerfil: (TrabajadorCompleto) -> Unit,
    onCambiarObra: (TrabajadorCompleto) -> Unit,
    onFinalizarAsignacion: (TrabajadorCompleto) -> Unit,
    onRegistrarAsistencia: (TrabajadorCompleto) -> Unit,
    onAsignarNuevo: () -> Unit
) {
    var expandido by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            // Cabecera de la obra
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorSecundario.copy(alpha = 0.3f))
                    .clickable { expandido = !expandido }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = asignacion.nombreObra,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = asignacion.ubicacion,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${asignacion.trabajadoresAsignados.size} trabajador(es) asignado(s)",
                        fontSize = 12.sp,
                        color = colorPrimario,
                        fontWeight = FontWeight.Bold
                    )
                }

                Icon(
                    imageVector = if (expandido) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expandido) "Contraer" else "Expandir",
                    tint = colorPrimario
                )
            }

            // Lista de trabajadores (expandible)
            if (expandido) {
                Column(modifier = Modifier.padding(16.dp)) {
                    asignacion.trabajadoresAsignados.forEachIndexed { index, trabajador ->
                        if (index > 0) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(color = Color.LightGray, thickness = 0.5.dp)
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        ItemTrabajadorEnObra(
                            trabajador = trabajador,
                            colorPrimario = colorPrimario,
                            onVerPerfil = { onVerPerfil(trabajador) },
                            onCambiarObra = { onCambiarObra(trabajador) },
                            onFinalizarAsignacion = { onFinalizarAsignacion(trabajador) },
                            onRegistrarAsistencia = { onRegistrarAsistencia(trabajador) }
                        )
                    }

                    // Botón asignar nuevo trabajador
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onAsignarNuevo,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = colorPrimario.copy(alpha = 0.8f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Asignar nuevo trabajador", fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemTrabajadorEnObra(
    trabajador: TrabajadorCompleto,
    colorPrimario: Color,
    onVerPerfil: () -> Unit,
    onCambiarObra: () -> Unit,
    onFinalizarAsignacion: () -> Unit,
    onRegistrarAsistencia: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = trabajador.nombreCompleto(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = trabajador.rol,
                    fontSize = 12.sp,
                    color = colorPrimario
                )
                if (trabajador.subCargo.isNotEmpty()) {
                    Text(
                        text = "• ${trabajador.subCargo}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Box {
            IconButton(onClick = { menuExpanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Más opciones",
                    tint = colorPrimario
                )
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Ver perfil") },
                    onClick = {
                        menuExpanded = false
                        onVerPerfil()
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    }
                )
                DropdownMenuItem(
                    text = { Text("Cambiar obra") },
                    onClick = {
                        menuExpanded = false
                        onCambiarObra()
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null
                        )
                    }
                )
                DropdownMenuItem(
                    text = { Text("Finalizar asignación") },
                    onClick = {
                        menuExpanded = false
                        onFinalizarAsignacion()
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null
                        )
                    }
                )
                DropdownMenuItem(
                    text = { Text("Registrar asistencia") },
                    onClick = {
                        menuExpanded = false
                        onRegistrarAsistencia()
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TarjetaTrabajadorAsignado(
    trabajador: TrabajadorCompleto,
    colorPrimario: Color,
    colorSecundario: Color,
    onVerPerfil: () -> Unit,
    onCambiarObra: () -> Unit,
    onFinalizarAsignacion: () -> Unit,
    onRegistrarAsistencia: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = trabajador.nombreCompleto(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = trabajador.rol,
                    fontSize = 12.sp,
                    color = colorPrimario
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = Color.Gray
                    )
                    Text(
                        text = trabajador.obraAsignada,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }

            Box {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Más opciones",
                        tint = colorPrimario
                    )
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Ver perfil") },
                        onClick = {
                            menuExpanded = false
                            onVerPerfil()
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null
                            )
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Cambiar obra") },
                        onClick = {
                            menuExpanded = false
                            onCambiarObra()
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null
                            )
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Finalizar asignación") },
                        onClick = {
                            menuExpanded = false
                            onFinalizarAsignacion()
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null
                            )
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Registrar asistencia") },
                        onClick = {
                            menuExpanded = false
                            onRegistrarAsistencia()
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    }
}

// ========== DIÁLOGOS ==========

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DialogoCambiarObra(
    trabajador: TrabajadorCompleto,
    obrasDisponibles: List<String>,
    onConfirmar: (String) -> Unit,
    onDismiss: () -> Unit,
    colorPrimario: Color
) {
    var obraSeleccionada by remember { mutableStateOf("") }
    var expandido by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Cambiar obra", fontWeight = FontWeight.Bold)
        },
        text = {
            Column {
                Text(
                    text = "Trabajador: ${trabajador.nombreCompleto()}",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Obra actual: ${trabajador.obraAsignada}",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = expandido,
                    onExpandedChange = { expandido = it }
                ) {
                    OutlinedTextField(
                        value = obraSeleccionada,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Nueva obra", fontSize = 14.sp) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorPrimario
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandido,
                        onDismissRequest = { expandido = false }
                    ) {
                        obrasDisponibles.forEach { obra ->
                            DropdownMenuItem(
                                text = { Text(obra, fontSize = 14.sp) },
                                onClick = {
                                    obraSeleccionada = obra
                                    expandido = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirmar(obraSeleccionada) },
                enabled = obraSeleccionada.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(containerColor = colorPrimario)
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = Color.Gray)
            }
        }
    )
}

@Composable
private fun DialogoFinalizarAsignacion(
    trabajador: TrabajadorCompleto,
    onConfirmar: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Finalizar asignación", fontWeight = FontWeight.Bold)
        },
        text = {
            Column {
                Text(
                    text = "¿Está seguro de finalizar la asignación de:",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = trabajador.nombreCompleto(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Obra: ${trabajador.obraAsignada}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Esta acción no se puede deshacer.",
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirmar,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Finalizar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = Color.Gray)
            }
        }
    )
}

@Composable
private fun DialogoRegistrarAsistencia(
    trabajador: TrabajadorCompleto,
    onConfirmar: () -> Unit,
    onDismiss: () -> Unit,
    colorPrimario: Color
) {
    var horaEntrada by remember { mutableStateOf("") }
    var horaSalida by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Registrar asistencia", fontWeight = FontWeight.Bold)
        },
        text = {
            Column {
                Text(
                    text = trabajador.nombreCompleto(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = trabajador.obraAsignada,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = horaEntrada,
                    onValueChange = { horaEntrada = it },
                    label = { Text("Hora de entrada", fontSize = 14.sp) },
                    placeholder = { Text("HH:MM", fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorPrimario
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = horaSalida,
                    onValueChange = { horaSalida = it },
                    label = { Text("Hora de salida", fontSize = 14.sp) },
                    placeholder = { Text("HH:MM", fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorPrimario
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = observaciones,
                    onValueChange = { observaciones = it },
                    label = { Text("Observaciones", fontSize = 14.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorPrimario
                    ),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirmar,
                enabled = horaEntrada.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(containerColor = colorPrimario)
            ) {
                Text("Registrar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = Color.Gray)
            }
        }
    )
}