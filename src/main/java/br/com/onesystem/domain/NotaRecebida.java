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
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Rafael
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_NOTARECEBIDA",
        sequenceName = "SEQ_NOTARECEBIDA")
public class NotaRecebida extends Nota implements Serializable {

    @OneToMany(mappedBy = "notaRecebida", cascade = {CascadeType.ALL})
    private List<ItemRecebido> itensRecebidos;

    public NotaRecebida() {
    }

    public NotaRecebida(Long id, Pessoa pessoa, Operacao operacao, List<ItemRecebido> itensRecebidos,
            FormaDeRecebimento formaDeRecebimento, ListaDePreco listaDePreco,
            ValoresAVista valoresAVista, List<Baixa> baixaDinheiro, Date emissao, boolean cancelada,
            Credito credito, List<Parcela> parcelas, Moeda moedaPadrao) throws DadoInvalidoException {
        super(id, pessoa, operacao, formaDeRecebimento, listaDePreco, valoresAVista, baixaDinheiro, emissao, cancelada, credito, parcelas, moedaPadrao);
        this.itensRecebidos = itensRecebidos;
    }

    public List<ItemRecebido> getItensRecebidos() {
        return itensRecebidos;
    }

    @Override
    public BigDecimal getTotalItens() {
        return itensRecebidos.stream().map(ItemRecebido::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String toString() {
        return "NotaEmitida{" + "id=" + getId() + ", pessoa=" + (getPessoa() != null ? getPessoa().getId() : null)
                + ", operacao=" + (getOperacao() != null ? getOperacao().getId() : null)
                + ", emissao=" + getEmissao()
                + ", cancelada=" + isCancelada()
                + ", credito=" + getCredito()
                + ", itensRecebidos=" + getItensRecebidos()
                + ", formaDeRecebimento=" + (getFormaDeRecebimento() != null ? getFormaDeRecebimento().getId() : null)
                + ", listaDePreco=" + getListaDePreco() + ", valoresAVista=" + getValoresAVista()
                + ", baixaDinheiro=" + getBaixaDinheiro()
                + ", parcelas=" + getParcelas() + '}';
    }

}
