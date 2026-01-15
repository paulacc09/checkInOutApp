// screens/detalle/componentes/encargado/ResumenObraEncargado.kt
package com.mega.appcheckinout.screens.detalle.componentes.encargado

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Tarjeta de resumen de obra para el Encargado
 * Muestra estadísticas clave de su obra asignada
 */
@Composable
fun ResumenObraEncargado(
    nombreObra: String,
    totalTrabajadores: Int,
    presentesHoy: Int,
    novedadesPendientes: Int,
    actividadesActivas: Int,
    onVerDetalles: () -> Unit,
    colorPrimario: Color
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
            // Encabezado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "MI OBRA",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = nombreObra,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario
                    )
                }
                IconButton(onClick = onVerDetalles) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Ver detalles",
                        tint = colorPrimario
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // Estadísticas en grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                EstadisticaItem(
                    icono = Icons.Default.Person,
                    valor = totalTrabajadores.toString(),
                    label = "Total Personal",
                    color = colorPrimario,
                    modifier = Modifier.weight(1f)
                )
                EstadisticaItem(
                    icono = Icons.Default.CheckCircle,
                    valor = presentesHoy.toString(),
                    label = "Presentes",
                    color = Color(0xFF4CAF50),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                EstadisticaItem(
                    icono = Icons.Default.Warning,
                    valor = novedadesPendientes.toString(),
                    label = "Novedades",
                    color = Color(0xFFFF9800),
                    modifier = Modifier.weight(1f)
                )
                EstadisticaItem(
                    icono = Icons.Default.Build,
                    valor = actividadesActivas.toString(),
                    label = "Actividades",
                    color = Color(0xFF2196F3),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun EstadisticaItem(
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    valor: String,
    label: String,
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
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = valor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = label,
                fontSize = 11.sp,
                color = color
            )
        }
    }
}