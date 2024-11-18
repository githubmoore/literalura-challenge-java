package com.literalura.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonAlias;

@Data
@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private Integer anioNacimiento;
    private Integer anioFallecimiento;
}

@Data
@Entity
@Table(name = "libros")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonAlias("title")
    private String titulo;
    
    private String idioma;
    
    @JsonAlias("download_count")
    private Integer numeroDescargas;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "autor_id")
    private Autor autor;
}
