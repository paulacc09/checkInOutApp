// screens/inspector_sst/DispositivosSSTScreen.kt
package com.mega.appcheckinout.screens.inspector_sst

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.ui.theme.BotonVolver
import com.mega.appcheckinout.data.DatosEjemplo
import com.mega.appcheckinout.models.*
import java.text.SimpleDateFormat
import java.util.*
@Composable
fun DispositivosSSTScreen(
    obraAsignada: String,
    onVolver: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    val formatoFecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
// Filtrar dispositivos de la obra asignada
    val dispositivosObra = remember {
        DatosEjemplo.dispositivos.filter { it.obraId == "1" }
    }

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
                text = "DISPOSITIVOS",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card informativa
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = colorSecundario.copy(alpha = 0.2f)
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = colorPrimario,
                        modifier = Modifier.size(24.dp)
                    )
                    Column {
                        Text(
                            text = "DISPOSITIVOS DE MI OBRA",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorPrimario
                        )
                        Text(
                            text = "Solo lectura. Contacte al administrador para gestionar.",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Estadística
            Text(
                text = "${dispositivosObra.size} dispositivo(s) registrado(s)",
                fontSize = 14.sp,
                color = Color.Gray
            )

            // Lista de dispositivos
            if (dispositivosObra.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )
                        Text(
                            text = "No hay dispositivos registrados",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(dispositivosObra) { dispositivo ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Icono del tipo
                                Surface(
                                    modifier = Modifier.size(48.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(dispositivo.estado.color).copy(alpha = 0.2f)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = when(dispositivo.tipo) {
                                                TipoDispositivo.TABLET -> Icons.Default.Phone
                                                TipoDispositivo.TELEFONO -> Icons.Default.Phone
                                                TipoDispositivo.BIOMETRICO -> Icons.Default.Lock
                                            },
                                            contentDescription = null,
                                            tint = Color(dispositivo.estado.color),
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                }

                                // Información
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = dispositivo.nombre,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Tipo: ${dispositivo.tipo.displayName}",
                                        fontSize = 13.sp,
                                        color = Color.Gray
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Responsable: ${dispositivo.responsableNombre}",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))

                                    // Estado
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(dispositivo.estado.color)
                                        ),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = dispositivo.estado.displayName,
                                            modifier = Modifier.padding(
                                                horizontal = 8.dp,
                                                vertical = 4.dp
                                            ),
                                            fontSize = 11.sp,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    dispositivo.ultimoUso?.let { ultimoUso ->
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "Último uso: ${formatoFecha.format(Date(ultimoUso))}",
                                            fontSize = 11.sp,
                                            color = Color.Gray
                                        )
                                    }

                                    // Métodos habilitados
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Métodos habilitados:",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colorPrimario
                                    )
                                    dispositivo.metodosHabilitados.forEach { metodo ->
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color(0xFF4CAF50),
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Text(
                                                text = metodo.displayName,
                                                fontSize = 11.sp,
                                                color = Color.Gray
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
