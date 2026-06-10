-- =============================================================
-- DATOS DE EJEMPLO — Colegio Albert Einstein
-- Contraseña de todos los usuarios: hesed2025
-- Hash BCrypt (cost 10) de "hesed2025"
-- =============================================================

-- ─── LIMPIAR (respeta FKs) ───────────────────────────────────
TRUNCATE TABLE mensajes          RESTART IDENTITY CASCADE;
TRUNCATE TABLE conversaciones    RESTART IDENTITY CASCADE;
TRUNCATE TABLE pagos             RESTART IDENTITY CASCADE;
TRUNCATE TABLE horarios          RESTART IDENTITY CASCADE;
TRUNCATE TABLE asistencias       RESTART IDENTITY CASCADE;
TRUNCATE TABLE calificaciones    RESTART IDENTITY CASCADE;
TRUNCATE TABLE tareas            RESTART IDENTITY CASCADE;
TRUNCATE TABLE mensualidades     RESTART IDENTITY CASCADE;
TRUNCATE TABLE matriculas        RESTART IDENTITY CASCADE;
TRUNCATE TABLE padre_estudiante;
TRUNCATE TABLE curso_profesores;
TRUNCATE TABLE cursos            RESTART IDENTITY CASCADE;
TRUNCATE TABLE estudiantes       RESTART IDENTITY CASCADE;
TRUNCATE TABLE padres            RESTART IDENTITY CASCADE;
TRUNCATE TABLE profesores        RESTART IDENTITY CASCADE;
TRUNCATE TABLE usuarios          RESTART IDENTITY CASCADE;

