package com.mega.appcheckinout.models

data class Dispositivo(
    val id: String = "",
    val nombre: String,
    val tipo: TipoDispositivo,
    val obraId: String,
    val obraNombre: String,
    val responsableId: String,
    val responsableNombre: String,
    val metodosHabilitados: List<MetodoMarcaje>,
    val estado: EstadoDispositivo = EstadoDispositivo.ACTIVO,
    val fechaRegistro: Long = System.currentTimeMillis(),
    val ultimoUso: Long? = null,
    val motivoBloqueo: String? = null
)


enum class TipoDispositivo(val displayName: String) {
    TABLET("Tablet"),
    TELEFONO("Teléfono"),
    BIOMETRICO("Lector Biométrico")
}

enum class MetodoMarcaje(val displayName: String) {
    QR("Código QR"),
    CEDULA("Número de Cédula"),
    BIOMETRIA("Biometría")
}

enum class EstadoDispositivo(val displayName: String, val color: Long) {
    ACTIVO("Activo", 0xFF4CAF50),
    INACTIVO("Inactivo", 0xFF9E9E9E),
    BLOQUEADO("Bloqueado", 0xFFF44336)
}

data class LogDispositivo(
    val id: String = "",
    val dispositivoId: String,
    val accion: String,
    val usuarioId: String,
    val usuarioNombre: String,
    val timestamp: Long = System.currentTimeMillis(),
    val detalles: String? = null
)