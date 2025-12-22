package com.mega.appcheckinout.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.ui.theme.BotonVolver

@Composable
fun GestionObrasScreen(
    onCrearObra: () -> Unit,
    onListadoObras: () -> Unit,
    onObrasActivas: () -> Unit,
    onObrasFinalizadas: () -> Unit,
    onVolver: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EFF5))
    ) {
        // Header con botón volver
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
                text = "GESTIÓN DE OBRAS",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        // Contenido
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Botón 1: Crear nueva obra
            androidx.compose.material3.Button(
                onClick = onCrearObra,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = colorSecundario
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Crear nueva obra",
                    fontSize = 16.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Botón 2: Listado de todas las obras
            androidx.compose.material3.Button(
                onClick = onListadoObras,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = colorSecundario
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Listado de obras",
                    fontSize = 16.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Botón 3: Obras activas
            androidx.compose.material3.Button(
                onClick = onObrasActivas,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = colorSecundario
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Obras activas",
                    fontSize = 16.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Botón 4: Obras finalizadas
            androidx.compose.material3.Button(
                onClick = onObrasFinalizadas,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = colorSecundario
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Obras finalizadas",
                    fontSize = 16.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}