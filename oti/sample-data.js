// Sample data replicating the server-side `invoice`, `receipt`, `summary`, `voided` objects.
// Used to render the HTML templates with realistic content.

window.SAMPLE = {
  invoice: {
    header: "",
    footer: "",
    logo: "data:image/svg+xml;utf8," + encodeURIComponent('<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 80 80"><rect width="80" height="80" rx="14" fill="currentColor" opacity="0.12"/><path d="M20 28h40M20 40h28M20 52h34" stroke="currentColor" stroke-width="4" stroke-linecap="round"/></svg>'),
    qr: "data:image/svg+xml;utf8," + encodeURIComponent('<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><rect width="100" height="100" fill="#fff"/><g fill="#000"><rect x="5" y="5" width="22" height="22"/><rect x="9" y="9" width="14" height="14" fill="#fff"/><rect x="12" y="12" width="8" height="8"/><rect x="73" y="5" width="22" height="22"/><rect x="77" y="9" width="14" height="14" fill="#fff"/><rect x="80" y="12" width="8" height="8"/><rect x="5" y="73" width="22" height="22"/><rect x="9" y="77" width="14" height="14" fill="#fff"/><rect x="12" y="80" width="8" height="8"/><rect x="32" y="8" width="4" height="4"/><rect x="40" y="8" width="4" height="4"/><rect x="48" y="8" width="4" height="8"/><rect x="56" y="12" width="4" height="4"/><rect x="64" y="8" width="4" height="4"/><rect x="32" y="16" width="8" height="4"/><rect x="44" y="16" width="4" height="4"/><rect x="52" y="16" width="8" height="4"/><rect x="64" y="16" width="4" height="4"/><rect x="8" y="32" width="4" height="8"/><rect x="16" y="32" width="4" height="4"/><rect x="24" y="32" width="8" height="4"/><rect x="36" y="32" width="4" height="8"/><rect x="44" y="32" width="8" height="4"/><rect x="56" y="32" width="4" height="4"/><rect x="64" y="32" width="4" height="8"/><rect x="72" y="32" width="4" height="4"/><rect x="80" y="32" width="4" height="4"/><rect x="88" y="32" width="4" height="8"/><rect x="12" y="40" width="8" height="4"/><rect x="24" y="40" width="4" height="4"/><rect x="32" y="40" width="4" height="8"/><rect x="44" y="40" width="4" height="4"/><rect x="52" y="40" width="8" height="4"/><rect x="64" y="40" width="4" height="4"/><rect x="76" y="40" width="4" height="8"/><rect x="84" y="40" width="4" height="4"/><rect x="8" y="48" width="4" height="4"/><rect x="20" y="48" width="8" height="4"/><rect x="36" y="48" width="4" height="4"/><rect x="48" y="48" width="4" height="8"/><rect x="60" y="48" width="8" height="4"/><rect x="72" y="48" width="4" height="4"/><rect x="84" y="48" width="8" height="4"/><rect x="12" y="56" width="4" height="8"/><rect x="24" y="56" width="8" height="4"/><rect x="36" y="56" width="4" height="4"/><rect x="44" y="56" width="4" height="8"/><rect x="56" y="56" width="4" height="4"/><rect x="68" y="56" width="8" height="4"/><rect x="80" y="56" width="4" height="4"/><rect x="88" y="56" width="4" height="8"/><rect x="32" y="64" width="4" height="8"/><rect x="40" y="64" width="8" height="4"/><rect x="52" y="64" width="4" height="8"/><rect x="64" y="64" width="4" height="4"/><rect x="76" y="64" width="8" height="4"/><rect x="88" y="64" width="4" height="4"/><rect x="32" y="72" width="4" height="4"/><rect x="44" y="72" width="8" height="4"/><rect x="56" y="72" width="4" height="4"/><rect x="64" y="76" width="4" height="8"/><rect x="76" y="72" width="4" height="8"/><rect x="88" y="72" width="4" height="8"/><rect x="32" y="80" width="8" height="4"/><rect x="44" y="80" width="4" height="4"/><rect x="52" y="80" width="4" height="8"/><rect x="60" y="80" width="8" height="4"/><rect x="72" y="80" width="4" height="4"/><rect x="84" y="80" width="4" height="4"/><rect x="32" y="88" width="4" height="4"/><rect x="40" y="88" width="8" height="4"/><rect x="56" y="88" width="8" height="4"/><rect x="72" y="88" width="4" height="4"/><rect x="80" y="88" width="8" height="4"/></g></svg>'),
    taxpayer: {
      name: "MANUFACTURAS ANDINA TEXTIL S.A.C.",
      tradeName: "ANDINA TEXTIL",
      identity: "20512345678",
      address: "Av. Argentina 2740 Int. 301",
      location: "LIMA - LIMA - CERCADO DE LIMA",
      contact: { email: "ventas@andinatextil.pe", telephone: "(01) 555 - 0102", web: "www.andinatextil.pe" }
    },
    customer: {
      name: "COMERCIAL LOS PORTALES S.A.",
      documentType: "RUC",
      identity: "20498123456",
      address: "Av. Jorge Chavez 455 - Miraflores",
      location: "LIMA - LIMA - MIRAFLORES"
    },
    name: "FACTURA ELECTRÓNICA",
    identity: "F001-0001287",
    issueDate: "24/04/2026",
    issueTime: "14:32:11",
    dueDate: "24/05/2026",
    currency: "SOLES",
    exchangeRate: { date: "24/04/2026", value: "3.742" },
    operationTypeCode: "0101",
    operationTypeName: "Venta interna",
    orderReference: "OC-2026-00487",
    deliveryAddress: "Almacén Los Portales - Av. Argentina 5520, Callao",
    payment: {
      type: "Crédito",
      total: "S/ 11,892.40",
      installments: [
        { dueDate: "24/05/2026", amount: "5,946.20" },
        { dueDate: "24/06/2026", amount: "5,946.20" }
      ]
    },
    items: [
      { index: 1, code: "TX-0014", sunatCode: "53102400", gs1Code: "7750112000142", description: "Polo algodón pima Premium - Talla M / Azul marino", igvType: "10 - Gravado Onerosa", unitCode: "NIU", quantity: "48.00", price: "S/ 65.00", taxTotal: "S/ 561.60" },
      { index: 2, code: "TX-0022", sunatCode: "53102400", description: "Polo algodón pima Premium - Talla L / Blanco", igvType: "10 - Gravado Onerosa", unitCode: "NIU", quantity: "36.00", price: "S/ 65.00", taxTotal: "S/ 421.20" },
      { index: 3, code: "TX-0031", description: "Camisa lino básica - Talla M / Beige arena", igvType: "10 - Gravado Onerosa", unitCode: "NIU", quantity: "24.00", price: "S/ 89.00", taxTotal: "S/ 384.48" },
      { index: 4, code: "TX-0045", description: "Pantalón chino casual - Talla 32 / Caqui", igvType: "10 - Gravado Onerosa", unitCode: "NIU", quantity: "18.00", price: "S/ 149.00", taxTotal: "S/ 482.76" },
      { index: 5, code: "SV-001", description: "Servicio de bordado personalizado - logo en pecho", igvType: "10 - Gravado Onerosa", unitCode: "ZZ", quantity: "126.00", price: "S/ 4.50", taxTotal: "S/ 102.06" }
    ],
    summary: {
      subtotal: "S/ 10,078.31",
      taxable: "S/ 10,078.31",
      exonerated: "S/ 0.00",
      unaffected: "S/ 0.00",
      free: "S/ 0.00",
      exportation: "S/ 0.00",
      igv: "S/ 1,814.10",
      isc: "S/ 0.00",
      ivap: "S/ 0.00",
      icb: "S/ 0.00",
      taxTotal: "S/ 1,814.10",
      total: "S/ 11,892.40",
      totalText: "SON: ONCE MIL OCHOCIENTOS NOVENTA Y DOS CON 40/100 SOLES",
      discount: "S/ 0.00"
    },
    taxRates: { igv: "18.00%", ivap: "4.00%", icb: "0.00" },
    reference: {
      guides: [{ type: "GRE", document: "T001-00452" }],
      documents: []
    },
    discounts: [],
    charges: [],
    advances: [],
    detraction: { amount: "S/ 1,427.09", serviceCode: "020 - Bienes gravados con IGV", method: "003 - Transferencia en cuenta", financialAccount: "00-032-019287" },
    perception: null,
    embeddedGuide: null,
    note: "Pedido fraccionado - entrega en 2 camiones.",
    legends: [
      { code: "1000", value: "SON ONCE MIL OCHOCIENTOS NOVENTA Y DOS CON 40/100 SOLES" },
      { code: "2006", value: "Operación sujeta a detracción" }
    ],
    extras: [{ name: "Vendedor", value: "Diego Paredes" }, { name: "Ruta", value: "Lima-Callao" }],
    hash: "3f8a21bc9d7e04a6c2f1b8e0d4a7c9f2e5b6d8a1",
    signer: { identity: "20512345678", name: "MANUFACTURAS ANDINA TEXTIL SAC" }
  },

  boleta: {
    name: "BOLETA DE VENTA ELECTRÓNICA",
    identity: "B001-0004912",
    customer: { name: "MARÍA ELENA CASTILLO RODRÍGUEZ", documentType: "DNI", identity: "45128734", address: "" },
    summary: { taxable: "S/ 120.34", igv: "S/ 21.66", total: "S/ 142.00", totalText: "SON: CIENTO CUARENTA Y DOS CON 00/100 SOLES" }
  },

  note: {
    name: "NOTA DE CRÉDITO ELECTRÓNICA",
    identity: "FC01-0000089",
    noteTypeCode: "01",
    noteTypeName: "Anulación de la operación",
    relatedDocument: { type: "FACTURA", document: "F001-0001287", code: "01", description: "Anulación por devolución de mercadería defectuosa" },
    sustento: "El cliente reportó que el lote TX-0022 presenta decoloración. Se procede con anulación total y emisión de nota de crédito."
  },

  debit: {
    name: "NOTA DE DÉBITO ELECTRÓNICA",
    identity: "FD01-0000017",
    noteTypeCode: "02",
    noteTypeName: "Aumento en el valor",
    relatedDocument: { type: "FACTURA", document: "F001-0001287", code: "02", description: "Intereses por mora - pago fuera de plazo" },
    sustento: "Cargo por intereses moratorios según cláusula quinta del contrato marco."
  },

  despatch: {
    header: "",
    footer: "",
    logo: "data:image/svg+xml;utf8," + encodeURIComponent('<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 80 80"><rect width="80" height="80" rx="14" fill="currentColor" opacity="0.12"/><path d="M20 28h40M20 40h28M20 52h34" stroke="currentColor" stroke-width="4" stroke-linecap="round"/></svg>'),
    qr: null,
    taxpayer: {
      name: "MANUFACTURAS ANDINA TEXTIL S.A.C.",
      identity: "20512345678",
      address: "Av. Argentina 2740 Int. 301, Lima",
      contact: { email: "logistica@andinatextil.pe", telephone: "(01) 555 - 0102" }
    },
    customer: { name: "COMERCIAL LOS PORTALES S.A.", documentType: "RUC", identity: "20498123456", address: "Av. Argentina 5520, Callao" },
    thirdParty: { name: "", documentType: "", identity: "" },
    buyer: { name: "", documentType: "", identity: "" },
    name: "GUÍA DE REMISIÓN - REMITENTE",
    identity: "T001-0000452",
    issueDate: "24/04/2026",
    issueTime: "09:15:00",
    startDate: "24/04/2026",
    handling: "01 - Venta",
    weight: "286.50",
    itemsWeight: "278.40",
    unitCode: "KGM",
    packages: "8",
    transportModeCode: "02",
    transportModeName: "Transporte privado",
    manifest: "",
    lightVehicle: false,
    weightReason: "",
    address: {
      origin: { ubigeo: "150101", line: "Av. Argentina 2740 Int. 301", code: "0001", ruc: "20512345678" },
      delivery: { ubigeo: "070101", line: "Av. Argentina 5520 - Callao", code: "0001", ruc: "20498123456" }
    },
    carrier: { name: "TRANSPORTES MANTARO E.I.R.L.", documentType: "RUC", identity: "20600456789", mtc: "LMA-123-4567" },
    vehicles: [{ label: "Principal", identity: "BCV-834", number: "CIR-2024-01928", authorization: "" }],
    drivers: [{ type: "Principal", surname: "QUISPE CARRASCO", name: "JUAN PABLO", documentType: "DNI", identity: "42198765", license: "Q42198765" }],
    containers: [],
    customsDeclarations: [],
    port: { code: "", description: "" },
    airport: { code: "", description: "" },
    indicators: [],
    items: [
      { code: "TX-0014", sunatCode: "53102400", description: "Polo algodón pima Premium - Talla M / Azul marino", unitCode: "NIU", quantity: "48.00" },
      { code: "TX-0022", description: "Polo algodón pima Premium - Talla L / Blanco", unitCode: "NIU", quantity: "36.00" },
      { code: "TX-0031", description: "Camisa lino básica - Talla M / Beige arena", unitCode: "NIU", quantity: "24.00" },
      { code: "TX-0045", description: "Pantalón chino casual - Talla 32 / Caqui", unitCode: "NIU", quantity: "18.00" }
    ],
    reference: { note: "FACTURA F001-0001287", relatedDocuments: [], additionalDocuments: [] },
    extras: [],
    note: "Entregar en horario de recepción: 8:00 am a 12:00 m. Coordinar previamente.",
    version: "2022",
    hash: "7c4b82e9a1f3d6580c9e2a4f7b1d8c5e3a9f6b20",
    signer: { identity: "20512345678", name: "MANUFACTURAS ANDINA TEXTIL SAC" }
  },

  perception: {
    name: "COMPROBANTE DE PERCEPCIÓN",
    identity: "P001-0000034",
    issueDate: "24/04/2026",
    taxpayer: { name: "DISTRIBUIDORA MAYORISTA ANDINA S.A.", identity: "20512345678", address: "Av. Industrial 1840, Lima" },
    supplier: { name: "COMERCIAL LOS PORTALES S.A.", documentType: "RUC", identity: "20498123456", address: "Av. Jorge Chavez 455 - Miraflores" },
    regime: "01 - Régimen de Percepción del IGV",
    rate: "2.00",
    currency: "SOLES",
    details: [
      { documentType: "FACTURA", documentNumber: "F001-0001287", issueDate: "24/04/2026", currency: "PEN", total: "11,892.40", perceived: "237.85", toCollect: "12,130.25" },
      { documentType: "FACTURA", documentNumber: "F001-0001312", issueDate: "18/04/2026", currency: "PEN", total: "8,450.00", perceived: "169.00", toCollect: "8,619.00" }
    ],
    totalPerceived: "S/ 406.85",
    totalCollected: "S/ 20,749.25",
    observation: "Aplicación de régimen de percepción del IGV según Resolución 037-2002/SUNAT.",
    hash: "b9e4a7c2f1d85306a0c9e8f4b2d7a5c3e6f1a098"
  },

  retention: {
    name: "COMPROBANTE DE RETENCIÓN",
    identity: "R001-0000021",
    issueDate: "24/04/2026",
    taxpayer: { name: "DISTRIBUIDORA MAYORISTA ANDINA S.A.", identity: "20512345678", address: "Av. Industrial 1840, Lima" },
    supplier: { name: "IMPORTACIONES TUPAC S.R.L.", documentType: "RUC", identity: "20512987654", address: "Calle Los Negocios 387 - Surquillo" },
    regime: "01 - Tasa de 3%",
    rate: "3.00",
    currency: "SOLES",
    details: [
      { documentType: "FACTURA", documentNumber: "F002-0002874", issueDate: "20/04/2026", currency: "PEN", total: "18,500.00", retained: "555.00", toPay: "17,945.00" },
      { documentType: "FACTURA", documentNumber: "F002-0002901", issueDate: "22/04/2026", currency: "PEN", total: "9,820.00", retained: "294.60", toPay: "9,525.40" }
    ],
    totalRetained: "S/ 849.60",
    totalPaid: "S/ 27,470.40",
    observation: "Retención aplicada por ser agente de retención designado.",
    hash: "a3f9c5e8b7d241960f2a6c1e9b4d8a7c5f3e2b10"
  },

  summary: {
    id: "RC-20260424-001",
    issueDate: "24/04/2026",
    referenceDate: "23/04/2026",
    currency: "PEN",
    issuer: { name: "MANUFACTURAS ANDINA TEXTIL S.A.C.", ruc: "20512345678", tradeName: "ANDINA TEXTIL" },
    signer: { ruc: "20512345678", name: "MANUFACTURAS ANDINA TEXTIL SAC" },
    lines: [
      { index: 1, documentType: "BOLETA", document: "B001-0004910", currency: "PEN", operationType: "01", customerName: "JORGE LUIS RAMÍREZ PALOMINO", customerDocType: "DNI", customerDoc: "45128901", total: "89.00", gravado: "75.42", exonerado: "0.00", inafecto: "0.00", gratuito: "0.00", igv: "13.58", isc: "0.00", icb: "0.00", ivap: "0.00", otros: "0.00", perceptionType: "", perceptionAmount: "", perceptionTotal: "", affectedType: "", affectedDocument: "" },
      { index: 2, documentType: "BOLETA", document: "B001-0004911", currency: "PEN", operationType: "01", customerName: "ROSA MARÍA FLORES CHAVEZ", customerDocType: "DNI", customerDoc: "41872365", total: "245.00", gravado: "207.63", exonerado: "0.00", inafecto: "0.00", gratuito: "0.00", igv: "37.37", isc: "0.00", icb: "0.00", ivap: "0.00", otros: "0.00", perceptionType: "", perceptionAmount: "", perceptionTotal: "", affectedType: "", affectedDocument: "" },
      { index: 3, documentType: "BOLETA", document: "B001-0004912", currency: "PEN", operationType: "01", customerName: "MARÍA ELENA CASTILLO RODRÍGUEZ", customerDocType: "DNI", customerDoc: "45128734", total: "142.00", gravado: "120.34", exonerado: "0.00", inafecto: "0.00", gratuito: "0.00", igv: "21.66", isc: "0.00", icb: "0.00", ivap: "0.00", otros: "0.00", perceptionType: "", perceptionAmount: "", perceptionTotal: "", affectedType: "", affectedDocument: "" },
      { index: 4, documentType: "BOLETA", document: "B001-0004913", currency: "PEN", operationType: "01", customerName: "CARLOS ALBERTO MENDOZA TORRES", customerDocType: "DNI", customerDoc: "08934521", total: "58.00", gravado: "49.15", exonerado: "0.00", inafecto: "0.00", gratuito: "0.00", igv: "8.85", isc: "0.00", icb: "0.00", ivap: "0.00", otros: "0.00", perceptionType: "", perceptionAmount: "", perceptionTotal: "", affectedType: "", affectedDocument: "" },
      { index: 5, documentType: "BOLETA", document: "B001-0004914", currency: "PEN", operationType: "01", customerName: "ANA LUCÍA VARGAS ESPINOZA", customerDocType: "DNI", customerDoc: "70125894", total: "398.00", gravado: "337.29", exonerado: "0.00", inafecto: "0.00", gratuito: "0.00", igv: "60.71", isc: "0.00", icb: "0.00", ivap: "0.00", otros: "0.00", perceptionType: "", perceptionAmount: "", perceptionTotal: "", affectedType: "", affectedDocument: "" }
    ],
    hash: "f8a2c4e1b7d96305c0a8e2f5b1d9a6c4e8f3b210"
  },

  voided: {
    id: "RA-20260424-001",
    issueDate: "24/04/2026",
    referenceDate: "23/04/2026",
    issuer: { name: "MANUFACTURAS ANDINA TEXTIL S.A.C.", ruc: "20512345678", tradeName: "ANDINA TEXTIL" },
    signer: { ruc: "20512345678", name: "MANUFACTURAS ANDINA TEXTIL SAC" },
    lines: [
      { index: 1, documentType: "01", serie: "F001", number: "0001280", reason: "Error en datos del cliente" },
      { index: 2, documentType: "01", serie: "F001", number: "0001281", reason: "Duplicidad en emisión" },
      { index: 3, documentType: "03", serie: "B001", number: "0004905", reason: "Anulación por solicitud del cliente" }
    ],
    hash: "d4a9b7c5f2e18306c0a4e7f9b2d5a8c1e3f6b9a2"
  }
};
