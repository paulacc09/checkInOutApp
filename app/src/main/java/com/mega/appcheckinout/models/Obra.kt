package com.mega.appcheckinout.models

data class Obra(
    val id: String,
    val codigo: String,
    val nombre: String,
    val ciudad: String,
    val direccion: String,
    val fechaInicio: String,
    val fechaFinEstimada: String = "",
    val responsableSISO: String,
    val descripcion: String = "",
    val estado: EstadoObra = EstadoObra.ACTIVA,
    val numeroTrabajadores: Int = 0
) {
    enum class EstadoObra {
        ACTIVA,
        FINALIZADA
    }

    fun estaActiva() = estado == EstadoObra.ACTIVA
    fun estaFinalizada() = estado == EstadoObra.FINALIZADA
}

data class ObraCompleta(
    val obra: Obra,
    val trabajadoresAsignados: List<TrabajadorCompleto> = emptyList(),
    val dispositivos: List<Dispositivo> = emptyList(),
    val estadisticas: EstadisticasObra = EstadisticasObra()
)

data class EstadisticasObra(
    val totalTrabajadores: Int = 0,
    val operativos: Int = 0,
    val inspectores: Int = 0,
    val encargados: Int = 0,
    val asistenciaHoy: Int = 0,
    val novedadesPendientes: Int = 0
)

data class Dispositivo(
    val id: String,
    val nombre: String,
    val tipo: TipoDispositivo,
    val estado: EstadoDispositivo,
    val obraAsignada: String,
    val responsable: String
) {
    enum class TipoDispositivo {
        TABLET,
        TELEFONO,
        LECTOR_BIOMETRICO
    }

    enum class EstadoDispositivo {
        ACTIVO,
        INACTIVO,
        BLOQUEADO
    }
}