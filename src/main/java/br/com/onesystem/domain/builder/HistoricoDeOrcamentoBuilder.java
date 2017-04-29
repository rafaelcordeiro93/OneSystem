/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.HistoricoDeOrcamento;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.EstadoDeOrcamento;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class HistoricoDeOrcamentoBuilder {

    private Long id;
    private Usuario usuario;
    private EstadoDeOrcamento estadoDeOrcamento;
    private String historico;
    private Orcamento orcamento;

    public HistoricoDeOrcamentoBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public HistoricoDeOrcamentoBuilder comUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public HistoricoDeOrcamentoBuilder comHistorico(String historico) {
        this.historico = historico;
        return this;
    }

    public HistoricoDeOrcamentoBuilder comEstadoDeOrcamento(EstadoDeOrcamento estadoDeOrcamento) {
        this.estadoDeOrcamento = estadoDeOrcamento;
        return this;
    }

    public HistoricoDeOrcamentoBuilder comOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
        return this;
    }

    public HistoricoDeOrcamento construir() throws DadoInvalidoException {
        return new HistoricoDeOrcamento(id, estadoDeOrcamento, historico, usuario, orcamento);
    }

}
