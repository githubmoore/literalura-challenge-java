package com.literalura.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.model.Libro;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
public class GutendexClient {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    
    public GutendexClient(ObjectMapper objectMapper) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = objectMapper;
        this.baseUrl = "https://gutendex.com/books/";
    }
    
    public Optional<Libro> buscarLibroPorTitulo(String titulo) {
        try {
            String url = baseUrl + "?search=" + titulo.replace(" ", "+");
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
                
            HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
                
            if (response.statusCode() == 200) {
                var jsonNode = objectMapper.readTree(response.body());
                var results = jsonNode.get("results");
                
                if (results.size() > 0) {
                    return Optional.of(objectMapper.treeToValue(results.get(0), Libro.class));
                }
            }
            
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar libro: " + e.getMessage(), e);
        }
    }
}
