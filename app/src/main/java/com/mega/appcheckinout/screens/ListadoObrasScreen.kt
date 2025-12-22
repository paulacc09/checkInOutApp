package com.mega.appcheckinout.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.models.Obra
import com.mega.appcheckinout.ui.theme.BotonVolver

@Composable
fun ListadoObrasScreen(
    onVolver: () -> Unit,
    onCrearObra: () -> Unit,
    onVerDetalleObra: (Obra) -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Estado de búsqueda
    var textoBusqueda by remember { mutableStateOf("") }
    var mostrarFiltros by remember { mutableStateOf(false) }
    var filtroEstado by remember { mutableStateOf("Todas") } // Todas, Activas, Finalizadas
    var filtroCiudad by remember { mutableStateOf("Todas") }

    // Datos de ejemplo (TODO: Cargar desde base de datos)
    val obrasEjemplo = remember {
        listOf(
            Obra(
                id = "1",
                codigo = "MCJ-001",
                nombre = "Edificio Mandarino",
                ciudad = "Ibagué",
                direccion = "Calle 42 # 5-67",
                fechaInicio = "15/01/2025",
                fechaFinEstimada = "15/12/2025",
                responsableSISO = "María González",
                descripcion = "Construcción de edificio residencial de 8 pisos",
                estado = Obra.EstadoObra.ACTIVA,
                numeroTrabajadores = 45
            ),
            Obra(
                id = "2",
                codigo = "MCJ-002",
                nombre = "Conjunto Rionegro",
                ciudad = "Ibagué",
                direccion = "Carrera 3 # 12-34",
                fechaInicio = "10/02/2025",
                fechaFinEstimada = "10/08/2025",
                responsableSISO = "Carlos Méndez",
                descripcion = "Conjunto residencial 120 apartamentos",
                estado = Obra.EstadoObra.ACTIVA,
                numeroTrabajadores = 67
            ),
            Obra(
                id = "3",
                codigo = "MCJ-003",
                nombre = "Centro Comercial Plaza",
                ciudad = "Bogotá",
                direccion = "Avenida 68 # 45-12",
                fechaInicio = "01/06/2024",
                fechaFinEstimada = "30/12/2024",
                responsableSISO = "Ana Rodríguez",
                descripcion = "Centro comercial 3 pisos",
                estado = Obra.EstadoObra.FINALIZADA,
                numeroTrabajadores = 0
            ),
            Obra(
                id = "4",
                codigo = "MCJ-004",
                nombre = "Parque Industrial Norte",
                ciudad = "Medellín",
                direccion = "Km 5 Autopista Norte",
                fechaInicio = "20/11/2024",
                fechaFinEstimada = "20/05/2025",
                responsableSISO = "Pedro Salazar",
                descripcion = "Bodegas industriales",
                estado = Obra.EstadoObra.ACTIVA,
                numeroTrabajadores = 32
            ),
            Obra(
                id = "5",
                codigo = "MCJ-005",
                nombre = "Torres del Bosque",
                ciudad = "Ibagué",
                direccion = "Calle 60 # 8-90",
                fechaInicio = "15/03/2024",
                fechaFinEstimada = "15/11/2024",
                responsableSISO = "Laura Jiménez",
                descripcion = "Dos torres residenciales",
                estado = Obra.EstadoObra.FINALIZADA,
                numeroTrabajadores = 0
            )
        )
    }

    // Filtrar obras según búsqueda y filtros
    val obrasFiltradas = remember(textoBusqueda, filtroEstado, filtroCiudad) {
        obrasEjemplo.filter { obra ->
            val cumpleBusqueda = obra.nombre.contains(textoBusqueda, ignoreCase = true) ||
                    obra.codigo.contains(textoBusqueda, ignoreCase = true) ||
                    obra.ciudad.contains(textoBusqueda, ignoreCase = true)

            val cumpleEstado = when (filtroEstado) {
                "Activas" -> obra.estaActiva()
                "Finalizadas" -> obra.estaFinalizada()
                else -> true
            }

            val cumpleCiudad = if (filtroCiudad == "Todas") true else obra.ciudad == filtroCiudad

            cumpleBusqueda && cumpleEstado && cumpleCiudad
        }
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
                text = "LISTADO DE OBRAS",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        // Barra de búsqueda y filtros
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            // Búsqueda
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar por nombre, código o ciudad") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = colorPrimario
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Botón de filtros
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = mostrarFiltros,
                    onClick = { mostrarFiltros = !mostrarFiltros },
                    label = { Text("Filtros") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = colorSecundario
                    )
                )

                FilterChip(
                    selected = filtroEstado == "Activas",
                    onClick = {
                        filtroEstado = if (filtroEstado == "Activas") "Todas" else "Activas"
                    },
                    label = { Text("Activas") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF4CAF50)
                    )
                )

                FilterChip(
                    selected = filtroEstado == "Finalizadas",
                    onClick = {
                        filtroEstado = if (filtroEstado == "Finalizadas") "Todas" else "Finalizadas"
                    },
                    label = { Text("Finalizadas") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF9E9E9E)
                    )
                )
            }

            // Panel de filtros expandible
            if (mostrarFiltros) {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Filtrar por ciudad",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        val ciudades = listOf("Todas", "Ibagué", "Bogotá", "Medellín")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ciudades.forEach { ciudad ->
                                FilterChip(
                                    selected = filtroCiudad == ciudad,
                                    onClick = { filtroCiudad = ciudad },
                                    label = { Text(ciudad, fontSize = 12.sp) }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Contador de resultados
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${obrasFiltradas.size} obras encontradas",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        // Lista de obras
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(obrasFiltradas) { obra ->
                TarjetaObra(
                    obra = obra,
                    onClick = { onVerDetalleObra(obra) },
                    colorPrimario = colorPrimario,
                    colorSecundario = colorSecundario
                )
            }

            // Espaciado final
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        // Botón flotante para crear obra
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = onCrearObra,
                modifier = Modifier.padding(16.dp),
                containerColor = colorPrimario
            ) {
                Icon(
                    painter = androidx.compose.ui.res.painterResource(android.R.drawable.ic_input_add),
                    contentDescription = "Crear obra",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun TarjetaObra(
    obra: Obra,
    onClick: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Encabezado: Código y Estado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = obra.codigo,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorPrimario
                )

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (obra.estaActiva()) Color(0xFF4CAF50)
                        else Color(0xFF9E9E9E)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = if (obra.estaActiva()) "ACTIVA" else "FINALIZADA",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 10.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre de la obra
            Text(
                text = obra.nombre,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Información de ubicación
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = androidx.compose.ui.res.painterResource(android.R.drawable.ic_dialog_map),
                    contentDescription = "Ubicación",
                    tint = colorPrimario,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${obra.ciudad} • ${obra.direccion}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Fechas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Inicio",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = obra.fechaInicio,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF212121)
                    )
                }

                if (obra.fechaFinEstimada.isNotEmpty()) {
                    Column {
                        Text(
                            text = "Fin estimado",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = obra.fechaFinEstimada,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF212121)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Divider(color = Color(0xFFE0E0E0))

            Spacer(modifier = Modifier.height(12.dp))

            // Footer: Responsable y trabajadores
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Responsable SISO",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = obra.responsableSISO,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF212121)
                    )
                }

                if (obra.estaActiva()) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = colorSecundario)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = androidx.compose.ui.res.painterResource(android.R.drawable.ic_menu_myplaces),
                                contentDescription = "Trabajadores",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${obra.numeroTrabajadores}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}