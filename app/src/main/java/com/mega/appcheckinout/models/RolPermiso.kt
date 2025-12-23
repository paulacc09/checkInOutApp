package com.mega.appcheckinout.models

data class PermisoRol(
    val funcion: String,
    val administrador: Boolean,
    val encargado: Boolean,
    val inspectorSST: Boolean,
    val operativo: Boolean
)

object MatrizPermisos {
    val permisos = listOf(
        PermisoRol("Crear/editar empresas", true, false, false, false),
        PermisoRol("Gestión de usuarios", true, false, false, false),
        PermisoRol("Crear/finalizar obras", true, false, false, false),
        PermisoRol("Registrar personal", true, true, true, false),
        PermisoRol("Gestionar dispositivos", true, false, false, false),
        PermisoRol("Abrir/cerrar asistencia diaria", true, true, true, false),
        PermisoRol("Marcar asistencia", true, true, true, true),
        PermisoRol("Registrar novedades", true, true, true, false),
        PermisoRol("Aprobar/rechazar novedades", true, false, true, false),
        PermisoRol("Generar reportes", true, true, true, false),
        PermisoRol("Exportar datos (CSV/PDF)", true, true, true, false),
        PermisoRol("Ver auditoría completa", true, false, false, false),
        PermisoRol("Traspasar trabajadores", true, true, false, false),
        PermisoRol("Retirar trabajadores", true, true, false, false)
    )
}