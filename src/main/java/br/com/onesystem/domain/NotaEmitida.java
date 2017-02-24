/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_NOTAEMITIDA",
        sequenceName = "SEQ_NOTAEMITIDA")
public class NotaEmitida implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_NOTAEMITIDA", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{pessoa_not_null}")
    @ManyToOne
    private Pessoa pessoa;
    @NotNull(message = "{operacao_not_null}")
    @ManyToOne
    private Operacao operacao;
    @OneToMany(mappedBy = "notaEmitida", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<ItemEmitido> itensEmitidos;
    @NotNull(message = "{forma_recebimento_not_null}")
    @ManyToOne
    private FormaDeRecebimento formaDeRecebimento;
    @ManyToOne
    private ListaDePreco listaDePreco;
    @OneToOne(mappedBy = "notaEmitida", cascade = {CascadeType.ALL})
    private ValoresAVista valoresAVista;
    @OneToMany(mappedBy = "notaEmitida", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Baixa> baixas;
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;
    private boolean cancelada;
    @OneToOne(mappedBy = "notaEmitida")
    private Credito credito;
    @OneToMany(mappedBy = "notaEmitida", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Cheque> cheques;
    @OneToMany(mappedBy = "notaEmitida", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<BoletoDeCartao> cartoes;
    @OneToMany(mappedBy = "notaEmitida", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Titulo> titulos;

    public NotaEmitida() {
    }

    public NotaEmitida(Long id, Pessoa pessoa, Operacao operacao, ListaDePreco listaDePreco, ValoresAVista valoresAVista,
            Date emissao, boolean cancelada, FormaDeRecebimento formaDeRecebimento) throws DadoInvalidoException {
        this.id = id;
        this.pessoa = pessoa;
        this.operacao = operacao;
        this.listaDePreco = listaDePreco;
        this.valoresAVista = valoresAVista;
        this.emissao = emissao;
        this.cancelada = cancelada;
        this.formaDeRecebimento = formaDeRecebimento;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("pessoa", "operacao", "formaDeRecebimento");
        new ValidadorDeCampos<NotaEmitida>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public FormaDeRecebimento getFormaDeRecebimento() {
        return formaDeRecebimento;
    }

    public List<ItemEmitido> getItensEmitidos() {
        return itensEmitidos;
    }

    public List<Titulo> getTitulos() {
        return titulos;
    }

    public ListaDePreco getListaDePreco() {
        return listaDePreco;
    }

    public ValoresAVista getValoresAVista() {
        return valoresAVista;
    }

    public List<Baixa> getBaixas() {
        return baixas;
    }

    public Date getEmissao() {
        return emissao;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public Credito getCredito() {
        return credito;
    }

    public List<Cheque> getCheques() {
        return cheques;
    }

    public List<BoletoDeCartao> getCartoes() {
        return cartoes;
    }

    public void setCheques(List<Cheque> cheques) {
        this.cheques = cheques;
    }

    public void setCartoes(List<BoletoDeCartao> cartoes) {
        this.cartoes = cartoes;
    }

    public void setTitulos(List<Titulo> titulos) {
        this.titulos = titulos;
    }

    public void setBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof NotaEmitida)) {
            return false;
        }
        NotaEmitida outro = (NotaEmitida) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "NotaEmitida{" + "id=" + id + ", pessoa=" + pessoa + ", operacao=" + operacao + ", itensEmitidos=" + itensEmitidos + ", titulos=" + titulos + ", listaDePreco=" + listaDePreco + '}';
    }

}
