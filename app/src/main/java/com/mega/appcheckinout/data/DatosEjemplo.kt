// data/DatosEjemplo.kt
package com.mega.appcheckinout.data

import com.mega.appcheckinout.models.*
import com.mega.appcheckinout.screens.administrador.gestion_personal.AsignacionObra
import com.mega.appcheckinout.screens.administrador.reportes.RegistroAsistencia


/**
 * Objeto singleton con todos los datos de ejemplo de la aplicación
 *
 * IMPORTANTE: Este archivo contiene datos MOCK para desarrollo.
 * En producción, estos datos deben venir de una base de datos real.
 *
 * Uso: import com.mega.appcheckinout.data.DatosEjemplo
 *      val trabajadores = DatosEjemplo.trabajadores
 */
object DatosEjemplo {

    // ==================== TRABAJADORES ====================

    val trabajadores = listOf(
        TrabajadorCompleto(
            id = "1",
            primerNombre = "Juan",
            segundoNombre = "Carlos",
            primerApellido = "García",
            segundoApellido = "Rodríguez",
            fechaNacimiento = "15/03/1990",
            tipoDocumento = "Cédula",
            numeroDocumento = "1234567890",
            telefono = "3001234567",
            direccion = "Calle 50 #23-45, Ibagué",
            rol = "Operativo",
            subCargo = "Latero",
            actividad = "Muros",
            obraAsignada = "Mandarino - Ibagué",
            arl = "Sura",
            eps = "Sanitas",
            fechaExamen = "01/01/2024",
            fechaCursoAlturas = "15/01/2024",
            biometriaRegistrada = true,
            estado = "Activo"
        ),
        TrabajadorCompleto(
            id = "2",
            primerNombre = "María",
            segundoNombre = "Fernanda",
            primerApellido = "Martínez",
            segundoApellido = "López",
            fechaNacimiento = "22/07/1985",
            tipoDocumento = "Cédula",
            numeroDocumento = "0987654321",
            telefono = "3109876543",
            direccion = "Carrera 10 #15-20, Rionegro",
            rol = "Inspector SST",
            subCargo = "",
            actividad = "",
            obraAsignada = "Bosque robledal - Rionegro",
            arl = "Positiva",
            eps = "Compensar",
            fechaExamen = "10/02/2024",
            fechaCursoAlturas = "20/02/2024",
            biometriaRegistrada = true,
            estado = "Activo"
        ),
        TrabajadorCompleto(
            id = "3",
            primerNombre = "Pedro",
            segundoNombre = "Antonio",
            primerApellido = "González",
            segundoApellido = "Pérez",
            fechaNacimiento = "05/11/1992",
            tipoDocumento = "Cédula",
            numeroDocumento = "1122334455",
            telefono = "3201122334",
            direccion = "Avenida 30 #12-34, Ibagué",
            rol = "Operativo",
            subCargo = "Mampostero",
            actividad = "Muros",
            obraAsignada = "Mandarino - Ibagué",
            arl = "Sura",
            eps = "Nueva EPS",
            fechaExamen = "15/03/2024",
            fechaCursoAlturas = "25/03/2024",
            biometriaRegistrada = false,
            estado = "Activo"
        ),
        TrabajadorCompleto(
            id = "4",
            primerNombre = "Ana",
            segundoNombre = "María",
            primerApellido = "Ramírez",
            segundoApellido = "Torres",
            fechaNacimiento = "18/09/1988",
            tipoDocumento = "Cédula",
            numeroDocumento = "5544332211",
            telefono = "3155443322",
            direccion = "Calle 20 #8-15, Villavicencio",
            rol = "Encargado",
            subCargo = "",
            actividad = "",
            obraAsignada = "Hacienda Nakare - Villavicencio",
            arl = "Colmena",
            eps = "Salud Total",
            fechaExamen = "05/04/2024",
            fechaCursoAlturas = "12/04/2024",
            biometriaRegistrada = true,
            estado = "Activo"
        ),
        TrabajadorCompleto(
            id = "5",
            primerNombre = "Luis",
            segundoNombre = "Fernando",
            primerApellido = "Sánchez",
            segundoApellido = "Vargas",
            fechaNacimiento = "30/01/1995",
            tipoDocumento = "Pasaporte",
            numeroDocumento = "6677889900",
            telefono = "3206677889",
            direccion = "Transversal 5 #40-20, Bogotá",
            rol = "Operativo",
            subCargo = "Rematador",
            actividad = "Parqueadero",
            obraAsignada = "Pomelo - Bogotá",
            arl = "Positiva",
            eps = "Compensar",
            fechaExamen = "20/04/2024",
            fechaCursoAlturas = "28/04/2024",
            biometriaRegistrada = false,
            estado = "Activo"
        ),
        TrabajadorCompleto(
            id = "6",
            primerNombre = "Carolina",
            segundoNombre = "",
            primerApellido = "Díaz",
            segundoApellido = "Castro",
            fechaNacimiento = "12/06/1991",
            tipoDocumento = "Cédula",
            numeroDocumento = "9988776655",
            telefono = "3159988776",
            direccion = "Diagonal 25 #18-30, Ibagué",
            rol = "Operativo",
            subCargo = "Aseo",
            actividad = "",
            obraAsignada = "Mandarino - Ibagué",
            arl = "Sura",
            eps = "Sanitas",
            fechaExamen = "01/05/2024",
            fechaCursoAlturas = "10/05/2024",
            biometriaRegistrada = true,
            estado = "Inactivo"
        ),
        TrabajadorCompleto(
            id = "7",
            primerNombre = "Jorge",
            segundoNombre = "Andrés",
            primerApellido = "Morales",
            segundoApellido = "Ruiz",
            fechaNacimiento = "25/04/1987",
            tipoDocumento = "Cédula",
            numeroDocumento = "3344556677",
            telefono = "3103344556",
            direccion = "Carrera 15 #22-10, Rionegro",
            rol = "Inspector SST",
            subCargo = "",
            actividad = "",
            obraAsignada = "Bosque robledal - Rionegro",
            arl = "Colmena",
            eps = "Nueva EPS",
            fechaExamen = "15/05/2024",
            fechaCursoAlturas = "22/05/2024",
            biometriaRegistrada = false,
            estado = "Activo"
        )
    )

