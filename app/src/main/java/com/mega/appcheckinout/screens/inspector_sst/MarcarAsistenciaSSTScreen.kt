// screens/inspector_sst/MarcarAsistenciaSSTScreen.kt
package com.mega.appcheckinout.screens.inspector_sst

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.mega.appcheckinout.models.TrabajadorCompleto
import com.mega.appcheckinout.data.DatosEjemplo
import java.text.SimpleDateFormat
import java.util.*


/**
 * Pantalla de Marcaje de Asistencia para Inspector/a SST
 *
 * Requisitos Funcionales: RF-009
 * - Permite marcar ingreso/salida de trabajadores
 * - Operativos marcan con cédula o biometría
 * - Inspector marca con usuario/contraseña
 * - Valida que la jornada esté abierta
 * - Previene duplicados (dos ingresos sin salida)
 */

data class RegistroAsistenciaTemp(
    val trabajadorId: String,
    val nombre: String,
    val numeroDocumento: String,
    val horaIngreso: String? = null,
    val horaSalida: String? = null,
    val estado: EstadoMarcaje = EstadoMarcaje.PENDIENTE
)

enum class EstadoMarcaje {
    PENDIENTE,      // No ha marcado
    INGRESADO,      // Marcó ingreso, falta salida
    COMPLETADO      // Tiene ingreso y salida
}

