package br.com.onesystem.war.builder;

import br.com.onesystem.domain.ConfiguracaoCambio;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConfiguracaoCambioBV implements Serializable {

    private Long id;
    private TipoDespesa despesaDivisaoLucro;
    private List<Pessoa> pessoaDivisaoLucro = new ArrayList<Pessoa>();
    private boolean ativo = false;
    private Pessoa pessoaCaixa;
    private Conta contaCaixa;

    public ConfiguracaoCambioBV(ConfiguracaoCambio configuracao) {
        this.id = configuracao.getId();
        this.despesaDivisaoLucro = configuracao.getDespesaDivisaoLucro();
        this.pessoaDivisaoLucro = configuracao.getPessoaDivisaoLucro();
        this.ativo = configuracao.isAtivo();
        this.pessoaCaixa = configuracao.getPessoaCaixa();
        this.contaCaixa = configuracao.getContaCaixa();
    }

    public ConfiguracaoCambioBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoDespesa getDespesaDivisaoLucro() {
        return despesaDivisaoLucro;
    }

    public void setDespesaDivisaoLucro(TipoDespesa despesaDivisaoLucro) {
        this.despesaDivisaoLucro = despesaDivisaoLucro;
    }

    public List<Pessoa> getPessoaDivisaoLucro() {
        return pessoaDivisaoLucro;
    }

    public void setPessoaDivisaoLucro(List<Pessoa> pessoaDivisaoLucro) {
        this.pessoaDivisaoLucro = pessoaDivisaoLucro;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Pessoa getPessoaCaixa() {
        return pessoaCaixa;
    }

    public Conta getContaCaixa() {
        return contaCaixa;
    }

    public void setContaCaixa(Conta contaCaixa) {
        this.contaCaixa = contaCaixa;
    }

    public void setPessoaCaixa(Pessoa pessoaCaixa) {
        this.pessoaCaixa = pessoaCaixa;
    }
    
    public ConfiguracaoCambio construir() throws DadoInvalidoException {
        return new ConfiguracaoCambio(id, despesaDivisaoLucro, ativo, pessoaCaixa, contaCaixa);
    }

}
