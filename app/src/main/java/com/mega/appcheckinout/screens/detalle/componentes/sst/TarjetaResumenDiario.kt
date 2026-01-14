package com.mega.appcheckinout.screens.detalle.componentes.sst

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TarjetaResumenDiario(
    totalTrabajadores: Int,
    presentes: Int,
    ausentes: Int,
    novedadesPendientes: Int,
    colorPrimario: Color = Color(0xFF4A6FA5),
    colorSecundario: Color = Color(0xFF8FB8C8)
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "RESUMEN DEL DÍA",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                EstadisticaResumen(
                    titulo = "Total",
                    valor = totalTrabajadores.toString(),
                    color = colorPrimario,
                    modifier = Modifier.weight(1f)
                )
                EstadisticaResumen(
                    titulo = "Presentes",
                    valor = presentes.toString(),
                    color = Color(0xFF4CAF50),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                EstadisticaResumen(
                    titulo = "Ausentes",
                    valor = ausentes.toString(),
                    color = Color(0xFFF44336),
                    modifier = Modifier.weight(1f)
                )
                EstadisticaResumen(
                    titulo = "Novedades",
                    valor = novedadesPendientes.toString(),
                    color = Color(0xFFFF9800),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun EstadisticaResumen(
    titulo: String,
    valor: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = valor,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = titulo,
                fontSize = 13.sp,
                color = color
            )
        }
    }
}