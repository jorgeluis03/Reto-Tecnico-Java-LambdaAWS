package com.example.retotecnico.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SwapiDao {
    public HashMap obtenerPlaneta(String id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> responseJson = restTemplate.getForEntity(
                "https://swapi.py4e.com/api/planets/" + id, HashMap.class);
        HashMap<String, Object> translatedResponse = traducirAtributos(responseJson.getBody());
        return translatedResponse;
    }

    private HashMap<String, Object> traducirAtributos(HashMap<String, Object> response) {
        HashMap<String, Object> translatedResponse = new HashMap<>();
        Map<String, String> traducciones = new HashMap<>();
        traducciones.put("name", "nombre");
        traducciones.put("rotation_period", "periodo_rotacion");
        traducciones.put("orbital_period", "periodo_orbital");
        traducciones.put("diameter", "diametro");
        traducciones.put("climate", "clima");
        traducciones.put("gravity", "gravedad");
        traducciones.put("terrain", "terreno");
        traducciones.put("surface_water", "agua_superficie");
        traducciones.put("population", "poblacion");
        traducciones.put("residents", "residentes");
        traducciones.put("created", "creado");
        traducciones.put("edited", "editado");
        traducciones.put("films", "peliculas");

        for (Map.Entry<String, Object> entry : response.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (traducciones.containsKey(key)) {
                translatedResponse.put(traducciones.get(key), value);
            } else {
                translatedResponse.put(key, value);
            }
        }

        return translatedResponse;
    }
}
