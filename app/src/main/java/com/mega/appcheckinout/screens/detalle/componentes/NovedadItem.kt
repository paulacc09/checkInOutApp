package com.mega.appcheckinout.screens.detalle.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NovedadItem(texto: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, shape = RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = texto,
            fontSize = 14.sp,
            color = Color(0xFF212121)
        )
    }
}