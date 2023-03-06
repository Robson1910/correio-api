package io.github.robson.correios_api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_correios")
public class Correio {
    private String estado;
    private String cidade;
    private String bairro;
    @Id
    private String cep;
    private String endereco;
}
