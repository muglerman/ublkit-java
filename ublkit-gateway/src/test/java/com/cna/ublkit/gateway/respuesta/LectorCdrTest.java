package com.cna.ublkit.gateway.respuesta;

import com.cna.ublkit.gateway.api.ZipHelper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LectorCdrTest {

    @Test
    void testExtraerCdrAceptado() {
        String xmlCdr = """
                <?xml version="1.0" encoding="ISO-8859-1"?>
                <ar:ApplicationResponse xmlns:ar="urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2" 
                                        xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" 
                                        xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2">
                    <cac:DocumentResponse>
                        <cac:Response>
                            <cbc:ResponseCode>0</cbc:ResponseCode>
                            <cbc:Description>El Comprobante ha sido aceptado</cbc:Description>
                        </cac:Response>
                    </cac:DocumentResponse>
                </ar:ApplicationResponse>
                """;
        
        byte[] zipBytes = ZipHelper.comprimir(xmlCdr, "R-20000000000-01-F001-1.xml");
        
        ArchivoCdr cdr = LectorCdr.extraer(zipBytes);
        
        assertThat(cdr).isNotNull();
        assertThat(cdr.codigoRegreso()).isEqualTo("0");
        assertThat(cdr.descripcion()).isEqualTo("El Comprobante ha sido aceptado");
        assertThat(cdr.notas()).isEmpty();
        
        assertThat(LectorCdr.determinarEstado(cdr)).isEqualTo(EstadoEnvio.ACEPTADO);
    }
    
    @Test
    void testExtraerCdrRechazado() {
        String xmlCdr = """
                <?xml version="1.0" encoding="ISO-8859-1"?>
                <ar:ApplicationResponse xmlns:ar="urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2" 
                                        xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" 
                                        xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2">
                    <cac:DocumentResponse>
                        <cac:Response>
                            <cbc:ResponseCode>1033</cbc:ResponseCode>
                            <cbc:Description>El Comprobante fue rechazado por reglas SUNAT</cbc:Description>
                        </cac:Response>
                    </cac:DocumentResponse>
                </ar:ApplicationResponse>
                """;
        
        byte[] zipBytes = ZipHelper.comprimir(xmlCdr, "R-20000000000-01-F001-2.xml");
        
        ArchivoCdr cdr = LectorCdr.extraer(zipBytes);
        
        assertThat(cdr).isNotNull();
        assertThat(cdr.codigoRegreso()).isEqualTo("1033");
        
        assertThat(LectorCdr.determinarEstado(cdr)).isEqualTo(EstadoEnvio.RECHAZADO);
    }

    @Test
    void testExtraerCdrAceptadoConObservaciones() {
        String xmlCdr = """
                <?xml version="1.0" encoding="ISO-8859-1"?>
                <ar:ApplicationResponse xmlns:ar="urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2" 
                                        xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" 
                                        xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2">
                    <cac:DocumentResponse>
                        <cac:Response>
                            <cbc:ResponseCode>0</cbc:ResponseCode>
                            <cbc:Description>El Comprobante ha sido aceptado</cbc:Description>
                        </cac:Response>
                    </cac:DocumentResponse>
                    <cbc:Note>4001 - Faltan datos secundarios</cbc:Note>
                    <cbc:Note>4002 - Otra observacion</cbc:Note>
                </ar:ApplicationResponse>
                """;
        
        byte[] zipBytes = ZipHelper.comprimir(xmlCdr, "R-20000000000-01-F001-3.xml");
        
        ArchivoCdr cdr = LectorCdr.extraer(zipBytes);
        
        assertThat(cdr.codigoRegreso()).isEqualTo("0");
        assertThat(cdr.notas()).hasSize(2).contains("4001 - Faltan datos secundarios");
        
        assertThat(LectorCdr.determinarEstado(cdr)).isEqualTo(EstadoEnvio.ACEPTADO_CON_OBSERVACIONES);
    }
}
