package com.mega.appcheckinout.screens

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.models.Trabajador
import com.mega.appcheckinout.ui.theme.AppColors

/**
 * Pantalla para visualizar el perfil completo de un trabajador
 * Muestra información básica en modo solo lectura
 *
 * @param trabajador Datos básicos del trabajador a mostrar
 * @param onVolver Callback para volver a la pantalla anterior
 *
 * TODO: Expandir para mostrar más información (historial, asistencias, etc.)
 */


@Composable
fun VerPerfilScreen(
    trabajador: Trabajador,
    onVolver: () -> Unit,
    colorPrimario: Color  // ← AGREGADO
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.colorFondo)
            .padding(16.dp)
    ) {
        // Botón volver
        IconButton(onClick = onVolver) {
            Icon(
                painter = painterResource(R.drawable.ic_menu_revert),
                contentDescription = "Volver",
                tint = colorPrimario  // ← Ahora usa el parámetro
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Título
        Text(
            text = "PERFIL DEL TRABAJADOR",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = colorPrimario  // ← Ahora usa el parámetro
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Información del trabajador
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),  // ← Cambié a blanco
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                InfoRow(label = "ID", value = trabajador.id, colorPrimario = colorPrimario)
                Spacer(modifier = Modifier.height(12.dp))
                InfoRow(label = "Nombres", value = trabajador.nombres, colorPrimario = colorPrimario)
                Spacer(modifier = Modifier.height(12.dp))
                InfoRow(label = "Apellidos", value = trabajador.apellidos, colorPrimario = colorPrimario)
                Spacer(modifier = Modifier.height(12.dp))
                InfoRow(label = "Tipo de documento", value = trabajador.tipoDoc, colorPrimario = colorPrimario)
            }
        }
    }
}

/**
 * Componente para mostrar una fila de información
 */
@Composable
private fun InfoRow(label: String, value: String, colorPrimario: Color) {
    Column {
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