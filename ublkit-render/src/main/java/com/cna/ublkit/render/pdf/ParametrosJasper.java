package com.cna.ublkit.render.pdf;

import com.cna.ublkit.render.modelo.ContextoRender;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

final class ParametrosJasper {
    private static final String PREFIJO_PROPIEDAD = "ublkit.render.jasper.param.";

    private ParametrosJasper() {
    }

    static Map<String, Object> construir(ContextoRender<?> contexto) {
        Map<String, Object> params = new HashMap<>();
        if (contexto != null && contexto.atributosPlantilla() != null) {
            params.putAll(contexto.atributosPlantilla());
        }
        Properties props = System.getProperties();
        for (String nombre : props.stringPropertyNames()) {
            if (!nombre.startsWith(PREFIJO_PROPIEDAD)) continue;
            String clave = nombre.substring(PREFIJO_PROPIEDAD.length());
            if (clave.isBlank()) continue;
            params.put(clave, props.getProperty(nombre));
        }
        return params;
    }
}
