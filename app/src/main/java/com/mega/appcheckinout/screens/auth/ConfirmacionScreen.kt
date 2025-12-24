package com.mega.appcheckinout.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==================== PANTALLA 3: Confirmación ====================
@Composable
fun ConfirmacionScreen(
    onVolverLogin: () -> Unit,
    colorPrimario: Color
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
            text = "¡SU REGISTRO SE HA",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorPrimario,
            textAlign = TextAlign.Center
        )

        Text(
            text = "REGISTRADO CON ÉXITO!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorPrimario,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Hemos enviado un correo de verificación al administrador",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onVolverLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorPrimario),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text("Volver a Inicio de sesión", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¿No recibiste el correo de verificación? Reenviar correo",
            fontSize = 12.sp,
            color = colorPrimario,
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable { }
        )
    }
}