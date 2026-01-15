// screens/encargado/obra/DetalleObraEncargadoScreen.kt
package com.mega.appcheckinout.screens.encargado.obra

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.mega.appcheckinout.models.Obra
import com.mega.appcheckinout.screens.detalle.componentes.encargado.AlertaPermisoLimitado
import com.mega.appcheckinout.screens.detalle.componentes.encargado.TipoAlerta

/**
 * Pantalla de Detalle de Obra para Encargado
 *
 * RESTRICCIONES:
 * - Solo puede ver su obra asignada
 * - Solo lectura, sin capacidad de edición
 * - Puede solicitar traspasos de trabajadores
 */
@Composable
fun DetalleObraEncargadoScreen(
    obraId: String,
    onVolver: () -> Unit,
    onSolicitarTraspaso: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Obtener obra (en producción desde BD)
    val obra = remember {
        DatosEjemplo.obras.find { it.id == obraId }
    }

    // Trabajadores de la obra
    val trabajadoresObra = remember {
        DatosEjemplo.getTrabajadoresPorObra(obraId)
    }

    // Estadísticas
    val operativos = trabajadoresObra.count { it.rol == "Operativo" }
    val inspectores = trabajadoresObra.count { it.rol == "Inspector SST" }
    val activos = trabajadoresObra.count { it.estado == "Activo" }

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
                text = "DETALLE DE OBRA",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        if (obra == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Obra no encontrada", color = Color.Gray)
            }
            return
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Alerta de permisos
            AlertaPermisoLimitado(
                titulo = "Solo Lectura",
                mensaje = "Esta es tu obra asignada. Para modificaciones contacta al Administrador.",
                tipo = TipoAlerta.INFO
            )

            // Información principal
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    // Nombre y código
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = obra.nombre,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorPrimario
                            )
                            Text(
                                text = "Código: ${obra.codigo}",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (obra.estaActiva())
                                    Color(0xFF4CAF50).copy(alpha = 0.1f)
                                else
                                    Color.Gray.copy(alpha = 0.1f)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = obra.estado.displayName,
                                modifier = Modifier.padding(
                                    horizontal = 12.dp,
                                    vertical = 6.dp
                                ),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (obra.estaActiva())
                                    Color(0xFF4CAF50)
                                else
                                    Color.Gray
                            )
                        }
                    }

                    Divider(modifier = Modifier.padding(vertical = 16.dp))

                    // Ubicación
                    InfoRow(
                        icono = Icons.Default.LocationOn,
                        titulo = "Ubicación",
                        valor = "${obra.ciudad}, ${obra.direccion}",
                        colorIcono = colorPrimario
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Fechas
                    InfoRow(
                        icono = Icons.Default.DateRange,
                        titulo = "Fecha de inicio",
                        valor = obra.fechaInicio,
                        colorIcono = colorPrimario
                    )

                    if (obra.fechaFinEstimada.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        InfoRow(
                            icono = Icons.Default.DateRange,
                            titulo = "Fecha estimada de fin",
                            valor = obra.fechaFinEstimada,
                            colorIcono = colorPrimario
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Responsable SISO
                    InfoRow(
                        icono = Icons.Default.Person,
                        titulo = "Responsable SISO",
                        valor = obra.responsableSISO,
                        colorIcono = colorPrimario
                    )

                    if (obra.descripcion.isNotEmpty()) {
                        Divider(modifier = Modifier.padding(vertical = 16.dp))
                        Text(
                            text = "DESCRIPCIÓN",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorPrimario,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = obra.descripcion,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            // Estadísticas del personal
            Text(
                text = "PERSONAL ASIGNADO",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        EstadisticaPersonal(
                            label = "Total",
                            count = trabajadoresObra.size,
                            color = colorPrimario
                        )
                        EstadisticaPersonal(
                            label = "Operativos",
                            count = operativos,
                            color = Color(0xFF2196F3)
                        )
                        EstadisticaPersonal(
                            label = "Inspectores",
                            count = inspectores,
                            color = Color(0xFFFF9800)
                        )
                    }

                    Divider(modifier = Modifier.padding(vertical = 12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        EstadisticaPersonal(
                            label = "Activos",
                            count = activos,
                            color = Color(0xFF4CAF50)
                        )
                        EstadisticaPersonal(
                            label = "Inactivos",
                            count = trabajadoresObra.size - activos,
                            color = Color(0xFFF44336)
                        )
                    }
                }
            }

            // Acciones disponibles
            Text(
                text = "ACCIONES",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario
            )

            Button(
                onClick = onSolicitarTraspaso,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorSecundario
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Send, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Solicitar Traspaso de Trabajador", fontSize = 15.sp)
            }

            // Nota informativa
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2196F3).copy(alpha = 0.1f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color(0xFF2196F3),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Para modificar información de la obra contacta al Administrador.",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        lineHeight = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    titulo: String,
    valor: String,
    colorIcono: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = colorIcono,
            modifier = Modifier.size(20.dp)
        )
        Column {
            Text(
                text = titulo,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                text = valor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
private fun EstadisticaPersonal(
    label: String,
    count: Int,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count.toString(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}