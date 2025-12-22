package com.mega.appcheckinout.screens.detalle.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.models.TrabajadorCompleto

@Composable
fun TarjetaTrabajadorObra(
    trabajador: TrabajadorCompleto,
    onClick: () -> Unit,
    colorPrimario: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono
            Card(
                colors = CardDefaults.cardColors(containerColor = colorPrimario.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_myplaces),
                    contentDescription = null,
                    tint = colorPrimario,
                    modifier = Modifier
                        .padding(12.dp)
                        .size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = trabajador.nombreCompleto(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = trabajador.rol,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            // Estado
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (trabajador.estado == "Activo") Color(0xFF4CAF50)
                    else Color(0xFF9E9E9E)
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = trabajador.estado,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    fontSize = 11.sp,
                    color = Color.White
                )
            }
        }
    }
}