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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
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
import androidx.compose.foundation.background
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import com.mega.appcheckinout.ui.theme.BotonVolver
import com.mega.appcheckinout.screens.detalle.componentes.TabRolesUsuarios
import com.mega.appcheckinout.screens.administrador.novedades.NovedadesScreen
import com.mega.appcheckinout.screens.administrador.dispositivos.GestionDispositivosScreen
import com.mega.appcheckinout.models.Novedad
import com.mega.appcheckinout.models.Dispositivo
import com.mega.appcheckinout.screens.administrador.novedades.DetalleNovedadScreen
import com.mega.appcheckinout.screens.administrador.dispositivos.DetalleDispositivoScreen
import com.mega.appcheckinout.screens.inspector_sst.*
import com.mega.appcheckinout.screens.encargado.asistencia.AsistenciaDiariaEncargadoScreen
import com.mega.appcheckinout.screens.encargado.asistencia.MarcarAsistenciaEncargadoScreen
import com.mega.appcheckinout.screens.encargado.novedades.ConsultarNovedadesScreen
import com.mega.appcheckinout.screens.encargado.novedades.CrearNovedadEncargadoScreen
import com.mega.appcheckinout.screens.encargado.obra.DetalleObraEncargadoScreen
import com.mega.appcheckinout.screens.encargado.personal.GestionPersonalEncargadoScreen
import com.mega.appcheckinout.screens.encargado.personal.SolicitarTraspasoScreen
import com.mega.appcheckinout.screens.encargado.reportes.ReportesEncargadoScreen
import com.mega.appcheckinout.screens.encargado.reportes.VistaReporteObraScreen
import com.mega.appcheckinout.screens.encargado.DashboardEncargadoScreen