    // ==================== OBRAS ====================

    val obras = listOf(
        Obra(
            id = "1",
            codigo = "MCJ-001",
            nombre = "Edificio Mandarino",
            ciudad = "Ibagué",
            direccion = "Calle 42 # 5-67",
            fechaInicio = "15/01/2025",
            fechaFinEstimada = "15/12/2025",
            responsableSISO = "María González",
            descripcion = "Construcción de edificio residencial de 8 pisos",
            estado = Obra.EstadoObra.ACTIVA,
            numeroTrabajadores = 45
        ),
        Obra(
            id = "2",
            codigo = "MCJ-002",
            nombre = "Conjunto Rionegro",
            ciudad = "Ibagué",
            direccion = "Carrera 3 # 12-34",
            fechaInicio = "10/02/2025",
            fechaFinEstimada = "10/08/2025",
            responsableSISO = "Carlos Méndez",
            descripcion = "Conjunto residencial 120 apartamentos",
            estado = Obra.EstadoObra.ACTIVA,
            numeroTrabajadores = 67
        ),
        Obra(
            id = "3",
            codigo = "MCJ-003",
            nombre = "Centro Comercial Plaza",
            ciudad = "Bogotá",
            direccion = "Avenida 68 # 45-12",
            fechaInicio = "01/06/2024",
            fechaFinEstimada = "30/12/2024",
            responsableSISO = "Ana Rodríguez",
            descripcion = "Centro comercial 3 pisos",
            estado = Obra.EstadoObra.FINALIZADA,
            numeroTrabajadores = 0
        ),
        Obra(
            id = "4",
            codigo = "MCJ-004",
            nombre = "Parque Industrial Norte",
            ciudad = "Medellín",
            direccion = "Km 5 Autopista Norte",
            fechaInicio = "20/11/2024",
            fechaFinEstimada = "20/05/2025",
            responsableSISO = "Pedro Salazar",
            descripcion = "Bodegas industriales",
            estado = Obra.EstadoObra.ACTIVA,
            numeroTrabajadores = 32
        ),
        Obra(
            id = "5",
            codigo = "MCJ-005",
            nombre = "Torres del Bosque",
            ciudad = "Ibagué",
            direccion = "Calle 60 # 8-90",
            fechaInicio = "15/03/2024",
            fechaFinEstimada = "15/11/2024",
            responsableSISO = "Laura Jiménez",
            descripcion = "Dos torres residenciales",
            estado = Obra.EstadoObra.FINALIZADA,
            numeroTrabajadores = 0
        )
    )

