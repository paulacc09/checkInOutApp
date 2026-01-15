// screens/detalle/componentes/encargado/TarjetaTrabajadorEncargado.kt
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
import com.mega.appcheckinout.models.TrabajadorCompleto

/**
 * Tarjeta de trabajador para vista del Encargado
 * Solo permite consulta, sin opciones de edición
 */
@Composable
fun TarjetaTrabajadorEncargado(
    trabajador: TrabajadorCompleto,
    onVerPerfil: () -> Unit,
    colorPrimario: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Avatar
            Surface(
                modifier = Modifier.size(56.dp),
                shape = RoundedCornerShape(28.dp),
                color = colorPrimario.copy(alpha = 0.2f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = colorPrimario,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Información
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = trabajador.nombreCompleto(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = trabajador.numeroDocumento,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Rol y Subcargo
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (trabajador.rol.isNotEmpty()) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = colorPrimario.copy(alpha = 0.1f)
                            ),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = trabajador.rol,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 10.sp,
                                color = colorPrimario,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    if (trabajador.subCargo.isNotEmpty()) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF2196F3).copy(alpha = 0.1f)
                            ),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = trabajador.subCargo,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 10.sp,
                                color = Color(0xFF2196F3),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Actividad
                if (trabajador.actividad.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = trabajador.actividad,
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }

                // Estado
                Spacer(modifier = Modifier.height(4.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (trabajador.estado == "Activo")
                            Color(0xFF4CAF50).copy(alpha = 0.1f)
                        else
                            Color(0xFFF44336).copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = trabajador.estado,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 10.sp,
                        color = if (trabajador.estado == "Activo")
                            Color(0xFF4CAF50)
                        else
                            Color(0xFFF44336),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Botón ver perfil
            IconButton(onClick = onVerPerfil) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Ver perfil",
                    tint = colorPrimario
                )
            }
        }
    }
}