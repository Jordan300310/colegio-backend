-- ============================================================
-- SEED PARA PRUEBAS (Auth, Seguridad, Usuarios, Academico,
-- Evaluaciones, Progreso, Reportes)
-- Base: db_cursos_online
-- Password: Admin$2026
-- Hash BCrypt: $2a$12$Lv0CXWPt/t7ZrJ3TSG7wGeLBSX/7OHhWobYG5tBHbupJHLbykze2W
-- ============================================================

-- 1) Catalogos basicos
INSERT INTO cat_rol (cod_rol, des_nombre, des_descripcion)
SELECT v.cod_rol, v.des_nombre, v.des_descripcion
FROM (VALUES
    ('ROL_ADMIN',    'Administrador', 'Control total del sistema'),
    ('ROL_PROFESOR', 'Profesor',      'Gestion de secciones asignadas'),
    ('ROL_ALUMNO',   'Alumno',        'Acceso a cursos y evaluaciones')
) AS v(cod_rol, des_nombre, des_descripcion)
WHERE NOT EXISTS (SELECT 1 FROM cat_rol r WHERE r.cod_rol = v.cod_rol);

INSERT INTO cat_tipo_pregunta (cod_tipo, des_nombre)
SELECT v.cod_tipo, v.des_nombre
FROM (VALUES
    ('OPCION_MULTIPLE',  'Opcion Multiple'),
    ('VERDADERO_FALSO',  'Verdadero / Falso'),
    ('COMPLETAR_CODIGO', 'Completar Codigo')
) AS v(cod_tipo, des_nombre)
WHERE NOT EXISTS (SELECT 1 FROM cat_tipo_pregunta t WHERE t.cod_tipo = v.cod_tipo);

INSERT INTO cat_tipo_recurso (cod_tipo, des_nombre)
SELECT v.cod_tipo, v.des_nombre
FROM (VALUES
    ('ARCHIVO', 'Archivo adjunto'),
    ('ENLACE',  'Enlace externo'),
    ('VIDEO',   'Video')
) AS v(cod_tipo, des_nombre)
WHERE NOT EXISTS (SELECT 1 FROM cat_tipo_recurso t WHERE t.cod_tipo = v.cod_tipo);

INSERT INTO cat_anio_escolar (val_anio, des_descripcion, fec_inicio, fec_fin)
SELECT v.val_anio, v.des_descripcion, v.fec_inicio, v.fec_fin
FROM (VALUES
    (2026, 'Anio escolar 2026', DATE '2026-01-15', DATE '2026-12-15')
) AS v(val_anio, des_descripcion, fec_inicio, fec_fin)
WHERE NOT EXISTS (SELECT 1 FROM cat_anio_escolar a WHERE a.val_anio = v.val_anio);

INSERT INTO cat_nivel (cod_nivel, des_nombre, val_orden)
SELECT v.cod_nivel, v.des_nombre, v.val_orden
FROM (VALUES
    ('BASICO', 'Basico', 1)
) AS v(cod_nivel, des_nombre, val_orden)
WHERE NOT EXISTS (SELECT 1 FROM cat_nivel n WHERE n.cod_nivel = v.cod_nivel);

-- 2) Usuarios base
INSERT INTO seg_usuario (id_rol, des_nombres, des_apellidos, des_correo, pwd_contrasena, est_activo, est_pwd_temporal)
SELECT r.id_rol, 'Super', 'Administrador', 'admin@colegio.edu',
       '$2a$12$Lv0CXWPt/t7ZrJ3TSG7wGeLBSX/7OHhWobYG5tBHbupJHLbykze2W',
       true, false
FROM cat_rol r
WHERE r.cod_rol = 'ROL_ADMIN'
  AND NOT EXISTS (SELECT 1 FROM seg_usuario u WHERE u.des_correo = 'admin@colegio.edu');

INSERT INTO seg_usuario (id_rol, des_nombres, des_apellidos, des_correo, pwd_contrasena, est_activo, est_pwd_temporal)
SELECT r.id_rol, 'Carlos', 'Profesor', 'profesor@colegio.edu',
       '$2a$12$Lv0CXWPt/t7ZrJ3TSG7wGeLBSX/7OHhWobYG5tBHbupJHLbykze2W',
       true, false
FROM cat_rol r
WHERE r.cod_rol = 'ROL_PROFESOR'
  AND NOT EXISTS (SELECT 1 FROM seg_usuario u WHERE u.des_correo = 'profesor@colegio.edu');

