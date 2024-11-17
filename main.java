package com.literalura;

import com.literalura.service.GutendexClient;
import com.literalura.repository.LibroRepository;
import com.literalura.repository.AutorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Scanner;

@SpringBootApplication
public class LiterAluraApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner commandLineRunner(
            GutendexClient gutendexClient,
            LibroRepository libroRepository,
            AutorRepository autorRepository) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            boolean continuar = true;
            
            while (continuar) {
                System.out.println("\n=== LiterAlura - Menú Principal ===");
                System.out.println("1. Buscar libro por título");
                System.out.println("2. Listar todos los libros");
                System.out.println("3. Listar libros por idioma");
                System.out.println("4. Listar autores");
                System.out.println("5. Buscar autores vivos en año específico");
                System.out.println("6. Top 10 libros más descargados");
                System.out.println("0. Salir");
                System.out.print("\nElija una opción: ");
                
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir nueva línea
                
                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese el título del libro: ");
                        String titulo = scanner.nextLine();
                        var libroOpt = gutendexClient.buscarLibroPorTitulo(titulo);
                        libroOpt.ifPresentOrElse(
                            libro -> {
                                libroRepository.save(libro);
                                System.out.println("Libro encontrado y guardado: " + libro.getTitulo());
                            },
                            () -> System.out.println("Libro no encontrado")
                        );
                        break;
                        
                    case 2:
                        libroRepository.findAll().forEach(libro -> 
                            System.out.println(libro.getTitulo() + " - " + libro.getAutor().getNombre()));
                        break;
                        
                    case 3:
                        System.out.print("Ingrese el idioma (en, es, fr, etc.): ");
                        String idioma = scanner.nextLine();
                        libroRepository.findByIdiomaIgnoreCase(idioma).forEach(libro ->
                            System.out.println(libro.getTitulo()));
                        break;
                        
                    case 4:
                        autorRepository.findAll().forEach(autor ->
                            System.out.println(autor.getNombre() + " (" + 
                                autor.getAnioNacimiento() + " - " + 
                                autor.getAnioFallecimiento() + ")"));
                        break;
                        
                    case 5:
                        System.out.print("Ingrese el año: ");
                        int anio = scanner.nextInt();
                        autorRepository.findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(
                            anio, anio).forEach(autor ->
                                System.out.println(autor.getNombre()));
                        break;
                        
                    case 6:
                        libroRepository.findTop10ByOrderByNumeroDescargasDesc().forEach(libro ->
                            System.out.println(libro.getTitulo() + " - " + 
                                libro.getNumeroDescargas() + " descargas"));
                        break;
                        
                    case 0:
                        continuar = false;
                        break;
                        
                    default:
                        System.out.println("Opción no válida");
                }
            }
            
            scanner.close();
        };
    }
}
