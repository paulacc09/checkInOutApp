package com.mega.appcheckinout.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

// ✅ ACTUALIZADO: Ahora acepta onClick
@Composable
fun BotonAccesoRapido(
    texto: String,
    colorFondo: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {} // ⬅️ AGREGADO
) {
    Button(
        onClick = onClick, // ⬅️ CONECTADO
        modifier = modifier.height(80.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorFondo),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = texto,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
    }
}

@Composable
fun BotonGestion(
    texto: String,
    colorFondo: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(120.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorFondo),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = texto,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
    }
}

// ============= COMPONENTE BOTÓN VOLVER =============
@Composable
fun BotonVolver(
    onClick: () -> Unit,
    colorIcono: Color = Color.White,
    colorFondo: Color = Color(0xFF4A90A4),
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.size(48.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = colorFondo),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = colorIcono,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}