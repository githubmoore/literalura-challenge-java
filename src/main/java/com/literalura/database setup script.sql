-- Active: 1731946601749@@aws-0-sa-east-1.pooler.supabase.com@6543@literalura
-- Crear la base de datos
CREATE DATABASE literalura;

-- Conectarse a la base de datos
\c literalura

-- Crear la tabla de autores
CREATE TABLE autores (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    anio_nacimiento INTEGER,
    anio_fallecimiento INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear la tabla de libros
CREATE TABLE libros (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    idioma VARCHAR(10) NOT NULL,
    numero_descargas INTEGER DEFAULT 0,
    autor_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_autor FOREIGN KEY (autor_id) REFERENCES autores(id)
);

-- Crear índices para mejorar el rendimiento de las consultas
CREATE INDEX idx_libros_titulo ON libros(titulo);
CREATE INDEX idx_libros_idioma ON libros(idioma);
CREATE INDEX idx_autores_nombre ON autores(nombre);

-- Crear función para actualizar el timestamp de última modificación
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Crear triggers para actualizar automáticamente el campo updated_at
CREATE TRIGGER update_autores_updated_at
    BEFORE UPDATE ON autores
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_libros_updated_at
    BEFORE UPDATE ON libros
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();
