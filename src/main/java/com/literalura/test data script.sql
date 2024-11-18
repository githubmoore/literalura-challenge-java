-- Active: 1731946601749@@aws-0-sa-east-1.pooler.supabase.com@6543@literalura@public
-- Insertar algunos autores de prueba
INSERT INTO autores (nombre, anio_nacimiento, anio_fallecimiento) VALUES
    ('Miguel de Cervantes', 1547, 1616),
    ('William Shakespeare', 1564, 1616),
    ('Jane Austen', 1775, 1817);

-- Insertar algunos libros de prueba
INSERT INTO libros (titulo, idioma, numero_descargas, autor_id) VALUES
    ('Don Quijote de la Mancha', 'es', 1000, 
        (SELECT id FROM autores WHERE nombre = 'Miguel de Cervantes')),
    ('Romeo y Julieta', 'en', 800, 
        (SELECT id FROM autores WHERE nombre = 'William Shakespeare')),
    ('Orgullo y Prejuicio', 'en', 1200, 
        (SELECT id FROM autores WHERE nombre = 'Jane Austen'));
