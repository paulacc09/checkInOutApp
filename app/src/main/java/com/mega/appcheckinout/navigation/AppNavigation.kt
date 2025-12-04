package com.mega.appcheckinout.navigation


import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mega.appcheckinout.models.Trabajador
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
    var rolSeleccionado by remember { mutableStateOf("") }  // â† Nueva variable para guardar el rol

    // Guarda el trabajador que se está viendo/editando en detalle
    var trabajadorSeleccionado by remember { mutableStateOf<Trabajador?>(null) }


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
                onRolSeleccionado = { rol ->  // Ahora recibe el rol
                    rolSeleccionado = rol      // Guarda el rol
                    pantallaActual = "loginRol" // Navega a login
                },
                onVolver = { pantallaActual = "login" },
                colorPrimario = colorPrimario
            )

            "loginRol" -> LoginRolScreen(
                rolSeleccionado = rolSeleccionado,  //  Pasa el rol guardado
                onLoginExitoso = { pantallaActual = "dashboardAdmin" },
                onVolver = { pantallaActual = "seleccionRol" },
                colorPrimario = colorPrimario
            )

            // ========== DASHBOARD ADMINISTRATIVO ==========

            "dashboardAdmin" -> DashboardAdminScreen(
                onCerrarSesion = { pantallaActual = "seleccionRol" },  // Vuelve al login
                onGestionPersonal = {pantallaActual = "gestionPersonal"},
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

            // ========== DETALLE DE TRABAJADOR ==========

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
 * FUTURAS MEJORAS:
 * - Migrar a Jetpack Navigation Compose para navegación más robusta
 * - Implementar ViewModel para manejar estados globales
 * - Agregar animaciones de transición entre pantallas
 */


