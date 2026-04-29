package com.cna.ublkit.render.pebble;

import io.pebbletemplates.pebble.PebbleEngine;

/**
 * Fabrica centralizada de motores Pebble con las extensiones propias del proyecto.
 *
 * @since 0.3.0
 */
public final class PebbleEngines {

    private static final PebbleEngine ENGINE = new PebbleEngine.Builder()
            .extension(new UblkitPebbleExtension())
            .build();

    private PebbleEngines() {
    }

    public static PebbleEngine crear() {
        return ENGINE;
    }
}
