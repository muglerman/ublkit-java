package com.cna.ublkit.storage;

import com.cna.ublkit.core.modelo.ResultadoOperacion;
import java.io.InputStream;

/**
 * Interface que define el contrato para almacenar documentos UBL (XML, CDR, PDF, etc).
 *
 * @since 1.0.0
 */
public interface AlmacenDocumentos {

    /**
     * Guarda el contenido de un archivo en el almacén.
     *
     * @param rutaDestino    Ruta o llave destino (ej: "20123456789/2024/01/F001-1.xml").
     * @param contenido      Bytes del archivo.
     * @param contentType    Tipo de contenido (ej: "application/xml", "application/pdf").
     * @return Resultado de la operación con la URL o ruta final si es exitoso.
     */
    ResultadoOperacion<String> guardar(String rutaDestino, byte[] contenido, String contentType);

    /**
     * Guarda el contenido de un archivo desde un flujo en el almacén.
     *
     * @param rutaDestino    Ruta o llave destino.
     * @param contenido      Flujo de entrada (InputStream) del archivo.
     * @param contentType    Tipo de contenido.
     * @return Resultado de la operación con la URL o ruta final si es exitoso.
     */
    ResultadoOperacion<String> guardar(String rutaDestino, InputStream contenido, String contentType);

    /**
     * Descarga el contenido de un archivo previamente almacenado.
     *
     * @param rutaArchivo Ruta o llave del archivo.
     * @return Resultado de la operación con los bytes del archivo en caso de éxito.
     */
    ResultadoOperacion<byte[]> descargar(String rutaArchivo);

    /**
     * Elimina un archivo del almacén.
     *
     * @param rutaArchivo Ruta o llave del archivo.
     * @return Resultado con un valor booleano indicando si la eliminación fue exitosa.
     */
    ResultadoOperacion<Boolean> eliminar(String rutaArchivo);
}