INSERT INTO seg_usuario (id_rol, des_nombres, des_apellidos, des_correo, pwd_contrasena, est_activo, est_pwd_temporal)
SELECT r.id_rol, 'Ana', 'Alumno', 'alumno@colegio.edu',
       '$2a$12$Lv0CXWPt/t7ZrJ3TSG7wGeLBSX/7OHhWobYG5tBHbupJHLbykze2W',
       true, false
FROM cat_rol r
WHERE r.cod_rol = 'ROL_ALUMNO'
  AND NOT EXISTS (SELECT 1 FROM seg_usuario u WHERE u.des_correo = 'alumno@colegio.edu');

-- 3) Curso, modulo, lecciones publicadas
WITH nivel AS (
    SELECT id_nivel FROM cat_nivel WHERE cod_nivel = 'BASICO' LIMIT 1
),
curso_ins AS (
    INSERT INTO cat_curso (id_nivel, des_nombre, des_descripcion, est_publicado, est_activo, fec_publicacion)
    SELECT n.id_nivel, 'Programacion I', 'Curso base de programacion', true, true, NOW()
    FROM nivel n
    WHERE NOT EXISTS (SELECT 1 FROM cat_curso c WHERE c.des_nombre = 'Programacion I')
    RETURNING id_curso
),
curso AS (
    SELECT id_curso FROM cat_curso WHERE des_nombre = 'Programacion I'
),
modulo_ins AS (
    INSERT INTO tra_modulo (id_curso, des_nombre, des_descripcion, val_orden, est_activo)
    SELECT c.id_curso, 'Modulo 1', 'Introduccion', 1, true
    FROM curso c
    WHERE NOT EXISTS (
        SELECT 1 FROM tra_modulo m
        WHERE m.id_curso = c.id_curso AND m.des_nombre = 'Modulo 1'
    )
    RETURNING id_modulo
),
modulo AS (
    SELECT id_modulo FROM tra_modulo m
    JOIN curso c ON c.id_curso = m.id_curso
    WHERE m.des_nombre = 'Modulo 1' AND c.id_curso = (SELECT id_curso FROM curso)
),
leccion1 AS (
    INSERT INTO tra_leccion (id_modulo, des_nombre, des_contenido, val_orden, est_obligatoria, est_publicada, est_activa, fec_publicacion)
    SELECT m.id_modulo, 'Leccion 1', 'Contenido basico', 1, true, true, true, NOW()
    FROM modulo m
    WHERE NOT EXISTS (
        SELECT 1 FROM tra_leccion l
        WHERE l.id_modulo = m.id_modulo AND l.des_nombre = 'Leccion 1'
    )
    RETURNING id_leccion
),
leccion2 AS (
    INSERT INTO tra_leccion (id_modulo, des_nombre, des_contenido, val_orden, est_obligatoria, est_publicada, est_activa, fec_publicacion)
    SELECT m.id_modulo, 'Leccion 2', 'Contenido intermedio', 2, true, true, true, NOW()
    FROM modulo m
    WHERE NOT EXISTS (
        SELECT 1 FROM tra_leccion l
        WHERE l.id_modulo = m.id_modulo AND l.des_nombre = 'Leccion 2'
    )
    RETURNING id_leccion
)
SELECT 1;

-- 4) Seccion y asignaciones
WITH curso AS (
    SELECT id_curso FROM cat_curso WHERE des_nombre = 'Programacion I'
),
anio AS (
    SELECT id_anio_escolar FROM cat_anio_escolar WHERE val_anio = 2026
),
seccion_ins AS (
    INSERT INTO tra_seccion (id_curso, id_anio_escolar, des_nombre, est_activa)
    SELECT c.id_curso, a.id_anio_escolar, 'Programacion I - A', true
    FROM curso c, anio a
    WHERE NOT EXISTS (
        SELECT 1 FROM tra_seccion s
        WHERE s.id_curso = c.id_curso AND s.id_anio_escolar = a.id_anio_escolar
          AND s.des_nombre = 'Programacion I - A'
    )
    RETURNING id_seccion
),
seccion AS (
    SELECT id_seccion FROM tra_seccion s
    JOIN curso c ON c.id_curso = s.id_curso
    JOIN anio a ON a.id_anio_escolar = s.id_anio_escolar
    WHERE s.des_nombre = 'Programacion I - A'
)
INSERT INTO rel_profesor_seccion (id_usuario, id_seccion, est_activo)
SELECT p.id_usuario, s.id_seccion, true
FROM seg_usuario p, seccion s
WHERE p.des_correo = 'profesor@colegio.edu'
  AND NOT EXISTS (
      SELECT 1 FROM rel_profesor_seccion r
      WHERE r.id_usuario = p.id_usuario AND r.id_seccion = s.id_seccion AND r.est_activo = true
  );

