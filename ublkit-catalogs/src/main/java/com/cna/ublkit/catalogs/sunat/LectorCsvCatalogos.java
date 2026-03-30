package com.cna.ublkit.catalogs.sunat;

import com.cna.ublkit.catalogs.api.ProveedorCatalogos;
import com.cna.ublkit.catalogs.modelo.EntradaCatalogo;
import com.cna.ublkit.core.error.ExcepcionUblKit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementación de {@link ProveedorCatalogos} respaldada por archivos CSV en el classpath.
 * Mantiene un caché en memoria para no cargar el mismo archivo múltiples veces.
 * Extrae todas las columnas dinámicamente y expone los atributos vía la interfaz base.
 *
 * @author Edwin Luis Barboza Pinedo
 * @since 0.1.0
 */
public class LectorCsvCatalogos implements ProveedorCatalogos {

    private final Map<String, List<EntradaCatalogo>> cacheCatalogo = new ConcurrentHashMap<>();

    @Override
    public List<EntradaCatalogo> obtenerCatalogo(String idCatalogo) {
        return cacheCatalogo.computeIfAbsent(idCatalogo, this::cargarDesdeCsv);
    }

    @Override
    public Optional<EntradaCatalogo> buscar(String idCatalogo, String codigo) {
        return obtenerCatalogo(idCatalogo).stream()
                .filter(e -> e.getCodigo().equals(codigo))
                .findFirst();
    }

    private List<EntradaCatalogo> cargarDesdeCsv(String idCatalogo) {
        String resourcePath = "/sunat/catalogos/" + idCatalogo + ".csv";
        InputStream is = getClass().getResourceAsStream(resourcePath);
        if (is == null) {
            return Collections.emptyList();
        }

        List<EntradaCatalogo> entradas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String headerLine = reader.readLine();
            if (headerLine == null) return Collections.emptyList();

            String[] headers = parsearLineaCsv(headerLine);
            
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] values = parsearLineaCsv(line);
                
                Map<String, String> atributos = new HashMap<>();
                String codigo = "";
                String descripcion = "";
                
                for (int i = 0; i < headers.length && i < values.length; i++) {
                    String h = headers[i].trim();
                    String v = values[i].trim();
                    atributos.put(h, v);
                    if (h.equalsIgnoreCase("codigo")) codigo = v;
                    else if (h.equalsIgnoreCase("descripcion")) descripcion = v;
                }
                
                entradas.add(new EntradaDinamica(codigo, descripcion, Collections.unmodifiableMap(atributos)));
            }
        } catch (Exception e) {
            throw new ExcepcionUblKit("Error procesando catálogo CSV " + idCatalogo, e);
        }

        return Collections.unmodifiableList(entradas);
    }

    /**
     * Parser simple de CSV que soporta comillas dobles (para valores con comas internas).
     */
    private String[] parsearLineaCsv(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '\"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());
        return result.toArray(new String[0]);
    }

    /**
     * Registro interno que sostiene la data de cada fila del CSV.
     */
    private record EntradaDinamica(
            String codigo,
            String descripcion,
            Map<String, String> atributos
    ) implements EntradaCatalogo {

        @Override
        public String getCodigo() {
            return codigo;
        }

        @Override
        public String getDescripcion() {
            return descripcion;
        }

        @Override
        public Optional<String> getAtributoAdicional(String clave) {
            return Optional.ofNullable(atributos.get(clave));
        }

        @Override
        public Map<String, String> getTodosAtributos() {
            return atributos;
        }
    }
}
