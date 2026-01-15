// screens/detalle/componentes/encargado/AlertaPermisoLimitado.kt
package com.mega.appcheckinout.screens.detalle.componentes.encargado

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Componente reutilizable para mostrar alertas de permisos limitados
 * Usado en varias pantallas del rol Encargado
 */
@Composable
fun AlertaPermisoLimitado(
    titulo: String,
    mensaje: String,
    tipo: TipoAlerta = TipoAlerta.INFO,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when(tipo) {
                TipoAlerta.INFO -> Color(0xFF2196F3).copy(alpha = 0.1f)
                TipoAlerta.WARNING -> Color(0xFFFF9800).copy(alpha = 0.1f)
                TipoAlerta.ERROR -> Color(0xFFF44336).copy(alpha = 0.1f)
            }
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when(tipo) {
                    TipoAlerta.INFO -> Icons.Default.Info
                    TipoAlerta.WARNING, TipoAlerta.ERROR -> Icons.Default.Warning
                },
                contentDescription = null,
                tint = when(tipo) {
                    TipoAlerta.INFO -> Color(0xFF2196F3)
                    TipoAlerta.WARNING -> Color(0xFFFF9800)
                    TipoAlerta.ERROR -> Color(0xFFF44336)
                },
                modifier = Modifier.size(24.dp)
            )
            Column {
                Text(
                    text = titulo,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = when(tipo) {
                        TipoAlerta.INFO -> Color(0xFF2196F3)
                        TipoAlerta.WARNING -> Color(0xFFFF9800)
                        TipoAlerta.ERROR -> Color(0xFFF44336)
                    }
                )
                Text(
                    text = mensaje,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

enum class TipoAlerta {
    INFO,
    WARNING,
    ERROR
}