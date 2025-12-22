package com.mega.appcheckinout.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.models.Obra
import com.mega.appcheckinout.models.TrabajadorCompleto
import com.mega.appcheckinout.ui.theme.BotonVolver
import com.mega.appcheckinout.screens.detalle.TabInformacion
import com.mega.appcheckinout.screens.detalle.TabTrabajadores
import com.mega.appcheckinout.screens.detalle.TabEstadisticas

@Composable
fun DetalleObraScreen(
    obra: Obra,
    onVolver: () -> Unit,
    onEditarObra: () -> Unit = {},
    onVerTrabajador: (TrabajadorCompleto) -> Unit = {},
    onAsignarTrabajador: () -> Unit = {},
    onGenerarReporte: () -> Unit = {},
    colorPrimario: Color,
    colorSecundario: Color
) {
    var mostrarMenuOpciones by remember { mutableStateOf(false) }
    var mostrarDialogoEstado by remember { mutableStateOf(false) }
    var tabSeleccionada by remember { mutableStateOf(0) }

    // Datos de ejemplo - trabajadores asignados (TODO: Cargar desde base de datos)
    val trabajadoresAsignados = remember {
        if (obra.estaActiva()) {
            listOf(
                TrabajadorCompleto(
                    id = "1",
                    primerNombre = "Juan",
                    segundoNombre = "Carlos",
                    primerApellido = "Pérez",
                    segundoApellido = "Gómez",
                    fechaNacimiento = "15/03/1985",
                    tipoDocumento = "CC",
                    numeroDocumento = "1234567890",
                    telefono = "3001234567",
                    direccion = "Calle 10 # 5-20, Centro",
                    rol = "Operario",
                    subCargo = "Albañil",
                    actividad = "Construcción",
                    obraAsignada = obra.id,
                    arl = "Sura ARL",
                    eps = "Sura",
                    fechaExamen = "10/01/2024",
                    fechaCursoAlturas = "15/01/2024",
                    biometriaRegistrada = true,
                    estado = "Activo"
                ),
                TrabajadorCompleto(
                    id = "2",
                    primerNombre = "María",
                    segundoNombre = "José",
                    primerApellido = "Rodríguez",
                    segundoApellido = "López",
                    fechaNacimiento = "20/07/1990",
                    tipoDocumento = "CC",
                    numeroDocumento = "0987654321",
                    telefono = "3112345678",
                    direccion = "Carrera 5 # 12-34, Norte",
                    rol = "Inspector SST",
                    subCargo = "Inspector",
                    actividad = "Seguridad Industrial",
                    obraAsignada = obra.id,
                    arl = "Positiva",
                    eps = "Nueva EPS",
                    fechaExamen = "01/02/2024",
                    fechaCursoAlturas = "05/02/2024",
                    biometriaRegistrada = true,
                    estado = "Activo"
                ),
                TrabajadorCompleto(
                    id = "3",
                    primerNombre = "Pedro",
                    segundoNombre = "",
                    primerApellido = "Martínez",
                    segundoApellido = "Silva",
                    fechaNacimiento = "10/11/1988",
                    tipoDocumento = "CC",
                    numeroDocumento = "1122334455",
                    telefono = "3209876543",
                    direccion = "Avenida 8 # 20-15",
                    rol = "Encargado",
                    subCargo = "Supervisor de Obra",
                    actividad = "Supervisión",
                    obraAsignada = obra.id,
                    arl = "Sura ARL",
                    eps = "Compensar",
                    fechaExamen = "05/01/2024",
                    fechaCursoAlturas = "10/01/2024",
                    biometriaRegistrada = true,
                    estado = "Activo"
                )
            )
        } else {
            emptyList()
        }
    }

    val tabs = listOf("Información", "Trabajadores", "Estadísticas")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EFF5))
    ) {
        // Header con acciones
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
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario
            )

            Box {
                IconButton(onClick = { mostrarMenuOpciones = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Más opciones",
                        tint = colorPrimario
                    )
                }

                DropdownMenu(
                    expanded = mostrarMenuOpciones,
                    onDismissRequest = { mostrarMenuOpciones = false }
                ) {
                    if (obra.estaActiva()) {
                        DropdownMenuItem(
                            text = { Text("Editar información") },
                            onClick = {
                                mostrarMenuOpciones = false
                                onEditarObra()
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Edit, contentDescription = null)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Cambiar estado") },
                            onClick = {
                                mostrarMenuOpciones = false
                                mostrarDialogoEstado = true
                            }
                        )
                    }
                    DropdownMenuItem(
                        text = { Text("Generar reporte") },
                        onClick = {
                            mostrarMenuOpciones = false
                            onGenerarReporte()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Ver historial") },
                        onClick = {
                            mostrarMenuOpciones = false
                            // TODO: Implementar historial
                        }
                    )
                }
            }
        }

        // Card principal con info básica
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Estado y código
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = obra.codigo,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario
                    )

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (obra.estaActiva()) Color(0xFF4CAF50)
                            else Color(0xFF9E9E9E)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = if (obra.estaActiva()) "ACTIVA" else "FINALIZADA",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Nombre de la obra
                Text(
                    text = obra.nombre,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Ubicación
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_dialog_map),
                        contentDescription = "Ubicación",
                        tint = colorPrimario,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = obra.ciudad,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF212121)
                        )
                        Text(
                            text = obra.direccion,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        // Tabs
        TabRow(
            selectedTabIndex = tabSeleccionada,
            containerColor = Color.White,
            contentColor = colorPrimario
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = tabSeleccionada == index,
                    onClick = { tabSeleccionada = index },
                    text = { Text(title) }
                )
            }
        }

        // Contenido según tab seleccionada
        when (tabSeleccionada) {
            0 -> TabInformacion(obra, colorPrimario, colorSecundario)
            1 -> TabTrabajadores(
                trabajadores = trabajadoresAsignados,
                obraActiva = obra.estaActiva(),
                onVerTrabajador = onVerTrabajador,
                onAsignarTrabajador = onAsignarTrabajador,
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )
            2 -> TabEstadisticas(obra, colorPrimario, colorSecundario)
        }
    }

    // Diálogo para cambiar estado
    if (mostrarDialogoEstado) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEstado = false },
            title = { Text("Cambiar estado de obra") },
            text = {Text("¿Deseas marcar esta obra como FINALIZADA? Esta acción no se puede deshacer.")
            },
            confirmButton = {
                Button(
                    onClick = {
// TODO: Actualizar estado en base de datos
                        mostrarDialogoEstado = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Finalizar obra")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoEstado = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}





