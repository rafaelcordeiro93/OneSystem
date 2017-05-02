/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Rafael
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_NOTAEMITIDA",
        sequenceName = "SEQ_NOTAEMITIDA")
public class NotaEmitida extends Nota implements Serializable {

    @OneToOne(cascade = {CascadeType.ALL})
    private Orcamento orcamento;

    @OneToMany(mappedBy = "notaEmitida", cascade = {CascadeType.ALL})
    private List<ItemEmitido> itensEmitidos;

    public NotaEmitida() {
    }

    public NotaEmitida(Long id, Pessoa pessoa, Operacao operacao, List<ItemEmitido> itensEmitidos,
            FormaDeRecebimento formaDeRecebimento, ListaDePreco listaDePreco,
            ValoresAVista valoresAVista, List<Baixa> baixaDinheiro, Date emissao, boolean cancelada,
            Credito credito, List<Parcela> parcelas, Moeda moedaPadrao, Orcamento orcamento) throws DadoInvalidoException {
        super(id, pessoa, operacao, formaDeRecebimento, listaDePreco, valoresAVista, baixaDinheiro, emissao, cancelada, credito, parcelas, moedaPadrao);
        this.itensEmitidos = itensEmitidos;
        this.orcamento = orcamento;
    }

    public List<ItemEmitido> getItensEmitidos() {
        return itensEmitidos;
    }

    @Override
    public BigDecimal getTotalItens() {
        return itensEmitidos.stream().map(ItemEmitido::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    @Override
    public String toString() {
        return "NotaEmitida{" + "id=" + getId() + ", pessoa=" + (getPessoa() != null ? getPessoa().getId() : null)
                + ", operacao=" + (getOperacao() != null ? getOperacao().getId() : null)
                + ", emissao=" + getEmissao()
                + ", cancelada=" + isCancelada()
                + ", credito=" + getCredito()
                + ", itensEmitidos=" + getItensEmitidos()
                + ", formaDeRecebimento=" + (getFormaDeRecebimento() != null ? getFormaDeRecebimento().getId() : null)
                + ", listaDePreco=" + getListaDePreco() + ", valoresAVista=" + getValoresAVista()
                + ", baixaDinheiro=" + getBaixaDinheiro()
                + ", parcelas=" + getParcelas()
                + ",orcamento=" + getOrcamento() + '}';
    }

}
