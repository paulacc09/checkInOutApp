// tarjetas/TarjetaTrabajador.kt
package com.mega.appcheckinout.tarjetas

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.models.TrabajadorCompleto
import com.mega.appcheckinout.ui.theme.AppColors

/**
 * Componente de tarjeta para mostrar información de un trabajador en el listado
 *
 * @param trabajador Datos completos del trabajador
 * @param menuExpanded Estado del menú contextual (abierto/cerrado)
 * @param onMenuExpandir Callback para abrir/cerrar el menú
 * @param onVerPerfil Callback cuando se selecciona "Ver perfil"
 * @param onEditar Callback cuando se selecciona "Editar"
 * @param onEliminar Callback cuando se selecciona "Desactivar"
 * @param onAsignarObra Callback cuando se selecciona "Asignar obra"
 */
@Composable
fun TarjetaTrabajador(
    trabajador: TrabajadorCompleto,
    colorPrimario: Color,
    colorSecundario: Color,
    menuExpanded: Boolean,
    onMenuExpandir: () -> Unit,
    onVerPerfil: () -> Unit,
    onEditar: () -> Unit,
    onEliminar: () -> Unit,
    onAsignarObra: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    // Nombre completo
                    Text(
                        text = trabajador.nombreCompleto(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // ID
                    Text(
                        text = "ID: ${trabajador.id}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                // Menú de opciones
                Box {
                    IconButton(onClick = onMenuExpandir) {
                        Icon(
                            painter = painterResource(R.drawable.ic_menu_more),
                            contentDescription = "Más opciones",
                            tint = colorPrimario
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = onMenuExpandir
                    ) {
                        DropdownMenuItem(
                            text = { Text("Ver perfil") },
                            onClick = {
                                onVerPerfil()
                                onMenuExpandir()
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_menu_view),
                                    contentDescription = null
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Editar") },
                            onClick = {
                                onEditar()
                                onMenuExpandir()
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_menu_edit),
                                    contentDescription = null
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Asignar obra") },
                            onClick = {
                                onAsignarObra()
                                onMenuExpandir()
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_menu_add),
                                    contentDescription = null
                                )
                            }
                        )
                        HorizontalDivider()
                        DropdownMenuItem(
                            text = { Text("Desactivar", color = Color.Red) },
                            onClick = {
                                onEliminar()
                                onMenuExpandir()
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_menu_delete),
                                    contentDescription = null,
                                    tint = Color.Red
                                )
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Información del trabajador
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Rol
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "ROL",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = trabajador.rol,
                        fontSize = 13.sp,
                        color = Color.Black
                    )
                    if (trabajador.subCargo.isNotEmpty()) {
                        Text(
                            text = trabajador.subCargo,
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }

                // Estado Biometría
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "BIOMETRÍA",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = if (trabajador.biometriaRegistrada)
                                        Color(0xFF4CAF50) else Color(0xFFFFC107),
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (trabajador.biometriaRegistrada) "Registrado" else "Pendiente",
                            fontSize = 13.sp,
                            color = if (trabajador.biometriaRegistrada)
                                Color(0xFF4CAF50) else Color(0xFFFFC107),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Obra asignada
            Column {
                Text(
                    text = "OBRA ASIGNADA",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = trabajador.obraAsignada,
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Estado
            Surface(
                color = if (trabajador.estado == "Activo")
                    Color(0xFF4CAF50).copy(alpha = 0.2f)
                else
                    Color(0xFFE0E0E0),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = trabajador.estado,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (trabajador.estado == "Activo")
                        Color(0xFF2E7D32)
                    else
                        Color.Gray,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
        }
    }
}