package com.mega.appcheckinout.models

data class Usuario(
    val id: String = "",
    val nombreCompleto: String = "",
    val tipoDocumento: String = "CC",
    val numeroDocumento: String = "",
    val telefono: String = "",
    val email: String = "",
    val fotoUrl: String = "",

    // Credenciales (solo para Admin, Encargado, Inspector)
    val nombreUsuario: String = "",
    val contraseñaTemporal: String = "",
    val requiereCambioContraseña: Boolean = true,

    // Rol y permisos
    val rol: RolUsuario = RolUsuario.OPERATIVO,
    val obraAsignadaId: String? = null,
    val obraAsignadaNombre: String = "",

    // Seguridad
    val estado: EstadoUsuario = EstadoUsuario.ACTIVO,
    val autenticacion2FA: Boolean = false,
    val permitirMultiplesSesiones: Boolean = false,
    val requiereCambioContraseñaCada90Dias: Boolean = false,

    // Auditoría
    val fechaCreacion: Long = System.currentTimeMillis(),
    val ultimoAcceso: Long? = null,
    val creadoPor: String = "",
    val motivoBloqueo: String = ""
)

enum class RolUsuario(val displayName: String, val descripcion: String) {
    ADMINISTRADOR("Administrador", "Acceso total al sistema"),
    ENCARGADO("Encargado", "Gestión de obra asignada"),
    INSPECTOR_SST("Inspector/a SST", "Control de asistencia y seguridad"),
    OPERATIVO("Operativo", "Solo marcaje de asistencia")
}

enum class EstadoUsuario(val displayName: String) {
    ACTIVO("Activo"),
    INACTIVO("Inactivo"),
    BLOQUEADO("Bloqueado")
}

data class SesionActiva(
    val usuarioId: String,
    val nombreUsuario: String,
    val rol: RolUsuario,
    val dispositivo: String,
    val ip: String,
    val ubicacion: String,
    val horaInicio: Long,
    val ultimaActividad: Long
)

data class EventoAuditoria(
    val id: String = "",
    val usuarioId: String = "",
    val nombreUsuario: String = "",
    val rol: RolUsuario = RolUsuario.OPERATIVO,
    val accion: TipoAccion = TipoAccion.LOGIN_EXITOSO,
    val descripcion: String = "",
    val dispositivo: String = "",
    val ip: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val exitoso: Boolean = true
)

enum class TipoAccion(val displayName: String) {
    LOGIN_EXITOSO("Inicio de sesión exitoso"),
    LOGIN_FALLIDO("Inicio de sesión fallido"),
    LOGOUT("Cierre de sesión"),
    CAMBIO_CONTRASEÑA("Cambio de contraseña"),
    CREACION_USUARIO("Creación de usuario"),
    EDICION_USUARIO("Edición de usuario"),
    ELIMINACION_USUARIO("Eliminación de usuario"),
    BLOQUEO_CUENTA("Bloqueo de cuenta"),
    DESBLOQUEO_CUENTA("Desbloqueo de cuenta"),
    MODIFICACION_PERMISOS("Modificación de permisos"),
    CIERRE_SESION_REMOTO("Cierre de sesión remoto")
}