    // ==================== ASIGNACIONES ====================

    val asignaciones = listOf(
        AsignacionObra(
            obraId = "1",
            nombreObra = "Mandarino - Ibagué",
            ubicacion = "Ibagué, Tolima",
            fechaInicio = "01/01/2024",
            trabajadoresAsignados = listOf(
                trabajadores[0], // Juan García
                trabajadores[2], // Pedro González
                trabajadores[5]  // Carolina Díaz
            ),
            estado = "Activa"
        ),
        AsignacionObra(
            obraId = "2",
            nombreObra = "Bosque robledal - Rionegro",
            ubicacion = "Rionegro, Antioquia",
            fechaInicio = "10/02/2024",
            trabajadoresAsignados = listOf(
                trabajadores[1], // María Martínez
                trabajadores[6]  // Jorge Morales
            ),
            estado = "Activa"
        ),
        AsignacionObra(
            obraId = "3",
            nombreObra = "Hacienda Nakare - Villavicencio",
            ubicacion = "Villavicencio, Meta",
            fechaInicio = "05/04/2024",
            trabajadoresAsignados = listOf(
                trabajadores[3]  // Ana Ramírez
            ),
            estado = "Activa"
        ),
        AsignacionObra(
            obraId = "4",
            nombreObra = "Pomelo - Bogotá",
            ubicacion = "Bogotá D.C.",
            fechaInicio = "20/04/2024",
            trabajadoresAsignados = listOf(
                trabajadores[4]  // Luis Sánchez
            ),
            estado = "Activa"
        )
    )

    // ==================== USUARIOS ====================

    val usuarios = listOf(
        Usuario(
            id = "1",
            nombreCompleto = "Juan Pérez",
            numeroDocumento = "1234567890",
            email = "juan.perez@mega.com",
            nombreUsuario = "jperez",
            rol = RolUsuario.ADMINISTRADOR,
            estado = EstadoUsuario.ACTIVO,
            ultimoAcceso = System.currentTimeMillis()
        ),
        Usuario(
            id = "2",
            nombreCompleto = "María García",
            numeroDocumento = "0987654321",
            email = "maria.garcia@mega.com",
            nombreUsuario = "mgarcia",
            rol = RolUsuario.INSPECTOR_SST,
            estado = EstadoUsuario.ACTIVO,
            obraAsignadaNombre = "Obra Mandarino"
        )
    )

    // ==================== SESIONES ACTIVAS ====================

    val sesionesActivas = listOf(
        SesionActiva(
            usuarioId = "1",
            nombreUsuario = "Juan Pérez",
            rol = RolUsuario.ADMINISTRADOR,
            dispositivo = "Web - Chrome",
            ip = "192.168.1.100",
            ubicacion = "Ibagué, Tolima",
            horaInicio = System.currentTimeMillis() - 3600000,
            ultimaActividad = System.currentTimeMillis() - 300000
        ),
        SesionActiva(
            usuarioId = "2",
            nombreUsuario = "María García",
            rol = RolUsuario.INSPECTOR_SST,
            dispositivo = "Android - Samsung Galaxy",
            ip = "192.168.1.105",
            ubicacion = "Ibagué, Tolima",
            horaInicio = System.currentTimeMillis() - 7200000,
            ultimaActividad = System.currentTimeMillis() - 60000
        ),
        SesionActiva(
            usuarioId = "3",
            nombreUsuario = "Carlos Rodríguez",
            rol = RolUsuario.ENCARGADO,
            dispositivo = "iOS - iPhone 13",
            ip = "192.168.1.110",
            ubicacion = "Ibagué, Tolima",
            horaInicio = System.currentTimeMillis() - 1800000,
            ultimaActividad = System.currentTimeMillis() - 120000
        )
    )

    // ==================== EVENTOS DE AUDITORÍA ====================

