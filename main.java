package com.literalura;

import com.literalura.service.LibroService;
import com.literalura.service.AutorService;
import com.literalura.model.Libro;
import com.literalura.model.Autor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.List;

@SpringBootApplication
@Slf4j
public class LiterAluraApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            LibroService libroService,
            AutorService autorService) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            boolean continuar = true;

            while (continuar) {
                try {
                    mostrarMenu();
                    int opcion = Integer.parseInt(scanner.nextLine());

                    switch (opcion) {
                        case 1:
                            buscarLibro(scanner, libroService);
                            break;
                        case 2:
                            listarLibros(libroService);
                            break;
                        case 3:
                            listarLibrosPorIdioma(scanner, libroService);
                            break;
                        case 4:
                            listarAutores(autorService);
                            break;
                        case 5:
                            buscarAutoresVivos(scanner, autorService);
                            break;
                        case 6:
                            mostrarTop10Libros(libroService);
                            break;
                        case 0:
                            continuar = false;
                            System.out.println("¡Gracias por usar LiterAlura!");
                            break;
                        default:
                            System.out.println("Opción no válida");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Por favor, ingrese un número válido");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    log.error("Error en la aplicación", e);
                }

                if (continuar) {
                    System.out.println("\nPresione Enter para continuar...");
                    scanner.nextLine();
                }
            }
        };
    }

    private void mostrarMenu() {
        System.out.println("\n=== LiterAlura - Menú Principal ===");
        System.out.println("1. Buscar libro por título");
        System.out.println("2. Listar todos los libros");
        System.out.println("3. Listar libros por idioma");
        System.out.println("4. Listar autores");
        System.out.println("5. Buscar autores vivos en año específico");
        System.out.println("6. Top 10 libros más descargados");
        System.out.println("0. Salir");
        System.out.print("\nElija una opción: ");
    }

    private void buscarLibro(Scanner scanner, LibroService libroService) {
        System.out.print("Ingrese el título del libro: ");
        String titulo = scanner.nextLine();
        try {
            Libro libro = libroService.buscarYGuardarLibro(titulo);
            System.out.println("Libro encontrado y guardado: " + libro.getTitulo());
            if (libro.getAutor() != null) {
                System.out.println("Autor: " + libro.getAutor().getNombre());
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el libro: " + e.getMessage());
        }
    }

    private void listarLibros(LibroService libroService) {
        List<Libro> libros = libroService.listarLibros();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados");
            return;
        }
        System.out.println("\nLibros registrados:");
        libros.forEach(libro -> {
            System.out.printf("Título: %s%n", libro.getTitulo());
            System.out.printf("Autor: %s%n", 
                libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido");
            System.out.printf("Idioma: %s%n", libro.getIdioma());
            System.out.printf("Descargas: %d%n%n", libro.getNumeroDescargas());
        });
    }

    private void listarLibrosPorIdioma(Scanner scanner, LibroService libroService) {
        System.out.print("Ingrese el idioma (en, es, fr, etc.): ");
        String idioma = scanner.nextLine();
        List<Libro> libros = libroService.listarLibrosPorIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en el idioma: " + idioma);
            return;
        }
        System.out.println("\nLibros en " + idioma + ":");
        libros.forEach(libro -> System.out.println("- " + libro.getTitulo()));
    }

    private void listarAutores(AutorService autorService) {
        List<Autor> autores = autorService.listarAutores();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados");
            return;
        }
        System.out.println("\nAutores registrados:");
        autores.forEach(autor -> {
            System.out.printf("Nombre: %s%n", autor.getNombre());
            System.out.printf("Años: %d - %s%n%n", 
                autor.getAnioNacimiento(),
                autor.getAnioFallecimiento() != null ? 
                    autor.getAnioFallecimiento().toString() : "presente");
        });
    }

    private void buscarAutoresVivos(Scanner scanner, AutorService autorService) {
        System.out.print("Ingrese el año: ");
        try {
            int anio = Integer.parseInt(scanner.nextLine());
            List<Autor> autores = autorService.buscarAutoresVivosPorAnio(anio);
            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año " + anio);
                return;
            }
            System.out.println("\nAutores vivos en " + anio + ":");
            autores.forEach(autor -> System.out.println("- " + autor.getNombre()));
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un año válido");
        }
    }

    private void mostrarTop10Libros(LibroService libroService) {
        List<Libro> libros = libroService.obtenerTop10Libros();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados");
            return;
        }
        System.out.println("\nTop 10 libros más descargados:");
        libros.forEach(libro -> {
            System.out.printf("%s - %d descargas%n", 
                libro.getTitulo(), 
                libro.getNumeroDescargas());
        });
    }
}
