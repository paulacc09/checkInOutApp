package com.mega.appcheckinout.screens.administrador.gestion_obras

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
import com.mega.appcheckinout.data.DatosEjemplo


@Composable
fun ObrasActivasScreen(
    onVolver: () -> Unit,
    onVerDetalleObra: (Obra) -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Datos de ejemplo - solo obras activas
    val obrasActivas = remember { DatosEjemplo.getObrasActivas() }

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