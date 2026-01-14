package com.mega.appcheckinout.screens.inspector_sst

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.mega.appcheckinout.models.*
import com.mega.appcheckinout.screens.detalle.componentes.sst.*

@Composable
fun GestionNovedadesScreen(
    obraAsignada: String,
    onVolver: () -> Unit,
    onVerDetalle: (Novedad) -> Unit = {},
    colorPrimario: Color,
    colorSecundario: Color
) {
    var tabSeleccionada by remember { mutableStateOf(0) }
    val tabs = listOf("Crear Novedad", "Pendientes", "Historial")

    // Novedades de la obra (filtradas)
    val novedadesObra = remember {
        DatosEjemplo.novedades.filter { it.obraId == "1" }
    }

    val novedadesPendientes = novedadesObra.filter {
        it.estado == EstadoNovedad.PENDIENTE
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
                text = "GESTIÓN DE NOVEDADES",
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
                // Crear Novedad
                FormularioNovedad(
                    trabajadoresObra = DatosEjemplo.getTrabajadoresPorObra("1"),
                    onGuardar = { novedad ->
                        // TODO: Guardar en BD como PENDIENTE
                        // Por ahora solo mostramos confirmación
                    },
                    colorPrimario = colorPrimario
                )
            }
            1 -> {
                // Novedades Pendientes
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "${novedadesPendientes.size} novedad(es) pendiente(s)",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    if (novedadesPendientes.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No hay novedades pendientes",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(novedadesPendientes) { novedad ->
                                TarjetaNovedadSST(
                                    novedad = novedad,
                                    onClick = { onVerDetalle(novedad) },
                                    colorPrimario = colorPrimario
                                )
                            }
                        }
                    }
                }
            }
            2 -> {
                // Historial
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "${novedadesObra.size} novedad(es) total(es)",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(novedadesObra) { novedad ->
                            TarjetaNovedadSST(
                                novedad = novedad,
                                onClick = { onVerDetalle(novedad) },
                                colorPrimario = colorPrimario
                            )
                        }
                    }
                }
            }
        }
    }
}