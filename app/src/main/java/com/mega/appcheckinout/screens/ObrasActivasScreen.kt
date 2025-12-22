package com.mega.appcheckinout.screens

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.models.Obra
import com.mega.appcheckinout.ui.theme.BotonVolver

@Composable
fun ObrasActivasScreen(
    onVolver: () -> Unit,
    onVerDetalleObra: (Obra) -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Datos de ejemplo - solo obras activas
    val obrasActivas = remember {
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
            )
        )
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
                text = "OBRAS ACTIVAS",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        // Estadísticas rápidas
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                EstadisticaItem(
                    titulo = "Total obras",
                    valor = "${obrasActivas.size}",
                    color = colorPrimario
                )
                EstadisticaItem(
                    titulo = "Trabajadores",
                    valor = "${obrasActivas.sumOf { it.numeroTrabajadores }}",
                    color = Color(0xFF4CAF50)
                )
            }
        }

        // Lista de obras activas
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(obrasActivas) { obra ->
                TarjetaObra(
                    obra = obra,
                    onClick = { onVerDetalleObra(obra) },
                    colorPrimario = colorPrimario,
                    colorSecundario = colorSecundario
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun EstadisticaItem(
    titulo: String,
    valor: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = valor,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = titulo,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}