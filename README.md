# 1️⃣ ARQUITECTURA GENERAL – DEFINICIÓN EXPLÍCITA DEL SISTEMA

## 📐 Flujo completo de datos

El sistema **CheckInOut** sigue el siguiente flujo de información:

**Biométrico → Backend → Base de Datos → Frontend → Reportes**

### Descripción del flujo:

1. El **dispositivo biométrico** captura el evento de marcaje (entrada o salida).
2. El **backend** recibe el evento, valida su origen, aplica las reglas de negocio y decide si el marcaje es válido.
3. El **backend** persiste el marcaje y la auditoría correspondiente en la **base de datos**.
4. El **frontend** consume la información procesada desde el backend mediante endpoints seguros.
5. Los **reportes** se generan a partir de datos ya validados y consolidados por el backend.

📌 En ningún punto el biométrico ni el frontend alteran o deciden la lógica del negocio.

---

## 🧠 Fuente de verdad del sistema

La **única fuente de verdad** del sistema es el **backend**.

* El **biométrico**:

  * Solo captura y envía eventos.
  * No valida horarios, estados ni reglas de asistencia.

* El **frontend**:

  * Solo muestra información.
  * Aplica validaciones básicas de interfaz (campos vacíos, formatos).
  * No toma decisiones sobre entrada/salida ni estados de marcaje.

* El **backend**:

  * Determina si un marcaje es entrada o salida.
  * Valida duplicados y errores.
  * Aplica reglas de negocio.
  * Controla seguridad y auditoría.

📌 Esto garantiza coherencia, seguridad y trazabilidad del sistema.

---

## 🧩 Responsabilidad de cada capa

### 🎨 Frontend

Responsable de:

* Interfaz de usuario.
* Validaciones básicas de formularios.
* Visualización de estados (éxito, error, carga).
* Consumo de endpoints del backend.

No responsable de:

* Reglas de negocio.
* Validación de marcajes.
* Seguridad crítica.

---

### ⚙️ Backend

Responsable de:

* Autenticación y autorización por roles.
* Reglas de negocio del sistema de asistencia.
* Validación del origen del marcaje.
* Prevención de inconsistencias (doble entrada, marcajes inválidos).
* Registro de auditoría.
* Preparación de datos para reportes.

---

### 🗄️ Base de Datos

Responsable de:

* Persistencia de información.
* Conservación del historial completo.
* Trazabilidad de acciones.
* Integridad de datos (no edición ni borrado de marcajes).
* Soporte para consultas administrativas y reportes.

---

## 📄 Documentación

Esta definición de arquitectura será documentada en:

* README del proyecto.
* Diagrama simple de arquitectura (opcional pero recomendado).
