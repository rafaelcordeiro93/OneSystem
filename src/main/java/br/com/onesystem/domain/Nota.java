/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.MoedaFomatter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author rauber
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "NOTA_CLASSE_NOME")
public abstract class Nota implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_NOTA", sequenceName = "SEQ_NOTA",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SEQ_NOTA", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{pessoa_not_null}")
    @ManyToOne
    private Pessoa pessoa;
    @NotNull(message = "{operacao_not_null}")
    @ManyToOne
    private Operacao operacao;
    @NotNull(message = "{forma_recebimento_not_null}")
    @ManyToOne
    private FormaDeRecebimento formaDeRecebimento;
    @ManyToOne
    private ListaDePreco listaDePreco;
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;
    private boolean cancelada;
    @OneToOne(mappedBy = "nota", cascade = {CascadeType.ALL})
    private ValoresAVista valoresAVista;
    @OneToMany(mappedBy = "nota", cascade = {CascadeType.ALL})
    private List<Baixa> baixaDinheiro;
    @OneToOne(mappedBy = "nota", cascade = {CascadeType.ALL})
    private Credito credito;
    @OneToMany(mappedBy = "nota", cascade = {CascadeType.ALL})
    private List<Parcela> parcelas;
    @NotNull(message = "{moeda_padrao_not_null}")
    @ManyToOne(optional = false)
    private Moeda moedaPadrao;

    public Nota() {
    }

    public Nota(Long id, Pessoa pessoa, Operacao operacao,
            FormaDeRecebimento formaDeRecebimento, ListaDePreco listaDePreco,
            ValoresAVista valoresAVista, List<Baixa> baixaDinheiro, Date emissao, boolean cancelada,
            Credito credito, List<Parcela> parcelas, Moeda moedaPadrao) throws DadoInvalidoException {
        this.id = id;
        this.pessoa = pessoa;
        this.operacao = operacao;
        this.formaDeRecebimento = formaDeRecebimento;
        this.listaDePreco = listaDePreco;
        this.valoresAVista = valoresAVista;
        this.baixaDinheiro = baixaDinheiro;
        this.emissao = emissao;
        this.cancelada = cancelada;
        this.credito = credito;
        this.parcelas = parcelas;
        this.moedaPadrao = moedaPadrao;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("pessoa", "operacao", "formaDeRecebimento", "moedaPadrao");
        new ValidadorDeCampos<>().valida(this, campos);
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

    public ListaDePreco getListaDePreco() {
        return listaDePreco;
    }

    public ValoresAVista getValoresAVista() {
        return valoresAVista;
    }

    public List<Baixa> getBaixaDinheiro() {
        return baixaDinheiro;
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

    public Moeda getMoedaPadrao() {
        return moedaPadrao;
    }

    public List<Parcela> getParcelas() {
        parcelas.sort(Comparator.comparingLong(Parcela::getDias));
        return parcelas;
    }

    public String getTotalItensFormatado() {
        return MoedaFomatter.format(moedaPadrao, getTotalItens());
    }

    public String getTotalParcelasFormatado() {
        return MoedaFomatter.format(moedaPadrao, getTotalParcelas());
    }

    public BigDecimal getTotalParcelas() {
        return parcelas.stream().map(Parcela::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

    public void setBaixaDinheiro(List<Baixa> baixaDinheiro) {
        this.baixaDinheiro = baixaDinheiro;
    }

    public abstract BigDecimal getTotalItens();

    public String getTotalNotaFormatado() {
        return MoedaFomatter.format(moedaPadrao, getTotalNota());
    }

    public BigDecimal getTotalNota() {
        return getTotalItens().add(valoresAVista.getAcrescimo().add(valoresAVista.getFrete().add(valoresAVista.getDespesaCobranca()))).subtract(valoresAVista.getDesconto());
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Nota)) {
            return false;
        }
        Nota outro = (Nota) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
