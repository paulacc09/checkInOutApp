package com.mega.appcheckinout.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mega.appcheckinout.models.Trabajador
import com.mega.appcheckinout.models.TrabajadorCompleto
import com.mega.appcheckinout.screens.*

/**
 * CheckInOutApp - Controlador central de navegación de la aplicación
 *
 * Este composable maneja:
 * 1. El estado de navegación (qué pantalla mostrar)
 * 2. El estado global de la app (rol seleccionado, trabajador seleccionado, etc.)
 * 3. Las transiciones entre pantallas
 *
 * Flujo de navegación:
 * login → registro/confirmación
 *      → seleccionRol → loginRol → dashboardAdmin → gestionPersonal
 *                                                  → registrarTrabajador
 *                                                  → listadoTrabajadores → verPerfil
 *                                                                        → editarTrabajador
 */
@Composable
fun CheckInOutApp() {
    // ========== ESTADOS DE NAVEGACIÓN ==========

    // Controla qué pantalla se muestra actualmente
    var pantallaActual by remember { mutableStateOf("login") }

    // Guarda el rol que el usuario seleccionó (Administrativo, Inspector SST, Encargado)
    var rolSeleccionado by remember { mutableStateOf("") }

    // Guarda el trabajador completo para ver el perfil con todos los datos
    var trabajadorCompletoSeleccionado by remember { mutableStateOf<TrabajadorCompleto?>(null) }

    // Colores del tema
    val colorPrimario = Color(0xFF4A6FA5)
    val colorSecundario = Color(0xFF8FB8C8)
    val colorFondo = Color(0xFFE8EFF5)

    // ========== CONTENEDOR PRINCIPAL ==========
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorFondo
    ) {
        // Switch de navegación - determina qué pantalla mostrar
        when(pantallaActual) {

            // ========== FLUJO DE AUTENTICACIÓN ==========

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

            // ========== SELECCIÓN DE ROL ==========

            "seleccionRol" -> SeleccionRolScreen(
                onRolSeleccionado = { rol ->
                    rolSeleccionado = rol
                    pantallaActual = "loginRol"
                },
                onVolver = { pantallaActual = "login" },
                colorPrimario = colorPrimario
            )

            "loginRol" -> LoginRolScreen(
                rolSeleccionado = rolSeleccionado,
                onLoginExitoso = { pantallaActual = "dashboardAdmin" },
                onVolver = { pantallaActual = "seleccionRol" },
                colorPrimario = colorPrimario
            )

            // ========== DASHBOARD ADMINISTRATIVO ==========

            "dashboardAdmin" -> DashboardAdminScreen(
                onCerrarSesion = { pantallaActual = "seleccionRol" },
                onGestionPersonal = { pantallaActual = "gestionPersonal" },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            // ========== GESTIÓN DE PERSONAL ==========

            "gestionPersonal" -> GestionPersonalScreen(
                onRegistrarNuevo = { pantallaActual = "registrarTrabajador" },
                onListaTrabajadores = { pantallaActual = "listadoTrabajadores" },
                onAsignaciones = {  // TODO: Implementar pantalla de asignaciones activas
                },
                onReportes = { // TODO: Implementar pantalla de reportes
                },
                onVolver = { pantallaActual = "dashboardAdmin" },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "registrarTrabajador" -> RegistrarTrabajadorScreen(
                onVolver = { pantallaActual = "gestionPersonal" },
                onRegistrarBiometrico = { // TODO: Implementar registro biométrico
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "listadoTrabajadores" -> ListadoTrabajadoresScreen(
                onVolver = { pantallaActual = "gestionPersonal" },
                onRegistrarPersonal = { pantallaActual = "registrarTrabajador" },
                onRegistrarBiometrico = { //TODO: Implementar registro biométrico desde listado
                },
                onVerPerfil = { trabajadorCompleto ->
                    // Recibe TrabajadorCompleto directamente
                    trabajadorCompletoSeleccionado = trabajadorCompleto
                    pantallaActual = "verPerfil"
                },
                onEditarTrabajador = { trabajadorCompleto ->
                    // CAMBIADO: Ahora también recibe TrabajadorCompleto
                    trabajadorCompletoSeleccionado = trabajadorCompleto
                    pantallaActual = "editarTrabajador"
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            // ========== DETALLE DE TRABAJADOR ==========

            "verPerfil" -> {
                // MODIFICADO: Ahora usa TrabajadorCompleto
                if (trabajadorCompletoSeleccionado != null) {
                    VerPerfilCompletoScreen(
                        trabajador = trabajadorCompletoSeleccionado!!,
                        onVolver = {
                            trabajadorCompletoSeleccionado = null
                            pantallaActual = "listadoTrabajadores"
                        },
                        colorPrimario = colorPrimario
                    )
                } else {
                    pantallaActual = "listadoTrabajadores"
                }
            }

            "editarTrabajador" -> {
                if (trabajadorCompletoSeleccionado != null) {
                    EditarTrabajadorCompletoScreen(
                        trabajador = trabajadorCompletoSeleccionado!!,
                        onVolver = {
                            trabajadorCompletoSeleccionado = null
                            pantallaActual = "listadoTrabajadores"
                        },
                        onGuardar = { actualizado ->
                            // Aquí deberías guardar los cambios en tu fuente de datos
                            trabajadorCompletoSeleccionado = actualizado
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

/**
 * NOTAS IMPORTANTES:
 *
 * 1. Este archivo SOLO maneja navegación, no lógica de negocio
 * 2. Cada pantalla es responsable de su propia UI y validaciones
 * 3. Los estados (rolSeleccionado, trabajadorSeleccionado) se mantienen aquí
 *    porque necesitan persistir entre navegaciones
 * 4. Para agregar una nueva pantalla:
 *    - Crear el archivo Screen.kt en ui/screens/
 *    - Agregar un nuevo case en el when()
 *    - Conectar con las callbacks de navegación
 *
 * CAMBIOS RECIENTES:
 * - Agregado trabajadorCompletoSeleccionado para manejar datos completos
 * - Modificado "verPerfil" para usar VerPerfilCompletoScreen
 * - ListadoTrabajadoresScreen ahora pasa TrabajadorCompleto directamente
 *
 * FUTURAS MEJORAS:
 * - Migrar a Jetpack Navigation Compose para navegación más robusta
 * - Implementar ViewModel para manejar estados globales
 * - Agregar animaciones de transición entre pantallas
 * - Implementar carga de datos completos desde base de datos real
 */