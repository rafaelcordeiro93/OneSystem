/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.services;

import br.com.onesystem.domain.HistoricoDeOrcamento;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.EstadoDeOrcamento;
import br.com.onesystem.war.util.UsuarioLogadoUtil;
import javax.inject.Inject;

/**
 *
 * @author rauber
 */
public class GerenciadorDeOrcamentos {

    private final BundleUtil msg = new BundleUtil();

    public void redefinir(Orcamento orcamento, String historico) throws DadoInvalidoException {
        if (orcamento.getEstado() == EstadoDeOrcamento.EM_APROVACAO
                || orcamento.getEstado() == EstadoDeOrcamento.REPROVADO) {
            orcamento.adiciona(new HistoricoDeOrcamento(null, EstadoDeOrcamento.EM_DEFINICAO, historico, new UsuarioLogadoUtil().getUsuario(), orcamento));
        } else {
            throw new EDadoInvalidoException(msg.getMessage("Orcamento_Em_Aprovacao_E_Reprovado_Em_Definicao"));
        }
    }

    public void enviarParaAprovacao(Orcamento orcamento, String historico) throws DadoInvalidoException {
        if (orcamento.getEstado() == EstadoDeOrcamento.EM_DEFINICAO) {
            orcamento.adiciona(new HistoricoDeOrcamento(null, EstadoDeOrcamento.EM_APROVACAO, historico, new UsuarioLogadoUtil().getUsuario(), orcamento));
        } else {
            throw new EDadoInvalidoException(msg.getMessage("Orcamento_Em_Definicao_Envia_Aprovacao"));
        }
    }

    public void aprova(Orcamento orcamento, String historico) throws DadoInvalidoException {
        if (orcamento.getEstado() == EstadoDeOrcamento.EM_APROVACAO) {
            orcamento.adiciona(new HistoricoDeOrcamento(null, EstadoDeOrcamento.APROVADO, historico, new UsuarioLogadoUtil().getUsuario(), orcamento));
        } else {
            throw new EDadoInvalidoException(msg.getMessage("Orcamento_Em_Aprovacao_Envia_Aprovado"));
        }
    }

    public void reprova(Orcamento orcamento, String historico) throws DadoInvalidoException {
        if (orcamento.getEstado() == EstadoDeOrcamento.EM_APROVACAO) {
            orcamento.adiciona(new HistoricoDeOrcamento(null, EstadoDeOrcamento.REPROVADO, historico, new UsuarioLogadoUtil().getUsuario(), orcamento));
        } else {
            throw new EDadoInvalidoException(msg.getMessage("Orcamento_Em_Aprovacao_Envia_Aprovado"));
        }
    }

    public void cancela(Orcamento orcamento, String historico) throws DadoInvalidoException {
        if (orcamento.getEstado() != EstadoDeOrcamento.CANCELADO) {
            orcamento.adiciona(new HistoricoDeOrcamento(null, EstadoDeOrcamento.CANCELADO, historico, new UsuarioLogadoUtil().getUsuario(), orcamento));
        } else {
            throw new EDadoInvalidoException(msg.getMessage("Orcamento_Ja_Esta_Cancelado"));
        }
    }

    public void efetiva(Orcamento orcamento, String historico) throws DadoInvalidoException {
        if (orcamento.getEstado() == EstadoDeOrcamento.APROVADO) {
            orcamento.adiciona(new HistoricoDeOrcamento(null, EstadoDeOrcamento.EFETIVADO, historico, new UsuarioLogadoUtil().getUsuario(), orcamento));
        } else {
            throw new EDadoInvalidoException(msg.getMessage("Somente_Orcamento_Aprovado_Efetiva"));
        }
    }

}
