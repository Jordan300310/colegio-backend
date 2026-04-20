# 📡 Módulo Académico — Referencia para Frontend

**Base URL:** `http://localhost:8080`  
**Autenticación:** `Authorization: Bearer <token>` (requerido en todos los endpoints)

---

## 📑 Índice

- [Niveles](#-niveles---niveles)
- [Cursos](#-cursos---cursos)
- [Secciones](#-secciones---secciones)
- [Errores globales del módulo](#-errores-del-módulo)

---

## 🎓 Niveles — `/niveles`

---

### `GET /niveles`
**Acceso:** 🔒 Autenticado

#### Envía
Solo el header `Authorization`. Sin body, sin parámetros.

#### Recibe — `datos: NivelResponse[]`
| Campo | Tipo | Descripción |
|---|---|---|
| `idNivel` | `number` | ID del nivel |
| `codNivel` | `string` | Código único. Ej: `BASICO` |
| `desNombre` | `string` | Nombre del nivel. Ej: `Nivel Básico` |
| `valOrden` | `number` | Orden de visualización |
| `estActivo` | `boolean` | Si el nivel está activo |

---

### `GET /niveles/{id}`
**Acceso:** 🔒 Autenticado

#### Envía
| Campo | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `id` | `number` | ✅ | ID del nivel. Va en la URL |

#### Recibe — `datos: NivelResponse`
Mismo objeto `NivelResponse` de la tabla anterior.

---

### `POST /niveles`
**Acceso:** 🔒 Solo `ROL_ADMIN`

#### Envía
| Campo | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `codNivel` | `string` | ✅ | Código único del nivel. Ej: `BASICO` |
| `desNombre` | `string` | ✅ | Nombre del nivel. Ej: `Nivel Básico` |
| `valOrden` | `number` | ✅ | Orden de visualización. Ej: `1` |

#### Recibe — `datos: NivelResponse`
El nivel recién creado.

---

### `PUT /niveles/{id}`
**Acceso:** 🔒 Solo `ROL_ADMIN`

#### Envía
| Campo | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `id` | `number` | ✅ | ID del nivel. Va en la URL |
| `codNivel` | `string` | ✅ | Nuevo código |
| `desNombre` | `string` | ✅ | Nuevo nombre |
| `valOrden` | `number` | ✅ | Nuevo orden |

#### Recibe — `datos: NivelResponse`
El nivel actualizado.

---

## 📚 Cursos — `/cursos`

---

### `GET /cursos`
**Acceso:** 🔒 Solo `ROL_ADMIN`

#### Envía — parámetros en la URL
| Parámetro | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `page` | `number` | ❌ | Página (0-based). Default: `0` |
| `size` | `number` | ❌ | Cantidad por página. Default: `10` |
| `sort` | `string` | ❌ | Campo a ordenar. Default: `desNombre` |

#### Recibe — `datos: Page<CursoResponse>`
| Campo | Tipo | Descripción |
|---|---|---|
| `content` | `CursoResponse[]` | Lista de cursos de la página actual |
| `totalElements` | `number` | Total de cursos |
| `totalPages` | `number` | Total de páginas |
| `number` | `number` | Página actual (0-based) |
| `size` | `number` | Tamaño de página |

**`CursoResponse`:**
| Campo | Tipo | Descripción |
|---|---|---|
| `idCurso` | `number` | ID del curso |
| `idNivel` | `number` \| `null` | ID del nivel asociado |
| `desNivel` | `string` \| `null` | Nombre del nivel asociado |
| `desNombre` | `string` | Nombre del curso |
| `desDescripcion` | `string` \| `null` | Descripción del curso |
| `estPublicado` | `boolean` | Si el curso está publicado |
| `estActivo` | `boolean` | Si el curso está activo |
| `fecCreacion` | `string` (datetime) | Fecha de creación |
| `fecPublicacion` | `string` (datetime) \| `null` | Fecha de publicación. `null` si no está publicado |

---

### `GET /cursos/publicados`
**Acceso:** 🔒 Autenticado (todos los roles)

#### Envía — parámetros en la URL
| Parámetro | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `page` | `number` | ❌ | Default: `0` |
| `size` | `number` | ❌ | Default: `10` |

#### Recibe — `datos: Page<CursoResponse>`
Solo cursos con `estPublicado: true`.

---

### `GET /cursos/{id}`
**Acceso:** 🔒 Autenticado

#### Envía
| Campo | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `id` | `number` | ✅ | ID del curso. Va en la URL |

#### Recibe — `datos: CursoResponse`
Detalle completo del curso.

---

### `POST /cursos`
**Acceso:** 🔒 Solo `ROL_ADMIN`

#### Envía
| Campo | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `idNivel` | `number` | ✅ | ID del nivel al que pertenece |
| `desNombre` | `string` | ✅ | Nombre del curso |
| `desDescripcion` | `string` | ❌ | Descripción del curso |

#### Recibe — `datos: CursoResponse`
El curso recién creado con `estPublicado: false` por defecto.

---

### `PUT /cursos/{id}`
**Acceso:** 🔒 Solo `ROL_ADMIN`

#### Envía
| Campo | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `id` | `number` | ✅ | ID del curso. Va en la URL |
| `idNivel` | `number` | ✅ | ID del nivel |
| `desNombre` | `string` | ✅ | Nombre del curso |
| `desDescripcion` | `string` | ❌ | Descripción |

#### Recibe — `datos: CursoResponse`
El curso actualizado.

---

### `PUT /cursos/{id}/publicar`
**Acceso:** 🔒 Solo `ROL_ADMIN`

#### Envía
| Campo | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `id` | `number` | ✅ | ID del curso. Va en la URL. Sin body |

#### Recibe — `datos: CursoResponse`
El curso con `estPublicado: true` y `fecPublicacion` con la fecha actual.

---

### `PUT /cursos/{id}/despublicar`
**Acceso:** 🔒 Solo `ROL_ADMIN`

#### Envía
| Campo | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `id` | `number` | ✅ | ID del curso. Va en la URL. Sin body |

#### Recibe — `datos: CursoResponse`
El curso con `estPublicado: false` y `fecPublicacion: null`.

---

## 🏫 Secciones — `/secciones`

---

### `GET /secciones`
**Acceso:** 🔒 Autenticado

#### Envía — parámetros en la URL
| Parámetro | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `page` | `number` | ❌ | Default: `0` |
| `size` | `number` | ❌ | Default: `10` |
| `sort` | `string` | ❌ | Default: `desNombre` |

#### Recibe — `datos: Page<SeccionResponse>`
| Campo | Tipo | Descripción |
|---|---|---|
| `content` | `SeccionResponse[]` | Lista de secciones |
| `totalElements` | `number` | Total de secciones |
| `totalPages` | `number` | Total de páginas |
| `number` | `number` | Página actual |
| `size` | `number` | Tamaño de página |

**`SeccionResponse`:**
| Campo | Tipo | Descripción |
|---|---|---|
| `idSeccion` | `number` | ID de la sección |
| `idCurso` | `number` | ID del curso |
| `desCurso` | `string` | Nombre del curso |
| `idAnioEscolar` | `number` | ID del año escolar |
| `valAnio` | `number` | Año. Ej: `2026` |
| `desNombre` | `string` | Nombre de la sección. Ej: `Taller de Programación - A` |
| `estActivo` | `boolean` | Si la sección está activa |
| `fecCreacion` | `string` (datetime) | Fecha de creación |
| `idProfesor` | `number` \| `null` | ID del profesor asignado. `null` si no tiene |
| `desProfesor` | `string` \| `null` | Nombre completo del profesor. `null` si no tiene |

---

### `GET /secciones/curso/{idCurso}`
**Acceso:** 🔒 Autenticado

#### Envía
| Campo | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `idCurso` | `number` | ✅ | ID del curso. Va en la URL |

#### Recibe — `datos: Page<SeccionResponse>`
Secciones filtradas por curso.

---

### `GET /secciones/anio/{idAnio}`
**Acceso:** 🔒 Autenticado

#### Envía
| Campo | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `idAnio` | `number` | ✅ | ID del año escolar. Va en la URL |

#### Recibe — `datos: Page<SeccionResponse>`
Secciones filtradas por año escolar.

---

### `GET /secciones/{id}`
**Acceso:** 🔒 Autenticado

#### Envía
| Campo | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `id` | `number` | ✅ | ID de la sección. Va en la URL |

#### Recibe — `datos: SeccionResponse`
Detalle completo de la sección incluyendo profesor asignado si tiene.

---

### `POST /secciones`
**Acceso:** 🔒 Solo `ROL_ADMIN`

#### Envía
| Campo | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `idCurso` | `number` | ✅ | ID del curso |
| `idAnioEscolar` | `number` | ✅ | ID del año escolar |
| `desNombre` | `string` | ✅ | Nombre de la sección. Ej: `Taller de Programación - A` |

#### Recibe — `datos: SeccionResponse`
La sección recién creada con `idProfesor: null` y `desProfesor: null`.

---

### `PUT /secciones/{id}`
**Acceso:** 🔒 Solo `ROL_ADMIN`

#### Envía
| Campo | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `id` | `number` | ✅ | ID de la sección. Va en la URL |
| `idCurso` | `number` | ✅ | ID del curso |
| `idAnioEscolar` | `number` | ✅ | ID del año escolar |
| `desNombre` | `string` | ✅ | Nombre de la sección |

#### Recibe — `datos: SeccionResponse`
La sección actualizada.

---

### `POST /secciones/{id}/profesor`
**Acceso:** 🔒 Solo `ROL_ADMIN`

#### Envía
| Campo | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `id` | `number` | ✅ | ID de la sección. Va en la URL |
| `idProfesor` | `number` | ✅ | ID del usuario con rol `ROL_PROFESOR` |

#### Recibe — `datos: SeccionResponse`
La sección con el profesor asignado en `idProfesor` y `desProfesor`.

> ⚠️ El usuario debe tener `ROL_PROFESOR`. Si ya tiene profesor asignado devuelve `409`.

---

### `DELETE /secciones/{id}/profesor`
**Acceso:** 🔒 Solo `ROL_ADMIN`

#### Envía
| Campo | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `id` | `number` | ✅ | ID de la sección. Va en la URL. Sin body |

#### Recibe — `datos: SeccionResponse`
La sección con `idProfesor: null` y `desProfesor: null`.

---

## ❌ Errores del módulo

| Código | Mensaje | Causa |
|---|---|---|
| `400` | `"El nivel es obligatorio."` | Campo vacío en request |
| `400` | `"El usuario seleccionado no tiene el rol de Profesor."` | Asignar un no-profesor |
| `400` | `"Esta sección no tiene un profesor asignado."` | Remover sin profesor |
| `404` | `"Nivel con ID X no encontrado."` | Nivel inexistente |
| `404` | `"Curso con ID X no encontrado."` | Curso inexistente |
| `404` | `"Sección con ID X no encontrada."` | Sección inexistente |
| `404` | `"Año escolar con ID X no encontrado."` | Año escolar inexistente |
| `409` | `"Ya existe un curso con el nombre 'X'."` | Nombre duplicado |
| `409` | `"Ya existe una sección con el nombre 'X' en ese curso y año escolar."` | Sección duplicada |
| `409` | `"Esta sección ya tiene un profesor asignado."` | Asignación duplicada |
