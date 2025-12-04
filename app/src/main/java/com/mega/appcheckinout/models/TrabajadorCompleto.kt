package com.mega.appcheckinout.models

data class TrabajadorCompleto(
    val id: String,
    val apellido1: String,
    val apellido2: String,
    val nombre1: String,
    val nombre2: String,
    val tipoDoc: String,
    val rol: String,
    val subCargo: String,
    val obraAsignada: String,
    val biometriaRegistrada: Boolean,
    val estado: String
) {
    fun nombreCompleto() = "$apellido1 $apellido2 $nombre1 ${nombre2.takeIf { it.isNotEmpty() } ?: ""}".trim()

    fun toTrabajador() = Trabajador(
        id = id,
        apellidos = "$apellido1 $apellido2",
        nombres = "$nombre1 ${nombre2.takeIf { it.isNotEmpty() } ?: ""}".trim(),
        tipoDoc = tipoDoc
    )
}