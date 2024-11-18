package com.literalura.service;

import com.literalura.model.Autor;
import com.literalura.repository.AutorRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutorService {
    private final AutorRepository autorRepository;

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> buscarAutoresVivosPorAnio(int anio) {
        return autorRepository
            .findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(anio, anio);
    }

    public List<Autor> buscarAutoresPorNombre(String nombre) {
        return autorRepository.findByNombreContainingIgnoreCase(nombre);
    }
}
