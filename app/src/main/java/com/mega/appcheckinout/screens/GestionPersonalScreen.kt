package com.mega.appcheckinout.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==================== PANTALLA 7: Gestión de Personal (Menú) ====================
@Composable
fun GestionPersonalScreen(
    onRegistrarNuevo: () -> Unit,
    onListaTrabajadores: () -> Unit,
    onAsignaciones: () -> Unit,
    onReportes: () -> Unit,
    onVolver: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EFF5))
    ) {
        // Header con botÃ³n volver
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFB8D4E3))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onVolver) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_revert),
                    contentDescription = "Volver",
                    tint = colorPrimario
                )
            }

            Text(
                text = "GESTIÓN DE PERSONAL",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario
            )

            Spacer(modifier = Modifier.width(48.dp)) // Balance visual
        }

        // Contenido
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // BotÃ³n 1: Registrar nuevo trabajador
            Button(
                onClick = onRegistrarNuevo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorSecundario),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Registrar nuevo trabajador",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // BotÃ³n 2: Lista de trabajadores
            Button(
                onClick = onListaTrabajadores,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorSecundario),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Lista de trabajadores",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // BotÃ³n 3: Asignaciones activas
            Button(
                onClick = onAsignaciones,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorSecundario),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Asignaciones activas",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // BotÃ³n 4: Reportes o exportar datos
            Button(
                onClick = onReportes,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorSecundario),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Reportes o exportar datos",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}