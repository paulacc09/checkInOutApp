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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.mega.appcheckinout.ui.theme.BotonVolver

// ==================== PANTALLA 5: Login por Rol ====================
@Composable
fun LoginRolScreen(
    rolSeleccionado: String,  // â† Nuevo parÃ¡metro
    onLoginExitoso: () -> Unit,
    onVolver: () -> Unit,
    colorPrimario: Color
) {
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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

        // TÃ­tulo dinÃ¡mico segÃºn el rol seleccionado
        Text(
            text = rolSeleccionado.uppercase(),  // â† Muestra el rol en mayÃºsculas
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colorPrimario
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo Usuario
        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
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
            label = { Text("Clave") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorPrimario,
                focusedLabelColor = colorPrimario
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // BotÃ³n Iniciar SesiÃ³n
        Button(
            onClick = {
                // âœ… Solo permite entrar si es Administrativo
                if (rolSeleccionado == "Administrativo") {
                    onLoginExitoso()
                }
                // âŒ Si es Inspector SST o Encargado, no hace nada
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorPrimario,
                disabledContainerColor = Color.Gray  // Color gris cuando deshabilitado
            ),
            shape = RoundedCornerShape(25.dp),
            enabled = if (rolSeleccionado == "Administrativo") {
                // âœ… Administrativo: habilitado si hay usuario y contraseÃ±a
                password.isNotBlank() && usuario.isNotBlank()
            } else {
                // âŒ Inspector SST y Encargado: siempre deshabilitado
                false
            }
        ) {
            Text(
                text = if (rolSeleccionado == "Administrativo") {
                    "Iniciar Sesión"
                } else {
                    "Próximamente"  // Texto diferente para roles no disponibles
                },
                fontSize = 16.sp
            )
        }

        // Mensaje informativo para roles no disponibles
        if (rolSeleccionado != "Administrativo") {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Esta funcionalidad estará disponible próximamente",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}