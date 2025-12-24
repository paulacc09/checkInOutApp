package com.mega.appcheckinout.screens.administrador.gestion_personal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mega.appcheckinout.ui.theme.BotonVolver
import com.mega.appcheckinout.data.DatosEjemplo

// ==================== PANTALLA 8: Registrar Nuevo Trabajador ====================
@OptIn(ExperimentalMaterial3Api::class)  // â† IMPORTANTE: Agregar esto
@Composable
fun RegistrarTrabajadorScreen(
    onVolver: () -> Unit,
    onRegistrarBiometrico: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Estados del formulario - Datos Personales
    var primerNombre by remember { mutableStateOf("") }
    var segundoNombre by remember { mutableStateOf("") }
    var primerApellido by remember { mutableStateOf("") }
    var segundoApellido by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var tipoDocumento by remember { mutableStateOf("") }
    var numeroDocumento by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }

    // Estados del formulario - Datos Laborales
    var rol by remember { mutableStateOf("") }
    var subCargo by remember { mutableStateOf("") }
    var actividad by remember { mutableStateOf("") }
    var obraAsignada by remember { mutableStateOf("") }
    var arl by remember { mutableStateOf("") }
    var eps by remember { mutableStateOf("") }
    var fechaExamen by remember { mutableStateOf("") }
    var fechaCursoAlturas by remember { mutableStateOf("") }

    // Estados para dropdowns expandidos
    var expandirTipoDoc by remember { mutableStateOf(false) }
    var expandirRol by remember { mutableStateOf(false) }
    var expandirSubCargo by remember { mutableStateOf(false) }
    var expandirActividad by remember { mutableStateOf(false) }
    var expandirObra by remember { mutableStateOf(false) }

    // Opciones de dropdowns
    val tiposDocumento = DatosEjemplo.tiposDocumento
    val roles = DatosEjemplo.roles
    val subCargos = DatosEjemplo.subCargos
    val actividades = DatosEjemplo.actividades
    val obras = DatosEjemplo.nombresObras

    // ValidaciÃ³n: al menos subcargo o actividad seleccionados
    val validacionSubcargoActividad = subCargo.isNotBlank() || actividad.isNotBlank()

    // ValidaciÃ³n completa del formulario
    val formularioValido = primerNombre.isNotBlank() &&
            primerApellido.isNotBlank() &&
            fechaNacimiento.isNotBlank() &&
            tipoDocumento.isNotBlank() &&
            numeroDocumento.isNotBlank() &&
            telefono.isNotBlank() &&
            direccion.isNotBlank() &&
            rol.isNotBlank() &&
            validacionSubcargoActividad &&
            obraAsignada.isNotBlank() &&
            arl.isNotBlank() &&
            eps.isNotBlank() &&
            fechaExamen.isNotBlank() &&
            fechaCursoAlturas.isNotBlank()

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
                text = "REGISTRAR NUEVO TRABAJADOR",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        // Formulario con scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // DATOS PERSONALES
            Text(
                text = "Datos personales",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = primerNombre,
                    onValueChange = { primerNombre = it },
                    label = { Text("Primer Nombre*") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorPrimario,
                        focusedLabelColor = colorPrimario
                    ),
                    singleLine = true
                )

                OutlinedTextField(
                    value = segundoNombre,
                    onValueChange = { segundoNombre = it },
                    label = { Text("Segundo Nombre") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorPrimario,
                        focusedLabelColor = colorPrimario
                    ),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = primerApellido,
                    onValueChange = { primerApellido = it },
                    label = { Text("Primer Apellido*") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorPrimario,
                        focusedLabelColor = colorPrimario
                    ),
                    singleLine = true
                )

                OutlinedTextField(
                    value = segundoApellido,
                    onValueChange = { segundoApellido = it },
                    label = { Text("Segundo Apellido") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorPrimario,
                        focusedLabelColor = colorPrimario
                    ),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Fecha de Nacimiento
            OutlinedTextField(
                value = fechaNacimiento,
                onValueChange = { fechaNacimiento = it },
                label = { Text("Fecha de Nacimiento*") },
                placeholder = { Text("DD/MM/AAAA") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tipo de Documento (Dropdown)
            ExposedDropdownMenuBox(
                expanded = expandirTipoDoc,
                onExpandedChange = { expandirTipoDoc = it }
            ) {
                OutlinedTextField(
                    value = tipoDocumento,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de Documento*") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirTipoDoc) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorPrimario,
                        focusedLabelColor = colorPrimario
                    )
                )

                ExposedDropdownMenu(
                    expanded = expandirTipoDoc,
                    onDismissRequest = { expandirTipoDoc = false }
                ) {
                    tiposDocumento.forEach { tipo ->
                        DropdownMenuItem(
                            text = { Text(tipo) },
                            onClick = {
                                tipoDocumento = tipo
                                expandirTipoDoc = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = numeroDocumento,
                onValueChange = { numeroDocumento = it },
                label = { Text("Número de Documento*") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono*") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección*") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // DATOS LABORALES
            Text(




                text = "Datos laborales",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Rol (Dropdown)
            ExposedDropdownMenuBox(
                expanded = expandirRol,
                onExpandedChange = { expandirRol = it }
            ) {
                OutlinedTextField(
                    value = rol,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Rol*") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirRol) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorPrimario,
                        focusedLabelColor = colorPrimario
                    )
                )

                ExposedDropdownMenu(
                    expanded = expandirRol,
                    onDismissRequest = { expandirRol = false }
                ) {
                    roles.forEach { r ->
                        DropdownMenuItem(
                            text = { Text(r) },
                            onClick = {
                                rol = r
                                expandirRol = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Sub Cargo (Dropdown)
            ExposedDropdownMenuBox(
                expanded = expandirSubCargo,
                onExpandedChange = { expandirSubCargo = it }
            ) {
                OutlinedTextField(
                    value = subCargo,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Sub Cargo") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirSubCargo) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorPrimario,
                        focusedLabelColor = colorPrimario
                    )
                )

                ExposedDropdownMenu(
                    expanded = expandirSubCargo,
                    onDismissRequest = { expandirSubCargo = false }
                ) {
                    subCargos.forEach { sc ->
                        DropdownMenuItem(
                            text = { Text(sc) },
                            onClick = {
                                subCargo = sc
                                expandirSubCargo = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Actividad (Dropdown)
            ExposedDropdownMenuBox(
                expanded = expandirActividad,
                onExpandedChange = { expandirActividad = it }
            ) {
                OutlinedTextField(
                    value = actividad,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Actividad") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirActividad) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorPrimario,
                        focusedLabelColor = colorPrimario
                    )
                )

                ExposedDropdownMenu(
                    expanded = expandirActividad,
                    onDismissRequest = { expandirActividad = false }
                ) {
                    actividades.forEach { act ->
                        DropdownMenuItem(
                            text = { Text(act) },
                            onClick = {
                                actividad = act
                                expandirActividad = false
                            }
                        )
                    }
                }
            }

            // Mensaje de validaciÃ³n subcargo/actividad
            if (!validacionSubcargoActividad && (subCargo.isBlank() && actividad.isBlank())) {
                Text(
                    text = "* Debe seleccionar al menos Sub Cargo o Actividad",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Obra Asignada (Dropdown)
            ExposedDropdownMenuBox(
                expanded = expandirObra,
                onExpandedChange = { expandirObra = it }
            ) {
                OutlinedTextField(
                    value = obraAsignada,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Obra Asignada*") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirObra) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorPrimario,
                        focusedLabelColor = colorPrimario
                    )
                )

                ExposedDropdownMenu(
                    expanded = expandirObra,
                    onDismissRequest = { expandirObra = false }
                ) {
                    obras.forEach { obra ->
                        DropdownMenuItem(
                            text = { Text(obra) },
                            onClick = {
                                obraAsignada = obra
                                expandirObra = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = arl,
                onValueChange = { arl = it },
                label = { Text("ARL*") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = eps,
                onValueChange = { eps = it },
                label = { Text("EPS*") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Fecha Examen de Ingreso
            OutlinedTextField(
                value = fechaExamen,
                onValueChange = { fechaExamen = it },
                label = { Text("Fecha Examen de Ingreso*") },
                placeholder = { Text("DD/MM/AAAA") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Fecha Curso de Alturas
            OutlinedTextField(
                value = fechaCursoAlturas,
                onValueChange = { fechaCursoAlturas = it },
                label = { Text("Fecha Curso de Alturas*") },
                placeholder = { Text("DD/MM/AAAA") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorPrimario,
                    focusedLabelColor = colorPrimario
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // CONFIGURACIÃ“N DE MARCAJE
            Text(
                text = "Configuración de Marcaje",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = numeroDocumento,
                onValueChange = {},
                label = { Text("Número de Documento") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color.Gray,
                    disabledLabelColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onRegistrarBiometrico,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = colorSecundario),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Registrar Biométrico")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // BotÃ³n Guardar
            Button(
                onClick = { /* Guardar trabajador */ },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorPrimario),
                shape = RoundedCornerShape(8.dp),
                enabled = formularioValido
            ) {
                Text("Guardar Trabajador", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}