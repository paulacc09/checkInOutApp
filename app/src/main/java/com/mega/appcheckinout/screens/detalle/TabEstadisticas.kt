package com.mega.appcheckinout.screens.detalle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.models.Obra
import com.mega.appcheckinout.screens.detalle.componentes.BarraDistribucion
import com.mega.appcheckinout.screens.detalle.componentes.EstadisticaCard
import com.mega.appcheckinout.screens.detalle.componentes.NovedadItem

@Composable
fun TabEstadisticas(
    obra: Obra,
    colorPrimario: Color,
    colorSecundario: Color
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Resumen general
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "RESUMEN",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorPrimario
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    EstadisticaCard(
                        titulo = "Trabajadores",
                        valor = obra.numeroTrabajadores.toString(),
                        color = colorPrimario
                    )
                    EstadisticaCard(
                        titulo = "Asistencia hoy",
                        valor = if (obra.estaActiva()) "85%" else "N/A",
                        color = Color(0xFF4CAF50)
                    )
                }
            }
        }

        // Distribución por cargo (si está activa)
        if (obra.estaActiva()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "DISTRIBUCIÓN POR CARGO",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    BarraDistribucion("Operarios", 35, 45, Color(0xFF2196F3))
                    Spacer(modifier = Modifier.height(8.dp))
                    BarraDistribucion("Inspectores SST", 8, 45, Color(0xFF4CAF50))
                    Spacer(modifier = Modifier.height(8.dp))
                    BarraDistribucion("Encargados", 2, 45, Color(0xFFFF9800))
                }
            }
        }

        // Novedades
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "NOVEDADES RECIENTES",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorPrimario
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (obra.estaActiva()) {
                    NovedadItem("3 incapacidades pendientes", Color(0xFFFFA726))
                    NovedadItem("2 permisos aprobados", Color(0xFF66BB6A))
                    NovedadItem("1 dotación pendiente", Color(0xFF42A5F5))
                } else {
                    Text(
                        text = "No hay novedades recientes",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}