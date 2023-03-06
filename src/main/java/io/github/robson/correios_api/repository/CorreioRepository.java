package io.github.robson.correios_api.repository;

import io.github.robson.correios_api.entities.Correio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorreioRepository extends JpaRepository<Correio,String> {
}
