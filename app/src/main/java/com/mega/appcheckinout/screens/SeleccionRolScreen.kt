package com.mega.appcheckinout.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.ui.theme.BotonVolver
// ==================== PANTALLA 4: Selección de Rol ====================
@Composable
fun SeleccionRolScreen(
    onRolSeleccionado: (String) -> Unit,
    onVolver: () -> Unit,
    colorPrimario: Color
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BotonVolver(
            onClick = onVolver,
            colorIcono = Color.White,
            colorFondo = colorPrimario,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.weight(0.5f))

        // Logo
        Box(
            modifier = Modifier
                .size(160.dp)
                .background(Color.White, shape = RoundedCornerShape(80.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CHECKINOUT",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "EMPLEADO",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colorPrimario
        )

        Text(
            text = "Elija su cargo:",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // BotÃ³n Administrativo
        Button(
            onClick = { onRolSeleccionado("Administrativo") },  // â† Pasa el rol
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8FB8C8)
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text("Administrativo", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // BotÃ³n Inspector SST
        Button(
            onClick = { onRolSeleccionado("Inspector SST")},  // â† Pasa el rol
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8FB8C8)
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text("Inspector SST", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // BotÃ³n Encargado
        Button(
            onClick = {onRolSeleccionado("Encargado") },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8FB8C8)
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text("Encargado", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}