# 📡 API Cursos Online — Módulo 4: Progreso del Alumno y Profesor

**Base URL:** `http://localhost:8080`
**Formato:** JSON
**Autenticación:** `Authorization: Bearer <token>`

---

## 📑 Índice

- [1. Progreso de Lecciones (Alumno)](#1-progreso-de-lecciones-alumno)
- [2. Tablero Grupal (Profesor)](#2-tablero-grupal-profesor)
- [3. Errores y Casos de Prueba](#3-errores-y-casos-de-prueba)

---

## 1. Progreso de Lecciones (Alumno) — `/progreso`

Este submódulo gestiona el seguimiento del avance individual del alumno (`TraProgresoLeccion`). Permite marcar lecciones como completadas (operación idempotente) y obtener estadísticas generales de los cursos inscritos (CUS-14).

### `POST /progreso/leccion/{idLeccion}/completar`
**Acceso:** 🔒 `ROL_ALUMNO` (debe tener inscripción activa en el curso de la lección)

> 💡 **Idempotencia:** Si el alumno ya había completado esta lección, el endpoint devuelve `200 OK` con los datos del progreso existente, sin duplicar registros en la base de datos.

#### Envía
| Campo | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `idLeccion` | `number` | ✅ | ID de la lección a completar. Va en la URL, sin body. |

#### Recibe — `datos: ProgresoLeccionResponse`
| Campo | Tipo | Descripción |
|---|---|---|
| `idProgreso` | `number` | ID del registro de progreso |
| `idLeccion` | `number` | ID de la lección completada |
| `desLeccion` | `string` | Nombre de la lección |
| `estCompletada` | `boolean` | Siempre `true` en respuestas exitosas |
| `fecCompletado` | `string` (datetime) | Fecha y hora en que se marcó como completada |

---

### `GET /progreso/mi-progreso`
**Acceso:** 🔒 `ROL_ALUMNO`

> 📊 Devuelve las estadísticas globales del alumno, calculadas mediante queries JPQL optimizadas, agrupando por curso y detallando por módulo.

#### Envía
Sin parámetros.

#### Recibe — `datos: ProgresoCursoResponse[]`
Lista de cursos en los que el alumno está inscrito.

**`ProgresoCursoResponse`:**
| Campo | Tipo | Descripción |
|---|---|---|
| `idCurso` | `number` | ID del curso |
| `desCurso` | `string` | Nombre del curso |
| `valTotalObligatorias` | `number` | Total de lecciones obligatorias publicadas en el curso |
| `valLeccionesCompletadas` | `number` | Cantidad de lecciones obligatorias completadas |
| `valPorcentaje` | `number` | Porcentaje de avance (0.0 a 100.0) |
| `fecUltimaActividad` | `string` (datetime) \| `null` | Fecha de la última lección completada |
| `modulos` | `ProgresoModuloResponse[]` | Desglose del progreso por módulo |

**`ProgresoModuloResponse`:**
| Campo | Tipo | Descripción |
|---|---|---|
| `idModulo` | `number` | ID del módulo |
| `desNombre` | `string` | Nombre del módulo |
| `estEstado` | `string` | Estado calculado (`PENDIENTE`, `EN_CURSO`, `COMPLETADO`) |
| `valCompletadas` | `number` | Lecciones completadas en este módulo |
| `valTotal` | `number` | Total de lecciones obligatorias en este módulo |

---

## 2. Tablero Grupal (Profesor) — `/progreso`

### `GET /progreso/seccion/{idSeccion}`
**Acceso:** 🔒 `ROL_PROFESOR` (Debe tener acceso validado al curso de la sección)

> 📊 **Tablero de Avance Grupal (CUS-15):** Permite al docente visualizar el progreso detallado de todos los alumnos inscritos en una sección específica, incluyendo porcentajes de avance y fecha de última actividad.

#### Parámetros
| Parámetro | Tipo | Ubicación | Descripción |
|---|---|---|---|
| `idSeccion` | `number` | Path | ID de la sección a consultar. |
| `page`, `size`, `sort` | `query` | Query | Parámetros de paginación estándar de Spring Data. |

#### Recibe — `datos: TableroSeccionResponse`
| Campo | Tipo | Descripción |
|---|---|---|
| `idSeccion` | `number` | ID de la sección consultada |
| `nombreSeccion` | `string` | Nombre de la sección |
| `idCurso` | `number` | ID del curso asociado |
| `nombreCurso` | `string` | Nombre del curso |
| `totalLeccionesObligatorias` | `number` | Total de lecciones obligatorias activas y publicadas |
| `alumnos` | `Page<FilaTableroResponse>` | Lista paginada con el detalle por alumno |

**`FilaTableroResponse` (Contenido de `alumnos`):**
| Campo | Tipo | Descripción |
|---|---|---|
| `idAlumno` | `number` | ID del usuario alumno |
| `nombres` | `string` | Nombres del alumno |
| `apellidos` | `string` | Apellidos del alumno |
| `leccionesCompletadas` | `number` | Lecciones obligatorias completadas por el alumno |
| `totalLeccionesObligatorias` | `number` | Lecciones totales obligatorias para el cálculo |
| `porcentaje` | `number` | Porcentaje de avance calculado (ej: `75.5`) |
| `ultimaActividad` | `string` (datetime) \| `null`| Fecha del último progreso registrado |

---

## 3. Errores y Casos de Prueba

Todos los errores mantienen el estándar de devolver `exito: false`, `datos: null` y un `mensaje` descriptivo.

### Mensajes de error esperados
| Endpoint | Código | Mensaje / Condición |
|---|---|---|
| `POST /leccion/{id}/completar` | `403` | `"No estás inscrito en el curso al que pertenece esta lección."` |
| `POST /leccion/{id}/completar` | `404` | `"Lección con ID X no encontrada o no está publicada."` |
| `GET /seccion/{idSeccion}` | `403` | `"Acceso denegado al curso."` (Si el profesor no tiene el curso asignado) |
| `GET /seccion/{idSeccion}` | `404` | `"Sección con ID X no encontrada."` |
| Todos | `401` | Sin token o token expirado. |

### 🧪 Matriz de Pruebas (QA - Postman)
| Acción | Rol | Condición | Código HTTP | Resultado Esperado |
|---|---|---|---|---|
| **POST** completar | Alumno | `idLeccion` válido y publicada | `200 OK` | Devuelve DTO con `estCompletada: true` y fecha actual. |
| **POST** completar | Alumno | Enviar 2 veces la misma petición | `200 OK` | Retorna el mismo `idProgreso` (Idempotencia). |
| **GET** mi-progreso | Alumno | Alumno tras completar lecciones | `200 OK` | `valPorcentaje` recalculado y módulo en `EN_CURSO` o `COMPLETADO`. |
| **GET** seccion | Profesor | Sección válida con alumnos | `200 OK` | Devuelve el objeto paginado `Page<FilaTableroResponse>`. |
| **GET** seccion | Profesor | Sección de otro profesor | `403 Forbidden` | Bloqueado por `validarAccesoProfesorACurso()`. |