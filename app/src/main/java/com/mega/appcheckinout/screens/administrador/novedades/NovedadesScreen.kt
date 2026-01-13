package com.mega.appcheckinout.screens.administrador.novedades

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
fun NovedadesScreen(
    onVolver: () -> Unit,
    onCrearNovedad: () -> Unit = {},
    onVerDetalleNovedad: (Novedad) -> Unit = {},
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Estados
    var textoBusqueda by remember { mutableStateOf("") }
    var filtroEstado by remember { mutableStateOf<EstadoNovedad?>(null) }
    var filtroTipo by remember { mutableStateOf<TipoNovedad?>(null) }
    var mostrarFiltros by remember { mutableStateOf(false) }

    // Datos de ejemplo
    val novedades = remember { DatosEjemplo.novedades }

    // Novedades filtradas
    val novedadesFiltradas = novedades.filter { novedad ->
        val cumpleBusqueda = textoBusqueda.isEmpty() ||
                novedad.trabajadorNombre.contains(textoBusqueda, ignoreCase = true) ||
                novedad.descripcion.contains(textoBusqueda, ignoreCase = true)

        val cumpleEstado = filtroEstado == null || novedad.estado == filtroEstado
        val cumpleTipo = filtroTipo == null || novedad.tipoNovedad == filtroTipo

        cumpleBusqueda && cumpleEstado && cumpleTipo
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
                    text = "GESTIÓN DE NOVEDADES",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorPrimario,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
                placeholder = { Text("Buscar por trabajador o descripción...", fontSize = 14.sp) },
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

                        // Chips de estado
                        Text("Estado:", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            EstadoNovedad.values().forEach { estado ->
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

                        // Chips de tipo
                        Text("Tipo:", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            TipoNovedad.entries.forEach { tipo ->
                                FilterChip(
                                    selected = filtroTipo == tipo,
                                    onClick = {
                                        filtroTipo = if (filtroTipo == tipo) null else tipo
                                    },
                                    label = { Text(tipo.displayName, fontSize = 11.sp) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        TextButton(
                            onClick = {
                                filtroEstado = null
                                filtroTipo = null
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
                // Contador
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${novedadesFiltradas.size} novedad(es) encontrada(s)",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    // Estadísticas rápidas
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        EstadisticaChip(
                            label = "Pendientes",
                            count = novedades.count { it.estado == EstadoNovedad.PENDIENTE },
                            color = Color(0xFFFF9800)
                        )
                        EstadisticaChip(
                            label = "Aprobadas",
                            count = novedades.count { it.estado == EstadoNovedad.APROBADO },
                            color = Color(0xFF4CAF50)
                        )
                    }
                }

                // Lista de novedades
                if (novedadesFiltradas.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "No se encontraron novedades",
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
                        items(novedadesFiltradas) { novedad ->
                            TarjetaNovedad(
                                novedad = novedad,
                                onClick = { onVerDetalleNovedad(novedad) },
                                colorPrimario = colorPrimario
                            )
                        }

                        item { Spacer(modifier = Modifier.height(80.dp)) }
                    }
                }
            }

            // FAB para crear novedad
            FloatingActionButton(
                onClick = onCrearNovedad,
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
                        text = "Nueva Novedad",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun TarjetaNovedad(
    novedad: Novedad,
    onClick: () -> Unit,
    colorPrimario: Color
) {
    val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Encabezado: Tipo y Estado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = when(novedad.tipoNovedad) {
                            TipoNovedad.INCAPACIDAD_ARL, TipoNovedad.INCAPACIDAD_EPS -> Icons.Default.Warning
                            TipoNovedad.PERMISO -> Icons.Default.Check
                            TipoNovedad.LICENCIA -> Icons.Default.DateRange
                            else -> Icons.Default.Info
                        },
                        contentDescription = null,
                        tint = colorPrimario,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = novedad.tipoNovedad.displayName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario
                    )
                }

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = when(novedad.estado) {
                            EstadoNovedad.PENDIENTE -> Color(0xFFFF9800)
                            EstadoNovedad.APROBADO -> Color(0xFF4CAF50)
                            EstadoNovedad.RECHAZADO -> Color(0xFFF44336)
                            EstadoNovedad.INFORMACION_ADICIONAL -> Color(0xFF2196F3)
                        }
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = novedad.estado.displayName.uppercase(),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 10.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Trabajador
            Text(
                text = novedad.trabajadorNombre,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Descripción
            Text(
                text = novedad.descripcion,
                fontSize = 13.sp,
                color = Color.Gray,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Fechas y evidencias
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        tint = colorPrimario,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "${novedad.fechaInicio} - ${novedad.fechaFin}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                if (novedad.evidencias.isNotEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${novedad.evidencias.size} evidencia(s)",
                            fontSize = 12.sp,
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Creado por
            Text(
                text = "Creado por: ${novedad.creadoPor}",
                fontSize = 11.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun EstadisticaChip(
    label: String,
    count: Int,
    color: Color
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = color.copy(alpha = 0.2f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = count.toString(),
                fontSize = 14.sp,
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