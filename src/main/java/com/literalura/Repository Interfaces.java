package com.literalura.repository;

import com.literalura.model.Autor;
import com.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(Integer anio, Integer anioMismo);
    List<Autor> findByNombreContainingIgnoreCase(String nombre);
}

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByIdiomaIgnoreCase(String idioma);
    
    @Query("SELECT l FROM Libro l ORDER BY l.numeroDescargas DESC LIMIT 10")
    List<Libro> findTop10ByOrderByNumeroDescargasDesc();
}
