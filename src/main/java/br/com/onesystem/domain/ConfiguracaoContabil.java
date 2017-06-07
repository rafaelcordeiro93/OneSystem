package br.com.onesystem.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, sequenceName = "SEQ_CONFIGURACAOCONTABIL",
        name = "SEQ_CONFIGURACAOCONTABIL")
@NamedQueries({
    @NamedQuery(name = "ConfiguracaoContabil.busca", query = "select c from ConfiguracaoContabil c")
})
public class ConfiguracaoContabil implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_CONFIGURACAOCONTABIL", strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToOne
    private TipoReceita receitaDeJuros;
    @OneToOne
    private TipoReceita receitaDeMultas;
    @OneToOne
    private TipoReceita receitaDeDescontosObtidos;
    @OneToOne
    private TipoReceita receitaDeVariacaoCambial;
    @OneToOne
    private TipoDespesa despesaDejuros;
    @OneToOne
    private TipoDespesa despesaDeMultas;
    @OneToOne
    private TipoDespesa despesaDeDescontosConcedidos;
    @OneToOne
    private TipoDespesa despesaDeVariacaoCambial;

    public ConfiguracaoContabil() {
    }

    public ConfiguracaoContabil(Long id, TipoReceita receitaDeJuros, TipoReceita receitaDeMultas, TipoReceita receitaDeDescontosObtidos, TipoReceita receitaDeVariacaoCambial, TipoDespesa despesaDejuros, TipoDespesa despesaDeMultas, TipoDespesa despesaDeDescontosConcedidos, TipoDespesa despesaDeVariacaoCambial) {
        this.id = id;
        System.out.println("Receita" + receitaDeJuros);
        this.receitaDeJuros = receitaDeJuros;
        this.receitaDeMultas = receitaDeMultas;
        this.receitaDeDescontosObtidos = receitaDeDescontosObtidos;
        this.receitaDeVariacaoCambial = receitaDeVariacaoCambial;
        this.despesaDejuros = despesaDejuros;
        this.despesaDeMultas = despesaDeMultas;
        this.despesaDeDescontosConcedidos = despesaDeDescontosConcedidos;
        this.despesaDeVariacaoCambial = despesaDeVariacaoCambial;
    }

    public Long getId() {
        return id;
    }

    public TipoReceita getReceitaDeJuros() {
        return receitaDeJuros;
    }

    public TipoReceita getReceitaDeMultas() {
        return receitaDeMultas;
    }

    public TipoReceita getReceitaDeDescontosObtidos() {
        return receitaDeDescontosObtidos;
    }

    public TipoReceita getReceitaDeVariacaoCambial() {
        return receitaDeVariacaoCambial;
    }

    public TipoDespesa getDespesaDeJuros() {
        return despesaDejuros;
    }

    public TipoDespesa getDespesaDeMultas() {
        return despesaDeMultas;
    }

    public TipoDespesa getDespesaDeDescontosConcedidos() {
        return despesaDeDescontosConcedidos;
    }

    public TipoDespesa getDespesaDeVariacaoCambial() {
        return despesaDeVariacaoCambial;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ConfiguracaoContabil)) {
            return false;
        }
        ConfiguracaoContabil outro = (ConfiguracaoContabil) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "ConfiguracaoContabil{" + "id=" + id + ", receitaDeJuros=" + receitaDeJuros + ", receitaDeMultas=" + receitaDeMultas + ", receitaDeDescontosObtidos=" + receitaDeDescontosObtidos + ", receitaDeVariacaoCambial=" + receitaDeVariacaoCambial + ", despesaDejuros=" + despesaDejuros + ", despesaDeMultas=" + despesaDeMultas + ", despesaDeDescontosConcedidos=" + despesaDeDescontosConcedidos + ", despesaDeVariacaoCambial=" + despesaDeVariacaoCambial + '}';
    }

}
