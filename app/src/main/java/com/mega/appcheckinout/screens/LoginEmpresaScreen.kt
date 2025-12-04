package com.mega.appcheckinout.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==================== PANTALLA 1: Login Empresa ====================
@Composable
fun LoginEmpresaScreen(
    onRegistrar: () -> Unit,
    onIniciarSesion: () -> Unit,
    colorPrimario: Color
) {
    var nitEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var recordarSesion by remember { mutableStateOf(false) }

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
                .size(180.dp)
                .background(Color.White, shape = RoundedCornerShape(90.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CHECKINOUT",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "EMPRESA",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = colorPrimario
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo RUT o email
        OutlinedTextField(
            value = nitEmail,
            onValueChange = { nitEmail = it },
            label = { Text("NIT o e-mail") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorPrimario,
                focusedLabelColor = colorPrimario
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo ContraseÃ±a
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorPrimario,
                focusedLabelColor = colorPrimario
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Recordar sesion
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = recordarSesion,
                onCheckedChange = { recordarSesion = it },
                colors = CheckboxDefaults.colors(checkedColor = colorPrimario)
            )
            Text("Mantener sesión iniciada", fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // BotÃ³n Iniciar SesiÃ³n
        Button(
            onClick = onIniciarSesion,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorPrimario),
            shape = RoundedCornerShape(25.dp),
            enabled = password.isNotBlank() && nitEmail.isNotBlank()
        ) {
            Text("Iniciar Sesión", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Link Olvidaste contraseÃ±a
        Text(
            text = "¿Olvidaste tu contraseña? Recuperar contraseña",
            fontSize = 12.sp,
            color = colorPrimario,
            modifier = Modifier.clickable { }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Link Registrar empresa
        Text(
            text = "¿No tienes cuenta de empresa? Registrarse",
            fontSize = 14.sp,
            color = colorPrimario,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onRegistrar() }
        )
    }
}
