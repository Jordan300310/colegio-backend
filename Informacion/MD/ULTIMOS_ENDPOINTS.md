# API - Ultimos Endpoints

Documentacion para el frontend. Cubre los endpoints nuevos o ajustados.

**Base URL:** http://localhost:8080
**Autenticacion:** todos requieren header Authorization: Bearer <jwt>

## Wrapper de respuesta

Toda respuesta del backend usa este formato:

```json
{
  "exito": true,
  "mensaje": "Operacion exitosa.",
  "datos": { /* contenido especifico del endpoint */ }
}
```

En errores: exito = false, datos = null y mensaje describe el problema.

## Codigos HTTP comunes

| Codigo | Significado |
|---|---|
| 200 | OK |
| 400 | Error de validacion (mensaje lo describe) |
| 401 | Token ausente o invalido |
| 403 | Token valido pero rol insuficiente |
| 404 | Recurso no encontrado |
| 409 | Conflicto |
| 500 | Error inesperado del servidor |

---

## 1. Listar secciones con filtros

```
GET /secciones
```

**Rol requerido:** cualquier autenticado.

### Query params

| Param | Tipo | Default | Descripcion |
|---|---|---|---|
| idCurso | int | - | Filtra por curso |
| idAnio | int | - | Filtra por anio escolar |
| idProfesor | int | - | Filtra por profesor asignado |
| estActiva | boolean | - | true o false |
| page | int | 0 | Pagina (0-based) |
| size | int | 10 | Tamano de pagina |
| sort | string | desNombre,asc | Ordenamiento |

### Ejemplos

- GET /secciones?idCurso=1
- GET /secciones?idAnio=2&estActiva=true
- GET /secciones?idProfesor=3

---

## 2. Secciones con asignacion de profesor

```
GET /secciones/asignaciones
```

**Rol requerido:** ROL_ADMIN

### Query params

| Param | Tipo | Default | Descripcion |
|---|---|---|---|
| idProfesor | int | requerido | Profesor a evaluar |
| idCurso | int | - | Filtra por curso |
| idAnio | int | - | Filtra por anio escolar |
| estActiva | boolean | - | true o false |
| page | int | 0 | Pagina (0-based) |
| size | int | 10 | Tamano de pagina |
| sort | string | desNombre,asc | Ordenamiento |

### Response 200 (ejemplo)

```json
{
  "exito": true,
  "mensaje": "Secciones con asignacion obtenidas.",
  "datos": {
    "content": [
      {
        "idSeccion": 5,
        "idCurso": 1,
        "desCurso": "Programacion I",
        "idAnioEscolar": 2,
        "valAnio": 2026,
        "desNombre": "Programacion I - A",
        "estActiva": true,
        "fecCreacion": "2026-02-01T10:00:00",
        "idProfesorAsignado": 3,
        "desProfesorAsignado": "Gabriel Garcia",
        "asignadaAlProfesor": true
      }
    ],
    "totalElements": 12,
    "totalPages": 2,
    "number": 0,
    "size": 10
  }
}
```

---

## 3. Alumnos sin seccion

```
GET /usuarios/sin-seccion
```

**Rol requerido:** ROL_ADMIN

### Query params

| Param | Tipo | Default | Descripcion |
|---|---|---|---|
| busqueda | string | - | Busca por nombres, apellidos o correo |
| estActivo | boolean | - | true o false |
| page | int | 0 | Pagina (0-based) |
| size | int | 10 | Tamano de pagina |
| sort | string | desApellidos,asc | Ordenamiento |

### Ejemplo

- GET /usuarios/sin-seccion?busqueda=juan&estActivo=true

---

## Notas para el frontend

1. Paginacion: los endpoints que devuelven Page usan el formato de Spring Data (content, totalElements, totalPages, number, size).
2. Filtros combinables: los query params son AND. Omitir un param = no filtrar por ese criterio.
3. Siempre revisar exito antes de usar datos.
