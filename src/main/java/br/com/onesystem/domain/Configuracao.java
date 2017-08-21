package br.com.onesystem.domain;

import br.com.onesystem.valueobjects.TipoDeCalculoDeCusto;
import br.com.onesystem.valueobjects.TipoDeFormacaoDePreco;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1,
        sequenceName = "SEQ_CONFIGURACAO", name = "SEQ_CONFIGURACAO")
@NamedQueries({
    @NamedQuery(name = "Configuracao.busca", query = "select c from Configuracao c ")})
public class Configuracao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONFIGURACAO")
    private Long id;

    @OneToOne
    private TipoDespesa despesaDeComissao;

    @OneToOne
    private Moeda moedaPadrao;

    @Enumerated(EnumType.STRING)
    private TipoDeFormacaoDePreco tipoDeFormacaoDePreco;

    @Enumerated(EnumType.STRING)
    private TipoDeCalculoDeCusto tipoDeCalculoDeCusto;

    public Configuracao() {
    }

    public Configuracao(Long id, TipoDespesa despesaDeComissao, Moeda moedaPadrao,
            TipoDeFormacaoDePreco tipoDeFormacaoDePreco, TipoDeCalculoDeCusto tipoDeCalculoDeCusto) {
        this.id = id;
        this.despesaDeComissao = despesaDeComissao;
        this.moedaPadrao = moedaPadrao;
        this.tipoDeFormacaoDePreco = tipoDeFormacaoDePreco;
        this.tipoDeCalculoDeCusto = tipoDeCalculoDeCusto;
    }

    public Long getId() {
        return id;
    }

    public TipoDespesa getDespesaDeComissao() {
        return despesaDeComissao;
    }

    public Moeda getMoedaPadrao() {
        return moedaPadrao;
    }

    public TipoDeFormacaoDePreco getTipoDeFormacaoDePreco() {
        return tipoDeFormacaoDePreco;
    }

    public TipoDeCalculoDeCusto getTipoDeCalculoDeCusto() {
        return tipoDeCalculoDeCusto;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Configuracao)) {
            return false;
        }
        Configuracao outro = (Configuracao) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Configuracao{" + "id=" + id + ", despesaDeComissao=" + despesaDeComissao + ", moedaPadrao=" + moedaPadrao + ", tipoDeFormacaoDePreco=" + tipoDeFormacaoDePreco + ", tipoDeCalculoDeCusto=" + tipoDeCalculoDeCusto + '}';
    }
}
