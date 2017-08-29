/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.LancamentoBancario;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoLancamentoBancario;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Cordeiro
 */
public class LancamentoBancarioBuilder {

    private Long id;
    private Conta conta;
    private TipoReceita receita;
    private BigDecimal valor;
    private TipoDespesa despesa;
    private List<Baixa> baixas;
    private Date emissao;
    private String observacao;
    private TipoLancamentoBancario tipoLancamentoBancario;
    private boolean estornado;
    private Long idRelacaoEstorno;
    private Filial filial;

    public LancamentoBancarioBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public LancamentoBancarioBuilder comConta(Conta conta) {
        this.conta = conta;
        return this;
    }

    public LancamentoBancarioBuilder comReceita(TipoReceita receita) {
        this.receita = receita;
        return this;
    }

    public LancamentoBancarioBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public LancamentoBancarioBuilder comDespesa(TipoDespesa despesa) {
        this.despesa = despesa;
        return this;
    }

    public LancamentoBancarioBuilder comBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
        return this;
    }

    public LancamentoBancarioBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public LancamentoBancarioBuilder comObservacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public LancamentoBancarioBuilder comTipoLancamentoBancario(TipoLancamentoBancario tipoLancamentoBancario) {
        this.tipoLancamentoBancario = tipoLancamentoBancario;
        return this;
    }

    public LancamentoBancarioBuilder comEstornado(boolean estornado) {
        this.estornado = estornado;
        return this;
    }

    public LancamentoBancarioBuilder comIdRelacaoEstorno(Long idEstorno) {
        this.idRelacaoEstorno = idEstorno;
        return this;
    }
    
    public LancamentoBancarioBuilder comFilial(Filial filial){
        this.filial = filial;
        return this;
    }

    public LancamentoBancario construir() throws DadoInvalidoException {
        return new LancamentoBancario(id, emissao, conta, valor, receita, despesa, baixas, observacao, tipoLancamentoBancario, estornado, idRelacaoEstorno, filial);
    }

}