INSERT INTO rel_alumno_seccion (id_usuario, id_seccion, est_activo)
SELECT a.id_usuario, s.id_seccion, true
FROM seg_usuario a, seccion s
WHERE a.des_correo = 'alumno@colegio.edu'
  AND NOT EXISTS (
      SELECT 1 FROM rel_alumno_seccion r
      WHERE r.id_usuario = a.id_usuario AND r.id_seccion = s.id_seccion AND r.est_activo = true
  );

-- 5) Evaluacion activa + pregunta + opciones
WITH modulo AS (
    SELECT m.id_modulo FROM tra_modulo m
    JOIN cat_curso c ON c.id_curso = m.id_curso
    WHERE m.des_nombre = 'Modulo 1' AND c.des_nombre = 'Programacion I'
),
eval_ins AS (
    INSERT INTO tra_evaluacion (id_modulo, des_titulo, des_instrucciones, val_puntaje_minimo, val_tiempo_limite, val_max_intentos, est_activa)
    SELECT m.id_modulo, 'Evaluacion 1', 'Resuelve la prueba', 60.00, 30, 3, true
    FROM modulo m
    WHERE NOT EXISTS (
        SELECT 1 FROM tra_evaluacion e
        WHERE e.id_modulo = m.id_modulo AND e.des_titulo = 'Evaluacion 1'
    )
    RETURNING id_evaluacion
),
eval AS (
    SELECT e.id_evaluacion FROM tra_evaluacion e
    JOIN modulo m ON m.id_modulo = e.id_modulo
    WHERE e.des_titulo = 'Evaluacion 1'
),
tipo AS (
    SELECT id_tipo_pregunta FROM cat_tipo_pregunta WHERE cod_tipo = 'OPCION_MULTIPLE'
),
preg_ins AS (
    INSERT INTO tra_pregunta (id_evaluacion, id_tipo_pregunta, des_enunciado, val_orden, val_puntaje, est_activa)
    SELECT e.id_evaluacion, t.id_tipo_pregunta, 'Capital de Peru', 1, 10.00, true
    FROM eval e, tipo t
    WHERE NOT EXISTS (
        SELECT 1 FROM tra_pregunta p
        WHERE p.id_evaluacion = e.id_evaluacion AND p.des_enunciado = 'Capital de Peru'
    )
    RETURNING id_pregunta
),
preg AS (
    SELECT id_pregunta FROM tra_pregunta p
    JOIN eval e ON e.id_evaluacion = p.id_evaluacion
    WHERE p.des_enunciado = 'Capital de Peru'
)
INSERT INTO tra_opcion_respuesta (id_pregunta, des_opcion, est_correcta, val_orden)
SELECT p.id_pregunta, 'Lima', true, 1
FROM preg p
WHERE NOT EXISTS (
    SELECT 1 FROM tra_opcion_respuesta o
    WHERE o.id_pregunta = p.id_pregunta AND o.des_opcion = 'Lima'
);

INSERT INTO tra_opcion_respuesta (id_pregunta, des_opcion, est_correcta, val_orden)
SELECT p.id_pregunta, 'Cusco', false, 2
FROM preg p
WHERE NOT EXISTS (
    SELECT 1 FROM tra_opcion_respuesta o
    WHERE o.id_pregunta = p.id_pregunta AND o.des_opcion = 'Cusco'
);

-- 6) Progreso completado (permite iniciar intento)
WITH alumno AS (
    SELECT id_usuario FROM seg_usuario WHERE des_correo = 'alumno@colegio.edu'
),
lecciones AS (
    SELECT l.id_leccion FROM tra_leccion l
    JOIN tra_modulo m ON m.id_modulo = l.id_modulo
    JOIN cat_curso c ON c.id_curso = m.id_curso
    WHERE c.des_nombre = 'Programacion I'
)
INSERT INTO tra_progreso_leccion (id_usuario, id_leccion, est_completada, fec_inicio, fec_completado)
SELECT a.id_usuario, l.id_leccion, true, NOW(), NOW()
FROM alumno a, lecciones l
WHERE NOT EXISTS (
    SELECT 1 FROM tra_progreso_leccion p
    WHERE p.id_usuario = a.id_usuario AND p.id_leccion = l.id_leccion
);
