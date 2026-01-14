package com.mega.appcheckinout.screens.detalle.componentes.sst

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.models.TrabajadorCompleto
import com.mega.appcheckinout.data.DatosEjemplo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalAsignarActividad(
    trabajador: TrabajadorCompleto,
    onDismiss: () -> Unit,
    onConfirmar: (String) -> Unit,
    colorPrimario: Color = Color(0xFF4A6FA5)
) {
    var actividadSeleccionada by remember { mutableStateOf(trabajador.actividad) }
    var expandido by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Asignar Actividad",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = trabajador.nombreCompleto(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Actividad actual: ${trabajador.actividad.ifEmpty { "Sin asignar" }}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                ExposedDropdownMenuBox(
                    expanded = expandido,
                    onExpandedChange = { expandido = it }
                ) {
                    OutlinedTextField(
                        value = actividadSeleccionada,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Nueva actividad") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorPrimario
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandido,
                        onDismissRequest = { expandido = false }
                    ) {
                        DatosEjemplo.actividades.forEach { actividad ->
                            DropdownMenuItem(
                                text = { Text(actividad) },
                                onClick = {
                                    actividadSeleccionada = actividad
                                    expandido = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirmar(actividadSeleccionada) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorPrimario
                )
            ) {
                Icon(Icons.Default.Check, null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}