package io.github.robson.correios_api.controller;

import io.github.robson.correios_api.entities.Correio;
import io.github.robson.correios_api.service.CorreioService;
import io.github.robson.correios_api.service.exception.NoContentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CorreioController {
    @Autowired
    private CorreioService service;

    @PostMapping("/insert")
    public ResponseEntity<String> getInsert(@RequestBody String link) throws Exception {
        return service.Insert(link);
    }

    @GetMapping("/busca/{cep}")
    public Correio getCorreioByCep(@PathVariable("cep") String cep) throws NoContentException {
        return service.getCorreioByCep(cep);
    }
}
