package com.kevinkons.location.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationTest {

    private String SOURCE_JSON =
            "{\"nome\":\"Biguaçu\"," +
            "\"microrregiao\":{" +
                    "\"mesorregiao\":{" +
                        "\"nome\":\"Grande Florianópolis\"," +
                        "\"UF\":{" +
                            "\"id\":\"42\"," +
                            "\"sigla\":\"SC\"," +
                            "\"regiao\":{" +
                                "\"nome\":\"Sul\"" +
                            "}" +
                        "}" +
                    "}" +
                "}" +
            "}";

    @Test
    public void whenUsingAnnotations_thenOk() throws IOException {
        Location location = new ObjectMapper().readerFor(Location.class)
                .readValue(SOURCE_JSON);

        assertEquals(location.getIdEstado(), "42");
        assertEquals(location.getSiglaEstado(), "SC");
        assertEquals(location.getRegiaoNome(), "Sul");
        assertEquals(location.getNomeCidade(), "Biguaçu");
        assertEquals(location.getNomeMesorregiao(), "Grande Florianópolis");
        assertEquals(location.getNomeFormatado(), "Biguaçu/SC");
    }

}
