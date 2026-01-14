package com.mega.appcheckinout.screens.detalle.componentes.sst

import androidx.compose.foundation.clickable
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
import com.mega.appcheckinout.models.*

@Composable
fun TarjetaNovedadSST(
    novedad: Novedad,
    onClick: () -> Unit,
    colorPrimario: Color = Color(0xFF4A6FA5)
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Encabezado
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
                            TipoNovedad.INCAPACIDAD_ARL,
                            TipoNovedad.INCAPACIDAD_EPS -> Icons.Default.Warning
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
                        fontSize = 13.sp,
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
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = novedad.estado.displayName,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
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
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
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

            // Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        tint = colorPrimario,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "${novedad.fechaInicio} - ${novedad.fechaFin}",
                        fontSize = 11.sp,
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
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "${novedad.evidencias.size} evidencia(s)",
                            fontSize = 11.sp,
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}