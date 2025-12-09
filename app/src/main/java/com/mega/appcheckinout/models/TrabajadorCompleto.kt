package com.mega.appcheckinout.models

data class TrabajadorCompleto(
    val id: String,
    // Datos Personales
    val primerNombre: String,
    val segundoNombre: String = "",
    val primerApellido: String,
    val segundoApellido: String = "",
    val fechaNacimiento: String,
    val tipoDocumento: String,
    val numeroDocumento: String,
    val telefono: String,
    val direccion: String,

    // Datos Laborales
    val rol: String,
    val subCargo: String = "",
    val actividad: String = "",
    val obraAsignada: String,
    val arl: String,
    val eps: String,
    val fechaExamen: String,
    val fechaCursoAlturas: String,

    // Estado
    val biometriaRegistrada: Boolean = false,
    val estado: String = "Activo"
) {
    fun nombreCompleto() = "$primerApellido $segundoApellido $primerNombre $segundoNombre".trim()

    fun nombresCompletos() = "$primerNombre $segundoNombre".trim()

    fun apellidosCompletos() = "$primerApellido $segundoApellido".trim()

    fun toTrabajador() = Trabajador(
        id = id,
        apellidos = apellidosCompletos(),
        nombres = nombresCompletos(),
        tipoDoc = tipoDocumento
    )
}