@file:OptIn(ExperimentalMaterial3Api::class)
package com.mega.appcheckinout.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mega.appcheckinout.models.TrabajadorCompleto
import com.mega.appcheckinout.models.Usuario
import com.mega.appcheckinout.models.Obra
import com.mega.appcheckinout.screens.administrador.roles_usuarios.TabRolesUsuarios
import com.mega.appcheckinout.screens.administrador.roles_usuarios.CrearEditarUsuarioScreen
import com.mega.appcheckinout.screens.administrador.roles_usuarios.DetalleUsuarioScreen
import androidx.compose.material3.ExperimentalMaterial3Api
import com.mega.appcheckinout.screens.administrador.DashboardAdminScreen
import com.mega.appcheckinout.screens.administrador.gestion_obras.CrearObraScreen
import com.mega.appcheckinout.screens.administrador.gestion_obras.DetalleObraScreen
import com.mega.appcheckinout.screens.administrador.gestion_obras.GestionObrasScreen
import com.mega.appcheckinout.screens.administrador.gestion_obras.ListadoObrasScreen
import com.mega.appcheckinout.screens.administrador.gestion_obras.ObrasActivasScreen
import com.mega.appcheckinout.screens.administrador.gestion_obras.ObrasFinalizadasScreen
import com.mega.appcheckinout.screens.administrador.gestion_personal.AsignacionesActivasScreen
import com.mega.appcheckinout.screens.administrador.gestion_personal.EditarTrabajadorCompletoScreen
import com.mega.appcheckinout.screens.administrador.gestion_personal.GestionPersonalScreen
import com.mega.appcheckinout.screens.administrador.gestion_personal.ListadoTrabajadoresScreen
import com.mega.appcheckinout.screens.administrador.gestion_personal.RegistrarTrabajadorScreen
import com.mega.appcheckinout.screens.administrador.gestion_personal.VerPerfilCompletoScreen
import com.mega.appcheckinout.screens.administrador.reportes.ReportesScreen
import com.mega.appcheckinout.screens.auth.ConfirmacionScreen
import com.mega.appcheckinout.screens.auth.LoginEmpresaScreen
import com.mega.appcheckinout.screens.auth.LoginRolScreen
import com.mega.appcheckinout.screens.auth.RegistroEmpresaScreen
import com.mega.appcheckinout.screens.auth.SeleccionRolScreen

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
 *      → seleccionRol → loginRol → dashboardAdmin → gestionPersonal → reportes
 *                                                  → gestionObras
 *                                                  → gestionRolesUsuarios ⬅️ NUEVO
 *                                                  → registrarTrabajador
 *                                                  → listadoTrabajadores → verPerfil
 *                                                                        → editarTrabajador
 *                                                  → asignacionesActivas
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

    // ⬅️ NUEVO: Estado para gestión de usuarios
    var usuarioSeleccionado by remember { mutableStateOf<Usuario?>(null) }
    var modoEdicionUsuario by remember { mutableStateOf(false) }

    var obraSeleccionada by remember { mutableStateOf<Obra?>(null) }

    var pantallaAnteriorObra by remember { mutableStateOf("listadoObras") }

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
                onGestionObras = { pantallaActual = "gestionObras" },
                onGestionRolesUsuarios = { pantallaActual = "gestionRolesUsuarios" }, // ⬅️ NUEVO
                onReportes = { pantallaActual = "reportes" },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            // ========== GESTIÓN DE PERSONAL ==========

            "gestionPersonal" -> GestionPersonalScreen(
                onRegistrarNuevo = { pantallaActual = "registrarTrabajador" },
                onListaTrabajadores = { pantallaActual = "listadoTrabajadores" },
                onAsignaciones = { pantallaActual = "asignacionesActivas" },
                onReportes = { pantallaActual = "reportes" },
                onVolver = { pantallaActual = "dashboardAdmin" },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "registrarTrabajador" -> RegistrarTrabajadorScreen(
                onVolver = { pantallaActual = "gestionPersonal" },
                onRegistrarBiometrico = {
                    // TODO: Implementar registro biométrico
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "listadoTrabajadores" -> ListadoTrabajadoresScreen(
                onVolver = { pantallaActual = "gestionPersonal" },
                onRegistrarPersonal = { pantallaActual = "registrarTrabajador" },
                onRegistrarBiometrico = {
                    // TODO: Implementar registro biométrico desde listado
                },
                onVerPerfil = { trabajadorCompleto ->
                    trabajadorCompletoSeleccionado = trabajadorCompleto
                    pantallaActual = "verPerfil"
                },
                onEditarTrabajador = { trabajadorCompleto ->
                    trabajadorCompletoSeleccionado = trabajadorCompleto
                    pantallaActual = "editarTrabajador"
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            // ========== ASIGNACIONES ACTIVAS ==========

            "asignacionesActivas" -> AsignacionesActivasScreen(
                onVolver = { pantallaActual = "gestionPersonal" },
                onVerPerfil = { trabajadorCompleto ->
                    trabajadorCompletoSeleccionado = trabajadorCompleto
                    pantallaActual = "verPerfil"
                },
                onAsignarNuevoTrabajador = { obraId ->
                    // TODO: Implementar asignación directa a obra específica
                    pantallaActual = "registrarTrabajador"
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            // ========== REPORTES Y EXPORTACIÓN ==========

            "reportes" -> ReportesScreen(
                onVolver = { pantallaActual = "gestionPersonal" },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            // ========== DETALLE DE TRABAJADOR ==========

            "verPerfil" -> {
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
                            // TODO: Guardar cambios en la base de datos
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

            // ========== GESTIÓN DE OBRAS ==========

            "gestionObras" -> GestionObrasScreen(
                onCrearObra = { pantallaActual = "crearObra" },
                onListadoObras = { pantallaActual = "listadoObras" },
                onObrasActivas = { pantallaActual = "obrasActivas" },
                onObrasFinalizadas = { pantallaActual = "obrasFinalizadas" },
                onVolver = { pantallaActual = "dashboardAdmin" },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "crearObra" -> CrearObraScreen(
                onVolver = { pantallaActual = "gestionObras" },
                onObraCreada = {
                    pantallaActual = "listadoObras"
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "listadoObras" -> ListadoObrasScreen(
                onVolver = { pantallaActual = "gestionObras" },
                onCrearObra = { pantallaActual = "crearObra" },
                onVerDetalleObra = { obra ->
                    obraSeleccionada = obra
                    pantallaAnteriorObra = "listadoObras"
                    pantallaActual = "detalleObra"
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "obrasActivas" -> ObrasActivasScreen(
                onVolver = { pantallaActual = "gestionObras" },
                onVerDetalleObra = { obra ->
                    obraSeleccionada = obra
                    pantallaAnteriorObra = "obrasActivas"
                    pantallaActual = "detalleObra"
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "obrasFinalizadas" -> ObrasFinalizadasScreen(
                onVolver = { pantallaActual = "gestionObras" },
                onVerDetalleObra = { obra ->
                    obraSeleccionada = obra
                    pantallaAnteriorObra = "obrasFinalizadas"
                    pantallaActual = "detalleObra"
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "detalleObra" -> {
                if (obraSeleccionada != null) {
                    DetalleObraScreen(
                        obra = obraSeleccionada!!,
                        onVolver = {
                            obraSeleccionada = null
                            pantallaActual = pantallaAnteriorObra
                        },
                        onEditarObra = {
                            // TODO: Navegar a pantalla de edición
                        },
                        onVerTrabajador = { trabajador ->
                            trabajadorCompletoSeleccionado = trabajador
                            pantallaActual = "verPerfil"
                        },
                        onAsignarTrabajador = {
                            // TODO: Navegar a asignación de trabajadores
                        },
                        onGenerarReporte = {
                            pantallaActual = "reportes"
                        },
                        colorPrimario = colorPrimario,
                        colorSecundario = colorSecundario
                    )
                } else {
                    pantallaActual = "listadoObras"
                }
            }

            // ========== GESTIÓN DE ROLES Y USUARIOS ⬅️ NUEVO ==========

            "gestionRolesUsuarios" -> {
                // Pantalla con tabs: Usuarios, Roles y Permisos, Sesiones, Auditoría
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorFondo
                ) {
                    Column {
                        // Header simple para volver
                        TopAppBar(
                            title = { Text("Gestión de Roles y Usuarios") },
                            navigationIcon = {
                                androidx.compose.material3.IconButton(
                                    onClick = { pantallaActual = "dashboardAdmin" }
                                ) {
                                    androidx.compose.material3.Icon(
                                        painter = androidx.compose.ui.res.painterResource(android.R.drawable.ic_menu_revert),
                                        contentDescription = "Volver"
                                    )
                                }
                            }
                        )

                        // Componente principal con tabs
                        TabRolesUsuarios()
                    }
                }
            }

            // Crear nuevo usuario
            "crearUsuario" -> {
                CrearEditarUsuarioScreen(
                    usuario = null, // null = crear nuevo
                    onGuardar = { nuevoUsuario ->
                        // TODO: Guardar en base de datos
                        // Volver a la lista
                        pantallaActual = "gestionRolesUsuarios"
                    },
                    onCancelar = {
                        pantallaActual = "gestionRolesUsuarios"
                    }
                )
            }

            // Editar usuario existente
            "editarUsuario" -> {
                if (usuarioSeleccionado != null) {
                    CrearEditarUsuarioScreen(
                        usuario = usuarioSeleccionado,
                        onGuardar = { usuarioActualizado ->
                            // TODO: Actualizar en base de datos
                            usuarioSeleccionado = null
                            pantallaActual = "gestionRolesUsuarios"
                        },
                        onCancelar = {
                            usuarioSeleccionado = null
                            pantallaActual = "gestionRolesUsuarios"
                        }
                    )
                } else {
                    pantallaActual = "gestionRolesUsuarios"
                }
            }

            // Ver detalle completo del usuario
            "detalleUsuario" -> {
                if (usuarioSeleccionado != null) {
                    DetalleUsuarioScreen(
                        usuario = usuarioSeleccionado!!,
                        onEditar = { usuario ->
                            usuarioSeleccionado = usuario
                            pantallaActual = "editarUsuario"
                        },
                        onBloquear = { usuario ->
                            // TODO: Bloquear/desbloquear usuario
                            // Actualizar estado y recargar
                        },
                        onEliminar = { usuario ->
                            // TODO: Eliminar usuario
                            usuarioSeleccionado = null
                            pantallaActual = "gestionRolesUsuarios"
                        },
                        onRestablecerContraseña = { usuario ->
                            // TODO: Generar nueva contraseña y enviar email
                        },
                        onCerrarSesiones = { usuario ->
                            // TODO: Cerrar todas las sesiones del usuario
                        },
                        onVolver = {
                            usuarioSeleccionado = null
                            pantallaActual = "gestionRolesUsuarios"
                        }
                    )
                } else {
                    pantallaActual = "gestionRolesUsuarios"
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
 * 3. Los estados (rolSeleccionado, trabajadorCompletoSeleccionado, usuarioSeleccionado) se mantienen aquí
 *    porque necesitan persistir entre navegaciones
 * 4. Para agregar una nueva pantalla:
 *    - Crear el archivo Screen.kt en screens/
 *    - Agregar un nuevo case en el when()
 *    - Conectar con las callbacks de navegación
 *
 * CAMBIOS RECIENTES:
 * - Agregado módulo completo de Gestión de Roles y Usuarios
 * - Conectado desde Dashboard con navegación bidireccional
 * - Implementadas pantallas: Lista, Crear, Editar, Detalle de usuarios
 *
 * FUTURAS MEJORAS:
 * - Migrar a Jetpack Navigation Compose para navegación más robusta
 * - Implementar ViewModel para manejar estados globales
 * - Agregar animaciones de transición entre pantallas
 * - Implementar carga de datos completos desde base de datos real
 * - Agregar sistema de permisos por rol de usuario
 */