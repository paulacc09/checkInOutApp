package com.mega.appcheckinout.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.models.Trabajador

// Pantalla para editar trabajador (muy básica)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarTrabajadorScreen(
    trabajador: Trabajador,
    onVolver: () -> Unit,
    onGuardar: (Trabajador) -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    var nombres by remember { mutableStateOf(trabajador.nombres) }
    var apellidos by remember { mutableStateOf(trabajador.apellidos) }
    var tipoDoc by remember { mutableStateOf(trabajador.tipoDoc) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFE8EFF5))
            .padding(16.dp)
    ) {
        TextButton(onClick = onVolver) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = colorPrimario,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Volver", color = colorPrimario)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "EDITAR TRABAJADOR", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colorPrimario)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(value = nombres, onValueChange = { nombres = it }, label = { Text("Nombres") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = apellidos, onValueChange = { apellidos = it }, label = { Text("Apellidos") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = tipoDoc, onValueChange = { tipoDoc = it }, label = { Text("Tipo doc") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val actualizado = trabajador.copy(nombres = nombres, apellidos = apellidos, tipoDoc = tipoDoc)
            onGuardar(actualizado)
        }, colors = ButtonDefaults.buttonColors(containerColor = colorPrimario), modifier = Modifier.fillMaxWidth()) {
            Text("Guardar", color = Color.White)
        }
    }
}