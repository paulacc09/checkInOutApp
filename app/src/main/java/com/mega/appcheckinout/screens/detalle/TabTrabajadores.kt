package com.mega.appcheckinout.screens.detalle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.mega.appcheckinout.screens.detalle.componentes.TarjetaTrabajadorObra

@Composable
fun TabTrabajadores(
    trabajadores: List<TrabajadorCompleto>,
    obraActiva: Boolean,
    onVerTrabajador: (TrabajadorCompleto) -> Unit,
    onAsignarTrabajador: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header con contador y botón
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = colorSecundario.copy(alpha = 0.2f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Total trabajadores",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "${trabajadores.size}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario
                    )
                }

                if (obraActiva) {
                    Button(
                        onClick = onAsignarTrabajador,
                        colors = ButtonDefaults.buttonColors(containerColor = colorPrimario)
                    ) {
                        Icon(
                            painter = painterResource(android.R.drawable.ic_input_add),
                            contentDescription = "Asignar",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Asignar")
                    }
                }
            }
        }

        // Lista de trabajadores
        if (trabajadores.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_menu_myplaces),
                        contentDescription = "Sin trabajadores",
                        tint = Color.Gray,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = if (obraActiva) "No hay trabajadores asignados"
                        else "Esta obra está finalizada",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(trabajadores) { trabajador ->
                    TarjetaTrabajadorObra(
                        trabajador = trabajador,
                        onClick = { onVerTrabajador(trabajador) },
                        colorPrimario = colorPrimario
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}