package com.mega.appcheckinout.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.models.Obra
import com.mega.appcheckinout.ui.theme.BotonVolver
import com.mega.appcheckinout.data.DatosEjemplo

@Composable
fun ObrasFinalizadasScreen(
    onVolver: () -> Unit,
    onVerDetalleObra: (Obra) -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Datos de ejemplo - solo obras finalizadas
    val obrasFinalizadas = remember { DatosEjemplo.getObrasFinalizadas() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EFF5))
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFB8D4E3))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BotonVolver(
                onClick = onVolver,
                colorIcono = Color.White,
                colorFondo = colorPrimario
            )

            Text(
                text = "OBRAS FINALIZADAS",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        // Mensaje informativo
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = androidx.compose.ui.res.painterResource(android.R.drawable.ic_dialog_info),
                    contentDescription = "Info",
                    tint = Color(0xFFF57C00),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Estas obras están cerradas. Solo puedes ver su historial y exportar reportes.",
                    fontSize = 13.sp,
                    color = Color(0xFF6D4C41)
                )
            }
        }

        // Contador
        Text(
            text = "${obrasFinalizadas.size} obras finalizadas",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 14.sp,
            color = Color.Gray
        )

        // Lista de obras finalizadas
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(obrasFinalizadas) { obra ->
                TarjetaObra(
                    obra = obra,
                    onClick = { onVerDetalleObra(obra) },
                    colorPrimario = colorPrimario,
                    colorSecundario = colorSecundario
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}