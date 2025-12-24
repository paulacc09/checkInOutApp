package com.mega.appcheckinout.screens.administrador.roles_usuarios

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mega.appcheckinout.models.MatrizPermisos
import com.mega.appcheckinout.models.RolUsuario

@Composable
fun RolesPermisosTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Encabezado
        Text(
            text = "Matriz de Permisos del Sistema",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "La estructura de roles es fija para garantizar la seguridad del sistema. Los permisos no son personalizables.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Botón exportar
        Button(
            onClick = { /* TODO: Exportar PDF */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(Icons.Default.Share, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Exportar Matriz (PDF)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tabla de permisos
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                // Encabezado de la tabla
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(8.dp)
                ) {
                    // Columna "Función"
                    Text(
                        text = "Función del Sistema",
                        modifier = Modifier
                            .width(280.dp)
                            .padding(8.dp),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    // Columnas de roles
                    listOf(
                        "Administrador",
                        "Encargado",
                        "Inspector/a SST",
                        "Operativo"
                    ).forEach { rol ->
                        Text(
                            text = rol,
                            modifier = Modifier
                                .width(140.dp)
                                .padding(8.dp),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Divider()

                // Filas de permisos
                MatrizPermisos.permisos.forEachIndexed { index, permiso ->
                    Row(
                        modifier = Modifier
                            .background(
                                if (index % 2 == 0) MaterialTheme.colorScheme.surface
                                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Nombre de la función
                        Text(
                            text = permiso.funcion,
                            modifier = Modifier
                                .width(280.dp)
                                .padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        // Permisos por rol
                        listOf(
                            permiso.administrador,
                            permiso.encargado,
                            permiso.inspectorSST,
                            permiso.operativo
                        ).forEach { tienePermiso ->
                            Box(
                                modifier = Modifier
                                    .width(140.dp)
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (tienePermiso) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Permitido",
                                        tint = Color(0xFF4CAF50),
                                        modifier = Modifier.size(28.dp)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "No permitido",
                                        tint = Color(0xFFF44336),
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                        }
                    }

                    if (index < MatrizPermisos.permisos.size - 1) {
                        Divider()
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Descripción de roles
        Text(
            text = "Descripción de Roles",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        RolUsuario.values().forEach { rol ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = when (rol) {
                        RolUsuario.ADMINISTRADOR -> Color(0xFF6200EE).copy(alpha = 0.1f)
                        RolUsuario.ENCARGADO -> Color(0xFF03DAC5).copy(alpha = 0.1f)
                        RolUsuario.INSPECTOR_SST -> Color(0xFFFF9800).copy(alpha = 0.1f)
                        RolUsuario.OPERATIVO -> Color(0xFF9E9E9E).copy(alpha = 0.1f)
                    }
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Badge(
                            containerColor = when (rol) {
                                RolUsuario.ADMINISTRADOR -> Color(0xFF6200EE)
                                RolUsuario.ENCARGADO -> Color(0xFF03DAC5)
                                RolUsuario.INSPECTOR_SST -> Color(0xFFFF9800)
                                RolUsuario.OPERATIVO -> Color(0xFF9E9E9E)
                            }
                        ) {
                            Text(
                                text = rol.displayName,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = rol.descripcion,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Características específicas del rol
                    when (rol) {
                        RolUsuario.ADMINISTRADOR -> {
                            Text("• Gestión completa de usuarios, obras y dispositivos", style = MaterialTheme.typography.bodySmall)
                            Text("• Acceso a todos los reportes y auditorías", style = MaterialTheme.typography.bodySmall)
                            Text("• Control total del sistema", style = MaterialTheme.typography.bodySmall)
                        }
                        RolUsuario.ENCARGADO -> {
                            Text("• Gestión de personal en su obra asignada", style = MaterialTheme.typography.bodySmall)
                            Text("• Puede abrir/cerrar asistencia en ausencia de Inspector", style = MaterialTheme.typography.bodySmall)
                            Text("• Visualización de reportes limitados a su obra", style = MaterialTheme.typography.bodySmall)
                        }
                        RolUsuario.INSPECTOR_SST -> {
                            Text("• Autorización diaria de asistencias", style = MaterialTheme.typography.bodySmall)
                            Text("• Registro y aprobación de novedades", style = MaterialTheme.typography.bodySmall)
                            Text("• Gestión de seguridad y salud en obra", style = MaterialTheme.typography.bodySmall)
                        }
                        RolUsuario.OPERATIVO -> {
                            Text("• Solo marcaje de asistencia con cédula o biometría", style = MaterialTheme.typography.bodySmall)
                            Text("• No tiene acceso a credenciales de usuario", style = MaterialTheme.typography.bodySmall)
                            Text("• No puede gestionar información del sistema", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}