package com.literalura;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class DatabaseConnectionTest {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseConnectionTest.class, args);
    }

    @Bean
    public CommandLineRunner testDatabaseConnection(JdbcTemplate jdbcTemplate) {
        return args -> {
            try {
                log.info("Testing database connection...");
                
                // Verificar la conexión a la base de datos
                jdbcTemplate.query("SELECT 1", rs -> {
                    log.info("Database connection successful!");
                });

                // Verificar las tablas existentes
                log.info("Checking existing tables...");
                jdbcTemplate.query(
                    "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'",
                    (rs, rowNum) -> {
                        log.info("Found table: {}", rs.getString("table_name"));
                        return null;
                    }
                );

                // Verificar la estructura de la tabla autores
                log.info("Checking 'autores' table structure...");
                jdbcTemplate.query(
                    "SELECT column_name, data_type FROM information_schema.columns WHERE table_name = 'autores'",
                    (rs, rowNum) -> {
                        log.info("Column: {} (Type: {})", 
                            rs.getString("column_name"), 
                            rs.getString("data_type"));
                        return null;
                    }
                );

                // Verificar la estructura de la tabla libros
                log.info("Checking 'libros' table structure...");
                jdbcTemplate.query(
                    "SELECT column_name, data_type FROM information_schema.columns WHERE table_name = 'libros'",
                    (rs, rowNum) -> {
                        log.info("Column: {} (Type: {})", 
                            rs.getString("column_name"), 
                            rs.getString("data_type"));
                        return null;
                    }
                );

            } catch (Exception e) {
                log.error("Error testing database connection", e);
                throw e;
            }
        };
    }
}
