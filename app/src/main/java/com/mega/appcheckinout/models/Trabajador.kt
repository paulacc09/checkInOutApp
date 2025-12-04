package com.mega.appcheckinout.models

data class Trabajador(
    val id: String,
    val apellidos: String,
    val nombres: String,
    val tipoDoc: String
) {
    fun nombreCompleto() = "$apellidos $nombres"
}