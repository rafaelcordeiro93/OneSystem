/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoContabil;
import br.com.onesystem.valueobjects.TipoNota;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.io.Serializable;
import java.util.Arrays;
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
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael
 */
@Entity
@SequenceGenerator(sequenceName = "SEQ_OPERACAO", initialValue = 1, allocationSize = 1,
        name = "SEQ_OPERACAO")
public class Operacao implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_OPERACAO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(min = 4, max = 80, message = "{nome_lenght}")
    private String nome;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{operacao_financeira_not_null}")
    private OperacaoFinanceira operacaoFinanceira;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{tipo_nota_not_null}")
    private TipoNota tipoNota;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{tipo_operacao_not_null}")
    private TipoOperacao tipoOperacao;
    @NotNull(message = "{receita_venda_vista_not_null}")
    @ManyToOne
    private Receita vendaAVista;
    @NotNull(message = "{receita_venda_prazo_not_null}")
    @ManyToOne
    private Receita vendaAPrazo;
    @NotNull(message = "{receita_servico_vista_not_null}")
    @ManyToOne
    private Receita servicoAVista;
    @NotNull(message = "{receita_servico_prazo_not_null}")
    @ManyToOne
    private Receita servicoAPrazo;
    @NotNull(message = "{receita_frete_not_null}")
    @ManyToOne
    private Receita receitaFrete;
    @NotNull(message = "{despesa_CMV_not_null}")
    @ManyToOne
    private Despesa despesaCMV;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{contabilizar_CMV_not_null}")
    private TipoContabil contabilizarCMV;
    @NotNull(message = "{compra_vista_not_null}")
    @ManyToOne
    private Despesa compraAVista;
    @NotNull(message = "{compra_prazo_not_null}")
    @ManyToOne
    private Despesa compraAPrazo;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "operacoes")
    private List<OperacaoDeEstoque> operacaoDeEstoque;
    @OneToMany(mappedBy = "operacao")
    private List<NotaEmitida> notasEmitidas;

    public Operacao() {
    }

    public Operacao(Long id, String nome, OperacaoFinanceira operacaoFinanceira, TipoNota tipoNota,
            TipoOperacao tipoOperacao, Receita vendaAVista, Receita vendaAPrazo, Receita servicoAVista,
            Receita servicoAPrazo, Receita receitaFrete, Despesa despesaCMV, TipoContabil contabilizarCMV,
            Despesa compraAVista, Despesa compraAPrazo, List<OperacaoDeEstoque> operacaoDeEstoque) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.operacaoFinanceira = operacaoFinanceira;
        this.tipoNota = tipoNota;
        this.tipoOperacao = tipoOperacao;
        this.vendaAVista = vendaAVista;
        this.vendaAPrazo = vendaAPrazo;
        this.servicoAVista = servicoAVista;
        this.servicoAPrazo = servicoAPrazo;
        this.receitaFrete = receitaFrete;
        this.despesaCMV = despesaCMV;
        this.contabilizarCMV = contabilizarCMV;
        this.compraAVista = compraAVista;
        this.compraAPrazo = compraAPrazo;
        this.operacaoDeEstoque = operacaoDeEstoque;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public OperacaoFinanceira getOperacaoFinanceira() {
        return operacaoFinanceira;
    }

    public TipoNota getTipoNota() {
        return tipoNota;
    }

    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }

    public Receita getVendaAVista() {
        return vendaAVista;
    }

    public Receita getVendaAPrazo() {
        return vendaAPrazo;
    }

    public Receita getServicoAVista() {
        return servicoAVista;
    }

    public Receita getServicoAPrazo() {
        return servicoAPrazo;
    }

    public Receita getReceitaFrete() {
        return receitaFrete;
    }

    public Despesa getDespesaCMV() {
        return despesaCMV;
    }

    public TipoContabil getContabilizarCMV() {
        return contabilizarCMV;
    }

    public Despesa getCompraAVista() {
        return compraAVista;
    }

    public Despesa getCompraAPrazo() {
        return compraAPrazo;
    }

    public List<OperacaoDeEstoque> getOperacaoDeEstoque() {
        return operacaoDeEstoque;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome", "operacaoFinanceira", "tipoNota", "tipoOperacao", "vendaAVista", "vendaAPrazo",
                "servicoAVista", "servicoAPrazo", "receitaFrete", "despesaCMV", "contabilizarCMV", "compraAVista", "compraAPrazo");
        new ValidadorDeCampos<Operacao>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Operacao)) {
            return false;
        }
        Operacao outro = (Operacao) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Operacao{" + "id=" + id + ", nome=" + nome + ", operacaoFinanceira=" + operacaoFinanceira + ", tipoNota=" + tipoNota + ", tipoOperacao=" + tipoOperacao + ", vendaAVista=" + vendaAVista + ", vendaAPrazo=" + vendaAPrazo + ", servicoAVista=" + servicoAVista + ", servicoAPrazo=" + servicoAPrazo + ", receitaFrete=" + receitaFrete + ", despesaCMV=" + despesaCMV + ", contabilizarCMV=" + contabilizarCMV + ", compraAVista=" + compraAVista + ", compraAPrazo=" + compraAPrazo + '}';
    }

}
