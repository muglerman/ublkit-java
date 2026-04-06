package com.cna.ublkit.ubl.modelo.guia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Vehiculo Record Tests")
class VehiculoTest {

    @Test
    @DisplayName("Should create Vehiculo with required fields")
    void testCrearVehiculo() {
        Vehiculo vehiculo = new Vehiculo(
            "ABC-1234",
            "TUC001",
            "AUTH001",
            "03",
            null
        );

        assertEquals("ABC-1234", vehiculo.placa());
        assertEquals("TUC001", vehiculo.numeroCirculacion());
        assertEquals("AUTH001", vehiculo.numeroAutorizacion());
        assertEquals("03", vehiculo.codigoEmisor());
        assertNull(vehiculo.secundarios());
    }

    @Test
    @DisplayName("Should support vehículos secundarios (carreta)")
    void testVehiculoConCarreta() {
        Vehiculo carreta = new Vehiculo("XYZ-5678", "TUC002", null, "03", null);
        Vehiculo principal = new Vehiculo(
            "ABC-1234",
            "TUC001",
            "AUTH001",
            "03",
            java.util.Arrays.asList(carreta)
        );

        assertEquals(1, principal.secundarios().size());
        assertEquals("XYZ-5678", principal.secundarios().get(0).placa());
    }

    @Test
    @DisplayName("Should maintain equality based on values")
    void testIgualdadPorValores() {
        Vehiculo veh1 = new Vehiculo("ABC-1234", "TUC001", "AUTH001", "03", null);
        Vehiculo veh2 = new Vehiculo("ABC-1234", "TUC001", "AUTH001", "03", null);

        assertEquals(veh1, veh2);
    }

    @Test
    @DisplayName("Should differentiate distinct vehicles")
    void testDesigualdadPorDiferentesValores() {
        Vehiculo veh1 = new Vehiculo("ABC-1234", "TUC001", "AUTH001", "03", null);
        Vehiculo veh2 = new Vehiculo("XYZ-5678", "TUC002", "AUTH002", "03", null);

        assertNotEquals(veh1, veh2);
    }

    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        Vehiculo vehiculo = new Vehiculo(
            "ABC-1234", "TUC001", "AUTH001", "03", null
        );

        String toString = vehiculo.toString();
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    @DisplayName("Should handle null optional fields")
    void testCamposNulos() {
        Vehiculo vehiculo = new Vehiculo("ABC-1234", null, null, null, null);

        assertEquals("ABC-1234", vehiculo.placa());
        assertNull(vehiculo.numeroCirculacion());
        assertNull(vehiculo.codigoEmisor());
    }
}