enum class MetodoMarcaje {
    CEDULA,
    BIOMETRIA,
    CREDENCIALES
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarcarAsistenciaSSTScreen(
    obraAsignada: String,
    jornadaAbierta: Boolean,
    onVolver: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Estado del marcaje
    var metodoSeleccionado by remember { mutableStateOf(MetodoMarcaje.CEDULA) }
    var cedulaBusqueda by remember { mutableStateOf("") }
    var mostrarDialogoExito by remember { mutableStateOf(false) }
    var mensajeExito by remember { mutableStateOf("") }

    // Datos de trabajadores de la obra
    val trabajadoresObra = remember {
        DatosEjemplo.trabajadores.filter {
            it.obraAsignada.contains("Mandarino", ignoreCase = true)
        }
    }

    // Registros del día (simulado - en producción vendría de BD)
    var registrosHoy by remember {
        mutableStateOf(
            trabajadoresObra.map { trabajador ->
                RegistroAsistenciaTemp(
                    trabajadorId = trabajador.id,
                    nombre = trabajador.nombreCompleto(),
                    numeroDocumento = trabajador.numeroDocumento
                )
            }
        )
    }

    val formatoHora = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun marcarAsistencia(numeroDocumento: String, metodo: MetodoMarcaje) {
        val index = registrosHoy.indexOfFirst { it.numeroDocumento == numeroDocumento }

        if (index != -1) {
            val registro = registrosHoy[index]
            val horaActual = formatoHora.format(Date())

            when (registro.estado) {
                EstadoMarcaje.PENDIENTE -> {
                    // Marcar INGRESO
                    registrosHoy = registrosHoy.toMutableList().apply {
                        this[index] = registro.copy(
                            horaIngreso = horaActual,
                            estado = EstadoMarcaje.INGRESADO
                        )
                    }
                    mensajeExito = "✓ Ingreso registrado: ${registro.nombre}\nHora: $horaActual"
                    mostrarDialogoExito = true
                }
                EstadoMarcaje.INGRESADO -> {
                    // Marcar SALIDA
                    registrosHoy = registrosHoy.toMutableList().apply {
                        this[index] = registro.copy(
                            horaSalida = horaActual,
                            estado = EstadoMarcaje.COMPLETADO
                        )
                    }
                    mensajeExito = "✓ Salida registrada: ${registro.nombre}\nHora: $horaActual"
                    mostrarDialogoExito = true
                }
                EstadoMarcaje.COMPLETADO -> {
                    mensajeExito = "⚠ ${registro.nombre} ya completó su jornada de hoy"
                    mostrarDialogoExito = true
                }
            }

            cedulaBusqueda = ""
        }
    }

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
                text = "MARCAR ASISTENCIA",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        // Contenido
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Estado de jornada
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (jornadaAbierta) Color(0xFF4CAF50).copy(alpha = 0.1f)
                    else Color(0xFFF44336).copy(alpha = 0.1f)
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (jornadaAbierta) Icons.Default.CheckCircle
                        else Icons.Default.Lock,
                        contentDescription = null,
                        tint = if (jornadaAbierta) Color(0xFF4CAF50) else Color(0xFFF44336),
                        modifier = Modifier.size(32.dp)
                    )
                    Column {
                        Text(
                            text = if (jornadaAbierta) "JORNADA ABIERTA" else "JORNADA CERRADA",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (jornadaAbierta) Color(0xFF4CAF50) else Color(0xFFF44336)
                        )
                        Text(
                            text = if (jornadaAbierta) "Puede marcar asistencias"
                            else "Debe abrir la jornada primero",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            if (jornadaAbierta) {
                // Selector de método de marcaje
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "MÉTODO DE MARCAJE",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorPrimario,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                selected = metodoSeleccionado == MetodoMarcaje.CEDULA,
                                onClick = { metodoSeleccionado = MetodoMarcaje.CEDULA },
                                label = { Text("Cédula", fontSize = 14.sp) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.AccountBox,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            )
                            FilterChip(
                                selected = metodoSeleccionado == MetodoMarcaje.BIOMETRIA,
                                onClick = { metodoSeleccionado = MetodoMarcaje.BIOMETRIA },
                                label = { Text("Biometría", fontSize = 14.sp) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Lock ,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            )
                        }
                    }
                }

                // Formulario de marcaje
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        when (metodoSeleccionado) {
                            MetodoMarcaje.CEDULA -> {
                                Text(
                                    text = "INGRESE NÚMERO DE CÉDULA",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorPrimario,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )

                                OutlinedTextField(
                                    value = cedulaBusqueda,
                                    onValueChange = { cedulaBusqueda = it },
                                    label = { Text("Número de cédula") },
                                    placeholder = { Text("Ej: 1234567890") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    leadingIcon = {
                                        Icon(Icons.Default.AccountBox, null)
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = colorPrimario,
                                        focusedLabelColor = colorPrimario
                                    )
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = {
                                        if (cedulaBusqueda.isNotBlank()) {
                                            marcarAsistencia(cedulaBusqueda, MetodoMarcaje.CEDULA)
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorPrimario
                                    ),
                                    shape = RoundedCornerShape(25.dp),
                                    enabled = cedulaBusqueda.isNotBlank()
                                ) {
                                    Icon(Icons.Default.Check, null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Marcar Asistencia", fontSize = 16.sp)
                                }
                            }

                            MetodoMarcaje.BIOMETRIA -> {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock ,
                                        contentDescription = null,
                                        modifier = Modifier.size(80.dp),
                                        tint = colorPrimario
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "COLOQUE SU DEDO EN EL LECTOR",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colorPrimario,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = "Esperando lectura biométrica...",
                                        fontSize = 14.sp,
                                        color = Color.Gray,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                            }

                            MetodoMarcaje.CREDENCIALES -> {
                                // No implementado para operativos
                            }
                        }
                    }
                }

                // Lista de asistencias del día
                Text(
                    text = "REGISTROS DE HOY",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorPrimario
                )

                // Resumen
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    EstadisticaMarcaje(
                        label = "Presentes",
                        count = registrosHoy.count { it.estado == EstadoMarcaje.INGRESADO ||
                                it.estado == EstadoMarcaje.COMPLETADO },
                        color = Color(0xFF4CAF50)
                    )
                    EstadisticaMarcaje(
                        label = "Completados",
                        count = registrosHoy.count { it.estado == EstadoMarcaje.COMPLETADO },
                        color = Color(0xFF2196F3)
                    )
                    EstadisticaMarcaje(
                        label = "Pendientes",
                        count = registrosHoy.count { it.estado == EstadoMarcaje.PENDIENTE },
                        color = Color(0xFFFF9800)
                    )
                }

                // Listado
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(registrosHoy) { registro ->
                        TarjetaRegistroAsistencia(
                            registro = registro,
                            colorPrimario = colorPrimario
                        )
                    }
                }
            }
        }
    }

    // Diálogo de confirmación
    if (mostrarDialogoExito) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoExito = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(48.dp)
                )
            },
            title = { Text("Asistencia Registrada") },
            text = { Text(mensajeExito) },
            confirmButton = {
                Button(onClick = { mostrarDialogoExito = false }) {
                    Text("Aceptar")
                }
            }
        )
    }
}

@Composable
private fun EstadisticaMarcaje(
    label: String,
    count: Int,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count.toString(),
            fontSize = 28.sp,
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

@Composable
private fun TarjetaRegistroAsistencia(
    registro: RegistroAsistenciaTemp,
    colorPrimario: Color
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
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = registro.nombre,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "CC: ${registro.numeroDocumento}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                when (registro.estado) {
                    EstadoMarcaje.PENDIENTE -> {
                        Text(
                            text = "SIN MARCAR",
                            fontSize = 11.sp,
                            color = Color(0xFFFF9800),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    EstadoMarcaje.INGRESADO -> {
                        Text(
                            text = "Ingreso: ${registro.horaIngreso}",
                            fontSize = 11.sp,
                            color = Color(0xFF2196F3),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    EstadoMarcaje.COMPLETADO -> {
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
            }
        }
    }
}