    val eventosAuditoria = listOf(
        EventoAuditoria(
            id = "1",
            usuarioId = "1",
            nombreUsuario = "Juan Pérez",
            rol = RolUsuario.ADMINISTRADOR,
            accion = TipoAccion.LOGIN_EXITOSO,
            descripcion = "Inicio de sesión exitoso desde Chrome",
            dispositivo = "Web - Chrome",
            ip = "192.168.1.100",
            timestamp = System.currentTimeMillis() - 3600000,
            exitoso = true
        ),
        EventoAuditoria(
            id = "2",
            usuarioId = "2",
            nombreUsuario = "María García",
            rol = RolUsuario.INSPECTOR_SST,
            accion = TipoAccion.LOGIN_FALLIDO,
            descripcion = "Intento de inicio de sesión fallido - Contraseña incorrecta",
            dispositivo = "Android - Samsung",
            ip = "192.168.1.105",
            timestamp = System.currentTimeMillis() - 7200000,
            exitoso = false
        ),
        EventoAuditoria(
            id = "3",
            usuarioId = "1",
            nombreUsuario = "Juan Pérez",
            rol = RolUsuario.ADMINISTRADOR,
            accion = TipoAccion.CREACION_USUARIO,
            descripcion = "Creó nuevo usuario: Carlos Rodríguez (Encargado)",
            dispositivo = "Web - Chrome",
            ip = "192.168.1.100",
            timestamp = System.currentTimeMillis() - 10800000,
            exitoso = true
        ),
        EventoAuditoria(
            id = "4",
            usuarioId = "1",
            nombreUsuario = "Juan Pérez",
            rol = RolUsuario.ADMINISTRADOR,
            accion = TipoAccion.BLOQUEO_CUENTA,
            descripcion = "Bloqueó la cuenta de: Pedro Martínez - Motivo: Inactividad prolongada",
            dispositivo = "Web - Chrome",
            ip = "192.168.1.100",
            timestamp = System.currentTimeMillis() - 14400000,
            exitoso = true
        ),
        EventoAuditoria(
            id = "5",
            usuarioId = "3",
            nombreUsuario = "Carlos Rodríguez",
            rol = RolUsuario.ENCARGADO,
            accion = TipoAccion.CAMBIO_CONTRASEÑA,
            descripcion = "Cambió su contraseña exitosamente",
            dispositivo = "iOS - iPhone",
            ip = "192.168.1.110",
            timestamp = System.currentTimeMillis() - 18000000,
            exitoso = true
        )
    )

    // ==================== REGISTROS DE ASISTENCIA ====================

    fun getRegistrosAsistencia(): List<RegistroAsistencia> {
        return listOf(
            RegistroAsistencia(
                trabajador = trabajadores[0],
                fecha = "10/12/2024",
                horaEntrada = "07:00",
                horaSalida = "17:00",
                horasTrabajadas = 9.0,
                observaciones = ""
            ),
            RegistroAsistencia(
                trabajador = trabajadores[1],
                fecha = "10/12/2024",
                horaEntrada = "08:00",
                horaSalida = "17:00",
                horasTrabajadas = 8.0,
                observaciones = ""
            )
        )



    }

