package com.mega.appcheckinout.screens.detalle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.models.Obra
import com.mega.appcheckinout.screens.detalle.componentes.InfoRow

@Composable
fun TabInformacion(
    obra: Obra,
    colorPrimario: Color,
    colorSecundario: Color
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Fechas
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "FECHAS",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorPrimario
                )
                Spacer(modifier = Modifier.height(12.dp))

                InfoRow("Fecha de inicio:", obra.fechaInicio)
                if (obra.fechaFinEstimada.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoRow("Fecha fin estimada:", obra.fechaFinEstimada)
                }
            }
        }

        // Responsable
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "RESPONSABLE SISO",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorPrimario
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_menu_myplaces),
                        contentDescription = "Responsable",
                        tint = colorSecundario,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = obra.responsableSISO,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Descripción
        if (obra.descripcion.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "DESCRIPCIÓN",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = obra.descripcion,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        lineHeight = 20.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}