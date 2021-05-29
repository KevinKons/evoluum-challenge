package com.kevinkons.location.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    private String idEstado;
    private String siglaEstado;
    private String regiaoNome;
    @JsonAlias("nome")
    private String nomeCidade;
    private String nomeMesorregiao;
    private String nomeFormatado;
    @JsonAlias("id")
    private String idCidade;

    @SuppressWarnings("unchecked")
    @JsonProperty("microrregiao")
    private void unpackNested(Map<String,Object> microrregiao) {
        Map<String, Object> mesorregiao = (Map<String, Object>) microrregiao.get("mesorregiao");
        this.nomeMesorregiao = mesorregiao.get("nome").toString();

        Map<String, Object> uf = (Map<String, Object>) mesorregiao.get("UF");
        this.siglaEstado = uf.get("sigla").toString();
        this.idEstado = uf.get("id").toString();

        Map<String, String> regiao = (Map<String, String>) uf.get("regiao");
        this.regiaoNome = regiao.get("nome");

        this.nomeFormatado = nomeCidade + "/" + siglaEstado;
    }

    public String toString() {
        return idEstado + "," + siglaEstado + "," + regiaoNome + "," + nomeCidade + "," + nomeMesorregiao + "," +
                nomeFormatado;
    }
}