    // ==================== NOVEDADES ====================
    val novedades = listOf(
        Novedad(
            id = "1",
            trabajadorId = "1",
            trabajadorNombre = "Juan Carlos García Rodríguez",
            tipoNovedad = TipoNovedad.INCAPACIDAD_EPS,
            fechaInicio = "01/01/2025",
            fechaFin = "03/01/2025",
            descripcion = "Incapacidad por gripe. Reposo de 3 días.",
            evidencias = listOf(
                Evidencia(
                    id = "1",
                    nombre = "incapacidad_medica.pdf",
                    tipo = TipoEvidencia.PDF,
                    url = "https://example.com/doc1.pdf"
                )
            ),
            estado = EstadoNovedad.APROBADO,
            creadoPor = "María Martínez (Inspector SST)",
            revisadoPor = "Admin Principal",
            fechaRevision = System.currentTimeMillis() - 86400000,
            obraId = "1"
        ),
        Novedad(
            id = "2",
            trabajadorId = "3",
            trabajadorNombre = "Pedro Antonio González Pérez",
            tipoNovedad = TipoNovedad.PERMISO,
            fechaInicio = "05/01/2025",
            fechaFin = "05/01/2025",
            descripcion = "Permiso por diligencia personal urgente",
            evidencias = emptyList(),
            estado = EstadoNovedad.PENDIENTE,
            creadoPor = "Ana Ramírez (Encargado)",
            obraId = "1"
        ),
        Novedad(
            id = "3",
            trabajadorId = "4",
            trabajadorNombre = "Ana María Ramírez Torres",
            tipoNovedad = TipoNovedad.LICENCIA,
            fechaInicio = "10/01/2025",
            fechaFin = "12/01/2025",
            descripcion = "Licencia por calamidad familiar",
            evidencias = listOf(
                Evidencia(
                    id = "2",
                    nombre = "certificado_defuncion.pdf",
                    tipo = TipoEvidencia.PDF,
                    url = "https://example.com/doc2.pdf"
                )
            ),
            estado = EstadoNovedad.APROBADO,
            creadoPor = "María Martínez (Inspector SST)",
            revisadoPor = "Admin Principal",
            fechaRevision = System.currentTimeMillis() - 172800000,
            obraId = "3"
        ),
        Novedad(
            id = "4",
            trabajadorId = "5",
            trabajadorNombre = "Luis Fernando Sánchez Vargas",
            tipoNovedad = TipoNovedad.INCAPACIDAD_ARL,
            fechaInicio = "28/12/2024",
            fechaFin = "02/01/2025",
            descripcion = "Accidente laboral menor - lesión en mano",
            evidencias = listOf(
                Evidencia(
                    id = "3",
                    nombre = "informe_arl.pdf",
                    tipo = TipoEvidencia.PDF,
                    url = "https://example.com/doc3.pdf"
                ),
                Evidencia(
                    id = "4",
                    nombre = "foto_lesion.jpg",
                    tipo = TipoEvidencia.IMAGEN,
                    url = "https://example.com/img1.jpg"
                )
            ),
            estado = EstadoNovedad.RECHAZADO,
            creadoPor = "Jorge Morales (Inspector SST)",
            revisadoPor = "Admin Principal",
            fechaRevision = System.currentTimeMillis() - 259200000,
            motivoRechazo = "Documentación incompleta. Falta firma del médico tratante.",
            obraId = "4"
        )
    )

    // ==================== DISPOSITIVOS ====================
    val dispositivos = listOf(
        Dispositivo(
            id = "1",
            nombre = "Tablet Obra Mandarino",
            tipo = TipoDispositivo.TABLET,
            obraId = "1",
            obraNombre = "Edificio Mandarino",
            responsableId = "2",
            responsableNombre = "María González (Inspector SST)",
            metodosHabilitados = listOf(
                MetodoMarcaje.CEDULA,
                MetodoMarcaje.QR,
                MetodoMarcaje.BIOMETRIA
            ),
            estado = EstadoDispositivo.ACTIVO,
            fechaRegistro = System.currentTimeMillis() - 2592000000, // Hace 30 días
            ultimoUso = System.currentTimeMillis() - 3600000

        ),
        Dispositivo(
            id = "2",
            nombre = "Teléfono Rionegro",
            tipo = TipoDispositivo.TELEFONO,
            obraId = "2",
            obraNombre = "Conjunto Rionegro",
            responsableId = "3",
            responsableNombre = "Carlos Méndez (Encargado)",
            metodosHabilitados = listOf(
                MetodoMarcaje.CEDULA,
                MetodoMarcaje.QR
            ),
            estado = EstadoDispositivo.ACTIVO,
            fechaRegistro = System.currentTimeMillis() - 1814400000, // Hace 21 días
            ultimoUso = System.currentTimeMillis() - 7200000
        ),
        Dispositivo(
            id = "3",
            nombre = "Lector Biométrico Mandarino",
            tipo = TipoDispositivo.BIOMETRICO,
            obraId = "1",
            obraNombre = "Edificio Mandarino",
            responsableId = "2",
            responsableNombre = "María González (Inspector SST)",
            metodosHabilitados = listOf(MetodoMarcaje.BIOMETRIA),
            estado = EstadoDispositivo.ACTIVO,
            fechaRegistro = System.currentTimeMillis() - 2592000000, // Hace 30 días
            ultimoUso = System.currentTimeMillis() - 1800000
        ),
        Dispositivo(
            id = "4",
            nombre = "Tablet Plaza (Inactiva)",
            tipo = TipoDispositivo.TABLET,
            obraId = "3",
            obraNombre = "Centro Comercial Plaza",
            responsableId = "4",
            responsableNombre = "Ana Rodríguez (Inspector SST)",
            metodosHabilitados = listOf(
                MetodoMarcaje.CEDULA,
                MetodoMarcaje.QR
            ),
            estado = EstadoDispositivo.INACTIVO,
            fechaRegistro = System.currentTimeMillis() - 5184000000 // Hace 60 días
        ),
        Dispositivo(
            id = "5",
            nombre = "Tablet Norte (BLOQUEADA)",
            tipo = TipoDispositivo.TABLET,
            obraId = "4",
            obraNombre = "Parque Industrial Norte",
            responsableId = "5",
            responsableNombre = "Pedro Salazar (Inspector SST)",
            metodosHabilitados = listOf(MetodoMarcaje.CEDULA),
            estado = EstadoDispositivo.BLOQUEADO,
            fechaRegistro = System.currentTimeMillis() - 3456000000, // Hace 40 días
            motivoBloqueo = "Dispositivo reportado como perdido"
        )
    )

