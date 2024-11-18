package com.literalura.service;

import com.literalura.dto.GutendexResponse;
import com.literalura.dto.BookDto;
import com.literalura.model.Libro;
import com.literalura.model.Autor;
import com.literalura.repository.LibroRepository;
import com.literalura.repository.AutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LibroService {
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String baseUrl = "https://gutendex.com/books/";

    @Transactional
    public Libro buscarYGuardarLibro(String titulo) {
        try {
            String url = baseUrl + "?search=" + titulo.replace(" ", "+");
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

            HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                GutendexResponse gutendexResponse = objectMapper.readValue(
                    response.body(), 
                    GutendexResponse.class
                );

                if (!gutendexResponse.getResults().isEmpty()) {
                    return convertirYGuardarLibro(gutendexResponse.getResults().get(0));
                }
            }
            
            throw new RuntimeException("No se encontró el libro: " + titulo);
        } catch (Exception e) {
            log.error("Error al buscar libro: " + e.getMessage(), e);
            throw new RuntimeException("Error al buscar libro: " + e.getMessage(), e);
        }
    }

    private Libro convertirYGuardarLibro(BookDto bookDto) {
        Libro libro = new Libro();
        libro.setTitulo(bookDto.getTitle());
        libro.setNumeroDescargas(bookDto.getDownloadCount());
        
        // Tomamos el primer idioma de la lista
        if (!bookDto.getLanguages().isEmpty()) {
            libro.setIdioma(bookDto.getLanguages().get(0));
        }

        // Convertimos y guardamos el autor si existe
        if (!bookDto.getAuthors().isEmpty()) {
            Autor autor = convertirAutor(bookDto.getAuthors().get(0));
            libro.setAutor(autor);
        }

        return libroRepository.save(libro);
    }

    private Autor convertirAutor(AuthorDto authorDto) {
        Autor autor = new Autor();
        autor.setNombre(authorDto.getName());
        autor.setAnioNacimiento(authorDto.getBirthYear());
        autor.setAnioFallecimiento(authorDto.getDeathYear());
        return autor;
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public List<Libro> listarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdiomaIgnoreCase(idioma);
    }

    public List<Libro> obtenerTop10Libros() {
        return libroRepository.findTop10ByOrderByNumeroDescargasDesc();
    }
}
