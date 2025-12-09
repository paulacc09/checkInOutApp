package com.mega.appcheckinout.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.models.TrabajadorCompleto
import com.mega.appcheckinout.ui.theme.BotonVolver

/**
 * Pantalla para visualizar el perfil completo de un trabajador
 * Muestra toda la información personal, laboral y de configuración
 */
@Composable
fun VerPerfilCompletoScreen(
    trabajador: TrabajadorCompleto,
    onVolver: () -> Unit,
    colorPrimario: Color
) {
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
                text = "PERFIL DEL TRABAJADOR",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        // Contenido con scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // DATOS PERSONALES
            SeccionTitulo(texto = "Datos personales", colorPrimario = colorPrimario)

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InfoRow(
                            label = "Primer Nombre",
                            value = trabajador.primerNombre,
                            colorPrimario = colorPrimario,
                            modifier = Modifier.weight(1f)
                        )
                        InfoRow(
                            label = "Segundo Nombre",
                            value = trabajador.segundoNombre.ifEmpty { "N/A" },
                            colorPrimario = colorPrimario,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InfoRow(
                            label = "Primer Apellido",
                            value = trabajador.primerApellido,
                            colorPrimario = colorPrimario,
                            modifier = Modifier.weight(1f)
                        )
                        InfoRow(
                            label = "Segundo Apellido",
                            value = trabajador.segundoApellido.ifEmpty { "N/A" },
                            colorPrimario = colorPrimario,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    InfoRow(
                        label = "Fecha de Nacimiento",
                        value = trabajador.fechaNacimiento,
                        colorPrimario = colorPrimario
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    InfoRow(
                        label = "Tipo de Documento",
                        value = trabajador.tipoDocumento,
                        colorPrimario = colorPrimario
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    InfoRow(
                        label = "Número de Documento",
                        value = trabajador.numeroDocumento,
                        colorPrimario = colorPrimario
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    InfoRow(
                        label = "Teléfono",
                        value = trabajador.telefono,
                        colorPrimario = colorPrimario
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    InfoRow(
                        label = "Dirección",
                        value = trabajador.direccion,
                        colorPrimario = colorPrimario
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // DATOS LABORALES
            SeccionTitulo(texto = "Datos laborales", colorPrimario = colorPrimario)

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    InfoRow(
                        label = "Rol",
                        value = trabajador.rol,
                        colorPrimario = colorPrimario
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    InfoRow(
                        label = "Sub Cargo",
                        value = trabajador.subCargo.ifEmpty { "N/A" },
                        colorPrimario = colorPrimario
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    InfoRow(
                        label = "Actividad",
                        value = trabajador.actividad.ifEmpty { "N/A" },
                        colorPrimario = colorPrimario
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    InfoRow(
                        label = "Obra Asignada",
                        value = trabajador.obraAsignada,
                        colorPrimario = colorPrimario
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    InfoRow(
                        label = "ARL",
                        value = trabajador.arl,
                        colorPrimario = colorPrimario
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    InfoRow(
                        label = "EPS",
                        value = trabajador.eps,
                        colorPrimario = colorPrimario
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    InfoRow(
                        label = "Fecha Examen de Ingreso",
                        value = trabajador.fechaExamen,
                        colorPrimario = colorPrimario
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    InfoRow(
                        label = "Fecha Curso de Alturas",
                        value = trabajador.fechaCursoAlturas,
                        colorPrimario = colorPrimario
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // CONFIGURACIÓN Y ESTADO
            SeccionTitulo(texto = "Configuración", colorPrimario = colorPrimario)

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    InfoRow(
                        label = "Estado",
                        value = trabajador.estado,
                        colorPrimario = colorPrimario
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "BIOMETRÍA REGISTRADA",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorPrimario
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (trabajador.biometriaRegistrada) "Sí" else "No",
                                fontSize = 16.sp,
                                color = if (trabajador.biometriaRegistrada) Color(0xFF4CAF50) else Color(0xFFF44336),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

/**
 * Componente para el título de sección
 */
@Composable
private fun SeccionTitulo(texto: String, colorPrimario: Color) {
    Text(
        text = texto,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = colorPrimario,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

/**
 * Componente para mostrar una fila de información
 */
@Composable
private fun InfoRow(
    label: String,
    value: String,
    colorPrimario: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label.uppercase(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = colorPrimario
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 16.sp
        )
    }
}