/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.domain.builder.BaixaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_CambioEmpresa",
        sequenceName = "SEQ_CambioEmpresa")
public class CambioEmpresa implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_CambioEmpresa", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;

    @NotNull(message = "{origem_not_null}")
    @ManyToOne(optional = false)
    private Conta origem;

    @NotNull(message = "{destino_not_null}")
    @ManyToOne(optional = false)
    private Conta destino;

    @NotNull(message = "{valor_not_null}")
    @Min(value = 0, message = "{valor_min}")
    private BigDecimal valor;

    @NotNull(message = "{valorConvertido_not_null}")
    @Min(value = 0, message = "{valorConvertido_min}")
    private BigDecimal valorConvertido;

    @OneToMany(mappedBy = "cambioEmpresa", cascade = {CascadeType.ALL})
    private List<Baixa> baixas;

    @Length(message = "{observacao_length}", max = 255)
    private String observacao;

    public CambioEmpresa() {
    }

    public CambioEmpresa(Long id, Date emissao, Conta origem, Conta destino, BigDecimal valor, BigDecimal valorConvertido, List<Baixa> baixas, String observacao) throws DadoInvalidoException {
        this.id = id;
        this.emissao = emissao;
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
        this.valorConvertido = valorConvertido;
        this.baixas = baixas;
        this.observacao = observacao;
        ehValido();
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("origem", "destino", "valor", "valorConvertido", "observacao");
        new ValidadorDeCampos<CambioEmpresa>().valida(this, campos);
    }

    /* Deve ser utilizado para gerar a baixa do saque */
    public void geraBaixaDeSaque(Cotacao origem, Cotacao destino) throws DadoInvalidoException {
        adiciona(new BaixaBuilder().comValor(valor).comOperacaoFinanceira(OperacaoFinanceira.SAIDA).comCotacao(origem).construir());
        adiciona(new BaixaBuilder().comValor(valorConvertido).comOperacaoFinanceira(OperacaoFinanceira.ENTRADA).comCotacao(destino).construir());
    }

    /* Adiciona Baixa*/
    public void adiciona(Baixa baixa) {
        try {
            BaixaBuilder b = new BaixaBuilder(baixa);
            if (baixas == null) {
                this.baixas = new ArrayList<>();
            }
            b.comCambioEmpresa(this);
            geraHistorico(baixa, b);
            b.comEmissao(emissao);
            this.baixas.add(b.construir());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void geraHistorico(Baixa baixa, BaixaBuilder b) {
        BundleUtil msg = new BundleUtil();
        if (baixa.getDespesa() == null) {
            b.comHistorico(msg.getLabel("Cambio_De_Moeda") + " " + msg.getLabel("de") + " (" + origem.getId() + " - " + origem.getNome() + ") "
                    + msg.getLabel("para") + " (" + destino.getId() + " - " + destino.getNome() + ")");
        } else {
            b.comHistorico(baixa.getDespesa().getNome() + " " + msg.getLabel("de") + " " + msg.getLabel("Cambio_De_Moeda"));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Conta getOrigem() {
        return origem;
    }

    public void setOrigem(Conta origem) {
        this.origem = origem;
    }

    public Conta getDestino() {
        return destino;
    }

    public void setDestino(Conta destino) {
        this.destino = destino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorConvertido() {
        return valorConvertido;
    }

    public void setValorConvertido(BigDecimal valorConvertido) {
        this.valorConvertido = valorConvertido;
    }

    public List<Baixa> getBaixas() {
        return baixas;
    }

    public void setBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof CambioEmpresa)) {
            return false;
        }
        CambioEmpresa outro = (CambioEmpresa) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
