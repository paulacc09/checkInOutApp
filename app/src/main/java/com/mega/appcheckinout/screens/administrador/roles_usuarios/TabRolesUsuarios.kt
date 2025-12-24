package com.mega.appcheckinout.screens.administrador.roles_usuarios

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TabRolesUsuarios() {
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
            .padding(16.dp)
    ) {
        // Título de la sección
        Text(
            text = "Gestión de Roles y Usuarios",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Tabs
        ScrollableTabRow(
            selectedTabIndex = tabSeleccionada,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, titulo ->
                Tab(
                    selected = tabSeleccionada == index,
                    onClick = { tabSeleccionada = index },
                    text = { Text(titulo) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Contenido según tab seleccionada
        when (tabSeleccionada) {
            0 -> ListaUsuariosTab()
            1 -> RolesPermisosTab()
            2 -> SesionesActivasTab()
            3 -> AuditoriaAccesosTab()
        }
    }
}