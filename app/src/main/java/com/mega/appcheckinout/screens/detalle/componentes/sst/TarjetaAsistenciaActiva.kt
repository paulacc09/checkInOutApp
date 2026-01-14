package com.mega.appcheckinout.screens.detalle.componentes.sst

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.screens.inspector_sst.RegistroAsistenciaTemp
import com.mega.appcheckinout.screens.inspector_sst.EstadoMarcaje

@Composable
fun TarjetaAsistenciaActiva(
    registro: RegistroAsistenciaTemp,
    onVerPerfil: () -> Unit,
    colorPrimario: Color = Color(0xFF4A6FA5)
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (registro.estado) {
                EstadoMarcaje.COMPLETADO -> Color(0xFF4CAF50).copy(alpha = 0.1f)
                EstadoMarcaje.INGRESADO -> Color(0xFF2196F3).copy(alpha = 0.1f)
                EstadoMarcaje.PENDIENTE -> Color.White
            }
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    color = colorPrimario.copy(alpha = 0.2f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = colorPrimario
                        )
                    }
                }

                Column {
                    Text(
                        text = registro.nombre,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "CC: ${registro.numeroDocumento}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    when (registro.estado) {
                        EstadoMarcaje.INGRESADO -> {
                            Text(
                                text = "Ingreso: ${registro.horaIngreso}",
                                fontSize = 12.sp,
                                color = Color(0xFF2196F3),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        EstadoMarcaje.COMPLETADO -> {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(
                                    text = "IN: ${registro.horaIngreso}",
                                    fontSize = 11.sp,
                                    color = Color(0xFF4CAF50)
                                )
                                Text(
                                    text = "OUT: ${registro.horaSalida}",
                                    fontSize = 11.sp,
                                    color = Color(0xFF4CAF50)
                                )
                            }
                        }
                        EstadoMarcaje.PENDIENTE -> {
                            Text(
                                text = "Sin marcar",
                                fontSize = 12.sp,
                                color = Color(0xFFFF9800)
                            )
                        }
                    }
                }
            }

            IconButton(onClick = onVerPerfil) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Ver perfil",
                    tint = colorPrimario
                )
            }
        }
    }
}