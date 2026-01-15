// screens/encargado/asistencia/MarcarAsistenciaEncargadoScreen.kt
package com.mega.appcheckinout.screens.encargado.asistencia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.mega.appcheckinout.screens.detalle.componentes.encargado.AlertaPermisoLimitado
import com.mega.appcheckinout.screens.detalle.componentes.encargado.TipoAlerta

/**
 * Pantalla de Marcaje de Asistencia para Encargado
 *
 * RESTRICCIÓN CRÍTICA: Solo accesible si NO hay Inspector/a SST presente
 * Permite marcar asistencia temporalmente en ausencia del Inspector
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarcarAsistenciaEncargadoScreen(
    obraAsignada: String,
    inspectorPresente: Boolean = true,
    onVolver: () -> Unit,
    onMarcarAsistencia: (String) -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    var cedulaBusqueda by remember { mutableStateOf("") }
    var mostrarConfirmacion by remember { mutableStateOf(false) }
    var trabajadorEncontrado by remember { mutableStateOf("") }

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Alerta crítica si Inspector está presente
            if (inspectorPresente) {
                AlertaPermisoLimitado(
                    titulo = "Función No Disponible",
                    mensaje = "El marcaje de asistencia debe ser realizado por el Inspector/a SST. Solo puedes marcar en su ausencia.",
                    tipo = TipoAlerta.ERROR
                )

                // Deshabilitar la interfaz si el inspector está presente
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Gray.copy(alpha = 0.1f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Función bloqueada",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                        Text(
                            text = "Contacta al Inspector/a SST",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                // Modo temporal habilitado
                AlertaPermisoLimitado(
                    titulo = "Modo Temporal Activo",
                    mensaje = "Inspector/a SST ausente. Puedes marcar asistencia temporalmente. Los registros quedarán sujetos a validación.",
                    tipo = TipoAlerta.WARNING
                )

                // Formulario de marcaje
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
                        Text(
                            text = "MARCAR ASISTENCIA",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorPrimario,
                            modifier = Modifier.padding(bottom = 16.dp)
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
                                    trabajadorEncontrado = "Trabajador Ejemplo"
                                    mostrarConfirmacion = true
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
                }

                // Información adicional
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF2196F3).copy(alpha = 0.1f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color(0xFF2196F3),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Los registros realizados en ausencia del Inspector/a SST quedarán marcados para revisión posterior.",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }

    // Diálogo de confirmación
    if (mostrarConfirmacion) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacion = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(48.dp)
                )
            },
            title = { Text("Asistencia Registrada") },
            text = {
                Column {
                    Text("✓ Registro exitoso")
                    Text("Trabajador: $trabajadorEncontrado")
                    Text("Cédula: $cedulaBusqueda")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Registro sujeto a validación",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onMarcarAsistencia(cedulaBusqueda)
                        mostrarConfirmacion = false
                        cedulaBusqueda = ""
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}