@Composable
fun CheckInOutApp() {
    // ========== ESTADOS DE NAVEGACIÓN ==========
    var pantallaActual by remember { mutableStateOf("login") }
    var rolSeleccionado by remember { mutableStateOf("") }
    var trabajadorCompletoSeleccionado by remember { mutableStateOf<TrabajadorCompleto?>(null) }
    var usuarioSeleccionado by remember { mutableStateOf<Usuario?>(null) }
    var modoEdicionUsuario by remember { mutableStateOf(false) }
    var obraSeleccionada by remember { mutableStateOf<Obra?>(null) }
    var pantallaAnteriorObra by remember { mutableStateOf("listadoObras") }
    var novedadSeleccionada by remember { mutableStateOf<Novedad?>(null) }
    var dispositivoSeleccionado by remember { mutableStateOf<Dispositivo?>(null) }
    var pantallaAnteriorReportes by remember { mutableStateOf("gestionPersonal") }

    // ✅ NUEVO: Estado para Inspector SST
    var jornadaAbierta by remember { mutableStateOf(false) }
    var registroAsistenciaSeleccionado by remember { mutableStateOf<RegistroAsistenciaTemp?>(null) }

    // ✅ NUEVO: Estados para Encargado
    var inspectorPresente by remember { mutableStateOf(true) }
    var obraEncargado by remember { mutableStateOf("Edificio Mandarino") } // TODO: Desde BD


    // Colores del tema
    val colorPrimario = Color(0xFF4A6FA5)
    val colorSecundario = Color(0xFF8FB8C8)
    val colorFondo = Color(0xFFE8EFF5)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorFondo
    ) {
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
                onLoginExitoso = {
                    // ✅ Redirige según el rol
                    when (rolSeleccionado) {
                        "Administrativo" -> pantallaActual = "dashboardAdmin"
                        "Inspector SST" -> pantallaActual = "dashboardSST"
                        else -> pantallaActual = "dashboardAdmin" // Fallback
                    }
                },
                onVolver = { pantallaActual = "seleccionRol" },
                colorPrimario = colorPrimario
            )

            // ========== DASHBOARD INSPECTOR SST ==========
            "dashboardSST" -> DashboardSSTScreen(
                obraAsignada = "Edificio Mandarino", // TODO: Obtener de BD
                onCerrarSesion = { pantallaActual = "seleccionRol" },
                onAbrirAsistencia = { jornadaAbierta = true },
                onCerrarAsistencia = { jornadaAbierta = false },
                onMarcarAsistencia = { pantallaActual = "marcarAsistenciaSST" },
                onGestionNovedades = { pantallaActual = "gestionNovedadesSST" },
                onRegistrarPersonal = { pantallaActual = "registrarTrabajador" },
                onReportes = { pantallaActual = "reportesSST" },
                onVerPersonal = { pantallaActual = "gestionPersonalSST" },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "marcarAsistenciaSST" -> MarcarAsistenciaSSTScreen(
                obraAsignada = "Edificio Mandarino",
                jornadaAbierta = jornadaAbierta,
                onVolver = { pantallaActual = "dashboardSST" },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "asistenciaDiariaSST" -> AsistenciaDiariaScreen(
                obraAsignada = "Edificio Mandarino",
                onVolver = { pantallaActual = "dashboardSST" },
                onVerPerfil = { registro ->
                    registroAsistenciaSeleccionado = registro
                    pantallaActual = "verPerfilSST"
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "gestionNovedadesSST" -> GestionNovedadesScreen(
                obraAsignada = "Edificio Mandarino",
                onVolver = { pantallaActual = "dashboardSST" },
                onVerDetalle = { novedad ->
                    novedadSeleccionada = novedad
                    pantallaActual = "detalleNovedadSST"
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "detalleNovedadSST" -> {
                if (novedadSeleccionada != null) {
                    DetalleNovedadSSTScreen(
                        novedad = novedadSeleccionada!!,
                        onVolver = {
                            novedadSeleccionada = null
                            pantallaActual = "gestionNovedadesSST"
                        },
                        onAprobar = {
                            // TODO: Actualizar en BD
                            novedadSeleccionada = null
                            pantallaActual = "gestionNovedadesSST"
                        },
                        onRechazar = { motivo ->
                            // TODO: Actualizar en BD con motivo
                            novedadSeleccionada = null
                            pantallaActual = "gestionNovedadesSST"
                        },
                        colorPrimario = colorPrimario,
                        colorSecundario = colorSecundario
                    )
                } else {
                    pantallaActual = "gestionNovedadesSST"
                }
            }

            "gestionPersonalSST" -> GestionPersonalSSTScreen(
                obraAsignada = "Edificio Mandarino",
                onVolver = { pantallaActual = "dashboardSST" },
                onVerPerfil = { trabajador ->
                    trabajadorCompletoSeleccionado = trabajador
                    pantallaActual = "verPerfil"
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "reportesSST" -> ReportesSSTScreen(
                obraAsignada = "Edificio Mandarino",
                onVolver = { pantallaActual = "dashboardSST" },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "dispositivosSST" -> DispositivosSSTScreen(
                obraAsignada = "Edificio Mandarino",
                onVolver = { pantallaActual = "dashboardSST" },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

                // ========== DASHBOARD ENCARGADO ==========
            "dashboardEncargado" -> DashboardEncargadoScreen(
            obraAsignada = obraEncargado,
            nombreEncargado = "Ana Ramírez", // TODO: Obtener del usuario logueado
            onCerrarSesion = { pantallaActual = "seleccionRol" },
            onVerPersonal = { pantallaActual = "gestionPersonalEncargado" },
            onAsistenciaDiaria = { pantallaActual = "asistenciaDiariaEncargado" },
            onConsultarNovedades = { pantallaActual = "consultarNovedadesEncargado" },
            onReportes = { pantallaActual = "reportesEncargado" },
            onDetalleObra = { pantallaActual = "detalleObraEncargado" },
            onSolicitarTraspaso = { pantallaActual = "solicitarTraspaso" },
            colorPrimario = colorPrimario,
            colorSecundario = colorSecundario
        )

            // ========== GESTIÓN DE PERSONAL - ENCARGADO ==========
            "gestionPersonalEncargado" -> GestionPersonalEncargadoScreen(
                obraAsignada = obraEncargado,
                obraId = "1", // TODO: Obtener ID real
                onVolver = { pantallaActual = "dashboardEncargado" },
                onVerPerfil = { trabajador ->
                    trabajadorCompletoSeleccionado = trabajador
                    pantallaActual = "verPerfil"
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            // ========== ASISTENCIA - ENCARGADO ==========
            "asistenciaDiariaEncargado" -> AsistenciaDiariaEncargadoScreen(
                obraAsignada = obraEncargado,
                inspectorPresente = inspectorPresente,
                onVolver = { pantallaActual = "dashboardEncargado" },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "marcarAsistenciaEncargado" -> MarcarAsistenciaEncargadoScreen(
                obraAsignada = obraEncargado,
                inspectorPresente = inspectorPresente,
                onVolver = { pantallaActual = "asistenciaDiariaEncargado" },
                onMarcarAsistencia = { cedula ->
                    // TODO: Guardar marcaje en BD
                    pantallaActual = "asistenciaDiariaEncargado"
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            // ========== NOVEDADES - ENCARGADO ==========
            "consultarNovedadesEncargado" -> ConsultarNovedadesScreen(
                obraAsignada = obraEncargado,
                onVolver = { pantallaActual = "dashboardEncargado" },
                onVerDetalle = { novedad ->
                    novedadSeleccionada = novedad
                    pantallaActual = "detalleNovedadEncargado"
                },
                onCrearNovedad = { pantallaActual = "crearNovedadEncargado" },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "crearNovedadEncargado" -> CrearNovedadEncargadoScreen(
                obraAsignada = obraEncargado,
                onVolver = { pantallaActual = "consultarNovedadesEncargado" },
                onGuardar = {
                    // TODO: Guardar en BD
                    pantallaActual = "consultarNovedadesEncargado"
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "detalleNovedadEncargado" -> {
                if (novedadSeleccionada != null) {
                    DetalleNovedadSSTScreen(
                        novedad = novedadSeleccionada!!,
                        onVolver = {
                            novedadSeleccionada = null
                            pantallaActual = "consultarNovedadesEncargado"
                        },
                        onAprobar = {
                            // Encargado NO puede aprobar
                            novedadSeleccionada = null
                            pantallaActual = "consultarNovedadesEncargado"
                        },
                        onRechazar = { motivo ->
                            // Encargado NO puede rechazar
                            novedadSeleccionada = null
                            pantallaActual = "consultarNovedadesEncargado"
                        },
                        colorPrimario = colorPrimario,
                        colorSecundario = colorSecundario
                    )
                } else {
                    pantallaActual = "consultarNovedadesEncargado"
                }
            }

            // ========== OBRA - ENCARGADO ==========
            "detalleObraEncargado" -> DetalleObraEncargadoScreen(
                obraId = "1", // TODO: ID de la obra del encargado
                onVolver = { pantallaActual = "dashboardEncargado" },
                onSolicitarTraspaso = { pantallaActual = "solicitarTraspaso" },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "solicitarTraspaso" -> SolicitarTraspasoScreen(
                obraAsignada = obraEncargado,
                obraIdActual = "1",
                onVolver = { pantallaActual = "dashboardEncargado" },
                onSolicitudEnviada = {
                    // TODO: Guardar solicitud en BD
                    pantallaActual = "dashboardEncargado"
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            // ========== REPORTES - ENCARGADO ==========
            "reportesEncargado" -> ReportesEncargadoScreen(
                obraAsignada = obraEncargado,
                obraId = "1",
                onVolver = { pantallaActual = "dashboardEncargado" },
                onGenerarReporte = { tipo, inicio, fin ->
                    // TODO: Generar reporte
                    pantallaActual = "vistaReporteObra"
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "vistaReporteObra" -> VistaReporteObraScreen(
                obraAsignada = obraEncargado,
                tipoReporte = "Asistencia Diaria",
                fechaInicio = "01/01/2025",
                fechaFin = "15/01/2025",
                filtroRol = "Todos",
                onVolver = { pantallaActual = "reportesEncargado" },
                onExportarCSV = {
                    // TODO: Exportar CSV
                },
                onExportarPDF = {
                    // TODO: Exportar PDF
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            // ========== DASHBOARD ADMINISTRATIVO ==========
            "dashboardAdmin" -> DashboardAdminScreen(
                onCerrarSesion = { pantallaActual = "seleccionRol" },
                onGestionPersonal = { pantallaActual = "gestionPersonal" },
                onGestionObras = { pantallaActual = "gestionObras" },
                onGestionRolesUsuarios = { pantallaActual = "gestionRolesUsuarios" },
                onGestionNovedades = { pantallaActual = "gestionNovedades" },
                onGestionDispositivos = { pantallaActual = "gestionDispositivos" },
                onReportes = {
                    pantallaAnteriorReportes = "dashboardAdmin"
                    pantallaActual = "reportes"
                },
                onCrearObra = { pantallaActual = "crearObra" },
                onRegistrarPersonal = { pantallaActual = "registrarTrabajador" },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            // ========== GESTIÓN DE PERSONAL ==========
            "gestionPersonal" -> GestionPersonalScreen(
                onRegistrarNuevo = { pantallaActual = "registrarTrabajador" },
                onListaTrabajadores = { pantallaActual = "listadoTrabajadores" },
                onAsignaciones = { pantallaActual = "asignacionesActivas" },
                onReportes = {
                    pantallaAnteriorReportes = "gestionPersonal"
                    pantallaActual = "reportes"
                },
                onVolver = { pantallaActual = "dashboardAdmin" },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "registrarTrabajador" -> RegistrarTrabajadorScreen(
                onVolver = {
                    pantallaActual = if (rolSeleccionado == "Inspector SST") {
                        "dashboardSST"
                    } else {
                        "gestionPersonal"
                    }
                },
                onRegistrarBiometrico = {},
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "listadoTrabajadores" -> ListadoTrabajadoresScreen(
                onVolver = { pantallaActual = "gestionPersonal" },
                onRegistrarPersonal = { pantallaActual = "registrarTrabajador" },
                onRegistrarBiometrico = {},
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

            "asignacionesActivas" -> AsignacionesActivasScreen(
                onVolver = { pantallaActual = "gestionPersonal" },
                onVerPerfil = { trabajadorCompleto ->
                    trabajadorCompletoSeleccionado = trabajadorCompleto
                    pantallaActual = "verPerfil"
                },
                onAsignarNuevoTrabajador = { obraId ->
                    pantallaActual = "registrarTrabajador"
                },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "reportes" -> ReportesScreen(
                onVolver = { pantallaActual = pantallaAnteriorReportes },
                colorPrimario = colorPrimario,
                colorSecundario = colorSecundario
            )

            "verPerfil" -> {
                if (trabajadorCompletoSeleccionado != null) {
                    VerPerfilCompletoScreen(
                        trabajador = trabajadorCompletoSeleccionado!!,
                        onVolver = {
                            trabajadorCompletoSeleccionado = null
                            pantallaActual = if (rolSeleccionado == "Inspector SST") {
                                "gestionPersonalSST"
                            } else {
                                "listadoTrabajadores"
                            }
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
                onObraCreada = { pantallaActual = "listadoObras" },
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
                        onEditarObra = {},
                        onVerTrabajador = { trabajador ->
                            trabajadorCompletoSeleccionado = trabajador
                            pantallaActual = "verPerfil"
                        },
                        onAsignarTrabajador = {},
                        onGenerarReporte = { pantallaActual = "reportes" },
                        colorPrimario = colorPrimario,
                        colorSecundario = colorSecundario
                    )
                } else {
                    pantallaActual = "listadoObras"
                }
            }

            // ========== GESTIÓN DE ROLES Y USUARIOS ==========
            "gestionRolesUsuarios" -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFE8EFF5))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFB8D4E3))
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BotonVolver(
                            onClick = { pantallaActual = "dashboardAdmin" },
                            colorIcono = Color.White,
                            colorFondo = colorPrimario
                        )
                        Text(
                            text = "GESTIÓN DE ROLES Y USUARIOS",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorPrimario,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(48.dp))
                    }
                    TabRolesUsuarios(colorPrimario, colorSecundario)
                }
            }

            "crearUsuario" -> {
                CrearEditarUsuarioScreen(
                    usuario = null,
                    onGuardar = { pantallaActual = "gestionRolesUsuarios" },
                    onCancelar = { pantallaActual = "gestionRolesUsuarios" }
                )
            }

            "editarUsuario" -> {
                if (usuarioSeleccionado != null) {
                    CrearEditarUsuarioScreen(
                        usuario = usuarioSeleccionado,
                        onGuardar = {
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

            "detalleUsuario" -> {
                if (usuarioSeleccionado != null) {
                    DetalleUsuarioScreen(
                        usuario = usuarioSeleccionado!!,
                        onEditar = { usuario ->
                            usuarioSeleccionado = usuario
                            pantallaActual = "editarUsuario"
                        },
                        onBloquear = {},
                        onEliminar = {
                            usuarioSeleccionado = null
                            pantallaActual = "gestionRolesUsuarios"
                        },
                        onRestablecerContraseña = {},
                        onCerrarSesiones = {},
                        onVolver = {
                            usuarioSeleccionado = null
                            pantallaActual = "gestionRolesUsuarios"
                        }
                    )
                } else {
                    pantallaActual = "gestionRolesUsuarios"
                }
            }

            // ========== GESTIÓN DE NOVEDADES ==========
            "gestionNovedades" -> {
                NovedadesScreen(
                    onVolver = { pantallaActual = "dashboardAdmin" },
                    onCrearNovedad = { pantallaActual = "crearNovedad" },
                    onVerDetalleNovedad = { novedad ->
                        novedadSeleccionada = novedad
                        pantallaActual = "detalleNovedad"
                    },
                    colorPrimario = colorPrimario,
                    colorSecundario = colorSecundario
                )
            }

            "detalleNovedad" -> {
                if (novedadSeleccionada != null) {
                    DetalleNovedadScreen(
                        novedad = novedadSeleccionada!!,
                        onVolver = {
                            novedadSeleccionada = null
                            pantallaActual = "gestionNovedades"
                        },
                        onAprobar = {
                            novedadSeleccionada = null
                            pantallaActual = "gestionNovedades"
                        },
                        onRechazar = {
                            novedadSeleccionada = null
                            pantallaActual = "gestionNovedades"
                        },
                        colorPrimario = colorPrimario,
                        colorSecundario = colorSecundario
                    )
                } else {
                    pantallaActual = "gestionNovedades"
                }
            }

            "crearNovedad" -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFE8EFF5))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFB8D4E3))
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BotonVolver(
                            onClick = { pantallaActual = "gestionNovedades" },
                            colorIcono = Color.White,
                            colorFondo = colorPrimario
                        )
                        Text(
                            text = "CREAR NUEVA NOVEDAD",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorPrimario,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(48.dp))
                    }

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Formulario de Crear Novedad\n(Próximamente)",
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // ========== GESTIÓN DE DISPOSITIVOS ==========
            "gestionDispositivos" -> {
                GestionDispositivosScreen(
                    onVolver = { pantallaActual = "dashboardAdmin" },
                    onRegistrarDispositivo = { pantallaActual = "registrarDispositivo" },
                    onVerDetalleDispositivo = { dispositivo ->
                        dispositivoSeleccionado = dispositivo
                        pantallaActual = "detalleDispositivo"
                    },
                    colorPrimario = colorPrimario,
                    colorSecundario = colorSecundario
                )
            }

            "detalleDispositivo" -> {
                if (dispositivoSeleccionado != null) {
                    DetalleDispositivoScreen(
                        dispositivo = dispositivoSeleccionado!!,
                        onVolver = {
                            dispositivoSeleccionado = null
                            pantallaActual = "gestionDispositivos"
                        },
                        onCambiarEstado = {
                            dispositivoSeleccionado = null
                            pantallaActual = "gestionDispositivos"
                        },
                        onEliminar = {
                            dispositivoSeleccionado = null
                            pantallaActual = "gestionDispositivos"
                        },
                        colorPrimario = colorPrimario,
                        colorSecundario = colorSecundario
                    )
                } else {
                    pantallaActual = "gestionDispositivos"
                }
            }

            "registrarDispositivo" -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFE8EFF5))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFB8D4E3))
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BotonVolver(
                            onClick = { pantallaActual = "gestionDispositivos" },
                            colorIcono = Color.White,
                            colorFondo = colorPrimario
                        )
                        Text(
                            text = "REGISTRAR DISPOSITIVO",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorPrimario,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(48.dp))
                    }

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Formulario de Registrar Dispositivo\n(Próximamente)",
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}