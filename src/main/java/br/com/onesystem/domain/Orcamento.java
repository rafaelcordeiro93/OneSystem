/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.GerenciadorDeOrcamentos;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.EstadoDeOrcamento;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author rauber
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_ORCAMENTO",
        name = "SEQ_ORCAMENTO")
public class Orcamento implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_ORCAMENTO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{pessoa_not_null}")
    @ManyToOne
    private Pessoa pessoa;
    @NotNull(message = "{forma_recebimento_not_null}")
    @ManyToOne
    private FormaDeRecebimento formaDeRecebimento;
    @ManyToOne
    private ListaDePreco listaDePreco;
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;
    @NotNull(message = "{cotacao_not_null}")
    @ManyToOne
    private Cotacao cotacao;
    @OneToMany(mappedBy = "orcamento", cascade = {CascadeType.ALL})
    private List<ItemOrcado> itemOrcado;
    @Temporal(TemporalType.TIMESTAMP)
    private Date validade;
    @Length(max = 250, min = 0, message = "{observacao_lenght}")
    private String observacao;
    @Enumerated(EnumType.STRING)
    private EstadoDeOrcamento estado;

    public Orcamento() {
        this.estado = EstadoDeOrcamento.EM_DEFINICAO;
    }

    public Orcamento(Long id, Pessoa pessoa, FormaDeRecebimento formaDeRecebimento,
            ListaDePreco listaDePreco, Cotacao cotacao, List<ItemOrcado> itemOrcado, Date validade, String observacao) throws DadoInvalidoException {
        this.id = id;
        this.pessoa = pessoa;
        this.formaDeRecebimento = formaDeRecebimento;
        this.listaDePreco = listaDePreco;
        this.cotacao = cotacao;
        this.validade = validade;
        this.observacao = observacao;
        this.emissao = new Date();
        this.estado = EstadoDeOrcamento.EM_DEFINICAO;
        this.itemOrcado = itemOrcado;
        itemOrcado.forEach(i -> {i.setOrcamento(this);});
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("pessoa", "formaDeRecebimento", "observacao","cotacao");
        new ValidadorDeCampos<>().valida(this, campos);
    }

    public void redefinir() throws DadoInvalidoException {
        new GerenciadorDeOrcamentos().redefinir(this);
    }

    public void enviaParaAprovacao() throws DadoInvalidoException {
        new GerenciadorDeOrcamentos().enviarParaAprovacao(this);
    }

    public void aprova() throws DadoInvalidoException {
        new GerenciadorDeOrcamentos().aprova(this);
    }

    public void reprova() throws DadoInvalidoException {
        new GerenciadorDeOrcamentos().reprova(this);
    }

    public void cancela() throws DadoInvalidoException {
        new GerenciadorDeOrcamentos().cancela(this);
    }

    public void efetiva() throws DadoInvalidoException {
        new GerenciadorDeOrcamentos().efetiva(this);
    }

    public Long getId() {
        return id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public FormaDeRecebimento getFormaDeRecebimento() {
        return formaDeRecebimento;
    }

    public ListaDePreco getListaDePreco() {
        return listaDePreco;
    }

    public Date getEmissao() {
        return emissao;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public List<ItemOrcado> getItemOrcado() {
        return itemOrcado;
    }

    public Date getValidade() {
        return validade;
    }

    public String getObservacao() {
        return observacao;
    }

    public EstadoDeOrcamento getEstado() {
        return estado;
    }

    public void setEstado(EstadoDeOrcamento estado) {
        this.estado = estado;
    }

}
