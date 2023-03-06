package io.github.robson.correios_api.arquivo;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Configuration
@Profile("test")
public class GerarArquivo implements CommandLineRunner {
    Logger logger =  Logger.getLogger("com.api.jar");
    @Value(value = "${GERAR_ARQUIVO}")
    private Boolean gerarArquivo;
    @Value(value = "${ARQUIVO_ENTRADA}")
    private String csvFile;
    @Value(value = "${ARQUIVO_SAIDA}")
    private String htmlFile;
    List<CorreioArquivo> correios = new ArrayList<CorreioArquivo>();

    @Override
    public void run(String... args) {
        if(gerarArquivo){
            executeReader();
            executeWriter();
        }
    }

    private void executeReader(){
        try{
            logger.info("Execute Reader.......");
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            String line = "";
            logger.info("Add Reader.......");
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("\"", "");
                String cvsSplitBy = ",";
                String[] dados = line.split(cvsSplitBy);
                CorreioArquivo correio = new CorreioArquivo(dados[0], dados[1], dados[2], dados[3], dados[4]);
                correios.add(correio);
            }
            br.close();
            logger.info("Finally Reader.......");
        }catch (IOException e){
            logger.info("Error Reader.......");
            e.printStackTrace();
        }
    }

    private void executeWriter(){
        try{
            logger.info("Execute Writer.......");
            Gson gson = new Gson();
            String json = gson.toJson(correios);
            BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFile));
            logger.info("Add Writer.......");
            writer.write(json);
            writer.close();
            logger.info("Finally Writer.......");
        }catch (IOException e){
            logger.info("Error Writer.......");
            e.printStackTrace();
        }
    }
}
