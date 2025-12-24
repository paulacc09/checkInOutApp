package com.mega.appcheckinout.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.mega.appcheckinout.ui.theme.BotonVolver

// ==================== PANTALLA 2: Registro Empresa ====================
@Composable
fun RegistroEmpresaScreen(
    onRegistroExitoso: () -> Unit,
    onVolver: () -> Unit,
    colorPrimario: Color
) {
    var nit by remember { mutableStateOf("") }
    var razonSocial by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Contenido principal con scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
                .padding(top = 48.dp), // Espacio para el botón de volver
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Logo pequeño
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.White, shape = RoundedCornerShape(60.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "CHECKINOUT",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorPrimario,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "REGISTRO",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campos del formulario
            OutlinedTextField(
                value = nit,
                onValueChange = { nit = it },
                label = { Text("NIT") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = razonSocial,
                onValueChange = { razonSocial = it },
                label = { Text("Razón social") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

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

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmarPassword,
                onValueChange = { confirmarPassword = it },
                label = { Text("Confirmar contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Registrar empresa
            Button(
                onClick = onRegistroExitoso,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorPrimario),
                shape = RoundedCornerShape(25.dp),
                enabled = nit.isNotBlank() && razonSocial.isNotBlank() &&
                        email.isNotBlank() && password.isNotBlank() && confirmarPassword.isNotBlank()
            ) {
                Text("Registrar empresa", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Botón de volver en la parte superior izquierda (encima del scroll)
        BotonVolver(
            onClick = onVolver,
            colorIcono = Color.White,
            colorFondo = colorPrimario,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )
    }
}