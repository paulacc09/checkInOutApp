// screens/encargado/personal/GestionPersonalEncargadoScreen.kt
package com.mega.appcheckinout.screens.encargado.personal

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.ui.theme.BotonVolver
import com.mega.appcheckinout.data.DatosEjemplo
import com.mega.appcheckinout.models.TrabajadorCompleto
import com.mega.appcheckinout.screens.detalle.componentes.encargado.AlertaPermisoLimitado
import com.mega.appcheckinout.screens.detalle.componentes.encargado.TipoAlerta
import com.mega.appcheckinout.screens.detalle.componentes.encargado.TarjetaTrabajadorEncargado

/**
 * Pantalla de Gestión de Personal para Encargado
 *
 * RESTRICCIONES:
 * - Solo puede ver trabajadores de SU obra asignada
 * - Solo consulta de perfiles (sin edición ni eliminación)
 * - Puede filtrar pero no modificar información
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionPersonalEncargadoScreen(
    obraAsignada: String,
    obraId: String = "1",
    onVolver: () -> Unit,
    onVerPerfil: (TrabajadorCompleto) -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    var textoBusqueda by remember { mutableStateOf("") }
    var filtroRol by remember { mutableStateOf("Todos") }
    var expandirFiltroRol by remember { mutableStateOf(false) }
    var filtroEstado by remember { mutableStateOf("Todos") }
    var expandirFiltroEstado by remember { mutableStateOf(false) }

    val roles = listOf("Todos", "Operativo", "Inspector SST", "Encargado")
    val estados = listOf("Todos", "Activo", "Inactivo")

    // Trabajadores de la obra
    val trabajadoresObra = remember {
        DatosEjemplo.getTrabajadoresPorObra(obraId)
    }

    // Aplicar filtros
    val trabajadoresFiltrados = trabajadoresObra.filter { trabajador ->
        val coincideBusqueda = trabajador.nombreCompleto()
            .contains(textoBusqueda, ignoreCase = true) ||
                trabajador.numeroDocumento.contains(textoBusqueda)

        val coincideRol = filtroRol == "Todos" || trabajador.rol == filtroRol
        val coincideEstado = filtroEstado == "Todos" || trabajador.estado == filtroEstado

        coincideBusqueda && coincideRol && coincideEstado
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
                text = "PERSONAL DE MI OBRA",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Alerta de permisos
            AlertaPermisoLimitado(
                titulo = "Solo Consulta",
                mensaje = "Puedes ver el personal de tu obra pero no modificar sus datos. Para cambios contacta al Administrador.",
                tipo = TipoAlerta.INFO
            )

            // Buscador
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar por nombre o cédula...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, null)
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario
                )
            )

            // Filtros
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Filtro por rol
                ExposedDropdownMenuBox(
                    expanded = expandirFiltroRol,
                    onExpandedChange = { expandirFiltroRol = it },
                    modifier = Modifier.weight(1f)
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
                                text = { Text(rol) },
                                onClick = {
                                    filtroRol = rol
                                    expandirFiltroRol = false
                                }
                            )
                        }
                    }
                }

                // Filtro por estado
                ExposedDropdownMenuBox(
                    expanded = expandirFiltroEstado,
                    onExpandedChange = { expandirFiltroEstado = it },
                    modifier = Modifier.weight(1f)
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
                                text = { Text(estado) },
                                onClick = {
                                    filtroEstado = estado
                                    expandirFiltroEstado = false
                                }
                            )
                        }
                    }
                }
            }

            // Contador de resultados
            Text(
                text = "${trabajadoresFiltrados.size} trabajador(es) de ${trabajadoresObra.size} total(es)",
                fontSize = 14.sp,
                color = Color.Gray
            )

            // Lista de trabajadores
            if (trabajadoresFiltrados.isEmpty()) {
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
                            tint = Color.Gray
                        )
                        Text(
                            text = "No se encontraron trabajadores",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                        Text(
                            text = "Intenta ajustar los filtros de búsqueda",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(trabajadoresFiltrados) { trabajador ->
                        TarjetaTrabajadorEncargado(
                            trabajador = trabajador,
                            onVerPerfil = { onVerPerfil(trabajador) },
                            colorPrimario = colorPrimario
                        )
                    }
                }
            }
        }
    }
}