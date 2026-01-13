// screens/administrador/DashboardAdminScreen.kt - VERSIÓN ACTUALIZADA CON ACCESOS RÁPIDOS FUNCIONALES

package com.mega.appcheckinout.screens.administrador

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.ui.theme.BotonAccesoRapido
import com.mega.appcheckinout.ui.theme.BotonGestion

@Composable
fun DashboardAdminScreen(
    onCerrarSesion: () -> Unit,
    onGestionPersonal: () -> Unit,
    onGestionObras: () -> Unit = {},
    onGestionRolesUsuarios: () -> Unit = {},
    onGestionNovedades: () -> Unit = {},
    onGestionDispositivos: () -> Unit = {},
    onReportes: () -> Unit = {},
    // ⬅️ NUEVO: Callbacks para accesos rápidos
    onCrearObra: () -> Unit = {},
    onRegistrarPersonal: () -> Unit = {},
    colorPrimario: Color,
    colorSecundario: Color
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
            Text(
                text = "CHECKINOUT",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_info_details),
                    contentDescription = "Info",
                    tint = colorPrimario,
                    modifier = Modifier.clickable { /* Info */ }
                )
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_preferences),
                    contentDescription = "Configuración",
                    tint = colorPrimario,
                    modifier = Modifier.clickable { /* Configuración */ }
                )
                Icon(
                    painter = painterResource(android.R.drawable.ic_lock_power_off),
                    contentDescription = "Cerrar Sesión",
                    tint = Color.Red,
                    modifier = Modifier.clickable { onCerrarSesion() }
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Accesos Rápidos
            Text(
                text = "ACCESOS RÁPIDOS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // ✅ BOTÓN CREAR OBRA AHORA FUNCIONAL
                BotonAccesoRapido(
                    texto = "Crear Obra",
                    colorFondo = colorSecundario,
                    modifier = Modifier.weight(1f),
                    onClick = onCrearObra // ⬅️ CONECTADO
                )

                // ✅ BOTÓN REGISTRAR PERSONAL AHORA FUNCIONAL
                BotonAccesoRapido(
                    texto = "Registrar personal",
                    colorFondo = colorSecundario,
                    modifier = Modifier.weight(1f),
                    onClick = onRegistrarPersonal // ⬅️ CONECTADO
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Gestión
            Text(
                text = "GESTIÓN",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Fila 1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    BotonGestion(
                        texto = "Gestión de Personal",
                        colorFondo = colorSecundario,
                        modifier = Modifier.weight(1f),
                        onClick = onGestionPersonal
                    )
                    BotonGestion(
                        texto = "Gestión de Obras",
                        colorFondo = colorSecundario,
                        modifier = Modifier.weight(1f),
                        onClick = onGestionObras
                    )
                }

                // Fila 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    BotonGestion(
                        texto = "Gestión de Roles y Usuarios",
                        colorFondo = colorSecundario,
                        modifier = Modifier.weight(1f),
                        onClick = onGestionRolesUsuarios
                    )
                    BotonGestion(
                        texto = "Gestión de Dispositivos",
                        colorFondo = colorSecundario,
                        modifier = Modifier.weight(1f),
                        onClick = onGestionDispositivos
                    )
                }

                // Fila 3
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    BotonGestion(
                        texto = "Reportes y Exportación",
                        colorFondo = colorSecundario,
                        modifier = Modifier.weight(1f),
                        onClick = onReportes
                    )
                    BotonGestion(
                        texto = "Novedades",
                        colorFondo = colorSecundario,
                        modifier = Modifier.weight(1f),
                        onClick = onGestionNovedades
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}