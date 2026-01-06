package com.mega.appcheckinout.screens.detalle.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mega.appcheckinout.screens.administrador.roles_usuarios.ListaUsuariosTab
import com.mega.appcheckinout.screens.administrador.roles_usuarios.RolesPermisosTab
import com.mega.appcheckinout.screens.administrador.roles_usuarios.SesionesActivasTab
import com.mega.appcheckinout.screens.administrador.roles_usuarios.AuditoriaAccesosTab

@Composable
fun TabRolesUsuarios(
    colorPrimario: Color = Color(0xFF4A6FA5),
    colorSecundario: Color = Color(0xFF8FB8C8)
) {
    var tabSeleccionada by remember { mutableStateOf(0) }
    val tabs = listOf(
        "Usuarios",
        "Roles y Permisos",
        "Sesiones Activas",
        "Auditoría"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EFF5))
    ) {
        // Tabs con el estilo de la app
        TabRow(
            selectedTabIndex = tabSeleccionada,
            containerColor = Color.White,
            contentColor = colorPrimario,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, titulo ->
                Tab(
                    selected = tabSeleccionada == index,
                    onClick = { tabSeleccionada = index },
                    text = {
                        Text(
                            text = titulo,
                            color = if (tabSeleccionada == index) colorPrimario else Color.Gray
                        )
                    }
                )
            }
        }

        // Contenido según tab seleccionada
        when (tabSeleccionada) {
            0 -> ListaUsuariosTab(colorPrimario, colorSecundario)
            1 -> RolesPermisosTab(colorPrimario)
            2 -> SesionesActivasTab(colorPrimario)
            3 -> AuditoriaAccesosTab(colorPrimario)
        }
    }
}