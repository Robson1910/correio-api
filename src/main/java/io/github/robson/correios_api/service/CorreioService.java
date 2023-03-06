package io.github.robson.correios_api.service;

import io.github.robson.correios_api.entities.Correio;
import io.github.robson.correios_api.repository.CorreioRepository;
import io.github.robson.correios_api.service.exception.NoContentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class CorreioService {
        Logger logger =  Logger.getLogger("com.api.jar");
        @Autowired
        CorreioRepository repository;
       @Value(value = "${EXECUTE_TEST}")
        private Boolean test;

        public ResponseEntity<String> Insert(String link) throws Exception{
            logger.info("Execute Reader.......");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(link))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            Correio[] correios = objectMapper.readValue(json, Correio[].class);
            logger.info("Execute Save.......");
            // Verifica se esta no ambiente test utilizando o banco h2.
            if(test){
                for(int i = 0; i<10;i++ ){
                    repository.save(correios[i]);
                }
            } else{
                repository.saveAll(List.of(correios));
            }
            logger.info("Finally...........");
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/insert").buildAndExpand(link).toUri();
            return ResponseEntity.created(uri).body("Save Database!");
        }

        public Correio getCorreioByCep(String cep) throws NoContentException {
            return repository.findById(cep).orElseThrow(NoContentException::new);
        }
}
