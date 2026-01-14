package com.mega.appcheckinout.screens.inspector_sst

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.ui.theme.BotonVolver
import com.mega.appcheckinout.data.DatosEjemplo
import com.mega.appcheckinout.models.TrabajadorCompleto
import com.mega.appcheckinout.tarjetas.TarjetaTrabajador
import com.mega.appcheckinout.screens.detalle.componentes.sst.ModalAsignarActividad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionPersonalSSTScreen(
    obraAsignada: String,
    onVolver: () -> Unit,
    onVerPerfil: (TrabajadorCompleto) -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    var tabSeleccionada by remember { mutableStateOf(0) }
    val tabs = listOf("Personal", "Asignar Actividad", "Asignar Subcargo")

    val trabajadoresObra = remember {
        DatosEjemplo.getTrabajadoresPorObra("1")
    }

    // Estados para asignación
    var textoBusqueda by remember { mutableStateOf("") }
    var trabajadorSeleccionado by remember { mutableStateOf<TrabajadorCompleto?>(null) }
    var nuevaActividad by remember { mutableStateOf("") }
    var nuevoSubCargo by remember { mutableStateOf("") }
    var mostrarModalActividad by remember { mutableStateOf(false) }
    var mostrarModalSubCargo by remember { mutableStateOf(false) }

    val trabajadoresFiltrados = trabajadoresObra.filter {
        it.nombreCompleto().contains(textoBusqueda, ignoreCase = true) ||
                it.numeroDocumento.contains(textoBusqueda)
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
                text = "GESTIÓN DE PERSONAL",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        // Tabs
        TabRow(
            selectedTabIndex = tabSeleccionada,
            containerColor = Color.White,
            contentColor = colorPrimario
        ) {
            tabs.forEachIndexed { index, titulo ->
                Tab(
                    selected = tabSeleccionada == index,
                    onClick = { tabSeleccionada = index },
                    text = {
                        Text(
                            titulo,
                            color = if (tabSeleccionada == index)
                                colorPrimario
                            else
                                Color.Gray
                        )
                    }
                )
            }
        }

        // Contenido según tab
        when (tabSeleccionada) {
            0 -> {
                // Lista de Personal
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Buscador
                    OutlinedTextField(
                        value = textoBusqueda,
                        onValueChange = { textoBusqueda = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        placeholder = { Text("Buscar por nombre o cédula...") },
                        leadingIcon = {
                            Icon(Icons.Default.Search, null)
                        },
                        singleLine = true
                    )

                    Text(
                        text = "${trabajadoresFiltrados.size} trabajador(es)",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(trabajadoresFiltrados) { trabajador ->
                            TarjetaTrabajador(
                                trabajador = trabajador,
                                colorPrimario = colorPrimario,
                                colorSecundario = colorSecundario,
                                menuExpanded = false,
                                onMenuExpandir = {},
                                onVerPerfil = { onVerPerfil(trabajador) },
                                onEditar = {},
                                onEliminar = {},
                                onAsignarObra = {}
                            )
                        }
                    }
                }
            }
            1 -> {
                // Asignar Actividad
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "ASIGNAR ACTIVIDAD",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario
                    )

                    // Buscador de trabajador
                    OutlinedTextField(
                        value = textoBusqueda,
                        onValueChange = { textoBusqueda = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Buscar trabajador...") },
                        leadingIcon = {
                            Icon(Icons.Default.Search, null)
                        },
                        singleLine = true
                    )

                    if (textoBusqueda.isNotEmpty() && trabajadoresFiltrados.isNotEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {
                            Column {
                                trabajadoresFiltrados.forEach { trabajador ->
                                    TextButton(
                                        onClick = {
                                            trabajadorSeleccionado = trabajador
                                            mostrarModalActividad = true
                                            textoBusqueda = ""
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = trabajador.nombreCompleto(),
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Start
                                        )
                                    }
                                    if (trabajador != trabajadoresFiltrados.last()) {
                                        Divider()
                                    }
                                }
                            }
                        }
                    }

                    if (trabajadorSeleccionado != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = colorSecundario.copy(alpha = 0.2f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "TRABAJADOR SELECCIONADO",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorPrimario
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = trabajadorSeleccionado!!.nombreCompleto(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Actividad actual: ${trabajadorSeleccionado!!.actividad.ifEmpty { "Sin asignar" }}",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
            2 -> {
                // Asignar Subcargo (similar a actividad)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "ASIGNAR SUBCARGO",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario
                    )

                    // Buscador de trabajador
                    OutlinedTextField(
                        value = textoBusqueda,
                        onValueChange = { textoBusqueda = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Buscar trabajador...") },
                        leadingIcon = {
                            Icon(Icons.Default.Search, null)
                        },
                        singleLine = true
                    )

                    if (textoBusqueda.isNotEmpty() && trabajadoresFiltrados.isNotEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {
                            Column {
                                trabajadoresFiltrados.forEach { trabajador ->
                                    TextButton(
                                        onClick = {
                                            trabajadorSeleccionado = trabajador
                                            mostrarModalSubCargo = true
                                            textoBusqueda = ""
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = trabajador.nombreCompleto(),
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Start
                                        )
                                    }
                                    if (trabajador != trabajadoresFiltrados.last()) {
                                        Divider()
                                    }
                                }
                            }
                        }
                    }

                    if (trabajadorSeleccionado != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = colorSecundario.copy(alpha = 0.2f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "TRABAJADOR SELECCIONADO",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorPrimario
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = trabajadorSeleccionado!!.nombreCompleto(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Subcargo actual: ${trabajadorSeleccionado!!.subCargo.ifEmpty { "Sin asignar" }}",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal Asignar Actividad
    if (mostrarModalActividad && trabajadorSeleccionado != null) {
        ModalAsignarActividad(
            trabajador = trabajadorSeleccionado!!,
            onDismiss = {
                mostrarModalActividad = false
                trabajadorSeleccionado = null
            },
            onConfirmar = { actividad ->
                // TODO: Guardar cambio en BD
                mostrarModalActividad = false
                trabajadorSeleccionado = null
            },
            colorPrimario = colorPrimario
        )
    }

    // Modal Asignar Subcargo (similar a actividad)
    if (mostrarModalSubCargo && trabajadorSeleccionado != null) {
        var subCargoSeleccionado by remember {
            mutableStateOf(trabajadorSeleccionado!!.subCargo)
        }
        var expandido by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = {
                mostrarModalSubCargo = false
                trabajadorSeleccionado = null
            },
            title = {
                Text(
                    "Asignar Subcargo",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = trabajadorSeleccionado!!.nombreCompleto(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Subcargo actual: ${trabajadorSeleccionado!!.subCargo.ifEmpty { "Sin asignar" }}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    ExposedDropdownMenuBox(
                        expanded = expandido,
                        onExpandedChange = { expandido = it }
                    ) {
                        OutlinedTextField(
                            value = subCargoSeleccionado,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Nuevo subcargo") },
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
                            DatosEjemplo.subCargos.forEach { subcargo ->
                                DropdownMenuItem(
                                    text = { Text(subcargo) },
                                    onClick = {
                                        subCargoSeleccionado = subcargo
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
                    onClick = {
                        // TODO: Guardar cambio
                        mostrarModalSubCargo = false
                        trabajadorSeleccionado = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorPrimario
                    )
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    mostrarModalSubCargo = false
                    trabajadorSeleccionado = null
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}