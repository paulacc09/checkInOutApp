package com.mega.appcheckinout.models

data class Novedad(
    val id: String = "",
    val trabajadorId: String,
    val trabajadorNombre: String,
    val tipoNovedad: TipoNovedad,
    val fechaInicio: String, // DD/MM/YYYY
    val fechaFin: String,    // DD/MM/YYYY
    val descripcion: String,
    val evidencias: List<Evidencia> = emptyList(),
    val estado: EstadoNovedad = EstadoNovedad.PENDIENTE,
    val creadoPor: String,
    val fechaCreacion: Long = System.currentTimeMillis(),
    val revisadoPor: String? = null,
    val fechaRevision: Long? = null,
    val motivoRechazo: String? = null,
    val obraId: String
)

enum class TipoNovedad(val displayName: String) {
    INCAPACIDAD_ARL("Incapacidad ARL"),
    INCAPACIDAD_EPS("Incapacidad EPS"),
    PERMISO("Permiso"),
    LICENCIA("Licencia"),
    JUSTIFICANTE_MEDICO("Justificante Médico"),
    OTRO("Otro")
}

enum class EstadoNovedad(val displayName: String) {
    PENDIENTE("Pendiente"),
    APROBADO("Aprobado"),
    RECHAZADO("Rechazado"),
    INFORMACION_ADICIONAL("Información Adicional Requerida")
}

data class Evidencia(
    val id: String = "",
    val nombre: String,
    val tipo: TipoEvidencia,
    val url: String, // En producción: URL de Firebase Storage
    val fechaCarga: Long = System.currentTimeMillis()
)

enum class TipoEvidencia {
    PDF, IMAGEN
}