    val logsDispositivos = listOf(
        LogDispositivo(
            id = "1",
            dispositivoId = "1",
            accion = "Marcaje exitoso",
            usuarioId = "1",
            usuarioNombre = "Juan García",
            timestamp = System.currentTimeMillis() - 3600000,
            detalles = "Ingreso registrado - Método: Cédula"
        ),
        LogDispositivo(
            id = "2",
            dispositivoId = "1",
            accion = "Marcaje exitoso",
            usuarioId = "3",
            usuarioNombre = "Pedro González",
            timestamp = System.currentTimeMillis() - 3000000,
            detalles = "Ingreso registrado - Método: Biometría"
        ),
        LogDispositivo(
            id = "3",
            dispositivoId = "2",
            accion = "Estado cambiado a ACTIVO",
            usuarioId = "admin1",
            usuarioNombre = "Admin Principal",
            timestamp = System.currentTimeMillis() - 86400000,
            detalles = "Dispositivo reactivado tras mantenimiento"
        ),
        LogDispositivo(
            id = "4",
            dispositivoId = "5",
            accion = "Estado cambiado a BLOQUEADO",
            usuarioId = "admin1",
            usuarioNombre = "Admin Principal",
            timestamp = System.currentTimeMillis() - 172800000,
            detalles = "Dispositivo reportado como extraviado en obra"
        )
    )




    // ==================== FUNCIONES AUXILIARES ====================

    /**
     * Obtiene trabajadores filtrados por obra
     */
    fun getTrabajadoresPorObra(obraId: String): List<TrabajadorCompleto> {
        val obraNombre = obras.find { it.id == obraId }?.nombre ?: return emptyList()
        return trabajadores.filter { it.obraAsignada.contains(obraNombre.split(" ")[0], ignoreCase = true) }
    }

    /**
     * Obtiene trabajadores activos
     */
    fun getTrabajadoresActivos(): List<TrabajadorCompleto> {
        return trabajadores.filter { it.estado == "Activo" }
    }

    /**
     * Obtiene obras activas
     */
    fun getObrasActivas(): List<Obra> {
        return obras.filter { it.estaActiva() }
    }

    /**
     * Obtiene obras finalizadas
     */
    fun getObrasFinalizadas(): List<Obra> {
        return obras.filter { it.estaFinalizada() }
    }

    /**
     * Lista de nombres de obras para dropdowns
     */
    val nombresObras: List<String> = obras.map { it.nombre }

    /**
     * Lista de ciudades disponibles
     */
    val ciudades = listOf(
        "Ibagué", "Bogotá", "Medellín", "Cali", "Barranquilla",
        "Cartagena", "Bucaramanga", "Pereira", "Manizales", "Armenia"
    )

    /**
     * Tipos de documento
     */
    val tiposDocumento = listOf(
        "Cédula",
        "Tarjeta de identidad",
        "Pasaporte",
        "Permiso de protección temporal"
    )

    /**
     * Roles disponibles
     */
    val roles = listOf("Encargado", "Inspector SST", "Operativo")

    /**
     * Sub cargos disponibles
     */
    val subCargos = listOf(
        "Latero",
        "Mampostero",
        "Mulero",
        "Rematador",
        "Aseo",
        "Añadir nuevo subcargo"
    )

    /**
     * Actividades disponibles
     */
    val actividades = listOf(
        "Muros",
        "Parqueadero",
        "Tanque",
        "Añadir nueva actividad"
    )



}