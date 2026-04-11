Here's a better plan breakdown:

1. **Ventas a Crédito y Cuotas**: In `EnsambladorFactura`, add logic to auto-calculate `FormaDePago.total()` by summing the `cuotas` if it is null and `tipo == "Credito"`.
2. **Detracciones**:
    - Create a map of Detraction percentages by `tipoBienDetraido` (Catalogo 54). For example, "027" -> 10%, "037" -> 12%.
    - Auto-calculate `porcentaje` and `monto` if missing in `Detraccion`.
    - In `EmisorDocumento` add `String cuentaDetraccion()`, and use it to auto-fill `cuentaBancaria` in `Detraccion`.
3. **Anticipos Acumulados**: Modify `SerializadorXmlFactura.java` to use `anticipo.comprobanteSerieNumero()` in `PrepaidPayment/ID` instead of `String.valueOf(idx)`.
4. **Guías de Remisión**: Create `EnsambladorGuia` (similar to `EnsambladorFactura`) that has a `ensamblar(BorradorGuiaRemision)` method.
    - If Importación ("08"), Exportación ("09"), auto-configure some things.
    - If Traslado entre establecimientos ("04"), ensure `codigoLocal` is set in `Partida` and `Destino` or put defaults like "0000".
    - Maybe in `BorradorGuiaRemision` we call this assembler before serialization.
5. **Facturas de Exportación**: In `EnsambladorFactura`, if `tipoOperacion == "0200"`, ensure all lines have `igvTipo = "40"`, IGV=0, and `moneda = "USD"` if not specified.
