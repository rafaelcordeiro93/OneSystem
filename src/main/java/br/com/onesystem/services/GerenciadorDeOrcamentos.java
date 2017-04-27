/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.services;

import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.EstadoDeOrcamento;
import javax.inject.Inject;

/**
 *
 * @author rauber
 */
public class GerenciadorDeOrcamentos {

    private final BundleUtil msg = new BundleUtil();

    public void redefinir(Orcamento orcamento) throws DadoInvalidoException {
        if (orcamento.getEstado() == EstadoDeOrcamento.EM_APROVACAO
                || orcamento.getEstado() == EstadoDeOrcamento.REPROVADO) {
            orcamento.setEstado(EstadoDeOrcamento.EM_DEFINICAO);
        } else {
            throw new EDadoInvalidoException(msg.getMessage("Orcamento_Em_Aprovacao_E_Reprovado_Em_Definicao"));
        }
    }

    public void enviarParaAprovacao(Orcamento orcamento) throws DadoInvalidoException {
        if (orcamento.getEstado() == EstadoDeOrcamento.EM_DEFINICAO) {
            orcamento.setEstado(EstadoDeOrcamento.EM_APROVACAO);
        } else {
            throw new EDadoInvalidoException(msg.getMessage("Orcamento_Em_Definicao_Envia_Aprovacao"));
        }
    }

    public void aprova(Orcamento orcamento) throws DadoInvalidoException {
        if (orcamento.getEstado() == EstadoDeOrcamento.EM_APROVACAO) {
            orcamento.setEstado(EstadoDeOrcamento.APROVADO);
        } else {
            throw new EDadoInvalidoException(msg.getMessage("Orcamento_Em_Aprovacao_Envia_Aprovado"));
        }
    }

    public void reprova(Orcamento orcamento) throws DadoInvalidoException {
        if (orcamento.getEstado() == EstadoDeOrcamento.EM_APROVACAO) {
            orcamento.setEstado(EstadoDeOrcamento.REPROVADO);
        } else {
            throw new EDadoInvalidoException(msg.getMessage("Orcamento_Em_Aprovacao_Envia_Aprovado"));
        }
    }

    public void cancela(Orcamento orcamento) throws DadoInvalidoException {
        if (orcamento.getEstado() != EstadoDeOrcamento.CANCELADO) {
            orcamento.setEstado(EstadoDeOrcamento.CANCELADO);
        } else {
            throw new EDadoInvalidoException(msg.getMessage("Orcamento_Ja_Esta_Cancelado"));
        }
    }

    public void efetiva(Orcamento orcamento) throws DadoInvalidoException {
        if (orcamento.getEstado() == EstadoDeOrcamento.APROVADO) {
            orcamento.setEstado(EstadoDeOrcamento.EFETIVADO);
        } else {
            throw new EDadoInvalidoException(msg.getMessage("Somente_Orcamento_Aprovado_Efetiva"));
        }
    }

}
