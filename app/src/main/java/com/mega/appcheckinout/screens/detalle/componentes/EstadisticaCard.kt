package com.mega.appcheckinout.screens.detalle.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun EstadisticaCard(
    titulo: String,
    valor: String,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = valor,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = titulo,
            fontSize = 13.sp,
            color = Color.Gray
        )
    }
}