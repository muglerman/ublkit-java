package com.cna.ublkit.render.pdf;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.BorradorFactura;

/**
 * Especialización de {@link RenderizadorPdfFactura} para formatos de ticket térmico.
 * <p>
 * Facilita la generación de representaciones PDF optimizadas para impresoras térmicas
 * de 58mm o 80mm.
 * </p>
 *
 * @since 0.1.0
 */
public class RenderizadorTicketFactura implements RenderizadorDocumento<BorradorFactura> {

    private final RenderizadorPdfFactura delegate;

    /**
     * Construye un renderizador de ticket usando el formato estándar de 80mm.
     */
    public RenderizadorTicketFactura() {
        this(FormatoImpresion.TICKET_80MM);
    }

    /**
     * Construye un renderizador de ticket con el formato especificado.
     * 
     * @param formato debe ser {@link FormatoImpresion#TICKET_80MM} o {@link FormatoImpresion#TICKET_58MM}
     */
    public RenderizadorTicketFactura(FormatoImpresion formato) {
        if (formato != FormatoImpresion.TICKET_80MM && formato != FormatoImpresion.TICKET_58MM) {
             throw new IllegalArgumentException("El formato debe ser TICKET_80MM o TICKET_58MM");
        }
        this.delegate = new RenderizadorPdfFactura(formato);
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<BorradorFactura> contexto) {
        return delegate.renderizar(contexto);
    }
}
