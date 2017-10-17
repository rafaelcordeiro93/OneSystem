package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.TipoDeCalculoDeCusto;
import br.com.onesystem.valueobjects.TipoDeFormacaoDePreco;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.inject.Any;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Any
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1,
        sequenceName = "SEQ_CONFIGURACAO", name = "SEQ_CONFIGURACAO")
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

    private String caminhoImpressoraTexto;

    @NotNull(message = "{Utilizar_Cep_Not_Null}")
    private boolean utilizarCep;

    public Configuracao() {
    }

    public Configuracao(Long id, TipoDespesa despesaDeComissao, Moeda moedaPadrao,
            TipoDeFormacaoDePreco tipoDeFormacaoDePreco, TipoDeCalculoDeCusto tipoDeCalculoDeCusto,
            String caminhoImpressoraTexto, boolean utilizarCep) throws DadoInvalidoException {
        this.id = id;
        this.despesaDeComissao = despesaDeComissao;
        this.moedaPadrao = moedaPadrao;
        this.tipoDeFormacaoDePreco = tipoDeFormacaoDePreco;
        this.tipoDeCalculoDeCusto = tipoDeCalculoDeCusto;
        this.caminhoImpressoraTexto = caminhoImpressoraTexto;
        this.utilizarCep = utilizarCep;
        ehValido();
    }

    public void ehValido() throws DadoInvalidoException{
        List<String> campos = Arrays.asList("utilizarCep");
        new ValidadorDeCampos<Configuracao>().valida(this, campos);
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

    public String getCaminhoImpressoraTexto() {
        return caminhoImpressoraTexto;
    }

    public boolean isUtilizarCep() {
        return utilizarCep;
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
