// MainActivity.kt
package com.mega.appcheckinout
aaaaa
import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheckInOutApp()
        }
    }
}

@Composable
fun CheckInOutApp() {
    var pantallaActual by remember { mutableStateOf("login") }
    var rolSeleccionado by remember { mutableStateOf("") }  // â† Nueva variable para guardar el rol
    var trabajadorSeleccionado by remember { mutableStateOf<Trabajador?>(null) }


    // Colores del tema
    val colorPrimario = Color(0xFF4A6FA5)
    val colorSecundario = Color(0xFF8FB8C8)
    val colorFondo = Color(0xFFE8EFF5)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorFondo
    ) {
        when(pantallaActual) {
            "login" -> LoginEmpresaScreen(
                onRegistrar = { pantallaActual = "registro" },
                onIniciarSesion = { pantallaActual = "seleccionRol" },
                colorPrimario = colorPrimario
            )

            "registro" -> RegistroEmpresaScreen(
                onRegistroExitoso = { pantallaActual = "confirmacion" },
                onVolver = { pantallaActual = "login" },
                colorPrimario = colorPrimario
            )

            "confirmacion" -> ConfirmacionScreen(
                onVolverLogin = { pantallaActual = "login" },
                colorPrimario = colorPrimario
            )

            "seleccionRol" -> SeleccionRolScreen(
                onRolSeleccionado = { rol ->  // â† Ahora recibe el rol
                    rolSeleccionado = rol      // â† Guarda el rol
                    pantallaActual = "loginRol" // â† Navega a login
                },
                onVolver = { pantallaActual = "login" },
                colorPrimario = colorPrimario
            )

            "loginRol" -> LoginRolScreen(
                rolSeleccionado = rolSeleccionado,  // â† Pasa el rol guardado
                onLoginExitoso = { pantallaActual = "dashboardAdmin" },
                onVolver = { pantallaActual = "seleccionRol" },
                colorPrimario = colorPrimario
            )

            "dashboardAdmin" -> DashboardAdminScreen(
                onCerrarSesion = { pantallaActual = "seleccionRol" },  // â† Vuelve al login
                onGestionPersonal = {pantallaActual = "gestionPersonal"},
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "gestionPersonal" -> GestionPersonalScreen(
                onRegistrarNuevo = { pantallaActual = "registrarTrabajador" },
                onListaTrabajadores = { pantallaActual = "listadoTrabajadores" },
                onAsignaciones = { /* Pantalla futura */ },
                onReportes = { /* Pantalla futura */ },
                onVolver = { pantallaActual = "dashboardAdmin" },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "registrarTrabajador" -> RegistrarTrabajadorScreen(
                onVolver = { pantallaActual = "gestionPersonal" },
                onRegistrarBiometrico = { /* Funcionalidad futura */ },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "listadoTrabajadores" -> ListadoTrabajadoresScreen(
                onVolver = { pantallaActual = "gestionPersonal" },
                onRegistrarPersonal = { pantallaActual = "registrarTrabajador" },
                onRegistrarBiometrico = { /* Funcionalidad futura */ },
                onVerPerfil = { t ->
                    trabajadorSeleccionado = t
                    pantallaActual = "verPerfil"
                },
                onEditarTrabajador = { t ->
                    trabajadorSeleccionado = t
                    pantallaActual = "editarTrabajador"
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )


            "verPerfil" -> {
                // pantalla simple para ver perfil - mostramos si hay seleccionado, si no volvemos a listado
                if (trabajadorSeleccionado != null) {
                    VerPerfilScreen(
                        trabajador = trabajadorSeleccionado!!,
                        onVolver = { pantallaActual = "listadoTrabajadores" },
                        colorPrimario = colorPrimario
                    )
                } else {
                    // si por alguna razón no hay seleccionado, volvemos al listado
                    pantallaActual = "listadoTrabajadores"
                }
            }

            "editarTrabajador" -> {
                if (trabajadorSeleccionado != null) {
                    EditarTrabajadorScreen(
                        trabajador = trabajadorSeleccionado!!,
                        onVolver = { pantallaActual = "listadoTrabajadores" },
                        onGuardar = { actualizado ->
                            trabajadorSeleccionado = actualizado
                            pantallaActual = "listadoTrabajadores"
                        },
                        colorPrimario = colorPrimario,
                        colorSecundario = colorSecundario
                    )
                } else {
                    pantallaActual = "listadoTrabajadores"
                }
            }

        }
    }
}

// ==================== PANTALLA 1: Login Empresa ====================
@Composable
fun LoginEmpresaScreen(
    onRegistrar: () -> Unit,
    onIniciarSesion: () -> Unit,
    colorPrimario: Color
) {
    var rutEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var recordarSesion by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Box(
            modifier = Modifier
                .size(180.dp)
                .background(Color.White, shape = RoundedCornerShape(90.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CHECKINOUT",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "EMPRESA",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = colorPrimario
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo RUT o email
        OutlinedTextField(
            value = rutEmail,
            onValueChange = { rutEmail = it },
            label = { Text("RUT o e-mail") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorPrimario,
                focusedLabelColor = colorPrimario
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo ContraseÃ±a
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorPrimario,
                focusedLabelColor = colorPrimario
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Recordar sesion
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = recordarSesion,
                onCheckedChange = { recordarSesion = it },
                colors = CheckboxDefaults.colors(checkedColor = colorPrimario)
            )
            Text("Mantener sesión iniciada", fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // BotÃ³n Iniciar SesiÃ³n
        Button(
            onClick = onIniciarSesion,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorPrimario),
            shape = RoundedCornerShape(25.dp),
            enabled = password.isNotBlank() && rutEmail.isNotBlank()
        ) {
            Text("Iniciar Sesión", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Link Olvidaste contraseÃ±a
        Text(
            text = "¿Olvidaste tu contraseña? Recuperar contraseña",
            fontSize = 12.sp,
            color = colorPrimario,
            modifier = Modifier.clickable { }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Link Registrar empresa
        Text(
            text = "¿No tienes cuenta de empresa? Registrarse",
            fontSize = 14.sp,
            color = colorPrimario,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onRegistrar() }
        )
    }
}

// ==================== PANTALLA 2: Registro Empresa ====================
@Composable
fun RegistroEmpresaScreen(
    onRegistroExitoso: () -> Unit,
    onVolver: () -> Unit,
    colorPrimario: Color
) {
    var rut by remember { mutableStateOf("") }
    var razonSocial by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Logo pequeÃ±o
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.White, shape = RoundedCornerShape(60.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CHECKINOUT",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "REGISTRO",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colorPrimario
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campos del formulario
        OutlinedTextField(
            value = rut,
            onValueChange = { rut = it },
            label = { Text("RUT") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorPrimario,
                focusedLabelColor = colorPrimario
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = razonSocial,
            onValueChange = { razonSocial = it },
            label = { Text("Razón social") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorPrimario,
                focusedLabelColor = colorPrimario
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorPrimario,
                focusedLabelColor = colorPrimario
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorPrimario,
                focusedLabelColor = colorPrimario
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorPrimario,
                focusedLabelColor = colorPrimario
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = confirmarPassword,
            onValueChange = { confirmarPassword = it },
            label = { Text("Confirmar contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorPrimario,
                focusedLabelColor = colorPrimario
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // BotÃ³n Registrar empresa
        Button(
            onClick = onRegistroExitoso,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorPrimario),
            shape = RoundedCornerShape(25.dp),
            enabled = rut.isNotBlank() && razonSocial.isNotBlank() &&
                    email.isNotBlank() && password.isNotBlank() && confirmarPassword.isNotBlank()
        ) {
            Text("Registrar empresa", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onVolver) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = colorPrimario,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Volver", color = colorPrimario)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// ==================== PANTALLA 3: ConfirmaciÃ³n ====================
@Composable
fun ConfirmacionScreen(
    onVolverLogin: () -> Unit,
    colorPrimario: Color
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Box(
            modifier = Modifier
                .size(160.dp)
                .background(Color.White, shape = RoundedCornerShape(80.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CHECKINOUT",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "¡SU REGISTRO SE HA",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorPrimario,
            textAlign = TextAlign.Center
        )

        Text(
            text = "REGISTRADO CON ÉXITO!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorPrimario,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Hemos enviado un correo de verificación al administrador",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onVolverLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorPrimario),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text("Volver a Inicio de sesión", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¿No recibiste el correo de verificación? Reenviar correo",
            fontSize = 12.sp,
            color = colorPrimario,
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable { }
        )
    }
}

// ==================== PANTALLA 4: SelecciÃ³n de Rol ====================
@Composable
fun SeleccionRolScreen(
    onRolSeleccionado: (String) -> Unit,
    onVolver: () -> Unit,
    colorPrimario: Color
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextButton(onClick = onVolver) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = colorPrimario,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Volver", color = colorPrimario)
            }
        }

        Spacer(modifier = Modifier.weight(0.5f))

        // Logo
        Box(
            modifier = Modifier
                .size(160.dp)
                .background(Color.White, shape = RoundedCornerShape(80.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CHECKINOUT",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "EMPLEADO",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colorPrimario
        )

        Text(
            text = "Elija su cargo:",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // BotÃ³n Administrativo
        Button(
            onClick = { onRolSeleccionado("Administrativo") },  // â† Pasa el rol
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8FB8C8)
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text("Administrativo", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // BotÃ³n Inspector SST
        Button(
            onClick = { onRolSeleccionado("Inspector SST")},  // â† Pasa el rol
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8FB8C8)
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text("Inspector SST", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // BotÃ³n Encargado
        Button(
            onClick = {onRolSeleccionado("Encargado") },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8FB8C8)
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text("Encargado", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

// ==================== PANTALLA 5: Login por Rol ====================
@Composable
fun LoginRolScreen(
    rolSeleccionado: String,  // â† Nuevo parÃ¡metro
    onLoginExitoso: () -> Unit,
    onVolver: () -> Unit,
    colorPrimario: Color
) {
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextButton(onClick = onVolver) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = colorPrimario,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Volver", color = colorPrimario)
            }
        }

        Spacer(modifier = Modifier.weight(0.5f))

        // Logo
        Box(
            modifier = Modifier
                .size(160.dp)
                .background(Color.White, shape = RoundedCornerShape(80.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CHECKINOUT",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // TÃ­tulo dinÃ¡mico segÃºn el rol seleccionado
        Text(
            text = rolSeleccionado.uppercase(),  // â† Muestra el rol en mayÃºsculas
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colorPrimario
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo Usuario
        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorPrimario,
                focusedLabelColor = colorPrimario
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo ContraseÃ±a
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Clave") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorPrimario,
                focusedLabelColor = colorPrimario
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // BotÃ³n Iniciar SesiÃ³n
        Button(
            onClick = {
                // âœ… Solo permite entrar si es Administrativo
                if (rolSeleccionado == "Administrativo") {
                    onLoginExitoso()
                }
                // âŒ Si es Inspector SST o Encargado, no hace nada
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorPrimario,
                disabledContainerColor = Color.Gray  // Color gris cuando deshabilitado
            ),
            shape = RoundedCornerShape(25.dp),
            enabled = if (rolSeleccionado == "Administrativo") {
                // âœ… Administrativo: habilitado si hay usuario y contraseÃ±a
                password.isNotBlank() && usuario.isNotBlank()
            } else {
                // âŒ Inspector SST y Encargado: siempre deshabilitado
                false
            }
        ) {
            Text(
                text = if (rolSeleccionado == "Administrativo") {
                    "Iniciar Sesión"
                } else {
                    "Próximamente"  // Texto diferente para roles no disponibles
                },
                fontSize = 16.sp
            )
        }

        // Mensaje informativo para roles no disponibles
        if (rolSeleccionado != "Administrativo") {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Esta funcionalidad estará disponible próximamente",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

// ==================== PANTALLA 6: Dashboard Admin ====================
@Composable
fun DashboardAdminScreen(
    onCerrarSesion: () ->Unit,
    onGestionPersonal: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
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
            // Logo pequeÃ±o
            Text(
                text = "CHECKINOUT",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_info_details),
                    contentDescription = "Info",
                    tint = colorPrimario,
                    modifier = Modifier.clickable { /* Info */ }
                )
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_preferences),
                    contentDescription = "Configuración",
                    tint = colorPrimario,
                    modifier = Modifier.clickable { /* ConfiguraciÃ³n */ }
                )
                // BotÃ³n de Cerrar SesiÃ³n
                Icon(
                    painter = painterResource(android.R.drawable.ic_lock_power_off),  // â† Ãcono de cerrar sesiÃ³n
                    contentDescription = "Cerrar Sesión",
                    tint = Color.Red,  // â† Color rojo para indicar salida
                    modifier = Modifier.clickable { onCerrarSesion() }  // â† Ejecuta cerrar sesiÃ³n
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Accesos RÃ¡pidos
            Text(
                text = "ACCESOS RÁPIDOS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BotonAccesoRapido(
                    texto = "Crear Obra",
                    colorFondo = colorSecundario,
                    modifier = Modifier.weight(1f)
                )
                BotonAccesoRapido(
                    texto = "Registrar personal",
                    colorFondo = colorSecundario,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // GestiÃ³n
            Text(
                text = "GESTIÓN",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    BotonGestion(
                        texto = "Gestión de Personal",
                        colorFondo = colorSecundario,
                        modifier = Modifier.weight(1f),
                        onClick = onGestionPersonal
                    )
                    BotonGestion(
                        texto = "Gestión de Obras",
                        colorFondo = colorSecundario,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    BotonGestion(
                        texto = "Gestión de Roles y Usuarios",
                        colorFondo = colorSecundario,
                        modifier = Modifier.weight(1f)
                    )
                    BotonGestion(
                        texto = "Gestión de Dispositivos",
                        colorFondo = colorSecundario,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    BotonGestion(
                        texto = "Asistencias y Reportes",
                        colorFondo = colorSecundario,
                        modifier = Modifier.weight(1f)
                    )
                    BotonGestion(
                        texto = "Novedades",
                        colorFondo = colorSecundario,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun BotonAccesoRapido(
    texto: String,
    colorFondo: Color,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { },
        modifier = modifier.height(80.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorFondo),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = texto,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
    }
}

@Composable
fun BotonGestion(
    texto: String,
    colorFondo: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(120.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorFondo),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = texto,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
    }
}

// Agregar estas pantallas al MainActivity.kt despuÃ©s de DashboardAdminScreen

// ==================== PANTALLA 7: GestiÃ³n de Personal (MenÃº) ====================
@Composable
fun GestionPersonalScreen(
    onRegistrarNuevo: () -> Unit,
    onListaTrabajadores: () -> Unit,
    onAsignaciones: () -> Unit,
    onReportes: () -> Unit,
    onVolver: () -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EFF5))
    ) {
        // Header con botÃ³n volver
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFB8D4E3))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onVolver) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_revert),
                    contentDescription = "Volver",
                    tint = colorPrimario
                )
            }

            Text(
                text = "GESTIÓN DE PERSONAL",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimario
            )

            Spacer(modifier = Modifier.width(48.dp)) // Balance visual
        }

        // Contenido
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // BotÃ³n 1: Registrar nuevo trabajador
            Button(
                onClick = onRegistrarNuevo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorSecundario),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Registrar nuevo trabajador",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // BotÃ³n 2: Lista de trabajadores
            Button(
                onClick = onListaTrabajadores,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorSecundario),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Lista de trabajadores",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // BotÃ³n 3: Asignaciones activas
            Button(
                onClick = onAsignaciones,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorSecundario),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Asignaciones activas",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // BotÃ³n 4: Reportes o exportar datos
            Button(
                onClick = onReportes,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorSecundario),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Reportes o exportar datos",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

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
    val tiposDocumento = listOf("Cédula", "Tarjeta de identidad", "Pasaporte", "Permiso de protección temporal")
    val roles = listOf("Encargado", "Inspector SST", "Operativo")
    val subCargos = listOf("Latero", "Mampostero", "Mulero", "Rematador", "Aseo", "Añadir nuevo subcargo")
    val actividades = listOf("Muros", "Parqueadero", "Tanque", "AÃ±adir nueva actividad")
    val obras = listOf("Mandarino - Ibagué", "Bosque robledal - Rionegro", "Hacienda Nakare - Villavicencio", "Pomelo - Bogotá")

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
            TextButton(onClick = onVolver) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = colorPrimario,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Volver", color = colorPrimario)
                }
            }

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

// ==================== PANTALLA 9: Listado de Trabajadores ====================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoTrabajadoresScreen(
    onVolver: () -> Unit,
    onRegistrarPersonal: () -> Unit,
    onRegistrarBiometrico: () -> Unit,
    onVerPerfil: (Trabajador) -> Unit,
    onEditarTrabajador: (Trabajador) -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    // Estados para bÃºsqueda y filtros
    var textoBusqueda by remember { mutableStateOf("") }
    var mostrarFiltros by remember { mutableStateOf(false) }
    var filtroObra by remember { mutableStateOf("Todas") }
    var filtroRol by remember { mutableStateOf("Todos") }
    var filtroEstadoBiometria by remember { mutableStateOf("Todos") }
    var expandirFiltroObra by remember { mutableStateOf(false) }
    var expandirFiltroRol by remember { mutableStateOf(false) }
    var expandirFiltroBiometria by remember { mutableStateOf(false) }

    // MenÃº contextual para cada trabajador
    var trabajadorMenuExpanded by remember { mutableStateOf<String?>(null) }
    var mostrarDialogoEliminar by remember { mutableStateOf<TrabajadorCompleto?>(null) }


    // Datos de ejemplo ampliados
    val trabajadoresCompletos = remember {
        listOf(
            TrabajadorCompleto("1234567890", "García", "Rodríguez", "Juan", "Carlos", "Cédula",
                "Operativo", "Latero", "Mandarino - Ibagué", true, "Activo"),
            TrabajadorCompleto("0987654321", "Martínez", "López", "María", "Fernanda", "Cédula",
                "Inspector SST", "", "Bosque robledal - Rionegro", true, "Activo"),
            TrabajadorCompleto("1122334455", "González", "Pérez", "Pedro", "Antonio", "Cédula",
                "Operativo", "Mampostero", "Mandarino - IbaguÃ©", false, "Activo"),
            TrabajadorCompleto("5544332211", "Ramírez", "Torres", "Ana", "María", "Cédula",
                "Encargado", "", "Hacienda Nakare - Villavicencio", true, "Activo"),
            TrabajadorCompleto("6677889900", "Sánchez", "Vargas", "Luis", "Fernando", "Pasaporte",
                "Operativo", "Rematador", "Pomelo - Bogotá", false, "Activo"),
            TrabajadorCompleto("9988776655", "Díaz", "Castro", "Carolina", "", "Cédula",
                "Operativo", "Aseo", "Mandarino - Ibagué", true, "Inactivo"),
            TrabajadorCompleto("3344556677", "Morales", "Ruiz", "Jorge", "Andrés", "Cédula",
                "Inspector SST", "", "Bosque robledal - Rionegro", false, "Activo")
        )
    }

    // Opciones para filtros
    val obras = listOf("Todas", "Mandarino - Ibagué", "Bosque robledal - Rionegro",
        "Hacienda Nakare - Villavicencio", "Pomelo - Bogotá")
    val roles = listOf("Todos", "Operativo", "Inspector SST", "Encargado")
    val estadosBiometria = listOf("Todos", "Registrado", "Pendiente")

    // Filtrar trabajadores
    val trabajadoresFiltrados = trabajadoresCompletos.filter { trabajador ->
        val coincideBusqueda = textoBusqueda.isEmpty() ||
                trabajador.nombreCompleto().contains(textoBusqueda, ignoreCase = true) ||
                trabajador.id.contains(textoBusqueda, ignoreCase = true)

        val coincideObra = filtroObra == "Todas" || trabajador.obraAsignada == filtroObra
        val coincideRol = filtroRol == "Todos" || trabajador.rol == filtroRol
        val coincideBiometria = filtroEstadoBiometria == "Todos" ||
                (filtroEstadoBiometria == "Registrado" && trabajador.biometriaRegistrada) ||
                (filtroEstadoBiometria == "Pendiente" && !trabajador.biometriaRegistrada)

        coincideBusqueda && coincideObra && coincideRol && coincideBiometria
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EFF5))
    ) {
        // Header con bÃºsqueda y filtros
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFB8D4E3))
        ) {
            // Fila superior: Volver, TÃ­tulo, BÃºsqueda, Filtros
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onVolver) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = colorPrimario,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Volver", color = colorPrimario)
                    }
                }

                Text(
                    text = "LISTA DE TRABAJADORES",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorPrimario,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(onClick = { /* Activar bÃºsqueda */ }) {
                        Icon(
                            painter = painterResource(android.R.drawable.ic_menu_search),
                            contentDescription = "Buscar",
                            tint = colorPrimario
                        )
                    }

                    IconButton(onClick = { mostrarFiltros = !mostrarFiltros }) {
                        Icon(
                            painter = painterResource(android.R.drawable.ic_menu_sort_by_size),
                            contentDescription = "Filtros",
                            tint = if (mostrarFiltros) Color(0xFF2196F3) else colorPrimario
                        )
                    }
                }
            }

            // Barra de bÃºsqueda
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                placeholder = { Text("Buscar por nombre o cédula...", fontSize = 14.sp) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_menu_search),
                        contentDescription = null,
                        tint = colorPrimario
                    )
                },
                trailingIcon = {
                    if (textoBusqueda.isNotEmpty()) {
                        IconButton(onClick = { textoBusqueda = "" }) {
                            Icon(
                                painter = painterResource(android.R.drawable.ic_menu_close_clear_cancel),
                                contentDescription = "Limpiar",
                                tint = Color.Gray
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = colorPrimario,
                    unfocusedBorderColor = Color.Gray
                ),
                shape = RoundedCornerShape(25.dp),
                singleLine = true
            )

            // Panel de filtros desplegable
            if (mostrarFiltros) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Filtros",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorPrimario,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        // Filtro por Obra
                        ExposedDropdownMenuBox(
                            expanded = expandirFiltroObra,
                            onExpandedChange = { expandirFiltroObra = it }
                        ) {
                            OutlinedTextField(
                                value = filtroObra,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Obra", fontSize = 14.sp) },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirFiltroObra)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colorPrimario
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandirFiltroObra,
                                onDismissRequest = { expandirFiltroObra = false }
                            ) {
                                obras.forEach { obra ->
                                    DropdownMenuItem(
                                        text = { Text(obra, fontSize = 14.sp) },
                                        onClick = {
                                            filtroObra = obra
                                            expandirFiltroObra = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Filtro por Rol
                        ExposedDropdownMenuBox(
                            expanded = expandirFiltroRol,
                            onExpandedChange = { expandirFiltroRol = it }
                        ) {
                            OutlinedTextField(
                                value = filtroRol,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Rol", fontSize = 14.sp) },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirFiltroRol)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colorPrimario
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandirFiltroRol,
                                onDismissRequest = { expandirFiltroRol = false }
                            ) {
                                roles.forEach { rol ->
                                    DropdownMenuItem(
                                        text = { Text(rol, fontSize = 14.sp) },
                                        onClick = {
                                            filtroRol = rol
                                            expandirFiltroRol = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Filtro por Estado BiometrÃ­a
                        ExposedDropdownMenuBox(
                            expanded = expandirFiltroBiometria,
                            onExpandedChange = { expandirFiltroBiometria = it }
                        ) {
                            OutlinedTextField(
                                value = filtroEstadoBiometria,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Estado Biometría", fontSize = 14.sp) },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirFiltroBiometria)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colorPrimario
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandirFiltroBiometria,
                                onDismissRequest = { expandirFiltroBiometria = false }
                            ) {
                                estadosBiometria.forEach { estado ->
                                    DropdownMenuItem(
                                        text = { Text(estado, fontSize = 14.sp) },
                                        onClick = {
                                            filtroEstadoBiometria = estado
                                            expandirFiltroBiometria = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // BotÃ³n limpiar filtros
                        TextButton(
                            onClick = {
                                filtroObra = "Todas"
                                filtroRol = "Todos"
                                filtroEstadoBiometria = "Todos"
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Limpiar filtros", color = colorPrimario, fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        // Contenido principal
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Contador de resultados
                Text(
                    text = "${trabajadoresFiltrados.size} trabajador(es) encontrado(s)",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Lista de trabajadores
                if (trabajadoresFiltrados.isEmpty()) {
                    // Mensaje cuando no hay resultados
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "No se encontraron trabajadores",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Intenta con otros criterios de búsqueda",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(trabajadoresFiltrados) { trabajador ->
                            TarjetaTrabajador(
                                trabajador = trabajador,
                                colorPrimario = colorPrimario,
                                colorSecundario = colorSecundario,
                                menuExpanded = trabajadorMenuExpanded == trabajador.id,
                                onMenuExpandir = {
                                    trabajadorMenuExpanded = if (trabajadorMenuExpanded == trabajador.id) {
                                        null
                                    } else {
                                        trabajador.id
                                    }
                                },
                                onVerPerfil = { onVerPerfil(trabajador.toTrabajador()) },
                                onEditar = { onEditarTrabajador(trabajador.toTrabajador()) },
                                onEliminar = { mostrarDialogoEliminar = trabajador },
                                onAsignarObra = { /* AcciÃ³n futura */ }
                            )
                        }
                    }
                }
            }

            // BotÃ³n flotante "Nuevo Trabajador"
            FloatingActionButton(
                onClick = onRegistrarPersonal,
                containerColor = colorPrimario,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_input_add),
                        contentDescription = "Agregar",
                        tint = Color.White
                    )
                    Text(
                        text = "Nuevo Trabajador",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // DiÃ¡logo de confirmaciÃ³n para eliminar
    if (mostrarDialogoEliminar != null) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEliminar = null },
            title = { Text("Confirmar eliminación") },
            text = {
                Text("¿Estás seguro de que deseas desactivar a ${mostrarDialogoEliminar!!.nombreCompleto()}?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        // AquÃ­ irÃ­a la lÃ³gica para eliminar/desactivar
                        mostrarDialogoEliminar = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Desactivar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun TarjetaTrabajador(
    trabajador: TrabajadorCompleto,
    colorPrimario: Color,
    colorSecundario: Color,
    menuExpanded: Boolean,
    onMenuExpandir: () -> Unit,
    onVerPerfil: () -> Unit,
    onEditar: () -> Unit,
    onEliminar: () -> Unit,
    onAsignarObra: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    // Nombre completo
                    Text(
                        text = trabajador.nombreCompleto(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrimario

                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // ID
                    Text(
                        text = "ID: ${trabajador.id}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                // MenÃº de opciones
                Box {
                    IconButton(onClick = onMenuExpandir) {
                        Icon(
                            painter = painterResource(android.R.drawable.ic_menu_more),
                            contentDescription = "Más opciones",
                            tint = colorPrimario
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = onMenuExpandir
                    ) {
                        DropdownMenuItem(
                            text = { Text("Ver perfil") },
                            onClick = {
                                onVerPerfil()
                                onMenuExpandir()
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(android.R.drawable.ic_menu_view),
                                    contentDescription = null
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Editar") },
                            onClick = {
                                onEditar()
                                onMenuExpandir()
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(android.R.drawable.ic_menu_edit),
                                    contentDescription = null
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Asignar obra") },
                            onClick = {
                                onAsignarObra()
                                onMenuExpandir()
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(android.R.drawable.ic_menu_add),
                                    contentDescription = null
                                )
                            }
                        )
                        Divider()
                        DropdownMenuItem(
                            text = { Text("Desactivar", color = Color.Red) },
                            onClick = {
                                onEliminar()
                                onMenuExpandir()
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(android.R.drawable.ic_menu_delete),
                                    contentDescription = null,
                                    tint = Color.Red
                                )
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // InformaciÃ³n del trabajador
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Rol
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "ROL",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = trabajador.rol,
                        fontSize = 13.sp,
                        color = Color.Black
                    )
                    if (trabajador.subCargo.isNotEmpty()) {
                        Text(
                            text = trabajador.subCargo,
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }

                // Estado BiometrÃ­a
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "BIOMETRÍA",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = if (trabajador.biometriaRegistrada)
                                        Color(0xFF4CAF50) else Color(0xFFFFC107),
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (trabajador.biometriaRegistrada) "Registrado" else "Pendiente",
                            fontSize = 13.sp,
                            color = if (trabajador.biometriaRegistrada)
                                Color(0xFF4CAF50) else Color(0xFFFFC107),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Obra asignada
            Column {
                Text(
                    text = "OBRA ASIGNADA",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = trabajador.obraAsignada,
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Estado
            Surface(
                color = if (trabajador.estado == "Activo")
                    Color(0xFF4CAF50).copy(alpha = 0.2f)
                else
                    Color(0xFFE0E0E0),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = trabajador.estado,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (trabajador.estado == "Activo")
                        Color(0xFF2E7D32)
                    else
                        Color.Gray,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
        }
    }
}

// Data class ampliada para Trabajador
data class TrabajadorCompleto(
    val id: String,
    val primerApellido: String,
    val segundoApellido: String,
    val primerNombre: String,
    val segundoNombre: String,
    val tipoDoc: String,
    val rol: String,
    val subCargo: String,
    val obraAsignada: String,
    val biometriaRegistrada: Boolean,
    val estado: String
) {
    fun nombreCompleto(): String {
        val segundo = if (segundoNombre.isNotEmpty()) " $segundoNombre" else ""
        val apellido2 = if (segundoApellido.isNotEmpty()) " $segundoApellido" else ""
        return "$primerNombre$segundo $primerApellido$apellido2"
    }

    fun toTrabajador(): Trabajador {
        return Trabajador(id, "$primerApellido $segundoApellido", "$primerNombre $segundoNombre", tipoDoc)
    }
}

// Pantalla para ver perfil (simple)
@Composable
fun VerPerfilScreen(
    trabajador: Trabajador,
    onVolver: () -> Unit,
    colorPrimario: Color
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EFF5))
            .padding(16.dp)
    ) {
        IconButton(onClick = onVolver) {
            Icon(
                painter = painterResource(android.R.drawable.ic_menu_revert),
                contentDescription = "Volver",
                tint = colorPrimario
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "PERFIL DEL TRABAJADOR", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colorPrimario)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "ID: ${trabajador.id}")
        Text(text = "Nombres: ${trabajador.nombres}")
        Text(text = "Apellidos: ${trabajador.apellidos}")
        Text(text = "Tipo doc: ${trabajador.tipoDoc}")
    }
}

// Pantalla para editar trabajador (muy básica)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarTrabajadorScreen(
    trabajador: Trabajador,
    onVolver: () -> Unit,
    onGuardar: (Trabajador) -> Unit,
    colorPrimario: Color,
    colorSecundario: Color
) {
    var nombres by remember { mutableStateOf(trabajador.nombres) }
    var apellidos by remember { mutableStateOf(trabajador.apellidos) }
    var tipoDoc by remember { mutableStateOf(trabajador.tipoDoc) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFE8EFF5))
            .padding(16.dp)
    ) {
        TextButton(onClick = onVolver) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = colorPrimario,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Volver", color = colorPrimario)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "EDITAR TRABAJADOR", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colorPrimario)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(value = nombres, onValueChange = { nombres = it }, label = { Text("Nombres") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = apellidos, onValueChange = { apellidos = it }, label = { Text("Apellidos") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = tipoDoc, onValueChange = { tipoDoc = it }, label = { Text("Tipo doc") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val actualizado = trabajador.copy(nombres = nombres, apellidos = apellidos, tipoDoc = tipoDoc)
            onGuardar(actualizado)
        }, colors = ButtonDefaults.buttonColors(containerColor = colorPrimario), modifier = Modifier.fillMaxWidth()) {
            Text("Guardar", color = Color.White)
        }
    }
}


// Mantener la data class original para compatibilidad
data class Trabajador(
    val id: String,
    val apellidos: String,
    val nombres: String,
    val tipoDoc: String
)