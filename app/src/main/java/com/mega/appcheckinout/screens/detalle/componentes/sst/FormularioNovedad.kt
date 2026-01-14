package com.mega.appcheckinout.screens.detalle.componentes.sst

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.models.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioNovedad(
    trabajadoresObra: List<TrabajadorCompleto>,
    onGuardar: (Novedad) -> Unit,
    colorPrimario: Color = Color(0xFF4A6FA5)
) {
    var trabajadorSeleccionado by remember { mutableStateOf<TrabajadorCompleto?>(null) }
    var expandirTrabajador by remember { mutableStateOf(false) }
    var tipoNovedad by remember { mutableStateOf(TipoNovedad.PERMISO) }
    var expandirTipo by remember { mutableStateOf(false) }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var evidenciasUri by remember { mutableStateOf<List<Uri>>(emptyList()) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            evidenciasUri = evidenciasUri + it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "REGISTRAR NUEVA NOVEDAD",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = colorPrimario
        )

        // Selector de trabajador
        ExposedDropdownMenuBox(
            expanded = expandirTrabajador,
            onExpandedChange = { expandirTrabajador = it }
        ) {
            OutlinedTextField(
                value = trabajadorSeleccionado?.nombreCompleto() ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Trabajador *") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirTrabajador)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario
                )
            )
            ExposedDropdownMenu(
                expanded = expandirTrabajador,
                onDismissRequest = { expandirTrabajador = false }
            ) {
                trabajadoresObra.forEach { trabajador ->
                    DropdownMenuItem(
                        text = { Text(trabajador.nombreCompleto()) },
                        onClick = {
                            trabajadorSeleccionado = trabajador
                            expandirTrabajador = false
                        }
                    )
                }
            }
        }

        // Selector de tipo de novedad
        ExposedDropdownMenuBox(
            expanded = expandirTipo,
            onExpandedChange = { expandirTipo = it }
        ) {
            OutlinedTextField(
                value = tipoNovedad.displayName,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo de novedad *") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirTipo)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario
                )
            )
            ExposedDropdownMenu(
                expanded = expandirTipo,
                onDismissRequest = { expandirTipo = false }
            ) {
                TipoNovedad.entries.forEach { tipo ->
                    DropdownMenuItem(
                        text = { Text(tipo.displayName) },
                        onClick = {
                            tipoNovedad = tipo
                            expandirTipo = false
                        }
                    )
                }
            }
        }

        // Fechas
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = fechaInicio,
                onValueChange = { fechaInicio = it },
                label = { Text("Fecha inicio *") },
                placeholder = { Text("DD/MM/YYYY") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario
                )
            )
            OutlinedTextField(
                value = fechaFin,
                onValueChange = { fechaFin = it },
                label = { Text("Fecha fin *") },
                placeholder = { Text("DD/MM/YYYY") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario
                )
            )
        }

        // Descripción
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción *") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            maxLines = 5,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorPrimario
            )
        )

        // Evidencias
        Text(
            text = "EVIDENCIAS",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = colorPrimario
        )

        Button(
            onClick = { launcher.launch("*/*") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8FB8C8)
            )
        ) {
            Icon(Icons.Default.Add, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Adjuntar archivo")
        }

        // Lista de evidencias adjuntas
        evidenciasUri.forEach { uri ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Info, null, tint = colorPrimario)
                        Text(uri.lastPathSegment ?: "Archivo", fontSize = 14.sp)
                    }
                    IconButton(onClick = {
                        evidenciasUri = evidenciasUri - uri
                    }) {
                        Icon(Icons.Default.Delete, null, tint = Color.Red)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón guardar
        Button(
            onClick = {
                if (trabajadorSeleccionado != null &&
                    fechaInicio.isNotBlank() &&
                    fechaFin.isNotBlank() &&
                    descripcion.isNotBlank()) {

                    val novedad = Novedad(
                        id = UUID.randomUUID().toString(),
                        trabajadorId = trabajadorSeleccionado!!.id,
                        trabajadorNombre = trabajadorSeleccionado!!.nombreCompleto(),
                        tipoNovedad = tipoNovedad,
                        fechaInicio = fechaInicio,
                        fechaFin = fechaFin,
                        descripcion = descripcion,
                        evidencias = emptyList(), // TODO: convertir URIs a Evidencia
                        estado = EstadoNovedad.PENDIENTE,
                        creadoPor = "Inspector SST", // TODO: obtener usuario actual
                        obraId = "1" // TODO: obtener obra actual
                    )
                    onGuardar(novedad)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = trabajadorSeleccionado != null &&
                    fechaInicio.isNotBlank() &&
                    fechaFin.isNotBlank() &&
                    descripcion.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorPrimario
            ),
            shape = RoundedCornerShape(25.dp)
        ) {
            Icon(Icons.Default.Check, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Guardar Novedad", fontSize = 16.sp)
        }
    }
}