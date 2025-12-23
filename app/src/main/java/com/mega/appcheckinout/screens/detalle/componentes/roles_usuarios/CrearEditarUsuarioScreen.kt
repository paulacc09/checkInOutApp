package com.mega.appcheckinout.screens.detalle.componentes.roles_usuarios

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.mega.appcheckinout.models.EstadoUsuario
import com.mega.appcheckinout.models.RolUsuario
import com.mega.appcheckinout.models.Usuario
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearEditarUsuarioScreen(
    usuario: Usuario? = null, // null = crear nuevo, no null = editar
    onGuardar: (Usuario) -> Unit,
    onCancelar: () -> Unit
) {
    val esEdicion = usuario != null

    // Estados del formulario - Información Personal
    var nombreCompleto by remember { mutableStateOf(usuario?.nombreCompleto ?: "") }
    var tipoDocumento by remember { mutableStateOf(usuario?.tipoDocumento ?: "CC") }
    var numeroDocumento by remember { mutableStateOf(usuario?.numeroDocumento ?: "") }
    var telefono by remember { mutableStateOf(usuario?.telefono ?: "") }
    var email by remember { mutableStateOf(usuario?.email ?: "") }
    var fotoUrl by remember { mutableStateOf(usuario?.fotoUrl ?: "") }

    // Estados - Credenciales
    var nombreUsuario by remember { mutableStateOf(usuario?.nombreUsuario ?: "") }
    var contraseñaTemporal by remember { mutableStateOf("") }
    var mostrarContraseña by remember { mutableStateOf(false) }
    var enviarPorEmail by remember { mutableStateOf(true) }
    var forzarCambioEnPrimerAcceso by remember { mutableStateOf(true) }

    // Estados - Rol y Obra
    var rolSeleccionado by remember { mutableStateOf(usuario?.rol ?: RolUsuario.OPERATIVO) }
    var obraAsignadaId by remember { mutableStateOf(usuario?.obraAsignadaId ?: "") }
    var obraAsignadaNombre by remember { mutableStateOf(usuario?.obraAsignadaNombre ?: "") }
    var mostrarSelectorObras by remember { mutableStateOf(false) }

    // Estados - Seguridad
    var autenticacion2FA by remember { mutableStateOf(usuario?.autenticacion2FA ?: false) }
    var permitirMultiplesSesiones by remember { mutableStateOf(usuario?.permitirMultiplesSesiones ?: false) }
    var requiereCambioContraseñaCada90Dias by remember { mutableStateOf(usuario?.requiereCambioContraseñaCada90Dias ?: false) }

    // Estado del usuario
    var estadoUsuario by remember { mutableStateOf(usuario?.estado ?: EstadoUsuario.ACTIVO) }

    // Validaciones

    var errores by remember { mutableStateOf(mapOf<String, String>()) }
    var mostrarDialogoConfirmacion by remember { mutableStateOf(false) }

    // Lista de obras disponibles (en producción vendría del backend)
    val obrasDisponibles = remember {
        listOf(
            "Obra Mandarino" to "obra_1",
            "Obra Rionegro" to "obra_2",
            "Obra Centro" to "obra_3"
        )
    }

    // Función de validación
    fun validarFormulario(): Boolean {
        val nuevosErrores = mutableMapOf<String, String>()

        if (nombreCompleto.isBlank()) {
            nuevosErrores["nombreCompleto"] = "El nombre completo es obligatorio"
        }
        if (numeroDocumento.isBlank()) {
            nuevosErrores["numeroDocumento"] = "El número de documento es obligatorio"
        }

        // Validaciones específicas para roles con credenciales
        if (rolSeleccionado != RolUsuario.OPERATIVO) {
            if (email.isBlank()) {
                nuevosErrores["email"] = "El email es obligatorio para este rol"
            }
            if (nombreUsuario.isBlank()) {
                nuevosErrores["nombreUsuario"] = "El nombre de usuario es obligatorio"
            }
            if (!esEdicion && contraseñaTemporal.isBlank()) {
                nuevosErrores["contraseña"] = "La contraseña es obligatoria"
            }
        }

        // Validación de obra para Encargado e Inspector
        if (rolSeleccionado in listOf(RolUsuario.ENCARGADO, RolUsuario.INSPECTOR_SST)) {
            if (obraAsignadaId.isBlank()) {
                nuevosErrores["obra"] = "Debe asignar una obra para este rol"
            }
        }

        errores = nuevosErrores
        return nuevosErrores.isEmpty()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (esEdicion) "Editar Usuario" else "Crear Nuevo Usuario") },
                navigationIcon = {
                    IconButton(onClick = onCancelar) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            if (validarFormulario()) {
                                mostrarDialogoConfirmacion = true
                            }
                        }
                    ) {
                        Text("GUARDAR", fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // SECCIÓN A: INFORMACIÓN PERSONAL
            SeccionFormulario(titulo = "Información Personal") {
                // Nombre completo
                OutlinedTextField(
                    value = nombreCompleto,
                    onValueChange = { nombreCompleto = it },
                    label = { Text("Nombre completo *") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = errores.containsKey("nombreCompleto"),
                    supportingText = errores["nombreCompleto"]?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                    leadingIcon = { Icon(Icons.Default.Person, null) }
                )

                // Tipo y número de documento
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Selector de tipo de documento
                    var expandidoTipoDoc by remember { mutableStateOf(false) }
                    val tiposDocumento = listOf("CC", "CE", "PA", "TI")

                    ExposedDropdownMenuBox(
                        expanded = expandidoTipoDoc,
                        onExpandedChange = { expandidoTipoDoc = it },
                        modifier = Modifier.weight(0.3f)
                    ) {
                        OutlinedTextField(
                            value = tipoDocumento,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Tipo *") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoTipoDoc) },
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expandidoTipoDoc,
                            onDismissRequest = { expandidoTipoDoc = false }
                        ) {
                            tiposDocumento.forEach { tipo ->
                                DropdownMenuItem(
                                    text = { Text(tipo) },
                                    onClick = {
                                        tipoDocumento = tipo
                                        expandidoTipoDoc = false
                                    }
                                )
                            }
                        }
                    }

                    // Número de documento
                    OutlinedTextField(
                        value = numeroDocumento,
                        onValueChange = { numeroDocumento = it },
                        label = { Text("Número de documento *") },
                        modifier = Modifier.weight(0.7f),
                        isError = errores.containsKey("numeroDocumento"),
                        supportingText = errores["numeroDocumento"]?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                        enabled = !esEdicion // No se puede cambiar en edición
                    )
                }

                // Teléfono
                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("Teléfono") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Phone, null) }
                )

                // Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email ${if (rolSeleccionado != RolUsuario.OPERATIVO) "*" else ""}") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = errores.containsKey("email"),
                    supportingText = errores["email"]?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                    leadingIcon = { Icon(Icons.Default.Email, null) }
                )

                // Foto (placeholder)
                OutlinedButton(
                    onClick = { /* TODO: Implementar carga de imagen */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Person, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (fotoUrl.isEmpty()) "Subir foto de perfil (opcional)" else "Cambiar foto")
                }
            }

            // SECCIÓN B: CREDENCIALES DE ACCESO
            if (rolSeleccionado != RolUsuario.OPERATIVO) {
                SeccionFormulario(
                    titulo = "Credenciales de Acceso",
                    subtitulo = "Este rol requiere usuario y contraseña para acceder al sistema"
                ) {
                    // Nombre de usuario
                    OutlinedTextField(
                        value = nombreUsuario,
                        onValueChange = { nombreUsuario = it.lowercase().replace(" ", "") },
                        label = { Text("Nombre de usuario *") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = errores.containsKey("nombreUsuario"),
                        supportingText = errores["nombreUsuario"]?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                        leadingIcon = { Icon(Icons.Default.Person, null) },
                        placeholder = { Text("Ej: jperez") }
                    )

                    // Contraseña temporal (solo en creación o si se resetea)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = contraseñaTemporal,
                            onValueChange = { contraseñaTemporal = it },
                            label = { Text(if (esEdicion) "Nueva contraseña (opcional)" else "Contraseña temporal *") },
                            modifier = Modifier.weight(1f),
                            isError = errores.containsKey("contraseña"),
                            supportingText = errores["contraseña"]?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                            visualTransformation = if (mostrarContraseña) VisualTransformation.None else PasswordVisualTransformation(),
                            leadingIcon = { Icon(Icons.Default.Lock, null) },
                            trailingIcon = {
                                IconButton(onClick = { mostrarContraseña = !mostrarContraseña }) {
                                    Icon(
                                        if (mostrarContraseña) Icons.Default.Close else Icons.Default.Check,
                                        "Mostrar/Ocultar contraseña"
                                    )
                                }
                            }
                        )

                        IconButton(
                            onClick = {
                                contraseñaTemporal = generarContraseñaAleatoria()
                            }
                        ) {
                            Icon(Icons.Default.Refresh, "Generar contraseña")
                        }
                    }

                    // Opciones de contraseña
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { enviarPorEmail = !enviarPorEmail }
                        ) {
                            Checkbox(
                                checked = enviarPorEmail,
                                onCheckedChange = { enviarPorEmail = it }
                            )
                            Text("Enviar contraseña por email", style = MaterialTheme.typography.bodyMedium)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { forzarCambioEnPrimerAcceso = !forzarCambioEnPrimerAcceso }
                        ) {
                            Checkbox(
                                checked = forzarCambioEnPrimerAcceso,
                                onCheckedChange = { forzarCambioEnPrimerAcceso = it }
                            )
                            Text("Forzar cambio de contraseña en primer acceso", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            } else {
                // Nota para operativos
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Los operativos no requieren credenciales de usuario. Solo marcan asistencia con cédula o biometría.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // SECCIÓN C: ASIGNACIÓN DE ROL
            SeccionFormulario(titulo = "Asignación de Rol") {
                Text(
                    text = "Seleccione el rol del usuario",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    RolUsuario.values().forEach { rol ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { rolSeleccionado = rol },
                            colors = CardDefaults.cardColors(
                                containerColor = if (rolSeleccionado == rol) {
                                    when (rol) {
                                        RolUsuario.ADMINISTRADOR -> Color(0xFF6200EE).copy(alpha = 0.2f)
                                        RolUsuario.ENCARGADO -> Color(0xFF03DAC5).copy(alpha = 0.2f)
                                        RolUsuario.INSPECTOR_SST -> Color(0xFFFF9800).copy(alpha = 0.2f)
                                        RolUsuario.OPERATIVO -> Color(0xFF9E9E9E).copy(alpha = 0.2f)
                                    }
                                } else {
                                    MaterialTheme.colorScheme.surface
                                }
                            ),
                            border = if (rolSeleccionado == rol) {
                                androidx.compose.foundation.BorderStroke(
                                    2.dp,
                                    when (rol) {
                                        RolUsuario.ADMINISTRADOR -> Color(0xFF6200EE)
                                        RolUsuario.ENCARGADO -> Color(0xFF03DAC5)
                                        RolUsuario.INSPECTOR_SST -> Color(0xFFFF9800)
                                        RolUsuario.OPERATIVO -> Color(0xFF9E9E9E)
                                    }
                                )
                            } else null
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = rolSeleccionado == rol,
                                    onClick = { rolSeleccionado = rol }
                                )
                                Column {
                                    Text(
                                        text = rol.displayName,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = rol.descripcion,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // SECCIÓN D: ASIGNACIÓN DE OBRA
            if (rolSeleccionado in listOf(RolUsuario.ENCARGADO, RolUsuario.INSPECTOR_SST, RolUsuario.OPERATIVO)) {
                SeccionFormulario(
                    titulo = "Asignación de Obra",
                    subtitulo = if (rolSeleccionado in listOf(RolUsuario.ENCARGADO, RolUsuario.INSPECTOR_SST)) {
                        "* Obligatorio para este rol"
                    } else {
                        "Opcional (puede asignarse después)"
                    }
                ) {
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { mostrarSelectorObras = true },
                        colors = CardDefaults.outlinedCardColors(
                            containerColor = if (errores.containsKey("obra")) {
                                MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
                            } else {
                                MaterialTheme.colorScheme.surface
                            }
                        )
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
                                    text = "Obra asignada",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = obraAsignadaNombre.ifEmpty { "Seleccionar obra" },
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = if (obraAsignadaNombre.isNotEmpty()) FontWeight.SemiBold else FontWeight.Normal,
                                    color = if (obraAsignadaNombre.isNotEmpty()) {
                                        MaterialTheme.colorScheme.onSurface
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    }
                                )
                            }
                            Icon(Icons.Default.KeyboardArrowRight, null)
                        }
                    }

                    errores["obra"]?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }
                }
            }

            // SECCIÓN E: CONFIGURACIÓN DE SEGURIDAD
            if (rolSeleccionado != RolUsuario.OPERATIVO) {
                SeccionFormulario(titulo = "Configuración de Seguridad") {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { requiereCambioContraseñaCada90Dias = !requiereCambioContraseñaCada90Dias }
                        ) {
                            Checkbox(
                                checked = requiereCambioContraseñaCada90Dias,
                                onCheckedChange = { requiereCambioContraseñaCada90Dias = it }
                            )
                            Column {
                                Text("Requerir cambio de contraseña cada 90 días", style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    "El usuario deberá actualizar su contraseña periódicamente",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { autenticacion2FA = !autenticacion2FA }
                        ) {
                            Checkbox(
                                checked = autenticacion2FA,
                                onCheckedChange = { autenticacion2FA = it }
                            )
                            Column {
                                Text("Habilitar autenticación de dos factores (2FA)", style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    "Mayor seguridad con verificación en dos pasos",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { permitirMultiplesSesiones = !permitirMultiplesSesiones }
                        ) {
                            Checkbox(
                                checked = permitirMultiplesSesiones,
                                onCheckedChange = { permitirMultiplesSesiones = it }
                            )
                            Column {
                                Text("Permitir múltiples sesiones simultáneas", style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    "El usuario puede iniciar sesión en varios dispositivos",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            // SECCIÓN F: ESTADO DEL USUARIO
            if (esEdicion) {
                SeccionFormulario(titulo = "Estado del Usuario") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        EstadoUsuario.values().forEach { estado ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { estadoUsuario = estado },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (estadoUsuario == estado) {
                                        when (estado) {
                                            EstadoUsuario.ACTIVO -> Color(0xFF4CAF50).copy(alpha = 0.2f)
                                            EstadoUsuario.INACTIVO -> Color(0xFF9E9E9E).copy(alpha = 0.2f)
                                            EstadoUsuario.BLOQUEADO -> Color(0xFFF44336).copy(alpha = 0.2f)
                                        }
                                    } else {
                                        MaterialTheme.colorScheme.surface
                                    }
                                ),
                                border = if (estadoUsuario == estado) {
                                    androidx.compose.foundation.BorderStroke(
                                        2.dp,
                                        when (estado) {
                                            EstadoUsuario.ACTIVO -> Color(0xFF4CAF50)
                                            EstadoUsuario.INACTIVO -> Color(0xFF9E9E9E)
                                            EstadoUsuario.BLOQUEADO -> Color(0xFFF44336)
                                        }
                                    )
                                } else null
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = estadoUsuario == estado,
                                        onClick = { estadoUsuario = estado }
                                    )
                                    Column {
                                        Text(
                                            text = estado.displayName,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            text = when (estado) {
                                                EstadoUsuario.ACTIVO -> "El usuario puede acceder normalmente al sistema"
                                                EstadoUsuario.INACTIVO -> "El usuario no puede iniciar sesión"
                                                EstadoUsuario.BLOQUEADO -> "Cuenta suspendida temporalmente"
                                            },
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Botones de acción al final
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onCancelar,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }

                Button(
                    onClick = {
                        if (validarFormulario()) {
                            mostrarDialogoConfirmacion = true
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Check, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Guardar Usuario")
                }
            }
        }
    }

    // Diálogo selector de obras
    if (mostrarSelectorObras) {
        AlertDialog(
            onDismissRequest = { mostrarSelectorObras = false },
            title = { Text("Seleccionar Obra") },
            text = {
                Column {
                    obrasDisponibles.forEach { (nombre, id) ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    obraAsignadaNombre = nombre
                                    obraAsignadaId = id
                                    mostrarSelectorObras = false
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = if (obraAsignadaId == id) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surface
                                }
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = obraAsignadaId == id,
                                    onClick = {
                                        obraAsignadaNombre = nombre
                                        obraAsignadaId = id
                                        mostrarSelectorObras = false
                                    }
                                )
                                Text(nombre, style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { mostrarSelectorObras = false }) {
                    Text("Cerrar")
                }
            }
        )
    }

    // Diálogo de confirmación
    if (mostrarDialogoConfirmacion) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoConfirmacion = false },
            icon = { Icon(Icons.Default.CheckCircle, null, tint = MaterialTheme.colorScheme.primary) },
            title = { Text(if (esEdicion) "Confirmar Edición" else "Confirmar Creación") },
            text = {
                Column {
                    Text(if (esEdicion) "¿Está seguro de guardar los cambios?" else "¿Está seguro de crear este usuario?")
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Resumen:", fontWeight = FontWeight.Bold)
                    Text("• Nombre: $nombreCompleto")
                    Text("• Documento: $tipoDocumento $numeroDocumento")
                    Text("• Rol: ${rolSeleccionado.displayName}")
                    if (obraAsignadaNombre.isNotEmpty()) {
                        Text("• Obra: $obraAsignadaNombre")
                    }
                    if (rolSeleccionado != RolUsuario.OPERATIVO && enviarPorEmail) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Se enviará un correo a $email con las credenciales de acceso.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val nuevoUsuario = Usuario(
                            id = usuario?.id ?: UUID.randomUUID().toString(),
                            nombreCompleto = nombreCompleto,
                            tipoDocumento = tipoDocumento,
                            numeroDocumento = numeroDocumento,
                            telefono = telefono,
                            email = email,
                            fotoUrl = fotoUrl,
                            nombreUsuario = nombreUsuario,
                            contraseñaTemporal = contraseñaTemporal,
                            requiereCambioContraseña = forzarCambioEnPrimerAcceso,
                            rol = rolSeleccionado,
                            obraAsignadaId = obraAsignadaId.ifEmpty { null },
                            obraAsignadaNombre = obraAsignadaNombre,
                            estado = estadoUsuario,
                            autenticacion2FA = autenticacion2FA,
                            permitirMultiplesSesiones = permitirMultiplesSesiones,
                            requiereCambioContraseñaCada90Dias = requiereCambioContraseñaCada90Dias,
                            creadoPor = "admin_actual" // TODO: Obtener del usuario autenticado
                        )
                        onGuardar(nuevoUsuario)
                        mostrarDialogoConfirmacion = false
                    }
                ) {
                    Text(if (esEdicion) "Guardar Cambios" else "Crear Usuario")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoConfirmacion = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
@Composable
fun SeccionFormulario(
    titulo: String,
    subtitulo: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        subtitulo?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Divider()
        content()
    }
}
// Función auxiliar para generar contraseña aleatoria
fun generarContraseñaAleatoria(): String {
    val caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%"
    return (1..12)
        .map { caracteres.random() }
        .joinToString("")
}