-- =============================================================
-- USUARIOS  (password: hesed2025)
-- =============================================================
INSERT INTO usuarios (id, email, password, nombre, apellido, apellido_materno, rol, created_at) VALUES
-- Director
(1,  'director@einstein.edu.pe',       '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lwiC', 'María',      'García',   'Lozano',    'DIRECTOR',   NOW()),
-- Profesores
(2,  'c.rodriguez@einstein.edu.pe',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lwiC', 'Carlos',     'Rodríguez','Vega',      'PROFESOR',   NOW()),
(3,  'a.torres@einstein.edu.pe',       '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lwiC', 'Ana',        'Torres',   'Ríos',      'PROFESOR',   NOW()),
(4,  'j.mendez@einstein.edu.pe',       '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lwiC', 'Juan',       'Méndez',   'Palomino',  'PROFESOR',   NOW()),
-- Padres
(5,  'r.perez@gmail.com',              '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lwiC', 'Roberto',    'Pérez',    'Huanca',    'PADRE',      NOW()),
(6,  'l.vargas@gmail.com',             '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lwiC', 'Lucía',      'Vargas',   'Mamani',    'PADRE',      NOW()),
(7,  'm.castro@gmail.com',             '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lwiC', 'Miguel',     'Castro',   'Quispe',    'PADRE',      NOW()),
-- Estudiantes
(8,  'sofia.perez@einstein.edu.pe',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lwiC', 'Sofía',      'Pérez',    'Vargas',    'ESTUDIANTE', NOW()),
(9,  'diego.perez@einstein.edu.pe',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lwiC', 'Diego',      'Pérez',    'Vargas',    'ESTUDIANTE', NOW()),
(10, 'valentina.v@einstein.edu.pe',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lwiC', 'Valentina',  'Vargas',   'López',     'ESTUDIANTE', NOW()),
(11, 'camila.v@einstein.edu.pe',       '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lwiC', 'Camila',     'Vargas',   'López',     'ESTUDIANTE', NOW()),
(12, 'mateo.castro@einstein.edu.pe',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lwiC', 'Mateo',      'Castro',   'Flores',    'ESTUDIANTE', NOW()),
(13, 'isabella.c@einstein.edu.pe',     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lwiC', 'Isabella',   'Castro',   'Flores',    'ESTUDIANTE', NOW());

-- =============================================================
-- PROFESORES
-- =============================================================
INSERT INTO profesores (id, usuario_id, telefono, activo) VALUES
(1, 2, '987-111-001', true),
(2, 3, '987-111-002', true),
(3, 4, '987-111-003', true);

-- =============================================================
-- PADRES
-- =============================================================
INSERT INTO padres (id, usuario_id, dni, telefono, activo) VALUES
(1, 5, '43210001', '987-222-001', true),
(2, 6, '43210002', '987-222-002', true),
(3, 7, '43210003', '987-222-003', true);

-- =============================================================
-- ESTUDIANTES
-- nivel: INICIAL(grado 1=3años, 2=4años, 3=5años) | PRIMARIA(1-6) | SECUNDARIA(1-5)
-- =============================================================
INSERT INTO estudiantes (id, usuario_id, dni, fecha_nacimiento, grado, seccion, nivel, direccion) VALUES
(1,  8,  '75100001', '2019-03-15', 1, 'A', 'PRIMARIA',   'Av. Los Álamos 234'),
(2,  9,  '75100002', '2017-07-22', 3, 'A', 'PRIMARIA',   'Av. Los Álamos 234'),
(3,  10, '75100003', '2012-11-05', 1, 'A', 'SECUNDARIA', 'Jr. Las Rosas 456'),
(4,  11, '75100004', '2021-01-30', 3, 'U', 'INICIAL',    'Jr. Las Rosas 456'),
(5,  12, '75100005', '2011-09-18', 2, 'B', 'SECUNDARIA', 'Calle Progreso 789'),
(6,  13, '75100006', '2015-04-12', 5, 'A', 'PRIMARIA',   'Calle Progreso 789');

-- =============================================================
-- CURSOS  (grados como string: "1,2,3")
-- =============================================================
INSERT INTO cursos (id, nombre, descripcion, nivel, grados, seccion, activo, anio) VALUES
-- Inicial
(1, 'Comunicación',       'Desarrollo del lenguaje oral y escrito',          'INICIAL',    '1,2,3', NULL, true, 2026),
(2, 'Matemática',         'Nociones de número, forma y espacio',             'INICIAL',    '1,2,3', NULL, true, 2026),
-- Primaria
(3, 'Comunicación',       'Comprensión lectora y expresión escrita',         'PRIMARIA',   '1,2,3', NULL, true, 2026),
(4, 'Matemática',         'Aritmética y geometría básica',                   'PRIMARIA',   '1,2,3', NULL, true, 2026),
(5, 'Ciencias Naturales', 'Ciencias del entorno natural y experimentos',     'PRIMARIA',   '4,5,6', NULL, true, 2026),
-- Secundaria
(6, 'Comunicación',       'Literatura, redacción y comprensión de textos',   'SECUNDARIA', '1,2,3', NULL, true, 2026),
(7, 'Álgebra',            'Ecuaciones, sistemas y funciones',                'SECUNDARIA', '1,2',   NULL, true, 2026),
(8, 'Ciencias',           'Biología, química e introducción a física',       'SECUNDARIA', '1,2,3', NULL, true, 2026);

-- =============================================================
-- RELACIONES CURSOS ↔ PROFESORES
-- =============================================================
INSERT INTO curso_profesores (curso_id, profesor_id) VALUES
(1, 2), -- Comunicación Inicial   → Ana Torres
(2, 1), -- Matemática Inicial     → Carlos Rodríguez
(3, 2), -- Comunicación Primaria  → Ana Torres
(4, 1), -- Matemática Primaria    → Carlos Rodríguez
(5, 3), -- Ciencias Primaria      → Juan Méndez
(6, 2), -- Comunicación Secundaria→ Ana Torres
(7, 1), -- Álgebra Secundaria     → Carlos Rodríguez
(8, 3); -- Ciencias Secundaria    → Juan Méndez

-- =============================================================
-- RELACIONES PADRES ↔ ESTUDIANTES
-- =============================================================
INSERT INTO padre_estudiante (padre_id, estudiante_id) VALUES
(1, 1), -- Roberto → Sofía
(1, 2), -- Roberto → Diego
(2, 3), -- Lucía   → Valentina
(2, 4), -- Lucía   → Camila
(3, 5), -- Miguel  → Mateo
(3, 6); -- Miguel  → Isabella

-- =============================================================
-- MATRÍCULAS (año 2026)
-- =============================================================
INSERT INTO matriculas (id, estudiante_id, anio, grado, nivel, estado, estado_pago, monto_matricula, monto_mensualidad, dia_pago, fecha_creacion) VALUES
(1, 1, 2026, 1, 'PRIMARIA',   'ACTIVA', 'PAGADO',   200.00, 150.00, 15, '2026-02-10 09:00:00'),
(2, 2, 2026, 3, 'PRIMARIA',   'ACTIVA', 'PAGADO',   200.00, 150.00, 15, '2026-02-10 09:30:00'),
(3, 3, 2026, 1, 'SECUNDARIA', 'ACTIVA', 'PENDIENTE',250.00, 180.00, 10, '2026-02-11 10:00:00'),
(4, 4, 2026, 3, 'INICIAL',    'ACTIVA', 'PAGADO',   150.00, 120.00, 20, '2026-02-11 10:30:00'),
(5, 5, 2026, 2, 'SECUNDARIA', 'ACTIVA', 'PAGADO',   250.00, 180.00, 10, '2026-02-12 09:00:00'),
(6, 6, 2026, 5, 'PRIMARIA',   'ACTIVA', 'PENDIENTE',200.00, 150.00, 15, '2026-02-12 09:30:00');

-- =============================================================
-- MENSUALIDADES  (Marzo–Junio 2026)
-- =============================================================
INSERT INTO mensualidades (matricula_id, mes, anio, monto, estado_pago, fecha_vencimiento, fecha_pago) VALUES
-- Sofía (matrícula 1)
(1, 'MARZO',  2026, 150.00, 'PAGADO',   '2026-03-15', '2026-03-12 10:00:00'),
(1, 'ABRIL',  2026, 150.00, 'PAGADO',   '2026-04-15', '2026-04-10 10:00:00'),
(1, 'MAYO',   2026, 150.00, 'PAGADO',   '2026-05-15', '2026-05-14 10:00:00'),
(1, 'JUNIO',  2026, 150.00, 'PENDIENTE','2026-06-15', NULL),
-- Diego (matrícula 2)
(2, 'MARZO',  2026, 150.00, 'PAGADO',   '2026-03-15', '2026-03-13 10:00:00'),
(2, 'ABRIL',  2026, 150.00, 'PAGADO',   '2026-04-15', '2026-04-11 10:00:00'),
(2, 'MAYO',   2026, 150.00, 'PAGADO',   '2026-05-15', '2026-05-13 10:00:00'),
(2, 'JUNIO',  2026, 150.00, 'PENDIENTE','2026-06-15', NULL),
-- Valentina (matrícula 3) — padre con deuda desde mayo
(3, 'MARZO',  2026, 180.00, 'PAGADO',   '2026-03-10', '2026-03-09 10:00:00'),
(3, 'ABRIL',  2026, 180.00, 'PAGADO',   '2026-04-10', '2026-04-08 10:00:00'),
(3, 'MAYO',   2026, 180.00, 'PENDIENTE','2026-05-10', NULL),
(3, 'JUNIO',  2026, 180.00, 'PENDIENTE','2026-06-10', NULL),
-- Camila (matrícula 4)
(4, 'MARZO',  2026, 120.00, 'PAGADO',   '2026-03-20', '2026-03-19 10:00:00'),
(4, 'ABRIL',  2026, 120.00, 'PAGADO',   '2026-04-20', '2026-04-18 10:00:00'),
(4, 'MAYO',   2026, 120.00, 'PAGADO',   '2026-05-20', '2026-05-20 10:00:00'),
(4, 'JUNIO',  2026, 120.00, 'PENDIENTE','2026-06-20', NULL),
-- Mateo (matrícula 5)
(5, 'MARZO',  2026, 180.00, 'PAGADO',   '2026-03-10', '2026-03-08 10:00:00'),
(5, 'ABRIL',  2026, 180.00, 'PAGADO',   '2026-04-10', '2026-04-07 10:00:00'),
(5, 'MAYO',   2026, 180.00, 'PAGADO',   '2026-05-10', '2026-05-09 10:00:00'),
(5, 'JUNIO',  2026, 180.00, 'PENDIENTE','2026-06-10', NULL),
-- Isabella (matrícula 6) — deuda desde abril
(6, 'MARZO',  2026, 150.00, 'PAGADO',   '2026-03-15', '2026-03-14 10:00:00'),
(6, 'ABRIL',  2026, 150.00, 'PENDIENTE','2026-04-15', NULL),
(6, 'MAYO',   2026, 150.00, 'PENDIENTE','2026-05-15', NULL),
(6, 'JUNIO',  2026, 150.00, 'PENDIENTE','2026-06-15', NULL);

-- =============================================================
-- HORARIOS (dia: 1=Lun, 2=Mar, 3=Mié, 4=Jue, 5=Vie)
-- =============================================================
INSERT INTO horarios (curso_id, dia, hora_inicio, hora_fin, salon) VALUES
(1, 1, '08:00', '09:00', 'Aula Inicial A'), (1, 3, '08:00', '09:00', 'Aula Inicial A'),
(2, 2, '08:00', '09:00', 'Aula Inicial A'), (2, 4, '08:00', '09:00', 'Aula Inicial A'),
(3, 1, '09:00', '10:00', 'Aula 101'),       (3, 3, '09:00', '10:00', 'Aula 101'),
(4, 2, '09:00', '10:00', 'Aula 101'),       (4, 4, '09:00', '10:00', 'Aula 101'),
(5, 1, '10:00', '11:00', 'Lab. Ciencias'),  (5, 5, '10:00', '11:00', 'Lab. Ciencias'),
(6, 1, '11:00', '12:00', 'Aula 201'),       (6, 3, '11:00', '12:00', 'Aula 201'),
(7, 2, '11:00', '12:00', 'Aula 202'),       (7, 4, '11:00', '12:00', 'Aula 202'),
(8, 2, '10:00', '11:00', 'Lab. Ciencias'),  (8, 5, '11:00', '12:00', 'Lab. Ciencias');

-- =============================================================
-- TAREAS
-- =============================================================
INSERT INTO tareas (id, curso_id, titulo, descripcion, fecha_entrega) VALUES
(1, 3, 'Lectura comprensiva — Capítulo 1',   'Leer el capítulo y responder las preguntas del cuadernillo.',  '2026-04-25 23:59:00'),
(2, 3, 'Redacción párrafo descriptivo',       'Escribir un párrafo describiendo tu lugar favorito.',          '2026-05-09 23:59:00'),
(3, 4, 'Sumas y restas con llevada',          'Resolver ejercicios del libro pág. 34–36.',                   '2026-04-28 23:59:00'),
(4, 4, 'Multiplicación por una cifra',        'Completar tabla de multiplicar y resolver los 20 problemas.',  '2026-05-12 23:59:00'),
(5, 5, 'Experimento: ciclo del agua',         'Armar maqueta y presentar informe de observación.',            '2026-05-20 23:59:00'),
(6, 6, 'Análisis de texto narrativo',         'Identificar estructura y recursos literarios del cuento.',     '2026-04-30 23:59:00'),
(7, 7, 'Ecuaciones de primer grado',          'Resolver las 15 ecuaciones propuestas, mostrar procedimiento.','2026-05-07 23:59:00'),
(8, 7, 'Sistemas de ecuaciones 2x2',          'Aplicar método de sustitución y eliminación en 10 sistemas.',  '2026-05-21 23:59:00'),
(9, 8, 'Informe: células procariotas',        'Investigar y presentar informe con diagramas.',                '2026-05-14 23:59:00');

-- =============================================================
-- CALIFICACIONES
-- =============================================================
INSERT INTO calificaciones (estudiante_id, curso_id, tarea_id, profesor_id, valor, tipo, fecha, observaciones) VALUES
-- Sofía (est 1) — Comunicación Primaria (curso 3)
(1, 3, NULL, 2, 14.0, 'EXAMEN',       '2026-04-20 09:00:00', 'Buen desempeño en comprensión'),
(1, 3, 1,    2, 16.0, 'TAREA',        '2026-04-26 09:00:00', NULL),
(1, 3, 2,    2, 15.0, 'TAREA',        '2026-05-10 09:00:00', NULL),
-- Sofía (est 1) — Matemática Primaria (curso 4)
(1, 4, NULL, 1, 18.0, 'EXAMEN',       '2026-04-22 09:00:00', 'Excelente'),
(1, 4, 3,    1, 17.0, 'TAREA',        '2026-04-29 09:00:00', NULL),
-- Diego (est 2) — Comunicación Primaria (curso 3)
(2, 3, NULL, 2, 12.0, 'EXAMEN',       '2026-04-20 09:00:00', 'Necesita reforzar comprensión de textos'),
(2, 3, 1,    2, 13.0, 'TAREA',        '2026-04-26 09:00:00', NULL),
-- Diego (est 2) — Matemática Primaria (curso 4)
(2, 4, NULL, 1, 16.0, 'EXAMEN',       '2026-04-22 09:00:00', NULL),
(2, 4, 3,    1, 15.0, 'TAREA',        '2026-04-29 09:00:00', NULL),
-- Valentina (est 3) — Comunicación Secundaria (curso 6)
(3, 6, NULL, 2, 15.0, 'EXAMEN',       '2026-04-23 09:00:00', NULL),
(3, 6, 6,    2, 18.0, 'TAREA',        '2026-05-01 09:00:00', 'Análisis muy completo'),
-- Valentina (est 3) — Álgebra (curso 7)
(3, 7, NULL, 1, 11.0, 'EXAMEN',       '2026-04-24 09:00:00', 'Revisar procedimientos'),
(3, 7, 7,    1, 14.0, 'TAREA',        '2026-05-08 09:00:00', NULL),
-- Mateo (est 5) — Álgebra (curso 7)
(5, 7, NULL, 1, 13.0, 'EXAMEN',       '2026-04-24 09:00:00', NULL),
(5, 7, 7,    1, 15.0, 'TAREA',        '2026-05-08 09:00:00', NULL),
(5, 7, 8,    1, 12.0, 'TAREA',        '2026-05-22 09:00:00', NULL),
-- Mateo (est 5) — Ciencias Secundaria (curso 8)
(5, 8, NULL, 3, 14.0, 'EXAMEN',       '2026-05-05 09:00:00', NULL),
(5, 8, 9,    3, 16.0, 'TAREA',        '2026-05-15 09:00:00', NULL),
-- Isabella (est 6) — Ciencias Primaria (curso 5)
(6, 5, NULL, 3, 17.0, 'EXAMEN',       '2026-05-06 09:00:00', 'Muy bien'),
(6, 5, 5,    3, 15.0, 'PROYECTO',     '2026-05-21 09:00:00', 'Maqueta bien presentada');

-- =============================================================
-- ASISTENCIAS (semanas representativas: 02-Jun y 09-Jun 2026)
-- =============================================================
INSERT INTO asistencias (estudiante_id, curso_id, fecha, presente, observaciones) VALUES
-- Sofía (est 1) — Comunicación Primaria
(1, 3, '2026-06-02', true,  NULL),
(1, 3, '2026-06-04', true,  NULL),
(1, 3, '2026-06-09', true,  NULL),
-- Sofía (est 1) — Matemática Primaria
(1, 4, '2026-06-03', true,  NULL),
(1, 4, '2026-06-05', true,  NULL),
-- Diego (est 2) — Comunicación Primaria
(2, 3, '2026-06-02', false, 'Justificado por enfermedad'),
(2, 3, '2026-06-04', true,  NULL),
(2, 3, '2026-06-09', true,  NULL),
-- Diego (est 2) — Matemática Primaria
(2, 4, '2026-06-03', true,  NULL),
(2, 4, '2026-06-05', false, 'Sin justificación'),
-- Valentina (est 3) — Comunicación Secundaria
(3, 6, '2026-06-02', true,  NULL),
(3, 6, '2026-06-04', true,  NULL),
(3, 6, '2026-06-09', true,  NULL),
-- Valentina (est 3) — Álgebra
(3, 7, '2026-06-03', true,  NULL),
(3, 7, '2026-06-05', false, 'Sin justificación'),
-- Camila (est 4) — Comunicación Inicial
(4, 1, '2026-06-02', true,  NULL),
(4, 1, '2026-06-04', true,  NULL),
(4, 1, '2026-06-09', true,  NULL),
-- Mateo (est 5) — Álgebra
(5, 7, '2026-06-03', true,  NULL),
(5, 7, '2026-06-05', true,  NULL),
-- Mateo (est 5) — Ciencias Secundaria
(5, 8, '2026-06-03', true,  NULL),
(5, 8, '2026-06-06', true,  NULL),
-- Isabella (est 6) — Ciencias Primaria
(6, 5, '2026-06-02', true,  NULL),
(6, 5, '2026-06-06', false, 'Justificado');

-- =============================================================
-- PAGOS
-- =============================================================
INSERT INTO pagos (estudiante_id, monto, concepto, fecha_pago, estado, metodo_pago) VALUES
(1, 200.00, 'Matrícula 2026',          '2026-02-10 09:00:00', 'PAGADO',   'Transferencia'),
(2, 200.00, 'Matrícula 2026',          '2026-02-10 09:30:00', 'PAGADO',   'Efectivo'),
(3, 250.00, 'Matrícula 2026',          '2026-02-11 10:00:00', 'PAGADO',   'Transferencia'),
(4, 150.00, 'Matrícula 2026',          '2026-02-11 10:30:00', 'PAGADO',   'Efectivo'),
(5, 250.00, 'Matrícula 2026',          '2026-02-12 09:00:00', 'PAGADO',   'Transferencia'),
(6, 200.00, 'Matrícula 2026',          '2026-02-12 09:30:00', 'PAGADO',   'Efectivo'),
(3, 180.00, 'Mensualidad Mayo 2026',   NULL,                  'PENDIENTE', NULL),
(6, 150.00, 'Mensualidad Abril 2026',  NULL,                  'VENCIDO',   NULL),
(6, 150.00, 'Mensualidad Mayo 2026',   NULL,                  'VENCIDO',   NULL);

-- =============================================================
-- CONVERSACIONES  (padre ↔ profesor)
-- =============================================================
INSERT INTO conversaciones (id, participante1_id, participante2_id, creado_en) VALUES
(1, 5,  3, '2026-05-15 08:30:00'), -- Roberto Pérez    ↔ Ana Torres
(2, 6,  1, '2026-05-20 10:00:00'), -- Lucía Vargas     ↔ Carlos Rodríguez
(3, 7,  3, '2026-06-01 09:00:00'); -- Miguel Castro    ↔ Juan Méndez

-- =============================================================
-- MENSAJES
-- =============================================================
INSERT INTO mensajes (conversacion_id, emisor_id, contenido, enviado_en, leido) VALUES
-- Conv 1: Roberto ↔ Ana
(1, 5, 'Buenos días, profesora Ana. Quería consultar sobre el rendimiento de Sofía en comunicación.', '2026-05-15 08:31:00', true),
(1, 3, 'Hola Roberto, Sofía va muy bien. Tiene un promedio de 15 en las evaluaciones recientes. Sigue practicando la lectura en casa.', '2026-05-15 09:02:00', true),
(1, 5, 'Qué buenas noticias, muchas gracias profesora.', '2026-05-15 09:15:00', true),
-- Conv 2: Lucía ↔ Carlos
(2, 6, 'Profesor, buenos días. Valentina me comentó que tuvo dificultades en el último examen de álgebra.', '2026-05-20 10:01:00', true),
(2, 1, 'Hola Lucía. Sí, Valentina sacó 11. Le recomiendo repasar ecuaciones de primer grado. Hay material de refuerzo en el portal.', '2026-05-20 11:30:00', true),
(2, 6, 'Gracias profesor, lo tendremos en cuenta.', '2026-05-20 12:00:00', false),
-- Conv 3: Miguel ↔ Juan
(3, 7, 'Profesor Méndez, ¿cómo va Isabella en Ciencias Naturales?', '2026-06-01 09:01:00', true),
(3, 4, 'Hola Miguel, Isabella tiene muy buen desempeño, sacó 17 en el último examen. Está entre los mejores del grupo.', '2026-06-01 09:45:00', false);

-- ─── RESETEAR SECUENCIAS ─────────────────────────────────────
SELECT setval('usuarios_id_seq',       (SELECT MAX(id) FROM usuarios));
SELECT setval('profesores_id_seq',     (SELECT MAX(id) FROM profesores));
SELECT setval('padres_id_seq',         (SELECT MAX(id) FROM padres));
SELECT setval('estudiantes_id_seq',    (SELECT MAX(id) FROM estudiantes));
SELECT setval('cursos_id_seq',         (SELECT MAX(id) FROM cursos));
SELECT setval('matriculas_id_seq',     (SELECT MAX(id) FROM matriculas));
SELECT setval('mensualidades_id_seq',  (SELECT MAX(id) FROM mensualidades));
SELECT setval('horarios_id_seq',       (SELECT MAX(id) FROM horarios));
SELECT setval('tareas_id_seq',         (SELECT MAX(id) FROM tareas));
SELECT setval('calificaciones_id_seq', (SELECT MAX(id) FROM calificaciones));
SELECT setval('asistencias_id_seq',    (SELECT MAX(id) FROM asistencias));
SELECT setval('pagos_id_seq',          (SELECT MAX(id) FROM pagos));
SELECT setval('conversaciones_id_seq', (SELECT MAX(id) FROM conversaciones));
SELECT setval('mensajes_id_seq',       (SELECT MAX(id) FROM mensajes));
