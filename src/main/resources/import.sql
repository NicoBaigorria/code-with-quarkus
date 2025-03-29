-- Insertando datos en la tabla project
INSERT INTO project (id, uuid, nombre, creationDate, status)
VALUES
(1, 'uuid-1', 'Project 1', '2025-03-27 10:00:00', true),
(2, 'uuid-2', 'Project 2', '2025-03-26 10:00:00', true);

-- Insertando datos en la tabla projectdetail
INSERT INTO projectdetail (id, CodigoProyecto, Descripcion, Area, Estado)
VALUES
(1, 1, 'Detail 1', 700, true),
(2, 2, 'Detail 2', 300, true);
